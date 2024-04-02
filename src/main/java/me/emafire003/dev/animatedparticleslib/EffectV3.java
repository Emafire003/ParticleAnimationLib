package me.emafire003.dev.animatedparticleslib;

import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.minecraft.entity.Entity;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class EffectV3 {


    protected int iterations;
    protected int delay;
    protected Vec3d origin_pos;

    //If an effect like an arc has a beginning and end pos, this is the one.
    protected Vec3d target_pos;
    protected boolean alwaysCenter;
    protected Entity centeredOriginEntity;
    protected Vec3d centeredOriginOffset;
    protected Entity centeredFinsihEntity;
    protected Vec3d centeredFinishOffset;
    protected ServerWorld world;
    public EffectType type;
    protected ParticleEffect particle;

    private boolean done = false;
    private int ticks = 0;

    public EffectV3(ServerWorld world, EffectType type, ParticleEffect particle){
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

    public void run(){
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

            if(alwaysCenter && centeredOriginEntity != null){
                this.origin_pos = centeredOriginEntity.getPos().add(centeredOriginOffset);
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

    public void displayParticle(ParticleEffect effect, Vec3d pos){
        //TODO make the count and speed configurable?
        world.spawnParticles(effect, pos.getX(), pos.getY(), pos.getZ(), 1,0, 0, 0 , 0);
    }

    public void displayParticle(ParticleEffect effect, Vec3d pos, Vec3d vel){
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

    public Vec3d getOriginPos() {
        return origin_pos;
    }

    public void setOriginPos(Vec3d origin_pos) {
        this.origin_pos = origin_pos;
    }

    public boolean isAlwaysCenter() {
        return alwaysCenter;
    }

    public void setAlwaysCenter(boolean alwaysCenter) {
        this.alwaysCenter = alwaysCenter;
    }

    public Entity getCenteredOriginEntity() {
        return centeredOriginEntity;
    }

    public void setCenteredOriginEntity(Entity centeredOriginEntity) {
        this.centeredOriginEntity = centeredOriginEntity;
    }

    public Vec3d getCenteredOriginOffset() {
        return centeredOriginOffset;
    }

    public void setCenteredOriginOffset(Vec3d centeredOriginOffset) {
        this.centeredOriginOffset = centeredOriginOffset;
    }

    public Vec3d getTargetPos() {
        return target_pos;
    }

    public void setTargetPos(Vec3d finish_pos) {
        this.target_pos = finish_pos;
    }

    public Entity getCenteredFinsihEntity() {
        return centeredFinsihEntity;
    }

    public void setCenteredFinsihEntity(Entity centeredFinsihEntity) {
        this.centeredFinsihEntity = centeredFinsihEntity;
    }

    public Vec3d getCenteredFinishOffset() {
        return centeredFinishOffset;
    }

    public void setCenteredFinishOffset(Vec3d centeredFinishOffset) {
        this.centeredFinishOffset = centeredFinishOffset;
    }
}
