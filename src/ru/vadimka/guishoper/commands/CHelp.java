package ru.vadimka.guishoper.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import ru.vadimka.guishoper.Config;

public class CHelp implements CommandExecutor {
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String lable, String[] args) {
		sender.sendMessage(Config.PLUGIN_PREFIX+" "+Config.HELP);
		if (sender.isPermissionSet("guishoper.reload")) {
			sender.sendMessage(Config.HELP_RELOAD);
		}
		if (sender.isPermissionSet("guishoper.use")) {
			sender.sendMessage(Config.HELP_OPEN_SHOP);
		}
		if (sender.isPermissionSet("guishoper.addItem")) {
			sender.sendMessage(Config.HELP_ADD_ITEM);
		}
		return true;
	}
}
