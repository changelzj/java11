package com.example;

import org.junit.Test;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;

/**
 * var 局部变量类型推断
 * 不能不赋值，无法类型推断会报错
 * 成员变量不能用
 * 不是关键字，class文件中会被真实类型替换
 */
public class Test1 {
    public static void main(String[] args) {
        var list = new ArrayList<>();
        list.add("aaa");
        System.out.println(list);
    }

    /**
     * 
     */
    @Test
    public void test() {
        Consumer<String> consumer = (@Deprecated var e) -> {System.out.println(e);};
        consumer.accept("hello world");
    }
    
    @Test
    public void testString() {
        String str = " \n \n hello world \t \r";
        System.out.println(str);
        System.out.println(str.isBlank());
        
        // 去除各种语言（英 韩 汉 日）首尾空白  （ trim只能去除unicode<=32的 ，不能去除非英文字符的空白）
        System.out.println(str.strip());
        //  去除尾部
        System.out.println(str.stripTrailing());
        // 去除头部
        System.out.println(str.stripLeading());

        // 复制字符串
        System.out.println(str.repeat(2));

        // 行数统计 \n
        System.out.println(str.lines().count());
    }
    
    @Test
    public void testOptional() {
        String o = Optional.ofNullable((String)null).orElseGet(() -> "dssd");

        String s = Optional.ofNullable((String)null).orElseThrow(() -> new RuntimeException("err"));
    }
    
    @Test
    public void file() throws Exception {
        var input = new FileInputStream("1.txt");
        var output = new FileOutputStream("2.txt");
        try(input; output) {
            input.transferTo(output);
        }
        
        
    }

    /**
     * java9中  httpclient正式可用
     */
    @Test
    public void testHttp() throws Exception {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest req = HttpRequest.newBuilder(URI.create("http://www.qq.com")).GET().build();
        HttpResponse<String> response = client.send(req, HttpResponse.BodyHandlers.ofString());
        System.out.println(response.statusCode());
        System.out.println(response.body());
        System.out.println(response.version().name());
    }

    @Test
    public void testHttpAsync() throws Exception {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest req = HttpRequest.newBuilder(URI.create("http://www.qq.com")).GET().build();
        CompletableFuture<HttpResponse<String>> future = client.sendAsync(req, HttpResponse.BodyHandlers.ofString());

        HttpResponse<String> response = future.get();
        System.out.println(response.statusCode());
        System.out.println(response.body());
        System.out.println(response.version().name());

        TimeUnit.SECONDS.sleep(5000);
    }
}



