package me.emafire003.dev.particleanimationlib.commands;

import com.mojang.brigadier.arguments.BoolArgumentType;
import com.mojang.brigadier.arguments.FloatArgumentType;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.tree.LiteralCommandNode;
import me.emafire003.dev.particleanimationlib.effects.LineEffect;
import net.minecraft.command.CommandRegistryAccess;
import net.minecraft.command.argument.ParticleEffectArgumentType;
import net.minecraft.command.argument.Vec3ArgumentType;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.text.Text;
import net.minecraft.util.math.Vec3d;

public class LineCommand implements PALCommand {

    private int spawnEffectTarget(CommandContext<ServerCommandSource> context) throws CommandSyntaxException {
        ServerCommandSource source = context.getSource();

        try{
            //(already written but still) write in the wiki tp use values of 0.0 instead of 0 beacuse of a minecraft bug, otherwise the game will set them to 0.5 for some reason

            if(source.getWorld().isClient()){
                return 0;
            }
            Vec3d pos = Vec3ArgumentType.getVec3(context, "origin");
            Vec3d target = Vec3ArgumentType.getVec3(context, "target");
            ParticleEffect particle = ParticleEffectArgumentType.getParticle(context, "particle");
            LineEffect effect = new LineEffect(source.getWorld(), particle, pos, target,
                    IntegerArgumentType.getInteger(context, "count"),
                    IntegerArgumentType.getInteger(context, "max_length"),
                    BoolArgumentType.getBool(context, "zigZag"),
                    IntegerArgumentType.getInteger(context, "number_of_zigZags"),
                    Vec3ArgumentType.getVec3(context, "zigZag_Offset"),
                    Vec3ArgumentType.getVec3(context, "zigZag_RelativeOffset")
            );
            effect.runFor(IntegerArgumentType.getInteger(context, "duration"));

            return 1;
        }catch(Exception e){
            e.printStackTrace();
            source.sendFeedback(Text.literal("Error: " + e),false);
            return 0;
        }
    }


    private int spawnEffectLengthNoYP(CommandContext<ServerCommandSource> context) throws CommandSyntaxException {
        ServerCommandSource source = context.getSource();

        try{
            if(source.getWorld().isClient()){
                return 0;
            }

            if(source.isExecutedByPlayer() && source.getPlayer() != null){
                Vec3d pos = Vec3ArgumentType.getVec3(context, "origin");
                ParticleEffect particle = ParticleEffectArgumentType.getParticle(context, "particle");
                LineEffect effect = new LineEffect(source.getWorld(), particle, pos, source.getPlayer().getYaw(), source.getPlayer().getPitch(),
                        IntegerArgumentType.getInteger(context, "count"),
                        IntegerArgumentType.getInteger(context, "length"),
                        BoolArgumentType.getBool(context, "zigZag"),
                        IntegerArgumentType.getInteger(context, "number_of_zigZags"),
                        Vec3ArgumentType.getVec3(context, "zigZag_Offset"),
                        Vec3ArgumentType.getVec3(context, "zigZag_RelativeOffset")
                );
                effect.runFor(IntegerArgumentType.getInteger(context, "duration"));

            }else{
                source.sendError(Text.literal("This command must be executed by a player! Try to specify the Yaw and Pitch if you are running the command from the console!"));
                return 0;
            }

            return 1;
        }catch(Exception e){
            e.printStackTrace();
            source.sendFeedback(Text.literal("Error: " + e),false);
            return 0;
        }
    }

    private int spawnEffectLength(CommandContext<ServerCommandSource> context) throws CommandSyntaxException {
        ServerCommandSource source = context.getSource();

        try{
            if(source.getWorld().isClient()){
                return 0;
            }

            if(source.isExecutedByPlayer() && source.getPlayer() != null){
                Vec3d pos = Vec3ArgumentType.getVec3(context, "origin");
                ParticleEffect particle = ParticleEffectArgumentType.getParticle(context, "particle");
                LineEffect effect = new LineEffect(source.getWorld(), particle, pos,
                        FloatArgumentType.getFloat(context, "yaw"), FloatArgumentType.getFloat(context, "pitch"),
                        IntegerArgumentType.getInteger(context, "count"),
                        IntegerArgumentType.getInteger(context, "length"),
                        BoolArgumentType.getBool(context, "zigZag"),
                        IntegerArgumentType.getInteger(context, "number_of_zigZags"),
                        Vec3ArgumentType.getVec3(context, "zigZag_Offset"),
                        Vec3ArgumentType.getVec3(context, "zigZag_RelativeOffset")
                );
                effect.runFor(IntegerArgumentType.getInteger(context, "duration"));

            }else{
                source.sendError(Text.literal("This command must be executed by a player! Try to specify the Yaw and Pitch if you are running the command from the console!"));
                return 0;
            }

            return 1;
        }catch(Exception e){
            e.printStackTrace();
            source.sendFeedback(Text.literal("Error: " + e),false);
            return 0;
        }
    }



    public LiteralCommandNode<ServerCommandSource> getNode(CommandRegistryAccess registryAccess) {
        return CommandManager
                .literal("line")
                .then(CommandManager.argument("particle", ParticleEffectArgumentType.particleEffect())
                        .then(CommandManager.argument("origin", Vec3ArgumentType.vec3())
                                .then(CommandManager.argument("target", Vec3ArgumentType.vec3())
                                        .then(CommandManager.argument("count", IntegerArgumentType.integer(0))
                                                .then(CommandManager.argument("max_length", IntegerArgumentType.integer(0))
                                                        .then(CommandManager.argument("zigZag", BoolArgumentType.bool())
                                                                .then(CommandManager.argument("number_of_zigZags", IntegerArgumentType.integer(0))
                                                                        .then(CommandManager.argument("zigZag_Offset", Vec3ArgumentType.vec3())
                                                                                .then(CommandManager.argument("zigZag_RelativeOffset", Vec3ArgumentType.vec3())
                                                                                        .then(CommandManager.argument("duration", IntegerArgumentType.integer(0))
                                                                                                .executes(this::spawnEffectTarget)
                                                                                        )
                                                                                )
                                                                        )
                                                                )
                                                        )

                                                )
                                        )
                                )
                        )

                )

                .then(CommandManager.argument("particle", ParticleEffectArgumentType.particleEffect())
                        .then(CommandManager.argument("origin", Vec3ArgumentType.vec3())
                                        .then(CommandManager.argument("count", IntegerArgumentType.integer(0))
                                                .then(CommandManager.argument("length", IntegerArgumentType.integer(0))
                                                        .then(CommandManager.argument("zigZag", BoolArgumentType.bool())
                                                                .then(CommandManager.argument("number_of_zigZags", IntegerArgumentType.integer(0))
                                                                        .then(CommandManager.argument("zigZag_Offset", Vec3ArgumentType.vec3())
                                                                                .then(CommandManager.argument("zigZag_RelativeOffset", Vec3ArgumentType.vec3())
                                                                                        .then(CommandManager.argument("duration", IntegerArgumentType.integer(0))
                                                                                                .executes(this::spawnEffectLengthNoYP)
                                                                                        )
                                                                                )
                                                                        )
                                                                )
                                                        )

                                                )
                                        )
                                )

                )
                .then(CommandManager.argument("particle", ParticleEffectArgumentType.particleEffect())
                        .then(CommandManager.argument("origin", Vec3ArgumentType.vec3())
                                .then(CommandManager.argument("yaw", FloatArgumentType.floatArg())
                                        .then(CommandManager.argument("pitch", FloatArgumentType.floatArg())
                                                .then(CommandManager.argument("count", IntegerArgumentType.integer(0))
                                                        .then(CommandManager.argument("length", IntegerArgumentType.integer(0))
                                                                .then(CommandManager.argument("zigZag", BoolArgumentType.bool())
                                                                        .then(CommandManager.argument("number_of_zigZags", IntegerArgumentType.integer(0))
                                                                                .then(CommandManager.argument("zigZag_Offset", Vec3ArgumentType.vec3())
                                                                                        .then(CommandManager.argument("zigZag_RelativeOffset", Vec3ArgumentType.vec3())
                                                                                                .then(CommandManager.argument("duration", IntegerArgumentType.integer(0))
                                                                                                        .executes(this::spawnEffectLength)
                                                                                                )
                                                                                        )
                                                                                )
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
