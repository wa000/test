package test.socket.p2p;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.util.HashMap;
import java.util.Map;

/**
 * P2P服务端，负责接收连接消息
 */
public class P2pServerTest
{
    private static Map<String, String> socketCache = new HashMap<>();

    public static void main(String args[]) throws Exception
    {

        int clientPort = 0;
        String ip = "";

        // 开启20880端口的udp监听服务
        final DatagramSocket ds = new DatagramSocket(20880);
        String message = "";
        String str = "";
        byte by[] = new byte[2000];
        DatagramPacket dp = new DatagramPacket(by, 0, by.length);
        SocketAddress socketAddress = null;
        InetSocketAddress inetSocketAddress = null;

        // 开始监听
        while(true)
        {
            System.out.println("wait...");
            ds.receive(dp);
            System.out.println("success");
            message = new String(dp.getData(),0, dp.getLength());
            System.out.println("message:" + message);
            socketAddress = dp.getSocketAddress();
            inetSocketAddress = (InetSocketAddress) socketAddress;
            clientPort = inetSocketAddress.getPort();
            ip=inetSocketAddress.getAddress().getHostAddress();
            if(message.startsWith("getIp"))
            {
                str = clientPort + "," + ip;

                if(message.contains("@_@"))
                {
                    String socketName = message.split("@_@")[1];

                    socketCache.put(socketName, str);
                }

                byte buf[] = str.getBytes();
                dp.setData(buf,0, buf.length);
                ds.send(dp);
                System.out.println(clientPort + "," + ip);
            }

            if(message.startsWith("getSocket"))
            {
                if(message.contains("@_@"))
                {
                    String socketName = message.split("@_@")[1];
                    String result = socketCache.get(socketName);
                    if(null == result)
                    {
                        result = "";
                    }
                    byte buf[] = result.getBytes();
                    dp.setData(buf,0, buf.length);
                    ds.send(dp);
                    System.out.println("getSocket返回：" + result);
                }
            }

            dp.setData(by,0,by.length);
        }
    }
}
