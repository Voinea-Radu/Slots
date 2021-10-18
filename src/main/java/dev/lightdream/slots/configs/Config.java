package dev.lightdream.slots.configs;

import dev.lightdream.api.dto.Item;
import dev.lightdream.api.dto.XMaterial;

import java.util.ArrayList;

public class Config extends dev.lightdream.api.configs.Config {

    public Item slotToken = new Item(XMaterial.PAPER, 0, "Slots Token", new ArrayList<>());

}
