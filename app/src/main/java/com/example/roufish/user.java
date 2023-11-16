package com.example.roufish;

public class user {

    private String  username, password,email,alamat, noHP;

    public user(String username, String password, String email, String alamat, String noHP) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.alamat = alamat;
        this.noHP = noHP;
    }
    public  user(){

    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
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

    public String getAlamat() {
        return alamat;
    }

    public void setAlamat(String alamat) {
        this.alamat = alamat;
    }

    public String getNoHP() {
        return noHP;
    }

    public void setNoHP(String noHP) {
        this.noHP = noHP;
    }
}
