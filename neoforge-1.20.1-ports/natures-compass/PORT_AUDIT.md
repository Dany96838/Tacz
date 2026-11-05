# Nature's Compass — auditoria da base Forge 1.20.1

**Destino:** Minecraft 1.20.1 / NeoForge 47.1.106  
**Base examinada:** `MattCzyr/NaturesCompass`, branch `forge-1.20.1`  
**Versão de origem:** 1.20.1-1.12.0-forge  
**Licença da origem:** CC BY-NC-SA 4.0 (manter atribuição, indicar alterações, mesma licença e uso não comercial).

## Resultado desta etapa

A branch de origem existe e contém o projeto completo (Gradle, fontes Java e recursos). Ela **não é uma base NeoForge pronta**: o build ainda usa ForgeGradle e Forge 47.4.18. Não é seguro apenas trocar o nome do loader no arquivo TOML.

Arquivos centrais examinados:
- `build.gradle`: plugin `net.minecraftforge.gradle` e dependência `net.minecraftforge:forge`.
- `gradle.properties`: Forge 47.4.18, range Forge `[47,)`, versão identificada como `1.20.1-1.12.0-forge`.
- `NaturesCompass.java`: inicialização FML, registro de configuração, canal de rede SimpleChannel, eventos e permissões.
- `NaturesCompassRegistry.java`: registro de item por `ForgeRegistries` e `RegisterEvent`.

## Migração necessária

1. Substituir o build ForgeGradle por configuração NeoForge para 1.20.1, fixando exatamente NeoForge 47.1.106 e Java 17.
2. Migrar metadados de `META-INF/mods.toml` para as coordenadas, ranges e dependências corretos de NeoForge, preservando o modid `naturescompass`.
3. Migrar imports/eventos de `net.minecraftforge.*` para os equivalentes `net.neoforged.neoforge.*` existentes nessa linha; revisar especialmente inicialização, configuração, registro e eventos de ciclo de vida.
4. Migrar o canal SimpleChannel para a API de rede compatível com NeoForge 47.1.106. Conferir todos os quatro pacotes e o fluxo cliente/servidor; não fazer substituição textual cega.
5. Revisar a API de permissões. A classe principal usa `PermissionGatherEvent.Nodes`, `PermissionNode` e `PermissionTypes`; a disponibilidade e assinatura exatas precisam ser confirmadas contra o artefato 47.1.106. Se não houver equivalente, preservar o comportamento padrão seguro (teleporte negado) e adaptar a integração sem remover silenciosamente a proteção.
6. Corrigir a criação de `ResourceLocation` para a API efetivamente disponível na versão-alvo. Isso também aborda o erro de linkage relatado anteriormente com `ResourceLocation.fromNamespaceAndPath(...)`: compilar e executar contra a versão real, não presumir que uma chamada existe.
7. Compilar, iniciar cliente e servidor dedicados e testar busca de bioma, sincronização, busca seguinte, teleporte/permissões, configuração e entrada na aba Ferramentas e Utilidades.

## Critério de aceite

- Build executado com Java 17 e NeoForge **47.1.106**.
- JAR gerado e metadados identificando NeoForge, sem dependência Forge.
- Cliente inicia sem crash e item aparece na aba correta.
- Busca de bioma funciona em servidor dedicado e cliente conectado.
- Pacotes de rede não geram erros de codec/direção.
- Testes de teleporte e permissão confirmam que o padrão continua negado.
- Nenhum erro `NoSuchMethodError` de `ResourceLocation`.

## Limitação atual

Esta auditoria não é um port compilado. Nenhum JAR foi produzido nem validado em runtime nesta etapa. O próximo commit de implementação deve incluir as fontes licenciadas com os avisos originais, as alterações identificadas acima e um build verificável.