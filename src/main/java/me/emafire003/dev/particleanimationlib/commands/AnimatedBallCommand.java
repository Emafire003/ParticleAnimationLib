package me.emafire003.dev.particleanimationlib.commands;

import com.mojang.brigadier.arguments.FloatArgumentType;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.tree.LiteralCommandNode;
import me.emafire003.dev.particleanimationlib.effects.AnimatedBallEffect;
import net.minecraft.command.CommandRegistryAccess;
import net.minecraft.command.argument.ParticleEffectArgumentType;
import net.minecraft.command.argument.Vec3ArgumentType;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.text.Text;
import net.minecraft.util.math.Vec3d;

public class AnimatedBallCommand implements PALCommand {

    private int spawnDefault(CommandContext<ServerCommandSource> context) throws CommandSyntaxException {
        ServerCommandSource source = context.getSource();

        try{
            Vec3d pos = Vec3ArgumentType.getVec3(context, "pos");
            ParticleEffect particle = ParticleEffectArgumentType.getParticle(context, "particle");
            AnimatedBallEffect effect = new AnimatedBallEffect(source.getWorld(), particle, pos);
            effect.runFor(IntegerArgumentType.getInteger(context, "duration"));

            return 1;
        }catch(Exception e){
            e.printStackTrace();
            source.sendFeedback(Text.literal("Error: " + e),false);
            return 0;
        }
    }

    private int spawnSize(CommandContext<ServerCommandSource> context) throws CommandSyntaxException {
        ServerCommandSource source = context.getSource();

        try{
            Vec3d pos = Vec3ArgumentType.getVec3(context, "pos");
            ParticleEffect particle = ParticleEffectArgumentType.getParticle(context, "particle");
            AnimatedBallEffect effect = new AnimatedBallEffect(source.getWorld(), particle, pos,
                    IntegerArgumentType.getInteger(context, "count"), IntegerArgumentType.getInteger(context, "perIteration"),
                    FloatArgumentType.getFloat(context, "size"));
            effect.runFor(IntegerArgumentType.getInteger(context, "duration"));

            return 1;
        }catch(Exception e){
            e.printStackTrace();
            source.sendFeedback(Text.literal("Error: " + e),false);
            return 0;
        }
    }

    private int spawnAll(CommandContext<ServerCommandSource> context) throws CommandSyntaxException {
        ServerCommandSource source = context.getSource();

        try{
            Vec3d factors = Vec3ArgumentType.getVec3(context, "factors");
            Vec3d rotations = Vec3ArgumentType.getVec3(context, "rotation");

            Vec3d pos = Vec3ArgumentType.getVec3(context, "pos");
            ParticleEffect particle = ParticleEffectArgumentType.getParticle(context, "particle");
            AnimatedBallEffect effect = new AnimatedBallEffect(source.getWorld(), particle, pos,
                    IntegerArgumentType.getInteger(context, "count"), IntegerArgumentType.getInteger(context, "perIteration"),
                    FloatArgumentType.getFloat(context, "size"),
                    factors, rotations);
            effect.runFor(IntegerArgumentType.getInteger(context, "duration"));

            return 1;
        }catch(Exception e){
            e.printStackTrace();
            source.sendFeedback(Text.literal("Error: " + e),false);
            return 0;
        }
    }


    public LiteralCommandNode<ServerCommandSource> getNode(CommandRegistryAccess registryAccess) {
        return CommandManager
                .literal("animatedball")
                .then(CommandManager.argument("particle", ParticleEffectArgumentType.particleEffect())
                        .then(CommandManager.argument("pos", Vec3ArgumentType.vec3())
                                .then(CommandManager.argument("duration", IntegerArgumentType.integer(0))
                                        .executes(this::spawnDefault)
                                )

                        )

                )
                .then(CommandManager.argument("particle", ParticleEffectArgumentType.particleEffect())
                        .then(CommandManager.argument("pos", Vec3ArgumentType.vec3())
                                .then(CommandManager.argument("count", IntegerArgumentType.integer())
                                        .then(CommandManager.argument("perIteration", IntegerArgumentType.integer(0))
                                                .then(CommandManager.argument("size", FloatArgumentType.floatArg(0))
                                                        .then(CommandManager.argument("duration", IntegerArgumentType.integer(0))
                                                                .executes(this::spawnSize)
                                                        )
                                                )
                                        )
                                )
                        )
                )
                .then(CommandManager.argument("particle", ParticleEffectArgumentType.particleEffect())
                        .then(CommandManager.argument("pos", Vec3ArgumentType.vec3())
                                .then(CommandManager.argument("count", IntegerArgumentType.integer())
                                        .then(CommandManager.argument("perIteration", IntegerArgumentType.integer(0))
                                                .then(CommandManager.argument("size", FloatArgumentType.floatArg(0))
                                                        .then(CommandManager.argument("factors", Vec3ArgumentType.vec3())
                                                                .then(CommandManager.argument("rotation", Vec3ArgumentType.vec3())
                                                                        .then(CommandManager.argument("duration", IntegerArgumentType.integer(0))
                                                                                .executes(this::spawnAll)
                                                                        )
                                                                )
                                                        )
                                                )
                                        )
                                )
                        )
                )
                .build();
    }
}
