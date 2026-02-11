package me.emafire003.dev.particleanimationlib.util.scheduler;

import net.minecraft.server.MinecraftServer;

public interface ServerTickRunnable {
    boolean run(MinecraftServer server, int ticks);
}
