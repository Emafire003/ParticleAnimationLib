package me.emafire003.dev.particleanimationlib.effects;

import me.emafire003.dev.particleanimationlib.EffectType;
import me.emafire003.dev.particleanimationlib.effects.base.YPREffect;
import me.emafire003.dev.particleanimationlib.util.VectorUtils;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.Vec3d;

public class DonutEffect extends YPREffect {

    /**
     * Amount of particles inside of a single vertical circle
     */
    public int particlesCircle = 10;

    /**
     * Amount of circles to build the torus
     */
    public int circles = 36;

    /**
     * Radius of the torus
     */
    public float radiusDonut = 2;

    /**
     * Radius of the tube (the circles on the outside).
     */
    public float radiusTube = .5f;

    /**
     * Rotation of the torus.
     */
    public Vec3d rotation = Vec3d.ZERO;

    /** Creates a new donut effect
     * <p>
     * A few details: The rotation is in radians, while the yaw/pitch are like in vanilla, in degrees
     * Rotation is applied AFTER the yaw & pitch.
     * Yaw and pitch are adjusted so the hole faces the player when given the yaw and pitch values.
     *
     * @param world The world the particles are going to spawn in
     * @param particle The particle effect that is going to be spawned. You can use {@link ParticleTypes}
     * @param originPos The origin position of the effect
     * @param yaw The yaw of the effect. For example, you can get it from an Entity using getYaw()
     * @param pitch The pitch of the effect. For example, you can get it from an Entity using getPitch()
     * @param circles The amount of circles to build the torus with (this will go on the "outside" of the hole, the more, the more defined the donut will be)
     * @param radiusDonut The radius of whole the donut/torus
     * @param radiusTube The radius of the tube (the individual circles that make the tube part of the donut torus. Not the hole, the donut part)
     * @param rotation The rotation of the whole donut/torus
     * */
    public DonutEffect(ServerWorld world, ParticleEffect particle, Vec3d originPos, float yaw, float pitch, int particlesCircle, int circles, float radiusDonut, float radiusTube, Vec3d rotation) {
        super(world, EffectType.REPEATING, particle, originPos);
        this.yaw = yaw;
        this.pitch = pitch;
        this.particlesCircle = particlesCircle;
        this.circles = circles;
        this.radiusDonut = radiusDonut;
        this.radiusTube = radiusTube;
        this.rotation = rotation;
    }

    public DonutEffect(ServerWorld world, ParticleEffect particle, Vec3d originPos){
        super(world, EffectType.REPEATING, particle, originPos);
    }

    @Override
    public void onRun() {
        Vec3d origin = this.getOriginPos();
        Vec3d v;

        if (origin == null){
            return;
        }

        for (int i = 0; i < circles; i++) {
            double theta = 2 * Math.PI * i / circles;
            for (int j = 0; j < particlesCircle; j++) {
                double phi = 2 * Math.PI * j / particlesCircle;
                double cosPhi = Math.cos(phi);
                v = new Vec3d((radiusDonut + radiusTube * cosPhi) * Math.cos(theta), (radiusDonut + radiusTube * cosPhi) * Math.sin(theta), radiusTube * Math.sin(phi));

                v = VectorUtils.rotateVector(v, this.getYaw()+90, this.getPitch()+90);
                v = VectorUtils.rotateVector(v, (float) rotation.x, (float) rotation.y, (float) rotation.z);

                this.displayParticle(this.particle, origin.add(v));
            }
        }
    }

    public int getParticlesCircle() {
        return particlesCircle;
    }

    public void setParticlesCircle(int particlesCircle) {
        this.particlesCircle = particlesCircle;
    }

    public int getCircles() {
        return circles;
    }

    public void setCircles(int circles) {
        this.circles = circles;
    }

    public float getRadiusTube() {
        return radiusTube;
    }

    public void setRadiusTube(float radiusTube) {
        this.radiusTube = radiusTube;
    }

    public float getRadiusDonut() {
        return radiusDonut;
    }

    public void setRadiusDonut(float radiusDonut) {
        this.radiusDonut = radiusDonut;
    }

    public Vec3d getRotation() {
        return rotation;
    }

    public void setRotation(Vec3d rotation) {
        this.rotation = rotation;
    }

}
