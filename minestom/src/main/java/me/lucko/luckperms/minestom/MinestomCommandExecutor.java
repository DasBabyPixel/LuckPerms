package me.lucko.luckperms.minestom;

import me.lucko.luckperms.common.command.CommandManager;
import me.lucko.luckperms.common.command.utils.ArgumentTokenizer;
import me.lucko.luckperms.common.sender.Sender;
import net.minestom.server.command.builder.Command;
import net.minestom.server.command.builder.arguments.ArgumentType;
import net.minestom.server.command.builder.suggestion.SuggestionEntry;

import java.util.List;

public class MinestomCommandExecutor extends CommandManager {
    private final LuckPermsCommand command;
    private final LPMinestomPlugin plugin;

    public MinestomCommandExecutor(LPMinestomPlugin plugin) {
        super(plugin);
        this.plugin = plugin;
        this.command = new LuckPermsCommand();
    }

    private class LuckPermsCommand extends Command {
        public LuckPermsCommand() {
            super("luckperms", "lp", "perm", "perms", "permission", "permissions");
            final var params = ArgumentType.StringArray("params");

            params.setSuggestionCallback((commandSender, commandContext, suggestion) -> {
                Sender wrapped = MinestomCommandExecutor.this.plugin.getSenderFactory().wrap(commandSender);
                String input = commandContext.getInput();
                String[] split = input.split(" ", 2);
                String args = split.length > 1 ? split[1] : "";
                List<String> arguments = ArgumentTokenizer.TAB_COMPLETE.tokenizeInput(args);
                tabCompleteCommand(wrapped, arguments).stream().map(SuggestionEntry::new).forEach(suggestion::addEntry);
            });
        }
    }
}
