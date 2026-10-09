# The Deep End 0.9.1-26.3 testing

This is a discoverability fix for the region markers. Newly generated chunks now have higher marker rates, and `/deepend landmark` places a marker at the player's location for direct verification.

Install only this jar in the Forge 66.0.9 / Minecraft 26.3 instance. Existing chunks are not retrofitted.

## Direct test

Teleport outside the central island, then run:

```text
/deepend region
/deepend landmark
```

The command places a marker at the surface using the current region's material. Use it at approximately 3,000, 10,000, 25,000, and 55,000 blocks from the End origin to verify purpur, ancient shrine stone, rune stone, and crying obsidian respectively.

## Natural-generation test

Explore newly generated chunks. Expected approximate rates are one marker per 12 Outer End chunks, 8 Fringe chunks, 6 Deep End chunks, or 4 Abyss chunks. Markers are small 2–5 block pillars. Existing chunks will not produce them.
