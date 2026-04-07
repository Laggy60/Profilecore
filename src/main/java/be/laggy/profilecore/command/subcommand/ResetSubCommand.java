package be.laggy.profilecore.command.subcommand;

import be.laggy.profilecore.Profilecore;
import be.laggy.profilecore.config.Settings;
import be.laggy.profilecore.util.ChatUtil;
import be.laggy.profilecore.util.PermissionUtil;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;

public class ResetSubCommand {

    private final Profilecore plugin;

    public ResetSubCommand(Profilecore plugin) {
        this.plugin = plugin;
    }

    public boolean execute(CommandSender sender, String[] args) {
        if (!PermissionUtil.has(sender, Settings.PERMISSION_RESET, Settings.PERMISSION_ADMIN)) {
            ChatUtil.send(sender, plugin.getMessageManager().get("no-permission"));
            return true;
        }

        if (args.length < 2) {
            ChatUtil.send(sender, plugin.getMessageManager().get("usage-reset"));
            return true;
        }

        OfflinePlayer target = Bukkit.getOfflinePlayer(args[1]);
        if (target.getUniqueId() == null) {
            ChatUtil.send(sender, plugin.getMessageManager().get("player-not-found"));
            return true;
        }

        plugin.getProfileService().resetProfile(target.getUniqueId());
        ChatUtil.send(sender, plugin.getMessageManager().get("reset-success")
                .replace("%player%", target.getName() == null ? args[1] : target.getName()));
        return true;
    }
}