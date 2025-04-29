package me.scruffyboy13.BedwarsEconomy.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.scheduler.BukkitRunnable;

import me.scruffyboy13.BedwarsEconomy.BedwarsEconomyMain;

public class PlayerJoinListener implements Listener {

	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent event) {
		
		Player player = event.getPlayer();
		
		if (!BedwarsEconomyMain.getEco().hasAccount(player.getUniqueId())) {
			new BukkitRunnable() {

				@Override
				public void run() {
					BedwarsEconomyMain.getEco().createAccount(player.getUniqueId());
				}
			}.runTaskAsynchronously(BedwarsEconomyMain.getInstance());
		}
		
	}
	
}
