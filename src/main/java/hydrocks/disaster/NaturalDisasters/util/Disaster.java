package hydrocks.disaster.NaturalDisasters.util;

import hydrocks.disaster.enums.Disasters;
import hydrocks.disaster.utils.HexUtil;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class Disaster extends BukkitRunnable {

    public static Disasters currentDisaster = null;
    public final Disasters disaster;
    public int ticks = 0;
    public Disaster(Disasters disaster, int delayTime, int tickTime) {
        this.disaster = disaster;
        currentDisaster = disaster;

        // Alert the players online of the incoming disaster
        for (Player player : Bukkit.getOnlinePlayers()) player.playSound(player.getLocation(), Sound.ENTITY_ENDER_DRAGON_AMBIENT, 1, 0);
        Bukkit.broadcastMessage(HexUtil.hexColor("&4&lDISASTER &8● " + disaster.getName() + " &7is coming, BEWARE!"));

        // Run the disaster while also canceling in the specified time
        this.runTaskTimer(hydrocks.disaster.Disaster.getPlugin(), delayTime, tickTime);
        this.doLaterCancel(delayTime);
    }

    /**
     * Placeholder run, will be handled mostly in the extended classes.
     */
    @Override
    public void run() {
        this.handleDisaster();
    }

    /**
     * Check If the disaster matches the current disaster
     */
    public void handleDisaster() {
        if (currentDisaster != null && currentDisaster == this.disaster)
            ticks++;
        else this.cancelGlobalTask();
    }

    /**
     * Cancels the disaster after the specified duration
     */
    private void doLaterCancel(int delayTime) {
        Bukkit.getScheduler().runTaskLater(hydrocks.disaster.Disaster.getPlugin(), this::cancelGlobalTask, (disaster.getDurationInSeconds() * 20L) + delayTime);
    }

    /**
     * Make sure you're canceling the right task
     */
    private void cancelGlobalTask() {
        this.cancel();
    }
}
