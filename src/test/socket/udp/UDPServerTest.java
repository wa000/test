package test.socket.udp;

import java.io.IOException;
import java.net.*;
import java.text.SimpleDateFormat;
import java.util.*;

public class UDPServerTest
{
    public static Map<String,String> map=new HashMap<String,String>();
    public static void main(String args[]) throws Exception
    {

        int clientPort=0;
        String ip="";
        final DatagramSocket ds = new DatagramSocket(20880);
        String message="";
        String str="";
        byte by[]=new byte[2000];
        DatagramPacket dp=new DatagramPacket(by,0,by.length);
        SocketAddress socketAddress = null;
        InetSocketAddress inetSocketAddress = null;
        while(true)
        {
            System.out.println("wait...");
            ds.receive(dp);
            System.out.println("success");
            message=new String(dp.getData(),0,dp.getLength());
            System.out.println("message:"+message);
            socketAddress=dp.getSocketAddress();
            inetSocketAddress = (InetSocketAddress) socketAddress;
            clientPort=inetSocketAddress.getPort();
            ip=inetSocketAddress.getAddress().getHostAddress();
            if(message.startsWith("getIp"))
            {
                map.put(String.valueOf(clientPort),ip);
                str=clientPort+","+ip;
                byte buf[]=str.getBytes();
                dp.setData(buf,0,buf.length);
                ds.send(dp);
                System.out.println(clientPort+","+ip);
            }
            if(message.startsWith("aa"))
            {
                Set<String> set=map.keySet();
                Iterator<String> iter=set.iterator();
                while(iter.hasNext())
                {
                    String key=iter.next();
                    String value=map.get(key);
                    System.out.println(key+"clientPort"+clientPort);
                    str="udp"+","+key+","+value;
                    byte buf[]=str.getBytes();
                    dp.setData(buf,0,buf.length);
                    ds.send(dp);
                    System.out.println("send");
//                    if(clientPort==Integer.valueOf(key))
//                    {
//                    }
                }
            }
            if(message.startsWith("sendMessage"))
            {
                final String port = message.split(",")[1];
                final String targetIp = message.split(",")[2];
                str=message.split(",")[3];
                byte buf[] = str.getBytes();
                SocketAddress sa = new InetSocketAddress(targetIp, Integer.valueOf(port));
                DatagramPacket target = new DatagramPacket(buf,0, buf.length, sa);
                ds.send(target);

                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        for(int i = 0; i < 1000; i++)
                        {
                            byte buf[] = ("wa00" + i + ": " + new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(new Date())).getBytes();
                            SocketAddress sa = new InetSocketAddress(targetIp, Integer.valueOf(port));
                            DatagramPacket target = new DatagramPacket(buf,0, buf.length, sa);
                            try
                            {
                                ds.send(target);
                            }
                            catch (IOException e)
                            {
                                e.printStackTrace();
                            }

                            if(i == 0)
                            {
                                try {
                                    Thread.sleep(10000);
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }
                            }
                            else
                            {
                                try {
                                    Thread.sleep(5 * 60 * 1000);
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                    }
                }).start();

                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        for(int i = 0; i < 10000000; i++)
                        {
                            byte buf[] = "603257390".getBytes();
                            SocketAddress sa = new InetSocketAddress(targetIp, Integer.valueOf(port));
                            DatagramPacket target = new DatagramPacket(buf,0, buf.length, sa);
                            try
                            {
                                ds.send(target);
                            }
                            catch (IOException e)
                            {
                                e.printStackTrace();
                            }

                            try {
                                Thread.sleep(1000);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }).start();
            }

            dp.setData(by,0,by.length);
        }
    }
}
