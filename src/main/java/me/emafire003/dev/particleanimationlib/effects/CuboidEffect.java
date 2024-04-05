package me.emafire003.dev.particleanimationlib.effects;

import me.emafire003.dev.particleanimationlib.EffectType;
import me.emafire003.dev.particleanimationlib.EffectV3;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import org.jetbrains.annotations.NotNull;

@SuppressWarnings("unused")
public class CuboidEffect extends EffectV3 {

    /**
     * Particles in each row
     */
    public int particles = 8;

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
    protected boolean initialized;


    /**
    * Creates a new cuboid effect.
     * Don't use this constructor, since xyz lengths and origin-target exclude each other
     *
     * @param world The world the particles are going to spawn in
     *              TODO add @link
     * @param particle The particle that are going to be spawned. You can use {@link net.minecraft.particle.ParticleTypes}
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
        super(world, EffectType.REPEATING, particle);
        this.type = EffectType.REPEATING;
        this.origin_pos = origin;
        this.target_pos = target;
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
     * @param particle The particle that are going to be spawned. You can use {@link net.minecraft.particle.ParticleTypes}
     * @param origin The origin position of the effect, aka the first corner of the cuboid
     * @param target The target position of the effect, aka the opposite corner of the cuboid
     * @param particles_per_row How many particles should each side/row have
     * @param padding The padding to add to the sides of the cuboid. A padding of 0.5 will result in having the effect closer to the border of a block, a padding of 0 will place the effect in the middle of a block
     * @param blockSnap Weather or not the corners should snap to blocks to be more precise.
     * */
    public CuboidEffect(@NotNull ServerWorld world, @NotNull ParticleEffect particle, @NotNull Vec3d origin, @NotNull Vec3d target, int particles_per_row, double padding, boolean blockSnap) {
        super(world, EffectType.REPEATING, particle);
        this.type = EffectType.REPEATING;
        this.origin_pos = origin;
        this.target_pos = target;
        this.particles = particles_per_row;
        this.padding = padding;
        this.blockSnap = blockSnap;
    }

    /**
     * Creates a new cuboid effect.
     *
     * @param world The world the particles are going to spawn in
     * @param particle The particle that are going to be spawned. You can use {@link net.minecraft.particle.ParticleTypes}
     * @param origin The origin position of the effect, aka the first corner of the cuboid
     * @param particles_per_row How many particles should each side/row have
     * @param x_length The length of the x component of the cuboid. The minimum is 1 block.
     * @param y_length The length of the y component of the cuboid. The minimum is 1 block
     * @param z_length The length of the z component of the cuboid. The minimum is 1 block
     * @param padding The padding to add to the sides of the cuboid. A padding of 0.5 will result in having the effect closer to the border of a block, a padding of 0 will place the effect in the middle of a block
     * @param blockSnap Weather or not the corners should snap to blocks to be more precise.
     * */
    public CuboidEffect(@NotNull ServerWorld world, @NotNull ParticleEffect particle, @NotNull Vec3d origin, int particles_per_row, double x_length, double y_length, double z_length, double padding, boolean blockSnap) {
        super(world, EffectType.REPEATING, particle);
        this.type = EffectType.REPEATING;
        this.origin_pos = origin;
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
     * @param particle The particle that are going to be spawned. You can use {@link net.minecraft.particle.ParticleTypes}
     * @param origin The origin position of the effect, aka the first corner of the cuboid
     * @param particles_per_row How many particles should each side/row have
     * @param x_length The length of the x component of the cuboid. The minimum is 1 block
     * @param y_length The length of the y component of the cuboid. The minimum is 1 block
     * @param z_length The length of the z component of the cuboid. The minimum is 1 block
     * */
    public CuboidEffect(@NotNull ServerWorld world, @NotNull ParticleEffect particle, @NotNull Vec3d origin, int particles_per_row, double x_length, double y_length, double z_length) {
        super(world, EffectType.REPEATING, particle);
        this.type = EffectType.REPEATING;
        this.origin_pos = origin;
        this.particles = particles_per_row;
        this.xLength = x_length;
        this.yLength = y_length;
        this.zLength = z_length;
    }

    /**
     * Creates a new cuboid effect.
     *
     * @param world The world the particles are going to spawn in
     * @param particle The particle that are going to be spawned. You can use {@link net.minecraft.particle.ParticleTypes}
     * @param origin The origin position of the effect, aka the first corner of the cuboid
     * @param target The target position of the effect, aka the opposite corner of the cuboid
     * @param particles_per_row How many particles should each side/row have
     * */
    public CuboidEffect(@NotNull ServerWorld world, @NotNull ParticleEffect particle, @NotNull Vec3d origin, @NotNull Vec3d target, int particles_per_row) {
        super(world, EffectType.REPEATING, particle);
        this.type = EffectType.REPEATING;
        this.origin_pos = origin;
        this.target_pos = target;
        this.particles = particles_per_row;
    }

    /**
     * Creates a new cuboid effect.
     *
     * @param world The world the particles are going to spawn in
     * @param particle The particle that are going to be spawned. You can use {@link net.minecraft.particle.ParticleTypes}
     * @param origin The origin position of the effect, aka the first corner of the cuboid
     * @param x_length The length of the x component of the cuboid. The minimum is 1 block
     * @param y_length The length of the y component of the cuboid. The minimum is 1 block
     * @param z_length The length of the z component of the cuboid. The minimum is 1 block
     * */
    public CuboidEffect(@NotNull ServerWorld world, @NotNull ParticleEffect particle, @NotNull Vec3d origin, double x_length, double y_length, double z_length) {
        super(world, EffectType.REPEATING, particle);
        this.type = EffectType.REPEATING;
        this.origin_pos = origin;
        this.xLength = x_length;
        this.yLength = y_length;
        this.zLength = z_length;
    }


    /**
     * Creates a new cuboid effect.
     *
     * @param world The world the particles are going to spawn in
     * @param particle The particle that are going to be spawned. You can use {@link net.minecraft.particle.ParticleTypes}
     * @param origin The origin position of the effect, aka the first corner of the cuboid
     * @param target The target position of the effect, aka the opposite corner of the cuboid
     * */
    public CuboidEffect(@NotNull ServerWorld world, @NotNull ParticleEffect particle, @NotNull Vec3d origin, @NotNull Vec3d target) {
        super(world, EffectType.REPEATING, particle);
        this.type = EffectType.REPEATING;
        this.origin_pos = origin;
        this.target_pos = target;
    }


    @Override
    public void onRun() {
        if(this.world == null || this.world.isClient()){
            return;
        }

        Vec3d target = this.getTargetPos();
        Vec3d origin = this.getOriginPos();
        if (target == null || origin == null) {
            return;
        }

        if (!initialized) {
            if (blockSnap) {
                //TODO make sure this are ok
                target = BlockPos.ofFloored(target).toCenterPos();
                minCorner = BlockPos.ofFloored(origin).toCenterPos();
            } else {
                minCorner = origin;
            }

            if (xLength == 0 && yLength == 0 && zLength == 0) {

                double x = minCorner.getX();
                double y = minCorner.getY();
                double z = minCorner.getZ();
                if (target.getX() < x) x=target.getX();
                if (target.getY() < y) y=target.getY();
                if (target.getZ() < z) z=target.getZ();
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

            initialized = true;
        }
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

}
