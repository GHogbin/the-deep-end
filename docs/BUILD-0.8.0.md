# 0.8.0 easier natural shrine discovery

Natural single-block shrines now use deterministic 2,048-block grid cells, with one candidate in every cell beyond 8,000 blocks from the End origin. A shrine appears when the player comes within 768 blocks of its candidate site, provided the candidate is over a solid End surface and the target block is empty. The central Dragon Island remains excluded.

The Resonance Lens now reports the nearest dormant candidate coordinates and approximate distance. If a real shrine is within the existing local scan radius, it reports the actual block instead.

## Test

1. Install only `the-deep-end-0.8.0-26.3.jar` on Minecraft 26.3 / Forge 66.0.9.
2. Travel beyond 8,000 blocks in the End and use the Resonance Lens. It should report dormant site coordinates.
3. Travel toward those coordinates. A single Observation Shrine block should appear automatically within about 768 blocks if the site has valid terrain.
4. Return to the same location or reload the world. The shrine must not duplicate.
5. Inside 8,000 blocks, natural generation must remain disabled. `/deepend shrine` still places a controlled single-block test shrine.

Automated compilation, geometry, resource and JAR checks pass. Natural placement remains a manual runtime test.
