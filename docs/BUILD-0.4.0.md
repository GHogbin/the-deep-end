# 0.4.0: multiblock-to-model transformation

Build the full north-facing shrine recipe (or use /deepend shrine to place the test blueprint), then right-click the front pedestal with Resonant Crystal. The complete build transforms into one 105-element, 18-block-scale custom visual model. Long carved surfaces, tapered towers, continuous crystal, and joined frame replace separately rendered grid blocks.

One functional pedestal remains for interaction and Lens detection. Invisible internal collision cells preserve the original walkable platform, slab steps, and tower collision. These internal blocks and the visual-model block have no obtainable items.

Sneak-right-click with a Crystal to restore the assembled block build for repairs. Restoration consumes no Crystal. Added blocks at recipe cells cause restoration to refuse without overwriting them. Breaking the awakened pedestal removes the tagged display and restores owned recipe cells when the full area is loaded; it does not recreate the broken pedestal or overwrite player-added blocks.

## Safety and persistence

- Activation checks the complete 15 x 18 x 15 loaded area: exact recipe states and empty interior space.
- Missing/wrong components or obstructions report coordinates; nothing is consumed or changed.
- A cancelled model spawn changes no blocks.
- Replacement failures attempt to restore the exact captured block states.
- Repeated activation does not spawn duplicates or consume another Crystal.
- Model transform and origin tag are persisted through Minecraft's vanilla block-display serialization.
- The display is anchored in the pedestal's chunk; frustum culling is disabled for its extended model.
- Fixed north-facing recipe only in this first build. Other rotations are not supported yet.
- This is a native custom model interpretation of the concept, not an exact shader-render reproduction.

## Manual acceptance (disposable world)

1. Place a NEW recipe with /deepend shrine. Remove one rune block. Crystal activation must refuse and preserve the stack/world.
2. Replace the rune and activate. Exactly one complete custom model should appear, with no original tower/frame cubes showing through.
3. Walk the base/steps; verify no falling through visible surfaces. Inspect from front, back, sides, and near the crystal.
4. Activate again: no duplicate model and no additional Crystal consumed.
5. Sneak-right-click: block build returns, model disappears, stack unchanged. Activate again.
6. Save/quit/reload while awakened: model, platform collision and pedestal persist; Lens still locates the pedestal.
7. In a copy of the test world, break the pedestal while the shrine area is loaded: model disappears, owned blocks restore, no invisible scaffold remains.
8. Place an unrelated block in a former frame/crystal recipe cell after awakening. Restoration must refuse rather than destroy it. Remove it and retry.

Automated validation: compilation, exact/missing/wrong/obstructed recipe checks, geometry invariants, model/resource/PNG checks, and JAR entries. Manual server/client activation, persistence and appearance tests remain pending.

Regenerate the unified model with node tools/Generate-AwakenedShrineModel.mjs. Texture provenance remains in docs/art-source.
