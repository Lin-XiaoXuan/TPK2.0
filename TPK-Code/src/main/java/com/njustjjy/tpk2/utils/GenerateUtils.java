package com.njustjjy.tpk2.utils;

import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Component
public class GenerateUtils {
     int[] Ints = new int[10];
     int[] indexs = new int[8];
     public static String[] chars = new String[] {"A", "B", "C", "D", "E", "F", "G", "H","I",
            "J", "K", "L", "M", "N", "O","P", "Q", "R", "S", "T", "U", "V",
            "W", "X", "Y", "Z","Y", "Z","X","W","V","U","S","R","Q","P","O","N","M","L","K",
             "J","I","H","G","F","E","D","C","B","A","Q","W","E","R","T","Y","U","I","O","P",
            "A","S","D","F","G","H","J","K","L","Z","X","C","V","B","N","M"};

     //构造方法
    public GenerateUtils() {}
    public GenerateUtils(int[] ints, int[] indexs) {
        Ints = ints;
        this.indexs = indexs;
    }

    //用户名自动生成
    @Deprecated
    public Integer getUserName() {
        //最终返回的值
        String reID = "";

        //1.随机填充static数组
        //获取一个时间戳并转为String类型尾数加"."方便截取
        //线程睡眠100ms
        try {
            Thread.currentThread().sleep(Long.parseLong("100"));
        } catch (Exception e) {
        }

        String timestr = String.valueOf(System.currentTimeMillis()).substring(9) + ".";

        //截取最后四位存入数组
        for (int i = 0; i <= timestr.length() - 1; i++) {
            if (i <= 3) {
                indexs[i] = Integer.valueOf(timestr.substring(i, i + 1));
            }
        }

        for (int a = 4; a <= indexs.length - 1; a++) {
            indexs[a] = (int) (Math.random() * 9);
        }

        //填充Ints数组内获取随机数
        for (int n = 0; n <= Ints.length - 1; n++) {
            Ints[n] = (int) (Math.random() * 9);
        }


        //根据indexs数组中索引取出Ints中数
        for (int b = 0; b < 8; b++) {
            //判断第一个取出数是否为0
            if (b == 0 && Ints[indexs[0]] == 0) {
                Ints[indexs[0]] = (int) (1 + Math.random() * (9 - 1) + 1);
            }
            reID = reID + String.valueOf(Ints[indexs[b]]);
        }

        return Integer.parseInt(reID);
    }


    /**
     * 分析身份证算法
     * <p>
     * 键值对参照表：
     * 1.old ： 年龄 -- int/Integer
     * 3.usersex ： 性别 -- int/Integer  : 1为男，0为女
     * 5.area : 地区 -- String  ： ！暂时默认，需要接入地区对照数据库！
     */
    @Deprecated
    public Map idCardAnalyse(String idCard) {
        //idCard : 340823 2003 10 13 56 1 7
        //inow：时间转化为Int形，old：年龄
        int area, year, month, day, sex, old, nowmonth;
        //snow：当前日期的string格式，sarea：身份证地点的String格式
        String snow, sarea;
        Map<String, Object> reMap = new HashMap<String, Object>();

        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        //获取地区
        area = Integer.parseInt(idCard.substring(0, 6));
        sarea = "安徽省铜陵市";
        //获取年份
        year = Integer.parseInt(idCard.substring(6, 10));
        //获取月份
        month = Integer.parseInt(idCard.substring(10, 12));
        //获取日数
        day = Integer.parseInt(idCard.substring(12, 14));
        //获取性别
        sex = Integer.parseInt(idCard.substring(16, 17));
        //获取今天
        snow = String.valueOf(sdf.format(new Date()));
        //存储当前年龄
        old = Integer.parseInt(snow.substring(0, 4)) - year;
        //存储当前月份(格式化为数字)
        nowmonth = Integer.parseInt(snow.substring(4, 6));


        //将所在地区存储入集合
        reMap.put("area", sarea);

        //判断性别并存入集合
        if (sex % 2 != 0 || sex == 1) {
            reMap.put("usersex", 1);
        } else {
            reMap.put("usersex", 0);
        }


        //判断是否成年和其现在岁数
        //判断算出来的岁数是否大于等于18岁，如果不是则未成年并写入当前岁数 19
        if (old >= 18) {
            //判断月份是否大于本月
            if (month == nowmonth) {
                //判断天数是否大于本天，若大于等于本天则成年，若没有则未成年
                if (day >= Integer.parseInt(snow.substring(6, 8))) {
                    //是否成年
                    reMap.put("old", old - 1);
                } else {
                    //如果日子没有大于今天则岁数-1且未成年
                    reMap.put("old", old);
                }
                //小于本月未成年
            } else if (month < nowmonth) {
                //是否成年
                reMap.put("old", old);
            } else {
                //如果大于当月了直接成年了
                reMap.put("old", old - 1);
            }
        } else {
//          岁数小于18则未成年字节存入岁数
            reMap.put("old", old);
        }

        //返回结果集合
        return reMap;
    }


    //生成n位uuid,静态方法
    public static String getUUID(Integer n){
        StringBuffer shortBuffer = new StringBuffer();
        String uuid = UUID.randomUUID().toString().replace("-", "");
        for (int i = 0; i < n; i++) {
            String str = uuid.substring(i * 4, i * 4 + 4);
            int x = Integer.parseInt(str, 16);
            shortBuffer.append(chars[x % 0x3E]);
        }
        return shortBuffer.toString();
    }

    //生成档案号
    public static synchronized String getRecordID(String idCard){

        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        //日期后面6位
        String mantissa = sdf.format(new Date()).substring(0,8);

        //睡眠随机秒数（0~62ms）确保时间尾数不同
        String time = String.valueOf((int) (0 + (Math.random()-0) * 62));
        try {
            Thread.currentThread().sleep(Long.parseLong(time));
        } catch (Exception e) {
        }

        return getUUID(4)    //四位uuid
                +mantissa .substring(2,8)  //日期后6位
                +String.valueOf(System.currentTimeMillis()).substring(9,13)//时间戳后四位
                + (int) (Math.random() * 9)//0-9随机数
                +idCard.substring(17,18); //身份证最后一位;
    }

    public synchronized static String getBusiness(){
        //获取日期
        String date = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"));

        //获取时间戳
        String lastCurrentTime = String.valueOf(System.currentTimeMillis()).substring(6);

        //返回日期和时间戳
        return date + lastCurrentTime;
    }

    /**
     * <p>获取年龄</p>
     *
     * */
    public static Integer getOld(String idCard){
        Integer year, month, day, age, nowmonth,nowday;


        //获取当前时间
        LocalDateTime nowTime = LocalDateTime.now();

        //获取年份
        year = Integer.parseInt(idCard.substring(6, 10));
        //获取月份
        month = Integer.parseInt(idCard.substring(10, 12));
        //获取日数
        day = Integer.parseInt(idCard.substring(12, 14));
        //存储当前年龄
        age = nowTime.getYear() - year;
        //存储当前月份(格式化为数字)
         nowmonth = nowTime.getMonthValue();
        //存储当前天
        nowday = nowTime.getDayOfMonth();

        //判断是否成年和其现在岁数
        if(nowmonth < month){
            age-=1;
        }

        if(nowday < day){
            age-=1;
        }

        return age;
    }


    //    public static String generateShortUuid(){
//        StringBuffer shortBuffer = new StringBuffer();
//        String uuid = UUID.randomUUID().toString().replace("-", "");
//        for (int i = 0; i < 8; i++) {
//            String str = uuid.substring(i * 4, i * 4 + 4);
//            int x = Integer.parseInt(str, 16);
//            shortBuffer.append(chars[x % 0x3E]);
//        }
//        return shortBuffer.toString();
//}




    //测试
    public static void main(String[] args){
//          GenerateUtils s = new GenerateUtils();
//          System.out.println(s.getBusiness(1));

        System.out.println("getBusiness() = " + getBusiness());

//
//        for(int i = 0;i<1000000;i++){
//            System.out.println(s.getRecordID("340823200310135617"));
//        }

//        System.out.println("自动生成id："+s.getUserName());
//        for(int a = 10;a<100000;a++){
//            System.out.println("自动生成id："+s.getUUID(4));
//        }

//
//        Map a = s.idCardAnalyse("340823199803285626");
//        System.out.println("年龄：" + a.get("old"));
//        System.out.println("性别：" +a.get("sex"));
//        System.out.println("地区：" +a.get("area"));
//
//
//      List<Integer> ls = new ArrayList<Integer>();
//        GenerateUtils g = new GenerateUtils();
//        Integer number,count=0;
//
//        for(int n = 0;n<=3;n++){
//            for(int a = 0;a<=10000000;a++){
//                //获取当前存的值
//                number = g.getUserName();
//                ls.add(a,number);
//                System.out.print("本次存入："+number+",");
//                //第一次存储不测试c
//                if(a == 0){
//                    continue;
//                }
//
//                //遍历集合判断是否有重复，判断重复率
//                for(int i = 0;i<a;i++){
//                    //判断当前你存储与前面值是否相同
//                    if(ls.get(i) == number){
//                        System.out.print("重复了！！!");
//                        //计数器计算重复次数
//                        count++;
//                    }
//
//                }
//                System.out.println("第"+a+"次完毕！！！");
//             }
//            System.out.println("重复了"+count+"次");
//            System.out.println("共测试10000000次,本次测试重复率为："+(count/10000000)*100+"%");
//            System.out.println("--------------------------");
//        }
//
//    }
}






}