package com.example.deepend.block;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;

/** Small caps and one continuous three-block-high centerpiece. */
public final class ShrineCrystalBlock extends Block {
    public static final BooleanProperty LARGE = BooleanProperty.create("large");
    public ShrineCrystalBlock(Properties properties) {
        super(properties);
        registerDefaultState(stateDefinition.any().setValue(LARGE, false));
    }
    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(LARGE);
    }
}
