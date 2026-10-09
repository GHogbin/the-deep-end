package com.example.deepend.item;

import com.example.deepend.world.EndRegion;
import com.example.deepend.world.EndRegionSample;
import com.example.deepend.world.EndRegionSampler;
import com.example.deepend.world.NaturalShrineGeneration;
import com.example.deepend.registry.DeepEndBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class ResonanceLensItem extends Item {
    public ResonanceLensItem(Properties properties) { super(properties); }

    @Override
    public InteractionResult use(Level level, net.minecraft.world.entity.player.Player player, InteractionHand hand) {
        ItemStack stack = player.getItemInHand(hand);
        if (!level.isClientSide() && player instanceof ServerPlayer serverPlayer && level.dimension() == Level.END) {
            EndRegionSample sample = EndRegionSampler.sample(serverPlayer.level(), serverPlayer.blockPosition());
            BlockPos shrine = findShrine(serverPlayer, 24);
            BlockPos candidate = NaturalShrineGeneration.nearestCandidate(serverPlayer.blockPosition());
            String signal = shrine != null ? "The Lens locks onto an Observation Shrine at "
                    + shrine.getX() + ", " + shrine.getY() + ", " + shrine.getZ()
                    + ". Its builders were looking outward."
                    : candidate != null ? "The Lens points toward a dormant shrine site at "
                    + candidate.getX() + ", " + candidate.getZ() + " (about "
                    + Math.round(Math.sqrt(serverPlayer.blockPosition().distSqr(candidate))) + " blocks away)."
                    : switch (sample.region()) {
                case DRAGON_ISLAND, OUTER_END -> "The lens is silent. The distant ruins remain dormant.";
                case FRINGE -> "A faint harmonic signal answers from beyond the islands.";
                case DEEP_END -> "The lens vibrates violently. Something ancient is close.";
                case ABYSS -> "The lens sees no distance at all—only a pressure beneath the void.";
            };
            serverPlayer.sendSystemMessage(Component.literal(signal));
            stack.hurtAndBreak(1, serverPlayer, hand);
        }
        return InteractionResult.SUCCESS;
    }

    private static BlockPos findShrine(ServerPlayer player, int radius) {
        BlockPos origin = player.blockPosition();
        for (BlockPos pos : BlockPos.betweenClosed(origin.offset(-radius, -radius, -radius),
                origin.offset(radius, radius, radius))) {
            if (player.level().getBlockState(pos).is(DeepEndBlocks.OBSERVATION_SHRINE.get())) return pos.immutable();
        }
        return null;
    }
}
