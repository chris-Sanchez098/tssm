package model;

import java.util.Objects;

public class User {

    private int cc;
    private String name;
    private String user;
    private String pwd;
    private String rol;
    private Boolean status;

    public User(int cc, String name, String user, String pwd, String rol, Boolean status) {
        this.cc = cc;
        this.name = name;
        this.user = user;
        this.pwd = pwd;
        this.rol = rol;
        this.status = status;
    }

    public int getCc() {
        return cc;
    }

    public void setCc(int cc) {
        this.cc = cc;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        if(!(name == "")){
            this.name = name;
        }
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        if(!(user == "")) {
            this.user = user;
        }
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        if(!(pwd == "")) {
            this.pwd = pwd;
        }
    }

    public String getRol() {
        return rol;
    }

    public void setRol(String rol) {
        if(!(rol == "")) {
            this.rol = rol;
        }
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public boolean pwdEqualUser(){
       return Objects.equals(user, pwd);
    }

    public boolean checkPwd(){
        String path = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}$";
        return pwd.matches(path);
    }

}
