package org.nuaa.tomax.mailclient.core;

import lombok.Data;

/**
 * @Author: ToMax
 * @Description:
 * @Date: Created in 2019/4/12 11:28
 */
@Data
public class SendMessage {
    private String msg;
    private int code;

    public SendMessage(String msg, int code) {
        this.msg = msg;
        this.code = code;
    }
}
