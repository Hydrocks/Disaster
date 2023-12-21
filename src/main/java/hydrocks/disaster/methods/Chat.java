package hydrocks.disaster.methods;

import hydrocks.disaster.utils.HexUtil;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class Chat {

    public static void sendActionBarMessageToAll(String msg) {
        for (Player pl : Bukkit.getOnlinePlayers()) {
            pl.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(HexUtil.hexColor(msg)));
        }
    }
}
