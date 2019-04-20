package org.nuaa.tomax.mailserver.core;

import lombok.Data;

/**
 * @Name: MailBean
 * @Description: TODO
 * @Author: tomax
 * @Date: 2019-04-16 19:02
 * @Version: 1.0
 */
@Data
public class MailBean {
    private String user;
    private String password;
    private String from;
    private String to;
    private String data;
    private String hostName;
    private int mode = -1;
}
