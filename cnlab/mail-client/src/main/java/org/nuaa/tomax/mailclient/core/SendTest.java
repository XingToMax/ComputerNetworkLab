package org.nuaa.tomax.mailclient.core;

import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.directory.InitialDirContext;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Hashtable;

/**
 * @Author: ToMax
 * @Description:
 * @Date: Created in 2019/4/9 20:35
 */
public class SendTest {
    public static void main(String[] args) throws NamingException {
        String from = "0@tomax.xin";
        String to = "1121584497@qq.com";

        String content = "hello";

        Hashtable<String,String> hashtable=new Hashtable<String,String>();
        hashtable.put("java.naming.factory.initial","com.sun.jndi.dns.DnsContextFactory");
        InitialDirContext dirContext = new InitialDirContext(hashtable);

        NamingEnumeration records=dirContext.getAttributes("qq.com", new String[]{"MX"}).getAll();
        while (records.hasMore()) {
            String url = records.next().toString();
            url = url.substring(url.indexOf(": ")+2);
            String[] addressList = url.split(",");
            Arrays.stream(addressList).
                    map(String::trim)
                    .sorted(Comparator.reverseOrder())
                    .map(s -> s.split("\\s+")[1])
                    .forEach(host -> {
                        try {
                            Socket socket = new Socket(host, 25);
                            InputStream in = socket.getInputStream();
                            OutputStream out = socket.getOutputStream();
                            byte[] buffer = new byte[1024];
                            buffer = new byte[1024];
                            in.read(buffer);
                            System.out.println(new String(buffer));
                            out.write("HELO tomax.xin".getBytes());
                            out.flush();
                            out.write('\r');
                            out.write('\n');
                            out.flush();
                            buffer = new byte[1024];
                            in.read(buffer);
                            System.out.println(new String(buffer));

                            out.write("MAIL FROM: <0@tomax.xin>".getBytes());
                            out.flush();
                            out.write('\r');
                            out.write('\n');
                            out.flush();
                            buffer = new byte[1024];
                            in.read(buffer);
                            System.out.println(new String(buffer));

                            out.write("RCPT TO: <1121584497@qq.com>".getBytes());
                            out.flush();
                            out.write('\r');
                            out.write('\n');
                            out.flush();
                            buffer = new byte[1024];
                            in.read(buffer);
                            System.out.println(new String(buffer));

                            out.write("DATA".getBytes());
                            out.flush();
                            out.write('\r');
                            out.write('\n');
                            out.flush();
                            buffer = new byte[1024];
                            in.read(buffer);
                            System.out.println(new String(buffer));

                            out.write("From: =?UTF-8?B?dG9tYXg=?= <0@tomax.xin>".getBytes());
                            out.flush();
                            out.write('\r');
                            out.write('\n');
                            out.flush();

                            out.write("To: =?UTF-8?B?U29s?= <1121584497@qq.com>".getBytes());
                            out.flush();
                            out.write('\r');
                            out.write('\n');
                            out.flush();

                            out.write("Subject: =?UTF-8?B?U1RNUOmCruS7tua1i+ivlQ==?=".getBytes());
                            out.flush();
                            out.write('\r');
                            out.write('\n');
                            out.flush();

                            out.write("Date: Tue, 9 Apr 2019 20:10:14 +0800 (CST)".getBytes());
                            out.flush();
                            out.write('\r');
                            out.write('\n');
                            out.flush();

                            out.write("MIME-Version: 1.0".getBytes());
                            out.flush();
                            out.write('\r');
                            out.write('\n');
                            out.flush();

                            out.write("Content-Type: text/plain; charset=UTF-8".getBytes());
                            out.flush();
                            out.write('\r');
                            out.write('\n');
                            out.flush();
                            out.write("hello world.".getBytes());
                            out.flush();
                            out.write('\r');
                            out.write('\n');
                            out.flush();

                            buffer = new byte[1024];
                            in.read(buffer);
                            System.out.println(new String(buffer));
                            PrintWriter writer = new PrintWriter(out, true);
                            out.write("rset".getBytes());
                            out.flush();
                            out.write('\r');
                            out.write('\n');
                            out.flush();
                            buffer = new byte[1024];
                            in.read(buffer);
                            System.out.println(new String(buffer));

                            out.write("quit".getBytes());
                            out.flush();
                            out.write('\r');
                            out.write('\n');
                            out.flush();
                            buffer = new byte[1024];
                            in.read(buffer);
                            System.out.println(new String(buffer));

                            System.out.println("hello");
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    });
        }
    }
}
