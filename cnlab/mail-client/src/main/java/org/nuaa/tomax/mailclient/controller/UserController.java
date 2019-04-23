package org.nuaa.tomax.mailclient.controller;

import org.nuaa.tomax.mailclient.entity.Response;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping("/login")
    public Response login(String username, String password) {
        return null;
    }

    @PostMapping("/signup")
    public Response signUp(String username, String password) {
        return null;
    }
}
