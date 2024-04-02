package me.emafire003.dev.animatedparticleslib.effects;

import me.emafire003.dev.animatedparticleslib.EffectType;
import me.emafire003.dev.animatedparticleslib.EffectV3;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import org.jetbrains.annotations.NotNull;

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
     *
     * @param world The world the particles are going to spawn in
     *              TODO add @link
     * @param particle The particle that are going to be spawned. You can use ParticleTypes class for example
     * @param origin The origin position of the effect, aka the first corner of the cuboid
     * @param target The target position of the effect, aka the opposite corner of the cuboid
     * @param particles_per_row How many particles should each side/row have
     * @param x_length The length of the x component of the cuboid
     * @param y_length The length of the y component of the cuboid
     * @param z_length The length of the z component of the cuboid
     * @param padding The padding to add to the sides of the cuboid
     * @param blockSnap Weather or not the corners should snap to blocks to be more precise. Aka should we use the corner of blocks or not?
     * */
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
                if (target.getX() < minCorner.getX()) x=target.getX();
                if (target.getY() < minCorner.getY()) y=target.getY();
                if (target.getZ() < minCorner.getZ()) z=target.getZ();
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

}
