package model;

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
        this.name = name;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public String getRol() {
        return rol;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }
}
