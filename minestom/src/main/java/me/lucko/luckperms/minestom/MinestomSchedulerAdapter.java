package me.lucko.luckperms.minestom;

import me.lucko.luckperms.common.plugin.bootstrap.LuckPermsBootstrap;
import me.lucko.luckperms.common.plugin.scheduler.AbstractJavaScheduler;
import net.minestom.server.MinecraftServer;

import java.util.concurrent.Executor;

public class MinestomSchedulerAdapter extends AbstractJavaScheduler {
    private final Executor executor = r -> MinecraftServer.getSchedulerManager().buildTask(r).schedule();

    public MinestomSchedulerAdapter(LuckPermsBootstrap bootstrap) {
        super(bootstrap);
    }

    @Override
    public Executor sync() {
        return executor;
    }
}
