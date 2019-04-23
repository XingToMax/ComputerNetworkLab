package org.nuaa.tomax.mailclient.service;

import org.nuaa.tomax.mailclient.entity.Response;

/**
 * @Name: IUserService
 * @Description: TODO
 * @Author: tomax
 * @Date: 2019-04-23 21:01
 * @Version: 1.0
 */
public interface IUserService {

    Response signin(String username, String password);

    Response signup(String username, String pasword);
}
