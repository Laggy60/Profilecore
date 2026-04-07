package be.laggy.profilecore.util;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

public final class ChatUtil {

    private ChatUtil() {}

    public static String color(String text) {
        return ChatColor.translateAlternateColorCodes('&', text);
    }

    public static void send(CommandSender sender, String message) {
        sender.sendMessage(color(message));
    }
}