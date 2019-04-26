package org.nuaa.tomax.mailclient.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

/**
 * @Name: AdminController
 * @Description: TODO
 * @Author: tomax
 * @Date: 2019-04-20 17:09
 * @Version: 1.0
 */
@Controller
@RequestMapping
public class ViewController {
    @GetMapping("")
    public String login() {
        return "login";
    }

    @GetMapping("/signup")
    public String signup() {
        return "signup";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "login";
    }

    /**
     * admin page
     */
    private static final List<String> ADMIN_PAGE_LIST = new ArrayList<String>(){{
        add("index");
        add("welcome");
        add("send");
        add("mail-send");
        add("mail-receive");
        add("mail-read");
    }};

    @GetMapping("/page/{page}")
    public String welcome(@PathVariable(name = "page") String page, HttpSession session) {
        return page;
//        if ("logout".equals(page)) {
//            session.invalidate();
//            return "login";
//        }
//        return ADMIN_PAGE_LIST.contains(page) ?
//                session.getAttribute("User") != null ? page : "login" :
//                "error/404";
    }
}
