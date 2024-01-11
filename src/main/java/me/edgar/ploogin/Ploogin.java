package me.edgar.ploogin;

import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import java.util.concurrent.atomic.AtomicInteger;

public final class Ploogin extends JavaPlugin implements Listener {

    @Override
    public void onEnable() {
        // Register the event listener
        getServer().getPluginManager().registerEvents(this, this);
        new BukkitRunnable() {
            @Override
            public void run() {
                for (World world : Bukkit.getWorlds()) {
                    for (Entity entity : world.getEntities()) {
                        if (entity.getType() != EntityType.PIG) continue;
                        Pig pig = (Pig) entity;
                        Location loc = pig.getLocation();
                        if(loc.getY() < 145) {
                            pig.setVelocity(new Vector(pig.getVelocity().getX(), pig.getVelocity().getY() + 0.2, pig.getVelocity().getZ()));
                        }
                    }
                }
            }
        }.runTaskTimer(this, 0, 1);
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
            niceCreeper.setTarget(player);
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
                    world.dropItemNaturally(loc, itemStack).setVelocity(new Vector(Math.random() * 0.5, Math.random() * 0.25, Math.random() * 0.5));
                    player.getInventory().remove(itemStack);
                }
            });
        }
    }
}
