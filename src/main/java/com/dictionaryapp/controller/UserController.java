package com.dictionaryapp.controller;

import com.dictionaryapp.model.dto.UserRegisterDto;
import com.dictionaryapp.service.UserService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.naming.Binding;

@Controller
public class UserController {
    private UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @ModelAttribute("registerData")
    public UserRegisterDto createEmptyDto(){
        return  new UserRegisterDto();
    }

    @GetMapping("/register")
    public String viewRegister() {
        /*UserRegisterDto userRegisterDto = new UserRegisterDto();
        userRegisterDto.setUsername("predefined");

        ModelAndView mnv = new ModelAndView();
        mnv.addObject("registerData",userRegisterDto); //th key = "registerData'*/

        return  "register";//mnv;
    }
    @PostMapping("/register")
    public String doRegister(@Valid UserRegisterDto data,
                             BindingResult bindingResult,
                             RedirectAttributes redirectAttributes){
        //Data get register input info(user,email,password,confirm
        //Validate data, return data, Register user
        if (bindingResult.hasErrors() || !userService.register(data)){
            redirectAttributes.addFlashAttribute("registerData", data);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.registerData",bindingResult);
            return "redirect:/register";
        }

        /*boolean success = userService.register(data);  // same as (bindingResult.hasErrors()|| userService.register(data))
        if (!success){
            redirectAttributes.addFlashAttribute("registerData", data);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.registerData",bindingResult);
            return "redirect:/register";
        }*/
        return "redirect:/login";
    }
}
