package me.emafire003.dev.particleanimationlib.effects;

import java.awt.Font;
import java.awt.Color;
import java.util.Objects;
import java.awt.image.BufferedImage;



import me.emafire003.dev.particleanimationlib.EffectType;
import me.emafire003.dev.particleanimationlib.effects.base.YPREffect;
import me.emafire003.dev.particleanimationlib.util.StringParser;
import me.emafire003.dev.particleanimationlib.util.VectorUtils;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.Vec3d;

public class TextEffect extends YPREffect {

    /**
     * Text to display
     */
    public String text = "Text";

    /**
     * Invert the text TODO as in it flips it on the axis?
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
     * Set this only to true if you are working with changing text. I'll advice
     * the parser to recalculate the BufferedImage every iteration.
     * Recommended FALSE
     */
    public boolean realtime = false;

    /**
     * Font to create the Text
     */
    public Font font;

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

    public TextEffect(ServerWorld world, ParticleEffect particle, Vec3d originPos, float yaw, float pitch, String text, boolean invert, int stepX, int stepY, float size, boolean realtime, Font font) {
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

    public void setFont(Font font) {
        this.font = font;
    }


//TODO add a parameter to skip an iteration to save on number of particles/memory

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
                    VectorUtils.rotateVector(v, -this.getYaw(), this.getPitch()+90);
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

}