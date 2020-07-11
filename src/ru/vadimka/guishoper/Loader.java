package ru.vadimka.guishoper;

import org.bukkit.plugin.java.JavaPlugin;

import ru.vadimka.guishoper.commands.*;
import ru.vadimka.guishoper.listeners.*;

public class Loader extends JavaPlugin {
	
	public void onEnable() {
		Config.init(this);
		getServer().getPluginManager().registerEvents(new EItemClicked(), this);
		getCommand("gsreload").setExecutor(new CReload());
		getCommand("gshelp").setExecutor(new CHelp());
		getCommand("gsadditem").setExecutor(new CAddItem());
		getCommand("shop").setExecutor(new COpenShop());
		Utils.print("Плагин запущен.");
	}
	
	public void onDisable() {
		Utils.closeAllMenus();
		Utils.print("Плагин остановлен.");
	}
}
