package org.ScubMembership.Users.NormalUser;

public class NormalUser {
    public String email;
    public String username;
    public String password;
    public int ID;
    public String preferredSport;

    public NormalUser (String email, String username, String password, int ID, String preferredSport){
        this.email = email;
        this.username = username;
        this.password = password;
        this.ID = ID;
        this.preferredSport = preferredSport;
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

    public String getPreferredSport(){
        return preferredSport;
    }

    public void setPreferredSport(String preferredSport){
        this.preferredSport = preferredSport;
    }
}
