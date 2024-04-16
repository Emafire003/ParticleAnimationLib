package me.emafire003.dev.particleanimationlib.effects;


import me.emafire003.dev.particleanimationlib.EffectType;
import me.emafire003.dev.particleanimationlib.util.RandomUtils;
import me.emafire003.dev.particleanimationlib.util.VectorUtils;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.Vec3d;

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

    /** Should the cone start from the max position and go to the origin position?*/
    boolean inverted = false;

    /** Do you want to draw the center axis as well?*/
    boolean drawCenterAxis = false;

    /** Draws a point (one particle) where the effect ends*/
    boolean drawFinishPoint = false;
    /** The particles to use for displaying the center axis/finish point
     * Falls back to the particle of this effect*/
    ParticleEffect secondaryParticle = particle;


    /**
     * Current step. Works as counter
     */
    protected int step = 0;
    private boolean lineCreated = false;

    /**
     * Creates a new cone effect
     *
     * @param world The world the particles are going to spawn in
     * @param particle The particle effect that is going to be spawned. You can use {@link net.minecraft.particle.ParticleTypes}
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
     * @param particle The particle effect that is going to be spawned. You can use {@link net.minecraft.particle.ParticleTypes}
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

    /**
     * Creates a new cone effect
     *
     * @param world The world the particles are going to spawn in
     * @param particle The particle effect that is going to be spawned. You can use {@link net.minecraft.particle.ParticleTypes}
     * @param origin The origin position of the effect, aka the starting point of the cone
     * */
    public ConeEffect(ServerWorld world, ParticleEffect particle, Vec3d origin) {
        super(world, EffectType.REPEATING, particle, origin);
    }

    /**
     * Creates a new cone effect
     *
     * @param world The world the particles are going to spawn in
     * @param particle The particle effect that is going to be spawned. You can use {@link net.minecraft.particle.ParticleTypes}
     * @param origin The origin position of the effect, aka the starting point of the cone
     * @param yaw The yaw of the effect. For example, you can get it from an Entity using getYaw()
     * @param pitch The pitch of the effect. For example, you can get it from an Entity using getPitch()
     * @param particleConeSize By how many particles should the cone be made of
     * @param particlesPerIteration How many particles to display per iteration
     * */
    public ConeEffect(ServerWorld world, ParticleEffect particle, Vec3d origin, float yaw, float pitch, int particleConeSize, int particlesPerIteration) {
        super(world, EffectType.REPEATING, particle, origin);
        this.yaw = yaw;
        this.pitch = pitch;
        this.particles = particlesPerIteration;
        this.particlesCone = particleConeSize;
        this.setShouldUpdateYPR(true);
    }

    /**
     * Creates a new cone effect
     *
     * @param world The world the particles are going to spawn in
     * @param particle The particle effect that is going to be spawned. You can use {@link net.minecraft.particle.ParticleTypes}
     * @param origin The origin position of the effect, aka the starting point of the cone
     * @param yaw The yaw of the effect. For example, you can get it from an Entity using getYaw()
     * @param pitch The pitch of the effect. For example, you can get it from an Entity using getPitch()
     * @param particleConeSize By how many particles should the cone be made of
     * @param particlesPerIteration How many particles to display per iteration
     * @param strands_number The number of the strands
     * @param lengthGrow Length growth amount per each iteration
     * @param radiusGrow Radius growth amount per each iteration
     * */
    public ConeEffect(ServerWorld world, ParticleEffect particle, Vec3d origin, float yaw, float pitch, int particleConeSize, int particlesPerIteration, int strands_number, float lengthGrow, float radiusGrow) {
        super(world, EffectType.REPEATING, particle, origin);
        this.yaw = yaw;
        this.pitch = pitch;
        this.particles = particlesPerIteration;
        this.particlesCone = particleConeSize;
        this.strands = strands_number;
        this.lengthGrow = lengthGrow;
        this.radiusGrow = radiusGrow;
        this.setShouldUpdateYPR(true);
    }

    /**
     * Creates a new cone effect
     *
     * @param world The world the particles are going to spawn in
     * @param particle The particle effect that is going to be spawned. You can use {@link net.minecraft.particle.ParticleTypes}
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
     * */
    public ConeEffect(ServerWorld world, ParticleEffect particle, Vec3d origin, float yaw, float pitch, int particleConeSize, int particlesPerIteration, int strands_number, float lengthGrow, float radiusGrow, double angularVel, double startRotation) {
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
        this.setShouldUpdateYPR(true);
    }

    /**
     * Creates a new cone effect
     *
     * @param world The world the particles are going to spawn in
     * @param particle The particle effect that is going to be spawned. You can use {@link net.minecraft.particle.ParticleTypes}
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
     * */
    public ConeEffect(ServerWorld world, ParticleEffect particle, Vec3d origin, float yaw, float pitch, int particleConeSize, int particlesPerIteration, int strands_number, float lengthGrow, float radiusGrow, double angularVel, double startRotation, boolean solid) {
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
        this.setShouldUpdateYPR(true);
    }

    /** Returns the position of the center of the cone at it maximum point*/
    public Vec3d getPredictedMaxCenterPosition(){
        float total_length = this.getIterations() * lengthGrow;

        Vec3d v = new Vec3d(0, total_length, 0);

        v = VectorUtils.rotateVector(v, this.getYaw(), this.getPitch()+90);

        return originPos.add(v);
    }

    @Override
    protected void onRun() {
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

                if(inverted){
                    v = v.multiply(-1);
                    this.displayParticle(particle, getPredictedMaxCenterPosition().add(v));
                }else{
                    this.displayParticle(particle, originPos.add(v));
                }

            }
            if(drawCenterAxis && !lineCreated){
                LineEffect line = new LineEffect(this.world, this.secondaryParticle, this.getOriginPos(), this.getPredictedMaxCenterPosition(), this.particles);
                line.setIterations(this.iterations);
                line.run();
                lineCreated = true;
            }
            if(drawFinishPoint){
                if(inverted){
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

}
