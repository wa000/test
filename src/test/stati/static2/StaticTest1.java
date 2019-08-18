package test.stati.static2;
import static test.stati.static1.StaticTest.print;

public class StaticTest1
{
    public static void main(String[] args)
    {
        print();
        
        int i = 10;
        
        switch (i)
        {
            case 1:  System.out.println(1);
            case 10:  System.out.println(10);
            case 11:  System.out.println(11);
            case 12:  
                {
                    System.out.println(12);
                    break;
                }
            
            
            case 13:  System.out.println(13);
            
            default:
                break;
        }
        
    }
}
