package me.emafire003.dev.particleanimationlib.effects;

import java.awt.Font;
import java.awt.Color;
import java.util.Objects;
import java.awt.image.BufferedImage;

import me.emafire003.dev.particleanimationlib.EffectType;
import me.emafire003.dev.particleanimationlib.effects.base.YPREffect;
import me.emafire003.dev.particleanimationlib.util.EffectModifier;
import me.emafire003.dev.particleanimationlib.util.StringParser;
import me.emafire003.dev.particleanimationlib.util.VectorUtils;
import net.minecraft.entity.Entity;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.Vec3d;

public class TextEffect extends YPREffect {

    /**
     * Text to display.
     */
    public String text = "Text";

    /**
     * Invert the text, as in it makes it hollowed out of a rectangle background
     */
    public boolean invert = false;


    /**
     * Each stepX pixel will be shown. Saves packets for lower fontsizes.
     */
    public int stepX = 1;

    /**
     * Each stepY pixel will be shown. Saves packets for lower fontsizes.
     */
    public int stepY = 1;

    /**
     * Scale the font down
     */
    public float size = (float) 1 / 5;

    /**
     * Set this only to true if you are working with changing text. I'd advise
     * the parser to recalculate the BufferedImage every iteration.
     * Recommended FALSE
     */
    public boolean realtime = false;

    private static final Font DEFAULT_FONT = new Font("Sans Serif", Font.PLAIN, 10);

    /**
     * Font used to create the Text
     */
    public Font font = DEFAULT_FONT;


    //===Stuff used to calculate the text and such===

    /**
     * Contains an image version of the String
     */
    protected BufferedImage image = null;

    /**
     * Track the text used most recently when parsing
     */
    private String lastParsedText = null;

    /**
     * Track the font used most recently when parsing
     */
    private Font lastParsedFont = null;

    /*public TextEffect(EffectManager effectManager) {
        super(effectManager);
        font = new Font("Tahoma", Font.PLAIN, 16);
        type = EffectType.REPEATING;
        period = 40;
        iterations = 20;
    }*/

    
    /**
     * Creates a new TextEffect, displaying a given string with a given Font etc
     *
     * @param world The world the particles are going to spawn in
     * @param particle The particle effect that is going to be spawned. You can use {@link ParticleTypes}
     * @param originPos The origin position of the effect. Aka the center of the sphere
     * @param yaw The yaw of the effect. For example, you can get it from an Entity using getYaw()
     * @param pitch The pitch of the effect. For example, you can get it from an Entity using getPitch()
     * @param text A string of the text that will be displayed
     * @param invert Set this true if you want the text to be "hollowed out" of a rectangle display, instead of only shown. Be careful! Way more particles are going to be used in this case!
     * @param stepX Each stepX pixel will be shown. Saves packets for lower fontsizes.
     *              UNRECOMMENDED: May very likely butcher the text if it's not set to 1
     * @param stepY Each stepX pixel will be shown. Saves packets for lower fontsizes.
     *              UNRECOMMENDED: May very likely butcher the text if it's not set to 1
     * @param size The size of the text to display. Keep it around 1/5 to be displayed correctly with fonts like Times New Roman and Thaoma
     * @param realtime Set this to True if you want to update the text that is displayed
     * @param font The font that will be applied to the displayed Text
     * */
    public TextEffect(ServerWorld world, ParticleEffect particle, Vec3d originPos, float yaw, float pitch, String text,
                      boolean invert, int stepX, int stepY, float size, boolean realtime, Font font) {
        super(world, EffectType.REPEATING, particle, originPos);
        this.yaw = yaw;
        this.pitch = pitch;
        this.text = text;
        this.invert = invert;
        this.stepX = stepX;
        this.stepY = stepY;
        this.size = size;
        this.realtime = realtime;
        this.font = font;
    }

    private TextEffect(Builder builder) {
        super(builder.world, EffectType.REPEATING, builder.particle, builder.originPos);
        setIterations(builder.iterations);
        setOriginPos(builder.originPos);
        setUpdatePositions(builder.updatePositions);
        setUseEyePosAsOrigin(builder.useEyePosAsOrigin);
        setEntityOrigin(builder.entityOrigin);
        setOriginOffset(builder.originOffset);
        world = builder.world;
        particle = builder.particle;
        setYawOffset(builder.yawOffset);
        setPitchOffset(builder.pitchOffset);
        setYaw(builder.yaw);
        setPitch(builder.pitch);
        setShouldUpdateYPR(builder.shouldUpdateYPR);
        setText(builder.text);
        setInvert(builder.invert);
        setStepX(builder.stepX);
        setStepY(builder.stepY);
        setSize(builder.size);
        setRealtime(builder.realtime);
        setFont(builder.font);
        setExecuteOnStop(builder.executeOnStop);
    }

    public static void copy(TextEffect original, TextEffect  copy) {
        YPREffect.copy(original, copy);
        copy.setFont(original.getFont());
        copy.setText(original.getText());
        copy.setSize(original.getSize());
        copy.setStepX(original.getStepX());
        copy.setStepY(original.getStepY());
        copy.setInvert(original.isInvert());
        copy.setRealtime(original.isRealtime());
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public boolean isInvert() {
        return invert;
    }

    public void setInvert(boolean invert) {
        this.invert = invert;
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

    public float getSize() {
        return size;
    }

    public void setSize(float size) {
        this.size = size;
    }

    public boolean isRealtime() {
        return realtime;
    }

    public void setRealtime(boolean realtime) {
        this.realtime = realtime;
    }

    public void setFont(Font font) {
        this.font = font;
    }
    
    public Font getFont() {
        return font;
    }


//TODO add a parameter to skip an iteration to save on number of particles/memory?

    @Override
    public void onRun() {
        if (font == null) {
            return;
        }

        Vec3d origin_pos = this.getOriginPos();

        if (origin_pos == null) {
            return;
        }

        int color;
        Vec3d v;

        try {
            if (image == null || shouldRecalculateImage()) {
                lastParsedText = text;
                lastParsedFont = font;
                // Use last parsed references instead for additional thread safety
                image = StringParser.stringToBufferedImage(lastParsedFont, lastParsedText);
            }
            for (int y = 0; y < image.getHeight(); y += stepY) {
                for (int x = 0; x < image.getWidth(); x += stepX) {
                    color = image.getRGB(x, y);
                    if (!invert && Color.black.getRGB() != color) continue;
                    else if (invert && Color.black.getRGB() == color) continue;

                    v = new Vec3d((float) image.getWidth() / 2 - x, (float) image.getHeight() / 2 - y, 0).multiply(size);
                    //VectorUtils.rotateAroundAxisY(v, -origin_pos.getYaw() * MathUtils.degreesToRadians);
                    //v = VectorUtils.rotateVector(v, this.getYaw()-90, this.getPitch());
                    //TODO find another way to rotate the vector
                    //v = VectorUtils.rotateVector(v, this.getYaw()-90, this.getPitch());
                    v = VectorUtils.rotateVector(v, this.getYaw()-90, 0);
                    this.displayParticle(particle, originPos.add(v));
                }
            }
        } catch (Exception ex) {
            // This seems to happen on bad characters in strings,
            // I'm choosing to ignore the exception and cancel the effect for now.
            return;
        }

    }

    private boolean shouldRecalculateImage() {
        // Don't bother if we don't use real time updates
        if (!realtime) return false;

        // Text content or font is different, recalculate
        return !Objects.equals(lastParsedText, text) || !Objects.equals(lastParsedFont, font);
    }


    /** Returns a builder for the effect.
     *
     * @param world The world the particles are going to spawn in
     * @param particle The particle effect that is going to be spawned. You can use {@link ParticleTypes}
     * @param originPos The origin position of the effect
     *<p>
     *  Setting a world, a particle effect and an origin position is ALWAYS mandatory, hence their presence in this method!
     * If this is an effect that uses Yaw and Pitch, remember to set those as well!
     * */
    public static Builder builder(ServerWorld world, ParticleEffect particle, Vec3d originPos) {
        return new Builder().world(world).particle(particle).originPos(originPos);
    }
    
    /**
     * {@code TextEffect} builder static inner class.
     */
    public static final class Builder {
        private int iterations;
        private Vec3d originPos;
        private boolean updatePositions;
        private boolean useEyePosAsOrigin;
        private Entity entityOrigin;
        private Vec3d originOffset;
        private ServerWorld world;
        private ParticleEffect particle;
        private float yawOffset;
        private float pitchOffset;
        private float yaw;
        private float pitch;
        private boolean shouldUpdateYPR;
        /**
         * Text to display
         */
        public String text = "Text";

        /**
         * Invert the text, as in it makes it hollowed out of a rectangle background
         */
        public boolean invert = false;


        /**
         * Each stepX pixel will be shown. Saves packets for lower fontsizes.
         */
        public int stepX = 1;

        /**
         * Each stepY pixel will be shown. Saves packets for lower fontsizes.
         */
        public int stepY = 1;

        /**
         * Scale the font down
         */
        public float size = (float) 1 / 5;

        /**
         * Set this only to true if you are working with changing text. I'd advise
         * the parser to recalculate the BufferedImage every iteration.
         * Recommended FALSE
         */
        public boolean realtime = false;

        /**
         * Font used to create the Text
         */
        public Font font = DEFAULT_FONT;
        private EffectModifier executeOnStop;

        private Builder() {
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
         * Sets the {@code particle} and returns a reference to this Builder enabling method chaining.
         *
         * @param val the {@code particle} to set
         * @return a reference to this Builder
         */
        public Builder particle(ParticleEffect val) {
            particle = val;
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
         * Sets the {@code text} and returns a reference to this Builder enabling method chaining.
         *
         * @param val the {@code text} to set
         * @return a reference to this Builder
         */
        public Builder text(String val) {
            text = val;
            return this;
        }

        /**
         * Sets the {@code invert} and returns a reference to this Builder enabling method chaining.
         *
         * @param val the {@code invert} to set
         * @return a reference to this Builder
         */
        public Builder invert(boolean val) {
            invert = val;
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
         * Sets the {@code size} and returns a reference to this Builder enabling method chaining.
         *
         * @param val the {@code size} to set
         * @return a reference to this Builder
         */
        public Builder size(float val) {
            size = val;
            return this;
        }

        /**
         * Sets the {@code realtime} and returns a reference to this Builder enabling method chaining.
         *
         * @param val the {@code realtime} to set
         * @return a reference to this Builder
         */
        public Builder realtime(boolean val) {
            realtime = val;
            return this;
        }

        /**
         * Sets the {@code font} and returns a reference to this Builder enabling method chaining.
         *
         * @param val the {@code font} to set
         * @return a reference to this Builder
         */
        public Builder font(Font val) {
            font = val;
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
         * Returns a {@code TextEffect} built from the parameters previously set.
         *
         * @return a {@code TextEffect} built with parameters of this {@code TextEffect.Builder}
         */
        public TextEffect build() {
            return new TextEffect(this);
        }
    }
}