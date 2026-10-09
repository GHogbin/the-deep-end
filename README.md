# The Deep End

A work-in-progress Minecraft End expansion built around distant regions, ancient shrines, and resonant exploration tools.

## Current development target

- Minecraft Java Edition **26.3**
- Minecraft Forge **66.0.9**
- Java **25**
- Current source version: **0.3.1-26.3**

This repository contains the current port, not the earlier Minecraft 1.20.1 prototype. It is an experimental baseline, not a finished release.

## Implemented baseline

- Resonant Crystal, Chorus Fibre, Resonance Lens, and seven custom blocks.
- End distance/region sampling and `/deepend region`.
- A 511-block Observation Shrine placed with `/deepend shrine`: stepped platform, unequal rear towers, sparse teal runes, a broken diamond frame, one continuous suspended crystal, four perimeter spires, and an interactive front pedestal.
- Shrine activation with a Resonant Crystal and nearby shrine detection with the Lens.
- Build-time checks for geometry invariants, local asset references, PNG decoding, and packaged classes/metadata/resources.

The shrine uses native JSON geometry and custom 32×32 charcoal stone, subdued violet crystal, and weathered teal inlay textures. Ordinary block lighting replaces shader bloom. Generated source artwork and prompts are preserved in docs/art-source, with a reproducible Windows conversion script. The user reported the 0.3.0 gameplay/texture-loading tests passed, but disliked its appearance. This 0.3.1 subtle ancient-ruins revision still needs an in-game visual/regression check.

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

The current artifact is `build/libs/the-deep-end-26.3-0.3.1-26.3.jar`. Use the JAR for the exact source version you built, never an arbitrary first JAR from that folder.

For the development client, run `gradlew.bat runClient` (or `./gradlew runClient`). See [testing](docs/TESTING.md) for the manual acceptance checklist.

## Development management

Track unfinished work in [Issues](https://github.com/GHogbin/the-deep-end/issues) and the [roadmap](docs/ROADMAP.md). Feature changes should use short-lived branches and pull requests. Build outputs and local Minecraft worlds are not committed.

The inherited mod metadata declares MIT, but a corresponding mod-wide license file and artwork licensing decision have not yet been documented. Resolve licensing before a public release; public visibility alone is not a licensing decision. Forge's bundled license is preserved separately in [third-party notices](docs/third-party/Forge-LICENSE.txt); it is not presented as a license for this mod's original code or artwork.
