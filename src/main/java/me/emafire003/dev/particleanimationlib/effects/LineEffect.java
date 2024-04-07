package me.emafire003.dev.particleanimationlib.effects;

import me.emafire003.dev.particleanimationlib.EffectType;
import me.emafire003.dev.particleanimationlib.EffectV3;
import me.emafire003.dev.particleanimationlib.util.VectorUtils;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.Vec3d;

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


    public LineEffect(ServerWorld world, EffectType type, ParticleEffect particle, Vec3d origin, int particles, double length, double max_length, boolean isZigZag, int numberOfZigZags, Vec3d zigZagOffset, Vec3d zigZagRelativeOffset, EffectV3 effectAtEnd) {
        super(world, type, particle);
        this.particles = particles;
        this.length = length;
        this.maxLength = max_length;
        this.isZigZag = isZigZag;
        this.zigZags = numberOfZigZags;
        this.zigZagOffset = zigZagOffset;
        this.zigZagRelativeOffset = zigZagRelativeOffset;
        this.subEffectAtEnd = effectAtEnd;
    }

    public LineEffect(ServerWorld world, EffectType type, ParticleEffect particle, Vec3d origin, Vec3d target, int particles, boolean isZigZag, int numberOfZigZags, Vec3d zigZagOffset, Vec3d zigZagRelativeOffset, EffectV3 effectAtEnd) {
        super(world, type, particle);
        this.particles = particles;
        this.isZigZag = isZigZag;
        this.zigZags = numberOfZigZags;
        this.zigZagOffset = zigZagOffset;
        this.zigZagRelativeOffset = zigZagRelativeOffset;
        this.subEffectAtEnd = effectAtEnd;
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
        if (maxLength > 0) length = (float) Math.min(length, maxLength);

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
            subEffectAtEnd.run();
        }
    }

}
