package test;

public class ThreadTest
{
    public static void main(String[] args)
    {
        for(int i = 0; i < 10; i++)
        {
            
            new Thread(new Runnable() {
                public void run()
                {
                    try
                    {
                        Thread.sleep(100);
                    }
                    catch (InterruptedException e)
                    {
                        e.printStackTrace();
                    }
                    System.out.println("123");
//                    while(true)
//                    {
//                    }
                }
            }).start();
            
            
        }
        System.out.println("main  finish"); 
    }
}






