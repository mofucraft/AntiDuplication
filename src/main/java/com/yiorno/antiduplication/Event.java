package com.yiorno.antiduplication;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.Map;

public class Event implements Listener {

    Map<Player, ItemStack> latestItem = new HashMap<>();;

    @EventHandler
    public void onRenameAnvil(InventoryClickEvent e) {

        Player p = (Player) e.getWhoClicked();

        if (e.getCurrentItem() == null) {
            return;
        }

        if (e.getCurrentItem().getType() != Material.AIR) {

            String displayName = e.getCurrentItem().getItemMeta().getDisplayName();

            if (e.getInventory().getType() == InventoryType.ANVIL) {

                if (e.getSlotType() == InventoryType.SlotType.RESULT) {

                    //元アイテムと違う名前になっている
                    //元アイテムにエンチャントがついている
                    //キャンセル
                    if ((displayName != latestItem.get(p).getItemMeta().getDisplayName()) &&
                            !(latestItem.get(p).getEnchantments().isEmpty())){

                        p.sendMessage(ChatColor.YELLOW + "このアイテムは名前の変更ができません！");
                        e.setCancelled(true);
                        return;
                    }

                    //チェストの名前変更禁止
                    //Vehiclesコピー対策
                    if ((displayName != latestItem.get(p).getItemMeta().getDisplayName()) &&
                            !(latestItem.get(p).getType() == Material.CHEST)){

                        p.sendMessage(ChatColor.YELLOW + "このアイテムは名前の変更ができません！");
                        e.setCancelled(true);
                        return;
                    }

                    //MOFU書き換えキャンセル
                    if ((displayName.contains(" " + ChatColor.AQUA + "MOFU")) &&
                            (latestItem.get(p).containsEnchantment(Enchantment.DURABILITY))){
                        p.sendMessage(ChatColor.YELLOW + "錬金するなー！！");
                        e.setCancelled(true);
                        return;
                    }

                } else {

                    if(latestItem.containsKey(p)){
                        latestItem.replace(p, e.getCurrentItem());
                    } else {
                        latestItem.put(p, e.getCurrentItem());
                    }

                }

            }




        }
    }
}
