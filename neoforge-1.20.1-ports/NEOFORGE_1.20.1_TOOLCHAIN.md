# Correção de identificação do loader e configuração do build

O alvo confirmado é **NeoForge para Minecraft 1.20.1, build 47.1.106**, Java 17.

A numeração 47.1.106 é válida para a linha NeoForge de 1.20.1. Nessa linha, o artefato Maven tem o identificador legado:
`net.neoforged:forge:1.20.1-47.1.106`.

Não confundir com a linha moderna do NeoForge, usada a partir de Minecraft 1.20.2, que usa coordenadas `net.neoforged:neoforge:20.2.x` e versões subsequentes.

## Ferramenta Gradle compatível

Para 1.20.1, a ferramenta oficial atual de desenvolvimento é o plugin legado:
`net.neoforged.moddev.legacyforge`.

A configuração deve selecionar explicitamente NeoForge, e não Forge:
`legacyForge { neoForgeVersion = "1.20.1-47.1.106" }`
A propriedade `neoForgeVersion` seleciona o grupo Maven `net.neoforged`; `forgeVersion` selecionaria `net.minecraftforge`.

Usar plugin e versão compatíveis (ModDevGradle 2.0.87 ou superior, pois o suporte à coordenada NeoForge 1.20.1 foi corrigido nessa versão).

## Referências

- Maven oficial NeoForged: `net.neoforged:forge:1.20.1-47.1.106`.
- Documentação ModDevGradle Legacy: suporte a Forge/NeoForge até 1.20.1.
- Issue #239 do ModDevGradle: suporte à propriedade `neoForgeVersion` para NeoForge 1.20.1, corrigido no ModDevGradle 2.0.87.

## Status

Esta nota corrige a nomenclatura no planejamento. Ela não significa que o Nature's Compass já foi migrado ou compilado. O build final deve resolver exatamente o artefato NeoForged acima e validar a saída no runtime.