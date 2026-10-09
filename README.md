# The Deep End

A work-in-progress Minecraft End expansion built around distant regions, ancient shrines, and resonant exploration tools.

## Current development target

- Minecraft Java Edition **26.3**
- Minecraft Forge **66.0.9**
- Java **25**
- Current source version: **0.9.0-26.3**

This repository contains the current port, not the earlier Minecraft 1.20.1 prototype. It is an experimental baseline, not a finished release.

## Implemented baseline

- Resonant Crystal, Chorus Fibre, Resonance Lens, seven obtainable building blocks, and internal model/collision blocks.
- End distance/region sampling and `/deepend region`.
- A 511-block Observation Shrine placed with `/deepend shrine`: stepped platform, unequal rear towers, sparse teal runes, a broken diamond frame, one continuous suspended crystal, four perimeter spires, and an interactive front pedestal.
- Complete north-facing shrine multiblock validation. Crystal activation swaps the build for one persistent, detailed display model. Sneak-right-click with the Crystal restores the block build without consuming it. Incomplete builds consume nothing.
- Build-time checks for geometry invariants, local asset references, PNG decoding, and packaged classes/metadata/resources.

The current gameplay baseline intentionally uses the single-block Observation Shrine placeholder. The custom renderer and multiblock systems remain in the source but are paused while the core exploration loop is developed. Natural distant discovery places single shrine blocks on deterministic 2,048-block sites beyond 8,000 blocks from the End origin, and the Resonance Lens reports the nearest dormant site. Newly generated End chunks also receive rare, deterministic region markers outside the central Dragon Island: purpur remnants in the Outer End, ancient shrine stone in the Fringe, rune stone in the Deep End, and crying obsidian markers in the Abyss.

See [0.9.0 testing](docs/BUILD-0.9.0.md).

## Build

Install JDK 25 and set `JAVA_HOME` to its installation directory. The checked-in Gradle wrapper downloads its declared distribution; the first build also downloads Forge dependencies.

Windows PowerShell:

```powershell
.\gradlew.bat build --rerun-tasks --no-build-cache --no-daemon --no-configuration-cache --console=plain
```

Linux/macOS:

```sh
./gradlew build --rerun-tasks --no-build-cache --no-daemon --no-configuration-cache --console=plain
```

The current artifact is `build/libs/the-deep-end-26.3-0.9.0-26.3.jar`. Use the JAR for the exact source version you built, never an arbitrary first JAR from that folder.

For the development client, run `gradlew.bat runClient` (or `./gradlew runClient`). See [testing](docs/TESTING.md) for the manual acceptance checklist.

## Development management

Track unfinished work in [Issues](https://github.com/GHogbin/the-deep-end/issues) and the [roadmap](docs/ROADMAP.md). Feature changes should use short-lived branches and pull requests. Build outputs and local Minecraft worlds are not committed.

The inherited mod metadata declares MIT, but a corresponding mod-wide license file and artwork licensing decision have not yet been documented. Resolve licensing before a public release; public visibility alone is not a licensing decision. Forge's bundled license is preserved separately in [third-party notices](docs/third-party/Forge-LICENSE.txt); it is not presented as a license for this mod's original code or artwork.
