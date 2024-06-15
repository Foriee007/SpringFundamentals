package com.dictionaryapp.controller;

import com.dictionaryapp.config.UserSession;
import com.dictionaryapp.model.dto.UserLoginDto;
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
    private final UserService userService;
    private final UserSession userSession;

    public UserController(UserService userService, UserSession userSession) {
        this.userService = userService;
        this.userSession = userSession;
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

    @ModelAttribute("loginData")
    public UserLoginDto createLoginDto(){
        return  new UserLoginDto();
    }

    @GetMapping("/login")
    public String viewLogin(){
        return "login";
    }
    @PostMapping("/login")
    public String doLogin(@Valid UserLoginDto data,
                          BindingResult bindingResult,
                          RedirectAttributes redirectAttributes){
        if (bindingResult.hasErrors()){
            redirectAttributes.addFlashAttribute("lodinData", data);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.loginData",bindingResult);
            return "redirect:/login";
        }

        boolean success = userService.login(data);
        if (!success) {
            redirectAttributes.addFlashAttribute("loginData", data);
            redirectAttributes.addFlashAttribute("userOrPasswordNotMatch", true);
            return "redirect:/login";
        }

        return "redirect:/home";
    }
}
