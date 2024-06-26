package me.emafire003.dev.particleanimationlib.effects.base;

import me.emafire003.dev.particleanimationlib.EffectType;
import me.emafire003.dev.particleanimationlib.Effect;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.Vec3d;
import org.jetbrains.annotations.NotNull;

/**Class used for effects that also require yaw pitch roll values
 * Note: Roll values are currently unused and won't do anything (version 0.0.1)*/
@SuppressWarnings("unused")
public class YPREffect extends Effect {


    public float yawOffset = 0;
    public float pitchOffset = 0;
    public float yaw = 0;
    public float pitch = 0;
    public float roll = 0;
    public float rollOffset = 0;
    public boolean shouldUpdateYPR = false;

    public YPREffect(ServerWorld world, EffectType type, ParticleEffect particle, Vec3d originPos) {
        super(world, type, particle, originPos);
    }

    protected static void copy(YPREffect original, YPREffect copy) {
        Effect.copy(original, copy);
        copy.setYawOffset(original.getYawOffset());
        copy.setPitchOffset(original.getPitchOffset());
        copy.setYaw(original.getYaw());
        copy.setPitch(original.getPitch());
        copy.setRoll(original.getRoll());
        copy.setRollOffset(original.getRollOffset());
        copy.setShouldUpdateYPR(original.getShouldUpdateYPR());
    }

    @Override
    public void updatePos(){
        super.updatePos();
        if(!this.getShouldUpdateYPR()){
            return;
        }
        if(entityOrigin != null){
            this.pitch = entityOrigin.getPitch()+this.pitchOffset;
            this.yaw = entityOrigin.getYaw()+this.yawOffset;
            //TODO may need to add the target's yaw pitch thing?
        }
    }

    public boolean getShouldUpdateYPR() {
        return shouldUpdateYPR;
    }

    /**Should the Yaw Pitch and Roll also be updated
     * when updating positions?
     *
     * @param shouldUpdateYPR True if you want to update yaw pitch roll values*/
    public void setShouldUpdateYPR(boolean shouldUpdateYPR) {
        this.shouldUpdateYPR = shouldUpdateYPR;
    }


    public float getYawOffset() {
        return yawOffset;
    }

    public void setYawOffset(float yawOffset) {
        this.yawOffset = yawOffset;
    }

    public float getPitchOffset() {
        return pitchOffset;
    }

    public void setPitchOffset(float pitchOffset) {
        this.pitchOffset = pitchOffset;
    }

    public float getYaw() {
        return yaw;
    }

    public void setYaw(float yaw) {
        this.yaw = yaw;
    }

    public float getPitch() {
        return pitch;
    }

    public void setPitch(float pitch) {
        this.pitch = pitch;
    }

    public float getRoll() {
        return roll;
    }

    public void setRoll(float roll) {
        this.roll = roll;
    }

    public float getRollOffset() {
        return rollOffset;
    }

    public void setRollOffset(float rollOffset) {
        this.rollOffset = rollOffset;
    }

    @NotNull
    public Vec3d getDirection() {
        Vec3d vector = Vec3d.ZERO;
        double rotX = this.getYaw();
        double rotY = this.getPitch();
        vector = new Vec3d(vector.getX(), -Math.sin(Math.toRadians(rotY)), vector.getZ());
        double xz = Math.cos(Math.toRadians(rotY));
        vector = new Vec3d(-xz * Math.sin(Math.toRadians(rotX)), vector.getY(), xz * Math.cos(Math.toRadians(rotX)));
        return vector;
    }
}
