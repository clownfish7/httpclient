package com.yzy.pic;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.impl.client.HttpClientBuilder;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.*;


@SpringBootApplication
public class PicApplication {

    public static void main(String[] args) throws IOException {
        SpringApplication.run(PicApplication.class, args);

        HttpClient client = HttpClientBuilder.create().build();
        HttpPost post = new HttpPost("http://localhost:8080/fileUploadController");
        File file = new File("C:\\Users\\yzy\\Desktop\\background.jpg");
        InputStream is = new FileInputStream(file);
        String message = "This is a multipart post";
        MultipartEntityBuilder builder = MultipartEntityBuilder.create();
        builder.setMode(HttpMultipartMode.BROWSER_COMPATIBLE);
        builder.addBinaryBody
                ("file", is, ContentType.DEFAULT_BINARY, "background.jpg");
        builder.addTextBody("deviceId", "C0000", ContentType.TEXT_PLAIN);
        builder.addTextBody("alarmDate", "19970806", ContentType.TEXT_PLAIN);

        HttpEntity entity = builder.build();
        post.setEntity(entity);
        HttpResponse response = client.execute(post);
        //获取响应码
        int status = response.getStatusLine().getStatusCode();
        HttpEntity httpEntity = response.getEntity();
        //将H中返回实体转化为输入流
        is = httpEntity.getContent();
        //读取输入流，即返回文本内容
        StringBuffer sb = new StringBuffer();
        BufferedReader br = new BufferedReader(new InputStreamReader(is));
        String line = "";
        while ((line = br.readLine()) != null) {
            sb.append(line);
        }
        System.out.println("status = " + status);
        System.out.println("sb = " + sb);
        System.out.println("{\"msg\":\"ok\"}".equals(sb.toString()));
    }
}
