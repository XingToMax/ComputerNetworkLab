package org.nuaa.tomax.mailclient.core;

/**
 * @Name: SMTPMain
 * @Description: TODO
 * @Author: tomax
 * @Date: 2019-04-14 22:41
 * @Version: 1.0
 */
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import org.nuaa.tomax.mailclient.utils.Base64Wrapper;

/*
 * 计算机网络原理用
 * Github:  http://github.com/icodeu
 */

public class SMTPMain {

    public static void main(String[] args) {

        // 定义一些下面会用到的邮箱密码等数据 但正常编程没人会把账号密码写的这么明显的
        String sender = "cnsmtp01@163.com";
        String receiver = "cnsmtp02@163.com";
        String password = "computer";
        String user = Base64Wrapper.encode(sender.substring(0,
                sender.indexOf("@")));
        String pass = Base64Wrapper.encode(password);

        try {
            Socket socket = new Socket("smtp.163.com", 25);
            InputStream inputStream = socket.getInputStream();
            OutputStream outputStream = socket.getOutputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(
                    inputStream));
            PrintWriter writter = new PrintWriter(outputStream, true); // 我他妈去
            // 这个true太关键了!

            System.out.println(reader.readLine());

            // HELO
            writter.println("HELO huan");
            System.out.println(reader.readLine());

            // AUTH LOGIN
            writter.println("auth login");
            System.out.println(reader.readLine());
            writter.println(user);
            System.out.println(reader.readLine());
            writter.println(pass);
            System.out.println(reader.readLine());
            // Above Authentication successful

            // Set "mail from" and "rcpt to"
            writter.println("mail from:<" + sender + ">");
            System.out.println(reader.readLine());
            writter.println("rcpt to:<" + receiver + ">");
            System.out.println(reader.readLine());

            // Set "data"
            writter.println("data");
            System.out.println(reader.readLine());

            writter.println("subject:女神，是我");
            writter.println("from:" + sender);
            writter.println("to:" + receiver);
            writter.println("Content-Type: text/plain;charset=\"gb2312\"");
            writter.println();
            writter.println("女神，晚上可以共进晚餐吗？");
            writter.println(".");
            writter.println("");
            System.out.println(reader.readLine());

            // 发送完毕了，我要和服务器说拜拜了
            writter.println("rset");
            System.out.println(reader.readLine());

            writter.println("quit");
            System.out.println(reader.readLine());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}