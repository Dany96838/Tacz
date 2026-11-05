# Final technical audit — three requested mods

**Target:** Minecraft 1.20.1 / NeoForge 47.1.106 / Java 17  
**Repository branch:** `ports/neoforge-1.20.1`

This audit checks the upstream 1.20.1 source branches, their build configuration, loader metadata, and published Modrinth files. It distinguishes source-port feasibility from permission to redistribute a modified build.

## 1. Nature's Compass

**Upstream:** `MattCzyr/NaturesCompass`, branch `forge-1.20.1`  
**License:** CC BY-NC-SA 4.0  
**Current state in this repository:** migration plan and source audit only; no rebuilt JAR yet.

Findings:
- The 1.20.1 branch is a Forge project, not a NeoForge 1.20.1 project.
- Its build uses ForgeGradle and Forge 47.4.18; this is not the requested NeoForge 47.1.106 toolchain.
- Main initialization, config, item registration, permission handling and SimpleChannel networking are Forge API integration points.
- The previous runtime error involving `ResourceLocation.fromNamespaceAndPath(...)` is consistent with compiling against a newer Minecraft API and running on 1.20.1. The 1.20.1 build must use APIs available in 1.20.1, not copy newer calls.
- Upstream has a NeoForge 1.20.2 branch, which is useful as a migration reference, but it is not a 1.20.1 port and must not be relabeled as one.

Decision:
- **Port is legally feasible** under CC BY-NC-SA, provided attribution, change notices, non-commercial use and share-alike terms are respected.
- Build should use ModDevGradle's `net.neoforged.moddev.legacyforge` plugin and explicitly select the NeoForged 1.20.1 artifact `net.neoforged:forge:1.20.1-47.1.106` (the 1.20.1 legacy coordinate).
- The port still requires source-level API review, a successful build, and client/server runtime testing.

## 2. Serene Seasons

**Upstream:** `Glitchfiend/SereneSeasons`, branch `1.20.1`  
**License:** All Rights Reserved  
**Current Modrinth 1.20.1 file examined:** `9.1.0.0`, Forge only.

Findings:
- The 1.20.1 source tree has `Common`, `Forge` and `Fabric` modules; there is no NeoForge module in that branch.
- Forge module uses ForgeGradle, `net.minecraftforge:forge`, Forge mixin configurations and Forge loader metadata.
- `gradle.properties` requires Forge `47.3.0+`; this does not match NeoForge 47.1.106.
- The 1.20.1 backport changelog explicitly records `kill neoforge`.
- The Forge module has a required GlitchCore dependency.
- Modrinth lists the 1.20.1 9.1.0.0 artifact as Forge-only; the current project page listing NeoForge for other game versions does not make this particular file NeoForge-compatible.

Decision:
- **No public modified source or rebuilt JAR will be committed** without written permission from the rights holder.
- Even with permission, a port needs a NeoForge-specific loader module/metadata, compatible GlitchCore, mixin review and runtime testing.

## 3. GlitchCore

**Upstream:** `Glitchfiend/GlitchCore`, branch `1.20.1`  
**License:** All Rights Reserved  
**Current Modrinth 1.20.1 file examined:** `1.0.0.60), Forge only.

Findings:
- The 1.20.1 source tree has `Common`, `Forge` and `Fabric` modules; there is no NeoForge module in that branch.
- Forge module uses ForgeGradle, Forge mixin configurations and Forge loader metadata.
- `gradle.properties` requires Forge `47.3.0+`, above the requested NeoForge 47.1.106 build number.
- The 1.20.1 backport changelog explicitly records `kill neoforge`.
- Modrinth's current overall project page lists NeoForge support for newer game versions, but the specific 1.20.1 file is Forge-only.

Decision:
- **No public modified source or rebuilt JAR will be committed** without written permission from the rights holder.
- This library is a prerequisite for Serene Seasons 9.1.0.0+, so porting Serene Seasons without a compatible GlitchCore is not a complete solution.

## Overall acceptance status

| Project | Source audit | Legal path for public fork | NeoForge 47.1.106 JAR | Runtime verified |
|---|---|---|---|---|
| Nature's Compass | Done | Yes, with CC BY-NC-SA compliance | Not built | No |
| Serene Seasons | Done | Permission required | Not built | No |
| GlitchCore | Done | Permission required | Not built | No |

## Important distinction

A Modrinth project page can list multiple loaders because it contains files for multiple game versions. Always check the exact file's loader and game version. A Forge 1.20.1 JAR is not automatically a NeoForge 1.20.1 JAR.

No project is marked complete until the exact target artifact builds, loads in a client and dedicated server, and passes functional tests.