package ru.vadimka.guishoper.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import ru.vadimka.guishoper.Config;
import ru.vadimka.guishoper.Utils;

public class CReload implements CommandExecutor {
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String lable, String[] args) {
		if (sender.isPermissionSet("guishoper.reload")) {
			Utils.closeAllMenus();
			Config.loadMessages();
			Config.loadMenus();
			sender.sendMessage(Config.PLUGIN_PREFIX+" Плагин перезагружен.");
		} else {
			sender.sendMessage(Config.PLUGIN_PREFIX+" "+Config.NOT_PERMITED);
		}
		return true;
	}

}
