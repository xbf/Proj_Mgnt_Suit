package com.sse.g4.proj.git;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import lombok.Getter;
import lombok.Setter;
import org.apache.http.HttpEntity;
import org.apache.http.ParseException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.util.List;

@Getter
@Setter
public class GitNotes {
    private String projectId;
    private String issueId;
    private List<Note> notes;
    private static String LIST_NOTES_URL;

//    @Getter
//    @Setter
//    class Note {
//        private String id;
//        private String body;
//        private String state;
//
//        class Author {
//            private String id;
//            private String name;
//            private String username;
//        }
//    }

    public void load(String privateToken, String currDate, String projectId, String issueId, String url) {
        CloseableHttpClient httpClient = HttpClientBuilder.create().build();

//        http://dev.tc.com/gitlab/api/v4/projects/73/issues/14/notes?private_token=s-EFyPDB3YMZcf31jNB2
        // 创建Get请求
        HttpGet httpGet = new HttpGet("http://dev.tc.com/gitlab/api/v4/projects/" + projectId + "/issues/" + issueId + "/notes?private_token=" + privateToken);

        // 响应模型
        CloseableHttpResponse response = null;
        try {
            // 由客户端执行(发送)Get请求
            response = httpClient.execute(httpGet);
            // 从响应模型中获取响应实体
            HttpEntity responseEntity = response.getEntity();
            System.out.println("响应状态为:" + response.getStatusLine());
            if (responseEntity != null) {

                System.out.println("响应内容长度为:" + responseEntity.getContentLength());
                System.out.println("响应内容为:" + EntityUtils.toString(responseEntity));
                JSONArray noteLst = (JSONArray)JSON.parse(EntityUtils.toString(responseEntity));
                noteLst.forEach(a -> {
                    JSONObject note = (JSONObject) a;
                    String id = (String) note.get("id");
                    String body = (String) note.get("body");
                    JSONObject authorjson = (JSONObject) note.get("author");


                });

            }
        } catch (ParseException | IOException e) {
            System.out.println("连接失败"+e.getMessage());
        } finally {
            try {
                // 释放资源
                if (httpClient != null) {
                    httpClient.close();
                }
                if (response != null) {
                    response.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }




}
