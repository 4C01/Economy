package me.scruffyboy13.BedwarsEconomy.commands.money;

import java.util.Arrays;
import java.util.List;

import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.google.common.collect.ImmutableMap;

import me.scruffyboy13.BedwarsEconomy.BedwarsEconomyMain;
import me.scruffyboy13.BedwarsEconomy.commands.CommandExecutor;
import me.scruffyboy13.BedwarsEconomy.data.ConfigHandler;
import me.scruffyboy13.BedwarsEconomy.utils.StringUtils;

public class MoneyGiveCommand extends CommandExecutor {

	public MoneyGiveCommand() {
		this.setName("give");
		this.setPermission("Bedwarseconomy.command.give");
		this.setUsage(ConfigHandler.getMessage("money.give.usage"));
		this.setBoth(true);
		this.setLengths(Arrays.asList(3));
	}
	
	@Override
	public void execute(CommandSender sender, String[] args) {
		
		List<OfflinePlayer> others = BedwarsEconomyMain.getPlayersFromString(sender, args[1]);
		
		if (others.isEmpty() && !args[1].equals("@a")) {
			StringUtils.sendConfigMessage(sender, "messages.money.give.otherDoesntExist", ImmutableMap.of(
					"%player%", args[1]));
			return;
		}
		
		double amount = 0;
		try {
			amount = BedwarsEconomyMain.getAmountFromString(args[2]);
		}
		catch (NumberFormatException e){
			StringUtils.sendConfigMessage(sender, "messages.money.give.invalidAmount", ImmutableMap.of(
					"%amount%", args[2]));
			return;
		}
		if (amount < 0) {
			StringUtils.sendConfigMessage(sender, "messages.money.give.invalidAmount", ImmutableMap.of(
					"%amount%", args[2]));
			return;
		}
		
		int total = 0;
		boolean failed = false;
		
		for (OfflinePlayer other : others) {
		
			if (!BedwarsEconomyMain.getEco().hasAccount(other.getUniqueId())) {
				StringUtils.sendConfigMessage(sender, "messages.money.give.otherNoAccount", ImmutableMap.of(
						"%player%", other.getName()));
				failed = true;
				continue;
			}
			
			BedwarsEconomyMain.getEco().deposit(other.getUniqueId(), amount);
		
			if (other instanceof Player) {
				if (!(sender instanceof Player && ((Player) sender).equals((Player) other))) {
					StringUtils.sendConfigMessage((Player) other, "messages.money.give.received", ImmutableMap.of(
							"%amount%", BedwarsEconomyMain.format(amount)));
				}
			}
			
			total += 1;
			
		}
		
		if (others.size() == 1) {
			
			if (!failed) {
			
				OfflinePlayer other = others.get(0);
				StringUtils.sendConfigMessage(sender, "messages.money.give.sent", ImmutableMap.of(
						"%amount%", BedwarsEconomyMain.format(amount),
						"%player%", other.getName()));
			
			}
			
		}
		
		else {
		
			StringUtils.sendConfigMessage(sender, "messages.money.give.sentMultiple", ImmutableMap.of(
					"%total%", total + "",
					"%amount%", BedwarsEconomyMain.format(amount)
					));
			
		}
		
	}

	@Override
	public List<String> onTabComplete(CommandSender sender, Command cmd, String label, String[] args) {
		return null;
	}
	
}
