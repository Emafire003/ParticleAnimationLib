package me.emafire003.dev.particleanimationlib.commands;

import com.mojang.brigadier.arguments.FloatArgumentType;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.tree.LiteralCommandNode;
import me.emafire003.dev.particleanimationlib.effects.ArcEffect;
import net.minecraft.command.CommandRegistryAccess;
import net.minecraft.command.argument.ParticleEffectArgumentType;
import net.minecraft.command.argument.Vec3ArgumentType;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.text.Text;
import net.minecraft.util.math.Vec3d;

public class ArcCommand implements PALCommand {

    private int spawnEffect(CommandContext<ServerCommandSource> context) throws CommandSyntaxException {
        ServerCommandSource source = context.getSource();

        try{

            Vec3d pos = Vec3ArgumentType.getVec3(context, "originPos");
            Vec3d target = Vec3ArgumentType.getVec3(context, "targetPos");
            ParticleEffect particle = ParticleEffectArgumentType.getParticle(context, "particle");
            ArcEffect effect = new ArcEffect(source.getWorld(), particle, pos, target, IntegerArgumentType.getInteger(context,"count"), FloatArgumentType.getFloat(context,"height"));
            effect.runFor(IntegerArgumentType.getInteger(context, "duration"));

            return 1;
        }catch(Exception e){
            e.printStackTrace();
            source.sendFeedback( () -> Text.literal("Error: " + e),false);
            return 0;
        }
    }


    public LiteralCommandNode<ServerCommandSource> getNode(CommandRegistryAccess registryAccess) {
        return CommandManager
                .literal("arc")
                .then(CommandManager.argument("particle", ParticleEffectArgumentType.particleEffect(registryAccess))
                        .then(CommandManager.argument("originPos", Vec3ArgumentType.vec3())
                                .then(CommandManager.argument("targetPos", Vec3ArgumentType.vec3())
                                        .then(CommandManager.argument("count", IntegerArgumentType.integer(0))
                                                .then(CommandManager.argument("height", FloatArgumentType.floatArg())
                                                        .then(CommandManager.argument("duration", IntegerArgumentType.integer(0))
                                                                .executes(this::spawnEffect)
                                                        )

                                                )
                                        )

                                )

                        )

                )
                .build();
    }
}
