package test.socket;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketAddress;


public class SocketServerTest {
    /**
     * Socket服务端
     */
    public static void main(String[] args) {
        try {
            ServerSocket serverSocket = new ServerSocket(20880);
            System.out.println("服务端已启动，等待客户端连接..");

            InputStream inputStream = null;
            PrintWriter printWriter = null;
            OutputStream outputStream = null;
            BufferedReader bufferedReader = null;
            InputStreamReader inputStreamReader = null;

            for(int i = 0; i < 100000; i++)
            {
                Socket socket = serverSocket.accept();//侦听并接受到此套接字的连接,返回一个Socket对象
                //根据输入输出流和客户端连接
                inputStream = socket.getInputStream();//得到一个输入流，接收客户端传递的信息
                inputStreamReader = new InputStreamReader(inputStream);//提高效率，将自己字节流转为字符流
                bufferedReader = new BufferedReader(inputStreamReader);//加入缓冲区
                String temp = null;
                String info = "";
                while ((temp = bufferedReader.readLine()) != null) {
                    info += temp;
                    System.out.println("已接收到客户端连接");
                    System.out.println("服务端接收到客户端信息：" + info + ",当前客户端ip为：" + socket.getInetAddress().getHostAddress());
                }

                outputStream = socket.getOutputStream();//获取一个输出流，向服务端发送信息
                printWriter = new PrintWriter(outputStream);//将输出流包装成打印流
                printWriter.print("你好，服务端已接收到您的信息");
                printWriter.flush();
                socket.shutdownOutput();//关闭输出流

                socket.close();
            }

            //关闭相对应的资源
            printWriter.close();
            outputStream.close();
            bufferedReader.close();
            inputStream.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}