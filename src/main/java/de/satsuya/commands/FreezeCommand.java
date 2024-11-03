package de.satsuya.commands;

import de.satsuya.managers.FreezeManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class FreezeCommand implements PluginCommand {

    private final FreezeManager freezeManager;

    public FreezeCommand(FreezeManager freezeManager) {
        this.freezeManager = freezeManager;
    }

    @Override
    public String getName() {
        return "freeze";
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("This command can only be used by players.");
            return true;
        }

        Player player = (Player) sender;
        if (!player.hasPermission("riascore.freeze")) {
            player.sendMessage(ChatColor.RED + "You don't have permission to use this command.");
            return true;
        }

        if (args.length < 1) {
            player.sendMessage(ChatColor.RED + "Please specify a player to freeze.");
            return true;
        }

        Player target = Bukkit.getPlayer(args[0]);
        if (target == null) {
            player.sendMessage(ChatColor.RED + "Player not found.");
            return true;
        }

        if (freezeManager.isFrozen(target)) {
            freezeManager.unfreezePlayer(target);
            player.sendMessage(ChatColor.GREEN + target.getName() + " has been unfrozen.");
            target.sendMessage(ChatColor.YELLOW + "You have been unfrozen.");
        } else {
            freezeManager.freezePlayer(target);
            player.sendMessage(ChatColor.GREEN + target.getName() + " has been frozen.");
            target.sendMessage(ChatColor.RED + "You have been frozen.");
        }

        return true;
    }
}