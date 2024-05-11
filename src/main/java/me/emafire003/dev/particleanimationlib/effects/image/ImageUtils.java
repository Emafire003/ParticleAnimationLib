package me.emafire003.dev.particleanimationlib.effects.image;

import me.emafire003.dev.particleanimationlib.ParticleAnimationLib;

import java.awt.image.BufferedImage;
import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class ImageUtils {


    //TODO make configurable or use teh Identifiers or something
    private static File imageCacheFolder;
    private static Map<String, BufferedImage[]> imageCache = new HashMap<>();
    public static void setImageCacheFolder(File folder) {
        imageCacheFolder = folder;
    }

    public static File getImageCacheFolder() {
        if(imageCacheFolder == null){
            return ParticleAnimationLib.DEFAULT_CHACE_PATH.toFile();
        }
        return imageCacheFolder;
    }

    private static void startTask(final String fileName, final ImageLoadCallback callback){
        ImageLoadTask imageLoadTask = new ImageLoadTask(fileName, images -> {
            imageCache.put(fileName, images);
            callback.loaded(images);
        });
        imageLoadTask.run();
    }
    public static void loadImage(final String fileName, final ImageLoadCallback callback) {
        if(imageCache == null){
            imageCache = new HashMap<>();
            startTask(fileName, callback);
        }

        BufferedImage[] images = imageCache.get(fileName);
        if (images != null) {
            callback.loaded(images);
        }else{
            startTask(fileName, callback);
        }


        //TODO should i spawn a new thread or just execute this while loading the server? Or when the image is requested the first time?
        // Because i feel like it could get problematic if it's loaded asyncronously each time

        /*owningPlugin.getServer().getScheduler().runTaskAsynchronously(owningPlugin, new ImageLoadTask(this, fileName, new ImageLoadCallback() {
            @Override
            public void loaded(final BufferedImage[] images) {
                owningPlugin.getServer().getScheduler().runTask(owningPlugin, new Runnable() {
                    @Override
                    public void run() {
                        imageCache.put(fileName, images);
                        callback.loaded(images);
                    }
                });
            }
        }));*/
    }

}
