package me.emafire003.dev.particleanimationlib.commands;

import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.tree.LiteralCommandNode;
import me.emafire003.dev.particleanimationlib.effects.AnimatedBallEffect;
import me.emafire003.dev.particleanimationlib.effects.ConeEffect;
import me.emafire003.dev.particleanimationlib.effects.CuboidEffect;
import net.minecraft.command.argument.EntityArgumentType;
import net.minecraft.entity.Entity;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.text.Text;
import net.minecraft.util.math.Vec3d;

import java.util.Collection;

public class PALDebugCommand implements PALCommand {

    private int particleEffect(CommandContext<ServerCommandSource> context) throws CommandSyntaxException {
        Collection<? extends Entity> targets = EntityArgumentType.getEntities(context, "target");
        ServerCommandSource source = context.getSource();

        try{
            for(Entity target : targets){

                ConeEffect coneEffect = new ConeEffect(source.getWorld(), ParticleTypes.FLAME, target.getEyePos(),
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
                cuboidEffect.setShouldCut(true);
                //cuboidEffect.runFor(5);
            }
            return 1;
        }catch(Exception e){
            e.printStackTrace();
            source.sendFeedback( () -> Text.literal("Error: " + e.toString()),false);
            return 0;
        }

    }


    public LiteralCommandNode<ServerCommandSource> getNode() {
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
