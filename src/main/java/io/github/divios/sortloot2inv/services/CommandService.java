package io.github.divios.sortloot2inv.services;

import com.github.divios.neptune_framework.core.annotations.Component;
import com.google.inject.Inject;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;

import javax.annotation.PostConstruct;
import java.text.MessageFormat;

@Component
public class CommandService implements CommandExecutor {

    @Inject
    private JavaPlugin javaPlugin;

    @Inject
    private SettingsService settingsService;

    @PostConstruct
    private void init() {
        javaPlugin.getCommand("SortLoot2Inv").setExecutor(this);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        settingsService.toggleStatus();
        sender.sendMessage(MessageFormat.format(
                "The status has been changed to {0}", settingsService.getStatus()));

        return true;
    }

}
