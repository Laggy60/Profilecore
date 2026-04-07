package be.laggy.profilecore.command.subcommand;

import be.laggy.profilecore.Profilecore;
import be.laggy.profilecore.config.Settings;
import be.laggy.profilecore.profile.Profile;
import be.laggy.profilecore.util.ChatUtil;
import be.laggy.profilecore.util.NumberUtil;
import be.laggy.profilecore.util.PermissionUtil;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SetDeathsSubCommand {

    private final Profilecore plugin;

    public SetDeathsSubCommand(Profilecore plugin) {
        this.plugin = plugin;
    }

    public boolean execute(CommandSender sender, String[] args) {
        if (!PermissionUtil.has(sender, Settings.PERMISSION_SET_DEATHS, Settings.PERMISSION_ADMIN)) {
            ChatUtil.send(sender, plugin.getMessageManager().get("no-permission"));
            return true;
        }

        if (args.length < 3) {
            ChatUtil.send(sender, plugin.getMessageManager().get("usage-setdeaths"));
            return true;
        }

        Player target = Bukkit.getPlayerExact(args[1]);
        if (target == null) {
            ChatUtil.send(sender, plugin.getMessageManager().get("player-not-found"));
            return true;
        }

        if (!NumberUtil.isInteger(args[2])) {
            ChatUtil.send(sender, plugin.getMessageManager().get("invalid-number"));
            return true;
        }

        Profile profile = plugin.getProfileService().getCachedProfile(target.getUniqueId());
        if (profile == null) {
            ChatUtil.send(sender, "&cThat profile is not loaded.");
            return true;
        }

        int amount = Integer.parseInt(args[2]);
        profile.setDeaths(amount);
        plugin.getProfileService().saveProfileAsync(profile);

        ChatUtil.send(sender, plugin.getMessageManager().get("set-deaths-success")
                .replace("%player%", target.getName())
                .replace("%amount%", String.valueOf(amount)));
        return true;
    }
}