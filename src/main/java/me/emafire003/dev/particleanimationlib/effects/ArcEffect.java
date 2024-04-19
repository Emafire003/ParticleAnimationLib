package me.emafire003.dev.particleanimationlib.effects;

import me.emafire003.dev.particleanimationlib.EffectType;
import me.emafire003.dev.particleanimationlib.effects.base.TargetedEffect;
import net.minecraft.entity.Entity;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.Vec3d;

@SuppressWarnings("unused")
public class ArcEffect extends TargetedEffect {


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
     * @param particle The particle effect that is going to be spawned. You can use {@link ParticleTypes}
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
     * @param particle The particle effect that is going to be spawned. You can use {@link ParticleTypes}
     * @param origin The origin position of the effect, aka the initial point of the arc
     * @param target The target position of the effect, aka the finial point of the arc
     */
    public ArcEffect(ServerWorld world, ParticleEffect particle, Vec3d origin, Vec3d target) {
        super(world, EffectType.REPEATING, particle, origin);
        this.setTargetPos(target);
    }

    public static void copy(ArcEffect original, ArcEffect copy) {
        TargetedEffect.copy(original, copy);
        copy.setHeight(original.getHeight());
        copy.setParticles(original.getParticles());
        copy.step = original.step;
    }

    /**
     * Creates a new arc effect between two points
     *
     * @param world The world the particles are going to spawn in
     * @param particle The particle effect that is going to be spawned. You can use {@link ParticleTypes}
     * @param origin The origin position of the effect, aka the initial point of the arc
     * @param target The target position of the effect, aka the finial point of the arc
     * @param count The number of particles to spread between the two points
     */
    public ArcEffect(ServerWorld world, ParticleEffect particle, Vec3d origin, Vec3d target, int count) {
        super(world, EffectType.REPEATING, particle, origin);
        this.setTargetPos(target);
        this.particles = count;
    }

    private ArcEffect(Builder builder) {
        super(builder.world, EffectType.REPEATING, builder.particle, builder.originPos);
        setIterations(builder.iterations);
        setOriginPos(builder.originPos);
        setUpdatePositions(builder.updatePositions);
        setEntityOrigin(builder.entityOrigin);
        setOriginOffset(builder.originOffset);
        world = builder.world;
        particle = builder.particle;
        setHeight(builder.height);
        setParticles(builder.particles);
        setTargetPos(builder.targetPos);
        setUpdateTargetPositions(builder.updateTargetPositions);
        setEntityTarget(builder.entityTarget);
        setTargetOffset(builder.targetOffset);
        setUseEyePosAsOrigin(builder.useEyePosAsOrigin);
        setUseEyePosAsTarget(builder.useEyePosAsTarget);
    }

    /** Returns a builder for the effect.
     *
     * @param world The world the particles are going to spawn in
     * @param particle The particle effect that is going to be spawned. You can use {@link ParticleTypes}
     * @param originPos The origin position of the effect
     *<p>
     *  Setting a world, a particle effect and an origin position is ALWAYS mandatory, hence their presence in this method!
     * If this is an effect that uses Yaw and Pitch, remember to set those as well!
     * */
    public static Builder builder(ServerWorld world, ParticleEffect particle, Vec3d originPos) {
        return new Builder().world(world).particle(particle).originPos(originPos);
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

        for (int i = 0; i < particles; i++) {

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

    /**
     * {@code ArcEffect} builder static inner class.
     */
    public static final class Builder {
        private int iterations;
        private Vec3d originPos;
        private boolean updatePositions;
        private Entity entityOrigin;
        private Vec3d originOffset;
        private ServerWorld world;
        private ParticleEffect particle;
        /**
         * Height of the arc in blocks
         */
        private float height = 2;

        /**
         * Particles per arc
         */
        private int particles = 100;
        private Vec3d targetPos;
        private boolean updateTargetPositions = true;
        private Entity entityTarget;
        private Vec3d targetOffset;
        private boolean useEyePosAsOrigin;
        private boolean useEyePosAsTarget;

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
         * Sets the {@code height} and returns a reference to this Builder enabling method chaining.
         *
         * @param height the {@code height} to set
         * @return a reference to this Builder
         */
        public Builder height(float height) {
            this.height = height;
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
         * Returns a {@code ArcEffect} built from the parameters previously set.
         *
         * @return a {@code ArcEffect} built with parameters of this {@code ArcEffect.Builder}
         */
        public ArcEffect build() {
            return new ArcEffect(this);
        }
    }
}
