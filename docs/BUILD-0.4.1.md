# 0.4.1: reference-matched ancient shrine model

The submitted shrine image is the visual target. The activated model now has masonry courses on a stepped plinth, asymmetric buttressed towers with chipped crowns, four rune spires, four thick carved diamond arms with open upper/lower tips, suspended axial stones, and a larger multi-shard floating crystal. Cyan angular script and violet fissures are separate geometry, not crosses repeated over entire blocks.

The single native model contains 1,081 elements. Vanilla `light_emission` keeps rune strokes and crystal accents bright; stone retains normal world lighting. Existing 32x32 art is reused, with colour swatches sampled for thin glowing strokes. No shader, resource pack, or additional dependency is required. Emissive faces are not dynamic light sources and do not create the concept image's bloom or transparent triangular crystal refraction. The crystal is a stepped/rotated cuboid approximation, not an exact mesh reproduction.

The complete north-facing multiblock recipe, Crystal activation, protected restoration, pedestal interaction and collision remain unchanged from 0.4.0. This remains ONE block-display entity, not 1,081 entities. Floors keep the recipe's collision heights. Decoration/frame/crystal remains non-colliding; tower collision follows the original recipe, not every chipped facade stone.

## Test in a disposable world

1. Replace the previous Deep End JAR with `the-deep-end-0.4.1-26.3.jar`; keep only one version. Use Minecraft 26.3 / Forge 66.0.9.
2. Run `/deepend shrine` for a new blueprint, then right-click its front pedestal with Resonant Crystal. The block build should become a detailed custom model.
3. Compare the silhouette against the supplied reference: unequal rear towers, four small spires, thick broken diamond frame, large pointed purple cluster and narrow cyan carvings. Check front/back/sides and look for checkerboard textures or flickering overlapping faces.
4. Check the cyan script and purple crystal remain readable in the dark while stone stays shaded. Walk the platform/steps and compare performance with one shrine versus several.
5. Sneak-right-click with a Crystal to restore, then activate again. Save/reload while awakened and verify the same model remains.
6. Repeat the missing-block, duplicate-activation and protected-restoration checks in `BUILD-0.4.0.md`.

Existing awakened displays reference the same model resource and should receive the new appearance after restarting. A new blueprint is recommended for acceptance testing.

Automated checks cover compilation, recipe logic, resource references, model bounds/detail counts, emissive ranges, geometry and JAR packaging. The generator also checks all rotated corners stay inside the 18-block display envelope. Visual appearance and runtime performance still require in-game acceptance.

Regenerate with `node tools/Generate-AwakenedShrineModel.mjs`.
