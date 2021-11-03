package cn.ponyzhang.server.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class RedPacketUtil {

    /*
    二倍均值法产生红包金额，
    金额单位：分
     */
    public static List<Integer> divideRedPacket(Integer totalAmount,Integer totalPeopleNum){
        List<Integer> redPacketList = new ArrayList<>();
        if(totalAmount>0 && totalPeopleNum>0){
            Random random = new Random();
            int restAmount = totalAmount;
            int restPeopleNum = totalPeopleNum;
            while(restPeopleNum-1>0){
                int amount = random.nextInt(restAmount/restPeopleNum*2) + 1;
                restAmount = restAmount - amount;
                restPeopleNum = restPeopleNum - 1;
                redPacketList.add(amount);
            }
            redPacketList.add(restAmount);
        }
        return redPacketList;
    }
}
