package dev.lightdream.slots.gui;

import dev.lightdream.api.IAPI;
import dev.lightdream.api.databases.User;
import dev.lightdream.api.dto.GUIConfig;
import dev.lightdream.api.dto.GUIItem;
import dev.lightdream.api.dto.Item;
import dev.lightdream.api.gui.GUI;
import dev.lightdream.api.utils.ItemBuilder;
import dev.lightdream.api.utils.Utils;
import dev.lightdream.slots.Main;
import dev.lightdream.slots.dto.Slot;
import fr.minuskube.inv.ClickableItem;
import fr.minuskube.inv.content.InventoryContents;
import fr.minuskube.inv.content.InventoryProvider;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.scheduler.BukkitTask;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class SlotsGUI extends GUI {

    public boolean run;
    public boolean reward;
    public List<List<Slot>> slots;
    public int current;
    public int counter;
    public int counterMax;
    public BukkitTask task;

    public SlotsGUI(IAPI api) {
        super(api);
        this.run = false;
        this.reward = false;
        this.slots = new ArrayList<>();
        this.current = -2;
        this.counterMax = 1;
    }

    @Override
    public String parse(String s, Player player) {
        return s;
    }

    @Override
    public GUIConfig setConfig() {
        return Main.instance.config.slotsConfig;
    }

    @Override
    public InventoryProvider getProvider() {
        return new SlotsGUI(api);
    }

    @Override
    public void functionCall(Player player, String s, List<String> list) {

    }

    @Override
    public boolean canAddItem(GUIItem guiItem, String s) {
        return true;
    }

    @Override
    public HashMap<Class<?>, Object> getArgs() {
        return new HashMap<>();
    }

    @Override
    public void setItems(Player player, InventoryContents inventoryContents) {
        if (slots.size() == 0 || !run) {
            return;
        }

        counter++;

        if (counter != counterMax) {
            return;
        }

        if (current % 5 == 0) {
            counterMax++;
        }

        counter = 0;

        for (int i = 0; i < slots.size(); i++) {
            for (int j = -2; j <= 2; j++) {
                if (current + j < 0) {
                    continue;
                }
                if (!run) {
                    return;
                }
                Slot slot = slots.get(i).get(current + j);
                inventoryContents.set(j + 2, i + 2, ClickableItem.empty(ItemBuilder.makeItem(slot.item)));
            }
        }
        current++;

        if (current == slots.get(0).size() - 5) {
            current--;

            boolean same = true;

            for (int i = 0; i < 4; i++) {
                if (!slots.get(i).get(current).equals(slots.get(i + 1).get(current))) {
                    same = false;
                    break;
                }
            }

            User user = Main.instance.databaseManager.getUser(player);

            if (same) {
                slots.get(0).get(current).win(user);
            }

            task = Bukkit.getScheduler().runTaskLater(Main.instance, () -> {
                SlotsGUI gui = getInventoryHandler(user, SlotsGUI.class);
                if (gui != null) {
                    gui.reward = false;
                }
                player.closeInventory();
            }, 5 * 20L);

            this.reward = true;
            this.run = false;
        }
    }

    @Override
    public void beforeUpdate(Player player, InventoryContents inventoryContents) {
        if (run || reward) {
            return;
        }

        for (int i = 0; i < 5; i++) {
            for (int j = 2; j <= 6; j++) {
                inventoryContents.set(i, j, null);
            }
        }
    }

    @Override
    public void onInventoryClose(InventoryCloseEvent event) {
        if (task != null) {
            task.cancel();
        }
        run = false;
        reward = false;
    }

    @Override
    public void onInventoryClick(InventoryClickEvent event) {

    }

    @Override
    public void onPlayerInventoryClick(InventoryClickEvent event) {
        User user = Main.instance.databaseManager.getUser((Player) event.getWhoClicked());

        Item item = new Item(event.getCurrentItem());

        if (!item.equals(Main.instance.config.slotToken, false)) {
            event.setCancelled(true);
            return;
        }

        if (run || reward) {
            return;
        }

        run(user);

        event.setCurrentItem(null);
    }

    @Override
    public boolean preventClose() {
        return false;
    }

    public void run(User user) {
        SlotsGUI gui = getInventoryHandler(user, getClass());

        gui.run = true;
        List<List<Slot>> rows = new ArrayList<>();

        for (int i = 0; i < 5; i++) {
            List<Slot> row = new ArrayList<>();
            for (int j = 0; j < 30; j++) {
                row.add(Utils.getRandom(Main.instance.config.slots));
            }
            rows.add(row);
        }

        gui.slots = rows;
        gui.current = -2;
    }

}
