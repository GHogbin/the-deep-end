# Development roadmap

Target: Minecraft 26.3 / Forge 66.0.9 / Java 25.

## Immediate priorities

1. Fix missing textures. Reproduce on a clean client, examine resource-loading errors, verify all modern item definitions, blockstates, models, and packaged PNGs. Test inventory, held items, and placed blocks separately.
2. Rebuild the Observation Shrine to match the approved concept: stepped dark platform, asymmetric rear towers, cyan rune bands, broken diamond frame, suspended faceted violet crystal, shorter perimeter spires, and a reachable interactive front pedestal.
3. Verify shrine placement safety, Lens detection, activation, and save/reload in a disposable test world.

## Later gameplay slices

- Natural landmark placement with deterministic spacing and rarity.
- Region-specific terrain without changing the central Dragon Island.
- Void-current hazards, ambient mobs, and encounter design.
- Lore progression, journal/advancement chain, loot, and server configuration.

These are planned features, not claims of existing functionality.

## Release gate

- Build and `verifyModJar` succeed on the supported toolchain.
- No example-mod classes or old-version dependency metadata in the JAR.
- Clean-client launch and texture checklist pass.
- Shrine interaction, placement safety, and save/reload pass.
- Release notes state precisely what was tested and what remains unfinished.

Use GitHub issues for acceptance criteria and branches/pull requests for changes. Do not publish a build as verified until the manual client checks are recorded.
