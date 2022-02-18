package model;

import java.util.Objects;

public class User {

    private String cc;
    private String name;
    private String user;
    private String pwd;
    private String rol;
    private Boolean status;

    /**
     * Constructor
     * @param cc cedula
     * @param name nombre
     * @param user usuario
     * @param pwd contraseña
     * @param rol cargo
     * @param status estado
     */
    public User(String cc, String name, String user, String pwd, String rol, Boolean status) {
        this.cc = cc;
        this.name = name;
        this.user = user;
        this.pwd = MD5.encrypt(pwd);
        this.rol = rol;
        this.status = status;
    }

    /**
     * @return int, get cc user
     */
    public String getCc() {
        return cc;
    }

    /**
     * @param cc, cc number of a user
     */
    public void setCc(String cc) {
        if(!(Objects.equals(cc, ""))){
            this.cc = cc;
        }
    }

    /**
     * @return str, get name user
     */
    public String getName() {
        return name;
    }

    /**
     * @param name, name of a user
     */
    public void setName(String name) {
        if(!(Objects.equals(name, ""))){
            this.name = name;
        }
    }

    /**
     * @return str, get user (login)
     */
    public String getUser() {
        return user;
    }

    /**
     * @param user, login
     */
    public void setUser(String user) {
        if(!(Objects.equals(user, ""))) {
            this.user = user;
        }
    }

    /**
     * @return str, get pwd
     */
    public String getPwd() {
        return pwd;
    }

    /**
     * @param pwd, password of a user
     */
    public void setPwd(String pwd) {
        if(!(Objects.equals(pwd, ""))) {
            this.pwd = MD5.encrypt(pwd);
        }
    }
    /**
     * @return str, get rol
     */
    public String getRol() {
        return rol;
    }

    /**
     * @param rol, rol of a user
     */
    public void setRol(String rol) {
        if(!rol.equals("")) {
            this.rol = rol;
        }
    }

    /**
     * @return boolean, get status
     */
    public Boolean getStatus() {
        return status;
    }

    /**
     * @param status, status of a user
     */
    public void setStatus(Boolean status) {
        this.status = status;
    }

    /**
     * @return boolean, password check
     */
    public static boolean checkPwd(String password){
        String path = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[.(!@#$%^&+=_-])(?=\\S+$).{8,}$";
        return password.matches(path);
    }

    public void setPwdNoEncrypt(String pwd){
        this.pwd = pwd;
    }
}
