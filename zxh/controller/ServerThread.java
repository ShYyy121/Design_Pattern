package controller;

import com.alibaba.fastjson2.JSON;
import model.Command.Add;
import model.Command.Alter;
import model.Command.Reduce;
import model.memento.Caretaker;
import model.memento.Memento;
import model.memento.Originator;
import model.Dao;
import model.Student;
import view.Director;
import view.GetHTML;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Set;

public class ServerThread extends Thread{
    ArrayList<Student> students=new ArrayList<>();
    Selector selector=null;
    ServerSocketChannel serverSocketChannel = null;
    StringBuffer result = null;

    public ServerThread(Selector selector,ServerSocketChannel serversocketChannel,StringBuffer result){
        this.selector=selector;
        this.serverSocketChannel=serversocketChannel;
        this.result=result;
    }
    @Override
    public void run() {
        Dao dao=Dao.getInstance();
        try {
            students=dao.getstudents();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        for (;;){

            int select = 0;
            try {
                select = selector.select();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            if(select == 0){
                continue;
            }

            Set<SelectionKey> selectionKeys = selector.selectedKeys();
            Iterator<SelectionKey> iterator = selectionKeys.iterator();
            while(iterator.hasNext()){
                SelectionKey selectionKey = iterator.next();
                iterator.remove();

                //accept
                if(selectionKey.isAcceptable()) {
                    SocketChannel channel = null;
                    try {
                        channel = serverSocketChannel.accept();
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }

                    result.append("HTTP /1.1 200 ok \r\n");
                    // 跨域解决
                    result.append("Access-Control-Allow-Origin:*\r\n");

                    StringBuffer sbuf = new StringBuffer();
                    ByteBuffer buffer = ByteBuffer.allocate(1024);
                    try {
                        channel.read(buffer);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                    buffer.flip();
                    sbuf.append(Charset.forName("UTF-8").decode(buffer));
                    buffer.clear();

                    String mes = sbuf.toString();
                    if(mes.startsWith("POST")){
                        String e = "";
                        //获取/的位置和空格位置 截取字符串得到参数 根据参数执行方法
                        String method = mes.substring(mes.indexOf("/")+1, sbuf.indexOf(" ", 6));
                        switch (method){
                            case "recall":
                                Memento recall = Caretaker.getRecall();
                                if(recall == null){
                                    e = "error";
                                }else{
                                    String op = recall.getState();
                                    Student student = recall.getStudent();
                                    switch (op){
                                        case "alter":
                                            Alter alter=new Alter(student);
                                            if (!alter.execute()){
                                                e="error";
                                            }
//                                            dao.reduce(student.getNum(),student.getName(),false);
                                            break;
                                        case "add":
                                            //新增对应删除
                                            Add add=new Add(student.getNum());
                                                if(!add.execute()){
                                                    e = "error";
                                                }
                                           break;
                                        case "reduce":
                                            //删除对应新增
                                            Reduce reduce=new Reduce(student);
                                            if(!reduce.execute()){
                                                e = "error";
                                            }
                                            break;
                                    }
                                }
                            case "get":
                                if(e == ""){
                                    String read = dao.read();
                                    e = read == null? "": read;
                                }
                                try {
                                    dao.detele();
                                } catch (Exception ex) {
                                    throw new RuntimeException(ex);
                                }
                                break;
                            case "add":
                                int i=0;
                                ArrayList<Student> students1=dao.getstudents();
                                String addData = mes.substring(mes.lastIndexOf("\n") + 1);
                                for (Student student : students1) {
                                    Student s1 = JSON.parseObject(JSON.toJSONString(student), Student.class);
                                    Student s2 = JSON.parseObject(addData, Student.class);
                                    if (s1.getNum().equals(s2.getNum())&&s1.getName()!=s2.getName()){
                                        Originator originator=new Originator();
                                        originator.setState("alter",student);
                                        Caretaker.add(originator.saveStateToMemento());
                                        i=1;
                                    }
                                }
                                if (i==1){
                                    dao.write(addData,false);
                                }
                                else {
                                    if (!dao.write(addData, true)) {
                                        e = "error";
                                    }
                                }
                                try {
                                    dao.detele();
                                } catch (Exception ex) {
                                    throw new RuntimeException(ex);
                                }
                                break;
                            case "reduce":
                                String reduceData = mes.substring(mes.lastIndexOf("\n") + 1);
                                if(!dao.reduce(reduceData, true)){
                                    e = "error";
                                }
                                break;
                            case "esc":
                                try {
                                    dao.save();
                                } catch (Exception ex) {
                                    throw new RuntimeException(ex);
                                }

                                System.exit(0);
                                break;
                        }

                        result.append("Content-Type:application/json \r\n");
                        result.append("Content-Length:"+e.getBytes(StandardCharsets.UTF_8).length + "\r\n");
                        result.append("\r\n" + e);
                    }else{
                        Director director=new Director(new GetHTML());
                        StringBuffer data=director.gethtml();
                        result.append("Content-Type:text/html \r\n");
                        result.append("Content-Length:" + data.toString().getBytes().length  + "\r\n");
                        result.append("\r\n" + data.toString());
                    }
                    if(result.length() > 0){
                        try {
                            channel.write(ByteBuffer.wrap(result.toString().getBytes()));
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                    }
                    //数据清空
                    result.setLength(0);
                }
            }
        }
    }
}
