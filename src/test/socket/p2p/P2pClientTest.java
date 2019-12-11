package test.socket.p2p;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.*;

public class P2pClientTest
{
    private static int SERVER_PORT = 20880;
    //    private static String SERVER_IP = "47.104.28.163";
    private static String SERVER_IP = "127.0.0.1";

    public static void main(String args[]) throws Exception {

        System.out.println("输入客户端名：");
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        String clientName = reader.readLine();

        int port = 20881;
        if("2".equals(clientName))
        {
            port = 20882;
        }

        SocketAddress server = new InetSocketAddress(SERVER_IP, SERVER_PORT);
        DatagramSocket ds = new DatagramSocket();
        String str = "getIp" + "@_@" + clientName;

        String message = sendMessage(str, server, port);
        System.out.println("本机端口、IP: " + message);

        System.out.println("输入需要连接的客户端名：");
        clientName = reader.readLine();

        String socketInfo = "";
        while("".equals(socketInfo))
        {
            System.out.println("等待连接客户端:" + clientName);
            Thread.sleep(1000);
            socketInfo = sendMessage("getSocket@_@" + clientName, server, port);
        }
        System.out.println(socketInfo);

        System.out.println("开始socket连接");

    }

    /**
     * 发送UDP请求
     *
     * @param str
     * @param server
     * @return
     */
    private static String sendMessage(String str, SocketAddress server, int port)
    {
        try
        {
            DatagramSocket ds = new DatagramSocket(port);
            byte buff[] = str.getBytes();
            byte buffer[] = new byte[8 * 1024];
            DatagramPacket dp = new DatagramPacket(buff, 0, buff.length, server);

            ds.send(dp);//发送信息到服务器
            dp.setData(buffer, 0, 8 * 1024);
            ds.receive(dp);
            String message = new String(dp.getData(), 0, dp.getLength());

            ds.close();
            return message;
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return null;
        }
    }

}
