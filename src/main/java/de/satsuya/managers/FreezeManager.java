package de.satsuya.managers;

import org.bukkit.entity.Player;

import java.util.HashSet;
import java.util.Set;

public class FreezeManager {

    private final Set<Player> frozenPlayers = new HashSet<>();

    public void freezePlayer(Player player) {
        frozenPlayers.add(player);
    }

    public void unfreezePlayer(Player player) {
        frozenPlayers.remove(player);
    }

    public boolean isFrozen(Player player) {
        return frozenPlayers.contains(player);
    }
}