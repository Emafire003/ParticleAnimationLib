package me.emafire003.dev.particleanimationlib.effects.image;

import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.Vec3d;

import java.awt.image.BufferedImage;

/**Render a monochrome image from an image file or animated gif.
 Black pixels will be skipped, all other pixels display as the selected particle type.

 Meaning the source image must be black and white. (I don't get it, can't you use a normal ColoredImage?)
 <a href="https://reference.elmakers.com/#effectlib.image">...</a>
 */
public class ImageEffect extends BaseImageEffect {

    /**
     * Invert the image
     */
    public boolean invert = false;

    public ImageEffect(ServerWorld world, Vec3d originPos, String image_fileName) {
        super(world, originPos, image_fileName);
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

        //TODO make size configurable
        //TODO allow usage of other types of particles
        this.displayParticle(pos.add(v), pixel_color, 1f);
    }
}
