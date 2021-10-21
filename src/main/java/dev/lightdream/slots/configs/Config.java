package dev.lightdream.slots.configs;

import dev.lightdream.api.dto.*;
import dev.lightdream.slots.dto.Slot;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class Config extends dev.lightdream.api.configs.Config {

    public Item slotToken = new Item(XMaterial.PAPER, 0, "Slots Token", new ArrayList<>());
    public GUIConfig slotsConfig = new GUIConfig(
            "slots",
            "CHEST",
            "Slots",
            5,
            new Item(XMaterial.AIR),
            new HashMap<String, GUIItem>() {{
                put("", new GUIItem(new Item(XMaterial.BLACK_STAINED_GLASS_PANE), new GUIItem.GUIItemArgs(), Arrays.asList(0, 9, 18, 27, 36, 1, 10, 19, 28, 37, 7, 16, 25, 34, 43, 8, 17, 26, 35, 44)));
            }}, true
    );

    public List<Slot> slots = Arrays.asList(
            new Slot(new Item(XMaterial.DIAMOND), 100, new Reward("say YES")),
            new Slot(new Item(XMaterial.DIRT), 20, null)
    );


}
