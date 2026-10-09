# 0.5.0 custom shrine renderer

The awakened shrine now uses a dedicated `AncientShrineEntity` and Forge entity renderer. It no longer relies on the oversized BlockDisplay JSON model. The server still validates the 511-block recipe, preserves the invisible walkable collision cells, and restores the original build.

The renderer contains separate baked geometry groups for stone, cyan runes, and the violet multi-shard crystal. The model is centered on the shrine base so the towers and frame remain aligned with the collision footprint. The entity is saved and tracked as one persistent visual object.

## Test

1. Remove older Deep End JARs and install only `the-deep-end-0.5.0-26.3.jar` with Minecraft 26.3 / Forge 66.0.9.
2. In a disposable End world, run `/deepend shrine`.
3. Right-click the front pedestal with Resonant Crystal.
4. Check for a solid stepped platform, two unequal rear towers, four smaller crystal spires, a thick broken diamond frame, a large central crystal, and bright cyan runes.
5. Walk across the platform and steps. Check front, side, rear, and close-up views.
6. Sneak-right-click with Resonant Crystal to restore the block build, then activate again.
7. Save/reload while awakened and verify that the custom renderer entity remains present.

If the game starts but the model is invisible, capture `latest.log`; that points to a renderer registration or client resource issue. If the model is visible but the proportions need tuning, capture a front-facing screenshot so the baked geometry can be adjusted against the reference.

Automated validation covers compilation, recipe logic, resources, geometry and JAR packaging. Runtime appearance and collision remain manual tests.
