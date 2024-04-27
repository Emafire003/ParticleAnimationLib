package me.emafire003.dev.particleanimationlib.effects.image;


import me.emafire003.dev.particleanimationlib.EffectType;
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
    public String fileName = null;

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
    public float size = (float) 1 / 40;

    /**
     * Should it rotate?
     */
    public boolean enableRotation = true;

    /**
     * Apply a fixed rotation
     */
    public Vec3d rotation = null;

    /**
     * Should it orient to face the player's direction?
     */
    public boolean orientYaw = true;
    
    /**
     * Should it face in the same direction as the location. Obeying yaw and pitch?
     */
    public boolean orientPitch = false;

    public boolean orient = false;

    /**
     * What plane should it rotate?
     */
    public ColoredImageEffect.Plane plane = ColoredImageEffect.Plane.XYZ;

    /**
     * Turns the cube by this angle each iteration around the x-axis
     */
    public double angularVelocityX = Math.PI / 200;

    /**
     * Turns the cube by this angle each iteration around the y-axis
     */
    public double angularVelocityY = Math.PI / 170;

    /**
     * Turns the cube by this angle each iteration around the z-axis
     */
    public double angularVelocityZ = Math.PI / 155;

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
    protected int rotationStep = 0;

    /**
     * Delay between steps
     */
    protected int stepDelay = 0;

    protected ImageLoadCallback imageLoadCallback;

    public BaseImageEffect(ServerWorld world, Vec3d originPos, String image_fileName) {
        super(world, EffectType.REPEATING, null, originPos);
        this.fileName = image_fileName;
        if(fileName != null || fileName.isBlank()){
            load(fileName);
        }else{
            //Default to error image
        }
    }

    public BaseImageEffect(ServerWorld world, Vec3d originPos, Identifier image_fileName) {
        super(world, EffectType.REPEATING, null, originPos);
        this.fileName = image_fileName.getPath();
        if(fileName != null || fileName.isEmpty() || fileName.isBlank()){
            load(fileName);
        }else{
            //Default to error image
        }
    }

    /*@Override
    public void reset() {
        step = 0;
        rotationStep = 0;
    }*/

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
                v = new Vec3d((float) image.getWidth() / 2 - x, (float) image.getHeight() / 2 - y, 0).multiply(size);

                if (rotation != null) {
                    v = VectorUtils.rotateVector(v, (float) rotation.getX(), (float) rotation.getY(), (float) rotation.getZ());
                    //Vec3dUtils.rotateVec3d(v, rotation.getX() * MathUtils.degreesToRadians, rotation.getY() * MathUtils.degreesToRadians, rotation.getZ() * MathUtils.degreesToRadians);
                }

                if (origin != null) {
                    /*if (orientPitch){
                        VectorUtils.rotateVector()
                        Vec3dUtils.rotateAroundAxisX(v, Math.toRadians(origin.getPitch()));
                    }
                    if (orientYaw) {
                        Vec3dUtils.rotateAroundAxisY(v, -origin.getYaw() * MathUtils.degreesToRadians);
                    }*/
                    if(orient){
                        v = VectorUtils.rotateVector(v, this.getYaw(), this.getPitch());
                    }
                }

                if (enableRotation) {
                    rotX = 0;
                    rotY = 0;
                    rotZ = 0;

                    switch (plane) {
                        case X:
                            rotX = angularVelocityX * rotationStep;
                            break;
                        case Y:
                            rotY = angularVelocityY * rotationStep;
                            break;
                        case Z:
                            rotZ = angularVelocityZ * rotationStep;
                            break;
                        case XY:
                            rotX = angularVelocityX * rotationStep;
                            rotY = angularVelocityY * rotationStep;
                            break;
                        case XZ:
                            rotX = angularVelocityX * rotationStep;
                            rotZ = angularVelocityZ * rotationStep;
                            break;
                        case XYZ:
                            rotX = angularVelocityX * rotationStep;
                            rotY = angularVelocityY * rotationStep;
                            rotZ = angularVelocityZ * rotationStep;
                            break;
                        case YZ:
                            rotY = angularVelocityY * rotationStep;
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
        rotationStep++;
    }

    public enum Plane {
        X, Y, Z, XY, XZ, XYZ, YZ
    }

    /**This method is to be overridden by extending classes such as {@link me.emafire003.dev.particleanimationlib.effects.image.ColoredImageEffect}*/
    protected abstract void display(BufferedImage image, Vec3d v, Vec3d location, int pixel);

}
