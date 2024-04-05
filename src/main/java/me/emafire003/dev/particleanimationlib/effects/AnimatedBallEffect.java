package me.emafire003.dev.particleanimationlib.effects;

import me.emafire003.dev.particleanimationlib.EffectType;
import me.emafire003.dev.particleanimationlib.EffectV3;
import me.emafire003.dev.particleanimationlib.util.MathUtils;
import me.emafire003.dev.particleanimationlib.util.VectorUtils;
import net.minecraft.entity.Entity;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

/**
 * Creates an animated Sphere.. Thanks to the author for sharing it!
 * <a href="https://www.youtube.com/watch?feature=player_embedded&v=RUjIw_RprRw">Demo on YouTube</a>
 *
 * @author <a href="http://forums.bukkit.org/members/qukie.90952701/">Qukie</a>
 */
public class AnimatedBallEffect extends EffectV3 {

    /**
     * Ball particles total (150)
     */
    public int particles = 150;

    /**
     * The amount of particles, displayed in one iteration (10)
     */
    public int particlesPerIteration = 10;

    /**
     * Size of this ball (1)
     */
    public float size = 1F;

    /**
     * Factors (1, 2, 1)
     *
     * TODO why the fuck was it 2? It's not a ball that way it's an oval
     * TODO Make these variables configurable as well, like "strechiness" or something
     */
    public float xFactor = 1F, yFactor = 1F, zFactor = 1F;

    /**
     * Offsets (0, 0.8, 0)
     *
     * TODO make sure this doen't do weird stuff.
     */
    public float xOffset, yOffset = 0.8F, zOffset;

    /**
     * Rotation of the ball.
     */
    public double xRotation, yRotation, zRotation = 0;

    /**
     * Internal Counter
     */
    protected int step = 0;

    World world;

    public AnimatedBallEffect(ParticleEffect particle, ServerWorld world, int count, int iterations, int particles_per_iteration, float size) {
        super(world, EffectType.REPEATING, particle);
        //TODO make configurable
        this.particle = particle;
        this.world = world;
        this.particles = count;
        this.iterations = iterations;
        this.size = size;
        this.particlesPerIteration = particles_per_iteration;
    }

    public AnimatedBallEffect(ParticleEffect particle, ServerWorld world, Vec3d origin_pos, int count, int iterations, int particles_per_iteration, float size) {
        super(world, EffectType.REPEATING, particle);
        //TODO make configurable
        this.particle = particle;
        this.origin_pos = origin_pos;
        this.world = world;
        this.particles = count;
        this.iterations = iterations;
        this.size = size;
        this.particlesPerIteration = particles_per_iteration;
    }

    public AnimatedBallEffect(ParticleEffect particle, ServerWorld world, Entity centeredEntity, int count, int iterations, int particles_per_iteration, float size) {
        super(world, EffectType.REPEATING, particle);
        //TODO make configurable
        this.particle = particle;
        this.centeredOriginEntity = centeredEntity;
        this.updatePositions = true;
        this.world = world;
        this.particles = count;
        this.iterations = iterations;
        this.size = size;
        this.particlesPerIteration = particles_per_iteration;
    }


    @Override
    public void onRun() {
        //TODO add entity locking!
        Vec3d pos = getOriginPos();

        if (pos == null) {
            return;
        }

        float t;
        float r;
        float s;

        for (int i = 0; i < particlesPerIteration; i++) {
            step++;

            t = (MathUtils.PI / particles) * step;
            r = MathUtils.sin(t) * size;
            s = 2 * MathUtils.PI * t;
            double x = (xFactor * r * MathUtils.cos(s) + xOffset);
            double y = (yFactor * size * MathUtils.cos(t) + yOffset);
            double z = (zFactor * r * MathUtils.sin(s) + zOffset);


            Vec3d vector = new Vec3d(x,y,z);
            //TODO ah cool this didn't work
            vector = VectorUtils.rotateVector(vector, (float) xRotation, (float) yRotation, (float) zRotation);

            this.displayParticle(this.particle, origin_pos.add(vector));
        }
    }

}
