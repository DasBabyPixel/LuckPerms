package me.lucko.luckperms.minestom;

import me.lucko.luckperms.common.loader.LoaderBootstrap;
import me.lucko.luckperms.common.plugin.bootstrap.BootstrappedWithLoader;
import me.lucko.luckperms.common.plugin.bootstrap.LuckPermsBootstrap;
import me.lucko.luckperms.common.plugin.classpath.ClassPathAppender;
import me.lucko.luckperms.common.plugin.classpath.JarInJarClassPathAppender;
import me.lucko.luckperms.common.plugin.logging.PluginLogger;
import me.lucko.luckperms.common.plugin.logging.Slf4jPluginLogger;
import me.lucko.luckperms.common.plugin.scheduler.SchedulerAdapter;
import net.luckperms.api.platform.Platform;
import net.minestom.server.MinecraftServer;
import net.minestom.server.entity.Player;
import org.slf4j.LoggerFactory;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.nio.file.Path;
import java.time.Instant;
import java.util.Collection;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.CountDownLatch;

public class LPMinestomBootstrap implements LuckPermsBootstrap, LoaderBootstrap, BootstrappedWithLoader {
    // The loader is an object, because the server might not run the extensions library
    private final Object loader;
    private final PluginLogger logger;
    private final CountDownLatch loadLatch = new CountDownLatch(1);
    private final CountDownLatch enableLatch = new CountDownLatch(1);

    private final LPMinestomPlugin plugin;
    private final MinestomSchedulerAdapter schedulerAdapter;
    private final ClassPathAppender classPathAppender;
    private final Path dataDirectory;

    private Instant startTime;

    public LPMinestomBootstrap(Object loader) {
        this.loader = loader;
        this.logger = new Slf4jPluginLogger(LoggerFactory.getLogger("LuckPerms"));

        this.plugin = new LPMinestomPlugin(this);
        this.schedulerAdapter = new MinestomSchedulerAdapter(this);
        this.classPathAppender = new JarInJarClassPathAppender(getClass().getClassLoader());
        this.dataDirectory = determineDataDirectory(loader);
    }

    @Override
    public void onLoad() {
        try {
            this.plugin.load();
        } finally {
            this.loadLatch.countDown();
        }
    }

    @Override
    public void onEnable() {
        this.startTime = Instant.now();
        try {
            this.plugin.enable();
        } finally {
            this.enableLatch.countDown();
        }
    }

    @Override
    public void onDisable() {
        this.plugin.disable();
    }

    private static Path determineDataDirectory(Object loader) {
        boolean isLoadedByExtension = loader.getClass().getSimpleName().equals("Extension");
        Path dataDirectory;
        if (isLoadedByExtension) {
            try {
                Method getDataDirectory = loader.getClass().getMethod("getDataDirectory");
                dataDirectory = (Path) getDataDirectory.invoke(loader);
                dataDirectory = dataDirectory.toAbsolutePath();
            } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
                throw new RuntimeException("Failed to load LuckPerms extension", e);
            }
        } else {
            dataDirectory = Path.of("LuckPerms");
        }
        return dataDirectory;
    }

    @Override
    public PluginLogger getPluginLogger() {
        return this.logger;
    }

    @Override
    public SchedulerAdapter getScheduler() {
        return this.schedulerAdapter;
    }

    @Override
    public ClassPathAppender getClassPathAppender() {
        return this.classPathAppender;
    }

    @Override
    public CountDownLatch getLoadLatch() {
        return this.loadLatch;
    }

    @Override
    public CountDownLatch getEnableLatch() {
        return this.enableLatch;
    }

    @Override
    public String getVersion() {
        return "@version@";
    }

    @Override
    public Instant getStartupTime() {
        return this.startTime;
    }

    @Override
    public Platform.Type getType() {
        return Platform.Type.MINESTOM;
    }

    @Override
    public String getServerBrand() {
        return MinecraftServer.getBrandName();
    }

    @Override
    public String getServerVersion() {
        return MinecraftServer.VERSION_NAME;
    }

    @Override
    public Path getDataDirectory() {
        return dataDirectory;
    }

    @Override
    public Optional<Player> getPlayer(UUID uniqueId) {
        return Optional.ofNullable(MinecraftServer.getConnectionManager().getOnlinePlayerByUuid(uniqueId));
    }

    @Override
    public Optional<UUID> lookupUniqueId(String username) {
        return Optional
                .ofNullable(MinecraftServer.getConnectionManager().findOnlinePlayer(username))
                .map(Player::getUuid);
    }

    @Override
    public Optional<String> lookupUsername(UUID uniqueId) {
        return Optional
                .ofNullable(MinecraftServer.getConnectionManager().getOnlinePlayerByUuid(uniqueId))
                .map(Player::getUsername);
    }

    @Override
    public int getPlayerCount() {
        return MinecraftServer.getConnectionManager().getOnlinePlayerCount();
    }

    @Override
    public Collection<String> getPlayerList() {
        return MinecraftServer.getConnectionManager().getOnlinePlayers().stream().map(Player::getUsername).toList();
    }

    @Override
    public Collection<UUID> getOnlinePlayers() {
        return MinecraftServer.getConnectionManager().getOnlinePlayers().stream().map(Player::getUuid).toList();
    }

    @Override
    public boolean isPlayerOnline(UUID uniqueId) {
        return MinecraftServer.getConnectionManager().getOnlinePlayerByUuid(uniqueId) != null;
    }

    @Override
    public Object getLoader() {
        return loader;
    }
}
