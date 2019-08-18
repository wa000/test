package test.analysis;

/**
 * 同花顺股票数据的数据bean
 * 
 * @author wa000
 *
 */
public class StockAnalysisBean
{
    /**
     * 时间
     */
    private String dateTime;
    
    /**
     * 开盘
     */
    private String beginPrice;
    
    /**
     * 最高
     */
    private String bigPrice;
    
    /**
     * 最低
     */
    private String smallPrice;
    
    /**
     * 收盘
     */
    private String endPrice;
    
    /**
     * 涨幅
     */
    private String rate;
    
    /**
     * 振幅
     */
    private String zfRate;
    
    /**
     * 总手
     */
    private String count;
    
    /**
     * 金额
     */
    private String sum;
    
    /**
     * 换手率
     */
    private String hsRate;
    
    /**
     * 成交次数
     */
    private String cjCount;

    public String getDateTime()
    {
        return dateTime;
    }

    public void setDateTime(String dateTime)
    {
        this.dateTime = dateTime;
    }

    public String getBeginPrice()
    {
        return beginPrice;
    }

    public void setBeginPrice(String beginPrice)
    {
        this.beginPrice = beginPrice;
    }

    public String getBigPrice()
    {
        return bigPrice;
    }

    public void setBigPrice(String bigPrice)
    {
        this.bigPrice = bigPrice;
    }

    public String getSmallPrice()
    {
        return smallPrice;
    }

    public void setSmallPrice(String smallPrice)
    {
        this.smallPrice = smallPrice;
    }

    public String getEndPrice()
    {
        return endPrice;
    }

    public void setEndPrice(String endPrice)
    {
        this.endPrice = endPrice;
    }

    public String getRate()
    {
        return rate;
    }

    public void setRate(String rate)
    {
        this.rate = rate;
    }

    public String getZfRate()
    {
        return zfRate;
    }

    public void setZfRate(String zfRate)
    {
        this.zfRate = zfRate;
    }

    public String getCount()
    {
        return count;
    }

    public void setCount(String count)
    {
        this.count = count;
    }

    public String getSum()
    {
        return sum;
    }

    public void setSum(String sum)
    {
        this.sum = sum;
    }

    public String getHsRate()
    {
        return hsRate;
    }

    public void setHsRate(String hsRate)
    {
        this.hsRate = hsRate;
    }

    public String getCjCount()
    {
        return cjCount;
    }

    public void setCjCount(String cjCount)
    {
        this.cjCount = cjCount;
    }

    @Override
    public String toString()
    {
        StringBuilder builder = new StringBuilder();
        builder.append("StockAnalysisBean [dateTime=");
        builder.append(dateTime);
        builder.append(", beginPrice=");
        builder.append(beginPrice);
        builder.append(", bigPrice=");
        builder.append(bigPrice);
        builder.append(", smallPrice=");
        builder.append(smallPrice);
        builder.append(", endPrice=");
        builder.append(endPrice);
        builder.append(", rate=");
        builder.append(rate);
        builder.append(", zfRate=");
        builder.append(zfRate);
        builder.append(", count=");
        builder.append(count);
        builder.append(", sum=");
        builder.append(sum);
        builder.append(", hsRate=");
        builder.append(hsRate);
        builder.append(", cjCount=");
        builder.append(cjCount);
        builder.append("]");
        return builder.toString();
    }
}
