package me.emafire003.dev.particleanimationlib.commands;

import com.mojang.brigadier.arguments.BoolArgumentType;
import com.mojang.brigadier.arguments.DoubleArgumentType;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.tree.LiteralCommandNode;
import me.emafire003.dev.particleanimationlib.effects.CuboidEffect;
import net.minecraft.command.CommandRegistryAccess;
import net.minecraft.command.argument.ParticleEffectArgumentType;
import net.minecraft.command.argument.Vec3ArgumentType;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.text.Text;
import net.minecraft.util.math.Vec3d;

public class CuboidCommand implements PALCommand {

    private int spawnEffect(CommandContext<ServerCommandSource> context) throws CommandSyntaxException {
        ServerCommandSource source = context.getSource();

        try{
            //TODO write in the wiki tp use values of 0.0 instead of 0 beacuse of a minecraft bug, otherwise the game will set them to 0.5 for some reason

            if(source.getWorld().isClient()){
                return 0;
            }
            Vec3d pos = Vec3ArgumentType.getVec3(context, "origin");
            Vec3d target = Vec3ArgumentType.getVec3(context, "target");
            ParticleEffect particle = ParticleEffectArgumentType.getParticle(context, "particle");
            if(BoolArgumentType.getBool(context, "useCorners")){
                CuboidEffect effect = new CuboidEffect(source.getWorld(), particle, pos, target,
                        IntegerArgumentType.getInteger(context, "particles_per_row"),
                        DoubleArgumentType.getDouble(context, "padding"),
                        BoolArgumentType.getBool(context, "blockSnap"));
                effect.runFor(IntegerArgumentType.getInteger(context, "duration"));
            }else{
                CuboidEffect effect = new CuboidEffect(source.getWorld(), particle, pos, IntegerArgumentType.getInteger(context, "particles_per_row"),
                        target.getX(), target.getY(), target.getZ(), // the lengths of the cuboid
                        DoubleArgumentType.getDouble(context, "padding"),
                        BoolArgumentType.getBool(context, "blockSnap"));
                effect.runFor(IntegerArgumentType.getInteger(context, "duration"));
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
                .literal("cuboid")
                .then(CommandManager.argument("particle", ParticleEffectArgumentType.particleEffect(registryAccess))
                        .then(CommandManager.argument("origin", Vec3ArgumentType.vec3())
                                .then(CommandManager.argument("target", Vec3ArgumentType.vec3())
                                        .then(CommandManager.argument("useCorners", BoolArgumentType.bool())
                                                .then(CommandManager.argument("particles_per_row", IntegerArgumentType.integer(0))
                                                        .then(CommandManager.argument("padding", DoubleArgumentType.doubleArg())
                                                                .then(CommandManager.argument("blockSnap", BoolArgumentType.bool())
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
                .build();
    }
}
