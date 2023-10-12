# 设计模式
## 大作业
实验目的：
练习实现多个模式合成解决问题。
## 实验内容：
1. 基本要求 
实现一个学生信息管理程序。 
（1）其基于命令行界面原型如下，你需要实现原型中所有的功能。界面仅 
供参考，你可以在此基础上适当杜撰其他功能性需求。 
（2）全部学生信息需要能持久化到文件中。每次重新运行程序，都可以载 
入之前输入的学生信息。 


您可以进行以下操作： 
1、添加一个新的学生信息 
2、修改一个已有的学生信息 
3、删除一个已有的学生信息 
4、撤销上一次操作 
5、列出全部学生信息 
6、退出程序 
您的输入是：
2. 进阶要求 
实现带有图形用户界面的程序。界面原型如下。图形用户界面首选使用 html 
进行表示，当然你也可以选择其他方法实现该图形用户界面。

![image](https://github.com/ShYyy121/Design_Pattern/assets/145829122/3b8a4407-71bb-46fe-85f9-8a42fa07ff16)

3. 难度选择 
你可以根据自己的情况选择以下四种难度中的一种完成该作业。 
（1）基础要求（完成好可以拿到 65+分数）：完成“1.基本要求”的全部要 
求。 
（2）进阶要求（完成好可以拿到 75+分数）：实现一个有图形用户界面的单 
机版程序。 
（3）提升要求（完成好可以拿到 80+分数）：实现一个网络系统。学生信息 
存储于服务器端。客户通过 web 端或 html 进行访问。 
（4）更高要求（完成好可以拿到 90+分数）：在提升要求基础上，支持多用 
户同时访问。这时需要考虑如何保证数据的一致性——你可以自己设计相应的操 
作逻辑。




## 实验过程与分析：
### 实验内容分析：
1.首先，明确想要完成的难度，大约是3或4的难度
2.需要完成的功能：
（一）可以通过网页打开，显示所需的界面（界面也需要设计）
（二）按下添加按钮，增加两个文本框，输入内容后，可以将内容发送到后端进行存储，并且实时反映到前端。同时这一操作将被记录下来，方便后续进行撤销。
（三）按下删除按钮，文本内容和对应的文本框全部被删除掉，发送请求至后端，在存储中删除掉相应的内容，同时，记录这一操作，方便后续进行撤销。
（四）修改功能，网页实时监测文本框内的变化，如果有文本发生变化，就将修改后的数据发送到后端进行存储和修改，同时，这一步操作也将被记录下来，方便后续进行撤销此操作。
（五）按下撤销按钮，读取到上一步进行过的操作，并将操作前的数据进行返回，并且更新到前端网页上。（可以一直撤销上一步操作）
（六）退出功能，按下退出按钮后，网页自动关闭（程序是否也需要关闭？ 应该不需要，本人根据日常生活中的系统，服务端使需要服务人员来进行关闭，所以按下退出按钮仅仅是客户网页的关闭）
3.预计需要使用的设计模式：
（一）MVC设计模式：利用mvc设计模式，在view层中进行网页的界面的数据可视化，在model层中，代表学生对象，同时是事务模型层，完成业务处理，在这一层中可以使用dao层直接操作数据。controller层中作用于模型和视图上。它控制数据流向模型对象，并在数据变化时更新视图。它使视图与模型分离开。
（二）单例模式：可以考虑在dao层中采用单例模式。
（三）备忘录模式：在进行撤销的操作的时候，可以采用备忘录模式，保存之前的操作状态，以便的实现撤销的功能的时候进行恢复操作状态。
（四）命令模式：在进行撤销的时候和备忘录模式一起使用，以命令的形式包裹在对象中，调用时执行相应的命令（在使用socket进行通信的时候，不仅仅是撤销功能，在进行添加/删除/修改的时候也同样可以使用命令模式，因为socket之间的通信方法本身就是发送请求和接收请求从而完成相对应的命令，只是在这里将命令模式+备忘录模式一起使用完成了撤销的功能）
（五）数据访问对象模式（Dao模式）：实现数据访问Student类接口的实体类，在Dao中完成数据的读取，写入等操作。
（六）建造者模式：将HTML文档读取出来并发送到网页端，使网页中可以显示所需的界面，这里采用这个是考虑到以后可以用不同的文件格式，方便我们进行功能的扩建。
（七）过滤器模式：在socket通信中，需要服务端和客户端来回不断地发送请求，在这些请求中可能会有许多不需要或者错误的请求，我们需要将这些请求拦截并过滤掉，所以可以采用过滤器模式，避免报错。
（八）迭代器模式：在进行数据的操作的时候，难免会需要对数据进行遍历，这时候可以采用迭代器模式，遍历数据。
（九）传输对象模式：利用传输对象模式，可以实现客户在网页向服务端一次性发送带有多个属性的数据，根据这次需求，感觉并不一定会用得上。
## 实验过程：
1.首先采用MVC设计模式，分层处理编写代码。
2.在model层中创建Student类，定义成员变量。同时采用数据访问对象模式（即Dao模式）实现数据的读取和写入，在数据的读取和写入中利用JSON文件格式，读取关键字，提取所需的数据，完成功能。
3.在view层中，编写HTML文档，设计界面。
4.在controller层中，定义成员变量，通过构造器，监听新进来的TCP连接通道ServerSocketChannel，当客户进入网页，发送HTML界面到网页中，并实时接收客户端发来的请求调用model中的dao模式，完成数据的操作，并将数据等信息实时通过view实时发送到客户端中。
5.在HTML中用JavaScipt，实时监听按钮和文本框，完成发送请求，增加删除文本框等操作。（耗时较大，需要学习javascipt）
6.在model中添加备忘录模式和命令模式，记录操作状态，接收客户端的请求后，结合命令模式，完成操作的恢复。
实验中的重构及缺陷的弥补（针对上述实验过程）：
1.在最开始数据的操作和读取中，想要在HTML中编写javascipt完成对数据的增删改的功能，但是这样对javascipt语言的要求度较高，同时操作较为复杂不方便进行功能的增加，同时违背了开闭原则，所以通过请求发送和接受的方式，在后端进行数据的处理，更加方便，可扩展性高。
2.在数据的操作中，并没有采用Dao模式，而是每个功能都创建新的类来完成，会造成高耦合，低内聚，所以采用Dao模式，让数据在Dao层中进行处理，隔离了数据访问代码和业务逻辑代码，同时在Dao中，利用List集合在程序运行的时候读取数据，在程序结束的时候将数据写入文件中，实现了动态存储，转储操作与用户事务并发进行，转储期间允许数据库进行存取和修改操作，它不必等待所有运行的事务结束，也不会影响新事务的运行，实现了操作的并发性，可以避免多用户在进行操作的时候破坏数据的原子性。
3.如果想要实现多用户同时访问，则需要利用线程池，在使用线程池的时候利用了单例模式，避免大量线程频繁创建与销毁带来的时间成本，任务节点以及线程有上限，避免资源耗尽的风险。但我并未完全实现要求4，因为按照4的思路应该是利用线程池，当有多人在操作同一个文本的时候，对线程进行加锁，保证最先操作的人可以对数据进行操作，而后续的人的线程被锁，无法进行操作。而我这里并未完全完成数据的一致性，仅依靠于数据操作的低延迟，可以完成数据的一致性，如果当数据操作较为缓慢时，则不能实现，所以对于数据一致性的完成，仍需继续的学习和摸索。
4.对界面的重构，最开始对html界面进行设计的时候，文本框和按钮都是固定的位置，通过限定绝对左表确定其的位置，不方便进行文本框的新增和删除，同时是在后端读取数据的长度和内容，创建相应的文本框，这样操作较为冗杂，改进为服务端将数据发送到客户端网页，网页读取数据并创建相对应的文本框，这样网页更为灵活，可以实时更新数据，同时利用JavaScript，当按下按钮时，文本框新增两个或者删除，同时相对应的按钮也进行移动，这样避免了较为死板的网页。
### 类图：

![image](https://github.com/ShYyy121/Design_Pattern/assets/145829122/098be4bc-7872-40ef-b1a6-1bcdaf1cb033)

重要代码和实现机制：
1.前端请求的发送,增删改功能的实现，文本框的新增和删除：
 var u = "http://localhost:8080/";
    //封装请求
    var xhr = function (url, func, data = ""){
        let request = new XMLHttpRequest();
        request.open("POST", u+url);
        request.send(data);
        request.onreadystatechange = function (){
            if(request.readyState == 4 && request.status == 200){
                func(request);
            }
        };
    }
    //封装字符串判空
    var isEmpty = function (str){
        if(typeof str == 'string'){
            if(str != null && str != undefined && str != ''){
                return false;
            }
        }
        return true;
    }
    //删除
    var reduceF = function (obj){
        //学号
        var num = obj.parentNode.nextSibling.firstChild.value;
        xhr("reduce", function(){

        }, num);
        obj.parentNode.parentNode.remove();
    }
    //退出
    var escF = function (){
        if (confirm("您确定要关闭学生管理系统吗？")){
        window.opener=null;
        window.open('','_self');
        //不同浏览器关闭方法不同
        window.location.href='about::blank';
        window.close();
    }
    else{}
        xhr("esc", function(){

        });
    }
    //添加
    var addF = function (value, name){
        var tbody = document.getElementById("info").tBodies[0];
        var tr = document.createElement("tr");
        var td1 = document.createElement("td");
        td1.innerHTML = "<button onclick='reduceF(this)'>➖</button>";
        tr.appendChild(td1);
        var td2 = document.createElement("td");
        td2.innerHTML = "<input type=\"text\" value=\""+value+"\" onblur='listen(this)'/>";
        tr.appendChild(td2);
        var td3 = document.createElement("td");
        td3.innerHTML = "<input type=\"text\" value=\""+name+"\" onblur='listen(this)'/>";
        tr.appendChild(td3);
        tbody.appendChild(tr);
    }

    //页面加载
    window.onload = function () {
        //发送请求获取文件持久化数据
        var func = function (request){
            var json = request.responseText;
            if(isEmpty(json)){
                return;
            }
            var arr = JSON.parse(json);
            if(arr != null && arr.length > 0){
                for(i of arr){
                    addF(i["num"], i["name"]);
                }
            }
        };
        xhr("get", func);

    }
    //获取dom元素
    var add = function () {
        addF('', '');
    }

    //input监听
    var listen = function (obj){
        var childs = obj.parentNode.parentNode.childNodes;
        //获取两个input框
        var num = childs[1].firstChild;
        var name = childs[2].firstChild;
        var numV = num.value;
        var nameV = name.value;

        if(isEmpty(numV) || isEmpty(nameV)){
            return;
        }

        var data = "{'num': '"+numV+"','name': '"+nameV+"'}"

        //发送请求持久化
        xhr("add", function (request){
            var mes = request.responseText;
            if(isEmpty(mes)){
                return;
            }
            alert("添加失败！");
            num.value = "";
            name.value = "";
        }, data);
    }

    var recall = document.getElementById('recall');
    recall.onclick = function (){
        xhr("recall", function (request){
            var mes = request.responseText;
            if(mes == "error"){
                alert("没有可以撤销的！");
            }else{
                var tbody = document.getElementById("info").tBodies[0];
                tbody.innerHTML = "";
                var arr = JSON.parse(mes);
                if(arr != null && arr.length > 0){
                    for(i of arr){
                        addF(i["num"], i["name"]);
                    }
                }
            }
        });
    }
    var esc = document.getElementById('esc');
    esc.onclick = function () {
        escF();
        xhr("esc", function (request) {

        });
}
2.线程池+单例模式：实现多用户操作，同时利用NIO进行通信：
ThreadPool threadPool=ThreadPool.getThreadPool();

            Selector selector = null;
            ServerSocketChannel serverSocketChannel = null;
            StringBuffer result = null;
            try {
                selector = Selector.open();
                //net -> pc
                serverSocketChannel = ServerSocketChannel.open();
                serverSocketChannel.bind(new InetSocketAddress(8080));
                serverSocketChannel.configureBlocking(false);
                serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
                result = new StringBuffer();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            threadPool.executor(new ServerThread(selector,serverSocketChannel,result));
public class ThreadPool {
    private ThreadPoolExecutor executor = new ThreadPoolExecutor(1, 1000, 200, TimeUnit.MILLISECONDS, new ArrayBlockingQueue<Runnable>(5));
    private static ThreadPool instance = null;
    public static ThreadPool getThreadPool() {
        if (instance == null) {
            synchronized (ThreadPool.class) {
                if (instance == null) {
                    instance = new ThreadPool();
                }
            }
        }
        return instance;
    }
    private ThreadPool() {
    }
    public void shutdown() {
        executor.shutdown();
    }
    public void executor(Runnable runnable) {
        executor.execute(runnable);
    }
}
3.服务端接受请求并解析：
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
4.用户打开网页，服务端向客户端发送html格式的网页界面：
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
5.撤销操作，记录之前的操作，并在进行撤销操作时进行恢复，采用了备忘录模式+命令模式：
public class Caretaker {

    private static ArrayList<Memento> mementos = new ArrayList<>();

    public static Memento getRecall() {
        if(mementos.size() > 0){
            return mementos.remove(mementos.size() - 1);
        }
        return null;
    }
    public static Memento getStudent(){
        if (mementos.size()>0) {
            return mementos.get(mementos.size() - 1);
        }
        else {
            return mementos.get(mementos.size());
        }
    }
    public static void add(Memento memento) {
        mementos.add(memento);
    }
}
public class Memento {
   private String state;
   private Student student;

   public void setState(String state) {
      this.state = state;
   }


   public Student getStudent() {


      return student;
   }




   public void setStudent(Student student) {
      this.student = student;
   }

   public Memento(String state, Student student) {
      this.state = state;
      this.student = student;
   }

   public String getState(){
      return state;
   }

}
public class Originator {
   private String state;
   private Student student;
   public void setState(String state,Student student){
      this.state = state;
      this.student=student;
   }

   public String getState(){
      return state;
   }
 
   public Memento saveStateToMemento(){
      return new Memento(state,student);
   }
   
}
6.动态内存存储，利用List存储数据，当controller需要数据的时候将Dao层的List提取：
private static ArrayList<Student> students=new ArrayList<>();
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
## 遇到的问题和解决方法：
问题一：
使用127.0.0.1：8080的使用进入网页不会自动刷新显示数据，需要手动刷新一下才会显示数据

![image](https://github.com/ShYyy121/Design_Pattern/assets/145829122/59b87f0e-e6e1-4292-beef-b842eb0b8db3)

![image](https://github.com/ShYyy121/Design_Pattern/assets/145829122/c042f920-4d69-46b9-96e9-d99c2a5037ee)

解决方法：127.0.0.1通过网卡传输，依赖网卡，并受到网络防火墙和网卡相关的限制，所以使用localhost就可以解决进入网页不显示数据的问题

![image](https://github.com/ShYyy121/Design_Pattern/assets/145829122/658ba5a2-0727-4ebb-b966-d7db1514c9ef)

问题二：
不能发送指定的请求内容，只能进行请求，不能根据不同的按钮和文本满足不同的需求。

![image](https://github.com/ShYyy121/Design_Pattern/assets/145829122/ed2f0bb3-93d2-47c9-9fec-a881e4708673)

解决方法：
封装请求，利用getElementById获取其id，根据id的不同，发送不同的自定义请求，并在服务端进行接收，解析请求内容，利用命令模式，实现功能完成数据操作。

![image](https://github.com/ShYyy121/Design_Pattern/assets/145829122/ad33b8c5-2634-46e7-8f41-7eaa76279a3d)

![image](https://github.com/ShYyy121/Design_Pattern/assets/145829122/8cff3850-19a8-4143-8870-c674bf403756)


问题三：
在服务端向客户端发送网页界面的时候，字符串长度超出Content-Length中字节的长度，产生报错
解决方法：Content-Length中的计算需要把返回内容转换成byte数组后计算长度，因为网页中包含中文，会导致字符串长度和字节数组长度不一致
问题四：
在进行撤销的操作的时候，最开始采用的是一个记录上一步操作，导致只能撤销上一步的操作，不能一直撤销
解决方法：利用备忘录模式，创建List对象，存储所有进行过的操作和进行操作的对象的之前的状态，当客户端发送recall撤回请求的时候，读取List中索引最大处的内容，并进行撤销返回操作，恢复之前的状态。
问题五：
当服务端运行后关闭，再次运行的时候，会报错，显示socket端口正在被占用。
解决方法：在程序运行结束的时候需要将socket端口关闭
问题六：

![image](https://github.com/ShYyy121/Design_Pattern/assets/145829122/9952f833-389d-47d3-9739-dae3ccbd53a6)

解决方法：需要将输出输入流按顺序关闭，否则会导致需要使用的时候，已经关闭了，按顺序关闭也可以避免系统资源耗尽。


## 使用的设计模式：
（一）MVC模式：利用mvc设计模式，在view层中进行网页的界面的数据可视化，在model层中，代表学生对象，同时是事务模型层，完成业务处理，在这一层中可以使用dao层直接操作数据。controller层中作用于模型和视图上。它控制数据流向模型对象，并在数据变化时更新视图。它使视图与模型分离开。
（二）备忘录模式：在进行撤销的操作的时候，可以采用备忘录模式，保存之前的操作状态，以便的实现撤销的功能的时候进行恢复操作状态
（三）命令模式：在进行撤销的时候和备忘录模式一起使用，以命令的形式包裹在对象中，调用时执行相应的命令（在使用socket进行通信的时候，不仅仅是撤销功能，在进行添加/删除/修改的时候也同样可以使用命令模式，因为socket之间的通信方法本身就是发送请求和接收请求从而完成相对应的命令，只是在这里将命令模式+备忘录模式一起使用完成了撤销的功能）
（四）数据访问对象模式（Dao模式）：实现数据访问Student类接口的实体类，在Dao中完成数据的读取，写入等操作。
（五）建造者模式：将HTML文档读取出来并发送到网页端，使网页中可以显示所需的界面，这里采用这个是考虑到以后可以用不同的文件格式，方便我们进行功能的扩建
（六）单例模式：在使用线程池的时候，采用了单例模式，避免大量线程频繁创建与销毁带来的时间成本，任务节点以及线程有上限，避免资源耗尽的风险。
## 不足之处和需要继续思考的问题：
一、数据一致性
在多用户进行操作的时候，本程序仅是依靠数据的快速处理从而间接达到数据的一致性，但是如果数据处理较慢，就需要在多线程的时候加“锁”，当多个用户操作一个文本框的时候需要让最先操作的用户继续操作，其他用户线程加锁，从而来保证数据的一致性。
二、撤销顺序问题
在本程序中在撤销的时候，会将撤销的内容保存到最后的一排，即相当于将操作掉的内容重新写入数据中，可以对其进行改进，记录撤销内容在原本数据中的索引，在恢复时将其恢复到之前索引处。
三、界面的设计
对界面设计，可以更加美观，设置按钮和文本框在想要的位置，插入图片等，增加其观赏感。可以使用CSS。
四、设计模式的理解和使用
对于设计模式的使用，可以使用更多的设计模式，比如过滤器模式，传输对象模式等，增加系统的健壮性，易修改性和可扩展性，同时需要对每个设计模式有更深层次的理解和使用，例如在本程序中个人感觉Dao模式的使用并没有很完美，以及建造者模式的使用略有牵强，所以在后续学习中需要更加注重设计模式的理解和使用。
五、网络通信的理解和使用
对于网络通信的理解，对于本作业，或许可以采用websocket，实现实时通信，socket的通信过于局限，需要请求的发送和接受，局限性较大，同时对于通信的理解也需要更深层次的理解，比如如何在多线程多用户操作时对线程加锁，如何理解TCP协议和HTTP协议等都需要日后深入的学习。
