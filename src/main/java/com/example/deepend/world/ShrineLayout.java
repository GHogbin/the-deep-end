package com.example.deepend.world;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;

/** Pure geometry shared by placement and tests. North (negative Z) is the entrance. */
public final class ShrineLayout {
    public static final int RADIUS = 7;
    public static final int HEIGHT = 18;
    public record Position(int x, int y, int z) {}
    public enum Material { FOUNDATION, DECK, STEP, TOWER, RUNE, CRYSTAL, LARGE_CRYSTAL, FRAME, FRAME_FORK, PEDESTAL }
    private ShrineLayout() {}

    public static Map<Position, Material> create() {
        Map<Position, Material> plan = new LinkedHashMap<>();
        for (int x = -7; x <= 7; x++) {
            for (int z = -7; z <= 7; z++) {
                if (Math.abs(x) == 7 && Math.abs(z) == 7) continue;
                put(plan, x, 0, z, Material.FOUNDATION);
                int edge = Math.max(Math.abs(x), Math.abs(z));
                if (edge <= 5) put(plan, x, 1, z, Material.DECK);
                else if (edge == 6) put(plan, x, 1, z, Material.STEP);
            }
        }
        // Unequal 2x2 rear towers taper into a single rune spine.
        for (int side : new int[]{-1, 1}) {
            int height = side < 0 ? 16 : 13;
            for (int y = 2; y <= height; y++) {
                put(plan, side * 5, y, 3, y % 4 == 0 && y <= height - 2 ? Material.RUNE : Material.TOWER);
                if (y <= height - 3) put(plan, side * 5, y, 4, Material.TOWER);
                if (y <= height - 5) put(plan, side * 6, y, 3, Material.TOWER);
                if (y <= height - 7) put(plan, side * 6, y, 4, Material.TOWER);
            }
            put(plan, side * 5, height + 1, 3, Material.CRYSTAL);
            for (int z : new int[]{-4, 5}) {
                int tip = z < 0 ? 6 : 8;
                for (int y = 2; y < tip; y++)
                    put(plan, side * 5, y, z, y % 3 == 0 ? Material.RUNE : Material.TOWER);
                put(plan, side * 5, tip, z, Material.CRYSTAL);
            }
        }
        // Each model starts at a vertex and reaches the next vertex, not a centred loose bar.
        put(plan, -4, 9, 0, Material.FRAME_FORK);
        for (int x = -3; x < 4; x++) {
            int top = 13 - Math.abs(x);
            if (x != 1) put(plan, x, top, 0, Material.FRAME); // one deliberate fracture
            put(plan, x, 5 + Math.abs(x), 0, Material.FRAME);
        }
        put(plan, 0, 9, 0, Material.LARGE_CRYSTAL);
        // Intentional deck replacements for cyan floor lines.
        for (int z = -5; z <= 2; z++) plan.put(new Position(0, 1, z), Material.RUNE);
        for (int x = -4; x <= 4; x++) plan.put(new Position(x, 1, 2), Material.RUNE);
        put(plan, 0, 2, -3, Material.DECK);
        put(plan, 0, 3, -3, Material.PEDESTAL);
        return Collections.unmodifiableMap(plan);
    }

    private static void put(Map<Position, Material> plan, int x, int y, int z, Material material) {
        if (Math.abs(x) > RADIUS || Math.abs(z) > RADIUS || y < 0 || y >= HEIGHT)
            throw new IllegalArgumentException("Shrine part outside clearance bounds");
        if (plan.putIfAbsent(new Position(x, y, z), material) != null)
            throw new IllegalStateException("Overlapping shrine parts at " + x + "," + y + "," + z);
    }
}
