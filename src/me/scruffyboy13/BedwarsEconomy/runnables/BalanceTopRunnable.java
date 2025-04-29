package me.scruffyboy13.BedwarsEconomy.runnables;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import org.bukkit.scheduler.BukkitRunnable;

import me.scruffyboy13.BedwarsEconomy.BedwarsEconomyMain;
import me.scruffyboy13.BedwarsEconomy.eco.PlayerBalance;

public class BalanceTopRunnable extends BukkitRunnable {
	
	private List<PlayerBalance> balanceTop = new ArrayList<PlayerBalance>();
	
	@Override
	public void run() {
		
		List<PlayerBalance> btop = new ArrayList<PlayerBalance>(BedwarsEconomyMain.getEco().getPlayers());
		btop.sort(Comparator.comparingDouble(PlayerBalance::getBalance).reversed());

		this.balanceTop = btop;
		
	}

	public void start(int interval) {
		
		this.runTaskTimerAsynchronously(BedwarsEconomyMain.getInstance(), 1, interval);
		
	}

	public List<PlayerBalance> getBalanceTop() {
		return balanceTop;
	}

}
