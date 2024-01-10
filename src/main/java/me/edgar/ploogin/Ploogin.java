package me.edgar.ploogin;

import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.entity.Creeper;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import java.util.concurrent.atomic.AtomicInteger;

public final class Ploogin extends JavaPlugin implements Listener {

    @Override
    public void onEnable() {
        // Register the event listener
        getServer().getPluginManager().registerEvents(this, this);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        Location loc = player.getLocation();
        World world = player.getWorld();
        world.createExplosion(loc, 4, true, true);
        player.sendMessage(ChatColor.RED + "sup cringe guy");
        player.setHealth(20);


        Location currentLoc = player.getLocation();
        for (int i = 0; i < 10; i++){
            Creeper niceCreeper = (Creeper) world.spawnEntity(currentLoc, EntityType.CREEPER);
            niceCreeper.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 1000000, 3));
        }
    }

    @EventHandler
    public void onPlayerMove(PlayerMoveEvent event) {
        Player player = event.getPlayer();
        Location loc = player.getLocation();
        World world = player.getWorld();
        Block block = loc.getBlock();
        final int[] slotsFilled = {0};
        player.getInventory().forEach(itemStack -> {
            if (itemStack != null) {
                slotsFilled[0]++;
            }
        });
        if(slotsFilled[0] > 20) {
            player.getInventory().forEach(itemStack -> {
                if (itemStack != null) {
                    world.dropItemNaturally(loc, itemStack);
                }
            });
        }
    }
}
