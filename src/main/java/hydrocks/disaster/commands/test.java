package hydrocks.disaster.commands;

import hydrocks.disaster.NaturalDisasters.Cyclone;
import hydrocks.disaster.NaturalDisasters.util.Disaster;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class test implements CommandExecutor {


    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (sender instanceof Player p) {

            if (args.length == 0) {
                new Cyclone(p.getLocation());
            } else {
                Disaster.currentDisaster = null;
            }
        }

        return false;
    }
}
