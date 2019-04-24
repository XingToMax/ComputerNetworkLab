package org.nuaa.tomax.mailserver.core;

import lombok.Data;
import org.nuaa.tomax.mailserver.entity.MailEntity;

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

    public MailEntity toMail() {
        MailEntity entity = new MailEntity();
        entity.setData(data);
        entity.setFromMail(from);
        entity.setToMail(to);
        entity.setMailType(mode);
        entity.setProp("");
        return entity;
    }
}
