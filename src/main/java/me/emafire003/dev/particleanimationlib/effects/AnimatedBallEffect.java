package me.emafire003.dev.particleanimationlib.effects;

import me.emafire003.dev.particleanimationlib.EffectType;
import me.emafire003.dev.particleanimationlib.EffectV3;
import me.emafire003.dev.particleanimationlib.util.MathUtils;
import me.emafire003.dev.particleanimationlib.util.VectorUtils;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.Vec3d;

/**
 * Creates an animated Sphere.. Thanks to the author for sharing it!
 * <a href="https://www.youtube.com/watch?feature=player_embedded&v=RUjIw_RprRw">Demo on YouTube</a>
 *
 * @author <a href="http://forums.bukkit.org/members/qukie.90952701/">Qukie</a>
 */
@SuppressWarnings("unused")
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
     * Factors (1, 1, 1)
     * 
     * Aka dimensions of the ball, like the "diameters"
     */
    public float xFactor = 1F, yFactor = 1F, zFactor = 1F;

    /**
     * Offsets (0, 0.8, 0)
     *
     */
    //public float xOffset, yOffset = 0.8F, zOffset;

    /**
     * Rotation of the ball.
     */
    public double xRotation, yRotation, zRotation = 0;

    
    
    /**
     * Internal Counter
     */
    protected int step = 0;

    /**
     * Creates a new animated "ball" effect.
     *
     * @param world The world the particles are going to spawn in
     * @param particle The particle that are going to be spawned. You can use {@link net.minecraft.particle.ParticleTypes}
     * @param origin The origin position of the effect
     * @param count The total number of particles that will be displayed
     * @param particles_per_iteration The number of particles displayed in each iteration
     * @param size The size of the ball effect
     * @param factors A {@link Vec3d} of xyz float factors for the ball effect. Non-uniform values will elongate the ball in one direction, for example (1,2,1) makes a vertical oval
     * @param rotation A {@link Vec3d} of xyz rotations (in radians) for the ball effect.
     * */
    public AnimatedBallEffect(ServerWorld world, ParticleEffect particle, Vec3d origin, int count, int particles_per_iteration, float size, Vec3d factors, Vec3d rotation) {
        super(world, EffectType.REPEATING, particle);
        this.particle = particle;
        this.world = world;
        this.particles = count;
        this.size = size;
        this.setOriginPos(origin);
        this.particlesPerIteration = particles_per_iteration;
        this.xFactor = (float) factors.getX();
        this.yFactor = (float) factors.getY();
        this.zFactor = (float) factors.getZ();
        this.xRotation = rotation.getX();
        this.yRotation = rotation.getY();
        this.zRotation = rotation.getZ();
    }

    /**
     * Creates a new animated "ball" effect.
     *
     * @param world The world the particles are going to spawn in
     * @param particle The particle that are going to be spawned. You can use {@link net.minecraft.particle.ParticleTypes}
     * @param origin The origin position of the effect
      */
    public AnimatedBallEffect(ServerWorld world, ParticleEffect particle, Vec3d origin) {
        super(world, EffectType.REPEATING, particle);
        this.particle = particle;
        this.world = world;
        this.originPos = origin;
    }

    /**
     * Creates a new animated "ball" effect.
     *
     * @param world The world the particles are going to spawn in
     * @param particle The particle that are going to be spawned. You can use {@link net.minecraft.particle.ParticleTypes}
     * @param origin The origin position of the effect
     * @param count The total number of particles that will be displayed
     * @param particles_per_iteration The number of particles displayed in each iteration
     * @param size The size of the ball effect
     */
    public AnimatedBallEffect(ServerWorld world, ParticleEffect particle, Vec3d origin, int count, int particles_per_iteration, float size) {
        super(world, EffectType.REPEATING, particle);
        this.particle = particle;
        this.world = world;
        this.particles = count;
        this.size = size;
        this.setOriginPos(origin);
        this.particlesPerIteration = particles_per_iteration;
    }

    /**
     * Creates a new animated "ball" effect.
     *
     * @param world The world the particles are going to spawn in
     * @param particle The particle that are going to be spawned. You can use {@link net.minecraft.particle.ParticleTypes}
     * @param origin The origin position of the effect
     * @param count The total number of particles that will be displayed
     * @param particles_per_iteration The number of particles displayed in each iteration
     * @param size The size of the ball effect
     * @param factors A {@link Vec3d} of xyz float factors for the ball effect. Non-uniform values will elongate the ball in one direction, for example (1,2,1) makes a vertical oval
     */
    public AnimatedBallEffect(ServerWorld world, ParticleEffect particle, Vec3d origin, int count, int particles_per_iteration, float size, Vec3d factors) {
        super(world, EffectType.REPEATING, particle);
        this.particle = particle;
        this.world = world;
        this.particles = count;
        this.size = size;
        this.setOriginPos(origin);
        this.particlesPerIteration = particles_per_iteration;
        this.xFactor = (float) factors.getX();
        this.yFactor = (float) factors.getY();
        this.zFactor = (float) factors.getZ();
    }


    @Override
    protected void onRun() {
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
            double x = (xFactor * r * MathUtils.cos(s) + this.originOffset.getX());
            double y = (yFactor * size * MathUtils.cos(t) + this.originOffset.getY());
            double z = (zFactor * r * MathUtils.sin(s) + this.originOffset.getZ());


            Vec3d vector = new Vec3d(x,y,z);
            vector = VectorUtils.rotateVector(vector, (float) xRotation, (float) yRotation, (float) zRotation);

            this.displayParticle(this.particle, originPos.add(vector));
        }
    }



}
