package me.emafire003.dev.particleanimationlib.effects;

import me.emafire003.dev.particleanimationlib.EffectType;
import me.emafire003.dev.particleanimationlib.effects.base.YPREffect;
import me.emafire003.dev.particleanimationlib.util.VectorUtils;
import net.minecraft.particle.ParticleEffect;
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
     * @param particle The particle effect that is going to be spawned. You can use {@link net.minecraft.particle.ParticleTypes}
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
     * @param particle The particle effect that is going to be spawned. You can use {@link net.minecraft.particle.ParticleTypes}
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
     * @param particle The particle effect that is going to be spawned. You can use {@link net.minecraft.particle.ParticleTypes}
     * @param origin The origin position of the effect, aka the center point of the circle
     * */
    public AnimatedCircleEffect(ServerWorld world, ParticleEffect particle, Vec3d origin) {
        super(world, EffectType.REPEATING, particle, origin);
    }

    /**
     * Creates a new circle effect
     *
     * @param world The world the particles are going to spawn in
     * @param particle The particle effect that is going to be spawned. You can use {@link net.minecraft.particle.ParticleTypes}
     * @param origin The origin position of the effect, aka the center point of the circle
     * @param yaw The yaw of the effect. For example, you can get it from an Entity using getYaw()
     * @param pitch The pitch of the effect. For example, you can get it from an Entity using getPitch()
     * @param particles_per_circle Number of particles that make up each circle
     * @param radius The radius of the circle
     * @param radiusGrow Radius growth amount per each iteration. Setting to 0 won't make the radius grow
     * */
    public AnimatedCircleEffect(ServerWorld world, ParticleEffect particle, Vec3d origin, float yaw, float pitch, int particles_per_circle, float radius, float radiusGrow) {
        super(world, EffectType.REPEATING, particle, origin);
        this.yaw = yaw;
        this.pitch = pitch;
        this.particles = particles_per_circle;
        this.radius = radius;
        this.radiusGrow = radiusGrow;
    }

    /**
     * Creates a new circle effect
     *
     * @param world The world the particles are going to spawn in
     * @param particle The particle effect that is going to be spawned. You can use {@link net.minecraft.particle.ParticleTypes}
     * @param origin The origin position of the effect, aka the center point of the circle
     * @param yaw The yaw of the effect. For example, you can get it from an Entity using getYaw()
     * @param pitch The pitch of the effect. For example, you can get it from an Entity using getPitch()
     * @param particles_per_circle Number of particles that make up each circle
     * @param radius The radius of the circle
     * @param radiusGrow Radius growth amount per each iteration. Setting to 0 won't make the radius grow
     * @param maxAngle Used to make a partial circle. Expressed in radians
     * @param wholeCircle Makes a whole circle every iteration
     * @param resetCircle Start at the same origin each step, use this along with maxAngle and wholeCircle to form persistent semicircles
     * */
    public AnimatedCircleEffect(ServerWorld world, ParticleEffect particle, Vec3d origin, float yaw, float pitch, int particles_per_circle, float radius, float radiusGrow, double maxAngle, boolean wholeCircle, boolean resetCircle) {
        super(world, EffectType.REPEATING, particle, origin);
        this.yaw = yaw;
        this.pitch = pitch;
        this.particles = particles_per_circle;
        this.radius = radius;
        this.radiusGrow = radiusGrow;
        this.maxAngle = maxAngle;
        this.wholeCircle = wholeCircle;
        this.resetCircle = resetCircle;
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

}
