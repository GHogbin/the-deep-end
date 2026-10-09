# The Deep End 0.9.0-26.3 testing

This build adds the first region-specific End terrain pass. It is deliberately sparse and only runs for newly generated chunks, so existing worlds and the central Dragon Island are left alone.

## Install

1. Build with JDK 25 and Forge 66.0.9 for Minecraft 26.3.
2. Copy `build/libs/the-deep-end-26.3-0.9.0-26.3.jar` into the instance's `mods` folder.
3. Remove older `the-deep-end` jars first so two versions are not loaded together.
4. Create a fresh End test world, or travel into unexplored End terrain in a backup world.

## Acceptance checklist

- The game loads with one Deep End mod and no duplicate-version warning.
- The End origin remains unchanged by the terrain pass.
- Newly explored terrain between roughly 1,500 and 8,000 blocks from origin can contain rare purpur remnants.
- Newly explored terrain between roughly 8,000 and 20,000 blocks can contain rare ancient shrine-stone markers.
- Newly explored terrain between roughly 20,000 and 50,000 blocks can contain rare rune-stone markers.
- Newly explored terrain beyond roughly 50,000 blocks can contain rare crying obsidian markers.
- Markers are small vertical landmarks, do not overwrite solid terrain, and remain consistent when the same chunks are revisited.
- `/deepend region` reports the expected region at each distance band.
- `/deepend shrine` still places one single-block Observation Shrine ten blocks south.
- A Resonance Lens still reports nearby shrines or dormant deterministic shrine sites.

The terrain markers are uncommon by design. Explore several newly generated chunks in each band before treating an absence as a failure. Existing chunks will not be retrofitted by this build.
