package io.github.divios.dropitems2inv;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class commands implements CommandExecutor {


    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        DropItems2Inv.toggleEnable();

        sender.sendMessage(ChatColor.DARK_AQUA + "DropItems2Inv > " + ChatColor.GRAY + "Features are now " + utils.getEnableStr());

        return true;
    }
}
