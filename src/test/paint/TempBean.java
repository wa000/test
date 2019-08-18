package test.paint;

public class TempBean
{
    private String s ;
    private String s1 ;
    private String s2 ;
    private String s3 ;
    private String s4 ;
    private String s5 ;
    
    
    
    
    public String getS()
    {
        return s;
    }
    public void setS(String s)
    {
        this.s = s;
    }
    public String getS1()
    {
        return s1;
    }
    public void setS1(String s1)
    {
        this.s1 = s1;
    }
    public String getS2()
    {
        return s2;
    }
    public void setS2(String s2)
    {
        this.s2 = s2;
    }
    public String getS3()
    {
        return s3;
    }
    public void setS3(String s3)
    {
        this.s3 = s3;
    }
    public String getS4()
    {
        return s4;
    }
    public void setS4(String s4)
    {
        this.s4 = s4;
    }
    public String getS5()
    {
        return s5;
    }
    public void setS5(String s5)
    {
        this.s5 = s5;
    }
    @Override
    public String toString()
    {
        StringBuilder builder = new StringBuilder();
        builder.append("TempBean [s=");
        builder.append(s);
        builder.append(", s1=");
        builder.append(s1);
        builder.append(", s2=");
        builder.append(s2);
        builder.append(", s3=");
        builder.append(s3);
        builder.append(", s4=");
        builder.append(s4);
        builder.append(", s5=");
        builder.append(s5);
        builder.append("]");
        return builder.toString();
    }
}
