package org.ScubMembership.Login.AdminFunctions;

import org.ScubMembership.SportActivity.CustomSport.CustomSport;
import org.ScubMembership.SportActivity.abs_sport;

import java.util.List;
import java.util.Scanner;

public class CreateNewSport {

    public static void execute(Scanner scanner, List<abs_sport> sports) {
        System.out.println("Enter sport name: ");
        String sportName = scanner.nextLine().trim();

        System.out.println("Enter equipment items (comma-separated): ");
        String[] equipment = scanner.nextLine().split(",");

        for (int i = 0; i < equipment.length; i++) {
            equipment[i] = equipment[i].trim();
        }

        System.out.println("Enter gym name: ");
        String gymName = scanner.nextLine().trim();

        System.out.println("Enter number of players: ");
        int numberOfPlayers;
        try {
            numberOfPlayers = Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("❌ Invalid number format.");
            return;
        }

        System.out.println("Enter the rules of the sport: ");
        String rules = scanner.nextLine();

        System.out.println("Is it an indoor sport (yes/no): ");
        boolean isIndoor = scanner.nextLine().trim().equalsIgnoreCase("yes");

        System.out.println("Enter the name and surname of the person in charge: ");
        String personInCharge = scanner.nextLine();

        abs_sport customSport = new CustomSport(sportName, equipment, gymName, numberOfPlayers, rules, isIndoor, personInCharge);
        sports.add(customSport);

        System.out.println("✅ Sport \"" + sportName + "\" added successfully.");
    }
}
