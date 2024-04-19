package me.emafire003.dev.particleanimationlib.commands;

import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.tree.LiteralCommandNode;
import me.emafire003.dev.particleanimationlib.effects.AnimatedBallEffect;
import me.emafire003.dev.particleanimationlib.effects.ConeEffect;
import me.emafire003.dev.particleanimationlib.effects.VortexEffect;
import net.minecraft.command.CommandRegistryAccess;
import net.minecraft.command.argument.EntityArgumentType;
import net.minecraft.entity.Entity;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.text.Text;

import java.util.Collection;

public class PALDebugCommand implements PALCommand {

    private int particleEffect(CommandContext<ServerCommandSource> context) throws CommandSyntaxException {
        Collection<? extends Entity> targets = EntityArgumentType.getEntities(context, "target");
        ServerCommandSource source = context.getSource();

        try{
            for(Entity target : targets){

                /*LineEffect lineEffect = new LineEffect(source.getWorld(), ParticleTypes.SCRAPE, target.getPos(),
                150, 5, 5, true, 2,
                        new Vec3d(0, 0, 0), new Vec3d(0.05, -0.03, 0), null);
                */
                if(target.getWorld().isClient()){
                    return 0;
                }

                //VortexEffect effect = new VortexEffect(source.getWorld(), ParticleTypes.DRAGON_BREATH, target.getPos(), 90, -90);


                VortexEffect effect = new VortexEffect(source.getWorld(), ParticleTypes.EGG_CRACK, target.getPos(), 90, -90,
                        0.3f, 0.008f, 0.0f, 0.02f,
                        Math.PI / 16, 3,2
                );
                effect.setInverted(true);
                //effect.setFlipped(true);
                //effect.runFor(4);

                VortexEffect effect1 = new VortexEffect(source.getWorld(), ParticleTypes.ANGRY_VILLAGER, target.getPos(), 90, -90,
                        0.3f, 0.008f, 0.0f, 0.02f,
                        Math.PI / 16, 3,2
                );
                //effect1.runFor(4);

                ConeEffect coneEffect1 = ConeEffect.builder(source.getWorld(), ParticleTypes.SCULK_CHARGE_POP, target.getPos().add(0,-1,0))
                        .yaw(target.getYaw()).pitch(target.getPitch())
                        //.lengthGrow(0.001f).radiusGrow(0.03f)
                        .inverted(true)
                        .drawCenterAxis(true)
                        .strands(2)
                        .secondaryParticle(ParticleTypes.BUBBLE).build();
                //effect.runFor(15);
                coneEffect1.setFlipped(true);
                coneEffect1.runFor(10);

                ConeEffect coneEffect = new ConeEffect(source.getWorld(), ParticleTypes.EGG_CRACK, target.getPos().add(0,-1,0),
                        target.getYaw(), target.getPitch());
                coneEffect.setDrawCenterAxis(true);
                coneEffect.setStrands(2);
                coneEffect.setFlipped(true);
                coneEffect.setSecondaryParticle(ParticleTypes.CLOUD);
                //coneEffect.runFor(10);

                AnimatedBallEffect ballEffect = AnimatedBallEffect.builder(source.getWorld(), ParticleTypes.EFFECT, target.getEyePos()).build();
                AnimatedBallEffect copy;

                /*LineEffect lineEffect = new LineEffect(source.getWorld(), ParticleTypes.CRIT, target.getPos(),
                        target.getPos().add(5,0,0),
                        150, 1, true, 3,
                        new Vec3d(0, 0, 0), new Vec3d(0.01, -0.01, 0.01), new AnimatedBallEffect(source.getWorld(), ParticleTypes.DRAGON_BREATH, target.getPos().add(3,0,0)));
                lineEffect.setEntityOrigin(target);
                //lineEffect.setUpdatePositions(true);
                lineEffect.setYaw(target.getYaw());
                lineEffect.setPitch(target.getPitch());
                lineEffect.runFor(5);*/

                //AnimatedCircleEffect effect = new AnimatedCircleEffect(source.getWorld(), ParticleTypes.SCRAPE, target.getEyePos(), target.getYaw(), target.getPitch());
                /*AnimatedCircleEffect effect = new AnimatedCircleEffect(source.getWorld(), ParticleTypes.SCRAPE, target.getPos().add(0,0.7,0),
                        target.getYaw(), target.getPitch(), 40, 1.2f, 0, Math.PI*2,
                        false, false, true,
                        new Vec3d(Math.PI, Math.PI, Math.PI/2).multiply(1),
                        new Vec3d(0,0,0), new Vec3d(0,0,0));
                //effect.setEntityOrigin(target);
                //effect.setUpdatePositions(true);
                effect.runFor(5);*/


                /*ConeEffect coneEffect = new ConeEffect(source.getWorld(), ParticleTypes.FLAME, target.getEyePos(),
                    200, 20, 1, 0.06f, 0.005f,
                Math.PI/8, 0, false, true);
                coneEffect.setPitch(target.getPitch());
                coneEffect.setYaw(target.getYaw());
                //coneEffect.setEntityOrigin(target);
                //coneEffect.setOriginOffset(new Vec3d(0, 0.8, 0));
                //coneEffect.setUpdatePositions(true);
                coneEffect.setIterations(5*20);
                //The predicted max center works btw
                //coneEffect.runFor(5);

                AnimatedBallEffect ballEffect = new AnimatedBallEffect(source.getWorld(), ParticleTypes.ELECTRIC_SPARK, target.getPos(), 200, 30, 1f);
                //ok this works in a weird way tho. Probably should get just two vectors one positive one negative/one from "above" the other from "below"
                ballEffect.setCutShape(new Vec3d(0, 0.5, 0), new Vec3d(0, 0, 0));
                ballEffect.setShouldCut(true);
                //ballEffect.runFor(5);

                CuboidEffect cuboidEffect = new CuboidEffect(source.getWorld(), ParticleTypes.ELECTRIC_SPARK, target.getPos(), target.getPos().add(2,2,2));
                cuboidEffect.setCutShape(new Vec3d(0.6, 1, 0), new Vec3d(0, 0.4, 0));
                cuboidEffect.setShouldCut(true);*/
                //cuboidEffect.runFor(5);
            }
            return 1;
        }catch(Exception e){
            e.printStackTrace();
            source.sendFeedback( () -> Text.literal("Error: " + e),false);
            return 0;
        }

    }


    public LiteralCommandNode<ServerCommandSource> getNode(CommandRegistryAccess registryAccess) {
        return CommandManager
                .literal("debug")
                .then(
                        CommandManager.literal("particle_effect").then(
                                CommandManager.argument("target", EntityArgumentType.entities())
                                        .executes(this::particleEffect)
                        )

                )
                .build();
    }
}
