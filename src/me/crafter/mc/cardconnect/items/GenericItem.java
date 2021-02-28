package me.crafter.mc.cardconnect.items;

import javax.annotation.Nonnull;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import me.crafter.mc.cardconnect.CardConnect;

public class GenericItem {

	static NamespacedKey KeyItem = new NamespacedKey(CardConnect.getInstance(), "CardConnect");
	static NamespacedKey KeyBroken = new NamespacedKey(CardConnect.getInstance(), "CardConnect.broken");
	
	public static ItemStack markItem(@Nonnull ItemStack itemStack, boolean canBreak) {
		if (itemStack.getType() == Material.AIR) return itemStack;
		ItemMeta itemMeta = itemStack.hasItemMeta() ? itemStack.getItemMeta() : Bukkit.getItemFactory().getItemMeta(itemStack.getType());
		PersistentDataContainer data = itemMeta.getPersistentDataContainer();
		data.set(KeyItem, PersistentDataType.INTEGER, 1);
		if (canBreak) {
			data.set(KeyBroken, PersistentDataType.INTEGER, 1);
		}
		itemStack.setItemMeta(itemMeta);
		return itemStack;
	}
	
	public static boolean isItem(@Nonnull ItemStack itemStack) {
		if (!itemStack.hasItemMeta()) return false;
		if (!itemStack.getItemMeta().getPersistentDataContainer().getKeys().contains(KeyItem)) return false;
		return true;
	}
	
	public static int getDurability(@Nonnull ItemStack itemStack) {
		if (!itemStack.hasItemMeta()) return 0;
		PersistentDataContainer data = itemStack.getItemMeta().getPersistentDataContainer();
		if (data.getKeys().contains(KeyBroken)) {
			return data.get(KeyBroken, PersistentDataType.INTEGER);
		} else {
			return 0;
		}
	}
	
	public static boolean dealDamage(@Nonnull ItemStack itemStack, int amount) {
		if (!itemStack.hasItemMeta()) return false;
		PersistentDataContainer data = itemStack.getItemMeta().getPersistentDataContainer();
		if (data.getKeys().contains(KeyBroken)) {
			int currentDamage = data.get(KeyBroken, PersistentDataType.INTEGER);
			if (currentDamage > 0) {
				data.set(KeyBroken, PersistentDataType.INTEGER, currentDamage - amount);
				return true;
			} else {
				return false;
			}
		} else {
			return false;
		}
	}

}
