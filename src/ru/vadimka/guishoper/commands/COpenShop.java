package ru.vadimka.guishoper.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import ru.vadimka.guishoper.Config;
import ru.vadimka.guishoper.Utils;

public class COpenShop implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (sender instanceof Player) {
			Player player = (Player) sender;
			if (sender.isPermissionSet("guishoper.use")) {
				Inventory inv = Utils.createMainInventory();
				player.openInventory(inv);
			} else {
				sender.sendMessage(Config.PLUGIN_PREFIX+" "+Config.NOT_PERMITED);
			}
		} else {
			sender.sendMessage(Config.PLUGIN_PREFIX+" Эта команда только для игроков");
		}
		return true;
	}

}
