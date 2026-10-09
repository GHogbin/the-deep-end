package com.example.deepend.block;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.Level;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import com.example.deepend.registry.DeepEndItems;

public class ObservationShrineBlock extends Block {
    public ObservationShrineBlock(BlockBehaviour.Properties properties) { super(properties); }

    @Override
    protected InteractionResult useItemOn(ItemStack stack, BlockState state, Level level, BlockPos pos,
                                          Player player, InteractionHand hand, BlockHitResult hit) {
        if (!stack.is(DeepEndItems.RESONANT_CRYSTAL.get())) return InteractionResult.PASS;
        if (!level.isClientSide()) {
            stack.shrink(1);
            level.playSound(null, pos, SoundEvents.AMETHYST_BLOCK_CHIME, SoundSource.BLOCKS, 1.0F, 0.7F);
            if (level instanceof ServerLevel server)
                server.sendParticles(ParticleTypes.END_ROD, pos.getX() + 0.5, pos.getY() + 1.1,
                        pos.getZ() + 0.5, 24, 0.35, 0.3, 0.35, 0.02);
            player.sendSystemMessage(Component.literal("The shrine awakens. Its symbols align toward the far void."));
            player.sendSystemMessage(Component.literal("Lore fragment: The islands were once connected by roads of light."));
            player.sendSystemMessage(Component.literal("A distant signal answers from farther outward."));
        }
        return InteractionResult.SUCCESS;
    }

}
