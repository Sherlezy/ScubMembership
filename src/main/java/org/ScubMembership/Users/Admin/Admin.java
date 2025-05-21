package org.ScubMembership.Users.Admin;

import java.io.Serializable;

public class Admin implements Serializable {
    private static final long serialVersionUID = 1L;
    public String email;
    public String username;
    public String password;
    public int ID;

    public Admin (String email, String username, String password, int ID){
        this.email = email;
        this.username = username;
        this.password = password;
        this.ID = ID;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }
}
