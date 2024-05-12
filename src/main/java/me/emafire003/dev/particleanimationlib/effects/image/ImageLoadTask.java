package me.emafire003.dev.particleanimationlib.effects.image;

import me.emafire003.dev.particleanimationlib.ParticleAnimationLib;
import net.minecraft.resource.Resource;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.Identifier;

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
import java.util.Optional;

import static me.emafire003.dev.particleanimationlib.ParticleAnimationLib.LOGGER;

//TODO maybe remove runnable?
public class ImageLoadTask{

    private static final int REQUEST_TIMEOUT = 30000;
    private static final int BUFFER_SIZE = 10 * 1024;
    private static boolean dirsMade = false;
    private final String fileName;
    private final ImageLoadCallback callback;
    private final MinecraftServer server;

    public ImageLoadTask(String filePath, ImageLoadCallback callback, MinecraftServer server) {
        this.fileName = filePath;
        this.callback = callback;
        this.server = server;
    }

    public void run() {
        BufferedImage[] images;
        File imageFile;

        LOGGER.info("The fileName is: " + fileName);
        if(fileName.startsWith("id:")){
            Identifier id = new Identifier(fileName.replaceFirst("id:", ""));
            Optional<Resource> resourceOptional = server.getResourceManager().getResource(id);
            if(resourceOptional.isEmpty()){
                LOGGER.error("Error! Can't find image from the id: " + id);
                return;
            }

            Resource imageResource = resourceOptional.get();
            try{

                if (fileName.endsWith(".gif")) {
                    ImageReader reader = ImageIO.getImageReadersBySuffix("GIF").next();
                    ImageInputStream in = ImageIO.createImageInputStream(imageResource.getInputStream());
                    reader.setInput(in);
                    int numImages = reader.getNumImages(true);
                    images = new BufferedImage[numImages];
                    for (int i = 0; i < numImages-1; i++) {
                        images[i] = reader.read(i);
                    }
                } else {
                    images = new BufferedImage[1];
                    images[0] = ImageIO.read(imageResource.getInputStream());
                }
                imageResource.getReader().close();
                imageResource.getInputStream().close();
                callback.loaded(images);
                return;
            }catch (Exception e){
                LOGGER.error("There was an error while trying to read from a file Identifier!");
                e.printStackTrace();
            }
        }

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
                LOGGER.error("Failed to load file " + fileName, ex);
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
