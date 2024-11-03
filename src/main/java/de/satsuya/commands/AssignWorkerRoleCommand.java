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

public class AssignWorkerRoleCommand implements PluginCommand {
    @Override
    public String getName() {
        return "assignworker";
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player leader)) {
            sender.sendMessage("This command can only be used by players.");
            return true;
        }

        if (args.length < 1) {
            leader.sendMessage(ChatColor.RED + "Usage: /assignworker <player>");
            return true;
        }

        Player target = Bukkit.getPlayer(args[0]);
        if (target == null) {
            leader.sendMessage(ChatColor.RED + "Player not found.");
            return true;
        }

        // Identify leader's role and corresponding worker role
        String leaderRole = getLeaderRole(leader);
        RiasCore.Instance.getLogger().info("Leader role: " + leaderRole);
        if (leaderRole == null) {
            leader.sendMessage(ChatColor.RED + "You do not have a leader role to assign a worker.");
            return true;
        }

        String workerRole = RiasCore.roleMapping.get(leaderRole);
        RiasCore.Instance.getLogger().info("Worker role: " + workerRole);
        if (workerRole == null) {
            leader.sendMessage(ChatColor.RED + "No corresponding worker role found.");
            return true;
        }

        if (hasWorkerRole(target)) {
            leader.sendMessage(ChatColor.RED + "The player already has a worker role.");
            return true;
        }

        // Assign worker role to the target player
        assignRole(target, "group." + workerRole);

        leader.sendMessage(ChatColor.GREEN + "Assigned worker role " + workerRole + " to " + target.getName());
        target.sendMessage(ChatColor.YELLOW + "You have been assigned the role of " + workerRole);

        return true;
    }

    private String getLeaderRole(Player player) {
        return RiasCore.roleMapping.keySet().stream()
                .filter(role -> player.hasPermission("group." + role))
                .findFirst()
                .orElse(null);
    }

    private boolean hasWorkerRole(Player player) {
        return player.getEffectivePermissions().stream()
                .anyMatch(perm -> perm.getPermission().startsWith("group.") &&
                        RiasCore.roleMapping.containsValue(perm.getPermission().substring(6)));
    }

    // Method to add a permission to a player
    public void assignRole(Player player, String permission) {
        UserManager userManager = RiasCore.luckPerms.getUserManager();

        User user = userManager.getUser(player.getUniqueId());
        if (user == null) {
            RiasCore.Instance.getLogger().warning("User not found: " + player.getName());
            return;
        }

        Node node = Node.builder(permission).build();
        user.data().add(node);
        userManager.saveUser(user);
    }
}