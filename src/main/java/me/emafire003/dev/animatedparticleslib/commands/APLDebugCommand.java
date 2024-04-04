package me.emafire003.dev.animatedparticleslib.commands;

import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.tree.LiteralCommandNode;
import me.emafire003.dev.animatedparticleslib.effects.VortexEffect;
import net.minecraft.command.argument.EntityArgumentType;
import net.minecraft.entity.Entity;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.text.Text;

import java.util.Collection;

public class APLDebugCommand implements APLCommand {

    private int particleEffect(CommandContext<ServerCommandSource> context) throws CommandSyntaxException {
        Collection<? extends Entity> targets = EntityArgumentType.getEntities(context, "target");
        ServerCommandSource source = context.getSource();

        try{
            for(Entity target : targets){
                //TODO spawn the effect

                VortexEffect vortexEffect = new VortexEffect(source.getWorld(), ParticleTypes.WITCH, target.getPos().add(0, 1,0),
                        target.getYaw(), target.getPitch(),
                    0.7f,0.0005f, 0.1f, 0.0f,
                        Math.PI/16, 50, 10);

                vortexEffect.setIterations(150);
                vortexEffect.run();

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
