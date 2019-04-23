package org.nuaa.tomax.mailclient.controller;

import org.nuaa.tomax.mailclient.entity.Response;
import org.nuaa.tomax.mailclient.service.IUserService;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

/**
 * @Name: UserController
 * @Description: TODO
 * @Author: tomax
 * @Date: 2019-04-20 16:42
 * @Version: 1.0
 */
@RequestMapping("/user")
@RestController
@CrossOrigin
public class UserController {
    private final IUserService userService;

    public UserController(IUserService userService) {
        this.userService = userService;
    }

    @PostMapping("/login")
    public Response login(String username, String password, HttpSession session) {
        Response response = userService.signin(username, password);

        if (response.getCode() == Response.SUCCESS_CODE) {
            session.setAttribute("username", username);
        }

        return response;
    }

    @PostMapping("/signup")
    public Response signUp(String username, String password) {
        return userService.signup(username, password);
    }
}
