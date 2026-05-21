package org.example.deckforge.Domain;

import org.example.deckforge.Domain.Enums.Role;

public class User {
    private int id;
    private String username;
    private String email;
    private String password;
    private String passwordHash;
    private Role role;

    public User(){}

    public User(String username, String email, String passwordHash, Role role){
        this.username = username;
        this.email = email;
        this.passwordHash = passwordHash;
        this.role = role;
    }

    public void setId(int id){
        this.id = id;
    }
    public int getId(){
        return id;
    }

    public void setUsername(String username){
        this.username = username;
    }
    public String getUsername(){
        return username;
    }

    public void setEmail(String email){
        this.email = email;
    }
    public String getEmail(){
        return email;
    }

    public void setPasswordHash(String passwordHash){
        this.passwordHash = passwordHash;
    }
    public String getPasswordHash(){
        return passwordHash;
    }

    public String getPassword(){
        return password;
    }
    public void setPassword(String password){
        this.password = password;
    }

    public void setRole(Role role){
        this.role = role;
    }
    public Role getRole(){
        return role;
    }

}
