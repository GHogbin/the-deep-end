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
        require(plan.get(new ShrineLayout.Position(0, 9, 0)) == ShrineLayout.Material.LARGE_CRYSTAL, "continuous central crystal");
        require(plan.values().stream().filter(m -> m == ShrineLayout.Material.LARGE_CRYSTAL).count() == 1, "one large crystal model");
        require(!plan.containsKey(new ShrineLayout.Position(0, 8, 0))
                && !plan.containsKey(new ShrineLayout.Position(0, 10, 0)), "reserve air for large crystal tips");
        require(plan.containsKey(new ShrineLayout.Position(-5, 17, 3)), "tall left tower");
        require(!plan.containsKey(new ShrineLayout.Position(5, 17, 3)), "asymmetric towers");
        require(!plan.containsKey(new ShrineLayout.Position(1, 12, 0)), "one deliberate frame fracture");
        require(plan.get(new ShrineLayout.Position(-4, 9, 0)) == ShrineLayout.Material.FRAME_FORK, "joined left corner");
        for (var pos : plan.keySet()) require(Math.abs(pos.x()) <= 7 && Math.abs(pos.z()) <= 7
                && pos.y() >= 0 && pos.y() < 18, "clearance bounds");
        verifyFrameChain(plan);
        require(ShrineRecipe.firstMismatch(plan, (pos, material) -> plan.get(pos) == material).isEmpty(), "complete recipe accepted");
        var missing = new java.util.HashMap<>(plan);
        missing.remove(new ShrineLayout.Position(-5, 4, 3));
        require(ShrineRecipe.firstMismatch(plan, (pos, material) -> missing.get(pos) == material).isPresent(), "missing rune rejected");
        var wrong = new java.util.HashMap<>(plan);
        wrong.put(new ShrineLayout.Position(-5, 4, 3), ShrineLayout.Material.TOWER);
        require(ShrineRecipe.firstMismatch(plan, (pos, material) -> wrong.get(pos) == material).isPresent(), "wrong component rejected");
        var obstruction = new java.util.HashMap<>(plan);
        obstruction.put(new ShrineLayout.Position(1, 8, 0), ShrineLayout.Material.TOWER);
        require(ShrineRecipe.firstMismatch(plan, (pos, material) -> obstruction.get(pos) == material).isPresent(), "interior obstruction rejected");
        try {
            plan.clear();
            throw new AssertionError("layout must be immutable");
        } catch (UnsupportedOperationException expected) { /* immutable by design */ }
        System.out.println("Shrine geometry checks passed: " + plan.size() + " blocks.");
    }
    private static void require(boolean condition, String message) {
        if (!condition) throw new AssertionError(message);
    }

    private static void verifyFrameChain(java.util.Map<ShrineLayout.Position, ShrineLayout.Material> plan) {
        var links = new java.util.HashMap<ShrineLayout.Position, java.util.Set<ShrineLayout.Position>>();
        for (var entry : plan.entrySet()) {
            var start = entry.getKey();
            if (entry.getValue() == ShrineLayout.Material.FRAME_FORK) {
                connect(links, start, new ShrineLayout.Position(start.x() + 1, start.y() + 1, 0));
                connect(links, start, new ShrineLayout.Position(start.x() + 1, start.y() - 1, 0));
            } else if (entry.getValue() == ShrineLayout.Material.FRAME) {
                boolean ascending = (start.x() >= 0) == (start.y() < 9);
                connect(links, start, new ShrineLayout.Position(start.x() + 1, start.y() + (ascending ? 1 : -1), 0));
            }
        }
        require(links.size() == 16, "all diamond vertices present");
        require(links.values().stream().filter(neighbours -> neighbours.size() == 1).count() == 2, "one fracture, two ends");
        require(links.values().stream().allMatch(neighbours -> neighbours.size() <= 2), "no stray branches");
        var visited = new java.util.HashSet<ShrineLayout.Position>();
        var queue = new java.util.ArrayDeque<ShrineLayout.Position>();
        queue.add(links.keySet().iterator().next());
        while (!queue.isEmpty()) {
            var vertex = queue.remove();
            if (visited.add(vertex)) queue.addAll(links.get(vertex));
        }
        require(visited.size() == links.size(), "frame is a single connected chain, not floating bars");
    }

    private static void connect(java.util.Map<ShrineLayout.Position, java.util.Set<ShrineLayout.Position>> links,
                                ShrineLayout.Position first, ShrineLayout.Position second) {
        links.computeIfAbsent(first, key -> new java.util.HashSet<>()).add(second);
        links.computeIfAbsent(second, key -> new java.util.HashSet<>()).add(first);
    }
}
