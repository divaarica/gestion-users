package entities;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class User {
    private int id;
    private String email, pwd, pwdhashed;
    private int idRole;


//    public User(int id, String email, String pwd, String pwdhashed) {
//        this.id = id;
//        this.email = email;
//        this.pwd = pwd;
//        this.pwdhashed = pwdhashed;
//    }
    public User(){

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public String getPwdhashed() {
        return pwdhashed;
    }

    public void setPwdhashed(String pwdhashed) {
        this.pwdhashed = pwdhashed;
    }

    public int getIdRole() {
        return idRole;
    }

    public void setIdRole(int role) {
        this.idRole = role;
    }
}
