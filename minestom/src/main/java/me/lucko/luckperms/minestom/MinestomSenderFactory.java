package me.lucko.luckperms.minestom;

import me.lucko.luckperms.common.locale.TranslationManager;
import me.lucko.luckperms.common.sender.Sender;
import me.lucko.luckperms.common.sender.SenderFactory;
import net.kyori.adventure.text.Component;
import net.luckperms.api.util.Tristate;
import net.minestom.server.MinecraftServer;
import net.minestom.server.command.CommandSender;
import net.minestom.server.command.ConsoleSender;
import net.minestom.server.entity.Player;

import java.util.Locale;
import java.util.UUID;

public class MinestomSenderFactory extends SenderFactory<LPMinestomPlugin, CommandSender> {
    public MinestomSenderFactory(LPMinestomPlugin plugin) {
        super(plugin);
    }

    @Override
    protected UUID getUniqueId(CommandSender sender) {
        if (sender instanceof Player player) {
            return player.getUuid();
        }
        return Sender.CONSOLE_UUID;
    }

    @Override
    protected String getName(CommandSender sender) {
        if (sender instanceof Player player) {
            return player.getUsername();
        }
        return Sender.CONSOLE_NAME;
    }

    @Override
    protected void sendMessage(CommandSender sender, Component message) {
        Locale locale = null;
        if (sender instanceof Player player) {
            locale = Locale.forLanguageTag(player.getSettings().getLocale());
        }
        Component rendered = TranslationManager.render(message, locale);
        sender.sendMessage(rendered);
    }

    @Override
    protected Tristate getPermissionValue(CommandSender sender, String node) {
        return Tristate.UNDEFINED;
    }

    @Override
    protected boolean hasPermission(CommandSender sender, String node) {
        return false;
    }

    @Override
    protected void performCommand(CommandSender sender, String command) {
        MinecraftServer.getCommandManager().execute(sender, command);
    }

    @Override
    protected boolean isConsole(CommandSender sender) {
        return sender instanceof ConsoleSender;
    }
}
