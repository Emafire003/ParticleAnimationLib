package me.emafire003.dev.particleanimationlib.commands;

import com.mojang.brigadier.arguments.BoolArgumentType;
import com.mojang.brigadier.arguments.DoubleArgumentType;
import com.mojang.brigadier.arguments.FloatArgumentType;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.tree.LiteralCommandNode;
import me.emafire003.dev.particleanimationlib.effects.AnimatedCircleEffect;
import net.minecraft.command.CommandRegistryAccess;
import net.minecraft.command.argument.ParticleEffectArgumentType;
import net.minecraft.command.argument.Vec3ArgumentType;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.text.Text;
import net.minecraft.util.math.Vec3d;

public class AnimatedCircleCommand implements PALCommand {

    private int spawnEffectNoYP(CommandContext<ServerCommandSource> context) throws CommandSyntaxException {
        ServerCommandSource source = context.getSource();

        try{
            if(source.isExecutedByPlayer() && source.getPlayer() != null){
                Vec3d pos = Vec3ArgumentType.getVec3(context, "originPos");
                ParticleEffect particle = ParticleEffectArgumentType.getParticle(context, "particle");
                AnimatedCircleEffect effect = new AnimatedCircleEffect(source.getWorld(), particle, pos,
                        source.getPlayer().getYaw(), source.getPlayer().getPitch(),
                        IntegerArgumentType.getInteger(context, "count"), FloatArgumentType.getFloat(context, "radius"),
                        FloatArgumentType.getFloat(context, "radiusGrow"), DoubleArgumentType.getDouble(context, "maxAngle"),
                        BoolArgumentType.getBool(context, "wholeCircle"),BoolArgumentType.getBool(context, "resetCircle"),
                        BoolArgumentType.getBool(context, "enableRotation"), Vec3ArgumentType.getVec3(context, "angularVelocity"),
                        Vec3ArgumentType.getVec3(context, "rotations"), Vec3ArgumentType.getVec3(context, "subtractFromOrigin")

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

    private int spawnEffect(CommandContext<ServerCommandSource> context) throws CommandSyntaxException {
        ServerCommandSource source = context.getSource();

        //nt particles_per_circle, float radius, float radiusGrow, double maxAngle, boolean wholeCircle, boolean resetCircle, boolean enableRotation,
        //     * Vec3d angularVelocity, Vec3d rotations, Vec3d subtractFromOrigin
        try{
            Vec3d pos = Vec3ArgumentType.getVec3(context, "originPos");
            ParticleEffect particle = ParticleEffectArgumentType.getParticle(context, "particle");
            AnimatedCircleEffect effect = new AnimatedCircleEffect(source.getWorld(), particle, pos,
                    FloatArgumentType.getFloat(context, "yaw"), FloatArgumentType.getFloat(context, "pitch"),
                    IntegerArgumentType.getInteger(context, "count"), FloatArgumentType.getFloat(context, "radius"),
                    FloatArgumentType.getFloat(context, "radiusGrow"), DoubleArgumentType.getDouble(context, "maxAngle"),
                    BoolArgumentType.getBool(context, "wholeCircle"),BoolArgumentType.getBool(context, "resetCircle"),
                    BoolArgumentType.getBool(context, "enableRotation"), Vec3ArgumentType.getVec3(context, "angularVelocity"),
                    Vec3ArgumentType.getVec3(context, "rotations"), Vec3ArgumentType.getVec3(context, "subtractFromOrigin")

            );
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
                .literal("animatedcircle")
                .then(CommandManager.argument("particle", ParticleEffectArgumentType.particleEffect())
                        .then(CommandManager.argument("originPos", Vec3ArgumentType.vec3())
                                .then(CommandManager.argument("count", IntegerArgumentType.integer(0))
                                        .then(CommandManager.argument("radius", FloatArgumentType.floatArg())
                                                .then(CommandManager.argument("radiusGrow", FloatArgumentType.floatArg())
                                                        .then(CommandManager.argument("maxAngle", DoubleArgumentType.doubleArg())
                                                                .then(CommandManager.argument("wholeCircle", BoolArgumentType.bool())
                                                                        .then(CommandManager.argument("resetCircle", BoolArgumentType.bool())
                                                                                .then(CommandManager.argument("enableRotation", BoolArgumentType.bool())
                                                                                        .then(CommandManager.argument("angularVelocity", Vec3ArgumentType.vec3())
                                                                                                .then(CommandManager.argument("rotations", Vec3ArgumentType.vec3())
                                                                                                        .then(CommandManager.argument("subtractFromOrigin", Vec3ArgumentType.vec3())
                                                                                                                .then(CommandManager.argument("duration", IntegerArgumentType.integer(0))
                                                                                                                        .executes(this::spawnEffectNoYP)
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

                        )

                )
                .then(CommandManager.argument("particle", ParticleEffectArgumentType.particleEffect())
                        .then(CommandManager.argument("originPos", Vec3ArgumentType.vec3())
                                .then(CommandManager.argument("yaw", FloatArgumentType.floatArg())
                                        .then(CommandManager.argument("pitch", FloatArgumentType.floatArg())
                                                .then(CommandManager.argument("count", IntegerArgumentType.integer(0))
                                                        .then(CommandManager.argument("radius", FloatArgumentType.floatArg())
                                                                .then(CommandManager.argument("radiusGrow", FloatArgumentType.floatArg())
                                                                        .then(CommandManager.argument("maxAngle", DoubleArgumentType.doubleArg())
                                                                                .then(CommandManager.argument("wholeCircle", BoolArgumentType.bool())
                                                                                        .then(CommandManager.argument("resetCircle", BoolArgumentType.bool())
                                                                                                .then(CommandManager.argument("enableRotation", BoolArgumentType.bool())
                                                                                                        .then(CommandManager.argument("angularVelocity", Vec3ArgumentType.vec3())
                                                                                                                .then(CommandManager.argument("rotations", Vec3ArgumentType.vec3())
                                                                                                                        .then(CommandManager.argument("subtractFromOrigin", Vec3ArgumentType.vec3())
                                                                                                                                .then(CommandManager.argument("duration", IntegerArgumentType.integer(0))
                                                                                                                                        .executes(this::spawnEffect)
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
                                        )
                                )

                        )

                )
                .build();
    }
}
