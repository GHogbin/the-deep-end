package com.example.deepend.world;

import java.util.Map;
import java.util.Optional;
import java.util.function.BiPredicate;

/** World-independent validation used by both activation and acceptance tests. */
public final class ShrineRecipe {
    private ShrineRecipe() {}
    public static Optional<ShrineLayout.Position> firstMismatch(
            Map<ShrineLayout.Position, ShrineLayout.Material> plan,
            BiPredicate<ShrineLayout.Position, ShrineLayout.Material> matches) {
        for (int x = -ShrineLayout.RADIUS; x <= ShrineLayout.RADIUS; x++)
            for (int z = -ShrineLayout.RADIUS; z <= ShrineLayout.RADIUS; z++)
                for (int y = 0; y < ShrineLayout.HEIGHT; y++) {
                    var pos = new ShrineLayout.Position(x, y, z);
                    if (!matches.test(pos, plan.get(pos))) return Optional.of(pos);
                }
        return Optional.empty();
    }
}
