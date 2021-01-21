package io.github.divios.dropitems2inv;

import io.github.divios.dropitems2inv.guis.guiWrapper;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class commands implements CommandExecutor {


    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        guiWrapper.openSettingsGui((Player) sender);
        return true;
    }
}
