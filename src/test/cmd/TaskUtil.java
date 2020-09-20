package test.cmd;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;

public class TaskUtil {

    public static void main(String[] args) {

        int count = 0;

        while (true) {
            try {
                SimpleDateFormat logForamt = new SimpleDateFormat("yyyyMMdd HH:mm:ss ");
                String logDate = logForamt.format(new Date());
                SimpleDateFormat sdf = new SimpleDateFormat("HHmm");
                String nowDate = sdf.format(new Date());

                if ("0615".equals(nowDate) && count <= 0) {
                    System.out.println(logDate + "执行脚本");

                    String cmd = "cmd /c  cd /d D:\\server && PowerShell -Command \"xcopy D:\\server E:\\server\\ /e /i /y\"";
//                    String cmd = "cmd /c  cd /d D:\\tmp && PowerShell -Command \"xcopy D:\\tmp E:\\tmp\\ /e /i /y\"";
//                    String cmd = "cmd /c  cd /d C:\\Users\\Administrator\\Desktop\\frpc && PowerShell -Command \"xcopy C:\\Users\\Administrator\\Desktop\\frpc C:\\Users\\Administrator\\Desktop\\frpc1 /e /i /y\"";

                    File file = new File("D:\\");
                    CMDUtil.executeCMDCommand(cmd, file);

                    logDate = logForamt.format(new Date());
                    System.out.println(logDate + "执行结束");
                    count++;
                }

                logDate = logForamt.format(new Date());
                if ("2323".equals(nowDate)) {
                    System.out.println(logDate + "设置为0");
                    count = 0;
                }

//                System.out.println(logDate + "本次循环结束，nowDate：" + nowDate);
                Thread.sleep(10 * 1000);

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private static void callCmd(String locationCmd) {
        StringBuilder sb = new StringBuilder();
        try {
            Process child = Runtime.getRuntime().exec(locationCmd);
            InputStream in = child.getInputStream();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(in, "GBK"));
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                sb.append(line + "\n");
                System.out.println(line);
            }
            in.close();
            try {
                child.waitFor();
            } catch (InterruptedException e) {
                System.out.println(e);
            }
            System.out.println("sb:" + sb.length());
            System.out.println("callCmd execute finished");
        } catch (IOException e) {
            System.out.println(e);
        }
    }

    static class CMDUtil {
        public static void executeCMDCommand(String cmdCommand, File file) {
            StringBuilder sb = new StringBuilder();
            Process process = null;

            try {
                process = Runtime.getRuntime().exec(cmdCommand, null, file);

                new StreamGobbler(process.getErrorStream(), "E").start();
                new StreamGobbler(process.getInputStream(), "O").start();

                process.waitFor();
                process.destroy();
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }

    static class StreamGobbler extends Thread {
        InputStream is;
        String type;

        StreamGobbler(InputStream is, String type) {
            this.is = is;
            this.type = type;
        }

        public void run() {
            try {
                InputStreamReader isr = new InputStreamReader(is, "GBK");
                BufferedReader br = new BufferedReader(isr);
                String line = null;
                while ((line = br.readLine()) != null) {

                    System.out.println(type + ":" + line);
                }
                is.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
