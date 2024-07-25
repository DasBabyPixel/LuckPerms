package me.lucko.luckperms.minestom;

import me.lucko.luckperms.common.api.LuckPermsApiProvider;
import me.lucko.luckperms.common.calculator.CalculatorFactory;
import me.lucko.luckperms.common.command.CommandManager;
import me.lucko.luckperms.common.config.generic.adapter.ConfigurationAdapter;
import me.lucko.luckperms.common.context.manager.ContextManager;
import me.lucko.luckperms.common.dependencies.Dependency;
import me.lucko.luckperms.common.event.AbstractEventBus;
import me.lucko.luckperms.common.messaging.MessagingFactory;
import me.lucko.luckperms.common.model.Group;
import me.lucko.luckperms.common.model.Track;
import me.lucko.luckperms.common.model.User;
import me.lucko.luckperms.common.model.manager.group.GroupManager;
import me.lucko.luckperms.common.model.manager.group.StandardGroupManager;
import me.lucko.luckperms.common.model.manager.track.StandardTrackManager;
import me.lucko.luckperms.common.model.manager.track.TrackManager;
import me.lucko.luckperms.common.model.manager.user.StandardUserManager;
import me.lucko.luckperms.common.model.manager.user.UserManager;
import me.lucko.luckperms.common.plugin.AbstractLuckPermsPlugin;
import me.lucko.luckperms.common.plugin.bootstrap.LuckPermsBootstrap;
import me.lucko.luckperms.common.plugin.util.AbstractConnectionListener;
import me.lucko.luckperms.common.sender.Sender;
import net.luckperms.api.LuckPerms;
import net.luckperms.api.query.QueryOptions;

import java.util.EnumSet;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Stream;

public class LPMinestomPlugin extends AbstractLuckPermsPlugin {
    private final LPMinestomBootstrap bootstrap;

    private MinestomSenderFactory senderFactory;
    private MinestomContextManager contextManager;
    private StandardUserManager userManager;
    private StandardGroupManager groupManager;
    private StandardTrackManager trackManager;
    private MinestomConnectionListener connectionListener;
    private MinestomCommandExecutor commandExecutor;

    public LPMinestomPlugin(LPMinestomBootstrap bootstrap) {
        this.bootstrap = bootstrap;
    }

    @Override
    protected Set<Dependency> getGlobalDependencies() {
        return EnumSet.of(
                Dependency.CAFFEINE,
                Dependency.OKIO,
                Dependency.OKHTTP,
                Dependency.BYTEBUDDY,
                Dependency.EVENT,
                Dependency.CONFIGURATE_CORE,
                Dependency.CONFIGURATE_YAML,
                Dependency.SNAKEYAML
        );
    }

    @Override
    protected void setupSenderFactory() {

    }

    @Override
    protected ConfigurationAdapter provideConfigurationAdapter() {
        return null;
    }

    @Override
    protected void registerPlatformListeners() {

    }

    @Override
    protected MessagingFactory<?> provideMessagingFactory() {
        return null;
    }

    @Override
    protected void registerCommands() {

    }

    @Override
    protected void setupManagers() {

    }

    @Override
    protected CalculatorFactory provideCalculatorFactory() {
        return null;
    }

    @Override
    protected void setupContextManager() {

    }

    @Override
    protected void setupPlatformHooks() {

    }

    @Override
    protected AbstractEventBus<?> provideEventBus(LuckPermsApiProvider apiProvider) {
        return null;
    }

    @Override
    protected void registerApiOnPlatform(LuckPerms api) {

    }

    @Override
    protected void performFinalSetup() {

    }

    @Override
    public LuckPermsBootstrap getBootstrap() {
        return null;
    }

    @Override
    public UserManager<? extends User> getUserManager() {
        return null;
    }

    @Override
    public GroupManager<? extends Group> getGroupManager() {
        return null;
    }

    @Override
    public TrackManager<? extends Track> getTrackManager() {
        return null;
    }

    @Override
    public CommandManager getCommandManager() {
        return null;
    }

    @Override
    public AbstractConnectionListener getConnectionListener() {
        return null;
    }

    @Override
    public ContextManager<?, ?> getContextManager() {
        return null;
    }

    @Override
    public Optional<QueryOptions> getQueryOptionsForUser(User user) {
        return Optional.empty();
    }

    @Override
    public Stream<Sender> getOnlineSenders() {
        return Stream.empty();
    }

    @Override
    public Sender getConsoleSender() {
        return null;
    }

    public MinestomSenderFactory getSenderFactory() {
        return this.senderFactory;
    }
}
