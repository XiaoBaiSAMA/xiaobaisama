package com.xiaobaisama.pwd;

import java.math.BigDecimal;
import java.math.BigInteger;

/**
 * Created by xiaobai on 2018/8/23.
 */
public class PasswordUtil {
    private String tpl;
    private int max;
    private BigInteger start;
    private BigInteger now;
    private BigInteger[] possibilities;
    private BigInteger count;
    private String[] tplArr;
    private int[] indexs;

    public PasswordUtil(String tpl,int max,BigInteger start) {
        this.tpl = tpl;
        this.max = max;
        this.start = start;
        this.now = start;

        final BigInteger possibility = new BigInteger(String.valueOf(tpl.length()));
        tplArr = tpl.split("");
        possibilities = new BigInteger[max];
        count = new BigInteger("0");
        for(int i=0;i<max;i++){
            BigInteger p = possibility.pow(i+1);
            count = count.add(p);
            possibilities[i]=p;
        }
        indexs = new int[max];
        for(int i=0;i<indexs.length;i++){
            indexs[i] = -1;
        }
        BigInteger temp = new BigInteger("0");
        for(int i=0;i<indexs.length;i++){
            if(start.compareTo(temp) >= 0){
                indexs[i] = start.subtract(temp).divide(possibility.pow(i)).mod(possibility).intValue();
                temp = temp.add(possibilities[i]);
            }else{
                break;
            }
        }
    }

    public String getPwd(){
        if(now.compareTo(count) >= 0) return null;
        StringBuilder sb = new StringBuilder();
        for(int i=0;i<indexs.length;i++){
            int index = indexs[i];
            if(index != -1){
                sb.insert(0,tplArr[index]);
            }else{
                break;
            }
        }
        now = now.add(new BigInteger("1"));
        indexs[0]++;
        for(int i=0;i<indexs.length-1;i++){
            if(indexs[i] >= tpl.length()){
                indexs[i+1]++;
                indexs[i]=0;
            }else{
                break;
            }
        }
        return sb.toString();
    }

    public BigInteger getNow() {
        return now;
    }

    public BigInteger getCount() {
        return count;
    }

    public double getProgress () {
        return new BigDecimal(now).divide(new BigDecimal(count),4,BigDecimal.ROUND_HALF_UP).doubleValue();
    }

    public static void main(String[] args) {
        PasswordUtil pu = new PasswordUtil("abcdefghijklmnopqrstuvwxyz",6,new BigInteger("0"));
        for(int i=0;i<pu.getCount().intValue();i++){
            String result = pu.getPwd();
            System.out.println(pu.getNow()+":"+result+"-"+pu.getProgress());
            if(result.equals("admin")){
                return;
            }
        }

    }
}
