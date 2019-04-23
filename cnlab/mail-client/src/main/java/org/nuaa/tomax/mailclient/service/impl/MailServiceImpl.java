package org.nuaa.tomax.mailclient.service.impl;

import org.nuaa.tomax.mailclient.entity.MailDataEntity;
import org.nuaa.tomax.mailclient.entity.Response;
import org.nuaa.tomax.mailclient.service.IMailService;
import org.springframework.stereotype.Service;

/**
 * @Name: MailServiceImpl
 * @Description: TODO
 * @Author: tomax
 * @Date: 2019-04-23 21:44
 * @Version: 1.0
 */
@Service
public class MailServiceImpl implements IMailService {

    @Override
    public Response sendMail(MailDataEntity mail) {
        return null;
    }

    @Override
    public Response getRecieveMailList(String username) {
        return null;
    }

    @Override
    public Response getSendMailList(String username) {
        return null;
    }

    @Override
    public Response removeMail(long id) {
        return null;
    }
}
