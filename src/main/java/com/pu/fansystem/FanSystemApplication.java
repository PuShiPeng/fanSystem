package com.pu.fansystem;

import com.pu.fansystem.map.GetAMapData;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

@SpringBootApplication
public class FanSystemApplication {

    public static void main(String[] args) {
//        SpringApplication.run(FanSystemApplication.class, args);
//        System.out.println("启动成功"+romanToInt("IV"));
        System.out.println("".length());
    }

    public static int romanToInt(String s) {
        Map<String,Integer> num = new HashMap<>();
        num.put("I",1);
        num.put("V",5);
        num.put("X",10);
        num.put("L",50);
        num.put("C",100);
        num.put("D",500);
        num.put("M",1000);
        char[] numStr = s.toCharArray();
        int target = 0;
        for(int i = 0; i < numStr.length; i++){
            if(i > 0){
                if("V".equalsIgnoreCase(String.valueOf(numStr[i])) && "I".equalsIgnoreCase(String.valueOf(numStr[i-1])))
                    target += num.get(String.valueOf(numStr[i])) - num.get(String.valueOf(numStr[i-1]));
            }else
                target += num.get(String.valueOf(numStr[i]));

        }
        return target;
    }

    public int strStr(String haystack, String needle) {
        if(null == haystack || null == needle)return -1;
        int size = haystack.length();
        int subSize = needle.length();
        if(size == 0 )return -1;
        if(subSize == 0)return 0;
        int prev = 0;
        int len = 0;
        for(int i =0; i < size; i++){
            if(haystack.charAt(i) == needle.charAt(prev)){
                if(prev == 0)len = i;
                prev++;
                if(prev == subSize)return len;
            }else{
                prev = 0;
            }
        }
        return -1;
    }

}
