package test.proxy.three;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class Connection extends Thread
{ // 多线程连接类
    Socket client; // 与客户端对话的套接字
    Service service; // 提供给客户端的service

    public Connection(Socket client, Service service)
    {// 构造函数
        super("Server.Connection:" + client.getInetAddress().getHostAddress() + ":" + client.getPort());
        this.client = client;
        this.service = service;
    }

    // 把客户端的输入输出流传给某个Service对象的 serve()方法
    public void run()
    {
        try
        {
            InputStream in = client.getInputStream();
            OutputStream out = client.getOutputStream();
            service.serve(in, out);
        }
        catch (IOException e)
        {
//            log(e);
        }
        finally
        {
            endConnection(this);
        }
    }
    
 // 在连接线程退出前调用这个函数，它把某个特定线程从线程列表中删除
    protected synchronized void endConnection(Connection c) {
//    connections.remove(c);
//    log("连接到： " + c.client.getInetAddress().getHostAddress() +":" + c.client.getPort() + " 关闭.");
    }
}