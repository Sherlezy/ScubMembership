package org.ScubMembership.RecommendationLogic;

import org.ScubMembership.Users.NormalUser.NormalUser;
import org.ScubMembership.Utils.UserStorage;

import java.util.HashMap;
import java.util.Map;

public class TimeSlotSuggester {
    public static String suggestBestTimeSlot(String selectedSport) {
        Map<String, Integer> slotCounts = new HashMap<>();
        slotCounts.put("Morning", 0);
        slotCounts.put("Afternoon", 0);
        slotCounts.put("Evening", 0);

        HashMap<String, NormalUser> users = UserStorage.loadUsers();
        int totalRelevantUsers = 0;

        for (NormalUser user : users.values()) {
            if (user.getPreferredSport().equalsIgnoreCase(selectedSport)) {
                totalRelevantUsers++;
                String slot = user.getPreferredTimeSlot();
                if(slot != null){
                slotCounts.put(slot, slotCounts.getOrDefault(slot, 0) + 1);}
            }
        }

        if (totalRelevantUsers == 0) {
            return "⚠️ No users found with preferred sport \"" + selectedSport + "\". No time slot suggestion available.";
        }

        int max = slotCounts.values().stream().max(Integer::compare).orElse(0);
        StringBuilder bestSlots = new StringBuilder();
        for (Map.Entry<String, Integer> entry : slotCounts.entrySet()) {
            if (entry.getValue() == max) {
                if (!bestSlots.isEmpty()) bestSlots.append(" or ");
                bestSlots.append(entry.getKey());
            }
        }

        return "📌 Suggestion: Best time slot(s) based on user preferences: " + bestSlots + " (" + max + " user(s) prefer this time).";
    }
}
