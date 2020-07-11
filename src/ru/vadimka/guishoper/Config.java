package ru.vadimka.guishoper;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.RegisteredServiceProvider;

import net.milkbowl.vault.economy.Economy;
import ru.vadimka.guishoper.objects.Item;
import ru.vadimka.guishoper.objects.Menu;

public abstract class Config {
	
	public static String PLUGIN_PREFIX = "[GUIShoper]";
	
	public static String HELP = "Помощь по плагину:";
	public static String ERROR = "Обнаружена ошибка (см. консоль)";
	public static String HELP_RELOAD = "%COMMAND% - Перезагрузка плагина.";
	public static String HELP_OPEN_SHOP = "%COMMAND% - Открывает магазин.";
	public static String HELP_ADD_ITEM = "%COMMAND% - Сохранят предмет в конфиг.";
	
	public static String NOT_PERMITED = "Вам не разрешена данная операция";
	public static String NOT_PERMITED_ITEM = "Вам нельзя покупать этот предмет";
	public static String NOT_PERMITED_MENU = "Вам нельзя открывать это меню";
	
	public static String YOU_BUY_ITEM = "Вы купили предмет %NAME%";
	public static String ITEM_DESCRIPTION = "Цена: %PRICE%|%DESCRIPTION%";
	public static String ENCHANT_DESCRIPTION = "%NAME%: %LVL%";
	public static String NOT_ENOUGH_MONEY = "У вас недостаточно денег";
	public static String INVENTORY_FULL = "У вас нет места в инвенторе";
	
	public static String SHOP_TITLE = "Магазин";
	public static String BUTTON_MENU = "В меню";
	public static String BUTTON_QUIT = "Выход";
	public static String BUTTON_PAGE_NEXT = "Следующая";
	public static String BUTTON_PAGE_PREV = "Предыдущая";
	
	public static Integer SLOT_EXIT = 1;
	public static Integer SLOT_PAGE_NEXT = 3;
	public static Integer SLOT_PAGE_PREV = 5;
	
	public static Loader loader;
	public static String configDir = "";
	public static Economy econ = null;
	public static HashMap<String,Menu> menus;
	public static HashMap<Integer,String> menusSlots;
	
	// Инициализация
	public static void init(Loader l) {
		loader = l;
		configDir = "plugins/"+l.getDescription().getName();
		setupEconomy();
		loadMessages();
		loadMenus();
	}
	
	private static boolean setupEconomy() {
		if (loader.getServer().getPluginManager().getPlugin("Vault") == null) {
			Utils.print("Плагин vault не найден.");
			return false;
		}
		RegisteredServiceProvider<Economy> rsp = loader.getServer().getServicesManager().getRegistration(Economy.class);
		if (rsp == null) {
			Utils.print("Не удалось загрузить экономику.");
			return false;
		}
		econ = rsp.getProvider();
		return econ != null;
	}
	
	// Локали
	public static void loadMessages() {
		File f = new File(configDir+"/messages.yml");
		YamlConfiguration y = YamlConfiguration.loadConfiguration(f);
		if (!f.exists()) saveDefaultMessages(f,y);
		if (y.getString("pluginPrefix") != null)
			PLUGIN_PREFIX = Utils.getColors(y.getString("pluginPrefix"));
		if (y.getString("error") != null)
			ERROR = Utils.getColors(y.getString("error"));
		if (y.getString("notPermited.Custom") != null)
			NOT_PERMITED = Utils.getColors(y.getString("notPermited.Custom"));
		if (y.getString("notPermited.Item") != null)
			NOT_PERMITED_ITEM = Utils.getColors(y.getString("notPermited.Item"));
		if (y.getString("notPermited.Menu") != null)
			NOT_PERMITED_MENU = Utils.getColors(y.getString("notPermited.Menu"));
		if (y.getString("help.title") != null)
			HELP = Utils.getColors(y.getString("help.title"));
		if (y.getString("help.Reload") != null)
			HELP_RELOAD = Utils.getColors(y.getString("help.Reload"));
		if (y.getString("help.OpenShop") != null)
			HELP_OPEN_SHOP = Utils.getColors(y.getString("help.OpenShop"));
		if (y.getString("help.AddItem") != null)
			HELP_ADD_ITEM = Utils.getColors(y.getString("help.AddItem"));
		if (y.getString("item.youBuyItem") != null)
			YOU_BUY_ITEM = Utils.getColors(y.getString("item.youBuyItem"));
		if (y.getString("item.Description") != null)
			ITEM_DESCRIPTION = Utils.getColors(y.getString("item.Description"));
		if (y.getString("item.enchantDescription") != null)
			ENCHANT_DESCRIPTION = Utils.getColors(y.getString("item.enchantDescription"));
		if (y.getString("item.notEnoughMoney") != null)
			NOT_ENOUGH_MONEY = Utils.getColors(y.getString("item.notEnoughMoney"));
		if (y.getString("item.notEnoughMoney") != null)
			INVENTORY_FULL = Utils.getColors(y.getString("item.inventoryFull"));
		if (y.getString("menu.shopTitle") != null)
			SHOP_TITLE = Utils.getColors(y.getString("menu.shopTitle"));
		if (y.getString("menu.button.Menu") != null)
			BUTTON_MENU = Utils.getColors(y.getString("menu.button.Menu"));
		if (y.getString("menu.button.Quit") != null)
			BUTTON_QUIT = Utils.getColors(y.getString("menu.button.Quit"));
		if (y.getString("menu.button.NextPage") != null)
			BUTTON_PAGE_NEXT = Utils.getColors(y.getString("menu.button.NextPage"));
		if (y.getString("menu.button.PrevPage") != null)
			BUTTON_PAGE_PREV = Utils.getColors(y.getString("menu.button.PrevPage"));

		// Заменяем выражения
		HELP_RELOAD = HELP_RELOAD.replace("%COMMAND%", "/gsreload");
		HELP_OPEN_SHOP = HELP_OPEN_SHOP.replace("%COMMAND%", "/shop");
		HELP_ADD_ITEM = HELP_ADD_ITEM.replace("%COMMAND%", "/gsadditem");
		// ===================
	}
	
	private static void saveDefaultMessages(File f,YamlConfiguration y) {
		y.options().copyDefaults(true);
		y.addDefault("pluginPrefix", PLUGIN_PREFIX);
		y.addDefault("error", ERROR);
		y.addDefault("notPermited.Custom", NOT_PERMITED);
		y.addDefault("notPermited.Item", NOT_PERMITED_ITEM);
		y.addDefault("notPermited.Menu", NOT_PERMITED_MENU);
		y.addDefault("help.title", HELP);
		y.addDefault("help.Reload", HELP_RELOAD);
		y.addDefault("help.OpenShop", HELP_OPEN_SHOP);
		y.addDefault("help.AddItem", HELP_ADD_ITEM);
		y.addDefault("item.youBuyItem", YOU_BUY_ITEM);
		y.addDefault("item.Description", ITEM_DESCRIPTION);
		y.addDefault("item.enchantDescription", ENCHANT_DESCRIPTION);
		y.addDefault("item.notEnoughMoney", NOT_ENOUGH_MONEY);
		y.addDefault("item.inventoryFull", INVENTORY_FULL);
		y.addDefault("menu.shopTitle", SHOP_TITLE);
		y.addDefault("menu.button.Menu", BUTTON_MENU);
		y.addDefault("menu.button.Quit", BUTTON_QUIT);
		y.addDefault("menu.button.NextPage", BUTTON_PAGE_NEXT);
		y.addDefault("menu.button.PrevPage", BUTTON_PAGE_PREV);
		try {y.save(f);} catch (IOException e) {
			Utils.print("Ошибка сохранения messages.yml.");
		}
	}
	
	@SuppressWarnings("unchecked")
	public static void loadMenus() {
		File f = new File(configDir+"/menus.yml");
		YamlConfiguration y = YamlConfiguration.loadConfiguration(f);
		if (!f.exists()) saveDefaultMenus(f,y);

		File fi = new File(configDir+"/items/default.yml");
		if (!fi.exists()) {
			YamlConfiguration yi = YamlConfiguration.loadConfiguration(fi);
			saveDefaultItems(fi,yi);
		}
		
		// Новая система конфига предметов
		File dirItems = new File(configDir+"/items");
		List<YamlConfiguration> ConfigItems = new ArrayList<YamlConfiguration>();
		for (File tmp_f : dirItems.listFiles()) {
			ConfigItems.add(YamlConfiguration.loadConfiguration(tmp_f));
		}
		// ===============================
		
		try {
			Integer MenuSlot = 0;
			menus = new HashMap<String,Menu>();
			menusSlots = new HashMap<Integer,String>();
			
			List<String> menuA = (List<String>) y.getList("enabledMenus");
			if (menuA == null) {
				Utils.print("Ошибка чтения активных меню. Подгрузка меню прервана.");
				return;
			}
			
			
			for (String menu : menuA) {
				
				String menuName = y.getString(menu+".name");
				if (menuName == null){
					Utils.print("[menus][name] Ошибка "+menu);
					menuName="";
				}
				
				String menuDescription = y.getString(menu+".description");
				if (menuDescription == null){
					Utils.print("[menus][description] Ошибка "+menu);
					menuDescription="";
				}
				
				String menuPermission = y.getString(menu+".permission");
				if (menuPermission == null){
					Utils.print("[menus][permission] Ошибка "+menu);
					menuPermission="";
				}
				
				String menuMaterial = y.getString(menu+".material").toUpperCase();
				Short menuDamage = (short)y.getInt(menu+".damage");
				try {
					Material.valueOf(menuMaterial);
				} catch (Exception e) {
					Utils.print("[menus][material] Ошибка "+menu+": "+menuMaterial);
					menuMaterial = "STONE";
					menuDamage = 0;
				}
				
				List<String> menuItemsT = (List<String>) y.getList(menu+".items");
				if (menuItemsT == null) {
					Utils.print("[menus][items] Ошибка "+menu);
					continue;
				}
				
				Integer slot = 0;
				HashMap<Integer,String> slots = new HashMap<Integer,String>();
				HashMap<String,Item> menuItems = new HashMap<String,Item>();
				for (String item : menuItemsT) {
					
					/*String itemDescription = yi.getString(item+".description");
					if (itemDescription==null){
						Utils.print("[items][description] Ошибка "+item);
						itemDescription = "";
					}
					
					Double itemPrice = yi.getDouble(item+".price");
					if (itemPrice < 0){
						Utils.print("[items][price] Ошибка "+item);
						itemPrice = 0d;
					}
					
					String itemPermission = yi.getString(item+".permission");
					if (itemPermission==null){
						Utils.print("[items][permission] Ошибка "+item);
						itemPermission = "";
					}
					
					ItemStack itemItem = yi.getItemStack(item+".item");
					if (itemItem == null) {
						Utils.print("[items][item] Ошибка "+item);
						itemItem = Utils.getErrorItem();
					}*/
					
					slots.put(slot, item);
					//menuItems.put(item, new Item(itemItem,itemPrice,itemDescription,itemPermission,slot));
					menuItems.put(item, getItem(ConfigItems,item,slot));
					slot++;
				}
				menus.put(menu, new Menu(menuName,menuDescription,MenuSlot,slots,menuMaterial,menuDamage,menuItems,menuPermission));
				menusSlots.put(MenuSlot, menu);
				MenuSlot++;
			}
		} catch (Exception e) {
			Utils.print("Ошибка чтения конфига.");
			menus = new HashMap<String,Menu>();
			menusSlots = new HashMap<Integer,String>();
		}
	}
	
	private static void saveDefaultMenus(File f,YamlConfiguration y) {
		y.options().copyDefaults(true);
		List<String> arr = new ArrayList<String>();
		arr.add("testmenu");
		y.addDefault("enabledMenus", arr);
		y.addDefault("testmenu.name", "Тестовое меню");
		y.addDefault("testmenu.description", "Это|тестовое|меню");
		y.addDefault("testmenu.material", "STONE");
		y.addDefault("testmenu.damage", 0);
		y.addDefault("testmenu.permission", "");
		List<String> arr2 = new ArrayList<String>();
		arr2.add("testItem");
		y.addDefault("testmenu.items", arr2);
		try {y.save(f);} catch (IOException e) {
			Utils.print("Ошибка сохранения menus.yml.");
		}
	}
	
	private static void saveDefaultItems(File f,YamlConfiguration y) {
		File folder = new File(configDir+"/items");
		if (!folder.exists()) folder.mkdir();
		y.options().copyDefaults(true);
		y.addDefault("testItem.description", "Это|тестовый|предмет");
		y.addDefault("testItem.price", 2d);
		y.addDefault("testItem.permission", "");
		y.addDefault("testItem.item", Utils.getErrorItem());
		try {y.save(f);} catch (IOException e) {
			Utils.print("Ошибка сохранения items.yml.");
		}
	}
	
	private static Item getItem(List<YamlConfiguration> configs, String alias, Integer slot) {
		String itemDescription = "";
		Double itemPrice = 0d;
		String itemPermission = "";
		ItemStack itemItem = null;
		for (YamlConfiguration y : configs) {
			if (y.getString(alias) == null) continue;
			itemDescription = y.getString(alias+".description");
			itemPrice = y.getDouble(alias+".price");
			itemPermission = y.getString(alias+".permission");
			itemItem = y.getItemStack(alias+".item");
			break;
		}
		if (itemDescription==null){
			Utils.print("[items][description] Ошибка "+alias);
			itemDescription = "";
		}
		if (itemPrice < 0){
			Utils.print("[items][price] Ошибка "+alias);
			itemPrice = 0d;
		}
		if (itemPermission==null){
			Utils.print("[items][permission] Ошибка "+alias);
			itemPermission = "";
		}
		if (itemItem == null) {
			Utils.print("[items][item] Ошибка "+alias);
			itemItem = Utils.getErrorItem();
		}
		return new Item(itemItem,itemPrice,itemDescription,itemPermission,slot);
	}
	
	public static void saveItem(String alias, String description, Double price,ItemStack i) {
		File f = new File(configDir+"/items/default.yml");
		YamlConfiguration y = YamlConfiguration.loadConfiguration(f);
		if (!f.exists()) saveDefaultItems(f,y);
		y.set(alias+".description", description);
		y.set(alias+".price", price);
		y.set(alias+".permission", "");
		y.set(alias+".item", i);
		try {y.save(f);} catch (IOException e) {
			Utils.print("Ошибка сохранения items.yml.");
		}
	}

}
