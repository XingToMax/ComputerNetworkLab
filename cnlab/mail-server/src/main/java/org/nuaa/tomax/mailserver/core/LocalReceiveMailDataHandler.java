package org.nuaa.tomax.mailserver.core;

import org.springframework.stereotype.Component;

/**
 * @Name: LocalReceiveMailDataHandler
 * @Description: TODO
 * @Author: tomax
 * @Date: 2019-04-20 11:11
 * @Version: 1.0
 */
@Component("localReceive")
public class LocalReceiveMailDataHandler implements IDataHandler{
    @Override
    public boolean handle(MailBean mail) {
        return false;
    }
}
