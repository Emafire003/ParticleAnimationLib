package me.emafire003.dev.particleanimationlib.effects;

import me.emafire003.dev.particleanimationlib.EffectType;
import me.emafire003.dev.particleanimationlib.EffectV3;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.Vec3d;

public class ArcEffect extends EffectV3 {

    /**
     * Height of the arc in blocks
     */
    public float height = 2;

    /**
     * Particles per arc
     */
    public int particles = 100;

    /**
     * Internal counter
     */
    protected int step = 0;

    public ArcEffect(ServerWorld world, ParticleEffect particle, int count, int height, int iterations) {
        super(world, EffectType.REPEATING, particle);
        this.particles = count;
        this.height = height;
        this.iterations = iterations;
    }

    @Override
    public void onRun() {
        Vec3d origin = this.getOriginPos();
        Vec3d target = this.getTargetPos();

        if (target == null) {
            return;
        }

        if (origin == null) {
            return;
        }

        Vec3d link = target.subtract(origin);
        float length = (float) link.length();
        float pitch = (float) (4 * height / Math.pow(length, 2));

        Vec3d v;
        float x;
        float y;

        for (int i = 0; i < particles; i++) {
            v = new Vec3d(link.getX(), link.getY(), link.getZ()).multiply(length * i / particles);
            x = ((float) i / particles) * length - length / 2;
            y = (float) (-pitch * Math.pow(x, 2) + height);

            Vec3d pos = origin.add(v).add(0, y, 0);
            this.displayParticle(particle, pos);

            step++;
        }
    }

}
