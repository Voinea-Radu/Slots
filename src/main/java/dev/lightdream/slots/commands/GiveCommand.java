package dev.lightdream.slots.commands;

import dev.lightdream.api.IAPI;
import dev.lightdream.api.commands.SubCommand;
import dev.lightdream.api.databases.User;
import dev.lightdream.api.utils.ItemBuilder;
import dev.lightdream.slots.Main;
import org.jetbrains.annotations.NotNull;

import java.util.Collections;
import java.util.List;
import java.util.Locale;

public class GiveCommand extends SubCommand {
    public GiveCommand() {
        super(Main.instance, "give", false, false, "[SlotToken] [player]");
    }

    @SuppressWarnings("ConstantConditions")
    @Override
    public void execute(User user, List<String> args) {
        if(args.size()!=2){
            sendUsage(user);
            return;
        }

        String item = args.get(0);

        User target = Main.instance.databaseManager.getUser(args.get(1));

        if(target==null){
            user.sendMessage(api, Main.instance.lang.invalidUser);
            return;
        }

        if(!target.isOnline()){
            user.sendMessage(api, Main.instance.lang.offlineUser);
            return;
        }

        switch (item.toLowerCase()){
            case "slottoken":
                target.getPlayer().getInventory().addItem(ItemBuilder.makeItem(Main.instance.config.slotToken));
                break;
            default:
                user.sendMessage(api, Main.instance.lang.invalidItem);
                return;
        }
        user.sendMessage(api, Main.instance.lang.itemGiven);
    }

    @Override
    public List<String> onTabComplete(User user, List<String> list) {
        return null;
    }
}
