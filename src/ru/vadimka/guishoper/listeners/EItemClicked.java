package ru.vadimka.guishoper.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;

import ru.vadimka.guishoper.Config;
import ru.vadimka.guishoper.Utils;
import ru.vadimka.guishoper.objects.Item;
import ru.vadimka.guishoper.objects.MenuGUIShoperHolder;

public class EItemClicked implements Listener {
	
	@EventHandler
	public void onInventoryClick(InventoryClickEvent e) {
		if (!(e.getWhoClicked() instanceof Player)) {
			return;
		}
		Player p = (Player) e.getWhoClicked();
		if (e.getInventory().getHolder() instanceof MenuGUIShoperHolder) {
			
			Integer slot = e.getRawSlot();
			
			MenuGUIShoperHolder holder = (MenuGUIShoperHolder)e.getInventory().getHolder();
			
			if (slot < 0 || slot > holder.getSize()) return;
			
			e.setCancelled(true);
			if (!p.isPermissionSet("guishoper.use")) return;
			
			String itemAliasInSlot = holder.getItemInSlot(slot);
			
			if (holder.getInventoryName().equalsIgnoreCase("")) {
				
				if (slot == holder.getSize()-Config.SLOT_EXIT) 
					p.closeInventory();
				
				if (itemAliasInSlot == null || itemAliasInSlot == "") return;
				
				if (!Config.menus.containsKey(itemAliasInSlot)) {
					p.sendMessage(Config.ERROR);
					Utils.print("Несуществующее меню: "+itemAliasInSlot);
					return;
				}
				if (Config.menus.get(itemAliasInSlot).isPermission() && !p.isPermissionSet(Config.menus.get(itemAliasInSlot).getPermission())) {
					p.sendMessage(Config.PLUGIN_PREFIX+" "+Config.NOT_PERMITED_MENU);
					return;
				}
				
				Inventory inv = Utils.createInventory(itemAliasInSlot);
				if (inv != null) p.openInventory(inv);
				else {
					p.sendMessage(Config.ERROR);
					Utils.print("Ошибка, при открытии меню: "+itemAliasInSlot);
				}
			} else {
				
				if (slot == holder.getSize()-Config.SLOT_EXIT)
					p.openInventory(Utils.createMainInventory());
				
				if (slot ==  holder.getSize()-Config.SLOT_PAGE_NEXT && holder.getNextPage() != 0) {
					p.openInventory(Utils.createInventory(holder.getInventoryName(),holder.getNextPage()));
				}
				
				if (slot ==  holder.getSize()-Config.SLOT_PAGE_PREV && holder.getPrevPage() != 0) {
					p.openInventory(Utils.createInventory(holder.getInventoryName(),holder.getPrevPage()));
				}
				
				if (itemAliasInSlot == null || itemAliasInSlot == "") return;
				
				Item itemInSlot = Config.menus.get(holder.getInventoryName()).getItem(itemAliasInSlot);
				if (itemInSlot == null) {
					p.sendMessage(Config.ERROR);
					Utils.print("Несуществующий предмет: "+itemAliasInSlot);
					return;
				}
				itemInSlot.buyItem(p,e.isShiftClick());
			}
		}
	}

}
