package org.nuaa.tomax.mailserver.core;

/**
 * @Name: Instruction
 * @Description: TODO
 * @Author: tomax
 * @Date: 2019-04-13 22:00
 * @Version: 1.0
 */
public class Instruction {
    /**
     * helo 指令
     */
    public final static String HELLO = "HELO";

    /**
     * auth 指令
     */
    public final static String AUTH = "auth login";

    /**
     * mail from 指令
     */
    public final static String MAIL_FROM = "MAIL FROM";

    /**
     * mail to 指令
     */
    public final static String MAIL_TO = "RCPT TO";

    /**
     * data 指令
     */
    public final static String DATA = "DATA";

    /**
     * quit 指令
     */
    public final static String QUIT = "QUIT";

}
