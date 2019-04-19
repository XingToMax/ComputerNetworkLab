package org.nuaa.tomax.mailclient;

import org.junit.Test;
import org.nuaa.tomax.mailclient.core.MailBean;
import org.nuaa.tomax.mailclient.core.Sender;
import org.nuaa.tomax.mailclient.utils.Base64Wrapper;
import org.nuaa.tomax.mailclient.utils.StringUtil;

import java.io.File;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

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

    static void checkStartsIgnoreCaseWith() {
        System.out.println(StringUtil.startsIgnoreCaseWith("abc", "A"));
        System.out.println(StringUtil.startsIgnoreCaseWith("abc", ""));
        System.out.println(StringUtil.startsIgnoreCaseWith("abc", "Abc"));
        System.out.println(StringUtil.startsIgnoreCaseWith("abc", "Abcd"));

        System.out.println(StringUtil.endsIgnoreCaseWith("asdgsdbcd", "BCD"));
        System.out.println(StringUtil.endsIgnoreCaseWith("asdgsdbcd", ""));
        System.out.println(StringUtil.endsIgnoreCaseWith("asdgsdbcd", "sd"));
        System.out.println(StringUtil.endsIgnoreCaseWith("asdgsdbcd", null));


    }

    public static void main(String[] args) throws InterruptedException {
//        testQueryDomain();
//
//        testExtractHostFromEmailAddress();
//        checkStartsIgnoreCaseWith();
        Sender sender = new Sender("localhost");
        String encodeUser = Base64Wrapper.encode("tomax");
        String encodePwd = Base64Wrapper.encode("tomax1111");

        MailBean mail = new MailBean("tomax@localhost", "1121584497@qq.com",
                Base64Wrapper.encode("hello"), "hello", "base64",
                false, false)
                .updateAuthInfo(encodeUser, encodePwd)
                .addAttachment(new File("/Users/tomax/study/error.txt"))
                .addAttachment(new File("/Users/tomax/work/blog/myblogs/README.md"));
        sender.send(mail);
    }

}
