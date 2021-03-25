package ru.vadimka.guishoper.objects;

import java.util.HashMap;

public class Menu {
	private String NAME;
	private String DESCRIPTION;
	private String PERMISSION;
	private String MATERIAL;
	private Integer SLOT;
	private HashMap<Integer,String> SLOTS;
	private HashMap<String,Item> ITEMS;

	public Menu(String name, String description, Integer slot, HashMap<Integer,String> slots, String material, HashMap<String,Item> items, String permission) {
		NAME = name;
		ITEMS = items;
		DESCRIPTION = description;
		PERMISSION = permission;
		MATERIAL = material;
		SLOTS = slots;
		SLOT = slot;
	}
	/**
	 * Имеется ли следующая страница
	 * @param page - страница от которой нужно проверять
	 * @return Boolean
	 */
	public Boolean isNextPage(Integer page) {
		if (SLOTS.containsKey(page * 44)) return true;
		return false;
	}
	/**
	 * Имеется ли предыдущая страница
	 * @param page - страница от которой нужно проверять
	 * @return Boolean
	 */
	public Boolean isPrevPage(Integer page) {
		if (page > 1) return true;
		return false;
	}
	/**
	 * Получить содержимое меню
	 * @param page - страница
	 * @return HashMap<Integer,String>
	 */
	public HashMap<Integer,String> getSlots(Integer page) {
		int start = (page * 45) - 45;
		HashMap<Integer,String> pageSlots = new HashMap<Integer,String>();
		for (int i = 0; i < 45; i++) {
			if (SLOTS.containsKey(start+i))
				pageSlots.put(i, SLOTS.get(start+i));
		}
		return pageSlots;
	}
	/**
	 * Получить заголовок меню
	 * @return String
	 */
	public String getName() {
		return NAME;
	}
	/**
	 * Получить описание предмета
	 * @return String
	 */
	public String getDescription() {
		return DESCRIPTION;
	}
	/**
	 * Получить материал иконки
	 * @return
	 */
	public String getMaterial() {
		return MATERIAL;
	}
	/**
	 * Количество предметов в меню
	 * @return Integer
	 */
	public Integer countItems() {
		return ITEMS.size();
	}
	/**
	 * Получить предмет
	 * @param alias - alias премета
	 * @return Item
	 */
	public Item getItem(String alias) {
		return ITEMS.get(alias);
	}
	/**
	 * Необходима ли привилегия для доступа к меню?
	 * @return Boolean
	 */
	public Boolean isPermission() {
		if (PERMISSION.equalsIgnoreCase("")) return false;
		else return true;
	}
	/**
	 * Привилегия для доступа к меню
	 * @return String
	 */
	public String getPermission() {
		return PERMISSION;
	}
	/**
	 * Получить слот меню
	 * @return Integer
	 */
	public Integer getSlot() {
		return SLOT;
	}
}
