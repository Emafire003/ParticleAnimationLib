package me.emafire003.dev.particleanimationlib.effects.image;


import me.emafire003.dev.particleanimationlib.EffectType;
import me.emafire003.dev.particleanimationlib.ParticleAnimationLib;
import me.emafire003.dev.particleanimationlib.effects.base.YPREffect;
import me.emafire003.dev.particleanimationlib.util.VectorUtils;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Vec3d;

import java.awt.image.BufferedImage;
import java.io.File;

import static me.emafire003.dev.particleanimationlib.effects.image.ImageUtils.loadImage;

public abstract class BaseImageEffect extends YPREffect {

    /**
     * For configuration-driven files
     */
    public String fileName;

    /**
     * Whether or not to check for transparent pixels
     */
    public boolean transparency = false;

    /**
     * How many ticks to show each frame
     */
    public int frameDelay = 5;

    /**
     * Each stepX pixel will be shown. Saves packets for high resolutions.
     */
    public int stepX = 10;

    /**
     * Each stepY pixel will be shown. Saves packets for high resolutions.
     */
    public int stepY = 10;

    /**
     * Scale the image down
     */
    public float scale = (float) 1 / 40;

    /**How big should the dust particles be?*/
    public float particleSize = 1f;

    /**
     * Should it rotate?
     */
    public boolean enableRotation = true;

    /**
     * Apply a fixed rotation
     */
    public Vec3d rotation = null;

    /**Orients the image to the specified Yaw Pitch, for example facing a player*/
    public boolean orient = false;

    /**
     * What plane should it rotate?
     */
    public Plane plane = Plane.XYZ;

    /**
     * Turns the image by this angle each iteration around the x-axis
     */
    public double angularVelocityX = Math.PI / 200;

    /**
     * Turns the image by this angle each iteration around the y-axis
     */
    public double angularVelocityY = Math.PI / 170;

    /**
     * Turns the image by this angle each iteration around the z-axis
     */
    public double angularVelocityZ = Math.PI / 155;


    //========= Calculations stuff ==========

    /**
     * Image as BufferedImage
     */
    protected BufferedImage[] images = null;

    /**
     * Step counter
     */
    protected int step = 0;

    /**
     * Rotation step counter
     */
    protected int rotationStepCounter = 0;

    /**
     * Delay between steps
     */
    protected int stepDelay = 0;

    protected ImageLoadCallback imageLoadCallback;

    public static final Identifier ERROR_IMAGE = new Identifier(ParticleAnimationLib.MOD_ID, "textures/error_particle_image.png");

    /**
     * Creates a new base image effect. It won't do much on its own. Use {@link ColoredImageEffect} or {@link BlackAndWhiteImageEffect}
     *
     * @param world The world the particles are going to spawn in
     * @param origin The origin position of the effect, aka the starting point of the cone
     * @param yaw The yaw of the effect. For example, you can get it from an Entity using getYaw()
     * @param pitch The pitch of the effect. For example, you can get it from an Entity using getPitch()
     * @param fileName The path and the name of the file that you want to display. It can also be an URL.
     *                 You can also use {@code new Identifier(modid, resource).getPath()}. Supported formats include jpg, png, gif
     * @param transparency If true transparent pixels will be transparent, otherwise they will be black.
     * @param frameDelay How many ticks to show each frame for
     * @param stepX How many pixel should be skipped on the X? Aka show only one pixel every *n* on the X plane
     * @param stepY How many pixel should be skipped on the Y? Aka show only one pixel every *n* on the Y plane
     * @param scale Scale factor for the image
     * @param particleSize How big should each individual Dust particle be? See also {@link net.minecraft.particle.DustParticleEffect}
     * @param rotation Apply a fixed rotation (independent of enableRotation)
     * @param orient Should the image orient towards yaw and pitch? For example orient to the player's facing direction
     * @param enableRotation Should it rotate?
     * @param plane What plane should it rotate?
     * @param angularVelocityX Turns the image by this angle each iteration around the x-axis (radians)
     * @param angularVelocityY Turns the image by this angle each iteration around the y-axis (radians)
     * @param angularVelocityZ Turns the image by this angle each iteration around the z-axis (radians)
     * */
    public BaseImageEffect(ServerWorld world, Vec3d origin, float yaw, float pitch, String fileName, boolean transparency, int frameDelay, int stepX, int stepY, float scale, float particleSize, Vec3d rotation, boolean orient, boolean enableRotation, Plane plane, double angularVelocityX, double angularVelocityY, double angularVelocityZ) {
        super(world, EffectType.REPEATING, null, origin);
        this.yaw = yaw;
        this.pitch = pitch;
        this.fileName = fileName;
        if(fileName != null && !fileName.isBlank()){
            load(fileName);
        }else{
            ParticleAnimationLib.LOGGER.error("The file you have specified is invalid! The file path you have specified is: " + fileName);
            load(ERROR_IMAGE.getPath());
        }
        this.transparency = transparency;
        this.frameDelay = frameDelay;
        this.stepX = stepX;
        this.stepY = stepY;
        this.scale = scale;
        this.particleSize = particleSize;
        this.enableRotation = enableRotation;
        this.rotation = rotation;
        this.orient = orient;
        this.plane = plane;
        this.angularVelocityX = angularVelocityX;
        this.angularVelocityY = angularVelocityY;
        this.angularVelocityZ = angularVelocityZ;
    }


    public static void copy(BaseImageEffect original, BaseImageEffect copy) {
        YPREffect.copy(original, copy);
        copy.setFileName(original.getFileName());
        copy.setTransparency(original.isTransparency());
        copy.setFrameDelay(original.getFrameDelay());
        copy.setStepX(original.getStepX());
        copy.setStepY(original.getStepY());
        copy.setScale(original.getScale());
        copy.setParticleSize(original.getParticleSize());
        copy.setEnableRotation(original.isEnableRotation());
        copy.setRotation(original.getRotation());
        copy.setOrient(original.isOrient());
        copy.setPlane(original.getPlane());
        copy.setAngularVelocityX(original.getAngularVelocityX());
        copy.setAngularVelocityY(original.getAngularVelocityY());
        copy.setAngularVelocityZ(original.getAngularVelocityZ());
        copy.images = original.images;
        copy.step = original.step;
        copy.rotationStepCounter = original.rotationStepCounter;
        copy.stepDelay = original.stepDelay;
        copy.imageLoadCallback = original.imageLoadCallback;
    }


    public BaseImageEffect(ServerWorld world, Vec3d originPos, String image_fileName) {
        super(world, EffectType.REPEATING, null, originPos);
        this.fileName = image_fileName;
        if(fileName != null && !fileName.isBlank()){
            load(fileName);
        }else{
            ParticleAnimationLib.LOGGER.error("The file you have specified is invalid! The file path you have specified is: " + fileName);
            load(ERROR_IMAGE.getPath());
        }
    }

    public BaseImageEffect(ServerWorld world, Vec3d originPos, Identifier image) {
        super(world, EffectType.REPEATING, null, originPos);
        this.fileName = image.getPath();
        if(fileName != null && !fileName.isBlank()){
            load(fileName);
        }else{
            ParticleAnimationLib.LOGGER.error("The file you have specified is invalid! The file path you have specified is: " + fileName);
            load(ERROR_IMAGE.getPath());
        }
    }

    /**Updates the image that is displayed. WARNING! Maye cause issues*/
    public void load(String fileName) {
        imageLoadCallback = i -> {
            images = i;
            imageLoadCallback = null;
        };
        loadImage(fileName, imageLoadCallback);
    }

    public void loadFile(File file) {
        load(file.getName());
    }

    @Override
    public void onRun() {
        if (images == null && fileName != null){
            return;
        }

        if (images == null && imageLoadCallback != null){
            return;
        }

        if (images == null || images.length == 0) {
            return;
        }

        if (stepDelay == frameDelay) {
            step++;
            stepDelay = 0;
        }

        stepDelay++;

        if (step >= images.length-1){
            step = 0;
        }

        BufferedImage image = images[step];
        //If for some reason the thing above doesn't work, go back to the first frame
        if(image == null){
            image = images[0];
            step = 0;
        }

        Vec3d origin = getOriginPos();
        Vec3d v;

        int pixel;
        double rotX;
        double rotY;
        double rotZ;

        for (int y = 0; y < image.getHeight(); y += stepY) {
            for (int x = 0; x < image.getWidth(); x += stepX) {
                v = new Vec3d((float) image.getWidth() / 2 - x, (float) image.getHeight() / 2 - y, 0).multiply(scale);

                if (rotation != null) {
                    v = VectorUtils.rotateVector(v, (float) rotation.getX(), (float) rotation.getY(), (float) rotation.getZ());
                    //Vec3dUtils.rotateVec3d(v, rotation.getX() * MathUtils.degreesToRadians, rotation.getY() * MathUtils.degreesToRadians, rotation.getZ() * MathUtils.degreesToRadians);
                }

                if (origin != null) {
                    if(orient){
                        v = v.rotateX((float) Math.toRadians(this.getPitch()));
                        v = v.rotateY((float) Math.toRadians(this.getYaw()));
                        //v = VectorUtils.rotateVector(v, this.getYaw()+90, this.getPitch());
                    }
                }

                if (enableRotation) {
                    rotX = 0;
                    rotY = 0;
                    rotZ = 0;

                    switch (plane) {
                        case X:
                            rotX = angularVelocityX * rotationStepCounter;
                            break;
                        case Y:
                            rotY = angularVelocityY * rotationStepCounter;
                            break;
                        case Z:
                            rotZ = angularVelocityZ * rotationStepCounter;
                            break;
                        case XY:
                            rotX = angularVelocityX * rotationStepCounter;
                            rotY = angularVelocityY * rotationStepCounter;
                            break;
                        case XZ:
                            rotX = angularVelocityX * rotationStepCounter;
                            rotZ = angularVelocityZ * rotationStepCounter;
                            break;
                        case XYZ:
                            rotX = angularVelocityX * rotationStepCounter;
                            rotY = angularVelocityY * rotationStepCounter;
                            rotZ = angularVelocityZ * rotationStepCounter;
                            break;
                        case YZ:
                            rotY = angularVelocityY * rotationStepCounter;
                            rotZ = angularVelocityZ * step;
                            break;
                    }
                    v = VectorUtils.rotateVector(v, (float) rotX, (float) rotY,(float)  rotZ);
                }

                pixel = image.getRGB(x, y);

                if (transparency && (pixel >> 24) == 0) continue;

                display(image, v, origin, pixel);
            }
        }
        rotationStepCounter++;
    }




    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public boolean isTransparency() {
        return transparency;
    }

    public void setTransparency(boolean transparency) {
        this.transparency = transparency;
    }

    public int getFrameDelay() {
        return frameDelay;
    }

    public void setFrameDelay(int frameDelay) {
        this.frameDelay = frameDelay;
    }

    public int getStepX() {
        return stepX;
    }

    public void setStepX(int stepX) {
        this.stepX = stepX;
    }

    public int getStepY() {
        return stepY;
    }

    public void setStepY(int stepY) {
        this.stepY = stepY;
    }

    public float getScale() {
        return scale;
    }

    public void setScale(float scale) {
        this.scale = scale;
    }

    public float getParticleSize() {
        return particleSize;
    }

    public void setParticleSize(float particleSize) {
        this.particleSize = particleSize;
    }

    public boolean isEnableRotation() {
        return enableRotation;
    }

    public void setEnableRotation(boolean enableRotation) {
        this.enableRotation = enableRotation;
    }

    public Vec3d getRotation() {
        return rotation;
    }

    public void setRotation(Vec3d rotation) {
        this.rotation = rotation;
    }

    public boolean isOrient() {
        return orient;
    }

    public void setOrient(boolean orient) {
        this.orient = orient;
    }

    public Plane getPlane() {
        return plane;
    }

    public void setPlane(Plane plane) {
        this.plane = plane;
    }

    public double getAngularVelocityX() {
        return angularVelocityX;
    }

    public void setAngularVelocityX(double angularVelocityX) {
        this.angularVelocityX = angularVelocityX;
    }

    public double getAngularVelocityY() {
        return angularVelocityY;
    }

    public void setAngularVelocityY(double angularVelocityY) {
        this.angularVelocityY = angularVelocityY;
    }

    public double getAngularVelocityZ() {
        return angularVelocityZ;
    }

    public void setAngularVelocityZ(double angularVelocityZ) {
        this.angularVelocityZ = angularVelocityZ;
    }


    public enum Plane {
        X, Y, Z, XY, XZ, XYZ, YZ
    }

    /**This method is to be overridden by extending classes such as {@link me.emafire003.dev.particleanimationlib.effects.image.ColoredImageEffect}*/
    protected abstract void display(BufferedImage image, Vec3d v, Vec3d location, int pixel);

}
