package test.analysis;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;
import java.util.Map.Entry;

/**
 * 股票分析的工具类  v2.0
 * 
 * @author wa000
 *
 */
public class StockAnalysisUtil
{
    public static void main(String[] args)
    {
        doAnalysis("C:\\Users\\Administrator\\Desktop\\Table.xls", "2018-08-21");
    }

    public static void saveData()
    {
        
    }
    
    /**
     * 分析股票数据
     */
    public static void doAnalysis(String path, String date)
    {
        //List<StockAnalysisBean> stockInfoFromFile = getStockHisInfoFromTHSFile("C:\\Users\\Administrator\\Desktop\\600500_his.xls");
        List<StockAnalysisBean> stockInfoFromFile = getStockHisInfoFromTHSFile(path);
        
        Map<String, Map<String, StockRateWeightBean>> cacheMap = new HashMap<String, Map<String,StockRateWeightBean>>();
        
        generateCacheMap(stockInfoFromFile, cacheMap);
        
        // TODO 使用权重的分析，待实现
        doAnalysisWithWeight();
        
        // 根据某一日的数据计算上涨概率
        String dateForAnalysis = date;
        
        // String[] markArr = {"beginPrice", "bigPrice", "smallPrice", "endPrice", "rate", "zfRate", "count", "sum", "hsRate", "cjCount"};
        
        String[] markArr = {"beginPrice", "bigPrice", "smallPrice", "endPrice", "rate", "zfRate", "count", "sum", "hsRate", "cjCount"};
        double[] likeRate = {  0.25,          0.25,       0.25,        0.25,     0.25,    0.25,    0.25,    0.25,   0.25,      0.25};
        
        Map<String, StockRateWeightBean> mapForAnalysis = cacheMap.get(dateForAnalysis);
        for(int i = 0; i < markArr.length; i++)
        {
            String oneStr = markArr[i];
            
            Set<Entry<String, Map<String, StockRateWeightBean>>> entrySet = cacheMap.entrySet();
            Iterator<Entry<String, Map<String, StockRateWeightBean>>> iterator = entrySet.iterator();
            for(;iterator.hasNext();)
            {
                Entry<String, Map<String, StockRateWeightBean>> next = iterator.next();
                Map<String, StockRateWeightBean> value = next.getValue();
                
                StockRateWeightBean stockRateWeightBean = value.get(oneStr);
                double rate = stockRateWeightBean.getRate();
                
                double rateForAnalysis = mapForAnalysis.get(oneStr).getRate();
                
                if(Math.abs(rate - rateForAnalysis) > likeRate[i])
                {
                    iterator.remove();
                }
            }
        }
        
        double count = 0;
        
        Map<String, Map<String, StockRateWeightBean>> cacheMapNew = new HashMap<String, Map<String,StockRateWeightBean>>();
        generateCacheMap(stockInfoFromFile, cacheMapNew);
        Set<Entry<String, Map<String, StockRateWeightBean>>> entrySet = cacheMap.entrySet();
        for(Entry<String, Map<String, StockRateWeightBean>> oneEntry : entrySet)
        {
            String key = oneEntry.getKey();
            
            for(int i = 0; i < stockInfoFromFile.size(); i++)
            {
                StockAnalysisBean stockAnalysisBean = stockInfoFromFile.get(i);
                if(key.equals(stockAnalysisBean.getDateTime()))
                {
                    if((i + 1) < stockInfoFromFile.size())
                    {
                        if(getDouble(stockInfoFromFile.get(i + 1).getRate()) > 0)
                        {
                            count = count + 1;
                        }
                    }
                    break;
                }
                
            }
        }
        System.out.println(cacheMap.size());
        System.out.println(((count / cacheMap.size()) * 100) + "%");
    }

    /**
     * 使用权重分析
     */
    private static void doAnalysisWithWeight()
    {
        // 初始化权重矩阵
        List<StockRateWeightBean> weightList = new ArrayList<>();
        addWeightList(weightList, "beginPrice", 1.0);
        addWeightList(weightList, "bigPrice", 1.0);
        addWeightList(weightList, "smallPrice", 1.0);
        addWeightList(weightList, "endPrice", 1.0);
        addWeightList(weightList, "rate", 1.0);
        addWeightList(weightList, "zfRate", 1.0);
        addWeightList(weightList, "count", 1.0);
        addWeightList(weightList, "sum", 1.0);
        addWeightList(weightList, "hsRate", 1.0);
        addWeightList(weightList, "cjCount", 1.0);
        // 计算并展示每种权重下对于上涨概率的预测效果
        // 计算权重时每次减少的数字
        double step = 0.3;
        // 当前的权重
        double curWeight = 1.0;
        for(curWeight = 1.0; curWeight >= 0; curWeight = curWeight - step)
        {
            for(int i = 0; i < weightList.size(); i++)
            {
                StockRateWeightBean curBean = weightList.get(i);
                curBean.setWeight(curWeight);
                
                // 根据当前的权重矩阵，计算并展示预测效果
                
            }
        }
    }

    private static void addWeightList(List<StockRateWeightBean> weightList, String name, double weight)
    {
        StockRateWeightBean oneBean = new StockRateWeightBean();
        oneBean.setName(name);
        oneBean.setWeight(weight);
        weightList.add(oneBean);
    }
    
    /**
     * 根据历史数据计算出变化率，并转换为带有权重的bean
     * 
     * @param stockInfoFromFile
     * @param cacheMap
     */
    private static void generateCacheMap(List<StockAnalysisBean> stockInfoFromFile,
            Map<String, Map<String, StockRateWeightBean>> cacheMap)
    {
        // 根据历史数据计算出变化率，并转换为带有权重的bean
        for(int i = 0; i < stockInfoFromFile.size(); i++)
        {
            // 第一个数据没办法计算变化率，故直接跳过
            if(i == 0)
            {
                continue;
            }
            
            StockAnalysisBean curentBean = stockInfoFromFile.get(i);
            
            String dateTime = curentBean.getDateTime();
            Map<String, StockRateWeightBean> map = cacheMap.get(dateTime);
            if(null == map)
            {
                map = new HashMap<String, StockRateWeightBean>();
                cacheMap.put(dateTime, map);
                
                // 计算变化率
                StockAnalysisBean beforeBean = stockInfoFromFile.get(i - 1);
                
                // 计算开盘价变化率
                setMap(map, "beginPrice", beforeBean.getBeginPrice(), curentBean.getBeginPrice());
                
                // 计算最高价变化率
                setMap(map, "bigPrice", beforeBean.getBigPrice(), curentBean.getBigPrice());
                
                // 计算最低价变化率
                setMap(map, "smallPrice", beforeBean.getSmallPrice(), curentBean.getSmallPrice());
                
                // 计算收盘价变化率
                setMap(map, "endPrice", beforeBean.getEndPrice(), curentBean.getEndPrice());
                
                // 计算涨幅变化率
                setMap(map, "rate", beforeBean.getRate(), curentBean.getRate());
                
                // 计算振幅变化率
                setMap(map, "zfRate", beforeBean.getZfRate(), curentBean.getZfRate());
                
                // 计算总手变化率
                setMap(map, "count", beforeBean.getCount(), curentBean.getCount());
                
                // 计算金额变化率
                setMap(map, "sum", beforeBean.getSum(), curentBean.getSum());
                
                // 计算换手变化率
                setMap(map, "hsRate", beforeBean.getHsRate(), curentBean.getHsRate());
                
                // 计算成交次数变化率
                setMap(map, "cjCount", beforeBean.getCjCount(), curentBean.getCjCount());
            }
        }
    }

    private static void setMap(Map<String, StockRateWeightBean> map, String name, String before, String after)
    {
        double beforeNum = getDouble(before);
        double afterNum = getDouble(after);
        StockRateWeightBean bean = createRateWeightBean(name, beforeNum, afterNum);
        map.put(name, bean);
    }
    
    private static double getDouble(String str)
    {
        if("--".equals(str))
        {
            return 0;
        }
        str = str.replaceAll("%", "");
        str = str.replaceAll(",", "");
        return Double.parseDouble(str);
    }
    
    /**
     * 创建权重比率bean
     * 
     * @return
     */
    private static StockRateWeightBean createRateWeightBean(String name, double beforeNum, double afterNum)
    {
        double rate = 0;
        if(0 != beforeNum)
        {
            rate = (afterNum - beforeNum) / beforeNum;
        }
        StockRateWeightBean bean = new StockRateWeightBean();
        bean.setName(name);
        bean.setRate(rate);
        bean.setWeight(1.00);
        return bean;
    }
    
    /**
     * 根据传入的文件名从同花顺导出文件中提取出股票历史数据
     * 
     * @param filePath
     * @return
     */
    public static List<StockAnalysisBean> getStockHisInfoFromTHSFile(String filePath)
    {
        BufferedReader br = null;

        List<StockAnalysisBean> resultList = new ArrayList<StockAnalysisBean>();

        try
        {
            br = new BufferedReader(new InputStreamReader(new FileInputStream(filePath), "GBK"));
            String line = "";
            while ((line = br.readLine()) != null)
            {
                try
                {
                    if(null == line || "".equals(line))
                    {
                        continue;
                    }
                    String[] oneBox = line.split("\t");
                    if (oneBox[0].equals("时间"))
                    {
                        continue;
                    }

                    StockAnalysisBean tempBean = new StockAnalysisBean();
                    tempBean.setDateTime(oneBox[0].split(",")[0]);
                    tempBean.setBeginPrice(oneBox[1]);
                    tempBean.setBigPrice(oneBox[2]);
                    tempBean.setSmallPrice(oneBox[3]);
                    tempBean.setEndPrice(oneBox[4]);
                    tempBean.setRate(oneBox[5]);
                    tempBean.setZfRate(oneBox[6]);
                    tempBean.setCount(oneBox[7]);
                    tempBean.setSum(oneBox[8]);
                    tempBean.setHsRate(oneBox[9]);
                    tempBean.setCjCount(oneBox[10]);

                    resultList.add(tempBean);
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        finally
        {
            try
            {
                br.close();
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
        }

        return resultList;
    }
}
