package hydrocks.disaster;

import hydrocks.disaster.commands.test;
import org.bukkit.plugin.java.JavaPlugin;

public final class Disaster extends JavaPlugin {

    private static Disaster plugin;
    public static Disaster getPlugin() {
        return plugin;
    }
    @Override
    public void onEnable() {
        plugin = this;
        // Plugin startup logic

        getCommand("test").setExecutor(new test());

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
