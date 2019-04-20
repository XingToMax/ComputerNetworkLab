package org.nuaa.tomax.mailserver.core;

import org.springframework.stereotype.Component;

/**
 * @Name: LocalSendMailDataHandler
 * @Description: TODO
 * @Author: tomax
 * @Date: 2019-04-20 11:09
 * @Version: 1.0
 */
@Component(value = "localSend")
public class LocalSendMailDataHandler implements IDataHandler {
    @Override
    public boolean handle(MailBean mail) {
        return false;
    }
}
