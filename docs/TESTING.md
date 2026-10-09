# Manual test checklist

Use a new disposable Creative world with cheats enabled and Minecraft 26.3 / Forge 66.0.9. Keep only one Deep End JAR in the instance's mods folder. Back up any existing worlds before testing experimental builds.

## Launch and textures

1. Confirm Deep End appears in the mod list and the world opens.
2. Obtain these items using `/give @s deep_end:<id>`: `resonant_crystal`, `chorus_fibre`, `resonance_lens`, `resonant_crystal_block`, `observation_shrine`, `shrine_rune_stone`, `shrine_crystal`, `shrine_frame`, `ancient_shrine_stone`, and `ancient_shrine_slab`.
3. Check each inventory icon and first-/third-person held appearance. No purple/black checkerboard should be present.
4. Place all seven custom blocks and inspect all faces. Place bottom, top, and double Ancient Shrine Slabs. Record inventory and placed-block results separately.
5. If resources fail, attach the relevant `latest.log` resource/model errors to the texture issue, removing personal paths and other private information first.

## Shrine and Lens

1. Enter the End with `/execute in minecraft:the_end run tp @s 0 80 0`, then find a safe platform or use Creative flight.
2. Run `/deepend region`; check the reported region changes at more distant coordinates.
3. Stand near a clear area and run `/deepend shrine`. The structure is centred 10 blocks south and requires an empty, loaded 15 × 18 × 15 placement area. If terrain blocks placement, fly a few blocks above a flat End island and retry; blocks are never cleared automatically.
4. Approach from the north/front and right-click the low pedestal with Resonant Crystal. The complete build must change into one custom rendered shrine. Verify the long charcoal surfaces, unequal towers, four capped spires, teal runes, joined diamond frame with one fracture, and continuous crystal. Verify one Crystal consumed, lore messages, chime, and particles.
5. Use Resonance Lens nearby in the End. Verify the pedestal coordinates are reported and durability is consumed.
6. Try placing again where blocks already exist. It should refuse without changing blocks.
7. Save, exit, reopen: the transformed model and walkable platform must persist. Activate again: it should report already awakened, without consuming a Crystal or duplicating the model. The Lens should still locate the pedestal.
8. Sneak-right-click with a Crystal: the model disappears and the block build returns, without consuming a Crystal. Activate again to confirm the cycle is repeatable.
9. Restore and remove one rune. Normal activation must refuse, report the mismatch coordinates, and consume nothing. Replace the rune and retry.

This first recipe is north-facing only. Place a NEW blueprint rather than relying on old generated shrines. Standalone shrine blocks no longer awaken unless the complete recipe is present. See docs/BUILD-0.4.0.md for cleanup/foreign-block safety tests. Automated checks cannot prove actual client appearance or persistence.
