package com.data.shuzi.datacollector.util;


import com.alibaba.fastjson.JSONArray;
import com.data.shuzi.datacollector.common.CodeEnum;
import org.asynchttpclient.AsyncHttpClient;
import org.asynchttpclient.Param;
import org.asynchttpclient.Response;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.stream.Collectors;

import static org.asynchttpclient.Dsl.*;


/**
 * @author zizuo.zdh
 * @ClassName RemoteHttpClient
 * @Description TODO
 * @Date 2018/6/25 15:44
 * @Version 1.0
 **/
public class RemoteHttpClient {
    /**
    *@author zizuo.zdh
    *@Description TODO
    *@Date 2018/6/25 17:14
    *@Param [url, data]
    *@return java.lang.String
    *
    **/
    public static String post(String url,Map<String,Object> params) {
        if(params==null||params.size()==0){
          return null;
        }
        String parmaStr=JSONArray.toJSONString(params);
        try {
            try (AsyncHttpClient client = asyncHttpClient()) {
                Future<Response> f = client.preparePost(url).setBody(parmaStr.getBytes()).execute();
                Response resp = f.get(60, TimeUnit.SECONDS);
                if(resp.getStatusCode()==CodeEnum.OK.getCode()){
                    return resp.getResponseBody();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (TimeoutException e) {
            e.printStackTrace();
        }
        return null;
    }
    /**
    *@author zizuo.zdh
    *@Description TODO
    *@Date 2018/6/25 17:14
    *@Param [url, params]
    *@return java.lang.String
    *
    **/
    public static  String get(String url,List<Param> params){
        try (AsyncHttpClient client = asyncHttpClient()) {
            Future<Response> f = client.prepareGet(url).addQueryParams(params).execute();
            Response resp = f.get(60, TimeUnit.SECONDS);
            if(resp.getStatusCode()== CodeEnum.OK.getCode()){
                return resp.getResponseBody();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (TimeoutException e) {
            e.printStackTrace();
        }
        return null;
    }

}
