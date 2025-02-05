package me.emafire003.dev.particleanimationlib.effects;


import me.emafire003.dev.particleanimationlib.EffectType;
import me.emafire003.dev.particleanimationlib.effects.base.YPREffect;
import me.emafire003.dev.particleanimationlib.util.EffectModifier;
import me.emafire003.dev.particleanimationlib.util.RandomUtils;
import me.emafire003.dev.particleanimationlib.util.VectorUtils;
import net.minecraft.entity.Entity;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.Vec3d;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("unused")
public class ConeEffect extends YPREffect {

    /**
     * Growing per iteration in the length (0.05)
     */
    public float lengthGrow = 0.05F;

    /**
     * Radials per iteration to spawn the next particle (PI / 16)
     */
    public double angularVelocity = Math.PI / 16;

    /**
     * Cone-particles per interation (10)
     */
    public int particles = 10;

    /**
     * Growth in blocks per iteration on the radius (0.006)
     */
    public float radiusGrow = 0.006F;

    /**
     * Conesize in particles per cone
     */
    public int particlesCone = 180;

    /**
     * Start-angle or rotation of the cone
     */
    public double rotation = 0;

    /**
     * Randomize every cone on creation (false)
     */
    public boolean randomize = false;

    /**
     * Solid cone
     */
    public boolean solid = false;

    /**
     * Amount of strands
     */
    public int strands = 1;

    //Added by Emafire003


    /** Flips the cone from the origin to the center of the predicted ending position
     * <p>
     * Experimental! May not work as intended*/
    public boolean flipped = false;

    /**Inverts the cone making it start from the last position and go all the way to the origin. Not to be cofused with flipped*/
    public boolean inverted = false;

    /** Do you want to draw the center axis as well?*/
    public boolean drawCenterAxis = false;

    /** Draws a point (one particle) where the effect ends*/
    public boolean drawFinishPoint = false;
    /** The particles to use for displaying the center axis/finish point
     * Falls back to the particle of this effect*/
    public ParticleEffect secondaryParticle = particle;


    /**
     * Current step. Works as counter
     */
    protected int step = 0;
    private boolean lineCreated = false;
    private List<Vec3d> positions = new ArrayList<>();
    private int counter;
    boolean inversionCalculated = false;

    /**
     * Creates a new cone effect
     *
     * @param world The world the particles are going to spawn in
     * @param particle The particle effect that is going to be spawned. You can use {@link ParticleTypes}
     * @param origin The origin position of the effect, aka the starting point of the cone
     * @param yaw The yaw of the effect. For example, you can get it from an Entity using getYaw()
     * @param pitch The pitch of the effect. For example, you can get it from an Entity using getPitch()
     * @param particleConeSize By how many particles should the cone be made of
     * @param particlesPerIteration How many particles to display per iteration
     * @param strands_number The number of the strands
     * @param lengthGrow Length growth amount per each iteration
     * @param radiusGrow Radius growth amount per each iteration
     * @param angularVel The angular velocity of the effect expressed in radials per iteration, to spawn the next particle
     * @param startRotation The starting rotation angle of the cone
     * @param solid Should the cone be solid?
     * @param random Makes the initial rotation of the cone random
     * */
    public ConeEffect(ServerWorld world, ParticleEffect particle, Vec3d origin, float yaw, float pitch, int particleConeSize, int particlesPerIteration, int strands_number, float lengthGrow, float radiusGrow, double angularVel, double startRotation, boolean solid, boolean random) {
        super(world, EffectType.REPEATING, particle, origin);
        this.yaw = yaw;
        this.pitch = pitch;
        this.particles = particlesPerIteration;
        this.particlesCone = particleConeSize;
        this.strands = strands_number;
        this.lengthGrow = lengthGrow;
        this.radiusGrow = radiusGrow;
        this.angularVelocity = angularVel;
        this.rotation = startRotation;
        this.solid = solid;
        this.randomize = random;
        this.setShouldUpdateYPR(true);
    }

    /**
     * Creates a new cone effect
     *
     * @param world The world the particles are going to spawn in
     * @param particle The particle effect that is going to be spawned. You can use {@link ParticleTypes}
     * @param origin The origin position of the effect, aka the starting point of the cone
     * @param yaw The yaw of the effect. For example, you can get it from an Entity using getYaw()
     * @param pitch The pitch of the effect. For example, you can get it from an Entity using getPitch()
     * */
    public ConeEffect(ServerWorld world, ParticleEffect particle, Vec3d origin, float yaw, float pitch) {
        super(world, EffectType.REPEATING, particle, origin);
        this.yaw = yaw;
        this.pitch = pitch;
        this.setShouldUpdateYPR(true);
    }

    private ConeEffect(Builder builder) {
        super(builder.world, EffectType.REPEATING, builder.particle, builder.originPos);
        setIterations(builder.iterations);
        setOriginPos(builder.originPos);
        setUpdatePositions(builder.updatePositions);
        setEntityOrigin(builder.entityOrigin);
        setOriginOffset(builder.originOffset);
        world = builder.world;
        particle = builder.particle;
        setLengthGrow(builder.lengthGrow);
        setAngularVelocity(builder.angularVelocity);
        setParticles(builder.particles);
        setRadiusGrow(builder.radiusGrow);
        setParticlesCone(builder.particlesCone);
        setRotation(builder.rotation);
        setRandomize(builder.randomize);
        setSolid(builder.solid);
        setStrands(builder.strands);
        setFlipped(builder.flipped);
        setDrawCenterAxis(builder.drawCenterAxis);
        setDrawFinishPoint(builder.drawFinishPoint);
        setSecondaryParticle(builder.secondaryParticle);
        setYawOffset(builder.yawOffset);
        setPitchOffset(builder.pitchOffset);
        setYaw(builder.yaw);
        setPitch(builder.pitch);
        setShouldUpdateYPR(builder.shouldUpdateYPR);
        setInverted(builder.inverted);
        setUseEyePosAsOrigin(builder.useEyePosAsOrigin);
        setExecuteOnStop(builder.executeOnStop);
        setShouldSpawnParticlesEveryNIteration(builder.shouldSpawnParticlesEveryNIteration);
        setSpawnParticlesEveryNIteration(builder.spawnParticlesEveryNIteration);
        setShouldLimitParticlesSpawnedPerIteration(builder.shouldLimitParticlesSpawnedPerIteration);
        setParticleLimit(builder.particleLimit);
        setShouldLimitParticlesEveryNIterations(builder.shouldLimitParticlesEveryNIterations);
        setLimitParticlesEveryNIterations(builder.limitParticlesEveryNIterations);
    }

    public static void copy(ConeEffect original, ConeEffect copy) {
        YPREffect.copy(original, copy);
        copy.setLengthGrow(original.getLengthGrow());
        copy.setAngularVelocity(original.getAngularVelocity());
        copy.setParticles(original.getParticles());
        copy.setRadiusGrow(original.getRadiusGrow());
        copy.setParticlesCone(original.getParticlesCone());
        copy.setRotation(original.getRotation());
        copy.setRandomize(original.isRandomize());
        copy.setSolid(original.isSolid());
        copy.setStrands(original.getStrands());
        copy.setFlipped(original.isFlipped());
        copy.setInverted(original.isInverted());
        copy.setDrawCenterAxis(original.isDrawCenterAxis());
        copy.setDrawFinishPoint(original.isDrawFinishPoint());
        copy.setSecondaryParticle(original.getSecondaryParticle());
        copy.step = original.step;
        copy.lineCreated = original.lineCreated;
        copy.positions = original.positions;
        copy.counter = original.counter;
        copy.inverted = original.inverted;
    }

    /**
     * Creates a new cone effect
     *
     * @param world The world the particles are going to spawn in
     * @param particle The particle effect that is going to be spawned. You can use {@link ParticleTypes}
     * @param origin The origin position of the effect, aka the starting point of the cone
     * */
    public ConeEffect(ServerWorld world, ParticleEffect particle, Vec3d origin) {
        super(world, EffectType.REPEATING, particle, origin);
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

    /** Returns the position of the center of the cone at it maximum point
     *<p>
     * Experimental! May not work as intended if run for more or less than 10 seconds!*/
    //TODO figure out what's wrong :/
    public Vec3d getPredictedMaxCenterPosition(){
        float total_length = this.getIterations() * lengthGrow;

        Vec3d v = new Vec3d(0, total_length, 0);

        v = VectorUtils.rotateVector(v, this.getYaw(), this.getPitch()+90);

        return originPos.add(v);
    }

    private void calculateAllPositions(){
        Vec3d originPos = this.getOriginPos();

        if (originPos == null) {
            return;
        }

        double angle;
        float radius;
        float length;

        Vec3d v;

        for (int x = 0; x < particles; x++) {

            if (step > particlesCone) step = 0;
            if (randomize && step == 0) rotation = RandomUtils.getRandomAngle();
            for (int y = 0; y < strands; y++) {
                angle = step * angularVelocity + rotation + (2 * Math.PI * y / strands);
                radius = step * radiusGrow;

                if (solid) {
                    //what the heck is this?
                    radius *= RandomUtils.random.nextFloat();
                }

                length = step * lengthGrow;

                v = new Vec3d(Math.cos(angle) * radius, length, Math.sin(angle) * radius);

                v = VectorUtils.rotateVector(v, this.getYaw(), this.getPitch()+90);

                this.positions.add(originPos.add(v));

            }
            step++;
        }

        step = 0;
        counter = this.positions.size();

    }

    @Override
    protected void onRun() {
        Vec3d originPos = this.getOriginPos();

        if (originPos == null) {
            return;
        }

        if(inverted && !inversionCalculated){
            calculateAllPositions();
            inversionCalculated = true;
        }

        double angle;
        float radius;
        float length;

        Vec3d v;

        for (int x = 0; x < particles; x++) {

            if (step > particlesCone) step = 0;
            if (randomize && step == 0) rotation = RandomUtils.getRandomAngle();
            for (int y = 0; y < strands; y++) {

                if(inverted){
                    counter--;
                    if(counter < 0){
                        return;
                    }
                    if(flipped){
                        this.displayParticle(particle, getPredictedMaxCenterPosition().add(positions.get(counter).multiply(-1)));
                    }else{
                        this.displayParticle(particle, originPos.add(positions.get(counter)));
                    }

                }else {
                    angle = step * angularVelocity + rotation + (2 * Math.PI * y / strands);
                    radius = step * radiusGrow;

                    if (solid) {
                        //what the heck is this?
                        radius *= RandomUtils.random.nextFloat();
                    }

                    length = step * lengthGrow;

                    v = new Vec3d(Math.cos(angle) * radius, length, Math.sin(angle) * radius);

                    v = VectorUtils.rotateVector(v, this.getYaw(), this.getPitch()+90);

                    if(flipped){
                        v = v.multiply(-1);
                        this.displayParticle(particle, getPredictedMaxCenterPosition().add(v));
                    }else{
                        this.displayParticle(particle, originPos.add(v));
                    }
                }

            }
            if(drawCenterAxis && !lineCreated){
                LineEffect line = new LineEffect(this.world, this.secondaryParticle, this.getOriginPos(), this.getPredictedMaxCenterPosition(), this.particles);
                line.setIterations(this.iterations);
                line.run();
                lineCreated = true;
            }
            if(drawFinishPoint){
                if(flipped){
                    this.displayParticle(secondaryParticle, originPos);
                }else{
                    this.displayParticle(secondaryParticle, getPredictedMaxCenterPosition());
                }
            }
            step++;
        }
    }

    public float getLengthGrow() {
        return lengthGrow;
    }

    public void setLengthGrow(float lengthGrow) {
        this.lengthGrow = lengthGrow;
    }

    public double getAngularVelocity() {
        return angularVelocity;
    }

    public void setAngularVelocity(double angularVelocity) {
        this.angularVelocity = angularVelocity;
    }

    public int getParticles() {
        return particles;
    }

    public void setParticles(int particles) {
        this.particles = particles;
    }

    public float getRadiusGrow() {
        return radiusGrow;
    }

    public void setRadiusGrow(float radiusGrow) {
        this.radiusGrow = radiusGrow;
    }

    public int getParticlesCone() {
        return particlesCone;
    }

    public void setParticlesCone(int particlesCone) {
        this.particlesCone = particlesCone;
    }

    public double getRotation() {
        return rotation;
    }

    public void setRotation(double rotation) {
        this.rotation = rotation;
    }

    public boolean isRandomize() {
        return randomize;
    }

    public void setRandomize(boolean randomize) {
        this.randomize = randomize;
    }

    public boolean isSolid() {
        return solid;
    }

    public void setSolid(boolean solid) {
        this.solid = solid;
    }

    public int getStrands() {
        return strands;
    }

    public void setStrands(int strands) {
        this.strands = strands;
    }

    public boolean isFlipped() {
        return flipped;
    }

    public void setFlipped(boolean flipped) {
        this.flipped = flipped;
    }

    public boolean isInverted() {
        return inverted;
    }

    public void setInverted(boolean inverted) {
        this.inverted = inverted;
    }

    public boolean isDrawCenterAxis() {
        return drawCenterAxis;
    }

    public void setDrawCenterAxis(boolean drawCenterAxis) {
        this.drawCenterAxis = drawCenterAxis;
    }

    public ParticleEffect getSecondaryParticle() {
        return secondaryParticle;
    }

    public void setSecondaryParticle(ParticleEffect secondaryParticle) {
        this.secondaryParticle = secondaryParticle;
    }

    public boolean isDrawFinishPoint() {
        return drawFinishPoint;
    }

    public void setDrawFinishPoint(boolean drawFinishPoint) {
        this.drawFinishPoint = drawFinishPoint;
    }

    /**
     * {@code ConeEffect} builder static inner class.
     */
    public static final class Builder {
        private int iterations;
        private Vec3d originPos;
        private boolean updatePositions;
        private boolean useEyePosAsOrigin;
        private Entity entityOrigin;
        private Vec3d originOffset;
        private ServerWorld world;
        private ParticleEffect particle = ParticleTypes.DRAGON_BREATH;
        private EffectModifier executeOnStop;
        /**
         * Growing per iteration in the length (0.05)
         */
        private float lengthGrow = 0.05F;

        /**
         * Radials per iteration to spawn the next particle (PI / 16)
         */
        private double angularVelocity = Math.PI / 16;

        /**
         * Cone-particles per interation (10)
         */
        private int particles = 10;

        /**
         * Growth in blocks per iteration on the radius (0.006)
         */
        private float radiusGrow = 0.006F;

        /**
         * Conesize in particles per cone
         */
        private int particlesCone = 180;

        /**
         * Start-angle or rotation of the cone
         */
        private double rotation = 0;

        /**
         * Randomize every cone on creation (false)
         */
        private boolean randomize = false;

        /**
         * Solid cone
         */
        private boolean solid = false;

        /**
         * Amount of strands
         */
        private int strands = 1;

        //Added by Emafire003

        private boolean flipped = false;
        private boolean inverted = false;

        /**
         * Do you want to draw the center axis as well?
         */
        private boolean drawCenterAxis = false;

        /**
         * Draws a point (one particle) where the effect ends
         */
        private boolean drawFinishPoint = false;
        /**
         * The particles to use for displaying the center axis/finish point
         * Falls back to the particle of this effect
         */
        private ParticleEffect secondaryParticle = particle;
        private float yawOffset;
        private float pitchOffset;
        private float yaw;
        private float pitch;
        private boolean shouldUpdateYPR = true;
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
         * Sets the {@code lengthGrow} and returns a reference to this Builder enabling method chaining.
         *
         * @param lengthGrow the {@code lengthGrow} to set
         * @return a reference to this Builder
         */
        public Builder lengthGrow(float lengthGrow) {
            this.lengthGrow = lengthGrow;
            return this;
        }

        /**
         * Sets the {@code angularVelocity} and returns a reference to this Builder enabling method chaining.
         *
         * @param angularVelocity the {@code angularVelocity} to set
         * @return a reference to this Builder
         */
        public Builder angularVelocity(double angularVelocity) {
            this.angularVelocity = angularVelocity;
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
         * Sets the {@code particlesCone} and returns a reference to this Builder enabling method chaining.
         *
         * @param particlesCone the {@code particlesCone} to set
         * @return a reference to this Builder
         */
        public Builder particlesCone(int particlesCone) {
            this.particlesCone = particlesCone;
            return this;
        }

        /**
         * Sets the {@code rotation} and returns a reference to this Builder enabling method chaining.
         *
         * @param rotation the {@code rotation} to set
         * @return a reference to this Builder
         */
        public Builder rotation(double rotation) {
            this.rotation = rotation;
            return this;
        }

        /**
         * Sets the {@code randomize} and returns a reference to this Builder enabling method chaining.
         *
         * @param randomize the {@code randomize} to set
         * @return a reference to this Builder
         */
        public Builder randomize(boolean randomize) {
            this.randomize = randomize;
            return this;
        }

        /**
         * Sets the {@code solid} and returns a reference to this Builder enabling method chaining.
         *
         * @param solid the {@code solid} to set
         * @return a reference to this Builder
         */
        public Builder solid(boolean solid) {
            this.solid = solid;
            return this;
        }

        /**
         * Sets the {@code strands} and returns a reference to this Builder enabling method chaining.
         *
         * @param strands the {@code strands} to set
         * @return a reference to this Builder
         */
        public Builder strands(int strands) {
            this.strands = strands;
            return this;
        }

        /**
         * Sets the {@code flipped} and returns a reference to this Builder enabling method chaining.
         *
         * @param flipped the {@code inverted} to set
         * @return a reference to this Builder
         */
        public Builder flipped(boolean flipped) {
            this.flipped = flipped;
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
         * Sets the {@code inverted} and returns a reference to this Builder enabling method chaining.
         *
         * @param inverted the {@code inverted} to set
         * @return a reference to this Builder
         */
        public Builder inverted(boolean inverted) {
            this.inverted = inverted;
            return this;
        }

        /**
         * Sets the {@code drawCenterAxis} and returns a reference to this Builder enabling method chaining.
         *
         * @param drawCenterAxis the {@code drawCenterAxis} to set
         * @return a reference to this Builder
         */
        public Builder drawCenterAxis(boolean drawCenterAxis) {
            this.drawCenterAxis = drawCenterAxis;
            return this;
        }

        /**
         * Sets the {@code drawFinishPoint} and returns a reference to this Builder enabling method chaining.
         *
         * @param drawFinishPoint the {@code drawFinishPoint} to set
         * @return a reference to this Builder
         */
        public Builder drawFinishPoint(boolean drawFinishPoint) {
            this.drawFinishPoint = drawFinishPoint;
            return this;
        }

        /**
         * Sets the {@code secondaryParticle} and returns a reference to this Builder enabling method chaining.
         *
         * @param secondaryParticle the {@code secondaryParticle} to set
         * @return a reference to this Builder
         */
        public Builder secondaryParticle(ParticleEffect secondaryParticle) {
            this.secondaryParticle = secondaryParticle;
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
         * Returns a {@code ConeEffect} built from the parameters previously set.
         *
         * @return a {@code ConeEffect} built with parameters of this {@code ConeEffect.Builder}
         */
        public ConeEffect build() {
            return new ConeEffect(this);
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
    }
}
