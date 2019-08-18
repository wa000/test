//package test.proxy.three;
//
//import java.io.OutputStream;
//import java.io.PrintWriter;
//import java.util.Date;
//import java.util.HashMap;
//import java.util.HashSet;
//import java.util.Map;
//import java.util.Set;
//
//// 下面的多线程server类框架，实现对端口的监听和数据传输的实时控制
//public class Server
//{ // 定义Server 类
//// server的状态量
//    Map services;
//    Set connections;
//    int maxConnections;
//    ThreadGroup threadGroup;
//    PrintWriter logStream;
//
//    public Server(OutputStream logStream, int maxConnections)
//    { // 构造函数
//        setLogStream(logStream);
//        log("Starting server"); // 记录日志信息
//        threadGroup = new ThreadGroup(Server.class.getName());
//        this.maxConnections = maxConnections;
//        services = new HashMap();
//        connections = new HashSet(maxConnections);
//    }
//
//    // 一个公共的方法 来设置当前登陆流，传递null来关闭登陆
//    public synchronized void setLogStream(OutputStream out)
//    {
//        if (out != null)
//            logStream = new PrintWriter(out);
//        else
//            logStream = null;
//    }
//
//    // 记录日志函数
//    protected synchronized void log(String s)
//    {
//        if (logStream != null)
//        {
//            logStream.println("[" + new Date() + "] " + s);
//            logStream.flush();
//        }
//    }
//
//    protected void log(Object o)
//    {
//        log(o.toString());
//    } // 把某个对象object写入log
//
//    // 使server在特定端口开特定服务
//    public synchronized void addService(Service service, int port) throws IOException
//    {
//        Integer key = new Integer(port);
//        // 检查在某个端口是否已经有其它service
//        if (services.get(key) != null)
//            throw new IllegalArgumentException("端口 " + port + " 已经被使用.");
//        // 创建监听类来监听端口的连接情况
//        Listener listener = new Listener(threadGroup, port, service);
//        // 保存在哈希表中
//        services.put(key, listener);
//        // 写log
//        log("启动服务： " + service.getClass().getName() + " 端口为： " + port);
//        // 开始监听
//        listener.start();
//    }
//
//    // 使server停止某个端口上的服务
//    public synchronized void removeService(int port)
//    {
//        Integer key = new Integer(port); // 哈希表关键字
//        // 在哈希表中查找对某个端口的监听对象
//        final Listener listener = (Listener) services.get(key);
//        if (listener == null)
//            return;
//        // 使得监听器停止
//        listener.pleaseStop();
//        // 从哈希表中删除
//        services.remove(key);
//        // 写log.
//        log("停止服务： " + listener.service.getClass().getName() + " 端口： " + port);
//    }
//}