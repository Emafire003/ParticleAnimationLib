package me.emafire003.dev.particleanimationlib.effects;


import me.emafire003.dev.particleanimationlib.EffectType;
import me.emafire003.dev.particleanimationlib.util.RandomUtils;
import me.emafire003.dev.particleanimationlib.util.VectorUtils;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.Vec3d;

@SuppressWarnings("unused")
public class ConeEffect extends YPREffectV3 {

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


    /**
     * Current step. Works as counter
     */
    protected int step = 0;

    /**
     * Creates a new cone effect
     *
     * @param world The world the particles are going to spawn in
     * @param particle The particle that are going to be spawned. You can use {@link net.minecraft.particle.ParticleTypes}
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
        super(world, EffectType.REPEATING, particle);
        this.originPos = origin;
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
    }

    /**
     * Creates a new cone effect
     *
     * @param world The world the particles are going to spawn in
     * @param particle The particle that are going to be spawned. You can use {@link net.minecraft.particle.ParticleTypes}
     * @param origin The origin position of the effect, aka the starting point of the cone
     * @param yaw The yaw of the effect. For example, you can get it from an Entity using getYaw()
     * @param pitch The pitch of the effect. For example, you can get it from an Entity using getPitch()
     * */
    public ConeEffect(ServerWorld world, ParticleEffect particle, Vec3d origin, float yaw, float pitch) {
        super(world, EffectType.REPEATING, particle);
        this.originPos = origin;
        this.yaw = yaw;
        this.pitch = pitch;
    }

    /**
     * Creates a new cone effect
     *
     * @param world The world the particles are going to spawn in
     * @param particle The particle that are going to be spawned. You can use {@link net.minecraft.particle.ParticleTypes}
     * @param origin The origin position of the effect, aka the starting point of the cone
     * */
    public ConeEffect(ServerWorld world, ParticleEffect particle, Vec3d origin) {
        super(world, EffectType.REPEATING, particle);
        this.originPos = origin;
    }

    /**
     * Creates a new cone effect
     *
     * @param world The world the particles are going to spawn in
     * @param particle The particle that are going to be spawned. You can use {@link net.minecraft.particle.ParticleTypes}
     * @param origin The origin position of the effect, aka the starting point of the cone
     * @param yaw The yaw of the effect. For example, you can get it from an Entity using getYaw()
     * @param pitch The pitch of the effect. For example, you can get it from an Entity using getPitch()
     * @param particleConeSize By how many particles should the cone be made of
     * @param particlesPerIteration How many particles to display per iteration
     * */
    public ConeEffect(ServerWorld world, ParticleEffect particle, Vec3d origin, float yaw, float pitch, int particleConeSize, int particlesPerIteration) {
        super(world, EffectType.REPEATING, particle);
        this.originPos = origin;
        this.yaw = yaw;
        this.pitch = pitch;
        this.particles = particlesPerIteration;
        this.particlesCone = particleConeSize;
    }

    /**
     * Creates a new cone effect
     *
     * @param world The world the particles are going to spawn in
     * @param particle The particle that are going to be spawned. You can use {@link net.minecraft.particle.ParticleTypes}
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
        super(world, EffectType.REPEATING, particle);
        this.originPos = origin;
        this.yaw = yaw;
        this.pitch = pitch;
        this.particles = particlesPerIteration;
        this.particlesCone = particleConeSize;
        this.strands = strands_number;
        this.lengthGrow = lengthGrow;
        this.radiusGrow = radiusGrow;
    }

    /**
     * Creates a new cone effect
     *
     * @param world The world the particles are going to spawn in
     * @param particle The particle that are going to be spawned. You can use {@link net.minecraft.particle.ParticleTypes}
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
        super(world, EffectType.REPEATING, particle);
        this.originPos = origin;
        this.yaw = yaw;
        this.pitch = pitch;
        this.particles = particlesPerIteration;
        this.particlesCone = particleConeSize;
        this.strands = strands_number;
        this.lengthGrow = lengthGrow;
        this.radiusGrow = radiusGrow;
        this.angularVelocity = angularVel;
        this.rotation = startRotation;
    }

    /**
     * Creates a new cone effect
     *
     * @param world The world the particles are going to spawn in
     * @param particle The particle that are going to be spawned. You can use {@link net.minecraft.particle.ParticleTypes}
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
        super(world, EffectType.REPEATING, particle);
        this.originPos = origin;
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
    }

    /** Returns the position of the center of the cone at it maximum point*/
    public Vec3d predictedMaxCenterPosition(){
        float total_length = this.getIterations() * lengthGrow;

        Vec3d v = new Vec3d(0, total_length*2, 0);

        v = VectorUtils.rotateVector(v, this.getYaw(), this.getPitch()+90);

        return originPos.add(v);
    }

    //TODO try to get an inverted cone, get the predicted max growth too.
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

                this.displayParticle(particle, originPos.add(v));
            }
            //this.displayParticle(ParticleTypes.EGG_CRACK, predictedMaxCenterPosition()); would have displayed a particle at the center of the max length of the cone
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

}
