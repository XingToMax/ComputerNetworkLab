package org.nuaa.tomax.mailserver.core;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * @Name: DataHandlerFactory
 * @Description: TODO
 * @Author: tomax
 * @Date: 2019-04-20 10:52
 * @Version: 1.0
 */
@Component
public class DataHandlerFactory {
    @Autowired
    private Map<String, IDataHandler> handlerMap;

    public boolean handle(String mode, MailBean mail) {
        if (!handlerMap.containsKey(mode)) {
            throw new IllegalArgumentException("not have handler of mode : " + mode);
        } else {
            return handlerMap.get(mode).handle(mail);
        }
    }
}
