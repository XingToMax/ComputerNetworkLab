package org.nuaa.tomax.mailclient.core;

import lombok.Data;

/**
 * @Name: MailBean
 * @Description: TODO
 * @Author: tomax
 * @Date: 2019-04-18 23:08
 * @Version: 1.0
 */
@Data
public class MailBean {
    private final String DEFAULT_CHARSET = "UTF-8";

    private String from;
    private String to;
    private String user;
    private String password;
    private String content;
    private String subject;
    private String contentType;
    private String charset = DEFAULT_CHARSET;
    private boolean isHtml;
    private boolean isUrgent;

    public MailBean() {}
    public MailBean(String from, String to, String content, String subject, String contentType, boolean isHtml, boolean isUrgent) {
        this.from = from;
        this.to = to;
        this.content = content;
        this.subject = subject;
        this.contentType = contentType;
        this.isHtml = isHtml;
        this.isUrgent = isUrgent;
    }

    public MailBean updateAuthInfo(String user, String password) {
        this.user = user;
        this.password = password;
        return this;
    }
}
