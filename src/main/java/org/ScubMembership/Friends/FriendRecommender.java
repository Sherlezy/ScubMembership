package org.ScubMembership.Friends;

import org.ScubMembership.QuizzSystem.QuizLeaderboardEntry;
import org.ScubMembership.QuizzSystem.QuizLeaderboardManager;
import org.ScubMembership.SportActivity.EventManager;
import org.ScubMembership.SportActivity.SportEvent;
import org.ScubMembership.Users.NormalUser.NormalUser;
import org.ScubMembership.Utils.UserStorage;

import java.util.*;
import java.util.stream.Collectors;

public class FriendRecommender {

    public static List<NormalUser> getRecommendedFriends(NormalUser currentUser) {
        Map<NormalUser, Integer> scoreMap = new HashMap<>();
        Map<String, NormalUser> users = UserStorage.loadUsers();
        Set<NormalUser> allUsers = new HashSet<>(users.values());

        for(NormalUser candidate : allUsers) {
            if(candidate.getUsername().equals(currentUser.getUsername())) continue;
            if(candidate.getFriends().contains(currentUser.getUsername())) continue;
            if(candidate.getSentFriendRequest().contains(currentUser.getUsername())) continue;

            int score = 0;

            if(Objects.equals(currentUser.getPreferredSport(), candidate.getPreferredSport())) score += 5;

            if(Objects.equals(currentUser.getPreferredTimeSlot(), candidate.getPreferredTimeSlot())) score += 3;

            Set<String> currentUserEvents = getAttendedEvents(currentUser);
            Set<String> candidateEvents = getAttendedEvents(candidate);

            Set<String> sharedEvents = new HashSet<>(currentUserEvents);
            sharedEvents.retainAll(candidateEvents);
            score += sharedEvents.size() * 2;

            Set<String> sharedGoodQuiz = getSharedQuizHighScore(currentUser, candidate);
            score += sharedGoodQuiz.size();

            if(score > 0) {
                scoreMap.put(candidate, score);
            }
        }

        return scoreMap.entrySet().stream()
                .sorted((a, b) -> Integer.compare(b.getValue(), a.getValue()))
                .map(Map.Entry::getKey)
                .toList();
    }

    private static Set<String> getAttendedEvents(NormalUser user) {
        Set<String> events = new HashSet<>();
        for(SportEvent event : EventManager.getEvents()) {
            if(event.getRegisteredUsers().contains(user.getUsername())) events.add(event.toString());
        }
        return events;
    }

    private static Set<String> getSharedQuizHighScore(NormalUser user1, NormalUser user2) {
        Map<String, Integer> user1Scores = new HashMap<>();
        Map<String, Integer> user2Scores = new HashMap<>();

        for(QuizLeaderboardEntry e : QuizLeaderboardManager.getLeaderboard("")) {
            if(e.getScore() >= 7) {
                if(e.getUsername().equals(user1.getUsername())) {
                    user1Scores.put(e.getSport(), e.getScore());
                } else if(e.getUsername().equals(user2.getUsername())) {
                    user2Scores.put(e.getSport(), e.getScore());
                }
            }
        }

        Set<String> sharedSports = new HashSet<>(user1Scores.keySet());
        sharedSports.retainAll(user2Scores.keySet());
        return sharedSports;
    }
}
