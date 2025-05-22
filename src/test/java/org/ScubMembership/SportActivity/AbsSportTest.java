package org.ScubMembership.SportActivity;

import org.ScubMembership.SportActivity.Basketball.Basketball;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class AbsSportTest {
    @Test
    public void testCustomSportCreation() {
        abs_sport mySport = new abs_sport("Dodgeball", new String[] {"Ball"}, "Dodge Gym", 7,
                "Hit all the opponents with the balls without getting hit in order to win", true,
                "Alessio Stroppari") {};
        assertEquals("Dodgeball", mySport.getName());
        assertArrayEquals(new String[] {"Ball"}, mySport.getEquipment());
        assertEquals("Dodge Gym", mySport.getGymName());
        assertEquals(7, mySport.getNumberOfPlayers());
        assertEquals("Hit all the opponents with the balls without getting hit in order to win", mySport.getRules());
        assertTrue(mySport.isIndoorSport());
        assertEquals("Alessio Stroppari", mySport.getPersonInCharge());
    }

    @Test
    public void testBasketballProperties() {
        Basketball basketball = new Basketball();
        assertEquals("Basketball", basketball.getName());
        assertArrayEquals(new String[] {"Basketball Shoes", "Jerseys"}, basketball.getEquipment());
        assertEquals("Talvera", basketball.getGymName());
        assertEquals(10, basketball.getNumberOfPlayers());
        assertEquals("Two teams of five players each try to score by shooting a ball through a hoop."
                + "The game consists of four quarters (each of 10 minutes). The team with the most points wins.", basketball.getRules());
        assertTrue(basketball.isIndoorSport());
        assertEquals("Lorenzo Vezzani", basketball.getPersonInCharge());
    }
}
