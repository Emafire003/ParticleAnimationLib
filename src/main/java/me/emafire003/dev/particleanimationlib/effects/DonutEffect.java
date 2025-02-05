package me.emafire003.dev.particleanimationlib.effects;

import me.emafire003.dev.particleanimationlib.EffectType;
import me.emafire003.dev.particleanimationlib.effects.base.YPREffect;
import me.emafire003.dev.particleanimationlib.util.EffectModifier;
import me.emafire003.dev.particleanimationlib.util.VectorUtils;
import net.minecraft.entity.Entity;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.Vec3d;

public class DonutEffect extends YPREffect {

    /**
     * Amount of particles inside a single vertical circle
     */
    public int particlesCircle = 10;

    /**
     * Amount of circles to build the torus
     */
    public int circles = 36;

    /**
     * Radius of the torus
     */
    public float radiusDonut = 2;

    /**
     * Radius of the tube (the circles on the outside).
     */
    public float radiusTube = .5f;

    /**
     * Rotation of the torus.
     */
    public Vec3d rotation = Vec3d.ZERO;

    /**
     * Increases the donut radius this much each tick
     */
    public float radiusDonutIncrease = 0;

    /**
     * Increases the radius of the tube (the circles on the outside) this much each iteration
     */
    public float radiusTubeIncrease = 0;

    /**
     * Increases the amount of particles inside a single vertical circle this much each iteration
     */
    public int particlesCircleIncrease = 0;

    /**Increase the amount of circles making up the donut*/
    public int circlesIncrease = 0;

    /**Increases the number of circles by {@code circleIncrease} once every tot iterations
     * By default it's 20 iterations, so once every second*/
    public int increaseCirclesEveryNIterations = 20;

    /**Increases the number of particles per circle by {@code particlesCircleIncrease} once every tot iterations
     * By default it's 20 iterations, so once every second*/
    public int increaseParticlesCircleEveryNIterations = 20;

    // Calculations
    private int iteration = 0;

    /** Creates a new donut effect
     * <p>
     * A few details: The rotation is in radians, while the yaw/pitch are like in vanilla, in degrees
     * Rotation is applied AFTER the yaw and pitch.
     * Yaw and pitch are adjusted so the hole faces the player when given the yaw and pitch values.
     *
     * @param world The world the particles are going to spawn in
     * @param particle The particle effect that is going to be spawned. You can use {@link ParticleTypes}
     * @param originPos The origin position of the effect
     * @param yaw The yaw of the effect. For example, you can get it from an Entity using getYaw()
     * @param pitch The pitch of the effect. For example, you can get it from an Entity using getPitch()
     * @param circles The amount of circles to build the torus with (this will go on the "outside" of the hole, the more, the more defined the donut will be)
     * @param radiusDonut The radius of whole the donut/torus
     * @param radiusTube The radius of the tube (the individual circles that make the tube part of the donut torus. Not the hole, the donut part)
     * @param rotation The rotation of the whole donut/torus
     * @param radiusDonutIncrease The amount used to increase the radius of the donut per iteration/tick. (also works with negatives!)
     * @param radiusTubeIncrease The amount used to increase the radius of the tube per iteration/tick (also works with negatives!)
     * @param particlesCircle The number of particles that make up each circle
     * @param particlesCircleIncrease The number of particles used to increase the circles' each iteration/tick (also works with negatives!)
     * @param increaseParticlesCircleEveryNIterations Increases the number of particles per circle by {@code particlesCircleIncrease} once every tot iterations
     *      * By default it's 20 iterations, so once every second
     * @param circlesIncrease Increase the number of circles by this much every n iterations specified next (also works with negatives!)
     * @param increaseCirclesEveryNIterations Increases the number of circles by {@code circleIncrease} once every tot iterations
     *      * By default it's 20 iterations, so once every second
     * */
    public DonutEffect(ServerWorld world, ParticleEffect particle, Vec3d originPos, float yaw, float pitch, int particlesCircle, int circles, float radiusDonut, float radiusTube, Vec3d rotation, float radiusDonutIncrease, float radiusTubeIncrease, int particlesCircleIncrease, int increaseParticlesCircleEveryNIterations, int circlesIncrease, int increaseCirclesEveryNIterations) {
        super(world, EffectType.REPEATING, particle, originPos);
        this.yaw = yaw;
        this.pitch = pitch;
        this.particlesCircle = particlesCircle;
        this.particlesCircleIncrease = particlesCircleIncrease;
        this.circles = circles;
        this.radiusDonut = radiusDonut;
        this.radiusTube = radiusTube;
        this.rotation = rotation;
        this.radiusDonutIncrease = radiusDonutIncrease;
        this.radiusTubeIncrease = radiusTubeIncrease;
        this.circlesIncrease = circlesIncrease;
        this.increaseParticlesCircleEveryNIterations = increaseParticlesCircleEveryNIterations;
        this.increaseCirclesEveryNIterations = increaseCirclesEveryNIterations;
    }

    public DonutEffect(ServerWorld world, ParticleEffect particle, Vec3d originPos){
        super(world, EffectType.REPEATING, particle, originPos);
    }

    public static void copy(DonutEffect original, DonutEffect copy) {
        YPREffect.copy(original, copy);
        copy.setCircles(original.getCircles());
        copy.setRadiusDonut(original.getRadiusDonut());
        copy.setRadiusTube(original.getRadiusTube());
        copy.setRadiusDonutIncrease(original.getRadiusDonutIncrease());
        copy.setRadiusTubeIncrease(original.getRadiusTubeIncrease());
        copy.setRotation(original.getRotation());
        copy.setParticlesCircle(original.getParticlesCircle());
        copy.setParticlesCircleIncrease(original.getParticlesCircleIncrease());
        copy.setIncreaseCirclesEveryNIterations(original.getIncreaseCirclesEveryNIterations());
        copy.setCirclesIncrease(original.getCirclesIncrease());
        copy.setIncreaseCirclesEveryNIterations(original.getIncreaseCirclesEveryNIterations());
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

    private DonutEffect(Builder builder) {
        super(builder.world, EffectType.REPEATING, builder.particle, builder.originPos);
        setIterations(builder.iterations);
        setOriginPos(builder.originPos);
        setUpdatePositions(builder.updatePositions);
        setUseEyePosAsOrigin(builder.useEyePosAsOrigin);
        setEntityOrigin(builder.entityOrigin);
        setOriginOffset(builder.originOffset);
        setExecuteOnStop(builder.executeOnStop);
        setShouldSpawnParticlesEveryNIteration(builder.shouldSpawnParticlesEveryNIteration);
        setSpawnParticlesEveryNIteration(builder.spawnParticlesEveryNIteration);
        setShouldLimitParticlesSpawnedPerIteration(builder.shouldLimitParticlesSpawnedPerIteration);
        setParticleLimit(builder.particleLimit);
        setShouldLimitParticlesEveryNIterations(builder.shouldLimitParticlesEveryNIterations);
        setLimitParticlesEveryNIterations(builder.limitParticlesEveryNIterations);
        setWorld(builder.world);
        setParticle(builder.particle);
        setYawOffset(builder.yawOffset);
        setPitchOffset(builder.pitchOffset);
        setYaw(builder.yaw);
        setPitch(builder.pitch);
        setShouldUpdateYPR(builder.shouldUpdateYPR);
        setParticlesCircle(builder.particlesCircle);
        setCircles(builder.circles);
        setRadiusDonut(builder.radiusDonut);
        setRadiusTube(builder.radiusTube);
        setRotation(builder.rotation);
        setRadiusDonutIncrease(builder.radiusDonutIncrease);
        setRadiusTubeIncrease(builder.radiusTubeIncrease);
        setParticlesCircleIncrease(builder.particlesCircleIncrease);
        setCirclesIncrease(builder.circlesIncrease);
        setIncreaseCirclesEveryNIterations(builder.increaseCirclesEveryNIterations);
        setIncreaseParticlesCircleEveryNIterations(builder.increaseParticlesCircleEveryNIterations);
    }

    @Override
    public void onRun() {
        Vec3d origin = this.getOriginPos();
        Vec3d v;

        if (origin == null){
            return;
        }

        //If the donut should grow, update the values

        if (this.radiusDonutIncrease != 0){
            radiusDonut += radiusDonutIncrease;
        }
        if (this.radiusTubeIncrease != 0){
            radiusTube += radiusTubeIncrease;
        }
        //Checks if it's the right iteration time to add new particles to the circle
        if (increaseParticlesCircleEveryNIterations == 0 || iteration%increaseParticlesCircleEveryNIterations == 0){
            if (particlesCircleIncrease != 0){
                particlesCircle += particlesCircleIncrease;
            }
        }
        //Same here but for the number of circles
        if (increaseCirclesEveryNIterations == 0 || iteration%increaseCirclesEveryNIterations == 0){
            if (circlesIncrease != 0){
                circles += circlesIncrease;
            }
        }

        for (int i = 0; i < circles; i++) {
            double theta = 2 * Math.PI * i / circles;
            for (int j = 0; j < particlesCircle; j++) {
                double phi = 2 * Math.PI * j / particlesCircle;
                double cosPhi = Math.cos(phi);
                v = new Vec3d((radiusDonut + radiusTube * cosPhi) * Math.cos(theta), (radiusDonut + radiusTube * cosPhi) * Math.sin(theta), radiusTube * Math.sin(phi));

                v = VectorUtils.rotateVector(v, this.getYaw()+90, this.getPitch()+90);
                v = VectorUtils.rotateVector(v, (float) rotation.x, (float) rotation.y, (float) rotation.z);

                this.displayParticle(this.particle, origin.add(v));
            }
        }
        iteration++;
        if(iterations==iteration){
            iteration = 0;
        }
    }

    public int getParticlesCircle() {
        return particlesCircle;
    }

    public void setParticlesCircle(int particlesCircle) {
        this.particlesCircle = particlesCircle;
    }

    public int getCircles() {
        return circles;
    }

    public void setCircles(int circles) {
        this.circles = circles;
    }

    public float getRadiusTube() {
        return radiusTube;
    }

    public void setRadiusTube(float radiusTube) {
        this.radiusTube = radiusTube;
    }

    public float getRadiusDonut() {
        return radiusDonut;
    }

    public void setRadiusDonut(float radiusDonut) {
        this.radiusDonut = radiusDonut;
    }

    public Vec3d getRotation() {
        return rotation;
    }

    public void setRotation(Vec3d rotation) {
        this.rotation = rotation;
    }

    public float getRadiusDonutIncrease() {
        return radiusDonutIncrease;
    }

    public void setRadiusDonutIncrease(float radiusDonutIncrease) {
        this.radiusDonutIncrease = radiusDonutIncrease;
    }

    public int getParticlesCircleIncrease() {
        return particlesCircleIncrease;
    }

    public void setParticlesCircleIncrease(int particlesCircleIncrease) {
        this.particlesCircleIncrease = particlesCircleIncrease;
    }

    public float getRadiusTubeIncrease() {
        return radiusTubeIncrease;
    }

    public void setRadiusTubeIncrease(float radiusTubeIncrease) {
        this.radiusTubeIncrease = radiusTubeIncrease;
    }

    public int getCirclesIncrease() {
        return circlesIncrease;
    }

    public void setCirclesIncrease(int circlesIncrease) {
        this.circlesIncrease = circlesIncrease;
    }

    public int getIncreaseCirclesEveryNIterations() {
        return increaseCirclesEveryNIterations;
    }

    public void setIncreaseCirclesEveryNIterations(int increaseCirclesEveryNIterations) {
        this.increaseCirclesEveryNIterations = increaseCirclesEveryNIterations;
    }

    public int getIncreaseParticlesCircleEveryNIterations() {
        return increaseParticlesCircleEveryNIterations;
    }

    public void setIncreaseParticlesCircleEveryNIterations(int increaseParticlesCircleEveryNIterations) {
        this.increaseParticlesCircleEveryNIterations = increaseParticlesCircleEveryNIterations;
    }

    /**
     * {@code DonutEffect} builder static inner class.
     */
    public static final class Builder {
        private int iterations;
        private Vec3d originPos;
        private boolean updatePositions;
        private boolean useEyePosAsOrigin;
        private Entity entityOrigin;
        private Vec3d originOffset;
        private EffectModifier executeOnStop;
        private boolean shouldSpawnParticlesEveryNIteration;
        private int spawnParticlesEveryNIteration;
        private boolean shouldLimitParticlesSpawnedPerIteration;
        private int particleLimit;
        private boolean shouldLimitParticlesEveryNIterations;
        private int limitParticlesEveryNIterations;
        private ServerWorld world;
        private ParticleEffect particle;
        private float yawOffset;
        private float pitchOffset;
        private float yaw;
        private float pitch;
        private boolean shouldUpdateYPR;
        private int particlesCircle;
        private int circles;
        private float radiusDonut;
        private float radiusTube;
        private Vec3d rotation;
        private float radiusDonutIncrease;
        private float radiusTubeIncrease;
        private int particlesCircleIncrease;
        private int circlesIncrease;
        private int increaseCirclesEveryNIterations;
        private int increaseParticlesCircleEveryNIterations;

        public Builder() {
        }

        /**
         * Sets the {@code iterations} and returns a reference to this Builder enabling method chaining.
         *
         * @param val the {@code iterations} to set
         * @return a reference to this Builder
         */
        public Builder iterations(int val) {
            iterations = val;
            return this;
        }

        /**
         * Sets the {@code originPos} and returns a reference to this Builder enabling method chaining.
         *
         * @param val the {@code originPos} to set
         * @return a reference to this Builder
         */
        public Builder originPos(Vec3d val) {
            originPos = val;
            return this;
        }

        /**
         * Sets the {@code updatePositions} and returns a reference to this Builder enabling method chaining.
         *
         * @param val the {@code updatePositions} to set
         * @return a reference to this Builder
         */
        public Builder updatePositions(boolean val) {
            updatePositions = val;
            return this;
        }

        /**
         * Sets the {@code useEyePosAsOrigin} and returns a reference to this Builder enabling method chaining.
         *
         * @param val the {@code useEyePosAsOrigin} to set
         * @return a reference to this Builder
         */
        public Builder useEyePosAsOrigin(boolean val) {
            useEyePosAsOrigin = val;
            return this;
        }

        /**
         * Sets the {@code entityOrigin} and returns a reference to this Builder enabling method chaining.
         *
         * @param val the {@code entityOrigin} to set
         * @return a reference to this Builder
         */
        public Builder entityOrigin(Entity val) {
            entityOrigin = val;
            return this;
        }

        /**
         * Sets the {@code originOffset} and returns a reference to this Builder enabling method chaining.
         *
         * @param val the {@code originOffset} to set
         * @return a reference to this Builder
         */
        public Builder originOffset(Vec3d val) {
            originOffset = val;
            return this;
        }

        /**
         * Sets the {@code executeOnStop} and returns a reference to this Builder enabling method chaining.
         *
         * @param val the {@code executeOnStop} to set
         * @return a reference to this Builder
         */
        public Builder executeOnStop(EffectModifier val) {
            executeOnStop = val;
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

        /**
         * Sets the {@code world} and returns a reference to this Builder enabling method chaining.
         *
         * @param val the {@code world} to set
         * @return a reference to this Builder
         */
        public Builder world(ServerWorld val) {
            world = val;
            return this;
        }

        /**
         * Sets the {@code particle} and returns a reference to this Builder enabling method chaining.
         *
         * @param val the {@code particle} to set
         * @return a reference to this Builder
         */
        public Builder particle(ParticleEffect val) {
            particle = val;
            return this;
        }

        /**
         * Sets the {@code yawOffset} and returns a reference to this Builder enabling method chaining.
         *
         * @param val the {@code yawOffset} to set
         * @return a reference to this Builder
         */
        public Builder yawOffset(float val) {
            yawOffset = val;
            return this;
        }

        /**
         * Sets the {@code pitchOffset} and returns a reference to this Builder enabling method chaining.
         *
         * @param val the {@code pitchOffset} to set
         * @return a reference to this Builder
         */
        public Builder pitchOffset(float val) {
            pitchOffset = val;
            return this;
        }

        /**
         * Sets the {@code yaw} and returns a reference to this Builder enabling method chaining.
         *
         * @param val the {@code yaw} to set
         * @return a reference to this Builder
         */
        public Builder yaw(float val) {
            yaw = val;
            return this;
        }

        /**
         * Sets the {@code pitch} and returns a reference to this Builder enabling method chaining.
         *
         * @param val the {@code pitch} to set
         * @return a reference to this Builder
         */
        public Builder pitch(float val) {
            pitch = val;
            return this;
        }

        /**
         * Sets the {@code shouldUpdateYPR} and returns a reference to this Builder enabling method chaining.
         *
         * @param val the {@code shouldUpdateYPR} to set
         * @return a reference to this Builder
         */
        public Builder shouldUpdateYPR(boolean val) {
            shouldUpdateYPR = val;
            return this;
        }

        /**
         * Sets the {@code particlesCircle} and returns a reference to this Builder enabling method chaining.
         *
         * @param val the {@code particlesCircle} to set
         * @return a reference to this Builder
         */
        public Builder particlesCircle(int val) {
            particlesCircle = val;
            return this;
        }

        /**
         * Sets the {@code circles} and returns a reference to this Builder enabling method chaining.
         *
         * @param val the {@code circles} to set
         * @return a reference to this Builder
         */
        public Builder circles(int val) {
            circles = val;
            return this;
        }

        /**
         * Sets the {@code radiusDonut} and returns a reference to this Builder enabling method chaining.
         *
         * @param val the {@code radiusDonut} to set
         * @return a reference to this Builder
         */
        public Builder radiusDonut(float val) {
            radiusDonut = val;
            return this;
        }

        /**
         * Sets the {@code radiusTube} and returns a reference to this Builder enabling method chaining.
         *
         * @param val the {@code radiusTube} to set
         * @return a reference to this Builder
         */
        public Builder radiusTube(float val) {
            radiusTube = val;
            return this;
        }

        /**
         * Sets the {@code rotation} and returns a reference to this Builder enabling method chaining.
         *
         * @param val the {@code rotation} to set
         * @return a reference to this Builder
         */
        public Builder rotation(Vec3d val) {
            rotation = val;
            return this;
        }

        /**
         * Sets the {@code radiusDonutIncrease} and returns a reference to this Builder enabling method chaining.
         *
         * @param val the {@code radiusDonutIncrease} to set
         * @return a reference to this Builder
         */
        public Builder radiusDonutIncrease(float val) {
            radiusDonutIncrease = val;
            return this;
        }

        /**
         * Sets the {@code radiusTubeIncrease} and returns a reference to this Builder enabling method chaining.
         *
         * @param val the {@code radiusTubeIncrease} to set
         * @return a reference to this Builder
         */
        public Builder radiusTubeIncrease(float val) {
            radiusTubeIncrease = val;
            return this;
        }

        /**
         * Sets the {@code particlesCircleIncrease} and returns a reference to this Builder enabling method chaining.
         *
         * @param val the {@code particlesCircleIncrease} to set
         * @return a reference to this Builder
         */
        public Builder particlesCircleIncrease(int val) {
            particlesCircleIncrease = val;
            return this;
        }

        /**
         * Sets the {@code circlesIncrease} and returns a reference to this Builder enabling method chaining.
         *
         * @param val the {@code circlesIncrease} to set
         * @return a reference to this Builder
         */
        public Builder circlesIncrease(int val) {
            circlesIncrease = val;
            return this;
        }

        /**
         * Sets the {@code increaseCirclesEveryNIterations} and returns a reference to this Builder enabling method chaining.
         *
         * @param val the {@code increaseCirclesEveryNIterations} to set
         * @return a reference to this Builder
         */
        public Builder increaseCirclesEveryNIterations(int val) {
            increaseCirclesEveryNIterations = val;
            return this;
        }

        /**
         * Sets the {@code increaseParticlesCircleEveryNIterations} and returns a reference to this Builder enabling method chaining.
         *
         * @param val the {@code increaseParticlesCircleEveryNIterations} to set
         * @return a reference to this Builder
         */
        public Builder increaseParticlesCircleEveryNIterations(int val) {
            increaseParticlesCircleEveryNIterations = val;
            return this;
        }

        /**
         * Returns a {@code DonutEffect} built from the parameters previously set.
         *
         * @return a {@code DonutEffect} built with parameters of this {@code DonutEffect.Builder}
         */
        public DonutEffect build() {
            return new DonutEffect(this);
        }
    }
}
