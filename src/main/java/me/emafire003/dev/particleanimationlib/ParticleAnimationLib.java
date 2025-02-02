package me.emafire003.dev.particleanimationlib;

import me.emafire003.dev.particleanimationlib.commands.PALCommands;
import me.emafire003.dev.particleanimationlib.effects.image.ImageUtils;
import net.fabricmc.api.ModInitializer;

import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.Identifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.nio.file.Path;
import java.util.List;

public class ParticleAnimationLib implements ModInitializer {
	// This logger is used to write text to the console and the log file.
	// It is considered best practice to use your mod id as the logger's name.
	// That way, it's clear which mod wrote info, warnings, and errors.
	public static final String MOD_ID = "particleanimationlib";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);
	//TODO this may need to change for quilt & forge and such
	public static final Path DEFAULT_CHACE_PATH = FabricLoader.getInstance().getConfigDir().resolve(MOD_ID+"_cache");

	public static MinecraftServer SERVER_INSTANCE = null;

	@Override
	public void onInitialize() {
		// This code runs as soon as Minecraft is in a mod-load-ready state.
		// However, some things (like resources) may still be uninitialized.
		// Proceed with mild caution.

		LOGGER.info("Loading ParticleAnimationLib for awesome particle effects!");
		CommandRegistrationCallback.EVENT.register(PALCommands::registerCommands);
		//A bit hacky, but there is no other way :/
		ServerLifecycleEvents.SERVER_STARTED.register(server -> {
			SERVER_INSTANCE = server;
		});
	}

	public static Identifier getIdentifier(String path){
		return Identifier.of(MOD_ID ,path);
	}


	/** Specify a Custom folder where loaded images are going to be cached.
	 * Defaults to: <i>config/particleanimationlib_cache/</i>
	 * */
	public static void setImageCacheFolder(File folder) {
		ImageUtils.setImageCacheFolder(folder);
	}

	//TODO test
	/**
	 * Caches / preloads some images that will be used in the Image Effects later on, to speed up the display
	 *
	 * @param image_paths A list of strings containing the paths of all the images you want to pre-load
	 * */
	public static void preloadImages(List<String> image_paths){
		ServerLifecycleEvents.SERVER_STARTED.register(server -> {
			image_paths.forEach(path -> {
				ImageUtils.loadImage(path, null, server);
			});
		});

	}
	//TODO test
	/**
	 * Caches / preloads some images that will be used in the Image Effects later on, to speed up the display
	 *
	 * @param image_paths A list of Identifiers containing the paths of all the images you want to pre-load
	 * */
	public static void preloadImagesIdentifiers(List<Identifier> image_paths){
		ServerLifecycleEvents.SERVER_STARTED.register(server -> {
			image_paths.forEach(path -> {
				ImageUtils.loadImage(path, null, server);
			});
		});

	}

	//TODO add a function to cache all of the images needed before starting/using them
}