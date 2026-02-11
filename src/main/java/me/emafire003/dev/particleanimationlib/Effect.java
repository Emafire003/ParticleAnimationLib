package me.emafire003.dev.particleanimationlib;

import me.emafire003.dev.particleanimationlib.util.EffectModifier;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.minecraft.world.entity.Entity;
import net.minecraft.core.particles.DustParticleOptions;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;
import org.joml.Vector3f;

@SuppressWarnings("unused")
public class Effect {

    protected int iterations;
    protected Vec3 originPos;
    protected boolean updatePositions;

    /**If true and an entity is the origin it will use their head position if possible*/
    protected boolean useEyePosAsOrigin;
    protected Entity entityOrigin;
    protected Vec3 originOffset = Vec3.ZERO;

    /** A function that executes when the effect stops. For example, you could use it to chain effects one after the other*/
    public EffectModifier executeOnStop;

    /** Set this to true to skip some iteration of particles spawning to save up on server and client resources*/
    public boolean shouldSpawnParticlesEveryNIteration = false;

    /** How many iterations to skip between a particle spawning and the other. By default, it's a quarter of a second
     * Only works if {@code shouldSpawnParticlesEveryNIteration} is enabled*/
    public int spawnParticlesEveryNIteration = 5;

    /** Set this to true to limit the max number of particles spawned each iteration, to save up on memory
     * By default it's on and is capped at 5000 particles per tick. Which is a lot.*/
    public boolean shouldLimitParticlesSpawnedPerIteration = true;

    /** The limit of particles spawned at a given time (like on one iteration)*/
    public int particleLimit = 5000;

    /** Limits the number of particles spawned every N iterations specified below*/
    public boolean shouldLimitParticlesEveryNIterations = false;

    /** Every N iterations specified here the number of maximum particles spawned in that time frame is {@code particleLimit} */
    public int limitParticlesEveryNIterations = 5;

    /** Weather or not the particles spawned have the "forced" property aka rendered much further away and bypass the option to not show them on the client */
    public boolean forced = false;


    /*public Vec3d cutAboveRightForward = Vec3d.ZERO;
    public Vec3d cutBelowLeftBackward = Vec3d.ZERO;
    public boolean shouldCut = false;*/
    protected ServerLevel world;

    protected ParticleOptions particle;

    public EffectType type;

    protected int delay;

    protected boolean done = false;
    protected int ticks = 0;

    private ResourceKey<Level> worldRegistryKey;

    //TODO maybe add a completition effect like in tot time the particles appear and complete the thing? Maybe.
    public Effect(ServerLevel world, EffectType type, ParticleOptions particle, Vec3 originPos){
        this.world = world;
        this.type = type;
        this.particle = particle;
        this.originPos = originPos;
        worldRegistryKey = world.dimension();
    }

    //Used by the copy method only!
    private Effect(){
        worldRegistryKey = world.dimension();
    }

    protected static void copy(Effect original, Effect copy) {
        if (original == null) {
            return;
        }
        copy.setIterations(original.getIterations());
        copy.setOriginPos(original.getOriginPos());
        copy.setUpdatePositions(original.isUpdatePositions());
        copy.setEntityOrigin(original.getEntityOrigin());
        copy.setOriginOffset(original.getOriginOffset());
        copy.setWorld(original.getWorld());
        copy.setParticle(original.getParticle());
        copy.setDelay(original.getDelay());
        copy.setUseEyePosAsOrigin(original.isUseEyePosAsOrigin());
        copy.setExecuteOnStop(original.getExecuteOnStop());
        //New stuff 0.1.0
        copy.setShouldSpawnParticlesEveryNIteration(original.shouldSpawnParticlesEveryNIteration());
        copy.setShouldLimitParticlesEveryNIterations(original.shouldLimitParticlesEveryNIterations());
        copy.setShouldLimitParticlesSpawnedPerIteration(original.shouldLimitParticlesSpawnedPerIteration());
        copy.setLimitParticlesEveryNIterations(original.getLimitParticlesEveryNIterations());
        copy.setSpawnParticlesEveryNIteration(original.getSpawnParticlesEveryNIteration());
        copy.setParticleLimit(original.getParticleLimit());
        //0.1.2
        copy.setForced(original.isForced());

        copy.type = original.type;
        copy.done = original.done;
        copy.ticks = original.ticks;
    }


    /**Main method to extend, here the animation code is run EACH tick*/
    protected void onRun(){
    }

    /**Can be override to add a finishing effect i guess*/
    protected void onStop(){
        if(this.executeOnStop != null){
            this.executeOnStop.modifyEffect(this, ticks);
        }

    }

    /** A function that executes when the effect stops. For example you could use it to chain effects one after the other*/

    public EffectModifier getExecuteOnStop() {
        return executeOnStop;
    }

    /** Execute a function when the effect stops. For example you could use it to chain effects one after the other*/
    public void setExecuteOnStop(EffectModifier executeOnStop) {
        this.executeOnStop = executeOnStop;
    }


    public EffectType getType(){
        return type;
    }

    public void updatePos(){
        if(entityOrigin != null){
            if(originOffset == null){
                if(useEyePosAsOrigin){
                    this.originPos = entityOrigin.getEyePosition();
                    return;
                }
                this.originPos = entityOrigin.position();
                return;
            }
            if(useEyePosAsOrigin){
                this.originPos = entityOrigin.getEyePosition().add(originOffset);
                return;
            }
            this.originPos = entityOrigin.position().add(originOffset);
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
     * <p>
     * You can also provide a custom
     * lambda function to modify the effect while it runs.
     * You have access to the effect instance and the current tick
     *<p>
     * You can do the same thing by setting the number of iterations
     * manually using {@link #setIterations(int)}. Each iteration is
     * one tick, and 20 ticks are one second.
     *<p>
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
        if(this.world.isClientSide){
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
            }else{ //Repeating each tick
                ticks++;
                //Checks if the limiter is enabled
                if(shouldSpawnParticlesEveryNIteration && !(ticks%spawnParticlesEveryNIteration==0)){
                    //If it is, checks if the current iteration/tick gives a return of 0 from the %, if not skips the iteration
                    return;
                }
                this.onRun();

                //ParticleAnimationLib.LOGGER.info("Should limiti every n : "  + shouldLimitParticlesEveryNIterations + " iteratios limit: " + limitParticlesEveryNIterations + " the division: " + ticks%limitParticlesEveryNIterations);

                //If the limiter on particle count every iteration is on, clears the current count when the n-iteration is reached
                if(shouldLimitParticlesEveryNIterations && ticks%limitParticlesEveryNIterations==0){
                    this.currentParticleCount = 0;
                }else if(shouldLimitParticlesSpawnedPerIteration){
                    //If it's every iteration, rests the count each time
                    this.currentParticleCount = 0;
                }

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
    /*@Deprecated
    public void setCutShape(Vec3d cutAboveLeftForward, Vec3d cutBelowRightBackward){
        this.cutAboveRightForward = cutAboveLeftForward;
        this.cutBelowLeftBackward = cutBelowRightBackward;
        this.setShouldCut(true);
    }*/

    /**Enable or disable particle effect cutting
     *
     * Not yet ready, may be scrapped*/
    /*@Deprecated
    public void setShouldCut(boolean shouldCut){
        this.shouldCut = shouldCut;
    }
*/
    /** Not yet ready, may be scrapped*/
 /*   @Deprecated
    public boolean getShouldCut(){
        return this.shouldCut;
    }
*/
    /**Needs to be overridden by the other classes
     * Not yet ready, may be scrapped*/
/*    @Deprecated
    protected boolean checkCut(Vec3d pos){
        //TODO figure out how to implement :( :/
        return false;
    }
*/

    public void displayParticle(ParticleOptions effect, Vec3 pos){
        this.displayParticle(effect, pos, Vec3.ZERO);
    }

    public void displayParticle(Vec3 pos, int color, float size){
        Vector3f col = Vec3.fromRGB24(color).toVector3f();
        DustParticleOptions dustParticle = new DustParticleOptions(col, size);
        this.displayParticle(dustParticle, pos);
    }

    private int currentParticleCount = 0;

    public void displayParticle(ParticleOptions effect, Vec3 pos, Vec3 vel){
        /*if(shouldCut && checkCut(pos)){
            return;
        }*/
        //Checks to see if limiters are in place and starts counting
        if(shouldLimitParticlesSpawnedPerIteration || shouldLimitParticlesEveryNIterations){
            currentParticleCount++;
            //If above the particle limit, don't spawn the particles.
            // Works when the particle limit is on a single iteration/tick, or when a few iteration passed without the count resetting (this happens in the run method)
            if(currentParticleCount > particleLimit){
                return;
            }
        }
        world.players().forEach(serverPlayerEntity -> world.sendParticles(serverPlayerEntity, effect, forced, pos.x(), pos.y(), pos.z(), 1, vel.x(), vel.y(), vel.z(), 0));
    }

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
    public Vec3 getOriginPos() {
        if(originPos != null){
            if(originOffset == null){
                return originPos;
            }
            return originPos.add(originOffset);
        }
        return null;
    }

    public void setOriginPos(Vec3 origin_pos) {
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

    public Vec3 getOriginOffset() {
        return originOffset;
    }

    public void setOriginOffset(Vec3 originOffset) {
        this.originOffset = originOffset;
    }
    public boolean isUseEyePosAsOrigin() {
        return useEyePosAsOrigin;
    }

    public void setUseEyePosAsOrigin(boolean useEyePosAsOrigin) {
        this.useEyePosAsOrigin = useEyePosAsOrigin;
    }

    public ServerLevel getWorld() {
        return world;
    }

    public ResourceKey<Level> getWorldRegistryKey() {
        return worldRegistryKey;
    }

    public void setWorld(ServerLevel world) {
        this.world = world;
    }

    public ParticleOptions getParticle() {
        return particle;
    }

    public void setParticle(ParticleOptions particle) {
        this.particle = particle;
    }

    public int getLimitParticlesEveryNIterations() {
        return limitParticlesEveryNIterations;
    }

    public void setLimitParticlesEveryNIterations(int limitParticlesEveryNIterations) {
        this.limitParticlesEveryNIterations = limitParticlesEveryNIterations;
    }

    public boolean shouldLimitParticlesEveryNIterations() {
        return shouldLimitParticlesEveryNIterations;
    }

    public void setShouldLimitParticlesEveryNIterations(boolean shouldLimitParticlesEveryNIterations) {
        this.shouldLimitParticlesEveryNIterations = shouldLimitParticlesEveryNIterations;
    }

    public int getParticleLimit() {
        return particleLimit;
    }

    public void setParticleLimit(int particleLimit) {
        this.particleLimit = particleLimit;
    }

    public int getSpawnParticlesEveryNIteration() {
        return spawnParticlesEveryNIteration;
    }

    public void setSpawnParticlesEveryNIteration(int spawnParticlesEveryNIteration) {
        this.spawnParticlesEveryNIteration = spawnParticlesEveryNIteration;
    }

    public boolean shouldSpawnParticlesEveryNIteration() {
        return shouldSpawnParticlesEveryNIteration;
    }

    public void setShouldSpawnParticlesEveryNIteration(boolean shouldSpawnParticlesEveryNIteration) {
        this.shouldSpawnParticlesEveryNIteration = shouldSpawnParticlesEveryNIteration;
    }

    public boolean shouldLimitParticlesSpawnedPerIteration() {
        return shouldLimitParticlesSpawnedPerIteration;
    }

    public void setShouldLimitParticlesSpawnedPerIteration(boolean shouldLimitParticlesSpawnedPerIteration) {
        this.shouldLimitParticlesSpawnedPerIteration = shouldLimitParticlesSpawnedPerIteration;
    }

    public boolean isForced() {
        return forced;
    }

    public void setForced(boolean forced) {
        this.forced = forced;
    }

}
