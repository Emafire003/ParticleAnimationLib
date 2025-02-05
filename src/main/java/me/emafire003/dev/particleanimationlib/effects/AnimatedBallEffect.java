package me.emafire003.dev.particleanimationlib.effects;

import me.emafire003.dev.particleanimationlib.Effect;
import me.emafire003.dev.particleanimationlib.EffectType;
import me.emafire003.dev.particleanimationlib.util.EffectModifier;
import me.emafire003.dev.particleanimationlib.util.VectorUtils;
import net.minecraft.entity.Entity;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.Vec3d;

/**
 * Creates an animated Sphere. Thanks to the author for sharing it!
 * <a href="https://www.youtube.com/watch?feature=player_embedded&v=RUjIw_RprRw">Demo on YouTube</a>
 *
 * @author <a href="http://forums.bukkit.org/members/qukie.90952701/">Qukie</a>
 */
@SuppressWarnings("unused")
public class AnimatedBallEffect extends Effect {
    /**
     * Ball particles total (150)
     */
    public int particles = 150;

    /**
     * The amount of particles, displayed in one iteration (10)
     */
    public int particlesPerIteration = 10;

    /**
     * Size of this ball (1)
     */
    public float size = 1F;

    /**
     * Factors (1, 1, 1)
     * Aka dimensions of the ball, like the "diameters"
     */
    public float xFactor = 1F, yFactor = 1F, zFactor = 1F;

    /**
     * Rotation of the ball.
     */
    public double xRotation, yRotation, zRotation = 0;

    
    
    /**
     * Internal Counter
     */
    protected int step = 0;

    /**
     * Creates a new animated "ball" effect.
     *
     * @param world The world the particles are going to spawn in
     * @param particle The particle effect that is going to be spawned. You can use {@link ParticleTypes}
     * @param origin The origin position of the effect
     * @param count The total number of particles that will be displayed
     * @param particles_per_iteration The number of particles displayed in each iteration
     * @param size The size of the ball effect
     * @param factors A {@link Vec3d} of xyz float factors for the ball effect. Non-uniform values will elongate the ball in one direction, for example (1,2,1) makes a vertical oval
     * @param rotation A {@link Vec3d} of xyz rotations (in radians) for the ball effect.
     * */
    public AnimatedBallEffect(ServerWorld world, ParticleEffect particle, Vec3d origin, int count, int particles_per_iteration, float size, Vec3d factors, Vec3d rotation) {
        super(world, EffectType.REPEATING, particle, origin);
        this.particle = particle;
        this.world = world;
        this.particles = count;
        this.size = size;
        this.particlesPerIteration = particles_per_iteration;
        this.xFactor = (float) factors.getX();
        this.yFactor = (float) factors.getY();
        this.zFactor = (float) factors.getZ();
        this.xRotation = rotation.getX();
        this.yRotation = rotation.getY();
        this.zRotation = rotation.getZ();
    }

    /**
     * Creates a new animated "ball" effect.
     *
     * @param world The world the particles are going to spawn in
     * @param particle The particle effect that is going to be spawned. You can use {@link ParticleTypes}
     * @param origin The origin position of the effect
      */
    public AnimatedBallEffect(ServerWorld world, ParticleEffect particle, Vec3d origin) {
        super(world, EffectType.REPEATING, particle, origin);
        this.particle = particle;
        this.world = world;
        this.originPos = origin;
    }

    /**
     * Creates a new animated "ball" effect.
     *
     * @param world The world the particles are going to spawn in
     * @param particle The particle effect that is going to be spawned. You can use {@link ParticleTypes}
     * @param origin The origin position of the effect
     * @param count The total number of particles that will be displayed
     * @param particles_per_iteration The number of particles displayed in each iteration
     * @param size The size of the ball effect
     */
    public AnimatedBallEffect(ServerWorld world, ParticleEffect particle, Vec3d origin, int count, int particles_per_iteration, float size) {
        super(world, EffectType.REPEATING, particle, origin);
        this.particle = particle;
        this.world = world;
        this.particles = count;
        this.size = size;
        this.particlesPerIteration = particles_per_iteration;
    }

    /**
     * Creates a new animated "ball" effect.
     *
     * @param world The world the particles are going to spawn in
     * @param particle The particle effect that is going to be spawned. You can use {@link ParticleTypes}
     * @param origin The origin position of the effect
     * @param count The total number of particles that will be displayed
     * @param particles_per_iteration The number of particles displayed in each iteration
     * @param size The size of the ball effect
     * @param factors A {@link Vec3d} of xyz float factors for the ball effect. Non-uniform values will elongate the ball in one direction, for example (1,2,1) makes a vertical oval
     */
    public AnimatedBallEffect(ServerWorld world, ParticleEffect particle, Vec3d origin, int count, int particles_per_iteration, float size, Vec3d factors) {
        super(world, EffectType.REPEATING, particle, origin);
        this.particle = particle;
        this.world = world;
        this.particles = count;
        this.size = size;
        this.particlesPerIteration = particles_per_iteration;
        this.xFactor = (float) factors.getX();
        this.yFactor = (float) factors.getY();
        this.zFactor = (float) factors.getZ();
    }


    private AnimatedBallEffect(Builder builder) {
        super(builder.world, EffectType.REPEATING, builder.particle, builder.originPos);
        setIterations(builder.iterations);
        setOriginPos(builder.originPos);
        setUpdatePositions(builder.updatePositions);
        setEntityOrigin(builder.entityOrigin);
        setOriginOffset(builder.originOffset);
        world = builder.world;
        particle = builder.particle;
        setParticles(builder.particles);
        setParticlesPerIteration(builder.particlesPerIteration);
        setSize(builder.size);
        setxFactor(builder.xFactor);
        setyFactor(builder.yFactor);
        setzFactor(builder.zFactor);
        setxRotation(builder.xRotation);
        setyRotation(builder.yRotation);
        setzRotation(builder.zRotation);
        setUseEyePosAsOrigin(builder.useEyePosAsOrigin);
        setExecuteOnStop(builder.executeOnStop);
        setSpawnParticlesEveryNIteration(builder.spawnParticlesEveryNIteration);
        setShouldLimitParticlesSpawnedPerIteration(builder.shouldLimitParticlesSpawnedPerIteration);
        setShouldSpawnParticlesEveryNIteration(builder.shouldSpawnParticlesEveryNIteration);
        setParticleLimit(builder.particleLimit);
        setShouldLimitParticlesEveryNIterations(builder.shouldLimitParticlesEveryNIterations);
        setLimitParticlesEveryNIterations(builder.limitParticlesEveryNIterations);
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

    public static void copy(AnimatedBallEffect original, AnimatedBallEffect copy) {
        Effect.copy(original, copy);
        copy.setParticles(original.getParticles());
        copy.setParticlesPerIteration(original.getParticlesPerIteration());
        copy.setSize(original.getSize());
        copy.setxFactor(original.getxFactor());
        copy.setyFactor(original.getyFactor());
        copy.setzFactor(original.getzFactor());
        copy.setxRotation(original.getxRotation());
        copy.setyRotation(original.getyRotation());
        copy.setzRotation(original.getzRotation());
    }


    @Override
    protected void onRun() {
        Vec3d pos = getOriginPos();

        if (pos == null) {
            return;
        }

        float t;
        float r;
        float s;

        for (int i = 0; i < particlesPerIteration; i++) {
            step++;

            t = (float) ((Math.PI / particles) * step);
            r = (float) (Math.sin(t) * size);
            //r = MathUtils.sin(t) * size;
            s = (float) (2 * Math.PI * t);
            //Need the offsets here because it's not using the originPos directly
            //TODO i think I should add the size in the x and z too
            double x = (xFactor * r * Math.cos(s) + this.originOffset.getX());
            double y = (yFactor * size * Math.cos(t) + this.originOffset.getY());
            double z = (zFactor * r * Math.sin(s) + this.originOffset.getZ());


            Vec3d vector = new Vec3d(x,y,z);
            vector = VectorUtils.rotateVector(vector, (float) xRotation, (float) yRotation, (float) zRotation);

            this.displayParticle(this.particle, originPos.add(vector));
        }
    }


    /*@Override
    @Deprecated
    protected boolean checkCut(Vec3d pos){
        //Applicare la box alla orgin pos centrata

        //Should be the position of the center
        Vec3d centerPos = new Vec3d(originPos.getX()+xFactor/2, originPos.getY()+yFactor/2, originPos.getZ()+zFactor/2);

        //ParticleAnimationLib.LOGGER.info("The cutAbove: " + this.cutAboveRightForward);
        //ParticleAnimationLib.LOGGER.info("The cutBelow: " + this.cutBelowLeftBackward);
        if(cutAboveRightForward.getX() != 0 && pos.getX() > centerPos.getX()+cutAboveRightForward.getX()){
            return true;
        }
        if(cutAboveRightForward.getY() != 0 && pos.getY() > centerPos.getY()+cutAboveRightForward.getY()){
            return true;
        }
        if(cutAboveRightForward.getZ() != 0 && pos.getZ() > centerPos.getZ()+cutAboveRightForward.getZ()){
            return true;
        }
        if(cutBelowLeftBackward.getX() != 0 && pos.getX() > centerPos.getX()-cutBelowLeftBackward.getX()){
            return true;
        }
        if(cutBelowLeftBackward.getY() != 0 && pos.getY() > centerPos.getY()-cutBelowLeftBackward.getY()){
            return true;
        }
        if(cutBelowLeftBackward.getZ() != 0 && pos.getZ() > centerPos.getZ()-cutBelowLeftBackward.getZ()){
            return true;
        }
        //ParticleAnimationLib.LOGGER.info("Center pos: "+ centerPos.getY() + " The curBoveWhaterver" + cutAboveRightForward.getY());
        //ParticleAnimationLib.LOGGER.info("The pos: "+ pos.getY() + " The cut+center" + (centerPos.getY()+cutAboveRightForward.getY()));
        return false;
        /*Box box = new Box(centerPos.getX()+cutAboveRightForward.getX(), centerPos.getY()+cutAboveRightForward.getY(), centerPos.getZ()+cutAboveRightForward.getZ(),
                centerPos.getX()-cutBelowLeftBackward.getX(), centerPos.getY()-cutBelowLeftBackward.getY(), centerPos.getZ()-cutBelowLeftBackward.getZ());

        return !box.contains(pos);* /
    }*/

    public int getParticles() {
        return particles;
    }

    public void setParticles(int particles) {
        this.particles = particles;
    }

    public int getParticlesPerIteration() {
        return particlesPerIteration;
    }

    public void setParticlesPerIteration(int particlesPerIteration) {
        this.particlesPerIteration = particlesPerIteration;
    }

    public float getSize() {
        return size;
    }

    public void setSize(float size) {
        this.size = size;
    }

    public float getxFactor() {
        return xFactor;
    }

    public void setxFactor(float xFactor) {
        this.xFactor = xFactor;
    }

    public float getyFactor() {
        return yFactor;
    }

    public void setyFactor(float yFactor) {
        this.yFactor = yFactor;
    }

    public float getzFactor() {
        return zFactor;
    }

    public void setzFactor(float zFactor) {
        this.zFactor = zFactor;
    }

    public double getxRotation() {
        return xRotation;
    }

    public void setxRotation(double xRotation) {
        this.xRotation = xRotation;
    }

    public double getyRotation() {
        return yRotation;
    }

    public void setyRotation(double yRotation) {
        this.yRotation = yRotation;
    }

    public double getzRotation() {
        return zRotation;
    }

    public void setzRotation(double zRotation) {
        this.zRotation = zRotation;
    }

    /**
     * {@code AnimatedBallEffect} builder static inner class.
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
        private boolean useEyePosAsOrigin = false;
        /**
         * Ball particles total (150)
         */
        private int particles = 150;

        /**
         * The amount of particles, displayed in one iteration (10)
         */
        private int particlesPerIteration = 10;

        /**
         * Size of this ball (1)
         */
        private float size = 1F;

        /**
         * Factors (1, 1, 1)
         * Aka dimensions of the ball, like the "diameters"
         */
        private float xFactor = 1F, yFactor = 1F, zFactor = 1F;

        /**
         * Rotation of the ball.
         */
        private double xRotation, yRotation, zRotation = 0;
        /** Set this to true to skip some iteration of particles spawning to save up on server and client resources*/
        private boolean shouldSpawnParticlesEveryNIteration = false;

        /** How many iterations to skip between a particle spawning and the other. By default, it's a quarter of a second
         * Only works if {@code shouldSpawnParticlesEveryNIteration} is enabled*/
        private int spawnParticlesEveryNIteration = 5;

        /** Set this to true to limit the max number of particles spawned each iteration, to save up on memory
         * By default it's on and is capped at 5000 particles per tick. Which is a lot.*/
        private boolean shouldLimitParticlesSpawnedPerIteration = true;

        /** The limit of particles spawned at a given time (like on one iteration)*/
        private int particleLimit = 5000;

        /** Limits the number of particles spawned every N iterations specified below*/
        private boolean shouldLimitParticlesEveryNIterations = false;

        /** Every N iterations specified here the number of maximum particles spawned in that time frame is {@code particleLimit} */
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
         * Sets the {@code particlesPerIteration} and returns a reference to this Builder enabling method chaining.
         *
         * @param particlesPerIteration the {@code particlesPerIteration} to set
         * @return a reference to this Builder
         */
        public Builder particlesPerIteration(int particlesPerIteration) {
            this.particlesPerIteration = particlesPerIteration;
            return this;
        }

        /**
         * Sets the {@code size} and returns a reference to this Builder enabling method chaining.
         *
         * @param size the {@code size} to set
         * @return a reference to this Builder
         */
        public Builder size(float size) {
            this.size = size;
            return this;
        }

        /**
         * Sets the {@code factor} and returns a reference to this Builder enabling method chaining.
         *
         * @param factor A Vec3d representing the xyz factors (aka xyz "strech" values) of the effect
         * @return a reference to this Builder
         */
        public Builder factor(Vec3d factor) {
            this.xFactor = (float) factor.getX();
            this.yFactor = (float) factor.getY();
            this.zFactor = (float) factor.getZ();
            return this;
        }

        /**
         * Sets the {@code rotation} and returns a reference to this Builder enabling method chaining.
         *
         * @param rotation A Vec3d representing the xyz rotations to set to the effect
         * @return a reference to this Builder
         */
        public Builder rotation(Vec3d rotation) {
            this.xRotation = rotation.getX();
            this.yRotation = rotation.getY();
            this.zRotation = rotation.getZ();
            return this;
        }

        /**
         * Returns a {@code AnimatedBallEffect} built from the parameters previously set.
         *
         * @return a {@code AnimatedBallEffect} built with parameters of this {@code AnimatedBallEffect.Builder}
         */
        public AnimatedBallEffect build() {
            return new AnimatedBallEffect(this);
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
    }
}
