package com.etsuni.craftingdeadspawn;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static com.etsuni.craftingdeadspawn.CraftingDeadSpawn.plugin;

public class Teleport implements CommandExecutor {

    public void addSpawnPoint(Location location) {
        List<Location> spawns = plugin.getSpawnsConfig().getList("spawns") == null ? new ArrayList<>()
                : (List<Location>) plugin.getSpawnsConfig().getList("spawns");

        spawns.add(location);
        plugin.getSpawnsConfig().set("spawns", spawns);
        plugin.saveCfg();
    }

    public void randomTp(Player player) {

        List<Location> spawns = (List<Location>) plugin.getSpawnsConfig().getList("spawns");

        if (spawns != null) {
            Random random = new Random();

            int randomIndex = random.nextInt(spawns.size());
            Location randomLocation = spawns.get(randomIndex);

            if(spawns.size() > 3) {
                while(RecentTeleports.getInstance().getRecentTps().contains(randomLocation)) {
                    randomIndex = random.nextInt(spawns.size());
                    randomLocation = spawns.get(randomIndex);
                }

                if(!RecentTeleports.getInstance().getRecentTps().contains(randomLocation)) {
                    if(RecentTeleports.getInstance().getRecentTps().size() == 3) {

                        //Remove oldest tp from list and add in the newest one, keeping the size of 3
                        RecentTeleports.getInstance().getRecentTps().remove(0);
                        RecentTeleports.getInstance().getRecentTps().add(randomLocation);
                    } else {
                        RecentTeleports.getInstance().getRecentTps().add(randomLocation);
                    }
                }
            }
            player.teleport(randomLocation);
        }
    }


    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(sender instanceof Player) {
            Player player = (Player) sender;
            if(command.getName().equalsIgnoreCase("cdsetspawn")) {
                Location location = player.getLocation();
                addSpawnPoint(location);
                return true;
            }
            else if(command.getName().equalsIgnoreCase("randomspawn")) {
                randomTp(player);
                return true;
            }
        }
        return false;
    }
}
