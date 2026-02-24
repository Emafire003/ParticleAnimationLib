package me.emafire003.dev.particleanimationlib;

import me.emafire003.dev.particleanimationlib.util.image.ImageUtils;
import me.emafire003.dev.particleanimationlib.util.scheduler.SchedulerUtils;
import net.minecraft.server.MinecraftServer;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.server.ServerStartingEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.loading.FMLConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.nio.file.Path;

@Mod(ParticleAnimationLib.MOD_ID)
public class ParticleAnimationLib {
	// This logger is used to write text to the console and the log file.
	// It is considered best practice to use your mod id as the logger's name.
	// That way, it's clear which mod wrote info, warnings, and errors.
	public static final String MOD_ID = "particleanimationlib";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);
	//TODO this may need to change for quilt & forge and such
	public static final Path DEFAULT_CHACE_PATH = Path.of(FMLConfig.defaultConfigPath()).resolve(MOD_ID+"_cache");

	public static MinecraftServer SERVER_INSTANCE = null;

	// The constructor for the mod class is the first code that is run when your mod is loaded.
	// FML will recognize some parameter types like IEventBus or ModContainer and pass them in automatically.
	public ParticleAnimationLib() {
		LOGGER.info("Loading ParticleAnimationLib for awesome particle effects!");

		// Register ourselves for server and other game events we are interested in.
		// Note that this is necessary if and only if we want *this* class (ParticleAnimationLibraryPAL) to respond directly to events.
		// Do not add this line if there are no @SubscribeEvent-annotated functions in this class, like onServerStarting() below.
		MinecraftForge.EVENT_BUS.register(this);
		MinecraftForge.EVENT_BUS.register(SchedulerUtils.class);
	}

	@SubscribeEvent
	public void onServerStarting(ServerStartingEvent event) {
		SERVER_INSTANCE = event.getServer();
	}


	/** Specify a Custom folder where loaded images are going to be cached.
	 * Defaults to: <i>config/particleanimationlib_cache/</i>
	 * */
	public static void setImageCacheFolder(File folder) {
		ImageUtils.setImageCacheFolder(folder);
	}

	/**
	 * Caches / preloads some images that will be used in the Image Effects later on, to speed up the display
	 *
	 * @param image_paths A list of strings containing the paths of all the images you want to pre-load
	 * */
	/* TODO later release/revision

	public static void preloadImages(List<String> image_paths){
		ServerLifecycleEvents.SERVER_STARTED.register(server -> {
			//TODO would need a callback not null, and maybe should be static BaseImageEffect
			image_paths.forEach(path -> {
				ImageUtils.loadImage(path, null, server);
			});
		});

	}*/
}