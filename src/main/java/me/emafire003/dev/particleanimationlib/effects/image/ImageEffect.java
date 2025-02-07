package me.emafire003.dev.particleanimationlib.effects.image;


import me.emafire003.dev.particleanimationlib.EffectType;
import me.emafire003.dev.particleanimationlib.ParticleAnimationLib;
import me.emafire003.dev.particleanimationlib.effects.base.YPREffect;
import me.emafire003.dev.particleanimationlib.util.EffectModifier;
import me.emafire003.dev.particleanimationlib.util.VectorUtils;
import net.minecraft.entity.Entity;
import net.minecraft.particle.DustParticleEffect;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Vec3d;

import java.awt.image.BufferedImage;

import static me.emafire003.dev.particleanimationlib.effects.image.ImageUtils.loadImage;

public class ImageEffect extends YPREffect {

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
    public int stepX = 5;

    /**
     * Each stepY pixel will be shown. Saves packets for high resolutions.
     */
    public int stepY = 5;

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

    /**Weather or not this should display as black and white*/
    public boolean blackAndWhite = false;

    /**Weather or not the color of the image should be inverted*/
    public boolean invertColors = false;

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

    public static final Identifier ERROR_IMAGE = ParticleAnimationLib.getIdentifier("images/error.png");
    //public static final Identifier LOGO_IMAGE = new Identifier(ParticleAnimationLib.MOD_ID, "images/icon.png");

    /**
     * Creates a new ImageEffect
     *
     * @param world The world the particles are going to spawn in
     * @param origin The origin position of the effect, aka the starting point of the cone
     * @param yaw The yaw of the effect. For example, you can get it from an Entity using getYaw()
     * @param pitch The pitch of the effect. For example, you can get it from an Entity using getPitch()
     * @param fileName The path and the name of the file that you want to display. It can also be an URL.
     *                Supported formats include jpg, png, gif
     * @param transparency If true transparent pixels will be transparent, otherwise they will be black.
     * @param frameDelay How many ticks to show each frame for
     * @param stepX How many pixel should be skipped on the X? Aka show only one pixel every *n* on the X plane
     * @param stepY How many pixel should be skipped on the Y? Aka show only one pixel every *n* on the Y plane
     * @param scale Scale factor for the image
     * @param particleSize How big should each individual Dust particle be? See also {@link DustParticleEffect}
     * @param rotation Apply a fixed rotation (independent of enableRotation)
     * @param orient Should the image orient towards yaw and pitch? For example orient to the player's facing direction
     * @param enableRotation Should it rotate?
     * @param plane What plane should it rotate?
     * @param angularVelocityX Turns the image by this angle each iteration around the x-axis (radians)
     * @param angularVelocityY Turns the image by this angle each iteration around the y-axis (radians)
     * @param angularVelocityZ Turns the image by this angle each iteration around the z-axis (radians)
     * @param
     * */
    public ImageEffect(ServerWorld world, Vec3d origin, float yaw, float pitch, String fileName,
                       boolean transparency, int frameDelay, int stepX, int stepY, float scale,
                       float particleSize, Vec3d rotation, boolean orient, boolean enableRotation,
                       Plane plane, double angularVelocityX, double angularVelocityY, double angularVelocityZ, boolean blackAndWhite, boolean invertColors) {
        super(world, EffectType.REPEATING, null, origin);
        this.yaw = yaw;
        this.pitch = pitch;
        this.fileName = fileName;
        if(fileName != null && !fileName.isBlank()){
            load(fileName);
        }else{
            ParticleAnimationLib.LOGGER.error("The file you have specified is invalid! The file path you have specified is: " + fileName);
            load(ERROR_IMAGE);
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
        this.blackAndWhite = blackAndWhite;
        this.invertColors = invertColors;
    }

    private ImageEffect(Builder builder) {
        super(builder.world, EffectType.REPEATING, null, builder.originPos);
        setIterations(builder.iterations);
        setOriginPos(builder.originPos);
        setUpdatePositions(builder.updatePositions);
        setUseEyePosAsOrigin(builder.useEyePosAsOrigin);
        setEntityOrigin(builder.entityOrigin);
        setOriginOffset(builder.originOffset);
        setExecuteOnStop(builder.executeOnStop);
        setShouldSpawnParticlesEveryNIteration(builder.shouldSpawnParticlesEveryNIteration);
        setSpawnParticlesEveryNIteration(builder.spawnParticlesEveryNIteration);
        setShouldLimitParticlesSpawnedPerIteration(builder.shouldLimitParticlesSpawnedPerIteration);
        setParticleLimit(builder.particleLimit);
        setShouldLimitParticlesEveryNIterations(builder.shouldLimitParticlesEveryNIterations);
        setLimitParticlesEveryNIterations(builder.limitParticlesEveryNIterations);
        setWorld(builder.world);
        setYawOffset(builder.yawOffset);
        setPitchOffset(builder.pitchOffset);
        setYaw(builder.yaw);
        setPitch(builder.pitch);
        setShouldUpdateYPR(builder.shouldUpdateYPR);
        setTransparency(builder.transparency);
        setFrameDelay(builder.frameDelay);
        setStepX(builder.stepX);
        setStepY(builder.stepY);
        setScale(builder.scale);
        setParticleSize(builder.particleSize);
        setEnableRotation(builder.enableRotation);
        setRotation(builder.rotation);
        setOrient(builder.orient);
        setPlane(builder.plane);
        setAngularVelocityX(builder.angularVelocityX);
        setAngularVelocityY(builder.angularVelocityY);
        setAngularVelocityZ(builder.angularVelocityZ);
        setBlackAndWhite(builder.blackAndWhite);
        setInvertColors(builder.invertColors);

        if(builder.fileId != null){
            this.fileName = "id:"+builder.fileId;
            load(builder.fileId);
            return;
        }
        //This also loads the image
        setFileName(builder.fileName);
    }


    public static void copy(ImageEffect original, ImageEffect copy) {
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
        copy.setBlackAndWhite(original.isBlackAndWhite());
        copy.setInvertColors(original.isInvertColors());
        copy.images = original.images;
        copy.step = original.step;
        copy.rotationStepCounter = original.rotationStepCounter;
        copy.stepDelay = original.stepDelay;
        copy.imageLoadCallback = original.imageLoadCallback;
    }

    /** Returns a builder for the effect.
     *
     * @param world The world the particles are going to spawn in
     * @param fileName The path and the name of the file that you want to display. It can also be an URL.
     *      *                 You can also use {@code new Identifier(modid, resource).getPath()}. Supported formats include jpg, png, gif
     * @param originPos The origin position of the effect
     *<p>
     * Setting a world, an image path and an origin position is ALWAYS mandatory, hence their presence in this method!
     * If this is an effect that uses Yaw and Pitch, remember to set those as well!
     * */
    public static Builder builder(ServerWorld world, Vec3d originPos, String fileName) {
        return new Builder().world(world).fileName(fileName).originPos(originPos);
    }

    /** Returns a builder for the effect.
     *
     * @param world The world the particles are going to spawn in
     * @param image An identifier representing the image that will be displayed.
     *              <b>The image must be placed in the <i>/data/</i> folder and not the <i>/assets/</i> folder!</b>
     * @param originPos The origin position of the effect
     *<p>
     * Setting a world, an image path and an origin position is ALWAYS mandatory, hence their presence in this method!
     * If this is an effect that uses Yaw and Pitch, remember to set those as well!
     * */
    public static Builder builder(ServerWorld world, Vec3d originPos, Identifier image) {
        return new Builder().world(world).fileId(image).originPos(originPos);
    }


    /*
    public ImageEffect(ServerWorld world, Vec3d originPos, String image_fileName) {
        super(world, EffectType.REPEATING, null, originPos);
        this.fileName = image_fileName;
        if(fileName != null && !fileName.isBlank()){
            load(fileName);
        }else{
            ParticleAnimationLib.LOGGER.error("The file you have specified is invalid! The file path you have specified is: " + fileName);
            load(ERROR_IMAGE);
        }
    }

    public ImageEffect(ServerWorld world, Vec3d originPos, Identifier image) {
        super(world, EffectType.REPEATING, null, originPos);
        this.fileName = "id:"+image.toString();
        load(image);
    }

    //Used by the builder methods of the other Image effects
    public ImageEffect(ServerWorld world, Vec3d originPos, Identifier image, String image_fileName){
        super(world, EffectType.REPEATING, null, originPos);
        if(image != null){
            this.fileName = "id:"+image;
            load(image);
            return;
        }
        this.fileName = image_fileName;
        if(fileName != null && !fileName.isBlank()){
            load(fileName);
        }else{
            ParticleAnimationLib.LOGGER.error("The file you have specified is invalid! The file path you have specified is: " + fileName);
            load(ERROR_IMAGE);
        }
    }*/

    /**Automatically called when creating a new ImageEffect using a string path
     * Can be used to update the image that is displayed.
     * WARNING! Maye cause issues
     * */
    public void load(String fileName) {
        imageLoadCallback = i -> {
            images = i;
            imageLoadCallback = null;
        };
        loadImage(fileName, imageLoadCallback, this.getWorld().getServer());
    }

    /**Automatically called when creating a new ImageEffect using an Identifier path
     * Can be used to update the image that is displayed.
     * WARNING! Maye cause issues
     * */
    public void load(Identifier fileName) {
        imageLoadCallback = i -> {
            images = i;
            imageLoadCallback = null;
        };
        loadImage(fileName, imageLoadCallback, this.getWorld().getServer());
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

    /**It will also update the loaded image!*/
    public void setFileName(String fileName) {
        this.fileName = fileName;
        if(fileName != null && !fileName.isBlank()){
            load(fileName);
        }else{
            ParticleAnimationLib.LOGGER.error("The file you have specified is invalid! The file path you have specified is: " + fileName);
            load(ERROR_IMAGE);
        }
    }

    /**It will also update the loaded image!*/
    public void setFileId(Identifier fileId) {
        if(fileId != null){
            this.fileName = "id:"+fileId;
            load(fileId);
        }else{
            ParticleAnimationLib.LOGGER.error("The file you have specified is invalid! The file id you have specified is: " + fileId);
            load(ERROR_IMAGE);
        }
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

    public boolean isBlackAndWhite() {
        return blackAndWhite;
    }

    public void setBlackAndWhite(boolean blackAndWhite) {
        this.blackAndWhite = blackAndWhite;
    }

    public boolean isInvertColors() {
        return invertColors;
    }

    public void setInvertColors(boolean invert_colors) {
        this.invertColors = invert_colors;
    }


    public enum Plane {
        X, Y, Z, XY, XZ, XYZ, YZ
    }

    private static final int black = 0;
    private static final int white = 0;

    /**This methods handles the display of the image into particle form*/
    protected void display(BufferedImage image, Vec3d v, Vec3d pos, int pixel_color){

        //If the transparency is enabled, all pixels given to this function should be black
        if(this.blackAndWhite && this.transparency){
            pixel_color = black;
        }else if(this.blackAndWhite){
            //Otherwise, the pixel that are less dark the half of white, should be black the other whites
            //TODO this method needs some work i think. Maybe i should edit the image color above the chain
            if(pixel_color < white/2){
                pixel_color = black;
            }else{
                pixel_color = white;
            }
        }

        if(this.invertColors){
            pixel_color = white-pixel_color;
        }

        this.displayParticle(pos.add(v), pixel_color, particleSize);
    }

    /**
     * {@code ImageEffect} builder static inner class.
     */
    public static final class Builder {
        private int iterations;
        private Vec3d originPos;
        private boolean updatePositions;
        private boolean useEyePosAsOrigin;
        private Entity entityOrigin;
        private Vec3d originOffset;
        private EffectModifier executeOnStop;
        private boolean shouldSpawnParticlesEveryNIteration;
        private int spawnParticlesEveryNIteration;
        private boolean shouldLimitParticlesSpawnedPerIteration;
        private int particleLimit;
        private boolean shouldLimitParticlesEveryNIterations;
        private int limitParticlesEveryNIterations;
        private ServerWorld world;
        private float yawOffset;
        private float pitchOffset;
        private float yaw;
        private float pitch;
        private boolean shouldUpdateYPR;
        private String fileName;
        private boolean transparency;
        private int frameDelay;
        private int stepX;
        private int stepY;
        private float scale;
        private float particleSize;
        private boolean enableRotation;
        private Vec3d rotation;
        private boolean orient;
        private Plane plane;
        private double angularVelocityX;
        private double angularVelocityY;
        private double angularVelocityZ;
        private boolean blackAndWhite;
        private boolean invertColors;
        private Identifier fileId;

        public Builder() {
        }

        /**
         * Sets the {@code iterations} and returns a reference to this Builder enabling method chaining.
         *
         * @param val the {@code iterations} to set
         * @return a reference to this Builder
         */
        public Builder iterations(int val) {
            iterations = val;
            return this;
        }

        /**
         * Sets the {@code originPos} and returns a reference to this Builder enabling method chaining.
         *
         * @param val the {@code originPos} to set
         * @return a reference to this Builder
         */
        public Builder originPos(Vec3d val) {
            originPos = val;
            return this;
        }

        /**
         * Sets the {@code updatePositions} and returns a reference to this Builder enabling method chaining.
         *
         * @param val the {@code updatePositions} to set
         * @return a reference to this Builder
         */
        public Builder updatePositions(boolean val) {
            updatePositions = val;
            return this;
        }

        /**
         * Sets the {@code useEyePosAsOrigin} and returns a reference to this Builder enabling method chaining.
         *
         * @param val the {@code useEyePosAsOrigin} to set
         * @return a reference to this Builder
         */
        public Builder useEyePosAsOrigin(boolean val) {
            useEyePosAsOrigin = val;
            return this;
        }

        /**
         * Sets the {@code entityOrigin} and returns a reference to this Builder enabling method chaining.
         *
         * @param val the {@code entityOrigin} to set
         * @return a reference to this Builder
         */
        public Builder entityOrigin(Entity val) {
            entityOrigin = val;
            return this;
        }

        /**
         * Sets the {@code originOffset} and returns a reference to this Builder enabling method chaining.
         *
         * @param val the {@code originOffset} to set
         * @return a reference to this Builder
         */
        public Builder originOffset(Vec3d val) {
            originOffset = val;
            return this;
        }

        /**
         * Sets the {@code executeOnStop} and returns a reference to this Builder enabling method chaining.
         *
         * @param val the {@code executeOnStop} to set
         * @return a reference to this Builder
         */
        public Builder executeOnStop(EffectModifier val) {
            executeOnStop = val;
            return this;
        }

        /**
         * Sets the {@code shouldSpawnParticlesEveryNIteration} and returns a reference to this Builder enabling method chaining.
         *
         * @param val the {@code shouldSpawnParticlesEveryNIteration} to set
         * @return a reference to this Builder
         */
        public Builder shouldSpawnParticlesEveryNIteration(boolean val) {
            shouldSpawnParticlesEveryNIteration = val;
            return this;
        }

        /**
         * Sets the {@code spawnParticlesEveryNIteration} and returns a reference to this Builder enabling method chaining.
         *
         * @param val the {@code spawnParticlesEveryNIteration} to set
         * @return a reference to this Builder
         */
        public Builder spawnParticlesEveryNIteration(int val) {
            spawnParticlesEveryNIteration = val;
            return this;
        }

        /**
         * Sets the {@code shouldLimitParticlesSpawnedPerIteration} and returns a reference to this Builder enabling method chaining.
         *
         * @param val the {@code shouldLimitParticlesSpawnedPerIteration} to set
         * @return a reference to this Builder
         */
        public Builder shouldLimitParticlesSpawnedPerIteration(boolean val) {
            shouldLimitParticlesSpawnedPerIteration = val;
            return this;
        }

        /**
         * Sets the {@code particleLimit} and returns a reference to this Builder enabling method chaining.
         *
         * @param val the {@code particleLimit} to set
         * @return a reference to this Builder
         */
        public Builder particleLimit(int val) {
            particleLimit = val;
            return this;
        }

        /**
         * Sets the {@code shouldLimitParticlesEveryNIterations} and returns a reference to this Builder enabling method chaining.
         *
         * @param val the {@code shouldLimitParticlesEveryNIterations} to set
         * @return a reference to this Builder
         */
        public Builder shouldLimitParticlesEveryNIterations(boolean val) {
            shouldLimitParticlesEveryNIterations = val;
            return this;
        }

        /**
         * Sets the {@code limitParticlesEveryNIterations} and returns a reference to this Builder enabling method chaining.
         *
         * @param val the {@code limitParticlesEveryNIterations} to set
         * @return a reference to this Builder
         */
        public Builder limitParticlesEveryNIterations(int val) {
            limitParticlesEveryNIterations = val;
            return this;
        }

        /**
         * Sets the {@code world} and returns a reference to this Builder enabling method chaining.
         *
         * @param val the {@code world} to set
         * @return a reference to this Builder
         */
        public Builder world(ServerWorld val) {
            world = val;
            return this;
        }

        /**
         * Sets the {@code yawOffset} and returns a reference to this Builder enabling method chaining.
         *
         * @param val the {@code yawOffset} to set
         * @return a reference to this Builder
         */
        public Builder yawOffset(float val) {
            yawOffset = val;
            return this;
        }

        /**
         * Sets the {@code pitchOffset} and returns a reference to this Builder enabling method chaining.
         *
         * @param val the {@code pitchOffset} to set
         * @return a reference to this Builder
         */
        public Builder pitchOffset(float val) {
            pitchOffset = val;
            return this;
        }

        /**
         * Sets the {@code yaw} and returns a reference to this Builder enabling method chaining.
         *
         * @param val the {@code yaw} to set
         * @return a reference to this Builder
         */
        public Builder yaw(float val) {
            yaw = val;
            return this;
        }

        /**
         * Sets the {@code pitch} and returns a reference to this Builder enabling method chaining.
         *
         * @param val the {@code pitch} to set
         * @return a reference to this Builder
         */
        public Builder pitch(float val) {
            pitch = val;
            return this;
        }

        /**
         * Sets the {@code shouldUpdateYPR} and returns a reference to this Builder enabling method chaining.
         *
         * @param val the {@code shouldUpdateYPR} to set
         * @return a reference to this Builder
         */
        public Builder shouldUpdateYPR(boolean val) {
            shouldUpdateYPR = val;
            return this;
        }

        /**
         * Sets the {@code fileName} and returns a reference to this Builder enabling method chaining.
         *
         * @param val the {@code fileName} to set
         * @return a reference to this Builder
         */
        public Builder fileName(String val) {
            fileName = val;
            return this;
        }

        /**
         * Same as fileName but with an Identifier instead
         *
         * @param val the {@code fileName} to set
         * @return a reference to this Builder
         */
        public Builder fileId(Identifier val) {
            fileId = val;
            return this;
        }

        /**
         * Sets the {@code transparency} and returns a reference to this Builder enabling method chaining.
         *
         * @param val the {@code transparency} to set
         * @return a reference to this Builder
         */
        public Builder transparency(boolean val) {
            transparency = val;
            return this;
        }

        /**
         * Sets the {@code frameDelay} and returns a reference to this Builder enabling method chaining.
         *
         * @param val the {@code frameDelay} to set
         * @return a reference to this Builder
         */
        public Builder frameDelay(int val) {
            frameDelay = val;
            return this;
        }

        /**
         * Sets the {@code stepX} and returns a reference to this Builder enabling method chaining.
         *
         * @param val the {@code stepX} to set
         * @return a reference to this Builder
         */
        public Builder stepX(int val) {
            stepX = val;
            return this;
        }

        /**
         * Sets the {@code stepY} and returns a reference to this Builder enabling method chaining.
         *
         * @param val the {@code stepY} to set
         * @return a reference to this Builder
         */
        public Builder stepY(int val) {
            stepY = val;
            return this;
        }

        /**
         * Sets the {@code scale} and returns a reference to this Builder enabling method chaining.
         *
         * @param val the {@code scale} to set
         * @return a reference to this Builder
         */
        public Builder scale(float val) {
            scale = val;
            return this;
        }

        /**
         * Sets the {@code particleSize} and returns a reference to this Builder enabling method chaining.
         *
         * @param val the {@code particleSize} to set
         * @return a reference to this Builder
         */
        public Builder particleSize(float val) {
            particleSize = val;
            return this;
        }

        /**
         * Sets the {@code enableRotation} and returns a reference to this Builder enabling method chaining.
         *
         * @param val the {@code enableRotation} to set
         * @return a reference to this Builder
         */
        public Builder enableRotation(boolean val) {
            enableRotation = val;
            return this;
        }

        /**
         * Sets the {@code rotation} and returns a reference to this Builder enabling method chaining.
         *
         * @param val the {@code rotation} to set
         * @return a reference to this Builder
         */
        public Builder rotation(Vec3d val) {
            rotation = val;
            return this;
        }

        /**
         * Sets the {@code orient} and returns a reference to this Builder enabling method chaining.
         *
         * @param val the {@code orient} to set
         * @return a reference to this Builder
         */
        public Builder orient(boolean val) {
            orient = val;
            return this;
        }

        /**
         * Sets the {@code plane} and returns a reference to this Builder enabling method chaining.
         *
         * @param val the {@code plane} to set
         * @return a reference to this Builder
         */
        public Builder plane(Plane val) {
            plane = val;
            return this;
        }

        /**
         * Sets the {@code angularVelocityX} and returns a reference to this Builder enabling method chaining.
         *
         * @param val the {@code angularVelocityX} to set
         * @return a reference to this Builder
         */
        public Builder angularVelocityX(double val) {
            angularVelocityX = val;
            return this;
        }

        /**
         * Sets the {@code angularVelocityY} and returns a reference to this Builder enabling method chaining.
         *
         * @param val the {@code angularVelocityY} to set
         * @return a reference to this Builder
         */
        public Builder angularVelocityY(double val) {
            angularVelocityY = val;
            return this;
        }

        /**
         * Sets the {@code angularVelocityZ} and returns a reference to this Builder enabling method chaining.
         *
         * @param val the {@code angularVelocityZ} to set
         * @return a reference to this Builder
         */
        public Builder angularVelocityZ(double val) {
            angularVelocityZ = val;
            return this;
        }

        /**
         * Sets the {@code blackAndWhite} and returns a reference to this Builder enabling method chaining.
         *
         * @param val the {@code blackAndWhite} to set
         * @return a reference to this Builder
         */
        public Builder blackAndWhite(boolean val) {
            blackAndWhite = val;
            return this;
        }

        /**
         * Sets the {@code invertColors} and returns a reference to this Builder enabling method chaining.
         *
         * @param val the {@code invertColors} to set
         * @return a reference to this Builder
         */
        public Builder invertColors(boolean val) {
            invertColors = val;
            return this;
        }

        /**
         * Returns a {@code ImageEffect} built from the parameters previously set.
         *
         * @return a {@code ImageEffect} built with parameters of this {@code ImageEffect.Builder}
         */
        public ImageEffect build() {
            return new ImageEffect(this);
        }
    }
}
