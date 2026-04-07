package be.laggy.profilecore.command.subcommand;

import be.laggy.profilecore.Profilecore;
import be.laggy.profilecore.config.Settings;
import be.laggy.profilecore.util.ChatUtil;
import be.laggy.profilecore.util.PermissionUtil;
import org.bukkit.command.CommandSender;

public class ReloadSubCommand {

    private final Profilecore plugin;

    public ReloadSubCommand(Profilecore plugin) {
        this.plugin = plugin;
    }

    public boolean execute(CommandSender sender, String[] args) {
        if (!PermissionUtil.has(sender, Settings.PERMISSION_RELOAD, Settings.PERMISSION_ADMIN)) {
            ChatUtil.send(sender, plugin.getMessageManager().get("no-permission"));
            return true;
        }

        plugin.reloadPlugin();
        ChatUtil.send(sender, plugin.getMessageManager().get("reloaded"));
        return true;
    }
}