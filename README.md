# Random User API (Java)

Projeto criado para testar o consumo de dados da API **Random User** (*https://randomuser.me*), que gera usuários aleatórios.

A ideia central é: **ao disparar uma requisição do tipo POST para o servidor/aplicação**, o sistema faz **fetch** de novos usuários em uma API externa, desserializa a resposta em DTOs e então transforma esses dados em entidades de domínio para uso interno.

> Repo: `opmile/random-user-api`  
> Linguagem: **Java (100%)**

---

## Visão geral do fluxo

Em alto nível, o fluxo de execução é:

1. Entrada (requisição POST / acionamento do fluxo)
2. Consumo da API externa (Random User)
3. Desserialização do JSON em DTOs (`record`)
4. Mapeamento DTO → Entidades de domínio (`User`, `Location`, etc.)
5. Retorno/uso da lista de usuários resultante

O consumo da API externa está centralizado no cliente:

- `RandomUserClient#fetchUsers(int quantity)`  
  - Faz um `GET` para `https://randomuser.me/api?results=<quantity>`
  - Lê o JSON como `String`
  - Converte para `FetchResponse` via Jackson
  - Retorna `List<UserApiDTO>`

---

## Estrutura de pastas

A estrutura principal (em `src/main/java`) segue esta organização:

- `org.example/`
  - `Main.java`  
    Classe de entrada para execução local/demonstração do fluxo. Exemplo: busca usuários e imprime nome/email.
  - `service/`
    - `RandomUserClient.java`  
      Responsável por **consumir a API externa** (Random User) via `java.net.http.HttpClient`.
  - `dto/`
    - `FetchResponse.java`  
      Record que representa o “envelope” da API (contém `results`).
    - `UserApiDTO.java`  
      DTO do usuário vindo da API.
    - `NameApiDTO.java`, `LocationApiDTO.java`, `LoginApiDTO.java`, `DobApiDTO.java`, etc.  
      DTOs auxiliares aninhados conforme a estrutura do JSON.
  - `model/`
    - `User.java`  
      Entidade de domínio “limpa”, usada internamente.
    - `Location.java`  
      Estrutura de localização do domínio.
    - `Gender.java`  
      Enum de domínio com método de conversão `fromText`.
  - `mapper/`
    - `UserMapper.java`  
      Converte `UserApiDTO` → `User`
    - `LocationMapper.java`  
      Converte `LocationApiDTO` → `Location`

---

## Implementação de “endpoint” e consumo via POST

O projeto, do jeito que está no repositório, **não aparenta estar usando Spring Boot/JAX-RS** (não há dependências típicas no `pom.xml`, e o consumo é demonstrado via `Main.java`). Ainda assim, o caso de uso descrito (POST que dispara o fetch externo) é refletido pelo design do fluxo: **um “handler” de POST chamaria diretamente o `RandomUserClient#fetchUsers(quantity)`** e devolveria os usuários processados.

Em outras palavras, o “núcleo” necessário para o endpoint já existe:

- Cliente HTTP: `RandomUserClient`
- DTOs de entrada: `dto/*`
- Mapeamento para domínio: `mapper/*`
- Domínio: `model/*`

Se a intenção for evoluir para um servidor HTTP real, o endpoint POST tenderia a fazer algo como:

- Receber `quantity`
- `client.fetchUsers(quantity)`
- `dtos.stream().map(UserMapper::toEntity).toList()`
- retornar o resultado

---

## Tratamento de exceções: decisões e impactos

### 1) Exceções no consumo HTTP/JSON
O método `fetchUsers` declara:

- `throws IOException, InterruptedException`

Isso é direto e explícito: o chamador decide o que fazer (propagar, encapsular, logar, retornar erro HTTP, etc.). Para um projeto de estudo/teste, isso é aceitável e até didático.

**Ponto de atenção**: em um endpoint POST real, seria recomendado encapsular essas exceções em exceções de aplicação (ex.: `ExternalApiException`) para padronizar retorno de erro e logging.

### 2) Exceções no domínio (ex.: Gender)
`Gender.fromText(String)` lança:

- `IllegalArgumentException` quando o gênero não é reconhecido

Essa escolha é coerente com regras de domínio: dados inválidos não devem ser aceitos silenciosamente. Em contexto de API, isso deveria ser traduzido para um erro de validação/contrato (ex.: HTTP 400), mas no estado atual do projeto é suficiente.

---

## Pontos positivos do projeto

- **Separação clara entre camadas de dados externos e domínio**:  
  DTOs (`dto`) representam o payload externo; entidades (`model`) representam o que o sistema realmente usa.
- **Uso de `record` para DTOs**:  
  Simples, imutável e expressivo para contratos de dados.
- **Cliente HTTP simples e moderno** (`java.net.http.HttpClient`):  
  Evita dependências extras e deixa evidente como o consumo acontece.
- **Mappers dedicados (mesmo que ainda incompletos como estratégia global)**:  
  A presença do pacote `mapper` já aponta para uma direção boa de evolução.

---

## Ponto de melhoria (estrutural): mapeamento Entidade ↔ DTO e SOLID

Apesar de já existirem classes como `UserMapper` e `LocationMapper`, há um **problema estrutural clássico** a ser endereçado no projeto:

### Problema
A necessidade de **criar e padronizar mappers** para realizar o mapeamento entre **entidade** e **DTO**.

Quando esse mapeamento fica **espalhado pela codebase** (ex.: concatenação de nome em vários lugares, conversões duplicadas, transformação de estruturas repetida em múltiplas classes), isso:

- **quebra fortemente princípios SOLID**, principalmente o **SRP (Single Responsibility Principle)**  
  porque:
  - controllers/handlers começam a “entender” detalhes de transformação
  - services passam a acumular regra de conversão
  - o domínio fica acoplado ao formato da API externa
- aumenta o custo de mudança (qualquer alteração no DTO externo pode exigir mudanças em muitos pontos)
- dificulta testes (transformações duplicadas espalham bugs)

### Direção recomendada
- Centralizar o mapeamento em componentes próprios (mappers) e garantir consistência:
  - `UserMapper` (DTO externo → domínio)
  - `LocationMapper` (DTO externo → domínio)
  - e, se houver API de saída, também domínio → DTO de resposta
- Avaliar bibliotecas como **MapStruct** (quando o projeto crescer), para reduzir boilerplate e evitar erro humano.
- Definir convenções: “toda conversão passa por mapper; domínio não conhece DTO”.

---

## Como rodar (Maven)

O `pom.xml` indica:
- Java **23**
- Dependência: `jackson-databind`

Para executar o exemplo atual (via `Main.java`), basta compilar/rodar pela IDE ou via Maven (conforme seu setup).

---

## Conclusão

O projeto cumpre bem o propósito de estudo: **consumir uma API externa**, desserializar o payload e transformar em um modelo de domínio utilizável. Ao mesmo tempo, o próximo passo natural para evoluir com qualidade é **corrigir o aspecto estrutural do mapeamento DTO ↔ entidade**, centralizando-o em mappers e reduzindo acoplamento — o que melhora manutenção e alinha o código com SOLID (especialmente SRP).

Se você quiser, eu adapto este README para o cenário “servidor com endpoint POST real” (Spring Boot/JAX-RS), incluindo exemplos de rota, payload e respostas.
