package com.dictionaryapp.controller;

import com.dictionaryapp.config.UserSession;
import com.dictionaryapp.model.dto.AddWordDto;
import com.dictionaryapp.model.entity.User;
import com.dictionaryapp.model.entity.Word;
import com.dictionaryapp.service.UserService;
import com.dictionaryapp.service.WordService;
import org.springframework.ui.Model;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.Set;

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

    @ModelAttribute("wordModel")
    public AddWordDto dictionary() {
        return new AddWordDto();
    }

    @GetMapping("/")
    public String notLogged(){
        if (userSession.userLoggedIn()){
            return "redirect:/home"; // Use for index page redirect to home
        }
        return "index";
    }

    @GetMapping("/home")
    public String logged(Model model){
        if (!userSession.userLoggedIn()){
            return "redirect:/"; // Use for home page redirect to index to log in.
        }
        User user = userService.findUserById(userSession.getId()).orElse(null);
        model.addAttribute("currentUserInfo", user);

        List<Word> allGermanWords = wordService.germanWords();
        List<Word> allFrenchWords = wordService.frenchWordsCount();
        List<Word> allSpanishWords = wordService.spanishWords();
        List<Word> allItalianWords = wordService.italianWords();

        model.addAttribute("allGermanWords", allGermanWords);
        model.addAttribute("allFrenchWords", allFrenchWords);
        model.addAttribute("allSpanishWords", allSpanishWords);
        model.addAttribute("allItalianWords", allItalianWords);

        long allCount = wordService.getAllCount();
        model.addAttribute("allCount", allCount);

        return "home";
    }
    @GetMapping("/home/delete-word/{id}")
    public String deleteSingleWord(@PathVariable Long id) {
        if (!userSession.userLoggedIn()) {
            return "redirect:/";
        }
        this.wordService.deleteSingleWord(id);
        return "redirect:/home";
    }

    @GetMapping("/home/remove-all")
    String removeAll(Model model){
        userService.removeAllWords();
        return "redirect:/home";
    }
}
