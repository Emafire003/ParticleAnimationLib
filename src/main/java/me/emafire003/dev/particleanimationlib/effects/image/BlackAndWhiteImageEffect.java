package me.emafire003.dev.particleanimationlib.effects.image;

import me.emafire003.dev.particleanimationlib.ParticleAnimationLib;
import me.emafire003.dev.particleanimationlib.util.EffectModifier;
import net.minecraft.entity.Entity;
import net.minecraft.particle.DustParticleEffect;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Vec3d;

import java.awt.image.BufferedImage;

/**Render a monochrome image from an image file or animated gif.
 Black pixels will be skipped, all other pixels display as the selected particle type.

 Meaning the source image must be black and white. (I don't get it, can't you use a normal ColoredImage?)
 <a href="https://reference.elmakers.com/#effectlib.image">...</a>
 */
@SuppressWarnings("unused")
public class BlackAndWhiteImageEffect extends BaseImageEffect {

    /**
     * Invert the image
     */
    public boolean invert = false;

    public BlackAndWhiteImageEffect(ServerWorld world, Vec3d originPos, String image_fileName) {
        super(world, originPos, image_fileName);
    }

    public BlackAndWhiteImageEffect(ServerWorld world, Vec3d originPos, Identifier image) {
        super(world, originPos, image.getPath());
    }

    /**
     * Creates a new Colored Image effect
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
     * @param particleSize How big should each individual Dust particle be? See also {@link DustParticleEffect}
     * @param rotation Apply a fixed rotation (independent of enableRotation)
     * @param orient Should the image orient towards yaw and pitch? For example orient to the player's facing direction
     * @param enableRotation Should it rotate?
     * @param plane What plane should it rotate?
     * @param angularVelocityX Turns the image by this angle each iteration around the x-axis (radians)
     * @param angularVelocityY Turns the image by this angle each iteration around the y-axis (radians)
     * @param angularVelocityZ Turns the image by this angle each iteration around the z-axis (radians)
     * @param invert Inverts the image color
     * */
    public BlackAndWhiteImageEffect(ServerWorld world, Vec3d origin, float yaw, float pitch, String fileName, boolean transparency, int frameDelay, int stepX, int stepY, float scale, float particleSize, Vec3d rotation, boolean orient, boolean enableRotation, Plane plane, double angularVelocityX, double angularVelocityY, double angularVelocityZ, boolean invert) {
        super(world, origin, fileName);
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
        this.invert = true;
    }

    private BlackAndWhiteImageEffect(Builder builder) {
        super(builder.world, builder.originPos, builder.fileId, builder.fileName);
        setIterations(builder.iterations);
        setOriginPos(builder.originPos);
        setUpdatePositions(builder.updatePositions);
        setUseEyePosAsOrigin(builder.useEyePosAsOrigin);
        setEntityOrigin(builder.entityOrigin);
        setOriginOffset(builder.originOffset);
        setExecuteOnStop(builder.executeOnStop);
        setWorld(builder.world);
        setYawOffset(builder.yawOffset);
        setPitchOffset(builder.pitchOffset);
        setYaw(builder.yaw);
        setPitch(builder.pitch);
        setShouldUpdateYPR(builder.shouldUpdateYPR);
        setFileName(builder.fileName);
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
        setInvert(builder.invert);
    }

    public static void copy(BlackAndWhiteImageEffect original, BlackAndWhiteImageEffect copy) {
        BaseImageEffect.copy(original, copy);
        copy.setInvert(original.isInvert());
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

    @Override
    protected void display(BufferedImage image, Vec3d v, Vec3d pos, int pixel_color) {
        //This is the decimal color value for black btw
        int black = 0;

        if (!invert && black != pixel_color){
            return;
        } else if (invert && black == pixel_color){
            return;
        }
        this.displayParticle(pos.add(v), pixel_color,  particleSize);
    }

    public boolean isInvert() {
        return invert;
    }

    public void setInvert(boolean invert) {
        this.invert = invert;
    }

    /**
     * {@code BlackAndWhiteImageEffect} builder static inner class.
     */
    public static final class Builder {
        private Identifier fileId = null;
        private int iterations;
        private Vec3d originPos;
        private boolean updatePositions;
        private boolean useEyePosAsOrigin;
        private Entity entityOrigin;
        private Vec3d originOffset;
        private EffectModifier executeOnStop;
        private ServerWorld world;
        private float yawOffset;
        private float pitchOffset;
        private float yaw;
        private float pitch;
        private boolean shouldUpdateYPR;
        /**
         * For configuration-driven files
         */
        private String fileName;

        /**
         * Whether or not to check for transparent pixels
         */
        private boolean transparency = false;

        /**
         * How many ticks to show each frame
         */
        private int frameDelay = 5;

        /**
         * Each stepX pixel will be shown. Saves packets for high resolutions.
         */
        private int stepX = 5;

        /**
         * Each stepY pixel will be shown. Saves packets for high resolutions.
         */
        private int stepY = 5;

        /**
         * Scale the image down
         */
        private float scale = (float) 1 / 40;

        /**How big should the dust particles be?*/
        private float particleSize = 1f;

        /**
         * Should it rotate?
         */
        private boolean enableRotation = true;

        /**
         * Apply a fixed rotation
         */
        private Vec3d rotation = null;

        /**Orients the image to the specified Yaw Pitch, for example facing a player*/
        private boolean orient = false;

        /**
         * What plane should it rotate?
         */
        private Plane plane = Plane.XYZ;

        /**
         * Turns the image by this angle each iteration around the x-axis
         */
        private double angularVelocityX = Math.PI / 200;

        /**
         * Turns the image by this angle each iteration around the y-axis
         */
        private double angularVelocityY = Math.PI / 170;

        /**
         * Turns the image by this angle each iteration around the z-axis
         */
        private double angularVelocityZ = Math.PI / 155;
        private boolean invert = false;

        private Builder() {
        }

        /**
         * Sets the {@code fileId} and returns a reference to this Builder enabling method chaining.
         * This will override any specified string filename
         *
         * @param fileId the {@code fileId} to set
         * @return a reference to this Builder
         */
        public Builder fileId(Identifier fileId) {
            this.fileId = fileId;
            return this;
        }

        /**
         * Sets the {@code iterations} and returns a reference to this Builder enabling method chaining.
         *
         * @param iterations the {@code iterations} to set
         * @return a reference to this Builder
         */
        public Builder iterations(int iterations) {
            this.iterations = iterations;
            return this;
        }

        /**
         * Sets the {@code originPos} and returns a reference to this Builder enabling method chaining.
         *
         * @param originPos the {@code originPos} to set
         * @return a reference to this Builder
         */
        public Builder originPos(Vec3d originPos) {
            this.originPos = originPos;
            return this;
        }

        /**
         * Sets the {@code updatePositions} and returns a reference to this Builder enabling method chaining.
         *
         * @param updatePositions the {@code updatePositions} to set
         * @return a reference to this Builder
         */
        public Builder updatePositions(boolean updatePositions) {
            this.updatePositions = updatePositions;
            return this;
        }

        /**
         * Sets the {@code useEyePosAsOrigin} and returns a reference to this Builder enabling method chaining.
         *
         * @param useEyePosAsOrigin the {@code useEyePosAsOrigin} to set
         * @return a reference to this Builder
         */
        public Builder useEyePosAsOrigin(boolean useEyePosAsOrigin) {
            this.useEyePosAsOrigin = useEyePosAsOrigin;
            return this;
        }

        /**
         * Sets the {@code entityOrigin} and returns a reference to this Builder enabling method chaining.
         *
         * @param entityOrigin the {@code entityOrigin} to set
         * @return a reference to this Builder
         */
        public Builder entityOrigin(Entity entityOrigin) {
            this.entityOrigin = entityOrigin;
            return this;
        }

        /**
         * Sets the {@code originOffset} and returns a reference to this Builder enabling method chaining.
         *
         * @param originOffset the {@code originOffset} to set
         * @return a reference to this Builder
         */
        public Builder originOffset(Vec3d originOffset) {
            this.originOffset = originOffset;
            return this;
        }

        /**
         * Sets the {@code executeOnStop} and returns a reference to this Builder enabling method chaining.
         *
         * @param executeOnStop the {@code executeOnStop} to set
         * @return a reference to this Builder
         */
        public Builder executeOnStop(EffectModifier executeOnStop) {
            this.executeOnStop = executeOnStop;
            return this;
        }

        /**
         * Sets the {@code world} and returns a reference to this Builder enabling method chaining.
         *
         * @param world the {@code world} to set
         * @return a reference to this Builder
         */
        public Builder world(ServerWorld world) {
            this.world = world;
            return this;
        }

        /**
         * Sets the {@code yawOffset} and returns a reference to this Builder enabling method chaining.
         *
         * @param yawOffset the {@code yawOffset} to set
         * @return a reference to this Builder
         */
        public Builder yawOffset(float yawOffset) {
            this.yawOffset = yawOffset;
            return this;
        }

        /**
         * Sets the {@code pitchOffset} and returns a reference to this Builder enabling method chaining.
         *
         * @param pitchOffset the {@code pitchOffset} to set
         * @return a reference to this Builder
         */
        public Builder pitchOffset(float pitchOffset) {
            this.pitchOffset = pitchOffset;
            return this;
        }

        /**
         * Sets the {@code yaw} and returns a reference to this Builder enabling method chaining.
         *
         * @param yaw the {@code yaw} to set
         * @return a reference to this Builder
         */
        public Builder yaw(float yaw) {
            this.yaw = yaw;
            return this;
        }

        /**
         * Sets the {@code pitch} and returns a reference to this Builder enabling method chaining.
         *
         * @param pitch the {@code pitch} to set
         * @return a reference to this Builder
         */
        public Builder pitch(float pitch) {
            this.pitch = pitch;
            return this;
        }

        /**
         * Sets the {@code shouldUpdateYPR} and returns a reference to this Builder enabling method chaining.
         *
         * @param shouldUpdateYPR the {@code shouldUpdateYPR} to set
         * @return a reference to this Builder
         */
        public Builder shouldUpdateYPR(boolean shouldUpdateYPR) {
            this.shouldUpdateYPR = shouldUpdateYPR;
            return this;
        }

        /**
         * Sets the {@code fileName} and returns a reference to this Builder enabling method chaining.
         *
         * @param fileName the {@code fileName} to set
         * @return a reference to this Builder
         */
        public Builder fileName(String fileName) {
            this.fileName = fileName;
            return this;
        }

        /**
         * Sets the {@code transparency} and returns a reference to this Builder enabling method chaining.
         *
         * @param transparency the {@code transparency} to set
         * @return a reference to this Builder
         */
        public Builder transparency(boolean transparency) {
            this.transparency = transparency;
            return this;
        }

        /**
         * Sets the {@code frameDelay} and returns a reference to this Builder enabling method chaining.
         *
         * @param frameDelay the {@code frameDelay} to set
         * @return a reference to this Builder
         */
        public Builder frameDelay(int frameDelay) {
            this.frameDelay = frameDelay;
            return this;
        }

        /**
         * Sets the {@code stepX} and returns a reference to this Builder enabling method chaining.
         *
         * @param stepX the {@code stepX} to set
         * @return a reference to this Builder
         */
        public Builder stepX(int stepX) {
            this.stepX = stepX;
            return this;
        }

        /**
         * Sets the {@code stepY} and returns a reference to this Builder enabling method chaining.
         *
         * @param stepY the {@code stepY} to set
         * @return a reference to this Builder
         */
        public Builder stepY(int stepY) {
            this.stepY = stepY;
            return this;
        }

        /**
         * Sets the {@code scale} and returns a reference to this Builder enabling method chaining.
         *
         * @param scale the {@code scale} to set
         * @return a reference to this Builder
         */
        public Builder scale(float scale) {
            this.scale = scale;
            return this;
        }

        /**
         * Sets the {@code particleSize} and returns a reference to this Builder enabling method chaining.
         *
         * @param particleSize the {@code particleSize} to set
         * @return a reference to this Builder
         */
        public Builder particleSize(float particleSize) {
            this.particleSize = particleSize;
            return this;
        }

        /**
         * Sets the {@code enableRotation} and returns a reference to this Builder enabling method chaining.
         *
         * @param enableRotation the {@code enableRotation} to set
         * @return a reference to this Builder
         */
        public Builder enableRotation(boolean enableRotation) {
            this.enableRotation = enableRotation;
            return this;
        }

        /**
         * Sets the {@code rotation} and returns a reference to this Builder enabling method chaining.
         *
         * @param rotation the {@code rotation} to set
         * @return a reference to this Builder
         */
        public Builder rotation(Vec3d rotation) {
            this.rotation = rotation;
            return this;
        }

        /**
         * Sets the {@code orient} and returns a reference to this Builder enabling method chaining.
         *
         * @param orient the {@code orient} to set
         * @return a reference to this Builder
         */
        public Builder orient(boolean orient) {
            this.orient = orient;
            return this;
        }

        /**
         * Sets the {@code plane} and returns a reference to this Builder enabling method chaining.
         *
         * @param plane the {@code plane} to set
         * @return a reference to this Builder
         */
        public Builder plane(Plane plane) {
            this.plane = plane;
            return this;
        }

        /**
         * Sets the {@code angularVelocityX} and returns a reference to this Builder enabling method chaining.
         *
         * @param angularVelocityX the {@code angularVelocityX} to set
         * @return a reference to this Builder
         */
        public Builder angularVelocityX(double angularVelocityX) {
            this.angularVelocityX = angularVelocityX;
            return this;
        }

        /**
         * Sets the {@code angularVelocityY} and returns a reference to this Builder enabling method chaining.
         *
         * @param angularVelocityY the {@code angularVelocityY} to set
         * @return a reference to this Builder
         */
        public Builder angularVelocityY(double angularVelocityY) {
            this.angularVelocityY = angularVelocityY;
            return this;
        }

        /**
         * Sets the {@code angularVelocityZ} and returns a reference to this Builder enabling method chaining.
         *
         * @param angularVelocityZ the {@code angularVelocityZ} to set
         * @return a reference to this Builder
         */
        public Builder angularVelocityZ(double angularVelocityZ) {
            this.angularVelocityZ = angularVelocityZ;
            return this;
        }

        /**
         * Sets the {@code invert} and returns a reference to this Builder enabling method chaining.
         *
         * @param invert the {@code invert} to set
         * @return a reference to this Builder
         */
        public Builder invert(boolean invert) {
            this.invert = invert;
            return this;
        }

        /**
         * Returns a {@code BlackAndWhiteImageEffect} built from the parameters previously set.
         *
         * @return a {@code BlackAndWhiteImageEffect} built with parameters of this {@code BlackAndWhiteImageEffect.Builder}
         */
        public BlackAndWhiteImageEffect build() {
            return new BlackAndWhiteImageEffect(this);
        }
    }
}
