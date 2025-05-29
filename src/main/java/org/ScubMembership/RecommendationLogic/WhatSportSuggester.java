package org.ScubMembership.RecommendationLogic;

import org.ScubMembership.Users.NormalUser.NormalUser;
import org.ScubMembership.SportActivity.SportEvent;
import org.ScubMembership.SportActivity.EventManager;
import org.ScubMembership.Utils.UserStorage;

import java.util.*;
import java.util.stream.Collectors;

public class WhatSportSuggester {
    private static final double SPORT_WEIGHT = 0.7;
    private static final double RATING_WEIGHT = 0.3;
    private static final int MIN_USERS_THRESHOLD = 2;
    private static final int MIN_EVENTS_THRESHOLD = 2;

    public static void suggestSportAndTimeSlot() {
        HashMap<String, NormalUser> users = UserStorage.loadUsers();
        List<SportEvent> events = EventManager.getEvents();

        if (users.isEmpty()) {
            System.out.println("⚠️ No users found.");
            return;
        }

        int totalUsers = users.size();


        Map<String, Integer> sportCounts = new HashMap<>();
        Map<String, Map<String, Integer>> timeSlotCountsPerSport = new HashMap<>();

        for (NormalUser user : users.values()) {
            String sport = user.getPreferredSport();
            if (sport == null) continue;
            sportCounts.put(sport, sportCounts.getOrDefault(sport, 0) + 1);

            String slot = user.getPreferredTimeSlot();
            if (slot != null) {
                timeSlotCountsPerSport.putIfAbsent(sport, new HashMap<>());
                Map<String, Integer> slotMap = timeSlotCountsPerSport.get(sport);
                slotMap.put(slot, slotMap.getOrDefault(slot, 0) + 1);
            }
        }


        Map<String, List<Double>> ratingsPerSport = new HashMap<>();
        for (SportEvent event : events) {
            String sportName = event.getSport().getName();
            List<Double> ratings = event.getFeedbackList().stream()
                    .map(f -> f.getRating())
                    .collect(Collectors.toList());
            if (!ratings.isEmpty()) {
                ratingsPerSport.putIfAbsent(sportName, new ArrayList<>());
                ratingsPerSport.get(sportName).addAll(ratings);
            }
        }


        String bestSport = null;
        String bestSlot = null;
        double bestScore = 0.0;

        for (String sport : sportCounts.keySet()) {
            int userCount = sportCounts.get(sport);
            if (userCount < MIN_USERS_THRESHOLD) continue;

            double sportScore = ((double) userCount / totalUsers) * SPORT_WEIGHT;

            // Time slot logic: find best slot for that sport
            Map<String, Integer> slotMap = timeSlotCountsPerSport.getOrDefault(sport, new HashMap<>());
            String mostCommonSlot = slotMap.entrySet().stream()
                    .max(Map.Entry.comparingByValue())
                    .map(Map.Entry::getKey).orElse("N/A");


            double ratingScore = 0.0;
            List<Double> ratings = ratingsPerSport.getOrDefault(sport, new ArrayList<>());
            if (ratings.size() >= MIN_EVENTS_THRESHOLD) {
                Collections.sort(ratings);
                double median = ratings.size() % 2 == 0
                        ? (ratings.get(ratings.size()/2 - 1) + ratings.get(ratings.size()/2)) / 2.0
                        : ratings.get(ratings.size()/2);
                ratingScore = (median / 5.0) * RATING_WEIGHT;
            }

            double totalScore = sportScore + ratingScore;
            if (totalScore > bestScore) {
                bestScore = totalScore;
                bestSport = sport;
                bestSlot = mostCommonSlot;
            }
        }


        if (bestSport != null && bestSlot != null) {
            System.out.println("📢 Suggested Sport Event: " + bestSport + " at " + bestSlot);
            System.out.printf("  - Total Score: %.2f/1.00\n", bestScore);
        } else {
            System.out.println("❌ No valid sport suggestion could be made.");
        }
    }
}
