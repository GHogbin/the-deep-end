# Development roadmap

Target: Minecraft 26.3 / Forge 66.0.9 / Java 25.

## Immediate priorities

1. Keep texture-loading regression checks in place. The user reported the 0.3.0 tests passed; the current custom-art revision needs fresh client validation.
2. Validate the 0.3.1 subtle ancient-ruins shrine: custom charcoal stone, sparse teal markings, darker crystal, and continuous frame/centerpiece geometry. The user rejected the 0.3.0 vanilla-texture appearance.
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
