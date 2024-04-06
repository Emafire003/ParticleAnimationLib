package me.emafire003.dev.particleanimationlib.effects;


import me.emafire003.dev.particleanimationlib.EffectType;
import me.emafire003.dev.particleanimationlib.util.RandomUtils;
import me.emafire003.dev.particleanimationlib.util.VectorUtils;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.Vec3d;

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
     * @param particleConeSize By how many particles should the cone be made of
     * @param particlesPerIteration How many particles to display per iteration
     * @param strands_number The number of the strands
     * @param lengthGrow Length growth amount per each iteration
     * @param radiusGrow Radius growth amount per each iteration
     * @param angularVel The angular velocity of the effect expressed in radials per iteration, to spawn the next particle
     * @param startRotation The starting rotation angle of the cone
     * @param solid Should the cone be solid?
     * @param random Should it be random?
     * */
    public ConeEffect(ServerWorld world, ParticleEffect particle, Vec3d origin, int particleConeSize, int particlesPerIteration, int strands_number, float lengthGrow, float radiusGrow, double angularVel, double startRotation, boolean solid, boolean random) {
        super(world, EffectType.REPEATING, particle);
        this.originPos = origin;
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
                    radius *= RandomUtils.random.nextFloat();
                }

                length = step * lengthGrow;

                v = new Vec3d(Math.cos(angle) * radius, length, Math.sin(angle) * radius);

                v = VectorUtils.rotateVector(v, this.getYaw(), this.getPitch()+90);

                this.displayParticle(particle, originPos.add(v));
                this.displayParticle(particle, v);
            }
            step++;
        }
    }

}
