package ru.vadimka.guishoper;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import ru.vadimka.guishoper.objects.Item;
import ru.vadimka.guishoper.objects.Menu;
import ru.vadimka.guishoper.objects.MenuGUIShoperHolder;

public abstract class Utils {
	/**
	 * Вывести сообщение в консоль
	 * @param msg - сообщение
	 */
	public static void print(String msg) {
		System.out.println(Config.PLUGIN_PREFIX+" "+msg);
	}
	/**
	 * Покрасить сообщение
	 * @param msg - сообщение
	 * @return String
	 */
	public static String getColors(String msg) {
		if (msg == null) return "";
		return msg.replaceAll("&([a-z0-9])", "\u00A7$1");
	}
	/**
	 * Создать главное меню
	 * @return Inventory
	 */
	public static Inventory createMainInventory() {
		Integer rows = (int)Math.ceil((double)Config.menus.size()/9);
		if (rows < 1) rows = 1;
		if (rows > 5) rows = 5;
		Integer cells = (rows+1)*9;
		Inventory inv = Bukkit.createInventory(new MenuGUIShoperHolder("",Config.menusSlots,cells,0,0), cells, Config.SHOP_TITLE);
		for (Map.Entry<String, Menu> i : Config.menus.entrySet()) {
			Menu val = i.getValue();
			if (val.getSlot() > cells-10) continue;
			ItemStack itemN = new ItemStack(Material.valueOf(val.getMaterial()),1);
			ItemMeta meta = itemN.getItemMeta();
			meta.setDisplayName(Utils.getColors(val.getName()));
			List<String> lore = Arrays.asList(Utils.getColors(val.getDescription()).split("\\|"));
			meta.setLore(lore);
			itemN.setItemMeta(meta);
			inv.setItem(val.getSlot(),itemN);
			inv.setItem(cells-Config.SLOT_EXIT, getQuitItem());
		}
		return inv;
	}
	/**
	 * Сознать меню с первой страницей
	 * @param inventory - тип меню
	 * @return Inventory
	 */
	public static Inventory createInventory(String inventory) {
		return createInventory(inventory,1);
	}
	/**
	 * Создать меню
	 * @param inventory - тип меню
	 * @param page - страница меню
	 * @return Inventory
	 */
	public static Inventory createInventory(String inventory,Integer page) {
		if (!Config.menus.containsKey(inventory)) return null;
		Menu menu = Config.menus.get(inventory);
		Integer rows = (int)Math.ceil((double)menu.countItems()/9);
		if (rows < 1) rows = 1;
		if (rows > 5) rows = 5;
		Integer cells = (rows+1)*9;
		HashMap<Integer,String> slots = menu.getSlots(page);
		Integer nextPage = 0;
		if (menu.isNextPage(page))
			nextPage = page+1;
		Integer prevPage = 0;
		if (menu.isPrevPage(page))
			prevPage = page - 1;
		Inventory inv = Bukkit.createInventory(new MenuGUIShoperHolder(inventory,slots,cells,nextPage,prevPage), cells, Utils.getColors(menu.getName()));
		for (Map.Entry<Integer, String> slot : slots.entrySet()) {
			Item item = menu.getItem(slot.getValue());
			ItemStack itemN = new ItemStack(item.getItem());
			ItemMeta meta = itemN.getItemMeta();
			List<String> lore = new ArrayList<String>();
			if (item.getDescription() != ""){
				lore = Arrays.asList(item.getDescription().split("\\|"));
			}
			meta.setLore(lore);
			itemN.setItemMeta(meta);
			inv.setItem(slot.getKey(),itemN);
		}
		if (menu.isNextPage(page))
			inv.setItem(cells-Config.SLOT_PAGE_NEXT,getPageItem(true));
		if (menu.isPrevPage(page))
			inv.setItem(cells-Config.SLOT_PAGE_PREV,getPageItem(false));
		inv.setItem(cells-Config.SLOT_EXIT, getMenuItem());
		return inv;
	}
	/**
	 * Получить иконку выхода
	 * @return ItemStack
	 */
	private static ItemStack getQuitItem() {
		ItemStack i = new ItemStack(Material.ARROW,1);
		ItemMeta m = i.getItemMeta();
		m.setDisplayName(Config.BUTTON_QUIT);
		i.setItemMeta(m);
		return i;
	}
	/**
	 * Получить иконку меню
	 * @return ItemStack
	 */
	private static ItemStack getMenuItem() {
		ItemStack i = new ItemStack(Material.ACACIA_SIGN,1);
		ItemMeta m = i.getItemMeta();
		m.setDisplayName(Config.BUTTON_MENU);
		i.setItemMeta(m);
		return i;
	}
	/**
	 * Получить иконку предмета с ошибкой
	 * @return
	 */
	public static ItemStack getErrorItem() {
		ItemStack i = new ItemStack(Material.DIRT,1);
		ItemMeta m = i.getItemMeta();
		m.setDisplayName("\u00A77Неизвестный предмет");
		i.setItemMeta(m);
		return i;
	}
	/**
	 * Получить иконку страницы
	 * @param next - Следующая?
	 * @return ItemStack
	 */
	private static ItemStack getPageItem(Boolean next) {
		ItemStack i = new ItemStack(Material.COMPASS,1);
		ItemMeta m = i.getItemMeta();
		if (next) m.setDisplayName(Config.BUTTON_PAGE_NEXT);
		if (!next) m.setDisplayName(Config.BUTTON_PAGE_PREV);
		i.setItemMeta(m);
		return i;
	}
	/**
	 * Закрыть все меню
	 */
	public static void closeAllMenus() {
		for (Player player : Config.loader.getServer().getOnlinePlayers()) {
			if (player.getOpenInventory() != null) {
				if (player.getOpenInventory().getTopInventory().getHolder() instanceof MenuGUIShoperHolder || player.getOpenInventory().getBottomInventory().getHolder() instanceof MenuGUIShoperHolder) {
					player.closeInventory();
				}
			}
		}
	}
}
