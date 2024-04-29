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

            //clicked
            if(e.getCurrentItem().getItemMeta() == null) {
                return;
            }

            String displayName = e.getCurrentItem().getItemMeta().getDisplayName();

            if (e.getInventory().getType() == InventoryType.ANVIL) {

                //anvilの結果欄
                if (e.getSlotType() == InventoryType.SlotType.RESULT) {

                    //元アイテムと違う名前になっている
                    //元アイテムにエンチャントがついている
                    //キャンセル
                    //if(latestItem.get(p).getItemMeta() == null) {
                    //    return;
                    //}

                    //チェストの名前変更禁止
                    //Vehiclesコピー対策
                   if (e.getCurrentItem().getType().equals(Material.CHEST)){

                        //p.sendMessage("" + e.getCurrentItem().getType().name());
                        p.sendMessage(ChatColor.YELLOW + "このアイテムは名前の変更ができません！");
                        e.setCancelled(true);
                        return;
                    }

                    //[&4悪魔の血液]作成キャンセル
                    if (displayName.contains("悪魔の血液") || displayName.contains("黒弾")){
                        p.sendMessage(ChatColor.RED + "アイテムからの魔力に弾かれた");
                        e.setCancelled(true);
                        return;
                    }
                    /*if (e.getCurrentItem().getType().name().equals("IRON_NUGGET") || e.getCurrentItem().getType().name().equals("RED_DYE")){
                        p.sendMessage(ChatColor.YELLOW + "これを触るのはまずいようだ...");
                        e.setCancelled(true);
                        return;
                    }*/

                    if (!latestItem.containsKey(p)) {
                        return;
                    }

                    if ((displayName != latestItem.get(p).getItemMeta().getDisplayName()) &&
                            !(latestItem.get(p).getEnchantments().isEmpty())){

                        //p.sendMessage("" + e.getCurrentItem().getType().name());
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

                    //p.sendMessage("" + e.getCurrentItem().getType().name());

                }

            }




        }
    }
}
