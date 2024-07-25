package me.lucko.luckperms.minestom.loader;

import me.lucko.luckperms.common.loader.JarInJarClassLoader;
import me.lucko.luckperms.common.loader.LoaderBootstrap;
import net.minestom.server.extensions.Extension;

public class MinestomLoaderExtension extends Extension {
    private static final String JAR_NAME = "luckperms-minestom.jarinjar";
    private static final String BOOTSTRAP_CLASS = "me.lucko.luckperms.minestom.LPMinestomBootstrap";

    private final LoaderBootstrap plugin;

    public MinestomLoaderExtension() {
        JarInJarClassLoader loader = new JarInJarClassLoader(getClass().getClassLoader(), JAR_NAME);
        this.plugin = loader.instantiatePlugin(BOOTSTRAP_CLASS, Object.class, this);
    }

    @Override
    public void preInitialize() {
        plugin.onLoad();
    }

    @Override
    public void initialize() {
        plugin.onEnable();
    }

    @Override
    public void terminate() {
        plugin.onDisable();
    }
}
