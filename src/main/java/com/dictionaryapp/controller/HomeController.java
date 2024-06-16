package com.dictionaryapp.controller;

import com.dictionaryapp.config.UserSession;
import com.dictionaryapp.service.UserService;
import com.dictionaryapp.service.WordService;
import org.springframework.ui.Model;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    private final UserSession userSession;
    private final UserService userService;
    private final WordService wordService;

    public HomeController(UserSession userSession, UserService userService, WordService wordService) {
        this.userSession = userSession;
        this.userService = userService;
        this.wordService = wordService;
    }

    @GetMapping("/")
    public String notLogged(){
        if (userSession.userLoggedIn()){
            return "redirect:/home"; // Use for index page redirect to home
        }
        return "index";
    }

    @GetMapping("/home")
    public String logged(){
        if (!userSession.userLoggedIn()){
            return "redirect:/"; // Use for home page redirect to index to log in.
        }
        return "home";
    }
//    @GetMapping("/home/remove-all")
//    String removeAll(Model model){
//        userService.removeAllWords();
//        return "redirect:/home";
//    }
}
