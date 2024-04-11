package me.emafire003.dev.particleanimationlib.commands;

import com.mojang.brigadier.tree.LiteralCommandNode;
import net.minecraft.command.CommandRegistryAccess;
import net.minecraft.server.command.ServerCommandSource;


//Based on Factions' code https://github.com/ickerio/factions (MIT license)
public interface PALCommand {
    LiteralCommandNode<ServerCommandSource> getNode(CommandRegistryAccess registryAccess);

}
