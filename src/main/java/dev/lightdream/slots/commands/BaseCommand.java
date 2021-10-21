package dev.lightdream.slots.commands;

import dev.lightdream.api.commands.SubCommand;
import dev.lightdream.api.databases.User;
import dev.lightdream.slots.Main;
import dev.lightdream.slots.gui.SlotsGUI;

import java.util.List;

public class BaseCommand extends SubCommand {
    public BaseCommand() {
        super(Main.instance, "", true, false, "");
    }

    @Override
    public void execute(User user, List<String> list) {
        new SlotsGUI(api).open(user);
    }

    @Override
    public List<String> onTabComplete(User user, List<String> list) {
        return null;
    }
}
