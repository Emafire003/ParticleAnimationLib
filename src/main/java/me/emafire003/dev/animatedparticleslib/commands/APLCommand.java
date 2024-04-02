package me.emafire003.dev.animatedparticleslib.commands;

import com.mojang.brigadier.tree.LiteralCommandNode;
import net.minecraft.server.command.ServerCommandSource;


//Based on Factions' code https://github.com/ickerio/factions (MIT license)
public interface APLCommand {
    LiteralCommandNode<ServerCommandSource> getNode();

}
