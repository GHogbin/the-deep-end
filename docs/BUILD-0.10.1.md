# The Deep End 0.10.1-26.3 testing

This is a workflow fix for the custom shrine model. A single Observation Shrine can now be activated directly with a Resonant Crystal; the old complete multiblock activation path remains supported.

## Test

1. Install only `the-deep-end-26.3-0.10.1-26.3.jar`.
2. Run `/deepend shrine`.
3. Hold a Resonant Crystal and right-click the single shrine block.
4. Confirm the single block changes to the custom 3D shrine model without the “incomplete or obstructed” message.
5. Confirm the crystal is consumed once.
6. Sneak-right-click the awakened model’s anchor block with another Resonant Crystal.
7. Confirm it restores to the single-block shrine and does not consume the restoring crystal.
