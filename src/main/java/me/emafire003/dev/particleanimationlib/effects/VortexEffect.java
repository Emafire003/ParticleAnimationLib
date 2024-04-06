package me.emafire003.dev.particleanimationlib.effects;


import me.emafire003.dev.particleanimationlib.EffectType;
import me.emafire003.dev.particleanimationlib.util.VectorUtils;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.Vec3d;
import org.jetbrains.annotations.NotNull;

@SuppressWarnings("unused")
public class VortexEffect extends YPREffectV3 {

    /**
     * Radius of vortex (2)
     */
    public float radius = 2;

    /**
     * Radius grow per iteration (0.00)
     */
    public float radiusGrow = 0.00F;

    /**
     * Initial range of the vortex (0.0)
     */
    public float startRange = 0.0F;

    /**
     * Growing per iteration (0.05)
     */
    public float grow = 0.05F;

    /**
     * Radials per iteration (PI / 16)
     */
    public double radials = Math.PI / 16;

    /**
     * Helix-circles per iteration (3)
     */
    public int circles = 3;

    /**
     * Amount of helices (4)
     * Yay for the typo
     */
    public int helixes = 4;

    // Stuff used for calculations

    /**
     * Current step. Works as counter
     */
    protected int step = 0;



    /** Creates a new Vortex effect
     * Remember to set the number of iterations! {@link #setIterations(int)}
     *
     * @param world The world the particles are going to spawn in
     * @param particle The particle that are going to be spawned. You can use {@link net.minecraft.particle.ParticleTypes}
     * @param originPos The origin position of the effect
     * @param yaw The yaw of the effect. For example, you can get it from an Entity using getYaw()
     * @param pitch The pitch of the effect. For example, you can get it from an Entity using getPitch()
     * @param radius The base radius of the vortex
     * @param radiusGrow How much should the radius grow each iteration, aka each tick? TIP: Set it to a fairly low number
     * @param startRange The initial range of the vortex
     * @param grow The distance the vortex grows each tick
     * @param radials_per_iteration Radials per iteration
     * @param circles The number of circles per iteration
     * @param helixes The number of helixes or helices whatever per iteration
     * */
    public VortexEffect(@NotNull ServerWorld world, ParticleEffect particle, Vec3d originPos, float yaw, float pitch,
                        float radius, float radiusGrow, float startRange, float grow,
                        double radials_per_iteration, int circles, int helixes) {
        super(world, EffectType.REPEATING, particle);
        this.type = EffectType.REPEATING;
        this.world = world;
        this.particle = particle;
        this.originPos = originPos;

        this.yaw = yaw;
        this.pitch = pitch;

        this.radius = radius;
        this.radiusGrow = radiusGrow;
        this.startRange = startRange;
        this.grow = grow;
        this.radials = radials_per_iteration;
        this.circles = circles;
        this.helixes = helixes;
    }

    /** Creates a new Vortex effect
     * Remember to set the number of iterations! {@link #setIterations(int)}
     *
     * @param world The world the particles are going to spawn in
     * @param particle The particle that are going to be spawned. You can use {@link net.minecraft.particle.ParticleTypes}
     * @param originPos The origin position of the effect
     * @param yaw The yaw of the effect. For example, you can get it from an Entity using getYaw()
     * @param pitch The pitch of the effect. For example, you can get it from an Entity using getPitch()
     * @param radius The base radius of the vortex
     * @param radiusGrow How much should the radius grow each iteration, aka each tick? TIP: Set it to a fairly low number
     * @param startRange The initial range of the vortex
     * @param grow The distance the vortex grows each tick
     * @param circles The number of circles per iteration
     * @param helixes The number of helixes or helices whatever per iteration
     * */
    public VortexEffect(@NotNull ServerWorld world, ParticleEffect particle, Vec3d originPos, float yaw, float pitch,
                        float radius, float radiusGrow, float startRange, float grow, int circles, int helixes) {
        super(world, EffectType.REPEATING, particle);
        this.type = EffectType.REPEATING;
        this.world = world;
        this.particle = particle;
        this.originPos = originPos;

        this.yaw = yaw;
        this.pitch = pitch;

        this.radius = radius;
        this.radiusGrow = radiusGrow;
        this.startRange = startRange;
        this.grow = grow;
        this.circles = circles;
        this.helixes = helixes;
    }

    /** Creates a new Vortex effect
     * Remember to set the number of iterations! {@link #setIterations(int)}
     *
     * @param world The world the particles are going to spawn in
     * @param particle The particle that are going to be spawned. You can use {@link net.minecraft.particle.ParticleTypes}
     * @param originPos The origin position of the effect
     * @param yaw The yaw of the effect. For example, you can get it from an Entity using getYaw()
     * @param pitch The pitch of the effect. For example, you can get it from an Entity using getPitch()
     * @param radius The base radius of the vortex
     * @param grow The distance the vortex grows each tick
     * @param radials_per_iteration Radials per iteration
     * @param circles The number of circles per iteration
     * @param helixes The number of helixes or helices whatever per iteration
     * */
    public VortexEffect(@NotNull ServerWorld world, ParticleEffect particle, Vec3d originPos, float yaw, float pitch,
                        float radius, float grow,
                        double radials_per_iteration, int circles, int helixes) {
        super(world, EffectType.REPEATING, particle);
        this.type = EffectType.REPEATING;
        this.world = world;
        this.particle = particle;
        this.originPos = originPos;

        this.yaw = yaw;
        this.pitch = pitch;

        this.radius = radius;
        this.grow = grow;
        this.radials = radials_per_iteration;
        this.circles = circles;
        this.helixes = helixes;
    }

    /** Creates a new Vortex effect
     * Remember to set the number of iterations! {@link #setIterations(int)}
     *
     * @param world The world the particles are going to spawn in
     * @param particle The particle that are going to be spawned. You can use {@link net.minecraft.particle.ParticleTypes}
     * @param originPos The origin position of the effect
     * @param yaw The yaw of the effect. For example, you can get it from an Entity using getYaw()
     * @param pitch The pitch of the effect. For example, you can get it from an Entity using getPitch()
     * @param radius The base radius of the vortex
     * @param grow The distance the vortex grows each tick
     * @param circles The number of circles per iteration
     * @param helixes The number of helixes or helices whatever per iteration
     * */
    public VortexEffect(@NotNull ServerWorld world, ParticleEffect particle, Vec3d originPos, float yaw, float pitch,
                        float radius, float grow, int circles, int helixes) {
        super(world, EffectType.REPEATING, particle);
        this.type = EffectType.REPEATING;
        this.world = world;
        this.particle = particle;
        this.originPos = originPos;

        this.yaw = yaw;
        this.pitch = pitch;

        this.radius = radius;
        this.grow = grow;
        this.circles = circles;
        this.helixes = helixes;
    }

    /** Creates a new Vortex effect
     * Remember to set the number of iterations! {@link #setIterations(int)}
     *
     * @param world The world the particles are going to spawn in
     * @param particle The particle that are going to be spawned. You can use {@link net.minecraft.particle.ParticleTypes}
     * @param originPos The origin position of the effect
     * @param yaw The yaw of the effect. For example, you can get it from an Entity using getYaw()
     * @param pitch The pitch of the effect. For example, you can get it from an Entity using getPitch()
     * */
    public VortexEffect(@NotNull ServerWorld world, ParticleEffect particle, Vec3d originPos, float yaw, float pitch) {
        super(world, EffectType.REPEATING, particle);
        this.type = EffectType.REPEATING;
        this.world = world;
        this.particle = particle;
        this.originPos = originPos;
        this.yaw = yaw;
        this.pitch = pitch;
    }

    @Override
    protected void onRun() {
        Vec3d origin = this.getOriginPos();
        double angle;
        Vec3d v;

        if (origin == null) {
            return;
        }

        for (int x = 0; x < circles; x++) {
            for (int i = 0; i < helixes; i++) {
                angle = step * radials + (2 * Math.PI * i / helixes);
                v = new Vec3d(Math.cos(angle) * (radius + step * radiusGrow), startRange + step * grow, Math.sin(angle) * (radius + step * radiusGrow));
                //The +90 flips the angle to be on the looking plane let's call it
                v = VectorUtils.rotateVector(v, this.getYaw(), this.getPitch()+90);

                this.displayParticle(particle, origin.add(v));
            }
            step++;
        }
    }

}
