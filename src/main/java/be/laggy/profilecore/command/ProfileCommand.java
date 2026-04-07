package be.laggy.profilecore.command;

import be.laggy.profilecore.Profilecore;
import be.laggy.profilecore.command.subcommand.ReloadSubCommand;
import be.laggy.profilecore.command.subcommand.ResetSubCommand;
import be.laggy.profilecore.command.subcommand.SetCoinsSubCommand;
import be.laggy.profilecore.command.subcommand.SetDeathsSubCommand;
import be.laggy.profilecore.command.subcommand.SetKillsSubCommand;
import be.laggy.profilecore.config.Settings;
import be.laggy.profilecore.profile.Profile;
import be.laggy.profilecore.util.ChatUtil;
import be.laggy.profilecore.util.PermissionUtil;
import be.laggy.profilecore.util.TimeUtil;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.*;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ProfileCommand implements CommandExecutor, TabCompleter {

    private final Profilecore plugin;
    private final ReloadSubCommand reloadSubCommand;
    private final SetKillsSubCommand setKillsSubCommand;
    private final SetDeathsSubCommand setDeathsSubCommand;
    private final SetCoinsSubCommand setCoinsSubCommand;
    private final ResetSubCommand resetSubCommand;

    public ProfileCommand(Profilecore plugin) {
        this.plugin = plugin;
        this.reloadSubCommand = new ReloadSubCommand(plugin);
        this.setKillsSubCommand = new SetKillsSubCommand(plugin);
        this.setDeathsSubCommand = new SetDeathsSubCommand(plugin);
        this.setCoinsSubCommand = new SetCoinsSubCommand(plugin);
        this.resetSubCommand = new ResetSubCommand(plugin);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!PermissionUtil.has(sender, Settings.PERMISSION_PROFILE)) {
            ChatUtil.send(sender, plugin.getMessageManager().get("no-permission"));
            return true;
        }

        if (args.length == 0) {
            if (!(sender instanceof Player player)) {
                ChatUtil.send(sender, plugin.getMessageManager().get("player-only"));
                return true;
            }

            sendProfile(sender, player.getName(), player.getUniqueId());
            return true;
        }

        String sub = args[0].toLowerCase();

        switch (sub) {
            case "reload" -> {
                return reloadSubCommand.execute(sender, args);
            }
            case "setkills" -> {
                return setKillsSubCommand.execute(sender, args);
            }
            case "setdeaths" -> {
                return setDeathsSubCommand.execute(sender, args);
            }
            case "setcoins" -> {
                return setCoinsSubCommand.execute(sender, args);
            }
            case "reset" -> {
                return resetSubCommand.execute(sender, args);
            }
            default -> {
                if (!PermissionUtil.has(sender, Settings.PERMISSION_VIEW_OTHERS, Settings.PERMISSION_ADMIN)) {
                    ChatUtil.send(sender, plugin.getMessageManager().get("no-permission"));
                    return true;
                }

                OfflinePlayer target = Bukkit.getOfflinePlayer(args[0]);
                if (target.getName() == null || target.getUniqueId() == null) {
                    ChatUtil.send(sender, plugin.getMessageManager().get("player-not-found"));
                    return true;
                }

                sendProfile(sender, target.getName(), target.getUniqueId());
                return true;
            }
        }
    }

    private void sendProfile(CommandSender sender, String fallbackName, java.util.UUID uuid) {
        Profile profile = plugin.getProfileService().getCachedProfile(uuid);

        if (profile == null) {
            ChatUtil.send(sender, "&cThat profile is not loaded in cache right now.");
            return;
        }

        ChatUtil.send(sender, plugin.getMessageManager().get("profile-header"));
        ChatUtil.send(sender, plugin.getMessageManager().get("profile-name").replace("%player%", profile.getUsername() != null ? profile.getUsername() : fallbackName));
        ChatUtil.send(sender, plugin.getMessageManager().get("profile-kills").replace("%kills%", String.valueOf(profile.getKills())));
        ChatUtil.send(sender, plugin.getMessageManager().get("profile-deaths").replace("%deaths%", String.valueOf(profile.getDeaths())));
        ChatUtil.send(sender, plugin.getMessageManager().get("profile-coins").replace("%coins%", String.valueOf(profile.getCoins())));
        ChatUtil.send(sender, plugin.getMessageManager().get("profile-playtime").replace("%playtime%", TimeUtil.formatDuration(profile.getPlaytimeSeconds())));
        ChatUtil.send(sender, plugin.getMessageManager().get("profile-last-seen").replace("%last_seen%", TimeUtil.formatTimestamp(profile.getLastSeen())));
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        if (args.length == 1) {
            return filter(Arrays.asList("reload", "setkills", "setdeaths", "setcoins", "reset"), args[0]);
        }

        if (args.length == 2 && List.of("setkills", "setdeaths", "setcoins", "reset").contains(args[0].toLowerCase())) {
            List<String> names = new ArrayList<>();
            Bukkit.getOnlinePlayers().forEach(player -> names.add(player.getName()));
            return filter(names, args[1]);
        }

        return List.of();
    }

    private List<String> filter(List<String> values, String input) {
        List<String> result = new ArrayList<>();
        for (String value : values) {
            if (value.toLowerCase().startsWith(input.toLowerCase())) {
                result.add(value);
            }
        }
        return result;
    }
}