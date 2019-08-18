package test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

public class Test1 {


    public static void imgTest() {
        ImageUtils.scale(null, null, 0, true);
    }


    public static void main(String[] args) {
        // interfaceTest();
        int i = 1, j = 10;
        do {
            if (i++ > --j) continue;
        } while (i < 5);
        System.out.println(i);
        System.out.println(j);
    }

    public static void interfaceTest() {
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        System.out.println(sdf.format(date));

        for (int i = 0; i < 10000; i++) {
            new Thread(new Runnable() {
                public void run() {
                    String s = sendGet("http://192.168.1.46:8080/ExamSystem/api/manager/tmlr/select/list");

                    File file = new File("D:/TEMP/" + UUID.randomUUID().toString() + ".txt");

                    try {
                        FileOutputStream fos = new FileOutputStream(file);

                        fos.write(s.getBytes());

                        fos.close();

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }).start();
        }
    }

    public static String sendGet(String url) {
        String result = "";
        BufferedReader in = null;
        try {
            String urlNameString = url;
            URL realUrl = new URL(urlNameString);
            // 打开和URL之间的连接

            URLConnection connection = realUrl.openConnection();
            connection.setConnectTimeout(8000);
            // 设置通用的请求属性
            connection.setRequestProperty("accept", "*/*");
            connection.setRequestProperty("connection", "Keep-Alive");
            connection.setRequestProperty("user-agent",
                    "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            // 建立实际的连接
            connection.connect();
            // 获取所有响应头字段

            // 定义 BufferedReader输入流来读取URL的响应
            in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }
        } catch (Exception e) {
            System.out.println("发送GET请求出现异常！" + e.getMessage());
            e.printStackTrace();
        }
        // 使用finally块来关闭输入流
        finally {
            try {
                if (in != null) {
                    in.close();
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }

        }
        return result;
    }
}
