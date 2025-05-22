package org.ScubMembership.SportActivity;

import java.io.Serializable;

public abstract class abs_sport implements Sport, Serializable {
    private static final long serialVersionUID = 1L;

    protected String name;
    protected String[] equipment;
    protected String gymName;
    protected int numberOfPlayers;
    protected String rules;
    protected boolean indoorSport;
    protected String personInCharge;

    public abs_sport(String name, String[] equipment, String gymName, int numberOfPlayers, String rules,
                     boolean indoorSport, String personInCharge) {
        this.name = name;
        this.equipment = equipment;
        this.gymName = gymName;
        this.numberOfPlayers = numberOfPlayers;
        this.rules = rules;
        this.indoorSport = indoorSport;
        this.personInCharge = personInCharge;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String[] getEquipment() {
        return equipment;
    }

    @Override
    public String getGymName() {
        return gymName;
    }

    @Override
    public int getNumberOfPlayers() {
        return numberOfPlayers;
    }

    @Override
    public String getRules() {
        return rules;
    }

    @Override
    public boolean isIndoorSport() {
        return indoorSport;
    }

    @Override
    public String getPersonInCharge() {
        return personInCharge;
    }

    @Override
    public void displayInfo() {
        System.out.println("Name: " + name);
        System.out.println("The sport is practiced ");
        if (indoorSport) {
            System.out.print("in the gym: " + gymName);
        } else {
            System.out.print("outside");
        }
        System.out.println("Number of players: " + numberOfPlayers);
        System.out.println("Equipment: ");
        for (String item : equipment) {
            System.out.print(item + " ");
        }
        System.out.println("\nRules: " + rules);
        System.out.println("Person in charge: " + personInCharge);
        System.out.println();
    }
}
