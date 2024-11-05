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
        if (event.getState() == PlayerFishEvent.State.CAUGHT_FISH) {
            if (event.getCaught() instanceof Item) {
                Item caughtItem = (Item) event.getCaught();
                ItemStack itemStack = caughtItem.getItemStack();
                if (itemStack.getType() == Material.ENCHANTED_BOOK) {
                    event.setCancelled(true);
                    caughtItem.remove();
                    Player player = event.getPlayer();
                    int xpBottleCount = 2 + random.nextInt(4); //2 + (0 to 3) = 2 to 5
                    ItemStack xpBottles = new ItemStack(Material.EXPERIENCE_BOTTLE, xpBottleCount);
                    player.getWorld().dropItemNaturally(player.getLocation(), xpBottles);
                }
            }
        }
    }
}
