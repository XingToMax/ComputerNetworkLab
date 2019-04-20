package org.nuaa.tomax.mailclient.core;

import lombok.Data;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

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
    private List<File> attachments;
    private String data;

    public MailBean() {
        attachments = new ArrayList<>();
    }
    public MailBean(String from, String to, String content, String subject, String contentType, boolean isHtml, boolean isUrgent) {
        this();
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

    public MailBean addAttachment(File file) {
        this.attachments.add(file);
        return this;
    }

    public MailBean addData(String data) {
        this.data = data;
        return this;
    }

    public boolean ifNeedBoundary() {
        return attachments.size() > 0;
    }
}
