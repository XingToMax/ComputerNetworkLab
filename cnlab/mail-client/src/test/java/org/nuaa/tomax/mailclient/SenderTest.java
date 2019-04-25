package org.nuaa.tomax.mailclient;

import org.junit.Test;
import org.nuaa.tomax.mailclient.core.MailBean;
import org.nuaa.tomax.mailclient.core.MailDataParser;
import org.nuaa.tomax.mailclient.core.Sender;
import org.nuaa.tomax.mailclient.entity.MailDataEntity;
import org.nuaa.tomax.mailclient.entity.MailEntity;
import org.nuaa.tomax.mailclient.utils.Base64Wrapper;
import org.nuaa.tomax.mailclient.utils.StringUtil;

import java.io.File;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.Timestamp;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

/**
 * @Author: ToMax
 * @Description:
 * @Date: Created in 2019/4/12 14:25
 */
public class SenderTest {

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

    static void testDataParser() {
        MailEntity src = new MailEntity();
        src.setFromMail("0@tomax.xin");
        src.setToMail("1121584497@qq.com");
        src.setTime(new Timestamp(System.currentTimeMillis()));
        src.setData("subject:hello\r\n" +
                "from:0@tomax.xin\r\n" +
                "to:1121584497@qq.com\r\n" +
                "MIME-Version: 1.0\r\n" +
                "Content-Type: multipart/mixed; BOUNDARY=\"Tomax=MGEwNTIzYjhiMzBkNjNmNWQ5M2EyYmYzMmE5YzE3YjE=Boundary\"\r\n" +
                "X-Priority: 3\r\n" +
                "--Tomax=MGEwNTIzYjhiMzBkNjNmNWQ5M2EyYmYzMmE5YzE3YjE=Boundary\r\n" +
                "Content-Type: text/plain; charset=\"UTF-8\"\r\n" +
                "Content-Transfer-Encoding: base64\r\n" +
                "\r\n" +
                "aGVsbG8=\r\n" +
                "--Tomax=MGEwNTIzYjhiMzBkNjNmNWQ5M2EyYmYzMmE5YzE3YjE=Boundary\r\n" +
                "Content-Type: application/octet-stream; name=\"UkVBRE1FLm1k\"\r\n" +
                "Content-Disposition: attachment; filename=\"UkVBRE1FLm1k\"\r\n" +
                "Content-Transfer-Encoding: base64\r\n" +
                "\r\n" +
                "I215YmxvZ3MK\r\n" +
                "--Tomax=MGEwNTIzYjhiMzBkNjNmNWQ5M2EyYmYzMmE5YzE3YjE=Boundary--\r\n" +
                ".\r\n");

        MailDataEntity mail = MailDataParser.parse(src);

        System.out.println(mail);
    }

    public static void main(String[] args) throws InterruptedException {
//        testQueryDomain();
//
//        testExtractHostFromEmailAddress();
//        checkStartsIgnoreCaseWith();
//        Sender sender = new Sender("tomax.xin");
//        String encodeUser = Base64Wrapper.encode("0");
//        String encodePwd = Base64Wrapper.encode("tomax1111");
//
//        MailBean mail = new MailBean("0@tomax.xin", "1121584497@qq.com",
//                "hello", "hello", "base64",
//                false, false)
//                .updateAuthInfo(encodeUser, encodePwd)
////                .addAttachment(new File("/Users/tomax/study/error.txt"))
//                .addAttachment(new File("/Users/tomax/work/blog/myblogs/README.md"));
//        sender.send(mail);

        testDataParser();
    }

}
