package org.ScubMembership.Registration;

import org.ScubMembership.Login.LoginFunctions.LoginMenu;
import org.ScubMembership.Login.LoginPage;
import org.ScubMembership.Users.NormalUser.NormalUser;
import org.ScubMembership.Utils.EmailValidator;

import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;



public class RegistrationProcess {
        private List<NormalUser> registeredUsers;
        private LoginPage loginPage;
        private Scanner scanner;

        public RegistrationProcess(LoginPage loginPage) {
            this.loginPage = loginPage;
            this.registeredUsers = new LinkedList<>();
            this.scanner = loginPage.getScanner();
        }

        public void startRegistration() {
            System.out.println("=== User Registration ===");

            String email;
            while (true){
                System.out.println("Enter email: ");
                email = scanner.nextLine().trim();

                if(!EmailValidator.isValidEmail(email)){
                    System.out.println("Your E-Mail address is invalid.");
                    continue;
                }

                boolean emailAddressAlreadyUsed = false;
                for (NormalUser user : loginPage.getLoginInfo().values()) {
                    if (user.getEmail().equalsIgnoreCase(email)) {
                        System.out.println("An account with this email already exists.");
                        emailAddressAlreadyUsed = true;
                        break;
                    }
                }

                if (!emailAddressAlreadyUsed) break;
            }

            System.out.print("Enter username: ");
            String username = scanner.nextLine().trim();

            while (loginPage.getLoginInfo().containsKey(username)) {
                System.out.println("Username already exists. Please choose a different one.");
                System.out.print("Enter username: ");
                username = scanner.nextLine().trim();
            }

            System.out.print("Enter password: ");
            String password = scanner.nextLine();

            //Qua raga ho messo l'ID che aumenta in base al numero di utenti registrati.
            int ID = loginPage.getLoginInfo().size() + 1;

            System.out.print("Enter your preferred sport: ");
            String preferredSport = scanner.nextLine();

            NormalUser newUser = new NormalUser(email, username, password, ID, preferredSport);
            registeredUsers.add(newUser);

            loginPage.getLoginInfo().put(username, newUser);
            loginPage.setCurrentUser(newUser);
            loginPage.setLoggedIn(true);

            System.out.println("User registered successfully!\n");

            LoginMenu menu = new LoginMenu(loginPage);
            menu.showLoginMenu();
        }

        public List<NormalUser> getRegisteredUsers() {
            return registeredUsers;
        }
    }


