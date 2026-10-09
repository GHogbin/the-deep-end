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
4. Approach from the north/front. Check the charcoal stepped base, tall left and shorter right tower, four capped spires, sparse teal runes, continuous diamond frame with one deliberate break, and a single floating violet crystal (not three beads). Right-click the low front pedestal with Resonant Crystal. Verify the lore/signal messages, crystal consumption, chime, and white particles.
5. Use Resonance Lens nearby in the End. Verify the pedestal coordinates are reported and durability is consumed.
6. Try placing again where blocks already exist. It should refuse without changing blocks.
7. Save, exit, reopen, and repeat activation and Lens detection.

Compare the actual landmark with the approved concept and report differences. The centerpiece is one extended native JSON model, not a bespoke entity renderer or shader. Place a new shrine: old generated structures are not rebuilt automatically. Geometry and asset checks cannot prove correct client appearance.
