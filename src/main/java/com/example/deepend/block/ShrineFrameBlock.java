package com.example.deepend.block;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;

/** Two mirrored diagonal segments connect the diamond without full cube faces. */
public final class ShrineFrameBlock extends Block {
    public static final BooleanProperty ASCENDING = BooleanProperty.create("ascending");
    public static final BooleanProperty FORK = BooleanProperty.create("fork");

    public ShrineFrameBlock(Properties properties) {
        super(properties);
        registerDefaultState(stateDefinition.any().setValue(ASCENDING, true).setValue(FORK, false));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(ASCENDING);
        builder.add(FORK);
    }
}
