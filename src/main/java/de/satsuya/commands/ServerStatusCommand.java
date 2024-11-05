package de.satsuya.commands;

import de.satsuya.managers.ServerManager;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ServerStatusCommand implements PluginCommand{
    private final ServerManager serverManager;

    public ServerStatusCommand(ServerManager serverManager) {
        this.serverManager = serverManager;
    }
    @Override
    public String getName() {
        return "serverstatus";
    }
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length == 0) {
            sender.sendMessage("§cUsage: /serverstatus <open|close|info>");
            return false;
        }
        String option = args[0].toLowerCase();
        switch (option) {
            case "open":
                handleOpen(sender);
                break;
            case "close":
                handleClose(sender);
                break;
            case "info":
                handleInfo(sender);
                break;
            default:
                sender.sendMessage("§cInvalid option. Use /serverstatus <open|close|info>");
                return false;
        }
        return true;
    }
    private void handleOpen(CommandSender sender) {
        serverManager.openServer();
        sender.sendMessage("The server is now open.");
    }
    private void handleClose(CommandSender sender) {
        serverManager.closeServer();
        for (Player player: Bukkit.getOnlinePlayers()) {
            if (!player.hasPermission("riascore.allowlist")) {
                player.kickPlayer("Der Server wurde geschlossen!");
            }
        }
        sender.sendMessage("The server is now closed.");
    }
    private void handleInfo(CommandSender sender) {
        if (serverManager.isServerActive()) {
            sender.sendMessage("The server is open");
        } else {
            sender.sendMessage("The server is closed");
        }
    }
}
