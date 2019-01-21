package producer_consumer_v1;

/**
 * 简介：
 *
 * @author 87627
 * @create 2019/1/21
 * @since 1.0.0
 */
public class PCData {
    private final int intData;
    public PCData(int d){
        intData = d;
    }
    public PCData(String d){
        intData = Integer.valueOf(d);
    }
    public int getData(){
        return intData;
    }
    @Override
    public String toString(){
        return "data:"+intData;
    }
}
