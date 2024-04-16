package me.emafire003.dev.particleanimationlib.effects;


import me.emafire003.dev.particleanimationlib.EffectType;
import me.emafire003.dev.particleanimationlib.effects.base.TargetedYPREffect;
import me.emafire003.dev.particleanimationlib.util.VectorUtils;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.Vec3d;
import org.jetbrains.annotations.NotNull;

@SuppressWarnings("unused")
public class VortexEffect extends TargetedYPREffect {

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
    public float lengthGrow = 0.05F;

    /**
     * Radials per iteration (PI / 16)
     */
    public double radials = Math.PI / 16;

    /**
     * Helix-circles per iteration (3)
     * (Also impacts on the length of the effect)
     */
    public int circles = 3;

    /**
     * Amount of helices (4)
     * Yay for the typo
     */
    public int helixes = 4;

    //Added by Emafire003

    /**Inverts the staring and ending position of the vortex*/
    public boolean inverted;

    // Stuff used for calculations

    /**
     * Current step. Works as counter
     */
    protected int step = 0;



    /** Creates a new Vortex effect
     *
     * @param world The world the particles are going to spawn in
     * @param particle The particle effect that is going to be spawned. You can use {@link net.minecraft.particle.ParticleTypes}
     * @param originPos The origin position of the effect
     * @param yaw The yaw of the effect. For example, you can get it from an Entity using getYaw()
     * @param pitch The pitch of the effect. For example, you can get it from an Entity using getPitch()
     * @param radius The base radius of the vortex
     * @param radiusGrow How much should the radius grow each iteration, aka each tick? TIP: Set it to a fairly low number
     * @param startRange The initial range of the vortex
     * @param lengthGrow The distance the vortex grows each tick
     * @param radials_per_iteration Radials per iteration
     * @param circles The number of circles per iteration
     * @param helixes The number of helixes or helices whatever per iteration
     * */
    public VortexEffect(@NotNull ServerWorld world, ParticleEffect particle, Vec3d originPos, float yaw, float pitch,
                        float radius, float radiusGrow, float startRange, float lengthGrow,
                        double radials_per_iteration, int circles, int helixes) {
        super(world, EffectType.REPEATING, particle, originPos);
        this.type = EffectType.REPEATING;
        this.world = world;
        this.particle = particle;

        this.yaw = yaw;
        this.pitch = pitch;

        this.radius = radius;
        this.radiusGrow = radiusGrow;
        this.startRange = startRange;
        this.lengthGrow = lengthGrow;
        this.radials = radials_per_iteration;
        this.circles = circles;
        this.helixes = helixes;
    }

    /** Creates a new Vortex effect
     *
     * @param world The world the particles are going to spawn in
     * @param particle The particle effect that is going to be spawned. You can use {@link net.minecraft.particle.ParticleTypes}
     * @param originPos The origin position of the effect
     * @param yaw The yaw of the effect. For example, you can get it from an Entity using getYaw()
     * @param pitch The pitch of the effect. For example, you can get it from an Entity using getPitch()
     * @param radius The base radius of the vortex
     * @param radiusGrow How much should the radius grow each iteration, aka each tick? TIP: Set it to a fairly low number
     * @param startRange The initial range of the vortex
     * @param lengthGrow The distance the vortex grows each tick
     * @param circles The number of circles per iteration
     * @param helixes The number of helixes or helices whatever per iteration
     * */
    public VortexEffect(@NotNull ServerWorld world, ParticleEffect particle, Vec3d originPos, float yaw, float pitch,
                        float radius, float radiusGrow, float startRange, float lengthGrow, int circles, int helixes) {
        super(world, EffectType.REPEATING, particle, originPos);
        this.type = EffectType.REPEATING;
        this.world = world;
        this.particle = particle;

        this.yaw = yaw;
        this.pitch = pitch;

        this.radius = radius;
        this.radiusGrow = radiusGrow;
        this.startRange = startRange;
        this.lengthGrow = lengthGrow;
        this.circles = circles;
        this.helixes = helixes;
    }

    /** Creates a new Vortex effect
     *
     * @param world The world the particles are going to spawn in
     * @param particle The particle effect that is going to be spawned. You can use {@link net.minecraft.particle.ParticleTypes}
     * @param originPos The origin position of the effect
     * @param yaw The yaw of the effect. For example, you can get it from an Entity using getYaw()
     * @param pitch The pitch of the effect. For example, you can get it from an Entity using getPitch()
     * @param radius The base radius of the vortex
     * @param lengthGrow The distance the vortex grows each tick
     * @param radials_per_iteration Radials per iteration
     * @param circles The number of circles per iteration
     * @param helixes The number of helixes or helices whatever per iteration
     * */
    public VortexEffect(@NotNull ServerWorld world, ParticleEffect particle, Vec3d originPos, float yaw, float pitch,
                        float radius, float lengthGrow,
                        double radials_per_iteration, int circles, int helixes) {
        super(world, EffectType.REPEATING, particle, originPos);
        this.type = EffectType.REPEATING;
        this.world = world;
        this.particle = particle;

        this.yaw = yaw;
        this.pitch = pitch;

        this.radius = radius;
        this.lengthGrow = lengthGrow;
        this.radials = radials_per_iteration;
        this.circles = circles;
        this.helixes = helixes;
    }

    /** Creates a new Vortex effect
     *
     * @param world The world the particles are going to spawn in
     * @param particle The particle effect that is going to be spawned. You can use {@link net.minecraft.particle.ParticleTypes}
     * @param originPos The origin position of the effect
     * @param yaw The yaw of the effect. For example, you can get it from an Entity using getYaw()
     * @param pitch The pitch of the effect. For example, you can get it from an Entity using getPitch()
     * @param radius The base radius of the vortex
     * @param lengthGrow The distance the vortex grows each tick
     * @param circles The number of circles per iteration
     * @param helixes The number of helixes or helices whatever per iteration
     * */
    public VortexEffect(@NotNull ServerWorld world, ParticleEffect particle, Vec3d originPos, float yaw, float pitch,
                        float radius, float lengthGrow, int circles, int helixes) {
        super(world, EffectType.REPEATING, particle, originPos);
        this.type = EffectType.REPEATING;
        this.world = world;
        this.particle = particle;

        this.yaw = yaw;
        this.pitch = pitch;

        this.radius = radius;
        this.lengthGrow = lengthGrow;
        this.circles = circles;
        this.helixes = helixes;
    }

    /** Creates a new Vortex effect
     *
     * @param world The world the particles are going to spawn in
     * @param particle The particle effect that is going to be spawned. You can use {@link net.minecraft.particle.ParticleTypes}
     * @param originPos The origin position of the effect
     * @param yaw The yaw of the effect. For example, you can get it from an Entity using getYaw()
     * @param pitch The pitch of the effect. For example, you can get it from an Entity using getPitch()
     * */
    public VortexEffect(@NotNull ServerWorld world, ParticleEffect particle, Vec3d originPos, float yaw, float pitch) {
        super(world, EffectType.REPEATING, particle, originPos);
        this.type = EffectType.REPEATING;
        this.world = world;
        this.particle = particle;
        this.yaw = yaw;
        this.pitch = pitch;
    }

    /**  Returns the predicted finish center position of the vortex, can be used to invert the vortex
     * */
    public Vec3d getPredictedMaxCenterPosition(){
        float total_length = this.getIterations() * lengthGrow * circles;

        Vec3d v = new Vec3d(0, total_length, 0);

        v = VectorUtils.rotateVector(v, this.getYaw(), this.getPitch()+90);

        return originPos.add(v);
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
                v = new Vec3d(Math.cos(angle) * (radius + step * radiusGrow), startRange + step * lengthGrow, Math.sin(angle) * (radius + step * radiusGrow));
                //The +90 flips the angle to be on the looking plane let's call it
                v = VectorUtils.rotateVector(v, this.getYaw(), this.getPitch()+90);

                if(inverted){
                    v = v.multiply(-1);
                    this.displayParticle(particle, getPredictedMaxCenterPosition().add(v));
                }else{
                    this.displayParticle(particle, origin.add(v));
                }

            }
            step++;
        }
        this.displayParticle(ParticleTypes.CLOUD, this.getPredictedMaxCenterPosition());
    }

    public float getRadius() {
        return radius;
    }

    public void setRadius(float radius) {
        this.radius = radius;
    }

    public float getRadiusGrow() {
        return radiusGrow;
    }

    public void setRadiusGrow(float radiusGrow) {
        this.radiusGrow = radiusGrow;
    }

    public float getStartRange() {
        return startRange;
    }

    public void setStartRange(float startRange) {
        this.startRange = startRange;
    }

    public float getLengthGrow() {
        return lengthGrow;
    }

    public void setLengthGrow(float lengthGrow) {
        this.lengthGrow = lengthGrow;
    }

    public double getRadials() {
        return radials;
    }

    public void setRadials(double radials) {
        this.radials = radials;
    }

    public int getCircles() {
        return circles;
    }

    public void setCircles(int circles) {
        this.circles = circles;
    }

    public int getHelixes() {
        return helixes;
    }

    public void setHelixes(int helixes) {
        this.helixes = helixes;
    }

    public boolean isInverted() {
        return inverted;
    }

    public void setInverted(boolean inverted) {
        this.inverted = inverted;
    }

}
