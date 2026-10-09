# 0.3.0-26.3 test build

- Replaced the small landmark prototype with a deterministic 513-block shrine.
- Added rune stone, faceted crystal, mirrored diagonal frame segments, and a modeled pedestal.
- Added chime and particles to pedestal activation.
- Enlarged clearance checks to the entire empty, loaded 15 x 18 x 15 volume before placement.
- Added dependency-free geometry acceptance checks and local model/texture reference checks.

Automated validation: build, geometry checks, PNG decoding/resource checks, and JAR packaging checks.
Manual Minecraft client validation: pending. Do not call this build a verified texture fix.

Replace the earlier Deep End JAR rather than installing both versions. Test in a disposable Creative world using docs/TESTING.md. Old shrines are not automatically upgraded; place a new shrine to see this layout. Old standalone shrine blocks retain their activation behavior but now use the pedestal model.
