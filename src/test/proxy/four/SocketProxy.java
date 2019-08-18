package test.proxy.four;

import java.net.Authenticator;
import java.net.PasswordAuthentication;
import java.net.ServerSocket;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * http 代理程序
 * 
 * @author lulaijun
 * 
 */
public class SocketProxy
{

    static final int listenPort = 8002;

    public static void main(String[] args) throws Exception
    {

        Properties prop = System.getProperties();
        // 设置http访问要使用的代理服务器的地址
        prop.setProperty("http.proxyHost", "183.45.78.31");
        // 设置http访问要使用的代理服务器的端口
        prop.setProperty("http.proxyPort", "8080");
        // 设置不需要通过代理服务器访问的主机，可以使用*通配符，多个地址用|分隔
        prop.setProperty("http.nonProxyHosts", "localhost|192.168.0.*");
        // 设置安全访问使用的代理服务器地址与端口
        // 它没有https.nonProxyHosts属性，它按照http.nonProxyHosts 中设置的规则访问
        prop.setProperty("https.proxyHost", "183.45.78.31");
        prop.setProperty("https.proxyPort", "443");
        // 使用ftp代理服务器的主机、端口以及不需要使用ftp代理服务器的主机
        prop.setProperty("ftp.proxyHost", "183.45.78.31");
        prop.setProperty("ftp.proxyPort", "21");
        prop.setProperty("ftp.nonProxyHosts", "localhost|192.168.0.*");
        // socks代理服务器的地址与端口
        prop.setProperty("socksProxyHost", "183.45.78.31");
        prop.setProperty("socksProxyPort", "1080");
        // 设置登陆到代理服务器的用户名和密码
        Authenticator.setDefault(new MyAuthenticator("userName", "Password"));

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
        ServerSocket serverSocket = new ServerSocket(listenPort);
        final ExecutorService tpe = Executors.newCachedThreadPool();
        System.out.println("Proxy Server Start At " + sdf.format(new Date()));
        System.out.println("listening port:" + listenPort + "……");
        System.out.println();
        System.out.println();

        while (true)
        {
            Socket socket = null;
            try
            {
                socket = serverSocket.accept();
                socket.setKeepAlive(true);
                // 加入任务列表，等待处理
                tpe.execute(new ProxyTask(socket));
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }
    }
    
    static class MyAuthenticator extends Authenticator
    {
        private String user = "";
        private String password = "";

        public MyAuthenticator(String user, String password)
        {
            this.user = user;
            this.password = password;
        }

        protected PasswordAuthentication getPasswordAuthentication()
        {
            return new PasswordAuthentication(user, password.toCharArray());
        }
    }
}

