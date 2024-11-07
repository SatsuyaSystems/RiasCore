package de.satsuya.listeners;

import org.bukkit.Material;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerFishEvent;
import org.bukkit.inventory.ItemStack;

import java.util.Random;

public class PlayerFishListener implements Listener {
    private final Random random = new Random();

    @EventHandler
    public void onPlayerFish(final PlayerFishEvent event) {
        Player player = event.getPlayer();
        if (!player.hasPermission("riascore.fishing")) {
            event.setCancelled(true);
        }
        if(event.getState() == PlayerFishEvent.State.CAUGHT_FISH) {
            if (event.getCaught() instanceof Item caughtItem) {
                ItemStack itemStack = caughtItem.getItemStack();
                if (itemStack.getType() == Material.ENCHANTED_BOOK) {
                    event.setCancelled(true);
                    caughtItem.remove();
                    int xpBottleCount = 2 + random.nextInt(4);
                    ItemStack xpBottles = new ItemStack(Material.EXPERIENCE_BOTTLE, xpBottleCount);
                    player.getWorld().dropItemNaturally(player.getLocation(), xpBottles);
                }
            }
        }
    }
}
