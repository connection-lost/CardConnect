package me.crafter.mc.cardconnect.items;

import javax.annotation.Nonnull;

import org.bukkit.inventory.ItemStack;

public class BrokenAPI {
	
	/**
	 * @param itemStack
	 * @return Whether this item still have durability. Always return false for non-eligible items.
	 */
	public static boolean hasDurability(@Nonnull ItemStack itemStack) {
		return GenericItem.getDurability(itemStack) > 0;
	}
	
	/**
	 * @param itemStack
	 * @param broken
	 */
	public static void setDurability() {
		
	}
	
	public static boolean dealDamage(@Nonnull ItemStack itemStack, int damage) {
		return GenericItem.dealDamage(itemStack, damage);
	}
	
	/**
	 * @param itemStack
	 * @return
	 */
	public static int getDurability(@Nonnull ItemStack itemStack) {
		return GenericItem.getDurability(itemStack);
	}

}
