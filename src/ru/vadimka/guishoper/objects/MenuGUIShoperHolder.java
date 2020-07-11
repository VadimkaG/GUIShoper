package ru.vadimka.guishoper.objects;

import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;

public class MenuGUIShoperHolder implements InventoryHolder {
	
	private String INVENTORY;
	private HashMap<Integer,String> ITEMS;
	private Integer SIZE;
	private Integer NEXT_PAGE;
	private Integer PREV_PAGE;
	
	public MenuGUIShoperHolder(String inventoryName, HashMap<Integer,String> slots, Integer size, Integer nextPage, Integer prevPage) {
		INVENTORY = inventoryName;
		ITEMS = slots;
		SIZE = size;
		NEXT_PAGE = nextPage;
		PREV_PAGE = prevPage;
	}
	
	@Override
	public Inventory getInventory() {
		return Bukkit.createInventory(null, 9);
	}
	/**
	 * Получить предмет
	 * @param slot - слот, в котором находится предмет
	 * @return
	 */
	public String getItemInSlot(Integer slot) {
		return ITEMS.get(slot);
	}
	/**
	 * Получить имя меню
	 * @return String
	 */
	public String getInventoryName() {
		return INVENTORY;
	}
	/**
	 * Получить размер меню
	 * @return Integer
	 */
	public Integer getSize() {
		return SIZE;
	}
	/**
	 * Получить следующую страницу
	 * @return Integer
	 */
	public Integer getNextPage() {
		return NEXT_PAGE;
	}
	/**
	 * Получить предыдущую страницу
	 * @return Integer
	 */
	public Integer getPrevPage() {
		return PREV_PAGE;
	}

}
