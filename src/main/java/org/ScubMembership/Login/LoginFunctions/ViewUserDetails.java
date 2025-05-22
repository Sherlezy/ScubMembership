package org.ScubMembership.Login.LoginFunctions;

import org.ScubMembership.Login.LoginPage;

public class ViewUserDetails {

    private LoginPage loginPage;

    public ViewUserDetails(LoginPage loginPage){
        this.loginPage = loginPage;
    }

    public void seeUserDetails(){
        System.out.println("\n=== User Details ===");
        System.out.println("E-Mail address: " + loginPage.getCurrentUser().getEmail());
        System.out.println("ID: " + loginPage.getCurrentUser().getID());
        System.out.println("Username: " + loginPage.getCurrentUser().getUsername());
        System.out.println("Password: " + "*".repeat(loginPage.getCurrentUser().getPassword().length()));
        System.out.println(("Favorite sport: " + loginPage.getCurrentUser().getPreferredSport()));
    }


}
