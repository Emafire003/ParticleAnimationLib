package me.emafire003.dev.particleanimationlib.effects;


import me.emafire003.dev.particleanimationlib.EffectType;
import me.emafire003.dev.particleanimationlib.effects.base.YPREffect;
import me.emafire003.dev.particleanimationlib.util.VectorUtils;
import net.minecraft.entity.Entity;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.Vec3d;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("unused")
public class VortexEffect extends YPREffect {

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

    /**Flips the staring and ending position of the vortex*/
    public boolean flipped;

    /** Inverts the direction of the effect, making the particles appear from the max radius
     * and end at the origin
     */
    public boolean inverted = false;


    // Stuff used for calculations

    /**
     * Current step. Works as counter
     */
    protected int step = 0;
    private boolean inversionCalculated = false;
    private List<Vec3d> positions = new ArrayList<>();
    private int counter;


    /** Creates a new Vortex effect
     *
     * @param world The world the particles are going to spawn in
     * @param particle The particle effect that is going to be spawned. You can use {@link ParticleTypes}
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
     * @param particle The particle effect that is going to be spawned. You can use {@link ParticleTypes}
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
     * @param particle The particle effect that is going to be spawned. You can use {@link ParticleTypes}
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

    private VortexEffect(Builder builder) {
        super(builder.world, EffectType.REPEATING, builder.particle, builder.originPos);
        setIterations(builder.iterations);
        setOriginPos(builder.originPos);
        setUpdatePositions(builder.updatePositions);
        setEntityOrigin(builder.entityOrigin);
        setOriginOffset(builder.originOffset);
        world = builder.world;
        particle = builder.particle;
        setRadius(builder.radius);
        setRadiusGrow(builder.radiusGrow);
        setStartRange(builder.startRange);
        setLengthGrow(builder.lengthGrow);
        setRadials(builder.radials);
        setCircles(builder.circles);
        setHelixes(builder.helixes);
        setFlipped(builder.flipped);
        setYawOffset(builder.yawOffset);
        setPitchOffset(builder.pitchOffset);
        setYaw(builder.yaw);
        setPitch(builder.pitch);
        setShouldUpdateYPR(builder.shouldUpdateYPR);
        setInverted(builder.inverted);
        setUseEyePosAsOrigin(builder.useEyePosAsOrigin);
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

    /**  Returns the predicted finish center position of the vortex, can be used to invert the vortex
     * */
    public Vec3d getPredictedMaxCenterPosition(){
        float total_length = this.getIterations() * lengthGrow * circles;

        Vec3d v = new Vec3d(0, total_length, 0);

        v = VectorUtils.rotateVector(v, this.getYaw(), this.getPitch()+90);

        return originPos.add(v);
    }

    private void calculateAllPositions(){
        Vec3d origin = this.getOriginPos();
        double angle;
        Vec3d v;

        if(origin == null){
            return;
        }

        for(int j = 0; j < this.iterations; j++){
            for (int x = 0; x < circles; x++) {
                for (int i = 0; i < helixes; i++) {
                    angle = step * radials + (2 * Math.PI * i / helixes);
                    v = new Vec3d(Math.cos(angle) * (radius + step * radiusGrow), startRange + step * lengthGrow, Math.sin(angle) * (radius + step * radiusGrow));
                    //The +90 flips the angle to be on the looking plane let's call it
                    v = VectorUtils.rotateVector(v, this.getYaw(), this.getPitch()+90);

                    positions.add(v);

                }
                step++;
            }
        }
        this.counter = positions.size();//-1;
        step = 0;
    }


    @Override
    protected void onRun() {
        Vec3d origin = this.getOriginPos();
        double angle;
        Vec3d v;

        if(origin == null){
            return;
        }

        if(inverted && !inversionCalculated){
            calculateAllPositions();
            inversionCalculated = true;
        }

        for (int x = 0; x < circles; x++) {
            for (int i = 0; i < helixes; i++) {

                if(inverted){
                    counter--;
                    if(counter < 0){
                        return;
                    }
                    if(flipped){
                        this.displayParticle(particle, getPredictedMaxCenterPosition().add(positions.get(counter).multiply(-1)));
                    }else{
                        this.displayParticle(particle, origin.add(positions.get(counter)));
                    }

                }else{
                    angle = step * radials + (2 * Math.PI * i / helixes);
                    v = new Vec3d(Math.cos(angle) * (radius + step * radiusGrow), startRange + step * lengthGrow, Math.sin(angle) * (radius + step * radiusGrow));
                    //The +90 flips the angle to be on the looking plane let's call it
                    v = VectorUtils.rotateVector(v, this.getYaw(), this.getPitch()+90);

                    if(flipped){
                        v = v.multiply(-1);
                        this.displayParticle(particle, getPredictedMaxCenterPosition().add(v));
                    }else{
                        this.displayParticle(particle, origin.add(v));
                    }
                }


            }
            step++;
        }
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

    public static void copy(VortexEffect original, VortexEffect copy) {
        YPREffect.copy(original, copy);
        copy.setRadius(original.getRadius());
        copy.setRadiusGrow(original.getRadiusGrow());
        copy.setStartRange(original.getStartRange());
        copy.setLengthGrow(original.getLengthGrow());
        copy.setRadials(original.getRadials());
        copy.setCircles(original.getCircles());
        copy.setHelixes(original.getHelixes());
        copy.setFlipped(original.isFlipped());
        copy.setInverted(original.isInverted());
        copy.step = original.step;
        copy.inversionCalculated = original.inversionCalculated;
        copy.positions = original.positions;
        copy.counter = original.counter;
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

    /**
     * {@code VortexEffect} builder static inner class.
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
         * Radius of vortex (2)
         */
        private float radius = 2;

        /**
         * Radius grow per iteration (0.00)
         */
        private float radiusGrow = 0.00F;

        /**
         * Initial range of the vortex (0.0)
         */
        private float startRange = 0.0F;

        /**
         * Growing per iteration (0.05)
         */
        private float lengthGrow = 0.05F;

        /**
         * Radials per iteration (PI / 16)
         */
        private double radials = Math.PI / 16;

        /**
         * Helix-circles per iteration (3)
         * (Also impacts on the length of the effect)
         */
        private int circles = 3;

        /**
         * Amount of helices (4)
         * Yay for the typo
         */
        private int helixes = 4;

        //Added by Emafire003

        /**Flips the staring and ending position of the vortex*/
        private boolean flipped = false;
        /** Inverts the direction of the effect, making the particles appear from the max radius
         * and end at the origin
         */
        public boolean inverted = false;
        private float yawOffset;
        private float pitchOffset;
        private float yaw;
        private float pitch;
        private boolean shouldUpdateYPR = true;
        private boolean useEyePosAsOrigin = false;

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
        public Builder radius(float radius) {
            this.radius = radius;
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
         * Sets the {@code startRange} and returns a reference to this Builder enabling method chaining.
         *
         * @param startRange the {@code startRange} to set
         * @return a reference to this Builder
         */
        public Builder startRange(float startRange) {
            this.startRange = startRange;
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
         * Sets the {@code radials} and returns a reference to this Builder enabling method chaining.
         *
         * @param radials the {@code radials} to set
         * @return a reference to this Builder
         */
        public Builder radials(double radials) {
            this.radials = radials;
            return this;
        }

        /**
         * Sets the {@code circles} and returns a reference to this Builder enabling method chaining.
         *
         * @param circles the {@code circles} to set
         * @return a reference to this Builder
         */
        public Builder circles(int circles) {
            this.circles = circles;
            return this;
        }

        /**
         * Sets the {@code helixes} and returns a reference to this Builder enabling method chaining.
         *
         * @param helixes the {@code helixes} to set
         * @return a reference to this Builder
         */
        public Builder helixes(int helixes) {
            this.helixes = helixes;
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
         * Returns a {@code VortexEffect} built from the parameters previously set.
         *
         * @return a {@code VortexEffect} built with parameters of this {@code VortexEffect.Builder}
         */
        public VortexEffect build() {
            return new VortexEffect(this);
        }
    }
}
