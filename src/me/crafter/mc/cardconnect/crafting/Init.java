package me.crafter.mc.cardconnect.crafting;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.enchantments.EnchantmentTarget;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.RecipeChoice;
import org.bukkit.inventory.SmithingRecipe;
import org.bukkit.plugin.Plugin;

public class Init {

	public static void init(Plugin plugin) {
		registerSmithingRecipe(plugin, Material.PAPER, Material.PAPER, Material.AIR);
		for (Material material : Material.values()) {
			if (EnchantmentTarget.ARMOR.includes(material) || EnchantmentTarget.WEAPON.includes(material) || 
					EnchantmentTarget.BOW.includes(material) ||	EnchantmentTarget.CROSSBOW.includes(material) || 
					EnchantmentTarget.TOOL.includes(material) || EnchantmentTarget.FISHING_ROD.includes(material)) {
				registerSmithingRecipe(plugin, material, Material.PAPER, Material.AIR);
			}
		}
	}
	
	public static void registerSmithingRecipe(Plugin plugin, Material input1, Material input2, Material output) {
		NamespacedKey key = new NamespacedKey(plugin, input1.name() + "-" + input2.name() + "-" + output.name());
		if (Bukkit.getRecipe(key) != null) Bukkit.removeRecipe(key);
		Bukkit.addRecipe(new SmithingRecipe(key,
				new ItemStack(output),
				new RecipeChoice.MaterialChoice(input1),
				new RecipeChoice.MaterialChoice(input2)));
	}

}
