package me.emafire003.dev.particleanimationlib.effects;

import me.emafire003.dev.particleanimationlib.Effect;
import me.emafire003.dev.particleanimationlib.EffectType;
import me.emafire003.dev.particleanimationlib.util.EffectModifier;
import me.emafire003.dev.particleanimationlib.util.RandomUtils;
import net.minecraft.entity.Entity;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.particle.ParticleTypes;
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

    /** Amount to increase the particles per tick*/
    public int particleIncrease = 0;
    
    //TODO new stuff
    /**Should it display as an half sphere?*/
    public boolean halfSphere = false;
    
    /**If it is an half sphere, should it be inverted the other way around? Like upside down*/
    public boolean invertHalfSphere = false;

    /**
     * Creates a new sphere effect.
     *
     * @param world The world the particles are going to spawn in
     * @param particle The particle effect that is going to be spawned. You can use {@link ParticleTypes}
     * @param origin The origin position of the effect. Aka the center of the sphere
     * @param particles The number of particles the sphere will be made of
     * @param radius The radius of the sphere
     * @param radiusIncrease The amount used to increase the radius per iteration/tick
     * @param particleIncrease The amount used to increase the particles per iteration/tick
     * @param half_sphere If true, displays as an half sphere/dome
     * @param invert_half_sphere If true and displaying as an half sphere, displays the bottom half
     * */
    public SphereEffect(ServerWorld world, ParticleEffect particle, Vec3d origin, int particles, double radius, double radiusIncrease, int particleIncrease, boolean half_sphere, boolean invert_half_sphere) {
        super(world, EffectType.REPEATING, particle, origin);
        this.particles = particles;
        this.radius = radius;
        this.radiusIncrease = radiusIncrease;
        this.particleIncrease = particleIncrease;
        this.halfSphere = half_sphere;
        this.invertHalfSphere = invert_half_sphere;
    }

    private SphereEffect(Builder builder) {
        super(builder.world, EffectType.REPEATING, builder.particle, builder.originPos);
        setIterations(builder.iterations);
        setOriginPos(builder.originPos);
        setUpdatePositions(builder.updatePositions);
        setEntityOrigin(builder.entityOrigin);
        setOriginOffset(builder.originOffset);
        world = builder.world;
        particle = builder.particle;
        setRadius(builder.radius);
        particles = builder.particles;
        setRadiusIncrease(builder.radiusIncrease);
        setParticleIncrease(builder.particleIncrease);
        setUseEyePosAsOrigin(builder.useEyePosAsOrigin);
        setExecuteOnStop(builder.executeOnStop);
        setHalfSphere(builder.halfSphere);
        setInvertedHalfSphere(builder.invertHalfSphere);
        setShouldSpawnParticlesEveryNIteration(builder.shouldSpawnParticlesEveryNIteration);
        setSpawnParticlesEveryNIteration(builder.spawnParticlesEveryNIteration);
        setShouldLimitParticlesSpawnedPerIteration(builder.shouldLimitParticlesSpawnedPerIteration);
        setParticleLimit(builder.particleLimit);
        setShouldLimitParticlesEveryNIterations(builder.shouldLimitParticlesEveryNIterations);
        setLimitParticlesEveryNIterations(builder.limitParticlesEveryNIterations);
        setForced(builder.forced);
    }

    public static void copy(SphereEffect original, SphereEffect copy) {
        Effect.copy(original, copy);
        copy.setRadius(original.getRadius());
        copy.setRadiusIncrease(original.getRadiusIncrease());
        copy.setParticleIncrease(original.getParticleIncrease());
        copy.setParticles(original.getParticles());
        copy.setHalfSphere(original.isHalfSphere());
        copy.setInvertedHalfSphere(original.isInvertHalSphere());
    }

    /**
     * Creates a new sphere effect.
     *
     * @param world The world the particles are going to spawn in
     * @param particle The particle effect that is going to be spawned. You can use {@link ParticleTypes}
     * @param origin The origin position of the effect. Aka the center of the sphere
     * */
    public SphereEffect(ServerWorld world, ParticleEffect particle, Vec3d origin) {
        super(world, EffectType.REPEATING, particle, origin);
    }

    /**
     * Creates a new sphere effect.
     *
     * @param world The world the particles are going to spawn in
     * @param particle The particle effect that is going to be spawned. You can use {@link ParticleTypes}
     * @param origin The origin position of the effect. Aka the center of the sphere
     * @param particles The number of particles the sphere will be made of
     * @param radius The radius of the sphere
     * */
    public SphereEffect(ServerWorld world, ParticleEffect particle, Vec3d origin, int particles, double radius) {
        super(world, EffectType.REPEATING, particle, origin);
        this.particles = particles;
        this.radius = radius;
    }



    
    /** Returns a builder for the effect.
     *
     * @param world The world the particles are going to spawn in
     * @param particle The particle effect that is going to be spawned. You can use {@link ParticleTypes}
     * @param originPos The origin position of the effect
     * <p>
     *  Setting a world, a particle effect and an origin position is ALWAYS mandatory, hence their presence in this method!
     * If this is an effect that uses Yaw and Pitch, remember to set those as well!
     * */
    public static Builder builder(ServerWorld world, ParticleEffect particle, Vec3d originPos) {
        return new Builder().world(world).particle(particle).originPos(originPos);
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
        Vec3d vector;

        for (int i = 0; i < particles; i++) {
            vector = RandomUtils.getRandomVector().multiply(radius);
            if (halfSphere) {
                if (invertHalfSphere){
                    vector = new Vec3d(vector.getX(), Math.abs(vector.getY()) * -1, vector.getZ());
                }
                else {
                    vector = new Vec3d(vector.getX(), Math.abs(vector.getY()), vector.getZ());
                }
            }
            this.displayParticle(particle, origin.add(vector));
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
    public int getParticles() {
        return particles;
    }

    public void setParticles(int particles) {
        this.particles = particles;
    }

    public boolean isHalfSphere() {
        return halfSphere;
    }

    public void setHalfSphere(boolean half_sphere) {
        this.halfSphere = half_sphere;
    }

    public boolean isInvertHalSphere() {
        return invertHalfSphere;
    }

    public void setInvertedHalfSphere(boolean invert_half_sphere) {
        this.invertHalfSphere = invert_half_sphere;
    }

    /**
     * {@code SphereEffect} builder static inner class.
     */
    public static final class Builder {
        private int iterations;
        private Vec3d originPos;
        private boolean updatePositions;
        private Entity entityOrigin;
        private Vec3d originOffset;
        private ServerWorld world;
        private ParticleEffect particle;
        private boolean useEyePosAsOrigin;
        private EffectModifier executeOnStop;
        /**
         * Radius of the sphere
         */
        private double radius = 0.6;


        /**
         * Particles to display
         */
        private int particles = 50;

        /**
         * Amount to increase the radius per tick
         */
        private double radiusIncrease = 0;

        // Amount to increase the particles per tick
        private int particleIncrease = 0;

        /**
         * Should it display as an half sphere?
         */
        public boolean halfSphere = false;

        /**
         * If it is an half sphere, should it be inverted the other way around? Like upside down
         */
        public boolean invertHalfSphere = false;
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
         * Sets the {@code radius} and returns a reference to this Builder enabling method chaining.
         *
         * @param radius the {@code radius} to set
         * @return a reference to this Builder
         */
        public Builder radius(double radius) {
            this.radius = radius;
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
         * Sets the {@code radiusIncrease} and returns a reference to this Builder enabling method chaining.
         *
         * @param radiusIncrease the {@code radiusIncrease} to set
         * @return a reference to this Builder
         */
        public Builder radiusIncrease(double radiusIncrease) {
            this.radiusIncrease = radiusIncrease;
            return this;
        }

        /**
         * Sets the {@code particleIncrease} and returns a reference to this Builder enabling method chaining.
         *
         * @param particleIncrease the {@code particleIncrease} to set
         * @return a reference to this Builder
         */
        public Builder particleIncrease(int particleIncrease) {
            this.particleIncrease = particleIncrease;
            return this;
        }

        /**
         * Sets the {@code halfSphere} and returns a reference to this Builder enabling method chaining.
         *
         * @param halfSphere the {@code halfSphere} to set
         * @return a reference to this Builder
         */
        public Builder halfSphere(boolean halfSphere) {
            this.halfSphere = halfSphere;
            return this;
        }

        /**
         * Sets the {@code invertHalfSphere} and returns a reference to this Builder enabling method chaining.
         *
         * @param invertHalfSphere the {@code invertHalfSphere} to set
         * @return a reference to this Builder
         */
        public Builder invertHalfSphere(boolean invertHalfSphere) {
            this.invertHalfSphere = invertHalfSphere;
            return this;
        }

        /**
         * Returns a {@code SphereEffect} built from the parameters previously set.
         *
         * @return a {@code SphereEffect} built with parameters of this {@code SphereEffect.Builder}
         */
        public SphereEffect build() {
            return new SphereEffect(this);
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
