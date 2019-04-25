package org.nuaa.tomax.mailclient.service;

import org.nuaa.tomax.mailclient.core.MailBean;
import org.nuaa.tomax.mailclient.entity.MailDataEntity;
import org.nuaa.tomax.mailclient.entity.Response;

/**
 * @Name: IMailService
 * @Description: TODO
 * @Author: tomax
 * @Date: 2019-04-23 21:40
 * @Version: 1.0
 */
public interface IMailService {
    Response sendMail(MailBean mail);

    Response getRecieveMailList(String username);

    Response getSendMailList(String username);

    Response removeMail(long id);
}
