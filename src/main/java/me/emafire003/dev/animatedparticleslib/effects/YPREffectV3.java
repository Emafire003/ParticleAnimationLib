package me.emafire003.dev.animatedparticleslib.effects;

import me.emafire003.dev.animatedparticleslib.EffectType;
import me.emafire003.dev.animatedparticleslib.EffectV3;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.server.world.ServerWorld;

/**Class used for effects that also require yaw pitch roll values*/
@SuppressWarnings("unused")
public class YPREffectV3 extends EffectV3 {


    public float yawOffset = 0;
    public float pitchOffset = 0;
    public float yaw = 0;
    public float pitch = 0;
    public float roll = 0;
    public float rollOffset = 0;

    public YPREffectV3(ServerWorld world, EffectType type, ParticleEffect particle) {
        super(world, type, particle);
    }

    @Override
    public void updatePos(){
        super.updatePos();
        if(centeredOriginEntity != null){
            this.pitch = centeredOriginEntity.getPitch()+this.pitchOffset;
            this.yaw = centeredOriginEntity.getYaw()+this.yawOffset;
            //TODO may need to add the target yaw pitch thing?
        }
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
}
