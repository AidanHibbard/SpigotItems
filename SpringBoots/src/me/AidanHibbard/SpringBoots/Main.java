package me.AidanHibbard.SpringBoots;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin implements Listener{
	@Override
	public void onEnable() {
		//Server Startup / Reloads
		this.getServer().getPluginManager().registerEvents(this, this);
	}
	@Override
	public void onDisable() {
		//Shutdown / Reloads
	}
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (label.equalsIgnoreCase("SpringBoots")) {
			if (!(sender instanceof Player)) {
				sender.sendMessage("Console got no feet");
				return true;
			}
			Player player = (Player) sender;
			if (player.getInventory().firstEmpty() == -1) {
				// inventory full
				Location loc = player.getLocation();
				World world = player.getWorld();
				world.dropItemNaturally(loc, getItem());
				return true;
			}
			// I think in the future I can pass args to get item 
			player.getInventory().addItem(getItem());
			return true;
		}
		return false;	
	}
	public ItemStack getItem() {
		// Create boots
		ItemStack boots = new ItemStack(Material.CHAINMAIL_BOOTS);
		// Create the Meta object, Set the name
		ItemMeta meta = boots.getItemMeta();
		meta.setDisplayName(ChatColor.DARK_PURPLE + "Boots of zoom");
		// Create the description
		List<String> lore = new ArrayList<String>();
		lore.add("");
		lore.add(ChatColor.LIGHT_PURPLE + "Make you go zoom");
		meta.setLore(lore);
		
		meta.addEnchant(Enchantment.PROTECTION_FALL, 1, true);
		meta.addEnchant(Enchantment.DURABILITY, 1, true);
		
		meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
		meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
		meta.setUnbreakable(true);
		
		boots.setItemMeta(meta);
		
		return boots;
	}
	@EventHandler
	public void onJump(PlayerMoveEvent event) {
		Player player = (Player) event.getPlayer();
		if (player.getInventory().getBoots() != null) {
			if (player.getInventory().getBoots().getItemMeta().getDisplayName().contains("Boots of zoom")) {
				if (player.getInventory().getBoots().getItemMeta().hasLore()) {
					if (event.getFrom().getY() < event.getTo().getY() &&
							player.getLocation().subtract(0, 1, 0).getBlock().getType() != Material.AIR) {
								player.setVelocity(player.getLocation().getDirection().multiply(2).setY(2));
					}
				}
			}
		}
	}
}
