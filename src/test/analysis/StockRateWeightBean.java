package test.analysis;

/**
 * 股票保存变化率的bean
 * 
 * @author wa000
 *
 */
public class StockRateWeightBean
{
    /**
     * 名称
     */
    private String name;
    
    /**
     * 变化比率
     */
    private double rate;
    
    /**
     * 权重
     */
    private double weight;

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public double getRate()
    {
        return rate;
    }

    public void setRate(double rate)
    {
        this.rate = rate;
    }

    public double getWeight()
    {
        return weight;
    }

    public void setWeight(double weight)
    {
        this.weight = weight;
    }

    @Override
    public String toString()
    {
        StringBuilder builder = new StringBuilder();
        builder.append("StockRateWeightBean [name=");
        builder.append(name);
        builder.append(", rate=");
        builder.append(rate);
        builder.append(", weight=");
        builder.append(weight);
        builder.append("]");
        return builder.toString();
    }
}
