package me.emafire003.dev.particleanimationlib.util.scheduler;

import me.emafire003.dev.particleanimationlib.ParticleAnimationLib;
import net.minecraft.server.MinecraftServer;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.event.tick.ServerTickEvent;

import java.util.ArrayList;
import java.util.List;

public class SchedulerUtils {
    /**Used to schedule the replacements*/
    public static final class ServerTaskScheduler {

        private static final List<ScheduledTask> TASKS = new ArrayList<>();

        public static void schedule(ScheduledTask task) {
            TASKS.add(task);
        }

        public static void tick(MinecraftServer server) {
            TASKS.removeIf(task -> !task.tick(server));
        }
    }

    public interface ScheduledTask {
        boolean tick(MinecraftServer server);
    }

    // Subscribe
    @SubscribeEvent
    public static void onServerTick(ServerTickEvent.Post event) {
        ServerTaskScheduler.tick(event.getServer());
    }

    //Called from any function at any time
    public static void runLater(int delayTicks, ServerRunnable action) {
        ServerTaskScheduler.schedule(new ScheduledTask() {
            int ticks = delayTicks;

            @Override
            public boolean tick(MinecraftServer server) {
                if (--ticks <= 0) {
                    action.run(server);
                    return false; // remove task
                }
                return true;
            }
        });
    }

    //Called from any function at any time

    /**Runs an action every tick and provides the tick count with an optional start delay.
     * If the action lambda returns a falls the task is stopepd
     */
    public static void runEveryTick(ServerTickRunnable action) {
        ServerTaskScheduler.schedule(new ScheduledTask() {
            int ticks = 0;

            @Override
            public boolean tick(MinecraftServer server) {
                boolean keepgoing = action.run(server, ticks);
                ticks++;
                //if the task is taking more than an hour just kill it
                if (ticks >= 20*3600) {
                    ParticleAnimationLib.LOGGER.warn("A task has taken more than an hour to complete, it has now been terminated");
                    return false; // remove task
                }
                return keepgoing;
            }
        });
    }
}
