//package test.proxy.three;
//
//import java.io.IOException;
//import java.io.InterruptedIOException;
//import java.io.PrintWriter;
//import java.net.ServerSocket;
//import java.net.Socket;
//import java.util.Iterator;
//
//// 下面是一个监听器，它监听来自某一个特定端口的连接。
//// 当它获得一个连接请求时，它调用服务器的addConnection()方法来接受或拒绝连接
//public class Listener extends Thread {
//ServerSocket listen_socket;    // 监听连接的套接字
//int port;                      // 端口
//Service service;               // 在端口上实现的服务
//volatile boolean stop = false; // 标志是否被请求停止
//// 创建一个服务器套接字来监听某个特定端口的连接
//public Listener(ThreadGroup group, int port, Service service) throws IOException
//{
//super(group, "Listener:" + port);
//listen_socket = new ServerSocket(port);
//// 给出一个非零超时信号使得accept()被中断
//listen_socket.setSoTimeout(600000);
//this.port = port;
//this.service = service;
//}
//
//// 停止接收连接
//public void pleaseStop() {
//this.stop = true;              // 设置停止标志位
//this.interrupt();              // 停止阻断accept()
//try { listen_socket.close(); } // 停止监听.
//catch(IOException e) {}
//}
//
//// 等待连接请求，接受，接着把socket传给sever的addConnection方法
//public void run() {
//while(!stop) {      // 循环直到要求停止
//try {
//Socket client = listen_socket.accept();
//addConnection(client, service);
//}
//catch (InterruptedIOException e) {}
//catch (IOException e) {log(e);}
//}
//}
//
//// 当接受客户端连接以后这个方法将会被监听器调用
//// 或者创建一个连接类，或者把这个连接加入到现有的连接列表中，抑或关闭连接
//protected synchronized void addConnection(Socket s, Service service) {
//// 如果到达连接上限
//if (connections.size() >= maxConnections) {
//try {
//// 则告诉客户端正在被拒绝.
//PrintWriter out = new PrintWriter(s.getOutputStream());
//out.print("连接被拒绝; " +"服务器忙，请稍后再试.\n");
//out.flush();
////关闭连接
//s.close();
//// 写log
//log("连接被拒绝：" +s.getInetAddress().getHostAddress() +
//":" + s.getPort() + ": 达到最大连接数.");
//} catch (IOException e) {log(e);}
//}
//else {  // 如果连接数未到上限
//// 创建一个线程处理连接
//Connection c = new Connection(s, service);
//// 并把它添加到当前连接列表中
//connections.add(c);
//// 把这个新的连接写入log
//log("Connected to " + s.getInetAddress().getHostAddress() +":" + s.getPort() + " on port " +
//s.getLocalPort() +" for service " + service.getClass().getName());
//// 开始连接线程提供服务
//c.start();
//}
//}
//
//// 在连接线程退出前调用这个函数，它把某个特定线程从线程列表中删除
//protected synchronized void endConnection(Connection c) {
//connections.remove(c);
//log("连接到： " + c.client.getInetAddress().getHostAddress() +":" + c.client.getPort() + " 关闭.");
//}
//
//// 改变现有的连接上限
//public synchronized void setMaxConnections(int max) {
//maxConnections = max;
//}
//
//// 显示server信息
//public synchronized void displayStatus(PrintWriter out) {
//// 显示所有服务的列表
//Iterator keys = services.keySet().iterator();
//while(keys.hasNext()) {
//Integer port = (Integer) keys.next();
//Listener listener = (Listener) services.get(port);
//out.print("SERVICE " + listener.service.getClass().getName()+ " ON PORT " + port +
//"\n");
//}
//// 显示现有连接上限
//out.print("MAX CONNECTIONS: " + maxConnections + "\n");
//// 显示现有连接列表
//Iterator conns = connections.iterator();
//while(conns.hasNext()) {
//Connection c = (Connection)conns.next();
//out.print("连接: " +c.client.getInetAddress().getHostAddress() +
//":" + c.client.getPort() + " 端口:" +c.client.getLocalPort() +
//c.service.getClass().getName() + "\n");
//}
//}
//}