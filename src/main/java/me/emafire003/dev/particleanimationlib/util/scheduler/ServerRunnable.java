package me.emafire003.dev.particleanimationlib.util.scheduler;

import net.minecraft.server.MinecraftServer;

public interface ServerRunnable {
    void run(MinecraftServer server);
}
