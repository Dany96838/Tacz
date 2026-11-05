# NeoForge 1.20.1 ports — status and work plan

Target runtime:
- Minecraft 1.20.1
- NeoForge 47.1.106
- Java 17

This directory is a porting workspace. It does not contain redistributed upstream JARs or copied ARR source code.

## Findings (checked 2026-11-04)

| Project | Official 1.20.1 artifact found | License | Action |
|---|---|---|---|
| Nature's Compass | Forge 1.12.0; no official NeoForge 1.20.1 artifact confirmed | CC BY-NC-SA 4.0 | A source adaptation may be prepared with attribution and same-license terms. |
| Serene Seasons | 9.1.0.0 is Forge 1.20.1; its changelog explicitly says the NeoForge target was removed in the 1.20.1 backport | ARR | Do not publish modified source or binaries without written permission. |
| GlitchCore | 1.20.1 release line lists Forge/Fabric, not NeoForge | ARR | Do not publish modified source or binaries without written permission. |

## Upstream projects

- Nature's Compass: https://github.com/MattCzyr/NaturesCompass
- Serene Seasons: https://github.com/Glitchfiend/SereneSeasons
- GlitchCore: https://github.com/Glitchfiend/GlitchCore

Official project pages:
- https://modrinth.com/mod/natures-compass
- https://modrinth.com/mod/serene-seasons
- https://modrinth.com/mod/glitchcore

## Important compatibility notes

1. A Forge 1.20.1 JAR is not automatically a NeoForge 1.20.1 JAR. The requested NeoForge build must be compiled against the exact NeoForge 47.1.106 toolchain and checked for loader-specific APIs, event bus behavior, registries, mixins, and metadata.
2. Nature's Compass previously produced a NoSuchMethodError involving ResourceLocation.fromNamespaceAndPath. That symbol/API mismatch must be checked against the exact mappings and loader dependencies; do not treat changing the loader label in metadata as a fix.
3. Serene Seasons 9.1.0.0's published changelog describes the 1.20.1 backport as Forge/Fabric and includes a commit note stating NeoForge was removed. It depends on GlitchCore in that version line.
4. GlitchCore is ARR. The existence of public source on GitHub does not grant permission to republish a modified fork.
5. Do not bundle upstream JARs in this repository. For ARR projects, link to the official author download pages and keep any locally authorized build outside this public repository.

## Validation required before calling a port complete

- Gradle compile against Minecraft 1.20.1 and NeoForge 47.1.106.
- Confirm generated mod metadata declares the correct loader and dependency ranges.
- Launch a clean client and dedicated server with only the port and required dependencies.
- Test world creation, save/reload, registry sync, config loading, and dedicated-server startup.
- For Nature's Compass: test biome search, compass pointing, dimension behavior, and no crashes when target biome is absent.
- For Serene Seasons (only with authorization): test season progression, crop growth, temperature/snow behavior, foliage colors, and server/client synchronization.
- For GlitchCore (only with authorization): test library initialization and dependent mod startup.

## Current state

This commit records the verified compatibility and licensing boundary. It is not a compiled mod release. No upstream binaries are included, and no claim of runtime compatibility is made until the build and tests above pass.
