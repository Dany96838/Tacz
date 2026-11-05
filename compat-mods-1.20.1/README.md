# Compatibilidade NeoForge 1.20.1 — Nature's Compass, Serene Seasons e GlitchCore

## Alvo do projeto

- Minecraft Java 1.20.1
- NeoForge 47.1.106
- Java 17
- Cliente Android/FCL: precisa ser validado em jogo, não apenas compilar.

## Estado inicial da análise (2026-11-04)

Este diretório registra a análise e os patches de compatibilidade. Não contém JARs redistribuídos nem afirma que os mods já foram portados ou testados.

### Nature's Compass

- Página oficial Modrinth: https://modrinth.com/mod/natures-compass
- Histórico/código-fonte oficial: https://github.com/MattCzyr/NaturesCompass
- A linha Forge 1.20.1 1.12.x existe. A página atual também anuncia suporte a NeoForge em versões do projeto, mas isso não prova por si só que exista um artefato NeoForge específico para Minecraft 1.20.1.
- O crash reportado anteriormente incluía `NoSuchMethodError: ResourceLocation.fromNamespaceAndPath(...)` na classe `BiomeSearchEntry`. É necessário reproduzir e identificar qual JAR e qual API foram carregados antes de alterar o código.
- Próximo passo: consultar os arquivos oficiais de 1.20.1 e verificar metadados de loader, dependências e bytecode. Preferir um build oficial compatível, se existir, em vez de um fork desnecessário.

### Serene Seasons

- Página oficial Modrinth: https://modrinth.com/mod/serene-seasons
- A versão Forge 1.20.1 9.1.0.0 exige GlitchCore.
- O changelog do backport 1.20.1 contém notas explícitas sobre a remoção do alvo NeoForge durante o desenvolvimento. Portanto, não se deve presumir compatibilidade direta com NeoForge 47.1.106.
- Licença indicada: ARR (All Rights Reserved). Não republicar JARs, código-fonte modificado ou builds redistribuíveis sem autorização do titular. Preferir patches locais ou obter autorização.

### GlitchCore

- Página oficial Modrinth: https://modrinth.com/mod/glitchcore
- Repositório oficial: https://github.com/Glitchfiend/GlitchCore
- É uma biblioteca de dependência; deve corresponder exatamente ao loader e à versão de Minecraft esperados pelo Serene Seasons.
- Não trocar apenas metadados de loader nem renomear um JAR Forge para NeoForge: isso não corrige incompatibilidades de API.

## Plano de trabalho

1. Obter apenas fontes e artefatos das páginas oficiais e registrar as versões exatas.
2. Inspecionar `META-INF/mods.toml`, dependências, Gradle, mappings e referências às APIs Forge.
3. Testar primeiro se o Nature's Compass oficial para 1.20.1 já funciona no NeoForge 47.1.106. Se o crash `NoSuchMethodError` vier de um build incompatível, testar o build correto antes de criar patch.
4. Para Serene Seasons + GlitchCore, preparar patches de compatibilidade mínimos a partir das fontes oficiais, respeitando as licenças. Não incluir cópias integrais dos projetos nem seus JARs no repositório.
5. Compilar em Java 17 com Gradle e NeoForge 47.1.106; guardar logs e artefatos locais de teste.
6. Testar inicialização, abrir o GUI da bússola e procurar biomas, avançar estações, verificar sincronização cliente/servidor e iniciar um mundo novo.
7. Não marcar como concluído nenhum mod até compilar e passar os testes reais.

## Critérios de aceite

- Compila para Minecraft 1.20.1 / NeoForge 47.1.106.
- Sem `NoSuchMethodError`, `ClassNotFoundException` ou falhas de registro.
- Nature's Compass abre e conclui uma busca de bioma.
- Serene Seasons avança as estações e preserva as funções básicas.
- GlitchCore carrega antes dos mods dependentes.
- Nenhum JAR de terceiros ou código ARR é republicado sem permissão.

## Nota

Esta é a preparação inicial. Ainda não há builds portados nem testes de jogo aprovados. Os próximos commits devem adicionar patches pequenos e auditáveis, não arquivos JAR binários.
