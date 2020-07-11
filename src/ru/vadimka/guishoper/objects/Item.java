package ru.vadimka.guishoper.objects;

import java.util.Map;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import ru.vadimka.guishoper.Config;
import ru.vadimka.guishoper.Utils;

public class Item {
	
	private ItemStack ITEM;
	private Double PRICE;
	private String PERMISSION;
	private String DESCRIPTION;
	private Integer SLOT;
	
	public Item(ItemStack item, Double price, String description, String permission, Integer slot) {
		ITEM = item;
		PRICE = price;
		PERMISSION = permission;
		SLOT = slot;
		DESCRIPTION = Config.ITEM_DESCRIPTION;
		DESCRIPTION = DESCRIPTION.replace("%PRICE%", String.valueOf(getPrice()));
		DESCRIPTION = DESCRIPTION.replace("%DESCRIPTION%", description);
		DESCRIPTION = DESCRIPTION.replace("%PERMISSION%", permission);
		DESCRIPTION = DESCRIPTION.replace("%LORE%", getLore());
		DESCRIPTION = DESCRIPTION.replace("%ENCHANTS%", getEchantMents());
		DESCRIPTION = Utils.getColors(DESCRIPTION);
	}
	/**
	 * Получить лор предмета
	 * @return String
	 */
	private String getLore() {
		if (!ITEM.hasItemMeta() || !ITEM.getItemMeta().hasLore()) return "";
		String out = "";
		Boolean first = true;
		if (ITEM.getItemMeta().hasLore()) {
			for (String str : ITEM.getItemMeta().getLore()) {
				out+=str+"|";
				if (first)first = false;
				else out+="|";
			}
		}
		return out;
	}
	/**
	 * Получить зачарования предмета
	 * для вывода
	 * @return String
	 */
	private String getEchantMents() {
		if (!ITEM.hasItemMeta() || !ITEM.getItemMeta().hasEnchants()) return "";
		String out = "";
		String message = "";
		Boolean first = true;
		Map<Enchantment, Integer> ench= ITEM.getItemMeta().getEnchants();
		for (Map.Entry<Enchantment, Integer> item : ench.entrySet()) {
			message = Config.ENCHANT_DESCRIPTION;
			DESCRIPTION = DESCRIPTION.replace("%NAME%", item.getKey().getName());
			DESCRIPTION = DESCRIPTION.replace("%LVL%", String.valueOf(item.getValue()));
			out += message;
			if (first)first = false;
			else out+="|";
		}
		return out;
	}
	/**
	 * Имеет displayName
	 * @return Boolean
	 */
	public boolean hasDisplayName() {
		if (!ITEM.hasItemMeta() || !ITEM.getItemMeta().hasDisplayName()) return false;
		else return true;
	}
	/**
	 * Получить имя предмета
	 * @return String
	 */
	public String getName() {
		if (ITEM.hasItemMeta()) {
			ItemMeta itemMeta = ITEM.getItemMeta();
			if (itemMeta.hasDisplayName())
				return itemMeta.getDisplayName();
			else if (itemMeta.hasLocalizedName())
				return itemMeta.getLocalizedName();
		}
		return ITEM.getType().toString();
	}
	/**
	 * Получить описание предмета
	 * @return String
	 */
	public String getDescription() {
		return DESCRIPTION;
	}
	/**
	 * Получить цену предмета
	 * @param shift - нажат ли shift
	 * @return
	 */
	private Double getPrice(Boolean shift) {
		if (shift) {
			return PRICE*64;
		} else {
			return PRICE;
		}
	}
	/**
	 * Получить цену предмета
	 * @return
	 */
	public Double getPrice() {
		return PRICE;
	}
	/**
	 * Необходимы ли привилегии для покупки
	 * @return Boolean
	 */
	private Boolean isPermission() {
		if (PERMISSION.equalsIgnoreCase("")) return false;
		else return true;
	}
	/**
	 * Получить предмет
	 * @param shift
	 * @return ItemStack
	 */
	private ItemStack getItem(Boolean shift) {
		if (shift) {
			ItemStack i = ITEM.clone();
			i.setAmount(64);
			return i;
		} else {
			return ITEM;
		}
	}
	/**
	 * Получить предмет
	 * @return ItemStack
	 */
	public ItemStack getItem() {
		return ITEM;
	}
	/**
	 * Купить предмет
	 * @param player - покупатель
	 * @param shift - Нажат ли shift
	 */
	public void buyItem(Player player, Boolean shift) {
		if (isPermission() && !player.isPermissionSet(PERMISSION)) {
			player.sendMessage(Config.PLUGIN_PREFIX+" "+Config.NOT_PERMITED_ITEM);
			return;
		}
		if (InventoryIsFull(player.getInventory())) {
			player.sendMessage(Config.PLUGIN_PREFIX+" "+Config.INVENTORY_FULL);
			return;
		}
		if (Config.econ != null) {
			if (Config.econ.getBalance(player) >= getPrice(shift)) {
				Config.econ.withdrawPlayer(player, getPrice(shift));
			} else {
				player.sendMessage(Config.PLUGIN_PREFIX+" "+Config.NOT_ENOUGH_MONEY);
				return;
			}
		}
		player.getInventory().addItem(getItem(shift));
		if (!Config.YOU_BUY_ITEM.equalsIgnoreCase("")) {
			String message = Config.YOU_BUY_ITEM;
			message = message.replace("%NAME%", getName());
			message = message.replace("%PRICE%", String.valueOf(getPrice(shift)));
			player.sendMessage(Config.PLUGIN_PREFIX+" "+message);
		}
	}
	/**
	 * Заполнен ли инвентарь
	 * @param inv - инвентарь, который будет проверен
	 * @return Boolean
	 */
	private Boolean InventoryIsFull(Inventory inv) {
		for (ItemStack i: inv.getStorageContents()) {
			if(i == null || i.getType().equals(Material.AIR)) 
				return false;
		}
		return true;
	}
	/**
	 * Получить слой иконки
	 * @return Integer
	 */
	public Integer getSlot() {
		return SLOT;
	}
}
