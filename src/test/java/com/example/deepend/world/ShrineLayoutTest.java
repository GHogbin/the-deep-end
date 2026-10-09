package com.example.deepend.world;

/** Dependency-free acceptance checks executed by the Gradle check task. */
public final class ShrineLayoutTest {
    public static void main(String[] args) {
        var plan = ShrineLayout.create();
        require(plan.equals(ShrineLayout.create()), "deterministic layout");
        require(plan.size() > 400, "substantial platform and towers");
        require(plan.values().stream().filter(m -> m == ShrineLayout.Material.PEDESTAL).count() == 1, "one pedestal");
        require(plan.get(new ShrineLayout.Position(0, 3, -3)) == ShrineLayout.Material.PEDESTAL, "front altar");
        require(!plan.containsKey(new ShrineLayout.Position(0, 7, 0)), "suspended crystal");
        require(plan.get(new ShrineLayout.Position(0, 9, 0)) == ShrineLayout.Material.CRYSTAL, "central crystal");
        require(plan.containsKey(new ShrineLayout.Position(-5, 17, 3)), "tall left tower");
        require(!plan.containsKey(new ShrineLayout.Position(5, 17, 3)), "asymmetric towers");
        require(!plan.containsKey(new ShrineLayout.Position(2, 11, 0)), "broken frame");
        for (var pos : plan.keySet()) require(Math.abs(pos.x()) <= 7 && Math.abs(pos.z()) <= 7
                && pos.y() >= 0 && pos.y() < 18, "clearance bounds");
        try {
            plan.clear();
            throw new AssertionError("layout must be immutable");
        } catch (UnsupportedOperationException expected) { /* immutable by design */ }
        System.out.println("Shrine geometry checks passed: " + plan.size() + " blocks.");
    }
    private static void require(boolean condition, String message) {
        if (!condition) throw new AssertionError(message);
    }
}
