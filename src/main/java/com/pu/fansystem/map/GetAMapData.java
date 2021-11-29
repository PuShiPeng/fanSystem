package com.pu.fansystem.map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@Component("getAMapData")
public class GetAMapData {

    @Value("${define.aMap.busInfoUrl}")
//    private String busInfoUrl;
    private String busInfoUrl = "https://restapi.amap.com/v3/bus/linename";

    @Value("${define.aMap.key}")
//    private String key;
    private String key = "3bdfeb49b258130c716e1605d247bdd5";

    @Value("${define.aMap.output}")
//    private String output;
    private String output = "json";

    @Value("${define.aMap.appName}")
//    private String appName;
    private String appName = "https://lbs.amap.com/demo/javascript-api/example/bus-info/search-bus-route";

    @Value("${define.aMap.extensions}")
//    private String extensions;
    private String extensions = "all";

    @Value("${define.aMap.s}")
//    private String s;
    private String s = "rsv3";

    /**
     * 获取公交信息
     */
    public ResponseEntity<String> getBusInfo(){
        System.out.println(busInfoUrl);

        HttpHeaders headers = new HttpHeaders();
        headers.add("key",key);
        headers.add("output",output);
        headers.add("appName",appName);
        headers.add("extensions",extensions);
        headers.add("s",s);
        HttpEntity<String> entity = new HttpEntity<>(headers);
        RestTemplate rest = new RestTemplate();
        ResponseEntity<String> res = rest.exchange(busInfoUrl, HttpMethod.GET, entity, String.class);
        return res;
    }
}
