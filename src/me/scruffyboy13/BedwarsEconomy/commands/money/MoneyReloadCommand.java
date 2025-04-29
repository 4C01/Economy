package me.scruffyboy13.BedwarsEconomy.commands.money;

import java.util.Arrays;
import java.util.List;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import me.scruffyboy13.BedwarsEconomy.BedwarsEconomyMain;
import me.scruffyboy13.BedwarsEconomy.commands.CommandExecutor;
import me.scruffyboy13.BedwarsEconomy.data.ConfigHandler;
import me.scruffyboy13.BedwarsEconomy.runnables.BalanceTopRunnable;
import me.scruffyboy13.BedwarsEconomy.utils.StringUtils;

public class MoneyReloadCommand extends CommandExecutor {

	public MoneyReloadCommand() {
		this.setName("reload");
		this.setPermission("Bedwarseconomy.command.reload");
		this.setUsage(ConfigHandler.getMessage("money.reload.usage"));
		this.setBoth(true);
		this.setLengths(Arrays.asList(1));
		this.setAliases(Arrays.asList("rl"));
	}

	@Override
	public void execute(CommandSender sender, String[] args) {

		BedwarsEconomyMain.getInstance().reloadConfig();
		BedwarsEconomyMain.setSuffixes(ConfigHandler.getSuffixes());
		
		BedwarsEconomyMain.getBalanceTopRunnable().cancel();
		BedwarsEconomyMain.setBalanceTopRunnable(new BalanceTopRunnable());
		BedwarsEconomyMain.getBalanceTopRunnable().start(ConfigHandler.getBalanceTopInterval());
		
		StringUtils.sendConfigMessage(sender, "messages.money.reload.reloaded");
		
	}

	@Override
	public List<String> onTabComplete(CommandSender sender, Command cmd, String label, String[] args) {
		return null;
	}
	
}
