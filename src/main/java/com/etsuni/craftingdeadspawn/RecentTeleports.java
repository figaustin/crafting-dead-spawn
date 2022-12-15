package com.etsuni.craftingdeadspawn;

import org.bukkit.Location;

import java.util.ArrayList;

public class RecentTeleports {

    private ArrayList<Location> recentTps = new ArrayList<>();
    private static RecentTeleports instance = new RecentTeleports();

    public static RecentTeleports getInstance() {
        return instance;
    }

    public ArrayList<Location> getRecentTps() {
        return recentTps;
    }
}
