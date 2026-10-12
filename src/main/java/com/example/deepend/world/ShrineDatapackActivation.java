package com.example.deepend.world;

import com.example.deepend.registry.DeepEndItems;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

/** Server-side bridge from the shrine block to the datapack-owned structure functions. */
public final class ShrineDatapackActivation {
    private ShrineDatapackActivation() {}

    public record Result(boolean success, String message) {}

    public static Result activate(ServerLevel level, BlockPos pos, Player player, ItemStack catalyst) {
        if (level.dimension() != Level.END)
            return new Result(false, "The shrine only answers in the End.");
        if (!(player instanceof ServerPlayer serverPlayer))
            return new Result(false, "Only a server player can activate the shrine.");
        if (!hasItem(player, DeepEndItems.CHORUS_FIBRE.get()))
            return new Result(false, "The shrine needs Chorus Fibre as a second resonance.");
        if (!hasItem(player, DeepEndItems.RESONANCE_LENS.get()))
            return new Result(false, "The shrine needs a Resonance Lens to align the signal.");

        String command = "execute in minecraft:the_end positioned "
                + pos.getX() + " " + pos.getY() + " " + pos.getZ()
                + " run function deep_end:shrine/activate";
        try {
            level.getServer().getCommands().performPrefixedCommand(
                    serverPlayer.createCommandSourceStack(), command);
        } catch (RuntimeException exception) {
            return new Result(false, "The shrine structure could not be placed. Nothing was consumed.");
        }

        consume(player, DeepEndItems.CHORUS_FIBRE.get());
        consume(player, DeepEndItems.RESONANCE_LENS.get());
        return new Result(true, "The shrine consumes the three resonances and unfolds from the ancient pattern.");
    }

    public static Result restore(ServerLevel level, BlockPos pos, Player player) {
        if (level.dimension() != Level.END)
            return new Result(false, "The shrine is not in the End.");
        if (!(player instanceof ServerPlayer serverPlayer))
            return new Result(false, "Only a server player can restore the shrine.");
        String command = "execute in minecraft:the_end positioned "
                + pos.getX() + " " + pos.getY() + " " + pos.getZ()
                + " run function deep_end:shrine/restore";
        try {
            level.getServer().getCommands().performPrefixedCommand(
                    serverPlayer.createCommandSourceStack(), command);
            return new Result(true, "The active shrine collapses back to its dormant anchor.");
        } catch (RuntimeException exception) {
            return new Result(false, "The shrine could not be restored.");
        }
    }

    private static boolean hasItem(Player player, Item item) {
        return player.getInventory().countItem(item) > 0;
    }

    private static void consume(Player player, Item item) {
        for (int slot = 0; slot < player.getInventory().getContainerSize(); slot++) {
            ItemStack stack = player.getInventory().getItem(slot);
            if (stack.is(item)) {
                stack.shrink(1);
                return;
            }
        }
    }
}
