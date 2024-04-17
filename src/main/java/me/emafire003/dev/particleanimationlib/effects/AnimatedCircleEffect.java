package me.emafire003.dev.particleanimationlib.effects;

import me.emafire003.dev.particleanimationlib.EffectType;
import me.emafire003.dev.particleanimationlib.effects.base.YPREffect;
import me.emafire003.dev.particleanimationlib.util.VectorUtils;
import net.minecraft.entity.Entity;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.Vec3d;

@SuppressWarnings("unused")
public class AnimatedCircleEffect extends YPREffect {

    /**
     * Rotation of the torus.
     */
    public float xRotation, yRotation, zRotation = 0;

    /**
     * Turns the circle by this angle each iteration around the x-axis
     */
    public double angularVelocityX = Math.PI / 200;

    /**
     * Turns the circle by this angle each iteration around the y-axis
     */
    public double angularVelocityY = Math.PI / 170;

    /**
     * Turns the circle by this angle each iteration around the z-axis
     */
    public double angularVelocityZ = Math.PI / 155;

    /**
     * Radius of circle above head
     */
    public float radius = 0.4F;

    /**
     * Used to make a partial circle
     */
    public double maxAngle = Math.PI * 2;

    /**
     * Start at the same origin each step, use this
     * along with maxAngle and wholeCircle to form persistent semicircles
     */
    public boolean resetCircle = false;

    /**
     * Subtracts from origin if needed
     */
    public double xSubtract, ySubtract, zSubtract;

    /**
     * Should it rotate?
     */
    public boolean enableRotation = true;

    /**
     * Amount of particles per circle
     */
    public int particles = 20;

    /**
     * To make a whole circle each iteration
     */
    public boolean wholeCircle = false;

    //Added by Emafire003
    /**
     * Growth in blocks per iteration on the radius
     * Setting to zero will disable it and fall back to the normal radius
     */
    public float radiusGrow = 0;


    //Calculations
    /**
     * Current step. Works as a counter
     */
    protected float step = 0;
    

    /**
     * Creates a new circle effect
     *
     * @param world The world the particles are going to spawn in
     * @param particle The particle effect that is going to be spawned. You can use {@link ParticleTypes}
     * @param origin The origin position of the effect, aka the center point of the circle
     * @param yaw The yaw of the effect. For example, you can get it from an Entity using getYaw()
     * @param pitch The pitch of the effect. For example, you can get it from an Entity using getPitch()
     * @param particles_per_circle Number of particles that make up each circle
     * @param radius The radius of the circle
     * @param radiusGrow Radius growth amount per each iteration. Setting to 0 won't make the radius grow
     * @param maxAngle Used to make a partial circle. Expressed in radians
     * @param wholeCircle Makes a whole circle every iteration
     * @param resetCircle Start at the same origin each step, use this along with maxAngle and wholeCircle to form persistent semicircles
     * @param enableRotation Should the circle rotate?
     * @param angularVelocity A {@link Vec3d} of the angular velocities of the effect (for the rotation) expressed in radials. Turns the circle by this angle each iteration around the (x|y|z)-axis
     * @param rotations A {@link Vec3d} of Rotations of the torus/circles
     * @param subtractFromOrigin A {@link Vec3d} of values that will be subtracted from the origin is needed
     * */
    public AnimatedCircleEffect(ServerWorld world, ParticleEffect particle, Vec3d origin, float yaw, float pitch, int particles_per_circle, float radius, float radiusGrow, double maxAngle, boolean wholeCircle, boolean resetCircle, boolean enableRotation, Vec3d angularVelocity, Vec3d rotations, Vec3d subtractFromOrigin) {
        super(world, EffectType.REPEATING, particle, origin);
        this.yaw = yaw;
        this.pitch = pitch;
        this.particles = particles_per_circle;
        this.radius = radius;
        this.radiusGrow = radiusGrow;
        this.maxAngle = maxAngle;
        this.wholeCircle = wholeCircle;
        this.resetCircle = resetCircle;
        this.enableRotation = enableRotation;
        this.angularVelocityX = angularVelocity.getX();
        this.angularVelocityY = angularVelocity.getY();
        this.angularVelocityZ = angularVelocity.getZ();
        this.xRotation = (float) rotations.getX();
        this.yRotation = (float) rotations.getY();
        this.zRotation = (float) rotations.getZ();
        this.xSubtract = subtractFromOrigin.getX();
        this.ySubtract = subtractFromOrigin.getY();
        this.zSubtract = subtractFromOrigin.getZ();
    }

    /**
     * Creates a new circle effect
     *
     * @param world The world the particles are going to spawn in
     * @param particle The particle effect that is going to be spawned. You can use {@link ParticleTypes}
     * @param origin The origin position of the effect, aka the center point of the circle
     * @param yaw The yaw of the effect. For example, you can get it from an Entity using getYaw()
     * @param pitch The pitch of the effect. For example, you can get it from an Entity using getPitch()
     * */
    public AnimatedCircleEffect(ServerWorld world, ParticleEffect particle, Vec3d origin, float yaw, float pitch) {
        super(world, EffectType.REPEATING, particle, origin);
        this.yaw = yaw;
        this.pitch = pitch;
    }

    /**
     * Creates a new circle effect
     *
     * @param world The world the particles are going to spawn in
     * @param particle The particle effect that is going to be spawned. You can use {@link ParticleTypes}
     * @param origin The origin position of the effect, aka the center point of the circle
     * */
    public AnimatedCircleEffect(ServerWorld world, ParticleEffect particle, Vec3d origin) {
        super(world, EffectType.REPEATING, particle, origin);
    }


    private AnimatedCircleEffect(Builder builder) {
        super(builder.world, EffectType.REPEATING, builder.particle, builder.originPos);
        setIterations(builder.iterations);
        setOriginPos(builder.originPos);
        setUpdatePositions(builder.updatePositions);
        setEntityOrigin(builder.entityOrigin);
        setOriginOffset(builder.originOffset);
        world = builder.world;
        particle = builder.particle;
        xRotation = builder.xRotation;
        yRotation = builder.yRotation;
        zRotation = builder.zRotation;
        angularVelocityX = builder.angularVelocityX;
        angularVelocityY = builder.angularVelocityY;
        angularVelocityZ = builder.angularVelocityZ;
        setRadius(builder.radius);
        setMaxAngle(builder.maxAngle);
        setResetCircle(builder.resetCircle);
        xSubtract = builder.xSubtract;
        ySubtract = builder.ySubtract;
        zSubtract = builder.zSubtract;
        setEnableRotation(builder.enableRotation);
        setParticles(builder.particles);
        setWholeCircle(builder.wholeCircle);
        setRadiusGrow(builder.radiusGrow);
        setYawOffset(builder.yawOffset);
        setPitchOffset(builder.pitchOffset);
        setYaw(builder.yaw);
        setPitch(builder.pitch);
        setShouldUpdateYPR(builder.shouldUpdateYPR);
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

        if (origin == null) {
            return;
        }

        origin = origin.subtract(xSubtract, ySubtract, zSubtract);
        if(radiusGrow != 0){
            radius = radius+step * radiusGrow;
        }

        double inc = maxAngle / particles;
        int steps = wholeCircle ? particles : 1;

        double angle;
        Vec3d v;

        for (int i = 0; i < steps; i++) {

            angle = step * inc;
            v = new Vec3d(Math.cos(angle) * radius, 0, Math.sin(angle) * radius );

            v = VectorUtils.rotateVector(v, xRotation, yRotation, zRotation);
            v = VectorUtils.rotateVector(v, this.getYaw(), this.getPitch()+90);

            if (enableRotation) {
                v = VectorUtils.rotateVector(v, (float) (angularVelocityX * step), (float) (angularVelocityY * step), (float) (angularVelocityZ * step));
            }

            this.displayParticle(particle, origin.add(v));

            step++;
        }

        if (resetCircle){
            step = 0;
        }
    }

    public float getRadius() {
        return radius;
    }

    public void setRadius(float radius) {
        this.radius = radius;
    }

    public double getMaxAngle() {
        return maxAngle;
    }

    public void setMaxAngle(double maxAngle) {
        this.maxAngle = maxAngle;
    }

    public boolean isResetCircle() {
        return resetCircle;
    }

    public void setResetCircle(boolean resetCircle) {
        this.resetCircle = resetCircle;
    }

    public boolean isEnableRotation() {
        return enableRotation;
    }

    public void setEnableRotation(boolean enableRotation) {
        this.enableRotation = enableRotation;
    }

    public int getParticles() {
        return particles;
    }

    public void setParticles(int particles) {
        this.particles = particles;
    }

    public boolean isWholeCircle() {
        return wholeCircle;
    }

    public void setWholeCircle(boolean wholeCircle) {
        this.wholeCircle = wholeCircle;
    }

    public float getRadiusGrow() {
        return radiusGrow;
    }

    public void setRadiusGrow(float radiusGrow) {
        this.radiusGrow = radiusGrow;
    }

    public Vec3d getRotations() {
        return new Vec3d(xRotation, yRotation, zRotation);
    }

    public void setRotations(Vec3d rotations) {
        this.xRotation = (float) rotations.getX();
        this.yRotation = (float) rotations.getY();
        this.zRotation = (float) rotations.getZ();
    }

    public Vec3d getSubtracts() {
        return new Vec3d(xSubtract, ySubtract, zSubtract);
    }

    public void setSubtracts(Vec3d subtracts) {
        this.xSubtract = subtracts.getX();
        this.ySubtract = subtracts.getY();
        this.zSubtract = subtracts.getZ();
    }

    /**
     * {@code AnimatedCircleEffect} builder static inner class.
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
         * Rotation of the torus.
         */
        private float xRotation, yRotation, zRotation = 0;

        /**
         * Turns the circle by this angle each iteration around the x-axis
         */
        private double angularVelocityX = Math.PI / 200;

        /**
         * Turns the circle by this angle each iteration around the y-axis
         */
        private double angularVelocityY = Math.PI / 170;

        /**
         * Turns the circle by this angle each iteration around the z-axis
         */
        private double angularVelocityZ = Math.PI / 155;

        /**
         * Radius of circle above head
         */
        private float radius = 0.4F;

        /**
         * Used to make a partial circle
         */
        private double maxAngle = Math.PI * 2;

        /**
         * Start at the same origin each step, use this
         * along with maxAngle and wholeCircle to form persistent semicircles
         */
        private boolean resetCircle = false;

        /**
         * Subtracts from origin if needed
         */
        private double xSubtract, ySubtract, zSubtract;

        /**
         * Should it rotate?
         */
        private boolean enableRotation = true;

        /**
         * Amount of particles per circle
         */
        private int particles = 20;

        /**
         * To make a whole circle each iteration
         */
        private boolean wholeCircle = false;

        //Added by Emafire003
        /**
         * Growth in blocks per iteration on the radius
         * Setting to zero will disable it and fall back to the normal radius
         */
        private float radiusGrow = 0;
        private float yawOffset;
        private float pitchOffset;
        private float yaw;
        private float pitch;
        private boolean shouldUpdateYPR = true;

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
         * Sets the rotations and returns a reference to this Builder enabling method chaining.
         *
         * @param rotation A Vec3d representing the xyz rotations to set to the effect
         * @return a reference to this Builder
         */
        public Builder rotations(Vec3d rotation) {
            this.xRotation = (float) rotation.getX();
            this.yRotation = (float) rotation.getY();
            this.zRotation = (float) rotation.getZ();
            return this;
        }

        /**
         * Sets the {@code angularVelocityX} and returns a reference to this Builder enabling method chaining.
         *
         * @param angularVelocityX the {@code angularVelocityX} to set
         * @return a reference to this Builder
         */
        public Builder angularVelocityX(double angularVelocityX) {
            this.angularVelocityX = angularVelocityX;
            return this;
        }

        /**
         * Sets the {@code angularVelocityY} and returns a reference to this Builder enabling method chaining.
         *
         * @param angularVelocityY the {@code angularVelocityY} to set
         * @return a reference to this Builder
         */
        public Builder angularVelocityY(double angularVelocityY) {
            this.angularVelocityY = angularVelocityY;
            return this;
        }

        /**
         * Sets the {@code angularVelocityY} and returns a reference to this Builder enabling method chaining.
         *
         * @param angularVelocityZ the {@code angularVelocityY} to set
         * @return a reference to this Builder
         */
        public Builder angularVelocityZ(double angularVelocityZ) {
            this.angularVelocityZ = angularVelocityZ;
            return this;
        }

        /**
         * Sets the {@code radius} and returns a reference to this Builder enabling method chaining.
         *
         * @param radius the {@code radius} to set
         * @return a reference to this Builder
         */
        public Builder radius(float radius) {
            this.radius = radius;
            return this;
        }

        /**
         * Sets the {@code maxAngle} and returns a reference to this Builder enabling method chaining.
         *
         * @param maxAngle the {@code maxAngle} to set
         * @return a reference to this Builder
         */
        public Builder maxAngle(double maxAngle) {
            this.maxAngle = maxAngle;
            return this;
        }

        /**
         * Sets the {@code resetCircle} and returns a reference to this Builder enabling method chaining.
         *
         * @param resetCircle the {@code resetCircle} to set
         * @return a reference to this Builder
         */
        public Builder resetCircle(boolean resetCircle) {
            this.resetCircle = resetCircle;
            return this;
        }

        /**
         * Sets the {@code subtract} and returns a reference to this Builder enabling method chaining.
         *
         * @param subtract A Vec3d representing the xyz subtractions to set to the effect
         * @return a reference to this Builder
         */
        public Builder subtract(Vec3d subtract) {
            this.xSubtract = subtract.getX();
            this.ySubtract = subtract.getY();
            this.zSubtract = subtract.getZ();
            return this;
        }

        /**
         * Sets the {@code enableRotation} and returns a reference to this Builder enabling method chaining.
         *
         * @param enableRotation the {@code enableRotation} to set
         * @return a reference to this Builder
         */
        public Builder enableRotation(boolean enableRotation) {
            this.enableRotation = enableRotation;
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
         * Sets the {@code wholeCircle} and returns a reference to this Builder enabling method chaining.
         *
         * @param wholeCircle the {@code wholeCircle} to set
         * @return a reference to this Builder
         */
        public Builder wholeCircle(boolean wholeCircle) {
            this.wholeCircle = wholeCircle;
            return this;
        }

        /**
         * Sets the {@code radiusGrow} and returns a reference to this Builder enabling method chaining.
         *
         * @param radiusGrow the {@code radiusGrow} to set
         * @return a reference to this Builder
         */
        public Builder radiusGrow(float radiusGrow) {
            this.radiusGrow = radiusGrow;
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
         * Returns a {@code AnimatedCircleEffect} built from the parameters previously set.
         *
         * @return a {@code AnimatedCircleEffect} built with parameters of this {@code AnimatedCircleEffect.Builder}
         */
        public AnimatedCircleEffect build() {
            return new AnimatedCircleEffect(this);
        }
    }
}
