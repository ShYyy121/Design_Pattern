package model;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import model.memento.Caretaker;
import model.memento.Originator;

import java.io.*;
import java.util.ArrayList;
import java.util.Iterator;

public class Dao {
    private static Dao dao;
    private static ArrayList<Student> students=new ArrayList<>();
    public Dao() {

    }

    public  static Dao getInstance(){
        if(dao==null) {
            synchronized (Dao.class) {
                if (dao == null) {
                    dao = new Dao();
                }
            }
        }
        return dao;
    }
    public  String read(){
        try {
            if (students!=null){
                String json = JSON.toJSON(students).toString();
                return json;
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return "";
    }
    public  boolean write(String data, boolean add){
        if(data != null && data != ""){
            Student student = JSON.parseObject(data, Student.class);
//            StudentInfo.add(student);
            students.add(student);
            if(add){
//                Recall recall = new Recall("add", student);
                Originator originator=new Originator();
                originator.setState("add",student);
                Caretaker.add(originator.saveStateToMemento());
            }
        }
            return true;
    }
    public void detele(){
        ArrayList<Student> students=getstudents();
        JSONObject object=null;
        try {
            String s1 = "\"data\"";
            s1 = "{" + s1 + ":";
            String s = s1 + JSON.toJSON(students).toString() + "}";
            object = JSON.parseObject(s);
        } catch (Exception e) {
            e.printStackTrace();
        }
        int conduct=1;
        JSONArray jsonArray = object.getJSONArray("data");
        students.clear();
        for (int i = 0; i < jsonArray.size(); i++) {
            conduct=1;
            int times=0;

            JSONObject jsonObject = jsonArray.getJSONObject(i);
            String name=jsonObject.getString("name");
            String num=jsonObject.getString("num");
            for (int j = i+1; j < jsonArray.size(); j++) {
                JSONObject jsonObject1 = jsonArray.getJSONObject(j);
                String name1=jsonObject1.getString("name");
                String num1=jsonObject1.getString("num");
                if (name.equals(name1)||num.equals(num1)){
                    conduct=0;
                }
            }
            if (conduct == 1){

                Student s=new Student(jsonObject.getString("num"), jsonObject.getString("name") );
                students.add(s);
            }
        }
    }
    static {
        try {
//            students=null;
            InputStream is = new FileInputStream(new File("zxh/Students.txt"));
            InputStreamReader isr = new InputStreamReader(is, "UTF-8");
            BufferedReader br = new BufferedReader(isr);
            String jsons = br.readLine();
            JSONArray array = JSON.parseArray(jsons);
            if (array != null && !array.isEmpty()) {
                students.clear();
                for (int i = 0; i < array.size(); i++) {
                    JSONObject jsonObject = array.getJSONObject(i);
                    Student student = new Student(jsonObject.getString("num"), jsonObject.getString("name"));
                    students.add(student);
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public  ArrayList<Student> getstudents() {
        return students;
    }
    public void save() throws Exception {
        OutputStream os = new FileOutputStream("zxh/Students.txt", false);
                OutputStreamWriter osw = new OutputStreamWriter(os, "UTF-8");
                BufferedWriter bw = new BufferedWriter(osw);
        bw.write(JSON.toJSON(students).toString());
        bw.close();
    }
    public boolean reduce(String num,String name,boolean add){
        if (num != null && num != ""){
            int i = 0;
            Iterator<Student> iterator = students.iterator();
            while (iterator.hasNext()) {
                Student next = iterator.next();
                if (num.equals(next.getNum())) {
                    students.get(i).setName(name);
                    return true;
                }
                i++;
            }
        }
        return false;
    }
    public  boolean reduce(String num, boolean add){
            if (num != null && num != "") {
                Iterator<Student> iterator = students.iterator();
                int i = 0;
                while (iterator.hasNext()) {
                    Student next = iterator.next();
                    if (num.equals(next.getNum())) {
                        //记录
                        students.remove(i);
                        if (add) {
                            Originator originator = new Originator();
                            originator.setState("reduce", next);
                            Caretaker.add(originator.saveStateToMemento());
                        }
                        return true;
                    }
                    i++;
                }
            }
        return false;
    }
}

