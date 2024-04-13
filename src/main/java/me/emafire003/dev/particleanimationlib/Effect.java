package me.emafire003.dev.particleanimationlib;

import me.emafire003.dev.particleanimationlib.util.EffectModifier;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.minecraft.entity.Entity;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.Vec3d;
import org.jetbrains.annotations.Nullable;

@SuppressWarnings("unused")
public class Effect {
    protected int iterations;
    protected int delay;
    protected Vec3d originPos;

    //If an effect like an arc has a beginning and end pos, this is the one.
    protected Vec3d targetPos;
    protected boolean updatePositions;
    protected Entity entityOrigin;
    protected Vec3d originOffset = Vec3d.ZERO;
    protected Entity entityTarget;
    protected Vec3d targetOffset = Vec3d.ZERO;
    public Vec3d cutAboveRightForward = Vec3d.ZERO;
    public Vec3d cutBelowLeftBackward = Vec3d.ZERO;
    public boolean shouldCut = false;
    protected ServerWorld world;
    public EffectType type;
    protected ParticleEffect particle;

    protected boolean done = false;
    protected int ticks = 0;

    public Effect(ServerWorld world, EffectType type, ParticleEffect particle){
        this.world = world;
        this.type = type;
        this.particle = particle;
    }



    /**Main method to extend, here the animation code is run EACH tick*/
    protected void onRun(){
    }

    /**Can be override to add a finishing effect i guess*/
    protected void onStop(){

    }

    public void updatePos(){
        if(entityOrigin != null){
            this.originPos = entityOrigin.getPos().add(originOffset);
        }
        if(entityTarget != null){
            this.targetPos = entityTarget.getPos().add(targetOffset);
        }
    }

    /**Runs the effect for the specified amount of seconds
     * You can do the same thing by setting the number of iterations
     * manually using {@link #setIterations(int)}. Each iteration is
     * one tick, and 20 ticks are one second.
     * Does not work with instant effects (wow eh?)
     *
     * @param seconds The number of seconds to run the effect for.
     *                Will be rounded to the closest tick number once calculated.
     *                For example 0.5 seconds will be 10 ticks, 0.01 seconds will be 0 ticks.*/
    public void runFor(double seconds){
        this.setIterations((int) (seconds*20));
        this.run();
    }

    /**Runs the effect for the specified amount of seconds
     *
     * You can also provide a custom
     * lambda function to modify the effect while it runs.
     * You have access to the effect instance and the current tick
     *
     * You can do the same thing by setting the number of iterations
     * manually using {@link #setIterations(int)}. Each iteration is
     * one tick, and 20 ticks are one second.
     *
     * Does not work with instant effects (wow eh?)
     *
     * @param seconds The number of seconds to run the effect for.
     *                Will be rounded to the closest tick number once calculated.
     *                For example 0.5 seconds will be 10 ticks, 0.01 seconds will be 0 ticks.*/
    public void runFor(double seconds, EffectModifier modifier){
        this.setIterations((int) (seconds*20));
        this.run(modifier);
    }


    /**Runs the effect*/
    public void run(){
        this.run(null);
    }

    /**Runs the effect, you can also provide a custom
     * lambda function to modify the effect while it runs.
     * You have access to the effect instance and the current tick
     * */
    public void run(EffectModifier modifier){
        if(this.world.isClient){
            return;
        }

        this.onRun();
        if(this.type == EffectType.INSTANT){
            return;
        }
        ServerTickEvents.END_SERVER_TICK.register(server -> {
            if (done) {
                return;
            }

            if(modifier != null){
                modifier.modifyEffect(this, ticks);
            }


            //TODO updating position like this kind of breaks the animation, but there isn't really another way i think.
            if(updatePositions){
                updatePos();
            }

            if(this.type == EffectType.DELAYED){
                //Increasing tick count every tick, and executing one after the ticks reached the delay
                ticks++;
                if(ticks > delay){
                    done = true;
                    ticks = 0;
                    this.onRun();
                }
            }else{
                //Repeating each tick
                this.onRun();
                ticks++;
                if(ticks > iterations){
                    done = true;
                    ticks = 0;
                    this.onStop();
                }
            }
        });
    }


    /**You can use this method to specify a cut shape.
     * This will cancel particles that are spawned beyond this box,
     * centered around the {@link #originPos}.
     *
     * Set high values if you don't want to cut in that coordinate
     *
     * @param cutAboveLeftForward A Vec3d which cuts from the top to the bottom (Y), from the left to the right (X), from the front to the back (Z)
     * @param cutBelowRightBackward A Vec3d that cuts the other way around
     * Not yet ready, may be scrapped*/
    @Deprecated
    public void setCutShape(Vec3d cutAboveLeftForward, Vec3d cutBelowRightBackward){
        this.cutAboveRightForward = cutAboveLeftForward;
        this.cutBelowLeftBackward = cutBelowRightBackward;
        this.setShouldCut(true);
    }

    /**Enable or disable particle effect cutting
     *
     * Not yet ready, may be scrapped*/
    @Deprecated
    public void setShouldCut(boolean shouldCut){
        this.shouldCut = shouldCut;
    }

    /** Not yet ready, may be scrapped*/
    @Deprecated
    public boolean getShouldCut(){
        return this.shouldCut;
    }

    /**Needs to be overridden by the other classes
     * Not yet ready, may be scrapped*/
    @Deprecated
    protected boolean checkCut(Vec3d pos){
        //TODO figure out how to implement :( :/
        return false;
    }

    public void displayParticle(ParticleEffect effect, Vec3d pos){
        //TODO make the count and speed configurable?
        this.displayParticle(effect, pos, Vec3d.ZERO);
    }


    public void displayParticle(ParticleEffect effect, Vec3d pos, Vec3d vel){
        if(shouldCut && checkCut(pos)){
            return;
        }
        world.spawnParticles(effect, pos.getX(), pos.getY(), pos.getZ(), 1,vel.getX(), vel.getY(), vel.getZ() , 0);
    }

    /*public void displayParticle(ParticleEffect effect, Vec3d pos, boolean alwaysSpawn){
        world.addParticle(effect, alwaysSpawn, pos.getX(), pos.getY(), pos.getZ(), 0, 0, 0);
    }

    public void displayParticle(ParticleEffect effect, Vec3d pos, Vec3d vel, boolean alwaysSpawn){
        world.addParticle(effect, alwaysSpawn, pos.getX(), pos.getY(), pos.getZ(), vel.getX(), vel.getY(), vel.getZ());
    }*/

    public int getIterations() {
        return iterations;
    }

    public void setIterations(int iterations) {
        this.iterations = iterations;
    }

    public int getDelay() {
        return delay;
    }

    public void setDelay(int delay) {
        this.delay = delay;
    }

    /** Already sums the offsets!*/
    @Nullable
    public Vec3d getOriginPos() {
        if(originPos != null){
            return originPos.add(originOffset);
        }
        return null;
    }

    public void setOriginPos(Vec3d origin_pos) {
        this.originPos = origin_pos;
    }

    public boolean isUpdatePositions() {
        return updatePositions;
    }

    public void setUpdatePositions(boolean updatePositions) {
        this.updatePositions = updatePositions;
    }

    public Entity getEntityOrigin() {
        return entityOrigin;
    }

    public void setEntityOrigin(Entity entityOrigin) {
        this.entityOrigin = entityOrigin;
    }

    public Vec3d getOriginOffset() {
        return originOffset;
    }

    public void setOriginOffset(Vec3d originOffset) {
        this.originOffset = originOffset;
    }

    /** Already sums the offsets!*/
    @Nullable
    public Vec3d getTargetPos() {
        if(targetPos != null){
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
