package org.ScubMembership.SportActivity;

public interface Sport {
    String getName();
    String[] getEquipment();
    String getGymName();
    int getNumberOfPlayers();
    void displayInfo();
    String getRules();
    boolean isIndoorSport();
    String getPersonInCharge();
}
