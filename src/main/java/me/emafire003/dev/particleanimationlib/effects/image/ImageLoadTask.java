package me.emafire003.dev.particleanimationlib.effects.image;

import me.emafire003.dev.particleanimationlib.ParticleAnimationLib;

import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

import static me.emafire003.dev.particleanimationlib.ParticleAnimationLib.LOGGER;

//TODO maybe remove runnable?
public class ImageLoadTask{

    private static final int REQUEST_TIMEOUT = 30000;
    private static final int BUFFER_SIZE = 10 * 1024;
    private static boolean dirsMade = false;
    private final String fileName;
    private final ImageLoadCallback callback;

    //TODO add support for identifiers?

    public ImageLoadTask(String fileName, ImageLoadCallback callback) {
        this.fileName = fileName;
        this.callback = callback;
        LOGGER.info("TASKED TASK");
    }

    public void run() {
        BufferedImage[] images;
        File imageFile;

        if (fileName.startsWith("http")) {
            try {
                File cacheFolder = ImageUtils.getImageCacheFolder();
                if (cacheFolder == null) {
                    // This should never really happen anymore, but leaving the check here just in case.
                    LOGGER.warn( "Can't load from URL because no cache folder has been set by the owning plugin: " + fileName);
                    callback.loaded(new BufferedImage[0]);
                    return;
                }

                if (!dirsMade) {
                    dirsMade = true;
                    if (!cacheFolder.exists() && !cacheFolder.mkdirs()) {
                        LOGGER.error("Could not create cache folder: " + cacheFolder.getAbsolutePath());
                    }
                }

                String cacheFileName = URLEncoder.encode(fileName, StandardCharsets.UTF_8);
                imageFile = new File(cacheFolder, cacheFileName);
                if (!imageFile.exists()) {

                    URL imageUrl = new URL(fileName);
                    HttpURLConnection conn = (HttpURLConnection)imageUrl.openConnection();
                    conn.setConnectTimeout(REQUEST_TIMEOUT);
                    conn.setReadTimeout(REQUEST_TIMEOUT);
                    conn.setInstanceFollowRedirects(true);
                    InputStream in = conn.getInputStream();
                    OutputStream out = new FileOutputStream(imageFile);
                    byte[] buffer = new byte[BUFFER_SIZE];
                    int len;

                    while ((len = in.read(buffer)) != -1) {
                        out.write(buffer, 0, len);
                    }
                    out.close();
                }
            } catch (Exception ex) {
                LOGGER.warn("Failed to load file " + fileName, ex);
                callback.loaded(new BufferedImage[0]);
                return;
            }
        } else if (!fileName.startsWith(File.pathSeparator)) {
            imageFile = new File(ParticleAnimationLib.DEFAULT_CHACE_PATH.toFile(), fileName);

            if (!imageFile.exists()){
                imageFile = new File(fileName);
            }
        } else {
            imageFile = new File(fileName);
        }
        if (!imageFile.exists()) {
            LOGGER.warn("Failed to find file " + fileName);
            images = new BufferedImage[0];
            callback.loaded(images);
            return;
        }

        try {
            if (fileName.endsWith(".gif")) {
                ImageReader reader = ImageIO.getImageReadersBySuffix("GIF").next();
                ImageInputStream in = ImageIO.createImageInputStream(imageFile);
                reader.setInput(in);
                int numImages = reader.getNumImages(true);
                images = new BufferedImage[numImages];
                //TODO may need to add a -1
                for (int i = 0; i < numImages-1; i++) {
                    images[i] = reader.read(i);
                }
            } else {
                images = new BufferedImage[1];
                images[0] = ImageIO.read(imageFile);
            }
        } catch (Exception ex) {
            LOGGER.warn("Failed to load file " + fileName, ex);
            images = new BufferedImage[0];
        }

        callback.loaded(images);
    }

}
