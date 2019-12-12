package test.socket.p2p;

import java.io.*;
import java.net.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

public class P2pClientTest {
    private static int SERVER_PORT = 20880;
    private static String SERVER_IP = "47.104.28.163";
//    private static String SERVER_IP = "127.0.0.1";

    public static void main(String args[]) throws Exception {

        System.out.println("输入客户端名：");
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        String clientName = reader.readLine();

        int port = 20881;
        if ("2".equals(clientName)) {
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
        while ("".equals(socketInfo)) {
            System.out.println("等待连接客户端:" + clientName);
            Thread.sleep(1000);
            socketInfo = sendMessage("getSocket@_@" + clientName, server, port);
        }
        System.out.println(socketInfo);
        System.out.println("开始udp打洞");
        String ipConn = socketInfo.split(",")[1];
        String portConn = socketInfo.split(",")[0];
        server = new InetSocketAddress(ipConn, Integer.parseInt(portConn));
        sendMessage("testUDPdadong", server, port);

        System.out.println("开始socket连接");
        if (port == 20881) {
            System.out.println(getdate() + "  等待客户端连接...");
            ServerSocket serverSocket = new ServerSocket(port);
            Socket socket = serverSocket.accept();
            new sendMessThread1(socket).start();

            System.out.println(getdate() + "  客户端 （" + socket.getInetAddress().getHostAddress() + "） 连接成功...");
            InputStream in = socket.getInputStream();
            int len = 0;
            byte[] buf = new byte[1024];
            while ((len = in.read(buf)) != -1) {
                System.out.println(getdate() + "  客户端: （"
                        + socket.getInetAddress().getHostAddress() + "）说："
                        + new String(buf, 0, len, "UTF-8"));
            }
        } else {
            System.out.println(getdate() + "  连接服务端...");
            Socket socket = new Socket();
            socket.bind(new InetSocketAddress(port));
            socket.connect(new InetSocketAddress(ipConn, Integer.parseInt(portConn)));

            new sendMessThread2(socket).start();

            InputStream s = socket.getInputStream();
            byte[] buf = new byte[1024];
            int len = 0;
            while ((len = s.read(buf)) != -1) {
                System.out.println(getdate() + "  服务器说：  " + new String(buf, 0, len, "UTF-8"));
            }
        }
    }

    public static String getdate() {
        Date date = new Date();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        String result = format.format(date);
        return result;
    }

    static class sendMessThread1 extends Thread {
        Socket socket = null;

        public sendMessThread1(Socket socket) {
            this.socket = socket;
        }

        @Override
        public void run() {
            super.run();
            Scanner scanner = null;
            OutputStream out = null;
            try {
                if (socket != null) {
                    scanner = new Scanner(System.in);
                    out = socket.getOutputStream();
                    String in = "";
                    do {
                        in = scanner.next();
                        out.write(("" + in).getBytes("UTF-8"));
                        out.flush();// 清空缓存区的内容
                    } while (!in.equals("q"));
                    scanner.close();
                    try {
                        out.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    static class sendMessThread2 extends Thread {
        Socket socket = null;

        public sendMessThread2(Socket socket) {
            this.socket = socket;
        }

        @Override
        public void run() {
            super.run();
            //写操作
            Scanner scanner = null;
            OutputStream os = null;
            try {
                scanner = new Scanner(System.in);
                os = socket.getOutputStream();
                String in = "";
                do {
                    in = scanner.next();
                    os.write(("" + in).getBytes());
                    os.flush();
                } while (!in.equals("bye"));
            } catch (IOException e) {
                e.printStackTrace();
            }
            scanner.close();
            try {
                os.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 发送UDP请求
     *
     * @param str
     * @param server
     * @return
     */
    private static String sendMessage(String str, SocketAddress server, int port) {
        try {
            String message = "";
            DatagramSocket ds = new DatagramSocket(port);
            byte buff[] = str.getBytes();
            byte buffer[] = new byte[8 * 1024];
            DatagramPacket dp = new DatagramPacket(buff, 0, buff.length, server);

            ds.send(dp);//发送信息到服务器
            dp.setData(buffer, 0, 8 * 1024);
            if (!"testUDPdadong".equals(str)) {
                ds.receive(dp);
                message = new String(dp.getData(), 0, dp.getLength());
            }

            ds.close();
            return message;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

}
