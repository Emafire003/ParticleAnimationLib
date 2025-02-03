package me.emafire003.dev.particleanimationlib.effects;

import me.emafire003.dev.particleanimationlib.Effect;
import me.emafire003.dev.particleanimationlib.EffectType;
import me.emafire003.dev.particleanimationlib.ParticleAnimationLib;
import me.emafire003.dev.particleanimationlib.util.RandomUtils;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.Vec3d;

@Deprecated
/**I may just add an option to make a half sphere to the sphere effect!*/
public class DomeShieldEffect extends Effect {


    /**
     * Radius of the shield
     */
    public double radius = 3;

    /**
     * Particles to display
     */
    public int particles = 50;

    /**
     * Set to false for a half-sphere and true for a complete sphere
     */
    public boolean as_sphere = false;

    /** Set to true to reverse the direction of the shield (works only if sphere is set to false)
     * */
    public boolean reverse = false;

    /*public ShieldEffect(EffectManager effectManager) {
        super(effectManager);
        type = EffectType.REPEATING;
        iterations = 500;
        period = 1;
    }*/


    /**
     * Creates a new DomeShield effect. It displays like a sphere or a half sphere around the chosen location
     *
     * @param world The world the particles are going to spawn in
     * @param particle The particle effect that is going to be spawned. You can use {@link ParticleTypes}
     * @param originPos The origin position of the effect
     * @param radius The radius of the effect
     * @param particles The number of particles used for this effect
     * @param as_sphere Set to True to display as a full sphere instead of a half sphere/dome
     * @param reverse Reverses the direction of the half sphere/dome*/
    public DomeShieldEffect(ServerWorld world, ParticleEffect particle, Vec3d originPos, double radius, int particles, boolean as_sphere, boolean reverse) {
        super(world, EffectType.REPEATING, particle, originPos);
        this.radius = radius;
        this.particles = particles;
        this.as_sphere = as_sphere;
        this.reverse = reverse;
    }

    @Override
    public void onRun() {
        Vec3d origin = this.getOriginPos();

        if(origin == null){
            ParticleAnimationLib.LOGGER.warn("The origin position of this effect is null!");
            return;
        }

        for (int i = 0; i < particles; i++) {
            Vec3d vector = RandomUtils.getRandomVector().multiply(radius);
            if (!as_sphere) {
                if (reverse){
                    vector = new Vec3d(vector.getX(), Math.abs(vector.getY()) * -1, vector.getZ());
                }
                else {
                    vector = new Vec3d(vector.getX(), Math.abs(vector.getY()), vector.getZ());
                }
            }
            this.displayParticle(particle, origin.add(vector));
        }
    }

}
