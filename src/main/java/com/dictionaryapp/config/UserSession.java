package com.dictionaryapp.config;

import com.dictionaryapp.model.entity.User;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.annotation.SessionScope;

@Configuration
@SessionScope
public class UserSession {
    private  long id;
    private String name;

    public void login(User user) {
        this.id = user.getId();
        this.name = user.getUserName();
    }

//    public LoggedUser() {
//    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
