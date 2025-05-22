package org.ScubMembership.QuizzSystem;

import java.time.format.DateTimeFormatter;
import java.util.List;

public class LeaderboardDisplay {

    public static void showLeaderboardForSport(String sport) {
        List<QuizLeaderboardEntry> leaderboard = QuizLeaderboardManager.getLeaderboard(sport);

        if (leaderboard.isEmpty()) {
            System.out.println("📭 No quiz entries yet for " + sport + ".");
            return;
        }

        System.out.println("\n🏆 Leaderboard for " + sport + ":");
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

        int rank = 1;
        for (QuizLeaderboardEntry entry : leaderboard) {
            String badge = entry.isFavorite() ? " ⭐ Favorite Sport" : "";
            String date = entry.getTimestamp().format(formatter);

            System.out.printf("%2d. %-15s — %2d pts%s [%s]%n",
                    rank++,
                    entry.getUsername(),
                    entry.getScore(),
                    badge,
                    date
            );
        }
    }
}
