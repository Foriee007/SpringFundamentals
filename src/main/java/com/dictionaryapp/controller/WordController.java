package com.dictionaryapp.controller;

import com.dictionaryapp.config.UserSession;
import com.dictionaryapp.model.dto.AddWordDto;
import com.dictionaryapp.service.WordService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.awt.*;

@Controller
public class WordController {
    private final UserSession userSession;
    private final WordService wordService;

    public WordController(UserSession userSession, WordService wordService) {
        this.userSession = userSession;
        this.wordService= wordService;
    }

    @GetMapping("/word/add")
    //@Transactional
    public String viewAddWord(Model model){
        if (!userSession.userLoggedIn()){
            return "redirect:/login"; // Use for home page redirect to index to log in.
        }
//        List<Language> languages = this.wordService.getAllLanguages();
//        System.out.println(languages);
//        model.addAttribute("languages", languages);
        return  "/word-add";
    }

    @ModelAttribute("addWordDTO")
    public AddWordDto newWordDto(){
        return  new AddWordDto();
    }

    @PostMapping("word/add")
    public String doAddWord(@Valid AddWordDto addWordDto,
                            BindingResult bindingResult,
                            RedirectAttributes redirectAttributes){
        //Adding validation
        if (bindingResult.hasErrors()){
            redirectAttributes.addFlashAttribute("AddWordData", addWordDto);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.AddWordData",bindingResult);
            return "redirect:/word/add";
        }
        wordService.add(addWordDto,userSession.getId());
        return  "redirect:/home";
    }
}
