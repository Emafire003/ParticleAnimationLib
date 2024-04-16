package me.emafire003.dev.particleanimationlib.effects;

import me.emafire003.dev.particleanimationlib.Effect;
import me.emafire003.dev.particleanimationlib.EffectType;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.Vec3d;

@SuppressWarnings("unused")
public class ArcEffect extends Effect {


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

    /**
     * Creates a new arc effect between two points
     *
     * @param world The world the particles are going to spawn in
     * @param particle The particle effect that is going to be spawned. You can use {@link net.minecraft.particle.ParticleTypes}
     * @param origin The origin position of the effect, aka the initial point of the arc
     * @param target The target position of the effect, aka the finial point of the arc
     * @param count The number of particles to spread between the two points
     * @param height The height (in blocks) of the arc, aka its curvature.
     */
    public ArcEffect(ServerWorld world, ParticleEffect particle, Vec3d origin, Vec3d target, int count, float height) {
        super(world, EffectType.REPEATING, particle, origin);
        this.setTargetPos(target);
        this.particles = count;
        this.height = height;
    }

    /**
     * Creates a new arc effect between two points
     *
     * @param world The world the particles are going to spawn in
     * @param particle The particle effect that is going to be spawned. You can use {@link net.minecraft.particle.ParticleTypes}
     * @param origin The origin position of the effect, aka the initial point of the arc
     * @param target The target position of the effect, aka the finial point of the arc
     */
    public ArcEffect(ServerWorld world, ParticleEffect particle, Vec3d origin, Vec3d target) {
        super(world, EffectType.REPEATING, particle, origin);
        this.setTargetPos(target);
    }

    /**
     * Creates a new arc effect between two points
     *
     * @param world The world the particles are going to spawn in
     * @param particle The particle effect that is going to be spawned. You can use {@link net.minecraft.particle.ParticleTypes}
     * @param origin The origin position of the effect, aka the initial point of the arc
     * @param target The target position of the effect, aka the finial point of the arc
     * @param count The number of particles to spread between the two points
     */
    public ArcEffect(ServerWorld world, ParticleEffect particle, Vec3d origin, Vec3d target, int count) {
        super(world, EffectType.REPEATING, particle, origin);
        this.setTargetPos(target);
        this.particles = count;
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

        //TODO make an arc on the other coordinates too
        for (int i = 0; i < particles; i++) {

            //v = link.clone().normalize().multiply(length * i / particles);


            step++;
            v = new Vec3d(link.getX(), link.getY(), link.getZ()).normalize().multiply(length * i / particles);
            //ParticleAnimationLib.LOGGER.info("The v is: " + v);
            x = ((float) i / particles) * length - length / 2;
            y = (float) (-pitch * Math.pow(x, 2) + height);

            this.displayParticle(particle, origin.add(v).add(0, y, 0));

            step++;
        }
    }

    public float getHeight() {
        return height;
    }

    public void setHeight(float height) {
        this.height = height;
    }

    public int getParticles() {
        return particles;
    }

    public void setParticles(int particles) {
        this.particles = particles;
    }

}
