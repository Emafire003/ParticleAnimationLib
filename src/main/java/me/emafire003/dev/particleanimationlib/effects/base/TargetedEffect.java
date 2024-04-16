package me.emafire003.dev.particleanimationlib.effects.base;

import me.emafire003.dev.particleanimationlib.Effect;
import me.emafire003.dev.particleanimationlib.EffectType;
import net.minecraft.entity.Entity;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.Vec3d;
import org.jetbrains.annotations.Nullable;

/** Used for effects that allow a target to be set*/
@SuppressWarnings("unused")
public class TargetedEffect extends Effect {
    //If an effect like an arc has a beginning and end pos, this is the one.
    protected Vec3d targetPos;
    protected boolean updateTargetPositions = true;
    protected Entity entityTarget;
    protected Vec3d targetOffset = Vec3d.ZERO;

    public TargetedEffect(ServerWorld world, EffectType type, ParticleEffect particle, Vec3d originPos) {
        super(world, type, particle, originPos);
    }


    @Override
    public void updatePos(){
        super.updatePos();
        if(!this.shouldUpdateTargetPositions()){
            return;
        }
        if(entityTarget != null){
            this.targetPos = entityTarget.getPos().add(targetOffset);
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
    public Vec3d getTargetPos() {
        if(targetPos != null){
            if(targetOffset == null){
                return targetPos;
            }
            return targetPos.add(targetOffset);
        }
        return null;
    }

    public void setTargetPos(Vec3d finish_pos) {
        this.targetPos = finish_pos;
    }

    public Entity getEntityTarget() {
        return entityTarget;
    }

    public void setEntityTarget(Entity entityTarget) {
        this.entityTarget = entityTarget;
    }

    public Vec3d getTargetOffset() {
        return targetOffset;
    }

    public void setTargetOffset(Vec3d targetOffset) {
        this.targetOffset = targetOffset;
    }
}
