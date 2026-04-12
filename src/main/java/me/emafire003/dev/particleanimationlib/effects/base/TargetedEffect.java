package me.emafire003.dev.particleanimationlib.effects.base;

import me.emafire003.dev.particleanimationlib.Effect;
import me.emafire003.dev.particleanimationlib.EffectType;
import net.minecraft.world.entity.Entity;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

/** Used for effects that allow a target to be set*/
@SuppressWarnings("unused")
public class TargetedEffect extends Effect {
    //If an effect like an arc has a beginning and end pos, this is the one.
    protected Vec3 targetPos;
    protected boolean updateTargetPositions = true;
    protected boolean useEyePosAsTarget = false;
    protected Entity entityTarget;
    protected Vec3 targetOffset = Vec3.ZERO;

    public TargetedEffect(ServerLevel world, EffectType type, ParticleOptions particle, Vec3 originPos) {
        super(world, type, particle, originPos);
    }


    protected static void copy(TargetedEffect original, TargetedEffect copy) {
        Effect.copy(original, copy);
        copy.setTargetPos(original.getTargetPos());
        copy.setEntityTarget(original.getEntityTarget());
        copy.setTargetOffset(original.getTargetOffset());
        copy.type = original.type;
        copy.done = original.done;
        copy.ticks = original.ticks;
        copy.updateTargetPositions = original.updateTargetPositions;
        copy.setUseEyePosAsTarget(original.isUseEyePosAsTarget());
    }


    @Override
    public void updatePos(){
        super.updatePos();
        if(!this.shouldUpdateTargetPositions()){
            return;
        }
        if(entityTarget != null){
            if(targetOffset == null){
                if(useEyePosAsTarget){
                    this.targetPos = entityTarget.getEyePosition();
                    return;
                }
                this.targetPos = entityTarget.position();
                return;
            }
            if(useEyePosAsTarget){
                this.targetPos = entityTarget.getEyePosition().add(targetOffset);
                return;
            }
            this.targetPos = entityTarget.position().add(targetOffset);
        }
    }

    public boolean shouldUpdateTargetPositions() {
        return updateTargetPositions;
    }

    public void setUpdateTargetPositions(boolean updateTargetPositions) {
        this.updateTargetPositions = updateTargetPositions;
    }

    /** Already sums the offsets!*/
    @Nullable
    public Vec3 getTargetPos() {
        if(targetPos != null){
            if(targetOffset == null){
                return targetPos;
            }
            return targetPos.add(targetOffset);
        }
        return null;
    }

    public void setTargetPos(Vec3 finish_pos) {
        this.targetPos = finish_pos;
    }

    public Entity getEntityTarget() {
        return entityTarget;
    }

    public void setEntityTarget(Entity entityTarget) {
        this.entityTarget = entityTarget;
    }

    public Vec3 getTargetOffset() {
        return targetOffset;
    }

    public void setTargetOffset(Vec3 targetOffset) {
        this.targetOffset = targetOffset;
    }

    public boolean isUpdateTargetPositions() {
        return updateTargetPositions;
    }

    public boolean isUseEyePosAsTarget() {
        return useEyePosAsTarget;
    }

    public void setUseEyePosAsTarget(boolean useEyePosAsTarget) {
        this.useEyePosAsTarget = useEyePosAsTarget;
    }
}
