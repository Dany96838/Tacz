# Spawning Overhaul — NeoForge 1.20.1

A compatibility port of [Payangar's original Spawning Overhaul](https://github.com/payangar-dev/spawning-overhaul), targeting Minecraft **1.20.1**, NeoForge **47.1.106**, Java **17**.

## Ported systems
- Environment-sensitive natural spawn acceptance.
- Cave detection below Y=60 with progressive depth multiplier (Y=60 through Y=-64).
- Forest density detection using vanilla `#minecraft:logs` tag, so modded wood/log blocks participate automatically when tagged.
- Dangerous/safe structure classification, additional structure ID lists, and a 10-second chunk-level cache.
- Generic modded-mob support plus configurable per-entity multipliers by registry ID.
- Spider/cave-spider cave restriction and optional creeper restriction near dangerous structures.
- Dimension allowlist.
- `/spawndebug` operator command.
- Configuration file generated at `config/spawningoverhaul.properties`.

## Important spawning behavior
This is intentionally an acceptance-probability overhaul of **existing natural spawn attempts**. It does not create a second spawn scheduler, ignore vanilla mob caps, or affect spawners, commands, raids, or scripted/event spawns. Higher calculated multipliers improve the chance that a vanilla-approved natural attempt is accepted; they do not guarantee more entities than vanilla mob caps permit.

Default upstream values are preserved: base 0.2, caves 1–5, forests 1–3, dangerous structures x3, forest scan radius 10, log threshold 50. The upstream normalization uses the largest configured multiplier as its reference, so default surface attempts have a 4% acceptance probability, shallow caves 20%, and deep caves 100%.

## Configuration
Edit `config/spawningoverhaul.properties` while the game/server is closed, then restart:
- `enabled`, `enabledDimensions` (comma-separated dimension IDs)
- `baseMultiplier`, `minCaveMultiplier`, `maxCaveMultiplier`
- `minForestMultiplier`, `maxForestMultiplier`
- `dangerousStructureMultiplier`, `enableStructureModifications`
- `denseForestLogThreshold`, `denseForestScanRadius`
- `spiderOnlyInCave`, `disableCreeperNearStructure`, `debugmode`
- `additionalDangerousStructures`, `additionalSafeStructures`
- `mobSpecificMultipliers` as comma-separated `namespace:entity=value`, e.g. `infectionz:zombie=2.0`

Modded entities are handled generically; no mob ID list is hardcoded. Modded structure IDs can be added to the dangerous/safe lists.

## Build
Requires JDK 17. Run `gradle clean build` (or use the included GitHub Actions workflow). Output: `build/libs/spawningoverhaul-neoforge-1.20.1-0.1.0.jar`.

## Attribution and license
Original project: Payangar, MIT license. This port retains the original project identity and core spawning model; see LICENSE for license terms.
