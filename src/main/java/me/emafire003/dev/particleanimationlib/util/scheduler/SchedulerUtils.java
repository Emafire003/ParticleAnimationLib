package me.emafire003.dev.particleanimationlib.util.scheduler;

import me.emafire003.dev.particleanimationlib.ParticleAnimationLib;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.minecraft.server.MinecraftServer;

import java.util.concurrent.ConcurrentLinkedQueue;

public class SchedulerUtils {
    /**Used to schedule the replacements*/
    public static final class ServerTaskScheduler {

        private static final ConcurrentLinkedQueue<ScheduledTask> TASKS = new ConcurrentLinkedQueue<>();

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

    public static void registerOnServerTick(){
        ServerTickEvents.END_SERVER_TICK.register( (ServerTaskScheduler::tick));
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
     * If the action lambda returns a false the task is stopped
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
