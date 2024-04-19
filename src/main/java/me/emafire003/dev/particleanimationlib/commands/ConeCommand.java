package me.emafire003.dev.particleanimationlib.commands;

import com.mojang.brigadier.arguments.BoolArgumentType;
import com.mojang.brigadier.arguments.DoubleArgumentType;
import com.mojang.brigadier.arguments.FloatArgumentType;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.tree.LiteralCommandNode;
import me.emafire003.dev.particleanimationlib.effects.ConeEffect;
import net.minecraft.command.CommandRegistryAccess;
import net.minecraft.command.argument.ParticleEffectArgumentType;
import net.minecraft.command.argument.Vec3ArgumentType;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.text.Text;
import net.minecraft.util.math.Vec3d;

public class ConeCommand implements PALCommand {

    private int spawnEffectNoYP(CommandContext<ServerCommandSource> context) throws CommandSyntaxException {
        ServerCommandSource source = context.getSource();

        try{
            if(source.isExecutedByPlayer() && source.getPlayer() != null){
                Vec3d pos = Vec3ArgumentType.getVec3(context, "originPos");
                ParticleEffect particle = ParticleEffectArgumentType.getParticle(context, "particle");
                ConeEffect effect = new ConeEffect(source.getWorld(), particle, pos,
                        source.getPlayer().getYaw(), source.getPlayer().getPitch(), IntegerArgumentType.getInteger(context, "count"),
                        IntegerArgumentType.getInteger(context, "particles_per_iteration"), IntegerArgumentType.getInteger(context, "strands"),
                        FloatArgumentType.getFloat(context, "lengthGrow"), FloatArgumentType.getFloat(context, "radiusGrow"),
                        DoubleArgumentType.getDouble(context, "angularVelocity"), DoubleArgumentType.getDouble(context, "startingRotation"),
                        BoolArgumentType.getBool(context, "solid"), BoolArgumentType.getBool(context, "randomStart")
                );
                effect.setFlipped(BoolArgumentType.getBool(context, "inverted"));
                effect.runFor(IntegerArgumentType.getInteger(context, "duration"));

            }else{
                source.sendError(Text.literal("This command must be executed by a player! Try to specify the Yaw and Pitch if you are running the command from the console!"));
                return 0;
            }

            return 1;
        }catch(Exception e){
            e.printStackTrace();
            source.sendFeedback( () -> Text.literal("Error: " + e),false);
            return 0;
        }
    }

    private int spawnEffect(CommandContext<ServerCommandSource> context) throws CommandSyntaxException {
        ServerCommandSource source = context.getSource();

        try{
            Vec3d pos = Vec3ArgumentType.getVec3(context, "originPos");
            ParticleEffect particle = ParticleEffectArgumentType.getParticle(context, "particle");
            ConeEffect effect = new ConeEffect(source.getWorld(), particle, pos,
                    FloatArgumentType.getFloat(context, "yaw"), FloatArgumentType.getFloat(context, "pitch"), IntegerArgumentType.getInteger(context, "count"),
                    IntegerArgumentType.getInteger(context, "particles_per_iteration"), IntegerArgumentType.getInteger(context, "strands"),
                    FloatArgumentType.getFloat(context, "lengthGrow"), FloatArgumentType.getFloat(context, "radiusGrow"),
                    DoubleArgumentType.getDouble(context, "angularVelocity"), DoubleArgumentType.getDouble(context, "startingRotation"),
                    BoolArgumentType.getBool(context, "solid"), BoolArgumentType.getBool(context, "randomStart")
            );
            effect.setFlipped(BoolArgumentType.getBool(context, "inverted"));
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
                .literal("cone")
                .then(CommandManager.argument("particle", ParticleEffectArgumentType.particleEffect(registryAccess))
                        .then(CommandManager.argument("originPos", Vec3ArgumentType.vec3())
                                .then(CommandManager.argument("count", IntegerArgumentType.integer(0))
                                        .then(CommandManager.argument("particles_per_iteration", IntegerArgumentType.integer(0))
                                                .then(CommandManager.argument("strands", IntegerArgumentType.integer())
                                                        .then(CommandManager.argument("lengthGrow", FloatArgumentType.floatArg(0))
                                                                .then(CommandManager.argument("radiusGrow", FloatArgumentType.floatArg(0))
                                                                        .then(CommandManager.argument("angularVelocity", DoubleArgumentType.doubleArg())
                                                                                .then(CommandManager.argument("startingRotation", DoubleArgumentType.doubleArg())
                                                                                        .then(CommandManager.argument("solid", BoolArgumentType.bool())
                                                                                                .then(CommandManager.argument("randomStart", BoolArgumentType.bool())
                                                                                                        .then(CommandManager.argument("inverted", BoolArgumentType.bool())
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
                .then(CommandManager.argument("particle", ParticleEffectArgumentType.particleEffect(registryAccess))
                        .then(CommandManager.argument("originPos", Vec3ArgumentType.vec3())
                                .then(CommandManager.argument("yaw", FloatArgumentType.floatArg())
                                        .then(CommandManager.argument("pitch", FloatArgumentType.floatArg())
                                                .then(CommandManager.argument("count", IntegerArgumentType.integer(0))
                                                        .then(CommandManager.argument("particles_per_iteration", IntegerArgumentType.integer(0))
                                                                .then(CommandManager.argument("strands", IntegerArgumentType.integer())
                                                                        .then(CommandManager.argument("lengthGrow", FloatArgumentType.floatArg(0))
                                                                                .then(CommandManager.argument("radiusGrow", FloatArgumentType.floatArg(0))
                                                                                        .then(CommandManager.argument("angularVelocity", DoubleArgumentType.doubleArg())
                                                                                                .then(CommandManager.argument("startingRotation", DoubleArgumentType.doubleArg())
                                                                                                        .then(CommandManager.argument("solid", BoolArgumentType.bool())
                                                                                                                .then(CommandManager.argument("randomStart", BoolArgumentType.bool())
                                                                                                                        .then(CommandManager.argument("inverted", BoolArgumentType.bool())
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
