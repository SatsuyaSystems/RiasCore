package de.satsuya.commands;


import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ResetDeathsCommand implements PluginCommand {

    @Override
    public String getName() {
        return "resetdeaths";
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("This command can only be used by players.");
            return true;
        }

        Player player = (Player) sender;
        if (!player.hasPermission("riascore.resetdeaths")) {
            player.sendMessage(ChatColor.RED + "You don't have permission to use this command.");
            return true;
        }

        if (args.length < 1) {
            player.sendMessage(ChatColor.RED + "Please specify a player to reset deaths.");
            return true;
        }

        Player target = Bukkit.getPlayer(args[0]);
        if (target == null) {
            player.sendMessage(ChatColor.RED + "Player not found.");
            return true;
        }

        if (target.getPlayer().isOnline()) {
            target.getPlayer().setStatistic(org.bukkit.Statistic.DEATHS, 0);
            player.sendMessage(ChatColor.GREEN + "Death set to 0.");
        }

        return true;
    }
}