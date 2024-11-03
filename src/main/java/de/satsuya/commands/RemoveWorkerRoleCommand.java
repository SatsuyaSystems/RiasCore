package de.satsuya.commands;

import de.satsuya.RiasCore;
import net.luckperms.api.model.user.User;
import net.luckperms.api.model.user.UserManager;
import net.luckperms.api.node.Node;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class RemoveWorkerRoleCommand implements PluginCommand {

    @Override
    public String getName() {
        return "removeworker";
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("This command can only be used by players.");
            return true;
        }

        Player leader = (Player) sender;

        if (args.length < 1) {
            leader.sendMessage(ChatColor.RED + "Usage: /removeworker <player>");
            return true;
        }

        Player target = Bukkit.getPlayer(args[0]);
        if (target == null) {
            leader.sendMessage(ChatColor.RED + "Player not found.");
            return true;
        }

        // Determine the leader's role
        String leaderRole = getLeaderRole(leader);
        if (leaderRole == null) {
            leader.sendMessage(ChatColor.RED + "You do not have the correct leader role to remove a worker.");
            return true;
        }

        // Check the corresponding worker role
        String workerRole = RiasCore.roleMapping.get(leaderRole);
        if (workerRole == null) {
            leader.sendMessage(ChatColor.RED + "No corresponding worker role found for your leader role.");
            return true;
        }

        // Verify if the target player has this specific worker role
        if (!target.hasPermission("group." + workerRole)) {
            leader.sendMessage(ChatColor.RED + "The target player does not have the worker role you manage.");
            return true;
        }

        // Remove the worker role from the target player
        removeWorkerRole(target, "group." + workerRole);
        leader.sendMessage(ChatColor.GREEN + "Removed worker role " + workerRole + " from " + target.getName());
        target.sendMessage(ChatColor.YELLOW + "Your worker role " + workerRole + " has been removed.");

        return true;
    }

    // Checks if the player has a leader role and returns it if found
    private String getLeaderRole(Player player) {
        for (String role : RiasCore.roleMapping.keySet()) {
            if (player.hasPermission("group." + role)) {
                return role;
            }
        }
        return null;
    }

    // Method to add a permission to a player
    public void removeWorkerRole(Player player, String permission) {
        UserManager userManager = RiasCore.luckPerms.getUserManager();

        User user = userManager.getUser(player.getUniqueId());
        if (user == null) {
            RiasCore.Instance.getLogger().warning("User not found: " + player.getName());
            return;
        }

        Node node = Node.builder(permission).build();
        user.data().remove(node);
        userManager.saveUser(user);
    }
}