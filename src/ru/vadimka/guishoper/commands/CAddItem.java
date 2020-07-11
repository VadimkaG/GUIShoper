package ru.vadimka.guishoper.commands;

import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import ru.vadimka.guishoper.Config;

public class CAddItem implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (sender instanceof Player) {
			if (sender.isPermissionSet("guishoper.addItem")) {
				Player player = (Player) sender;
				if (player.getInventory().getItemInMainHand().getType() == Material.AIR || player.getInventory().getItemInMainHand().getType() == null) {
					sender.sendMessage(Config.PLUGIN_PREFIX+" Вы должны держать предмет в главной руке");
					return true;
				}
				if (args.length == 3) {
					Config.saveItem(args[0], args[2], Double.parseDouble(args[1]), player.getInventory().getItemInMainHand());
					sender.sendMessage(Config.PLUGIN_PREFIX+" Предмет сохранен");
				} else if (args.length == 2) {
					Config.saveItem(args[0], "", Double.parseDouble(args[1]), player.getInventory().getItemInMainHand());
					sender.sendMessage(Config.PLUGIN_PREFIX+" Предмет сохранен");
				} else return false;
			} else {
				sender.sendMessage(Config.PLUGIN_PREFIX+" "+Config.NOT_PERMITED);
			}
		} else {
			sender.sendMessage(Config.PLUGIN_PREFIX+" Эта команда только для игроков");
		}
		return true;
	}

}
