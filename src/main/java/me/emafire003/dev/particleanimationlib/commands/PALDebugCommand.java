package me.emafire003.dev.particleanimationlib.commands;

import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.tree.LiteralCommandNode;
import me.emafire003.dev.particleanimationlib.effects.ConeEffect;
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
                    200, 20, 1, 0.03f, 0.005f,
                Math.PI/8, 0, false, true);
                coneEffect.setPitch(target.getPitch());
                coneEffect.setYaw(target.getYaw());
                coneEffect.setEntityOrigin(target);
                coneEffect.setOriginOffset(new Vec3d(0, 0.8, 0));
                coneEffect.setUpdatePositions(true);
                coneEffect.runFor(5);

                /*ArcEffect arcEffect = new ArcEffect(source.getWorld(), ParticleTypes.ENCHANTED_HIT, target.getPos(), target.getPos().add(5,0,0), 150, -5);
                arcEffect.setCenteredOriginEntity(target);
                arcEffect.setUpdatePositions(true);
                arcEffect.runFor(5);*/

                /*VortexEffect vortexEffect = new VortexEffect(source.getWorld(), ParticleTypes.WITCH, target.getPos().add(0, 1,0),
                        target.getYaw(), target.getPitch(),
                    0.7f,0.0005f, 0.1f, 0.0f,
                        Math.PI/16, 50, 10);

                vortexEffect.setIterations(150);
                vortexEffect.run();*/
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
