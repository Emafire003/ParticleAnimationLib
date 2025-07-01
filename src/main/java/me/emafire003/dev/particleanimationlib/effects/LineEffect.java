package me.emafire003.dev.particleanimationlib.effects;

import me.emafire003.dev.particleanimationlib.Effect;
import me.emafire003.dev.particleanimationlib.EffectType;
import me.emafire003.dev.particleanimationlib.effects.base.TargetedYPREffect;
import me.emafire003.dev.particleanimationlib.util.EffectModifier;
import me.emafire003.dev.particleanimationlib.util.VectorUtils;
import net.minecraft.entity.Entity;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.Vec3d;

@SuppressWarnings("unused")
public class LineEffect extends TargetedYPREffect {

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
    private Effect subEffectAtEnd = null;

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
     * @param particle The particle effect that is going to be spawned. You can use {@link ParticleTypes}
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
    public LineEffect(ServerWorld world, ParticleEffect particle, Vec3d origin, float yaw, float pitch, int particles, double length, boolean isZigZag, int numberOfZigZags, Vec3d zigZagOffset, Vec3d zigZagRelativeOffset, Effect effectAtEnd) {
        super(world, EffectType.REPEATING, particle, origin);
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
     * @param particle The particle effect that is going to be spawned. You can use {@link ParticleTypes}
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
        super(world, EffectType.REPEATING, particle, origin);
        this.particles = particles;
        this.length = length;
        this.yaw = yaw;
        this.pitch = pitch;
        this.isZigZag = isZigZag;
        this.zigZags = numberOfZigZags;
        this.zigZagOffset = zigZagOffset;
        this.zigZagRelativeOffset = zigZagRelativeOffset;
    }

    private LineEffect(Builder builder) {
        super(builder.world, EffectType.REPEATING, builder.particle, builder.originPos);
        setIterations(builder.iterations);
        setOriginPos(builder.originPos);
        setUpdatePositions(builder.updatePositions);
        setEntityOrigin(builder.entityOrigin);
        setOriginOffset(builder.originOffset);
        world = builder.world;
        particle = builder.particle;
        setZigZag(builder.isZigZag);
        setZigZags(builder.zigZags);
        setZigZagOffset(builder.zigZagOffset);
        setZigZagRelativeOffset(builder.zigZagRelativeOffset);
        setParticles(builder.particles);
        setLength(builder.length);
        setMaxLength(builder.maxLength);
        setSubEffectAtEnd(builder.subEffectAtEnd);
        setTargetPos(builder.targetPos);
        setUpdateTargetPositions(builder.updateTargetPositions);
        setEntityTarget(builder.entityTarget);
        setTargetOffset(builder.targetOffset);
        setYawOffset(builder.yawOffset);
        setPitchOffset(builder.pitchOffset);
        setYaw(builder.yaw);
        setPitch(builder.pitch);
        setShouldUpdateYPR(builder.shouldUpdateYPR);
        setUseEyePosAsOrigin(builder.useEyePosAsOrigin);
        setUseEyePosAsTarget(builder.useEyePosAsTarget);
        setExecuteOnStop(builder.executeOnStop);
        setParticleLimit(builder.particleLimit);
        setShouldLimitParticlesEveryNIterations(builder.shouldLimitParticlesEveryNIterations);
        setLimitParticlesEveryNIterations(builder.limitParticlesEveryNIterations);
        setShouldLimitParticlesSpawnedPerIteration(builder.shouldLimitParticlesSpawnedPerIteration);
        setSpawnParticlesEveryNIteration(builder.spawnParticlesEveryNIteration);
        setShouldSpawnParticlesEveryNIteration(builder.shouldSpawnParticlesEveryNIteration);
        setForced(builder.forced);
    }

    public static void copy(LineEffect original, LineEffect copy) {
        TargetedYPREffect.copy(original, copy);
        copy.setZigZag(original.isZigZag());
        copy.setZigZags(original.getZigZags());
        copy.setZigZagOffset(original.getZigZagOffset());
        copy.setZigZagRelativeOffset(original.getZigZagRelativeOffset());
        copy.setParticles(original.getParticles());
        copy.setLength(original.getLength());
        copy.setMaxLength(original.getMaxLength());
        copy.setSubEffectAtEnd(original.getSubEffectAtEnd());
        copy.zag = original.zag;
        copy.step = original.step;
    }

    /**
     * Creates a new line effect.
     *
     * @param world The world the particles are going to spawn in
     * @param particle The particle effect that is going to be spawned. You can use {@link ParticleTypes}
     * @param origin The origin position of the line
     * @param yaw The yaw of the effect. For example, you can get it from an Entity using getYaw()
     * @param pitch The pitch of the effect. For example, you can get it from an Entity using getPitch()
     * @param particles The number of particles that make up the line
     * @param length The length of the line
     * */
    public LineEffect(ServerWorld world, ParticleEffect particle, Vec3d origin, float yaw, float pitch, int particles, double length) {
        super(world, EffectType.REPEATING, particle, origin);
        this.particles = particles;
        this.length = length;
        this.yaw = yaw;
        this.pitch = pitch;
    }


    /**
     * Creates a new line effect.
     *
     * @param world The world the particles are going to spawn in
     * @param particle The particle effect that is going to be spawned. You can use {@link ParticleTypes}
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
        super(world, EffectType.REPEATING, particle, origin);
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
     * @param particle The particle effect that is going to be spawned. You can use {@link ParticleTypes}
     * @param origin The origin position of the line
     * @param target The ending position of the line
     * @param particles The number of particles that make up the line
     * */
    public LineEffect(ServerWorld world, ParticleEffect particle, Vec3d origin, Vec3d target, int particles) {
        super(world, EffectType.REPEATING, particle, origin);
        this.targetPos = target;
        this.particles = particles;
    }



    /** Returns a builder for the effect.
     *
     * @param world The world the particles are going to spawn in
     * @param particle The particle effect that is going to be spawned. You can use {@link ParticleTypes}
     * @param originPos The origin position of the effect
     *<p>
     * Setting a world, a particle effect and an origin position is ALWAYS mandatory, hence their presence in this method!
     * If this is an effect that uses Yaw and Pitch, remember to set those as well!
     * */
    public static Builder builder(ServerWorld world, ParticleEffect particle, Vec3d originPos) {
        return new Builder().world(world).particle(particle).originPos(originPos);
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

        int amount = particles;
        if(isZigZag){
            amount = amount  / zigZags;
        }

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

    public Effect getSubEffectAtEnd() {
        return subEffectAtEnd;
    }

    public void setSubEffectAtEnd(Effect subEffectAtEnd) {
        this.subEffectAtEnd = subEffectAtEnd;
    }

    /**
     * {@code LineEffect} builder static inner class.
     */
    public static final class Builder {
        private int iterations;
        private Vec3d originPos;
        private boolean updatePositions;
        private Entity entityOrigin;
        private Vec3d originOffset;
        private ServerWorld world;
        private ParticleEffect particle;
        private EffectModifier executeOnStop;
        /**
         * Should it do a zig zag?
         */
        private boolean isZigZag = false;

        /**
         * Number of zig zags in the line
         */
        private int zigZags = 10;

        /**
         * Direction of zig-zags
         */
        private Vec3d zigZagOffset = new Vec3d(0, 0.1, 0);

        /**
         * Relative direction of zig-zags
         */
        private Vec3d zigZagRelativeOffset = new Vec3d(0, 0, 0);

        /**
         * Particles per arc
         */
        private int particles = 100;

        /**
         * Length of arc
         * A non-zero value here will use a length instead of the target endpoint
         */
        private double length = 0;

        /**
         * Max length of arc
         * A non-zero value here will use this as the upper bound for the computed length
         */
        private double maxLength = 0;


        /**
         * Sub effect at end.
         * This will play a subeffect at the end origin of the line
         */
        private Effect subEffectAtEnd = null;
        private Vec3d targetPos;
        private boolean updateTargetPositions = true;
        private Entity entityTarget;
        private Vec3d targetOffset;
        private float yawOffset;
        private float pitchOffset;
        private float yaw;
        private float pitch;
        private boolean shouldUpdateYPR = true;
        private boolean useEyePosAsOrigin;
        private boolean useEyePosAsTarget;
        private boolean shouldSpawnParticlesEveryNIteration = false;
        private int spawnParticlesEveryNIteration = 5;
        private boolean shouldLimitParticlesSpawnedPerIteration = true;
        private int particleLimit = 5000;
        private boolean shouldLimitParticlesEveryNIterations = false;
        private int limitParticlesEveryNIterations = 5;

        private Builder() {
        }

        /**
         * Sets the {@code iterations} and returns a reference to this Builder enabling method chaining.
         *
         * @param iterations the {@code iterations} to set
         * @return a reference to this Builder
         */
        public Builder iterations(int iterations) {
            this.iterations = iterations;
            return this;
        }

        /**
         * Sets the {@code originPos} and returns a reference to this Builder enabling method chaining.
         *
         * @param originPos the {@code originPos} to set
         * @return a reference to this Builder
         */
        public Builder originPos(Vec3d originPos) {
            this.originPos = originPos;
            return this;
        }

        /**
         * Sets the {@code updatePositions} and returns a reference to this Builder enabling method chaining.
         *
         * @param updatePositions the {@code updatePositions} to set
         * @return a reference to this Builder
         */
        public Builder updatePositions(boolean updatePositions) {
            this.updatePositions = updatePositions;
            return this;
        }

        /**
         * Sets the {@code executeOnStop} and returns a reference to this Builder enabling method chaining.
         *
         * @param executeOnStop the {@code executeOnStop} to set
         * @return a reference to this Builder
         */
        public Builder executeOnStop(EffectModifier executeOnStop) {
            this.executeOnStop = executeOnStop;
            return this;
        }

        /**
         * Sets the {@code entityOrigin} and returns a reference to this Builder enabling method chaining.
         *
         * @param entityOrigin the {@code entityOrigin} to set
         * @return a reference to this Builder
         */
        public Builder entityOrigin(Entity entityOrigin) {
            this.entityOrigin = entityOrigin;
            return this;
        }

        /**
         * Sets the {@code useEyePosAsOrigin} and returns a reference to this Builder enabling method chaining.
         *
         * @param useEyePos the {@code useEyePosAsOrigin} to set
         * @return a reference to this Builder
         */
        public Builder useEyePosAsOrigin(boolean useEyePos) {
            this.useEyePosAsOrigin = useEyePos;
            return this;
        }

        /**
         * Sets the {@code useEyePosAsTarget} and returns a reference to this Builder enabling method chaining.
         *
         * @param useEyePos the {@code useEyePosAsTarget} to set
         * @return a reference to this Builder
         */
        public Builder useEyePosAsTarget(boolean useEyePos) {
            this.useEyePosAsTarget = useEyePos;
            return this;
        }

        /**
         * Sets the {@code originOffset} and returns a reference to this Builder enabling method chaining.
         *
         * @param originOffset the {@code originOffset} to set
         * @return a reference to this Builder
         */
        public Builder originOffset(Vec3d originOffset) {
            this.originOffset = originOffset;
            return this;
        }

        /**
         * Sets the {@code world} and returns a reference to this Builder enabling method chaining.
         *
         * @param world the {@code world} to set
         * @return a reference to this Builder
         */
        public Builder world(ServerWorld world) {
            this.world = world;
            return this;
        }

        /**
         * Sets the {@code particle} and returns a reference to this Builder enabling method chaining.
         *
         * @param particle the {@code particle} to set
         * @return a reference to this Builder
         */
        public Builder particle(ParticleEffect particle) {
            this.particle = particle;
            return this;
        }

        /**
         * Sets the {@code isZigZag} and returns a reference to this Builder enabling method chaining.
         *
         * @param isZigZag the {@code isZigZag} to set
         * @return a reference to this Builder
         */
        public Builder isZigZag(boolean isZigZag) {
            this.isZigZag = isZigZag;
            return this;
        }

        /**
         * Sets the {@code zigZags} and returns a reference to this Builder enabling method chaining.
         *
         * @param zigZags the {@code zigZags} to set
         * @return a reference to this Builder
         */
        public Builder zigZags(int zigZags) {
            this.zigZags = zigZags;
            return this;
        }

        /**
         * Sets the {@code zigZagOffset} and returns a reference to this Builder enabling method chaining.
         *
         * @param zigZagOffset the {@code zigZagOffset} to set
         * @return a reference to this Builder
         */
        public Builder zigZagOffset(Vec3d zigZagOffset) {
            this.zigZagOffset = zigZagOffset;
            return this;
        }

        /**
         * Sets the {@code zigZagRelativeOffset} and returns a reference to this Builder enabling method chaining.
         *
         * @param zigZagRelativeOffset the {@code zigZagRelativeOffset} to set
         * @return a reference to this Builder
         */
        public Builder zigZagRelativeOffset(Vec3d zigZagRelativeOffset) {
            this.zigZagRelativeOffset = zigZagRelativeOffset;
            return this;
        }

        /**
         * Sets the {@code particles} and returns a reference to this Builder enabling method chaining.
         *
         * @param particles the {@code particles} to set
         * @return a reference to this Builder
         */
        public Builder particles(int particles) {
            this.particles = particles;
            return this;
        }

        /**
         * Sets the {@code length} and returns a reference to this Builder enabling method chaining.
         *
         * @param length the {@code length} to set
         * @return a reference to this Builder
         */
        public Builder length(double length) {
            this.length = length;
            return this;
        }

        /**
         * Sets the {@code maxLength} and returns a reference to this Builder enabling method chaining.
         *
         * @param maxLength the {@code maxLength} to set
         * @return a reference to this Builder
         */
        public Builder maxLength(double maxLength) {
            this.maxLength = maxLength;
            return this;
        }

        /**
         * Sets the {@code subEffectAtEnd} and returns a reference to this Builder enabling method chaining.
         *
         * @param subEffectAtEnd the {@code subEffectAtEnd} to set
         * @return a reference to this Builder
         */
        public Builder subEffectAtEnd(Effect subEffectAtEnd) {
            this.subEffectAtEnd = subEffectAtEnd;
            return this;
        }

        /**
         * Sets the {@code targetPos} and returns a reference to this Builder enabling method chaining.
         *
         * @param targetPos the {@code targetPos} to set
         * @return a reference to this Builder
         */
        public Builder targetPos(Vec3d targetPos) {
            this.targetPos = targetPos;
            return this;
        }

        /**
         * Sets the {@code updateTargetPositions} and returns a reference to this Builder enabling method chaining.
         *
         * @param updateTargetPositions the {@code updateTargetPositions} to set
         * @return a reference to this Builder
         */
        public Builder updateTargetPositions(boolean updateTargetPositions) {
            this.updateTargetPositions = updateTargetPositions;
            return this;
        }

        /**
         * Sets the {@code entityTarget} and returns a reference to this Builder enabling method chaining.
         *
         * @param entityTarget the {@code entityTarget} to set
         * @return a reference to this Builder
         */
        public Builder entityTarget(Entity entityTarget) {
            this.entityTarget = entityTarget;
            return this;
        }

        /**
         * Sets the {@code targetOffset} and returns a reference to this Builder enabling method chaining.
         *
         * @param targetOffset the {@code targetOffset} to set
         * @return a reference to this Builder
         */
        public Builder targetOffset(Vec3d targetOffset) {
            this.targetOffset = targetOffset;
            return this;
        }

        /**
         * Sets the {@code yawOffset} and returns a reference to this Builder enabling method chaining.
         *
         * @param yawOffset the {@code yawOffset} to set
         * @return a reference to this Builder
         */
        public Builder yawOffset(float yawOffset) {
            this.yawOffset = yawOffset;
            return this;
        }

        /**
         * Sets the {@code pitchOffset} and returns a reference to this Builder enabling method chaining.
         *
         * @param pitchOffset the {@code pitchOffset} to set
         * @return a reference to this Builder
         */
        public Builder pitchOffset(float pitchOffset) {
            this.pitchOffset = pitchOffset;
            return this;
        }

        /**
         * Sets the {@code yaw} and returns a reference to this Builder enabling method chaining.
         *
         * @param yaw the {@code yaw} to set
         * @return a reference to this Builder
         */
        public Builder yaw(float yaw) {
            this.yaw = yaw;
            return this;
        }

        /**
         * Sets the {@code pitch} and returns a reference to this Builder enabling method chaining.
         *
         * @param pitch the {@code pitch} to set
         * @return a reference to this Builder
         */
        public Builder pitch(float pitch) {
            this.pitch = pitch;
            return this;
        }

        /**
         * Sets the {@code shouldUpdateYPR} and returns a reference to this Builder enabling method chaining.
         *
         * @param shouldUpdateYPR the {@code shouldUpdateYPR} to set
         * @return a reference to this Builder
         */
        public Builder shouldUpdateYPR(boolean shouldUpdateYPR) {
            this.shouldUpdateYPR = shouldUpdateYPR;
            return this;
        }

        /**
         * Returns a {@code LineEffect} built from the parameters previously set.
         *
         * @return a {@code LineEffect} built with parameters of this {@code LineEffect.Builder}
         */
        public LineEffect build() {
            return new LineEffect(this);
        }

        /**
         * Sets the {@code particleLimit} and returns a reference to this Builder enabling method chaining.
         *
         * @param val the {@code particleLimit} to set
         * @return a reference to this Builder
         */
        public Builder particleLimit(int val) {
            particleLimit = val;
            return this;
        }

        /**
         * Sets the {@code shouldLimitParticlesEveryNIterations} and returns a reference to this Builder enabling method chaining.
         *
         * @param val the {@code shouldLimitParticlesEveryNIterations} to set
         * @return a reference to this Builder
         */
        public Builder shouldLimitParticlesEveryNIterations(boolean val) {
            shouldLimitParticlesEveryNIterations = val;
            return this;
        }

        /**
         * Sets the {@code limitParticlesEveryNIterations} and returns a reference to this Builder enabling method chaining.
         *
         * @param val the {@code limitParticlesEveryNIterations} to set
         * @return a reference to this Builder
         */
        public Builder limitParticlesEveryNIterations(int val) {
            limitParticlesEveryNIterations = val;
            return this;
        }

        /**
         * Sets the {@code shouldLimitParticlesSpawnedPerIteration} and returns a reference to this Builder enabling method chaining.
         *
         * @param val the {@code shouldLimitParticlesSpawnedPerIteration} to set
         * @return a reference to this Builder
         */
        public Builder shouldLimitParticlesSpawnedPerIteration(boolean val) {
            shouldLimitParticlesSpawnedPerIteration = val;
            return this;
        }

        /**
         * Sets the {@code spawnParticlesEveryNIteration} and returns a reference to this Builder enabling method chaining.
         *
         * @param val the {@code spawnParticlesEveryNIteration} to set
         * @return a reference to this Builder
         */
        public Builder spawnParticlesEveryNIteration(int val) {
            spawnParticlesEveryNIteration = val;
            return this;
        }

        /**
         * Sets the {@code shouldSpawnParticlesEveryNIteration} and returns a reference to this Builder enabling method chaining.
         *
         * @param val the {@code shouldSpawnParticlesEveryNIteration} to set
         * @return a reference to this Builder
         */
        public Builder shouldSpawnParticlesEveryNIteration(boolean val) {
            shouldSpawnParticlesEveryNIteration = val;
            return this;
        }

        private boolean forced = false;
        /**
         * Sets the {@code forced} and returns a reference to this Builder enabling method chaining.
         *
         * @param forced the {@code forced} to set
         * @return a reference to this Builder
         */
        public Builder forced(boolean forced) {
            this.forced = forced;
            return this;
        }
    }
}
