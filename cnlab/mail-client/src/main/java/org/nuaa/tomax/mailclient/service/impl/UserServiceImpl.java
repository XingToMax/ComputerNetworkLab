package org.nuaa.tomax.mailclient.service.impl;

import lombok.extern.java.Log;
import org.nuaa.tomax.mailclient.entity.Response;
import org.nuaa.tomax.mailclient.entity.UserEntity;
import org.nuaa.tomax.mailclient.repository.IUserRepository;
import org.nuaa.tomax.mailclient.service.IUserService;
import org.nuaa.tomax.mailclient.utils.PasswordEncryptUtil;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;

/**
 * @Name: UserServiceImpl
 * @Description: TODO
 * @Author: tomax
 * @Date: 2019-04-23 21:02
 * @Version: 1.0
 */
@Log
@Service
public class UserServiceImpl implements IUserService {

    private final IUserRepository userRepository;

    public UserServiceImpl(IUserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public Response signin(String username, String password) {
        UserEntity user = userRepository.findUserEntityByUsername(username);
        try {
            if (user != null && PasswordEncryptUtil.validPassword(password, user.getPassword())) {
                return new Response(Response.SUCCESS_CODE, "登录成功");
            }
        } catch (NoSuchAlgorithmException e) {
            log.info(e.getMessage());
            return new Response(Response.SERVER_ERROR_CODE, "服务器故障");
        } catch (UnsupportedEncodingException e) {
            log.info(e.getMessage());
            return new Response(Response.SERVER_ERROR_CODE, "服务器故障");
        }
        return new Response(Response.NORMAL_EROOR_CODE, "用户名或密码错误");
    }

    @Override
    public Response signup(String username, String pasword) {
        // check username exists or not
        UserEntity check = userRepository.findUserEntityByUsername(username);
        if (check != null) {
            return new Response(Response.NORMAL_EROOR_CODE, "用户名已存在");
        }

        UserEntity user = new UserEntity();
        user.setUsername(username);
        try {
            user.setPassword(PasswordEncryptUtil.getEncryptedPwd(pasword));
            userRepository.save(user);
        } catch (NoSuchAlgorithmException e) {
            log.info(e.getMessage());
            return new Response(Response.SERVER_ERROR_CODE, "服务器故障");
        } catch (UnsupportedEncodingException e) {
            log.info(e.getMessage());
            return new Response(Response.SERVER_ERROR_CODE, "服务器故障");
        }
        return new Response(Response.SUCCESS_CODE, "邮箱申请成功");
    }
}
