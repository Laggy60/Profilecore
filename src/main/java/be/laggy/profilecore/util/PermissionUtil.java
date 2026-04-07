package be.laggy.profilecore.util;

import org.bukkit.command.CommandSender;

public final class PermissionUtil {

    private PermissionUtil() {}

    public static boolean has(CommandSender sender, String... permissions) {
        for (String permission : permissions) {
            if (sender.hasPermission(permission)) {
                return true;
            }
        }
        return false;
    }
}