package me.emafire003.dev.particleanimationlib.commands;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.tree.LiteralCommandNode;
import net.minecraft.command.CommandRegistryAccess;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;

public class PALCommands {

    //Based on Factions' code https://github.com/ickerio/factions
    public static void registerCommands(CommandDispatcher<ServerCommandSource> dispatcher, CommandRegistryAccess registryAccess, CommandManager.RegistrationEnvironment environment) {
        LiteralCommandNode<ServerCommandSource> pal_commands = CommandManager
                .literal("pal")
                .build();


        dispatcher.getRoot().addChild(pal_commands);

        PALCommand[] commands = new PALCommand[] {
                new PALDebugCommand()
        };

        for (PALCommand command : commands) {
            pal_commands.addChild(command.getNode());
        }
    }
}
