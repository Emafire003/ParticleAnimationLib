package me.emafire003.dev.particleanimationlib.effects;

import me.emafire003.dev.particleanimationlib.EffectType;
import me.emafire003.dev.particleanimationlib.ParticleAnimationLib;
import me.emafire003.dev.particleanimationlib.effects.base.TargetedEffect;
import me.emafire003.dev.particleanimationlib.util.EffectModifier;
import net.minecraft.entity.Entity;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import org.jetbrains.annotations.NotNull;

@SuppressWarnings("unused")
public class CuboidEffect extends TargetedEffect {


    /**
     * Particles in each row
     */
    public int particles;

    /**
     * Length of x component of cuboid
     */
    public double xLength = 0;

    /**
     * Length of y component of cuboid
     */
    public double yLength = 0;

    /**
     * Length of z component of cuboid
     */
    public double zLength = 0;

    /**
     * Amount of padding to add around the cube
     */
    public double padding = 0;

    /**
     * Use corners of blocks
     */
    public boolean blockSnap = false;

    //=================== Calculation section aka variables used to calculate the effect not configure it! ==============

    /**
     * Calculated length
     */
    private double useXLength = 0;
    private double useYLength = 0;
    private double useZLength = 0;

    /**
     * State variables
     */
    protected Vec3d minCorner;


    /**
    * Creates a new cuboid effect.
     * Don't use this constructor, since xyz lengths and origin-target exclude each other
     *
     * @param world The world the particles are going to spawn in
     * @param particle The particle effect that is going to be spawned. You can use {@link ParticleTypes}
     * @param origin The origin position of the effect, aka the first corner of the cuboid
     * @param target The target position of the effect, aka the opposite corner of the cuboid
     * @param particles_per_row How many particles should each side/row have
     * @param x_length The length of the x component of the cuboid. The minimum is 1 block
     * @param y_length The length of the y component of the cuboid. The minimum is 1 block
     * @param z_length The length of the z component of the cuboid. The minimum is 1 block
     * @param padding The padding to add to the sides of the cuboid. A padding of 0.5 will result in having the effect closer to the border of a block, a padding of 0 will place the effect in the middle of a block
     * @param blockSnap Weather or not the corners should snap to blocks to be more precise.
     * */
    @Deprecated
    public CuboidEffect(@NotNull ServerWorld world, @NotNull ParticleEffect particle, @NotNull Vec3d origin, @NotNull Vec3d target, int particles_per_row, double x_length, double y_length, double z_length, double padding, boolean blockSnap) {
        super(world, EffectType.REPEATING, particle, origin);
        this.targetPos = target;
        this.particles = particles_per_row;
        this.xLength = x_length;
        this.yLength = y_length;
        this.zLength = z_length;
        this.padding = padding;
        this.blockSnap = blockSnap;
    }

    /**
     * Creates a new cuboid effect.
     *
     * @param world The world the particles are going to spawn in
     * @param particle The particle effect that is going to be spawned. You can use {@link ParticleTypes}
     * @param origin The origin position of the effect, aka the first corner of the cuboid
     * @param target The target position of the effect, aka the opposite corner of the cuboid
     * @param particles_per_row How many particles should each side/row have
     * @param padding The padding to add to the sides of the cuboid. A padding of 0.5 will result in having the effect closer to the border of a block, a padding of 0 will place the effect in the middle of a block
     * @param blockSnap Weather or not the corners should snap to blocks to be more precise.
     * */
    public CuboidEffect(@NotNull ServerWorld world, @NotNull ParticleEffect particle, @NotNull Vec3d origin, @NotNull Vec3d target, int particles_per_row, double padding, boolean blockSnap) {
        super(world, EffectType.REPEATING, particle, origin);
        this.targetPos = target;
        this.particles = particles_per_row;
        this.padding = padding;
        this.blockSnap = blockSnap;
    }

    /**
     * Creates a new cuboid effect.
     *
     * @param world The world the particles are going to spawn in
     * @param particle The particle effect that is going to be spawned. You can use {@link ParticleTypes}
     * @param origin The origin position of the effect, aka the first corner of the cuboid
     * @param particles_per_row How many particles should each side/row have
     * @param x_length The length of the x component of the cuboid. The minimum is 1 block.
     * @param y_length The length of the y component of the cuboid. The minimum is 1 block
     * @param z_length The length of the z component of the cuboid. The minimum is 1 block
     * @param padding The padding to add to the sides of the cuboid. A padding of 0.5 will result in having the effect closer to the border of a block, a padding of 0 will place the effect in the middle of a block
     * @param blockSnap Weather or not the corners should snap to blocks to be more precise.
     * */
    public CuboidEffect(@NotNull ServerWorld world, @NotNull ParticleEffect particle, @NotNull Vec3d origin, int particles_per_row, double x_length, double y_length, double z_length, double padding, boolean blockSnap) {
        super(world, EffectType.REPEATING, particle, origin);
        this.particles = particles_per_row;
        this.xLength = x_length;
        this.yLength = y_length;
        this.zLength = z_length;
        this.padding = padding;
        this.blockSnap = blockSnap;
    }

    /**
     * Creates a new cuboid effect.
     *
     * @param world The world the particles are going to spawn in
     * @param particle The particle effect that is going to be spawned. You can use {@link ParticleTypes}
     * @param origin The origin position of the effect, aka the first corner of the cuboid
     * @param particles_per_row How many particles should each side/row have
     * @param x_length The length of the x component of the cuboid. The minimum is 1 block
     * @param y_length The length of the y component of the cuboid. The minimum is 1 block
     * @param z_length The length of the z component of the cuboid. The minimum is 1 block
     * */
    public CuboidEffect(@NotNull ServerWorld world, @NotNull ParticleEffect particle, @NotNull Vec3d origin, int particles_per_row, double x_length, double y_length, double z_length) {
        super(world, EffectType.REPEATING, particle, origin);
        this.particles = particles_per_row;
        this.xLength = x_length;
        this.yLength = y_length;
        this.zLength = z_length;
    }

    /**
     * Creates a new cuboid effect.
     *
     * @param world The world the particles are going to spawn in
     * @param particle The particle effect that is going to be spawned. You can use {@link ParticleTypes}
     * @param origin The origin position of the effect, aka the first corner of the cuboid
     * @param target The target position of the effect, aka the opposite corner of the cuboid
     * @param particles_per_row How many particles should each side/row have
     * */
    public CuboidEffect(@NotNull ServerWorld world, @NotNull ParticleEffect particle, @NotNull Vec3d origin, @NotNull Vec3d target, int particles_per_row) {
        super(world, EffectType.REPEATING, particle, origin);
        this.targetPos = target;
        this.particles = particles_per_row;
    }

    private CuboidEffect(Builder builder) {
        super(builder.world, EffectType.REPEATING, builder.particle, builder.originPos);
        setIterations(builder.iterations);
        setOriginPos(builder.originPos);
        setUpdatePositions(builder.updatePositions);
        setEntityOrigin(builder.entityOrigin);
        setOriginOffset(builder.originOffset);
        world = builder.world;
        particle = builder.particle;
        particles = builder.particles;
        xLength = builder.xLength;
        yLength = builder.yLength;
        zLength = builder.zLength;
        setPadding(builder.padding);
        setBlockSnap(builder.blockSnap);
        setTargetPos(builder.targetPos);
        setUpdateTargetPositions(builder.updateTargetPositions);
        setEntityTarget(builder.entityTarget);
        setTargetOffset(builder.targetOffset);
        setUseEyePosAsOrigin(builder.useEyePosAsOrigin);
        setUseEyePosAsTarget(builder.useEyePosAsTarget);
        setExecuteOnStop(builder.executeOnStop);
    }

    public static void copy(CuboidEffect original, CuboidEffect copy) {
        TargetedEffect.copy(original, copy);
        copy.setPadding(original.getPadding());
        copy.setBlockSnap(original.isBlockSnap());
        copy.setXLength(original.getXLength());
        copy.setYLength(original.getYLength());
        copy.setZLength(original.getZLength());
        copy.setParticles(original.getParticles());
        copy.useXLength = original.useXLength;
        copy.useYLength = original.useYLength;
        copy.useZLength = original.useZLength;
        copy.minCorner = original.minCorner;
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


    //TODO add a fill-in option
    @Override
    protected void onRun() {
        if(this.world == null || this.world.isClient()){
            return;
        }

        Vec3d target = this.getTargetPos();
        Vec3d origin = this.getOriginPos();
        if (origin == null) {
            return;
        }
        if(target == null && xLength == 0 && yLength == 0 && zLength == 0){
            return;
        }


        if (blockSnap) {
            if(target != null){
                target = Vec3d.ofCenter(new BlockPos(MathHelper.floor(target.x), MathHelper.floor(target.y), MathHelper.floor(target.z)));
            }
            minCorner = Vec3d.ofCenter(new BlockPos(MathHelper.floor(origin.x), MathHelper.floor(origin.y), MathHelper.floor(origin.z)));
        } else {
            minCorner = origin;
        }

        if (xLength == 0 && yLength == 0 && zLength == 0) {

            double x = minCorner.getX();
            double y = minCorner.getY();
            double z = minCorner.getZ();
            try{
                if (target.getX() < x) x=target.getX();
                if (target.getY() < y) y=target.getY();
                if (target.getZ() < z) z=target.getZ();
            }catch (NullPointerException e){
                ParticleAnimationLib.LOGGER.error("Error! The target position is null and the lengths are zero! Specify at least a target position or a length value!");
                e.printStackTrace();
            }

            minCorner = new Vec3d(x,y,z);

            useXLength = Math.abs(origin.getX() - target.getX());
            useYLength = Math.abs(origin.getY() - target.getY());
            useZLength = Math.abs(origin.getZ() - target.getZ());
        } else {
            useXLength = xLength;
            useYLength = yLength;
            useZLength = zLength;
        }

        double extra = padding * 2;
        if (blockSnap) extra++;

        useXLength += extra;
        useYLength += extra;
        useZLength += extra;

        if (padding != 0) minCorner = minCorner.add(-padding, -padding, -padding);

        drawOutline();
    }

    private void drawOutline() {
        for (int i = 0; i < particles; i++) {
            // X edges
            drawEdge(i, 0, 2, 2);
            drawEdge(i, 0, 1, 2);
            drawEdge(i, 0, 1, 1);
            drawEdge(i, 0, 2, 1);

            // Y edges
            drawEdge(i, 2, 0, 2);
            drawEdge(i, 1,0, 2);
            drawEdge(i, 1,0, 1);
            drawEdge(i, 2,0, 1);

            // Z edges
            drawEdge(i, 2, 2, 0);
            drawEdge(i, 1, 2, 0);
            drawEdge(i, 1, 1, 0);
            drawEdge(i, 2, 1, 0);
        }
    }

    private void drawEdge( int i, int dx, int dy, int dz) {
        double x;
        double y;
        double z;
        if (dx == 0){
            x=useXLength * i / particles;
        }
        else{
            x=(useXLength * (dx - 1));
        }

        if (dy == 0){
            y=(useYLength * i / particles);
        }
        else{
            y=(useYLength * (dy - 1));
        }

        if (dz == 0){
            z=(useZLength * i / particles);
        }
        else{
            z=(useZLength * (dz - 1));
        }

        this.displayParticle(particle, minCorner.add(x,y,z));
    }

    // Methods to add stuff to the effect after it has been created:

    public double getXLength() {
        return xLength;
    }

    public void setXLength(double xLength) {
        this.xLength = xLength;
    }

    public double getYLength() {
        return yLength;
    }

    public void setYLength(double yLength) {
        this.yLength = yLength;
    }

    public double getZLength() {
        return zLength;
    }

    public void setZLength(double zLength) {
        this.zLength = zLength;
    }

    public double getPadding() {
        return padding;
    }

    public void setPadding(double padding) {
        this.padding = padding;
    }

    public boolean isBlockSnap() {
        return blockSnap;
    }

    public void setBlockSnap(boolean blockSnap) {
        this.blockSnap = blockSnap;
    }
    public int getParticles() {
        return particles;
    }

    public void setParticles(int particles) {
        this.particles = particles;
    }

    /**
     * {@code CuboidEffect} builder static inner class.
     */
    public static final class Builder {
        private int iterations;
        private Vec3d originPos;
        private boolean updatePositions;
        private boolean useEyePosAsOrigin;
        private boolean useEyePosAsTarget;
        private Entity entityOrigin;
        private Vec3d originOffset;
        private ServerWorld world;
        private ParticleEffect particle;
        private EffectModifier executeOnStop;
        /**
         * Particles in each row
         */
        private int particles = 8;

        /**
         * Length of x component of cuboid
         */
        private double xLength = 0;

        /**
         * Length of y component of cuboid
         */
        private double yLength = 0;

        /**
         * Length of z component of cuboid
         */
        private double zLength = 0;

        /**
         * Amount of padding to add around the cube
         */
        private double padding = 0;

        /**
         * Use corners of blocks
         */
        private boolean blockSnap = false;
        private Vec3d targetPos;
        private boolean updateTargetPositions = true;
        private Entity entityTarget;
        private Vec3d targetOffset;

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
         * Sets the {@code xLength} and returns a reference to this Builder enabling method chaining.
         *
         * @param length the {@code xLength} to set
         * @return a reference to this Builder
         */
        public Builder xLength(double length) {
            this.xLength = length;
            return this;
        }

        /**
         * Sets the {@code yLength} and returns a reference to this Builder enabling method chaining.
         *
         * @param length the {@code yLength} to set
         * @return a reference to this Builder
         */
        public Builder yLength(double length) {
            this.yLength = length;
            return this;
        }

        /**
         * Sets the {@code zLength} and returns a reference to this Builder enabling method chaining.
         *
         * @param length the {@code zLength} to set
         * @return a reference to this Builder
         */
        public Builder zLength(double length) {
            this.zLength = length;
            return this;
        }

        /**
         * Sets the lenghts and returns a reference to this Builder enabling method chaining.
         *
         * @param lengths A Vec3d of the xyz lengths to set
         * @return a reference to this Builder
         */
        public Builder xLength(Vec3d lengths) {
            this.xLength = lengths.x;
            this.yLength = lengths.y;
            this.zLength = lengths.z;
            return this;
        }

        /**
         * Sets the {@code padding} and returns a reference to this Builder enabling method chaining.
         *
         * @param padding the {@code padding} to set
         * @return a reference to this Builder
         */
        public Builder padding(double padding) {
            this.padding = padding;
            return this;
        }

        /**
         * Sets the {@code blockSnap} and returns a reference to this Builder enabling method chaining.
         *
         * @param blockSnap the {@code blockSnap} to set
         * @return a reference to this Builder
         */
        public Builder blockSnap(boolean blockSnap) {
            this.blockSnap = blockSnap;
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
         * Returns a {@code CuboidEffect} built from the parameters previously set.
         *
         * @return a {@code CuboidEffect} built with parameters of this {@code CuboidEffect.Builder}
         */
        public CuboidEffect build() {
            return new CuboidEffect(this);
        }
    }

    //TODO implement better
    /*@Override
    @Deprecated
    protected boolean checkCut(Vec3d pos){
        //Applicare la box alla orgin pos centrata

        //Should be the position of the center
        Vec3d centerPos = new Vec3d(originPos.getX()+useXLength/2, originPos.getY()+useYLength/2, originPos.getZ()+useZLength/2);

        /*Box box = new Box(centerPos.getX()+cutAboveRightForward.getX(), centerPos.getY()+cutAboveRightForward.getY(), centerPos.getZ()+cutAboveRightForward.getZ(),
                centerPos.getX()-cutBelowLeftBackward.getX(), centerPos.getY()-cutBelowLeftBackward.getY(), centerPos.getZ()-cutBelowLeftBackward.getZ());
        return !box.contains(pos);
         
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
    }*/

}
