# Nature's Compass — NeoForge 1.20.1 port plan

Upstream: https://github.com/MattCzyr/NaturesCompass
Source baseline: upstream branch `forge-1.20.1` (not the current main branch, which targets Minecraft 26.3 / Java 25).
License: CC BY-NC-SA 4.0. Any adapted source must retain attribution, indicate changes, remain non-commercial, and be distributed under the same license.

## Port target
- Minecraft 1.20.1
- NeoForge 47.1.106
- Java 17

## Required work
1. Start from the 1.20.1 Forge source branch, not current main.
2. Replace ForgeGradle / Forge-specific build configuration with a NeoForge 1.20.1 toolchain compatible with NeoForge 47.1.106.
3. Port mod entrypoint, event registration, item registration, client setup, config registration, and networking to the NeoForge APIs available in 47.1.106.
4. Inspect the previous `ResourceLocation.fromNamespaceAndPath` NoSuchMethodError. Confirm the compiled Minecraft mappings and remove or replace calls unavailable in the target runtime; do not apply a blind global replacement.
5. Preserve compass search behavior, biome filtering, NBT state, durability, translations, recipes, and client rendering.
6. Update `neoforge.mods.toml` with exact Minecraft and NeoForge dependency ranges and remove stale Forge metadata.

## Test matrix
- Compile with Java 17 and the exact NeoForge 47.1.106 dependency.
- Launch client and dedicated server.
- Craft and use the compass; search common and modded biomes.
- Search from Nether/End and verify dimension behavior.
- Verify no-biome-found handling, compass target persistence, and save/reload.
- Verify no `NoSuchMethodError`, `NoClassDefFoundError`, or loader metadata rejection.

## Current status
Source branch and license have been identified. No source files have yet been changed or compiled in this workspace.
