package de.satsuya.listeners;

import de.satsuya.RiasCore;
import de.satsuya.managers.ServerManager;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;


public class PlayerJoinListener implements Listener {

    @EventHandler
    public void onPlayerJoin(final PlayerJoinEvent event) {
        if (!ServerManager.serverActive) {
            if (event.getPlayer().hasPermission("RiasCore.allowlist")) {
                return;
            } else {
                Bukkit.getScheduler().runTaskLater(RiasCore.getPlugin(RiasCore.class), () -> {
                    event.getPlayer().kickPlayer("Der Server ist grade Geschlossen!");
                }, 30L);
            }
        }
    }
}
