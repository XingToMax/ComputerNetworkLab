package org.nuaa.tomax.mailclient;

import org.junit.Test;
import org.nuaa.tomax.mailclient.core.Sender;

/**
 * @Author: ToMax
 * @Description:
 * @Date: Created in 2019/4/12 14:25
 */
public class SenderTest {
    static void testQueryDomain() {
        Sender.queryDomain("tomax.xin").forEach(System.out::println);
        Sender.queryDomain("163.com").forEach(System.out::println);
        Sender.queryDomain("qq.com").forEach(System.out::println);
        Sender.queryDomain("localhost").forEach(System.out::println);

//        Sender.queryDomain("http:// 127.0.0.1").forEach(System.out::println);
    }

    static void testExtractHostFromEmailAddress() {
        System.out.println(Sender.extractHostFromEmailAddress("hello@localhost"));
        System.out.println(Sender.extractHostFromEmailAddress("hello@qq.com"));
    }

    public static void main(String[] args) {
        testQueryDomain();

        testExtractHostFromEmailAddress();
    }
}
