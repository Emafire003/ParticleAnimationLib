package me.emafire003.dev.particleanimationlib.effects.image;

import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.Vec3d;

import java.awt.image.BufferedImage;

/**Draw an image or animated gif using colored particles*/
public class ColoredImageEffect extends BaseImageEffect {

    public ColoredImageEffect(ServerWorld world, Vec3d originPos, String image_fileName) {
        super(world, originPos, image_fileName);
    }

    protected void display(BufferedImage image, Vec3d v, Vec3d pos, int pixel_color) {
        //TODO this is the effect's display. Also, i can only use dust particles right?

        //TODO make size configurable
        this.displayParticle(pos.add(v), pixel_color, 1f);
    }


}
