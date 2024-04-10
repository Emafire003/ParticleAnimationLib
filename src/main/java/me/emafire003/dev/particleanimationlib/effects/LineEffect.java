package me.emafire003.dev.particleanimationlib.effects;

import me.emafire003.dev.particleanimationlib.EffectType;
import me.emafire003.dev.particleanimationlib.EffectV3;
import me.emafire003.dev.particleanimationlib.util.VectorUtils;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.Vec3d;

@SuppressWarnings("unused")
public class LineEffect extends YPREffectV3 {

    /**
     * Should it do a zig zag?
     */
    public boolean isZigZag = false;

    /**
     * Number of zig zags in the line
     */
    public int zigZags = 10;

    /**
     * Direction of zig-zags
     */
    public Vec3d zigZagOffset = new Vec3d(0, 0.1, 0);

    /**
     * Relative direction of zig-zags
     */
    public Vec3d zigZagRelativeOffset = new Vec3d(0, 0, 0);

    /**
     * Particles per arc
     */
    public int particles = 100;

    /**
     * Length of arc
     * A non-zero value here will use a length instead of the target endpoint
     */
    public double length = 0;

    /**
     * Max length of arc
     * A non-zero value here will use this as the upper bound for the computed length
     */
    public double maxLength = 0;


    /**
     * Sub effect at end.
     * This will play a subeffect at the end origin of the line
     */
    private EffectV3 subEffectAtEnd = null;

    //Internal stuff

    /**
     * Internal boolean
     */
    protected boolean zag = false;
    
    /**
     * Internal counter
     */
    protected int step = 0;


    /**
     * Creates a new line effect.
     *
     * @param world The world the particles are going to spawn in
     * @param particle The particle effect that is going to be spawned. You can use {@link net.minecraft.particle.ParticleTypes}
     * @param origin The origin position of the line
     * @param yaw The yaw of the effect. For example, you can get it from an Entity using getYaw()
     * @param pitch The pitch of the effect. For example, you can get it from an Entity using getPitch()
     * @param particles The number of particles that make up the line
     * @param length The length of the line
     * @param isZigZag Should the line ZigZag
     * @param numberOfZigZags The number of zig zags that the line will do
     * @param zigZagOffset An offset for the zigZag
     * @param zigZagRelativeOffset A relative offsets for the zigzags
     * @param effectAtEnd Another Effect that will be spawned at the ending position of the line (its originPos will be set to the end of the line)
     * */
    public LineEffect(ServerWorld world, ParticleEffect particle, Vec3d origin, float yaw, float pitch, int particles, double length, boolean isZigZag, int numberOfZigZags, Vec3d zigZagOffset, Vec3d zigZagRelativeOffset, EffectV3 effectAtEnd) {
        super(world, EffectType.REPEATING, particle);
        this.originPos = origin;
        this.particles = particles;
        this.length = length;
        this.yaw = yaw;
        this.pitch = pitch;
        this.isZigZag = isZigZag;
        this.zigZags = numberOfZigZags;
        this.zigZagOffset = zigZagOffset;
        this.zigZagRelativeOffset = zigZagRelativeOffset;
        this.subEffectAtEnd = effectAtEnd;
    }

    /**
     * Creates a new line effect.
     *
     * @param world The world the particles are going to spawn in
     * @param particle The particle effect that is going to be spawned. You can use {@link net.minecraft.particle.ParticleTypes}
     * @param origin The origin position of the line
     * @param yaw The yaw of the effect. For example, you can get it from an Entity using getYaw()
     * @param pitch The pitch of the effect. For example, you can get it from an Entity using getPitch()
     * @param particles The number of particles that make up the line
     * @param length The length of the line
     * @param isZigZag Should the line ZigZag
     * @param numberOfZigZags The number of zig zags that the line will do
     * @param zigZagOffset An offset for the zigZag
     * @param zigZagRelativeOffset A relative offsets for the zigzags
     * */
    public LineEffect(ServerWorld world, ParticleEffect particle, Vec3d origin, float yaw, float pitch, int particles, double length, boolean isZigZag, int numberOfZigZags, Vec3d zigZagOffset, Vec3d zigZagRelativeOffset) {
        super(world, EffectType.REPEATING, particle);
        this.originPos = origin;
        this.particles = particles;
        this.length = length;
        this.yaw = yaw;
        this.pitch = pitch;
        this.isZigZag = isZigZag;
        this.zigZags = numberOfZigZags;
        this.zigZagOffset = zigZagOffset;
        this.zigZagRelativeOffset = zigZagRelativeOffset;
    }

    /**
     * Creates a new line effect.
     *
     * @param world The world the particles are going to spawn in
     * @param particle The particle effect that is going to be spawned. You can use {@link net.minecraft.particle.ParticleTypes}
     * @param origin The origin position of the line
     * @param yaw The yaw of the effect. For example, you can get it from an Entity using getYaw()
     * @param pitch The pitch of the effect. For example, you can get it from an Entity using getPitch()
     * @param particles The number of particles that make up the line
     * @param length The length of the line
     * */
    public LineEffect(ServerWorld world, ParticleEffect particle, Vec3d origin, float yaw, float pitch, int particles, double length) {
        super(world, EffectType.REPEATING, particle);
        this.originPos = origin;
        this.particles = particles;
        this.length = length;
        this.yaw = yaw;
        this.pitch = pitch;
    }

    /**
     * Creates a new line effect.
     *
     * @param world The world the particles are going to spawn in
     * @param particle The particle effect that is going to be spawned. You can use {@link net.minecraft.particle.ParticleTypes}
     * @param origin The origin position of the line
     * @param yaw The yaw of the effect. For example, you can get it from an Entity using getYaw()
     * @param pitch The pitch of the effect. For example, you can get it from an Entity using getPitch()
     * @param particles The number of particles that make up the line
     * @param length The length of the line
     * @param effectAtEnd Another Effect that will be spawned at the ending position of the line (its originPos will be set to the end of the line)
     * */
    public LineEffect(ServerWorld world, ParticleEffect particle, Vec3d origin, float yaw, float pitch, int particles, double length, EffectV3 effectAtEnd) {
        super(world, EffectType.REPEATING, particle);
        this.originPos = origin;
        this.particles = particles;
        this.length = length;
        this.yaw = yaw;
        this.pitch = pitch;
        this.subEffectAtEnd = effectAtEnd;
    }

    /**
     * Creates a new line effect.
     *
     * @param world The world the particles are going to spawn in
     * @param particle The particle effect that is going to be spawned. You can use {@link net.minecraft.particle.ParticleTypes}
     * @param origin The origin position of the line
     * @param yaw The yaw of the effect. For example, you can get it from an Entity using getYaw()
     * @param pitch The pitch of the effect. For example, you can get it from an Entity using getPitch()
      */
    public LineEffect(ServerWorld world, ParticleEffect particle, Vec3d origin, float yaw, float pitch) {
        super(world, EffectType.REPEATING, particle);
        this.originPos = origin;
        this.yaw = yaw;
        this.pitch = pitch;
    }

    /**
     * Creates a new line effect.
     *
     * @param world The world the particles are going to spawn in
     * @param particle The particle effect that is going to be spawned. You can use {@link net.minecraft.particle.ParticleTypes}
     * @param origin The origin position of the line
     * @param target The ending position of the line
     * @param particles The number of particles that make up the line
     * @param maxLength The Max length of the line arcs. Setting to 0 will remove the limit
     * @param isZigZag Should the line ZigZag
     * @param numberOfZigZags The number of zig zags that the line will do
     * @param zigZagOffset An offset for the zigZag
     * @param zigZagRelativeOffset A relative offsets for the zigzags
     * @param effectAtEnd Another Effect that will be spawned at the ending position of the line (its originPos will be set to the end of the line)
     * */
    public LineEffect(ServerWorld world, ParticleEffect particle, Vec3d origin, Vec3d target, int particles, int maxLength, boolean isZigZag, int numberOfZigZags, Vec3d zigZagOffset, Vec3d zigZagRelativeOffset, EffectV3 effectAtEnd) {
        super(world, EffectType.REPEATING, particle);
        this.originPos = origin;
        this.targetPos = target;
        this.particles = particles;
        this.isZigZag = isZigZag;
        this.maxLength = maxLength;
        this.zigZags = numberOfZigZags;
        this.zigZagOffset = zigZagOffset;
        this.zigZagRelativeOffset = zigZagRelativeOffset;
        this.subEffectAtEnd = effectAtEnd;
    }

    /**
     * Creates a new line effect.
     *
     * @param world The world the particles are going to spawn in
     * @param particle The particle effect that is going to be spawned. You can use {@link net.minecraft.particle.ParticleTypes}
     * @param origin The origin position of the line
     * @param target The ending position of the line
     * @param particles The number of particles that make up the line
     * @param maxLength The Max length of the line arcs. Setting to 0 will remove the limit
     * @param isZigZag Should the line ZigZag
     * @param numberOfZigZags The number of zig zags that the line will do
     * @param zigZagOffset An offset for the zigZag
     * @param zigZagRelativeOffset A relative offsets for the zigzags
     */
    public LineEffect(ServerWorld world, ParticleEffect particle, Vec3d origin, Vec3d target, int particles, int maxLength, boolean isZigZag, int numberOfZigZags, Vec3d zigZagOffset, Vec3d zigZagRelativeOffset) {
        super(world, EffectType.REPEATING, particle);
        this.originPos = origin;
        this.targetPos = target;
        this.particles = particles;
        this.isZigZag = isZigZag;
        this.maxLength = maxLength;
        this.zigZags = numberOfZigZags;
        this.zigZagOffset = zigZagOffset;
        this.zigZagRelativeOffset = zigZagRelativeOffset;
    }

    /**
     * Creates a new line effect.
     *
     * @param world The world the particles are going to spawn in
     * @param particle The particle effect that is going to be spawned. You can use {@link net.minecraft.particle.ParticleTypes}
     * @param origin The origin position of the line
     * @param target The ending position of the line
     * @param particles The number of particles that make up the line
     * @param maxLength The Max length of the line arcs. Setting to 0 will remove the limit
     * @param effectAtEnd Another Effect that will be spawned at the ending position of the line (its originPos will be set to the end of the line)
     * */
    public LineEffect(ServerWorld world, ParticleEffect particle, Vec3d origin, Vec3d target, int particles, int maxLength,EffectV3 effectAtEnd) {
        super(world, EffectType.REPEATING, particle);
        this.originPos = origin;
        this.targetPos = target;
        this.particles = particles;
        this.maxLength = maxLength;
        this.subEffectAtEnd = effectAtEnd;
    }

    /**
     * Creates a new line effect.
     *
     * @param world The world the particles are going to spawn in
     * @param particle The particle effect that is going to be spawned. You can use {@link net.minecraft.particle.ParticleTypes}
     * @param origin The origin position of the line
     * @param target The ending position of the line
     * @param particles The number of particles that make up the line
     * @param maxLength The Max length of the line arcs. Setting to 0 will remove the limit
     * */
    public LineEffect(ServerWorld world, ParticleEffect particle, Vec3d origin, Vec3d target, int particles, int maxLength) {
        super(world, EffectType.REPEATING, particle);
        this.originPos = origin;
        this.targetPos = target;
        this.particles = particles;
        this.maxLength = maxLength;
    }

    /**
     * Creates a new line effect.
     *
     * @param world The world the particles are going to spawn in
     * @param particle The particle effect that is going to be spawned. You can use {@link net.minecraft.particle.ParticleTypes}
     * @param origin The origin position of the line
     * @param target The ending position of the line
     * @param particles The number of particles that make up the line
     * */
    public LineEffect(ServerWorld world, ParticleEffect particle, Vec3d origin, Vec3d target, int particles) {
        super(world, EffectType.REPEATING, particle);
        this.originPos = origin;
        this.targetPos = target;
        this.particles = particles;
    }


    @Override
    public void onRun() {
        Vec3d origin = this.getOriginPos();
        Vec3d target;

        if (origin == null) {
            return;
        }

        if (length > 0){
            target = origin.add(this.getDirection().normalize().multiply(length));
        }
        else {
            target = this.getTargetPos();
        }

        int amount = particles / zigZags;
        if (target == null) {
            return;
        }

        Vec3d link = target.subtract(origin);
        float length = (float) link.length();
        if (maxLength > 0){
            length = (float) Math.min(length, maxLength);
        }

        link = link.normalize();

        float ratio = length / particles;
        Vec3d v = link.multiply(ratio);
        Vec3d loc = origin.subtract(v);
        Vec3d rel;

        for (int i = 0; i < particles; i++) {
            if (isZigZag) {
                rel = VectorUtils.rotateVector(zigZagRelativeOffset, this.getYaw(), this.getPitch());
                if (zag) {
                    loc = loc.add(rel);
                    loc = loc.add(zigZagOffset);
                } else {
                    loc = loc.subtract(rel);
                    loc = loc.subtract(zigZagOffset);
                }
            }
            if (step >= amount) {
                zag = !zag;
                step = 0;
            }
            step++;
            loc = loc.add(v);
            this.displayParticle(particle, loc);
        }

        if (subEffectAtEnd != null){
            subEffectAtEnd.setOriginPos(loc);
            subEffectAtEnd.run();
        }
    }

    public boolean isZigZag() {
        return isZigZag;
    }

    public void setZigZag(boolean zigZag) {
        isZigZag = zigZag;
    }

    public int getZigZags() {
        return zigZags;
    }

    public void setZigZags(int zigZags) {
        this.zigZags = zigZags;
    }

    public Vec3d getZigZagOffset() {
        return zigZagOffset;
    }

    public void setZigZagOffset(Vec3d zigZagOffset) {
        this.zigZagOffset = zigZagOffset;
    }

    public Vec3d getZigZagRelativeOffset() {
        return zigZagRelativeOffset;
    }

    public void setZigZagRelativeOffset(Vec3d zigZagRelativeOffset) {
        this.zigZagRelativeOffset = zigZagRelativeOffset;
    }

    public int getParticles() {
        return particles;
    }

    public void setParticles(int particles) {
        this.particles = particles;
    }

    public double getLength() {
        return length;
    }

    public void setLength(double length) {
        this.length = length;
    }

    public double getMaxLength() {
        return maxLength;
    }

    public void setMaxLength(double maxLength) {
        this.maxLength = maxLength;
    }

    public EffectV3 getSubEffectAtEnd() {
        return subEffectAtEnd;
    }

    public void setSubEffectAtEnd(EffectV3 subEffectAtEnd) {
        this.subEffectAtEnd = subEffectAtEnd;
    }

}
