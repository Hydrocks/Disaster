package hydrocks.disaster.utils;

import org.bukkit.ChatColor;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class HexUtil {
    private static final Pattern HEX_PATTERN = Pattern.compile("&(#\\w{6})");
    public static String hexColor(String str) {
        Matcher matcher = HEX_PATTERN.matcher(ChatColor.translateAlternateColorCodes('&', str));
        StringBuilder buffer = new StringBuilder();

        while (matcher.find())
            matcher.appendReplacement(buffer, net.md_5.bungee.api.ChatColor.of(matcher.group(1)).toString());

        return matcher.appendTail(buffer).toString();

    }
}
