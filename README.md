<div align="center">

# Brasileirao API

### API REST para monitoramento em tempo real de partidas do Campeonato Brasileiro

[![Java](https://img.shields.io/badge/Java-17-E76F00?style=for-the-badge&logo=openjdk&logoColor=white)](https://openjdk.org/projects/jdk/17/)
[![Spring Boot](https://img.shields.io/badge/Spring_Boot-3.3.5-6DB33F?style=for-the-badge&logo=springboot&logoColor=white)](https://spring.io/projects/spring-boot)
[![Maven](https://img.shields.io/badge/Maven-3.9-C71A36?style=for-the-badge&logo=apache&logoColor=white)](https://maven.apache.org/)
[![H2 Database](https://img.shields.io/badge/H2-Database-003545?style=for-the-badge&logo=databricks&logoColor=white)](https://www.h2database.com/)
[![License](https://img.shields.io/badge/License-MIT-blue?style=for-the-badge)](LICENSE)

<br/>

Sistema automatizado que captura **gols, cartoes, penaltis e placares** em tempo real utilizando web scraping, disponibilizando os dados via API REST documentada com Swagger.

[Como Executar](#como-executar) | [Endpoints](#endpoints) | [Arquitetura](#arquitetura) | [Tecnologias](#tecnologias)

</div>

---

## Funcionalidades

| Recurso | Status | Descricao |
|---------|--------|-----------|
| Scraping em tempo real | Feito | Captura automatica via Google Search a cada 30s |
| Gestao de Equipes | Feito | CRUD completo com validacao |
| Gestao de Partidas | Feito | CRUD completo com validacao |
| Documentacao API | Feito | Swagger/OpenAPI interativo |
| Agendamento | Feito | Verificacao periodica via Spring Scheduler |
| Notificacoes WebSocket | Planejado | Alertas em tempo real |
| Dashboard | Planejado | Painel visual de monitoramento |

---

## Arquitetura

```
┌─────────────────────────────────────────────────────────────────────┐
│                        BRASILEIRAO API                              │
├─────────────────────────────────────────────────────────────────────┤
│                                                                     │
│  ┌──────────┐    ┌──────────────┐    ┌────────────┐                │
│  │  Agenda  │───>│ ScrapingSvc  │───>│ Jsoup/HTTP │                │
│  │ (30 sec) │    │              │    │  Google    │                │
│  └──────────┘    └──────┬───────┘    └────────────┘                │
│                         │                                           │
│                         v                                           │
│  ┌──────────┐    ┌──────────────┐    ┌────────────┐                │
│  │ Partida  │<───│  PartidaSvc  │───>│  H2 DB     │                │
│  │Controller│    │              │    │ (HikariCP) │                │
│  └──────────┘    └──────────────┘    └────────────┘                │
│                                                                     │
│  ┌──────────┐    ┌──────────────┐                                  │
│  │ Equipe   │<───│  EquipeSvc   │                                  │
│  │Controller│    │              │                                  │
│  └──────────┘    └──────────────┘                                  │
│                                                                     │
│  ┌──────────────────────────────────┐                              │
│  │  Swagger UI  /api-docs           │                              │
│  └──────────────────────────────────┘                              │
└─────────────────────────────────────────────────────────────────────┘
```

---

## Pre-requisitos

- **Java 17** ou superior
- **Maven 3.8+** (ou usar o wrapper `./mvnw`)
- **Git**

---

## Como Executar

### 1. Clone o repositorio

```bash
git clone https://github.com/edvaldovitor250/brasileirao-api.git
cd brasileirao-api
```

### 2. Configure as variaveis de ambiente (opcional)

Caso nao configure, a aplicacao usa H2 embedded com valores padrao.

```bash
# Linux/Mac
export DB_URL=jdbc:h2:file:./data/brasileirao;AUTO_SERVER=TRUE
export DB_USERNAME=sa
export DB_PASSWORD=

# Windows (PowerShell)
$env:DB_URL="jdbc:h2:file:./data/brasileirao;AUTO_SERVER=TRUE"
$env:DB_USERNAME="sa"
$env:DB_PASSWORD=""
```

### 3. Execute a aplicacao

```bash
# Com Maven local
mvn spring-boot:run

# Ou com o wrapper
./mvnw spring-boot:run
```

### 4. Acesse a documentacao

| Recurso | URL |
|---------|-----|
| Swagger UI | http://localhost:8080/swagger-ui/index.html |
| H2 Console | http://localhost:8080/h2 |

---

## Endpoints

### Equipes

| Metodo | Endpoint | Descricao | Body |
|--------|----------|-----------|------|
| `GET` | `/api/v1/equipes` | Listar todas as equipes | - |
| `GET` | `/api/v1/equipes/{id}` | Buscar equipe por ID | - |
| `POST` | `/api/v1/equipes` | Criar nova equipe | `EquipeDTO` |
| `PUT` | `/api/v1/equipes/{id}` | Atualizar equipe | `EquipeDTO` |

### Partidas

| Metodo | Endpoint | Descricao | Body |
|--------|----------|-----------|------|
| `GET` | `/api/v1/partidas` | Listar todas as partidas | - |
| `GET` | `/api/v1/partidas/{id}` | Buscar partida por ID | - |
| `POST` | `/api/v1/partidas` | Criar nova partida | `PartidaDTO` |
| `PUT` | `/api/v1/partidas/{id}` | Atualizar partida | `PartidaDTO` |

### Exemplo de Request

**Criar equipe:**

```json
POST /api/v1/equipes
{
  "nomeEquipe": "Flamengo",
  "urlLogoEquipe": "https://example.com/flamengo.png"
}
```

**Criar partida:**

```json
POST /api/v1/partidas
{
  "nomeEquipeCasa": "Flamengo",
  "nomeEquipeVisitante": "Palmeiras",
  "localPartida": "Maracana",
  "dataHoraPartida": "2025-07-15"
}
```

---

## Fluxo do Scraping

```
┌─────────────┐     ┌─────────────┐     ┌─────────────┐     ┌─────────────┐
│   Schedule  │────>│  Scraping   │────>│    Jsoup    │────>│  Partida    │
│   (30s)     │     │  Service    │     │  (Google)   │     │  Service    │
└─────────────┘     └─────────────┘     └─────────────┘     └──────┬──────┘
                                                                   │
                                                                   v
                                                            ┌─────────────┐
                                                            │  H2 Database │
                                                            └─────────────┘
```

1. **Agendamento** - `PartidaTask` executa a cada 30 segundos
2. **Scraping** - `ScrapingService` identifica partidas em andamento
3. **Captura** - `ScrapingUtil` monta URL e extrai dados via Jsoup
4. **Atualizacao** - `PartidaService` persiste os dados atualizados

---

## Estrutura do Projeto

```
src/main/java/br/com/brasileirao_api/
│
├── config/                  # Configuracoes da aplicacao
│   ├── ModelMapperConfig    # Configuracao do ModelMapper
│   └── SwaggerConfig        # Configuracao do OpenAPI/Swagger
│
├── controller/              # Endpoints REST (Camada de apresentacao)
│   ├── EquipeController     # Endpoints de equipes
│   └── PartidaController    # Endpoints de partidas
│
├── dto/                     # Data Transfer Objects
│   ├── equipe/              # DTOs de equipe (entrada/saida)
│   └── partida/             # DTOs de partida (entrada/saida)
│
├── exception/               # Tratamento centralizado de erros
│   ├── StandardError        # Padrao de erro da API
│   ├── NotFoundException    # Recurso nao encontrado (404)
│   ├── BadRequestException  # Requisicao invalida (400)
│   └── ResourceExceptionHandler  # Handler global
│
├── model/                   # Entidades JPA
│   ├── Equipe               # Entidade de equipe
│   └── Partida              # Entidade de partida
│
├── repository/              # Acesso a dados (Spring Data)
│   ├── EquipeRepository     # Repositorio de equipes
│   └── PartidaRepository    # Repositorio de partidas
│
├── service/                 # Logica de negocio
│   ├── EquipeService        # Servico de equipes
│   ├── PartidaService       # Servico de partidas
│   └── ScrapingService      # Servico de web scraping
│
├── task/                    # Agendamentos
│   └── PartidaTask          # Task de verificacao periodica
│
└── util/                    # Utilitarios
    ├── ScrapingUtil         # Logica de parsing HTML
    ├── ScrapingConstants    # Constantes de seletores CSS
    ├── StatusPartida        # Enum de status da partida
    └── DataUtil             # Utilitario de formatacao
```

---

## Variaveis de Ambiente

| Variavel | Padrao | Descricao |
|----------|--------|-----------|
| `DB_URL` | `jdbc:h2:file:./data/brasileirao;AUTO_SERVER=TRUE` | URL de conexao com o banco |
| `DB_DRIVER` | `org.h2.Driver` | Driver JDBC |
| `DB_USERNAME` | `sa` | Usuario do banco |
| `DB_PASSWORD` | *(vazio)* | Senha do banco |
| `DB_DDL_AUTO` | `update` | Estrategia de DDL |
| `H2_CONSOLE` | `true` | Habilitar console H2 |

---

## Stack Tecnica

| Camada | Tecnologia | Versao |
|--------|------------|--------|
| Linguagem | Java | 17 |
| Framework | Spring Boot | 3.3.5 |
| Persistencia | Spring Data JPA | 3.3.5 |
| Banco | H2 Database | Embedded |
| Pool de Conexao | HikariCP | (via Spring Boot) |
| Web Scraping | Jsoup | 1.16.1 |
| Mapeamento | ModelMapper | 3.1.0 |
| Documentacao | SpringDoc OpenAPI | 2.1.0 |
| Boilerplate | Lombok | 1.18.34 |
| Build | Maven | 3.9+ |

---

## Melhorias Futuras

- [ ] Notificacoes em tempo real via WebSocket
- [ ] Dashboard visual de monitoramento
- [ ] Exportacao de relatorios em CSV/PDF
- [ ] Suporte a multiplos campeonatos
- [ ] Cache de dados com Redis
- [ ] Testes de integracao completos
- [ ] CI/CD com GitHub Actions

---

## Contribuindo

1. Fork o projeto
2. Crie uma branch para sua feature (`git checkout -b feature/nova-funcionalidade`)
3. Commit suas mudancas (`git commit -m 'feat: add nova funcionalidade'`)
4. Push para a branch (`git push origin feature/nova-funcionalidade`)
5. Abra um Pull Request

---

## Autor

**Edvaldo Vitor De Castro Souza**

[![GitHub](https://img.shields.io/badge/GitHub-100000?style=for-the-badge&logo=github&logoColor=white)](https://github.com/edvaldovitor250)
[![Email](https://img.shields.io/badge/Email-D14836?style=for-the-badge&logo=gmail&logoColor=white)](mailto:edvaldovitor250@gmail.com)

---

<div align="center">

Feito com dedicacao para o futebol brasileiro

</div>
