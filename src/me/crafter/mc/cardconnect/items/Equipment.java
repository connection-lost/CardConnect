package me.crafter.mc.cardconnect.items;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import me.crafter.mc.cardconnect.CardConnect;
import net.md_5.bungee.api.ChatColor;

public class Equipment {
	
	static NamespacedKey KeyMeta = new NamespacedKey(CardConnect.getInstance(), "CardConnect.equipment");
	static NamespacedKey KeyId = new NamespacedKey(CardConnect.getInstance(), "CardConnect.equipment.card.id");
	static NamespacedKey KeyLevel = new NamespacedKey(CardConnect.getInstance(), "CardConnect.equipment.card.level");
	
	public static boolean canEnchant(Card card, ItemStack weapon) {
		if (weapon == null) return false;
		return true;
	}
	
	public static ItemStack applyEnchantment(Card card, ItemStack target) {
		if (!canEnchant(card, target)) return null;
		target = target.clone();
		GenericItem.markItem(target, true);
		
		ItemMeta itemMeta = target.hasItemMeta() ? target.getItemMeta() : Bukkit.getItemFactory().getItemMeta(target.getType());
		
		List<String> lore = itemMeta.hasLore() ? itemMeta.getLore() : new ArrayList<String>();
		lore.add(ChatColor.LIGHT_PURPLE + "卡牌: " + card.getType() + " Lv." + card.getLevel());
		itemMeta.setLore(lore);
		
		PersistentDataContainer data = itemMeta.getPersistentDataContainer();
		data.set(KeyMeta, PersistentDataType.INTEGER, 1);
		data.set(KeyId, PersistentDataType.INTEGER, card.getId());
		data.set(KeyLevel, PersistentDataType.INTEGER, card.getLevel());
		
		target.setItemMeta(itemMeta);
		return target;
	}

}
