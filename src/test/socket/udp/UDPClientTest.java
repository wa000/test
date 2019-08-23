package test.socket.udp;

import java.util.*;
import java.net.*;

public class UDPClientTest {
    private static int SERVER_PORT = 20880;
    private static String SERVER_IP = "47.104.28.163";
//    private static String SERVER_IP = "127.0.0.1";
    public static Map<String, String> map = new HashMap<String, String>();

    public static void main(String args[]) throws Exception {
        SocketAddress server = new InetSocketAddress(SERVER_IP, SERVER_PORT);
        DatagramSocket ds = new DatagramSocket();
        String str = "getIp";
        byte buff[] = str.getBytes();
        byte buffer[] = new byte[8 * 1024];
        DatagramPacket dp = new DatagramPacket(buff, 0, buff.length, server);

        Scanner sc = new Scanner(System.in);

        ds.send(dp);//发送信息到服务器
        dp.setData(buffer, 0, 8 * 1024);
        ds.receive(dp);
        String message = new String(dp.getData(), 0, dp.getLength());
        System.out.println("本机端口、IP" + message);

        MyThread mt = new MyThread(ds);
        Thread thread = new Thread(mt);
        thread.start();

        while (true) {
            str = sc.next();
            if (str.startsWith("aa")) {
                buff = str.getBytes();
                dp.setData(buff, 0, buff.length);
                ds.send(dp);
            } else {
                String tempString = str;
                Set<String> set = map.keySet();
                Iterator<String> iter = set.iterator();
                while (iter.hasNext()) {
                    String key = iter.next();
                    String value = map.get(key);
                    tempString = "sendMessage" + "," + key + "," + value + "," + tempString;
                    buff = tempString.getBytes();
                    dp.setData(buff, 0, buff.length);
                    ds.send(dp);
                    System.out.println("发送成功");
                }
            }
        }
    }
}

class MyThread implements Runnable {
    DatagramSocket ds = null;
    DatagramPacket dp = null;
    String message = "";

    public MyThread(DatagramSocket ds) {
        this.ds = ds;
    }

    public void run() {
        try {
            byte buffer[] = new byte[1024];
            dp = new DatagramPacket(buffer, 0, buffer.length);
            byte buff[] = new byte[1024];
            while (true) {
                dp.setData(buff, 0, 1024);
                System.out.println("准备接收");
                ds.receive(dp);
                message = new String(dp.getData(), 0, dp.getLength());
                if (message.startsWith("udp")) {
                    String port = message.split(",")[1];
                    String ip = message.split(",")[2];
                    UDPClientTest.map.put(port, ip);
                    System.out.println("目标端口、IP:" + port + "," + ip);
                } else {
                    System.out.println("接收到" + message);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}