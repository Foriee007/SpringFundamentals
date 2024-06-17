package com.dictionaryapp.config;

import com.dictionaryapp.model.entity.User;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.SessionScope;

@Component
@SessionScope
public class UserSession {
    private  long id;
    private String username;

    public void login(User user) {
        this.id = user.getId(); // Need only ID, if it has greetings, add username
        this.username = user.getUsername();
    }

    public long getId() {
        return id;
    }


    public boolean userLoggedIn(){
        return id > 0;
    }

    public void logout() {
        id = 0;
        username = "";
    }

    public String username(){
        return username; // goes to nav-bar welcome user
    }
}
