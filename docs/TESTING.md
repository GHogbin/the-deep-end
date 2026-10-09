# Manual test checklist

Use a new disposable Creative world with cheats enabled and Minecraft 26.3 / Forge 66.0.9. Keep only one Deep End JAR in the instance's mods folder. Back up any existing worlds before testing experimental builds.

## Launch and textures

1. Confirm Deep End appears in the mod list and the world opens.
2. Obtain these items using `/give @s deep_end:<id>`: `resonant_crystal`, `chorus_fibre`, `resonance_lens`, `resonant_crystal_block`, and `observation_shrine`.
3. Check each inventory icon and first-/third-person held appearance. No purple/black checkerboard should be present.
4. Place both blocks and inspect all faces. Record inventory and placed-block results separately.
5. If resources fail, attach the relevant `latest.log` resource/model errors to the texture issue, removing personal paths and other private information first.

## Shrine and Lens

1. Enter the End with `/execute in minecraft:the_end run tp @s 0 80 0`, then find a safe platform or use Creative flight.
2. Run `/deepend region`; check the reported region changes at more distant coordinates.
3. Stand near a clear area and run `/deepend shrine`. The current structure is centred 10 blocks south and requires an empty, loaded 9 × 13 × 9 placement area.
4. Right-click the low front shrine pedestal with Resonant Crystal. Verify the lore/signal messages and crystal consumption.
5. Use Resonance Lens nearby in the End. Verify the pedestal coordinates are reported and durability is consumed.
6. Try placing again where blocks already exist. It should refuse without changing blocks.
7. Save, exit, reopen, and repeat activation and Lens detection.

The present shrine is a block-built prototype; it does not yet match the approved custom-geometry render. Do not mark that design's acceptance criteria complete based on this checklist alone.
