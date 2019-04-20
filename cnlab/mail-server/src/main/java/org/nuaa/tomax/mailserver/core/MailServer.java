package org.nuaa.tomax.mailserver.core;

import lombok.extern.java.Log;
import org.nuaa.tomax.mailserver.utils.MailSendUtil;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @Name: MailServer
 * @Description: TODO
 * @Author: tomax
 * @Date: 2019-04-15 20:22
 * @Version: 1.0
 */
@Log
@Component(value = "mailServer")
public class MailServer {
    private SmtpServerContext context = null;
    private ServerSocket server = null;
    private ExecutorService executor = null;
    private boolean run = false;

    public MailServer(SmtpServerContext context) {
        this.context = context;
        executor = new ThreadPoolExecutor(4, 4, 0L,
                TimeUnit.MILLISECONDS, new LinkedBlockingQueue<Runnable>(), new ThreadFactory() {
            private AtomicInteger id = new AtomicInteger(1);
            @Override
            public Thread newThread(Runnable r) {
                return new Thread(r, "socket-handler-thread-" + id.getAndIncrement());
            }
        });
    }

    public void start() {
        try {
            server = new ServerSocket(context.getPort());
            log.info("server start");
            run = true;
            while (run) {
                Socket socket = server.accept();
                log.info("a new socket connect from: " + MailSendUtil.extractIpFromSocket(socket));
                executor.execute(new SocketHandler(socket, context));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void close() {
        if (run) {
            run = false;
            try {
                executor.shutdown();
                server.close();
                log.info("server destroy");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
