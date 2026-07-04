# Brasileirao API

![Java](https://img.shields.io/badge/Java-17-E76F00?style=flat&logo=openjdk&logoColor=white)
![Spring Boot](https://img.shields.io/badge/Spring_Boot-3.3.5-6DB33F?style=flat&logo=springboot&logoColor=white)
![License](https://img.shields.io/badge/License-MIT-blue)

API REST para captura em tempo real de eventos de partidas do Brasileirao, incluindo gols, cartoes e penaltis, utilizando web scraping com Jsoup.

## Funcionalidades

- **Scraping em tempo real** - Captura automatica de dados de partidas via Google Search
- **Gestao de Equipes** - CRUD completo de equipes participantes
- **Gestao de Partidas** - CRUD completo de partidas
- **API RESTful** - Endpoints documentados com Swagger/OpenAPI
- **Agendamento automatico** - Verificacao periodica das partidas em andamento

## Tecnologias

| Tecnologia | Versao | Descricao |
|------------|--------|-----------|
| Java | 17 | Linguagem principal |
| Spring Boot | 3.3.5 | Framework web e DI |
| Spring Data JPA | - | Persistencia |
| H2 Database | - | Banco de dados embedded |
| Jsoup | 1.16.1 | Web scraping |
| Swagger/OpenAPI | 2.1.0 | Documentacao da API |
| Lombok | 1.18.34 | Reducao de boilerplate |
| Maven | - | Gerenciamento de dependencias |

## Estrutura do Projeto

```
src/main/java/br/com/brasileirao_api/
├── config/           # Configuracoes (Swagger, ModelMapper)
├── controller/       # Endpoints REST
│   ├── EquipeController.java
│   └── PartidaController.java
├── dto/              # Data Transfer Objects
│   ├── equipe/
│   └── partida/
├── exception/        # Tratamento de erros
├── model/            # Entidades JPA
│   ├── Equipe.java
│   └── Partida.java
├── repository/       # Acesso a dados
├── service/          # Logica de negocio
│   ├── EquipeService.java
│   ├── PartidaService.java
│   └── ScrapingService.java
├── task/             # Agendamentos
└── util/             # Utilitarios de scraping
```

## Como Executar

1. **Clone o repositorio:**
   ```bash
   git clone https://github.com/edvaldovitor250/brasileirao-api.git
   cd brasileirao-api
   ```

2. **Configure o banco de dados:**
   ```bash
   cp src/main/resources/application.properties.example src/main/resources/application-dev.properties
   # Edite application-dev.properties conforme necessario
   ```

3. **Execute a aplicacao:**
   ```bash
   mvn spring-boot:run -Dspring-boot.run.profiles=dev
   ```

4. **Acesse a documentacao:**
   ```
   http://localhost:8080/swagger-ui/index.html
   ```

## Endpoints

| Metodo | Endpoint | Descricao |
|--------|----------|-----------|
| GET | `/api/v1/equipes` | Listar todas as equipes |
| GET | `/api/v1/equipes/{id}` | Buscar equipe por ID |
| POST | `/api/v1/equipes` | Criar nova equipe |
| PUT | `/api/v1/equipes/{id}` | Atualizar equipe |
| GET | `/api/v1/partidas` | Listar todas as partidas |
| GET | `/api/v1/partidas/{id}` | Buscar partida por ID |
| POST | `/api/v1/partidas` | Criar nova partida |
| PUT | `/api/v1/partidas/{id}` | Atualizar partida |

## Fluxo do Scraping

```
[Agendamento] → [ScrapingService] → [ScrapingUtil] → [Jsoup/Google] → [PartidaService] → [Banco de Dados]
```

O sistema verifica automaticamente a cada 30 segundos se ha partidas em andamento e atualiza os dados em tempo real.

## Melhorias Futuras

- Notificacoes em tempo real via WebSocket
- Dashboard visual dos eventos
- Exportacao de relatorios em CSV/PDF
- Suporte a multiplos campeonatos

## Autor

**Edvaldo Vitor De Castro Souza**
- GitHub: [@edvaldovitor250](https://github.com/edvaldovitor250)
- Email: edvaldovitor250@gmail.com
