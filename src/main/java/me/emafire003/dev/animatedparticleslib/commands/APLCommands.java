package me.emafire003.dev.animatedparticleslib.commands;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.tree.LiteralCommandNode;
import net.minecraft.command.CommandRegistryAccess;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;

public class APLCommands {

    //Based on Factions' code https://github.com/ickerio/factions
    public static void registerCommands(CommandDispatcher<ServerCommandSource> dispatcher, CommandRegistryAccess registryAccess, CommandManager.RegistrationEnvironment environment) {
        LiteralCommandNode<ServerCommandSource> lightcommands = CommandManager
                .literal("apl")
                .build();


        dispatcher.getRoot().addChild(lightcommands);

        APLCommand[] commands = new APLCommand[] {
                new APLDebugCommand()
        };

        for (APLCommand command : commands) {
            lightcommands.addChild(command.getNode());
        }
    }
}
