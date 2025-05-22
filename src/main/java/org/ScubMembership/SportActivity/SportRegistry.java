package org.ScubMembership.SportActivity;

import org.ScubMembership.SportActivity.Basketball.Basketball;
import org.ScubMembership.SportActivity.Climbing.Climbing;
import org.ScubMembership.SportActivity.Cycling.Cycling;
import org.ScubMembership.SportActivity.Football.Football;
import org.ScubMembership.SportActivity.Running.Running;
import org.ScubMembership.SportActivity.Swimming.Swimming;
import org.ScubMembership.SportActivity.Tennis.Tennis;
import org.ScubMembership.SportActivity.Volleyball.Volleyball;
import org.ScubMembership.SportActivity.Yoga.Yoga;
import org.ScubMembership.SportActivity.abs_sport;

import java.util.List;

public class SportRegistry {
    public static final List<abs_sport> SPORTS = List.of(new Tennis(), new Basketball(),new Climbing(), new Cycling(), new Football(), new Running(), new Swimming(), new Volleyball(), new Yoga());
}
