package com.example.deepend;

import com.example.deepend.world.EndRegionSampler;
import com.example.deepend.world.ObservationShrineStructure;
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
                            if (!ObservationShrineStructure.place(player.level(), pos)) {
                                player.sendSystemMessage(Component.literal("Shrine needs an empty, loaded 15 × 18 × 15 area, centred 10 blocks south of you. Nothing was changed."));
                                return 0;
                            }
                            player.sendSystemMessage(Component.literal("Observation Shrine built 10 blocks south. Activate the low front pedestal with Resonant Crystal."));
                            return 1;
                        })));
    }
}
