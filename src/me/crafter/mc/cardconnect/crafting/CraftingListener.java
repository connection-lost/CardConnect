package me.crafter.mc.cardconnect.crafting;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.PrepareSmithingEvent;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.SmithingInventory;
import org.bukkit.inventory.meta.ItemMeta;

import me.crafter.mc.cardconnect.items.Card;
import me.crafter.mc.cardconnect.items.CardAPI;
import me.crafter.mc.cardconnect.items.Equipment;
import net.md_5.bungee.api.ChatColor;

public class CraftingListener implements Listener {
	
	private static ItemStack CraftError;
	
	static {
		CraftError = new ItemStack(Material.BARRIER);
		ItemMeta itemMeta = Bukkit.getItemFactory().getItemMeta(Material.BARRIER);
		itemMeta.setDisplayName(ChatColor.RED + "无法合成");
		List<String> lore = new ArrayList<String>();
		lore.add(ChatColor.YELLOW + "请使用两张相同类别与");
		lore.add(ChatColor.YELLOW + "等级的卡牌进行合成。");
		itemMeta.setLore(lore);
		itemMeta.addItemFlags(ItemFlag.HIDE_DYE);
		CraftError.setItemMeta(itemMeta);
	}
	
	// Card combing
	@EventHandler
	public void onCombineCard(PrepareSmithingEvent event) {
		SmithingInventory inventory = event.getInventory();
		if (inventory.getInputEquipment() == null || inventory.getInputMineral() == null) return;
		Card card1 = CardAPI.getCard(inventory.getInputEquipment());
		Card card2 = CardAPI.getCard(inventory.getInputMineral());
		if (card1 != null && card2 != null) {
			if (card1.isSimilar(card2)) {
				event.setResult(Card.createNaiveCard(card1.getType(), card1.getLevel() + 1).getItemStack());
			} else {
				event.setResult(CraftError);
			}
		}
	}
	
	// Enchant item
	@EventHandler
	public void onEnchantEquipment(PrepareSmithingEvent event) {
		SmithingInventory inventory = event.getInventory();
		if (inventory.getInputEquipment() == null || inventory.getInputMineral() == null) return;
		ItemStack target = inventory.getInputEquipment();
		Card card = CardAPI.getCard(inventory.getInputMineral());
		if (card != null && target.getType() != Material.AIR && !CardAPI.isCard(target)) {
			if (Equipment.canEnchant(card, target)) {
				event.setResult(Equipment.applyEnchantment(card, target));
			} else {
				event.setResult(CraftError);
			}
		}
	}
	
	// Prevent taking out craft error item
	@EventHandler(priority=EventPriority.HIGHEST)
	public void onPlayerStealBarrier(InventoryClickEvent event) {
		ItemStack currentItem = event.getCurrentItem();
		if (currentItem != null) {
			if (currentItem.getType() == Material.BARRIER && currentItem.hasItemFlag(ItemFlag.HIDE_DYE)) {
				event.setCancelled(true);
			}
		}
	}
	
}