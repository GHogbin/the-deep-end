package com.example.deepend;

import com.example.deepend.world.EndRegionSampler;
import com.example.deepend.world.ObservationShrineStructure;
import com.example.deepend.world.ShrineTransformation;
import com.example.deepend.registry.DeepEndBlocks;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.core.BlockPos;
import net.minecraftforge.event.RegisterCommandsEvent;

public final class DeepEndCommands {
    private DeepEndCommands() {}

    public static void register(com.mojang.brigadier.CommandDispatcher<net.minecraft.commands.CommandSourceStack> dispatcher) {
        dispatcher.register(Commands.literal("deepend")
                .then(Commands.literal("region")
                        .executes(context -> {
                            ServerPlayer player = context.getSource().getPlayerOrException();
                            var sample = EndRegionSampler.sample(player.level(), player.blockPosition());
                            player.sendSystemMessage(Component.literal("Region: " + sample.region().id()
                                    + " | distance: " + Math.round(sample.distance())));
                            return 1;
                        }))
                .then(Commands.literal("shrine")
                        .requires(source -> source.isPlayer())
                        .executes(context -> {
                            ServerPlayer player = context.getSource().getPlayerOrException();
                            BlockPos pos = player.blockPosition().offset(0, 0, 10);
                            var result = ShrineTransformation.placeFinished(player.level(), pos);
                            player.sendSystemMessage(Component.literal(result.success()
                                    ? result.message() + " Sneak-right-click the pedestal with Resonant Crystal to restore the repairable block build."
                                    : result.message()));
                            return result.success() ? 1 : 0;
                        })));
    }
}
