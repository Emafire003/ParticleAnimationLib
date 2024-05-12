package me.emafire003.dev.particleanimationlib.commands;

import com.mojang.brigadier.arguments.BoolArgumentType;
import com.mojang.brigadier.arguments.FloatArgumentType;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.tree.LiteralCommandNode;
import me.emafire003.dev.particleanimationlib.effects.image.ColoredImageEffect;
import net.minecraft.command.CommandRegistryAccess;
import net.minecraft.command.argument.Vec3ArgumentType;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.text.Text;
import net.minecraft.util.math.Vec3d;

public class ColoredImageCommand implements PALCommand {

    private int spawnEffect(CommandContext<ServerCommandSource> context) throws CommandSyntaxException {
        ServerCommandSource source = context.getSource();

        try{

            Vec3d vel = Vec3ArgumentType.getVec3(context, "angularVelocity");
            ColoredImageEffect imageEffect = ColoredImageEffect
                    .builder(source.getWorld(), Vec3ArgumentType.getVec3(context, "originPos"), StringArgumentType.getString(context, "filePath"))
                    .transparency(BoolArgumentType.getBool(context, "transparent"))
                    .scale(FloatArgumentType.getFloat(context, "scale"))
                    .orient(BoolArgumentType.getBool(context, "orientPlayer"))
                    .rotation(Vec3ArgumentType.getVec3(context, "fixedRotation"))
                    .enableRotation(BoolArgumentType.getBool(context, "enableDynamicRotation"))
                    .angularVelocityX(vel.x).angularVelocityY(vel.y).angularVelocityZ(vel.z)
                    .particleSize(FloatArgumentType.getFloat(context, "particleSize"))
                    .build();
            imageEffect.runFor(IntegerArgumentType.getInteger(context, "duration"));
            return 1;
        }catch(Exception e){
            e.printStackTrace();
            source.sendFeedback( () -> Text.literal("Error: " + e),false);
            return 0;
        }
    }


    public LiteralCommandNode<ServerCommandSource> getNode(CommandRegistryAccess registryAccess) {
        return CommandManager
                .literal("coloredImage")
                .then(CommandManager.argument("filePath", StringArgumentType.string())
                        .then(CommandManager.argument("originPos", Vec3ArgumentType.vec3())
                                .then(CommandManager.argument("transparent", BoolArgumentType.bool())
                                        .then(CommandManager.argument("scale", FloatArgumentType.floatArg())
                                                .then(CommandManager.argument("particleSize", FloatArgumentType.floatArg(0))
                                                        .then(CommandManager.argument("orientPlayer", BoolArgumentType.bool())
                                                                .then(CommandManager.argument("fixedRotation", Vec3ArgumentType.vec3())
                                                                        .then(CommandManager.argument("enableDynamicRotation", BoolArgumentType.bool())
                                                                                .then(CommandManager.argument("angularVelocity", Vec3ArgumentType.vec3())
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
                .build();
    }
}
