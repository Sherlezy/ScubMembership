package org.ScubMembership.RecommendationLogic;

import org.ScubMembership.SportActivity.SportEvent;
import org.ScubMembership.SportActivity.SportRegistry;
import org.ScubMembership.SportActivity.abs_sport;
import org.ScubMembership.Users.NormalUser.NormalUser;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class SportUtils {

    // Already existing method: sorts sports with favorite first
    public static List<abs_sport> getSortedSportsWithFavoriteFirst(NormalUser user) {
        String favorite = user.getPreferredSport();
        List<abs_sport> sorted = new ArrayList<>();

        // Add favorite sport first
        for (abs_sport sport : SportRegistry.SPORTS) {
            if (sport.getName().equalsIgnoreCase(favorite)) {
                sorted.add(sport);
                break;
            }
        }

        // Add the rest
        for (abs_sport sport : SportRegistry.SPORTS) {
            if (!sport.getName().equalsIgnoreCase(favorite)) {
                sorted.add(sport);
            }
        }

        return sorted;
    }

    // ✅ NEW: Sorts events so that events of the favorite sport come first
    public static List<SportEvent> sortEventsByFavoriteSportFirst(List<SportEvent> events, NormalUser user) {
        String favoriteSport = user.getPreferredSport();

        return events.stream()
                .sorted(Comparator.comparing((SportEvent e) ->
                        !e.getSport().getName().equalsIgnoreCase(favoriteSport)))
                .collect(Collectors.toList());
    }
}
