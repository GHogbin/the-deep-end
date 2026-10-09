# 0.6.0 natural shrine generation

This build adds the first world-generation gameplay slice. Observation Shrines are now discovered naturally in the End once the player travels beyond 8,000 blocks from the origin. Placement is deterministic by 4,096-block grid cell: one quarter of cells contain a candidate. A candidate only places on a solid End surface, near the player, and when the complete 15 × 18 × 15 volume is empty. The shrine is placed directly as the finished custom-rendered structure with invisible collision and an interactive pedestal.

The central Dragon Island and the 0–8,000-block region remain excluded. `/deepend shrine` remains available for controlled testing. Existing placement, restore, persistence and custom-renderer behavior are unchanged.

## Manual test

1. Install only `the-deep-end-0.6.0-26.3.jar` on Minecraft 26.3 / Forge 66.0.9.
2. In a disposable End world, teleport beyond 8,000 blocks, for example `/tp @s 9000 100 0`.
3. Travel through the End. Candidate locations are deterministic but sparse; a shrine appears only when you approach a valid candidate cell's surface.
4. Confirm the chat message says an ancient shrine surfaced, and confirm the placed result is the finished custom model rather than the old block blueprint.
5. Walk the platform and use the Resonant Lens nearby. Verify the shrine does not duplicate when revisiting the area or reloading the world.
6. Check a location inside 8,000 blocks: natural placement must not occur there. `/deepend shrine` should still place a controlled test shrine in an empty area.

Automated validation covers compilation, existing recipe/geometry checks, model/resource validation, and JAR packaging. Natural-generation distribution and runtime visual acceptance remain manual tests.
