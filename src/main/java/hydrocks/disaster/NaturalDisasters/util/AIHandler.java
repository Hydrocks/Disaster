package hydrocks.disaster.NaturalDisasters.util;

import jline.internal.Nullable;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Mob;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class AIHandler {
    @Nullable
    public static Player closestPlayer(Location loc, int range) {
        List<Player> players = new ArrayList<>();

        Player closestPlayer = null;
        double closestDistance = Double.MAX_VALUE;

        // Loop through the entities within the range
        for (Entity boss : loc.getWorld().getNearbyEntities(loc, range, range, range)) {
            if (boss instanceof Player vic) {
                if (vic.getLocation().distance(loc) < range && vic.getGameMode() != GameMode.CREATIVE && vic.getGameMode() != GameMode.SPECTATOR) {
                    players.add(vic);
                }
            }
        }


        // Check the closest player to the given location
        for (Player player : players) {
            double distance = player.getLocation().distance(loc);
            if (distance < closestDistance) {
                closestDistance = distance;
                closestPlayer = player;
            }
        }

        return closestPlayer;
    }

    @Nullable
    public static Player closestPlayer(Location loc) {
        List<Player> players = new ArrayList<>();

        Player closestPlayer = null;
        double closestDistance = Double.MAX_VALUE;

        // Add all the players to the list
        for (Player player : Bukkit.getOnlinePlayers()) {
            if (player.getGameMode() != GameMode.CREATIVE && player.getGameMode() != GameMode.SPECTATOR) {
                players.add(player);
            }
        }


        // Check the closest player to the given location
        for (Player player : players) {
            double distance = player.getLocation().distance(loc);
            if (distance < closestDistance) {
                closestDistance = distance;
                closestPlayer = player;
            }
        }

        return closestPlayer;
    }
}
