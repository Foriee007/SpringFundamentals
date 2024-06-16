package com.dictionaryapp.model.entity;


import jakarta.persistence.*;

import java.util.Set;

@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(unique = true, nullable = false)
    private String userName;

    @Column(nullable = false)
    private String password;

    @Column(unique = true, nullable = false)
    private String email;

    /*@OneToMany(mappedBy = "addedBy")
    private Set<Word> addedWords;*/
    /* public User() {
        addedWords = new HashSet<>();
    }*/

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Word> addedWords;

    public void setAddedWords(Set<Word> words) {
        this.addedWords = words;
    }
    public User() {
    }

    public Set<Word> getAddedWords() {
        return addedWords;
    }

    /*public void setAddedWords(Set<Word> addedWords) {
        this.addedWords = addedWords;
    }*/

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }


}




