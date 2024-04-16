package me.emafire003.dev.particleanimationlib.effects;

import me.emafire003.dev.particleanimationlib.Effect;
import me.emafire003.dev.particleanimationlib.EffectType;
import me.emafire003.dev.particleanimationlib.util.RandomUtils;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.Vec3d;

@SuppressWarnings("unused")
public class SphereEffect extends Effect {

    /**
     * Radius of the sphere
     */
    public double radius = 0.6;


    /**
     * Particles to display
     */
    public int particles = 50;

    /**
     * Amount to increase the radius per tick
     */
    public double radiusIncrease = 0;

    // Amount to increase the particles per tick
    public int particleIncrease = 0;

    /**
     * Creates a new sphere effect.
     *
     * @param world The world the particles are going to spawn in
     * @param particle The particle effect that is going to be spawned. You can use {@link net.minecraft.particle.ParticleTypes}
     * @param origin The origin position of the effect. Aka the center of the sphere
     * @param particles The number of particles the sphere will be made of
     * @param radius The radius of the sphere
     * @param radiusIncrease The amount to increase the radius per iteration/tick
     * @param particleIncrease The amount to increase the particles per iteration/tick
     * */
    public SphereEffect(ServerWorld world, ParticleEffect particle, Vec3d origin, int particles, double radius, double radiusIncrease, int particleIncrease) {
        super(world, EffectType.REPEATING, particle, origin);
        this.particles = particles;
        this.radius = radius;
        this.radiusIncrease = radiusIncrease;
        this.particleIncrease = particleIncrease;
    }


    /**
     * Creates a new sphere effect.
     *
     * @param world The world the particles are going to spawn in
     * @param particle The particle effect that is going to be spawned. You can use {@link net.minecraft.particle.ParticleTypes}
     * @param origin The origin position of the effect. Aka the center of the sphere
     * */
    public SphereEffect(ServerWorld world, ParticleEffect particle, Vec3d origin) {
        super(world, EffectType.REPEATING, particle, origin);
    }

    /**
     * Creates a new sphere effect.
     *
     * @param world The world the particles are going to spawn in
     * @param particle The particle effect that is going to be spawned. You can use {@link net.minecraft.particle.ParticleTypes}
     * @param origin The origin position of the effect. Aka the center of the sphere
     * @param particles The number of particles the sphere will be made of
     * @param radius The radius of the sphere
     * */
    public SphereEffect(ServerWorld world, ParticleEffect particle, Vec3d origin, int particles, double radius) {
        super(world, EffectType.REPEATING, particle, origin);
        this.particles = particles;
        this.radius = radius;
    }

    @Override
    public void onRun() {
        if (radiusIncrease != 0) radius += radiusIncrease;
        if (particleIncrease != 0) particles += particleIncrease;

        Vec3d origin = this.getOriginPos();

        if (origin == null) {
            return;
        }

        //Should be already adding the stuff
        //origin.add(0, yOffset, 0);
        Vec3d v;

        for (int i = 0; i < particles; i++) {
            v = RandomUtils.getRandomVector().multiply(radius);
            this.displayParticle(particle, origin.add(v));
        }
    }

    public double getRadius() {
        return radius;
    }

    public void setRadius(double radius) {
        this.radius = radius;
    }

    public double getRadiusIncrease() {
        return radiusIncrease;
    }

    public void setRadiusIncrease(double radiusIncrease) {
        this.radiusIncrease = radiusIncrease;
    }

    public int getParticleIncrease() {
        return particleIncrease;
    }

    public void setParticleIncrease(int particleIncrease) {
        this.particleIncrease = particleIncrease;
    }

}
