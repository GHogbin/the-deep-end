# 0.3.1-26.3: subtle ancient ruins

## Changes

- Custom 32×32 charcoal stone with faint violet fissures replaces vanilla crying obsidian on new shrines.
- Narrow weathered teal inlays replace white cross-shaped markings; tower runes are less frequent and block lighting is reduced.
- Dark violet crystal material replaces pale vanilla amethyst, including Resonant Crystal Block.
- Resonant Crystal's inventory/held sprite is now a single transparent-background shard.
- The centerpiece is one continuous three-block-high crystal model; perimeter caps remain small.
- Frame models reach end-to-end, with a fork joining the left corner and only one deliberate upper-right fracture.
- Ancient Shrine Stone and Ancient Shrine Slab are available as separate building blocks.

## Validation and testing

The user reported all 0.3.0 tests passed, but rejected its appearance and selected subtle ancient ruins.

0.3.1 automated checks pass: build, 511-block geometry, frame connectivity (one chain/two fracture ends), frame model endpoint math, local resource references, 32×32 texture dimensions, transparent crystal icon, and required JAR entries.

0.3.1 manual client appearance/regression checks remain pending. Replace the old JAR, place a NEW shrine with /deepend shrine, inspect from front and sides, activate it, test the Lens, and save/reload. Existing generated shrines are not rebuilt automatically.

## Art provenance

Four textures/sprites were generated using the built-in image-generation tool. The user explicitly approved nearest-neighbour conversion to 32×32. Original PNGs and exact prompts are preserved in docs/art-source. Run tools/Convert-ShrineTextures.ps1 on Windows to reproduce the game-ready PNGs. No API key or external image-generation CLI is required.
