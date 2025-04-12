<body>
  <h1>📄 Projeto: <strong>Brasileirão Live Scraper</strong> ⚽🚀</h1>
  <p><em>Automação em tempo real para captura de eventos de partidas de futebol</em>, como gols ⚽, pênaltis 🟥, cartões amarelos 🟨 e vermelhos 🟥, usando <strong>Spring Boot</strong>, <strong>Jsoup</strong>, e práticas avançadas de <strong>web scraping</strong>, com armazenamento em banco de dados relacional.</p>

  <h2>🧩 Descrição Geral</h2>
  <ul>
    <li>✅ <strong>Gols</strong></li>
    <li>✅ <strong>Pênaltis</strong></li>
    <li>✅ <strong>Cartões Amarelos 🟨</strong></li>
    <li>✅ <strong>Cartões Vermelhos 🟥</strong></li>
  </ul>
  <p>Todas as informações são extraídas diretamente de fontes públicas e salvas no banco de dados para consultas e análises futuras.</p>
  <p>O sistema permite ainda:</p>
  <ul>
    <li>📋 <strong>Gestão de Equipes</strong></li>
    <li>📋 <strong>Gestão de Partidas</strong></li>
    <li>🌐 <strong>API RESTful completa</strong> para consumo dos dados via endpoints</li>
  </ul>

  <h2>🚀 Tecnologias e Ferramentas Utilizadas</h2>
  <table>
    <tr>
      <th>Tecnologia</th>
      <th>Descrição</th>
    </tr>
    <tr>
      <td><img src="https://skillicons.dev/icons?i=java" width="20"/> Java</td>
      <td>Linguagem principal de desenvolvimento da aplicação.</td>
    </tr>
    <tr>
      <td><img src="https://skillicons.dev/icons?i=spring" width="20"/> Spring Boot</td>
      <td>Framework para construção da API REST e gestão das dependências.</td>
    </tr>
    <td><img src="https://github.com/user-attachments/assets/4171ebe4-ba0e-4711-9a87-e1ca81e9d228" width="20"/> Jsoup</td>
<td>Biblioteca para extração e análise de dados HTML (web scraping).</td>
</tr>
    <tr>
      <td><img src="https://skillicons.dev/icons?i=maven" width="20"/> Maven</td>
      <td>Gerenciamento de dependências e build da aplicação.</td>
    </tr>
    <tr>
      <td><img src="https://skillicons.dev/icons?i=mysql" width="20"/> MySQL</td>
      <td>Banco de dados para armazenamento das informações.</td>
    </tr>
    <tr>
      <td><img src="https://github.com/user-attachments/assets/c04e7009-57e8-44b5-9b07-a542badafb4b" width="20" alt="Swagger logo"/> Swagger/OpenAPI</td>
<td>Documentação e testes dos endpoints da API.</td>
    </tr>
    <tr>
  <td><img src="https://github.com/user-attachments/assets/5490b9d1-c366-4b05-9571-fffb96cc8916" width="20" alt="SLF4J Logger logo"/> SLF4J / Logger</td>
  <td>Logs para monitoramento da aplicação.</td>
</tr>
  </table>

  <h2>⚙️ Como Funciona</h2>
  <h3>🔗 1. Captura em tempo real</h3>
  <p>O serviço <strong>ScrapingService</strong> monta URLs dinâmicas e utiliza o <strong>Jsoup</strong> para capturar dados das partidas em tempo real.</p>
  <p>Os dados são processados e atualizados no banco de dados através do <strong>PartidaService</strong>.</p>

  <h3>🧩 2. Atualização automática do banco de dados</h3>
  <ul>
    <li>Verifica partidas em andamento.</li>
    <li>Atualiza tempo de jogo, placares, gols e cartões.</li>
    <li>Evita duplicidade de atualizações.</li>
  </ul>

  <h3>🌐 3. API RESTful</h3>
  <p>Consulte ou gerencie as informações via endpoints:</p>
  <ul>
    <li><strong>/api/v1/equipes</strong>: Consultar, inserir e alterar equipes.</li>
    <li><strong>/api/v1/partidas</strong>: Consultar, inserir e alterar partidas.</li>
  </ul>

  <h3>🗂️ Fluxo do Processo</h3>
  <p>[ScrapingService] 🔄 → [ScrapingUtil monta URL] 🌐 → [Jsoup captura os dados] 📋 → [Atualiza banco via PartidaService] 🗃️</p>

  <h2>💻 Exemplo de Execução</h2>
  <ol>
    <li>🚀 Inicie a aplicação:
      <pre><code>mvn spring-boot:run</code></pre>
    </li>
    <li>🌐 Acesse o Swagger UI:
      <pre><code>http://localhost:8080/swagger-ui/index.html</code></pre>
    </li>
    <li>✅ O bot inicia automaticamente a verificação das partidas em tempo real.</li>
  </ol>

  <h2>🧩 Estrutura do Projeto</h2>
  <pre>
br.com.brasileirao_api
├── controller
│   ├── EquipeController.java
│   └── PartidaController.java
├── service
│   ├── EquipeService.java
│   ├── PartidaService.java
│   └── ScrapingService.java
├── dto
│   └── DTOs de Equipe e Partida
├── util
│   ├── ScrapingUtil.java
│   └── StatusPartida.java
├── model
│   └── Entidades JPA de Equipe e Partida
└── repository
    ├── EquipeRepository.java
    └── PartidaRepository.java
  </pre>

  <h2>📦 Boas Práticas Aplicadas</h2>
  <ul>
    <li>✅ Injeção de dependências com Spring Boot</li>
    <li>✅ Utilização de DTOs para transferir dados com segurança</li>
    <li>✅ Arquitetura limpa com separação de responsabilidades</li>
    <li>✅ Logs detalhados para auditoria dos processos</li>
    <li>✅ Documentação automática com Swagger/OpenAPI</li>
    <li>✅ Web scraping estruturado e com tratamento de erros</li>
  </ul>

  <h2>🌟 Possíveis melhorias futuras</h2>
  <ul>
    <li>🚀 Agendamento automático de execuções com Spring Scheduler.</li>
    <li>🔔 Notificações em tempo real via WebSocket.</li>
    <li>📊 Dashboard visual em tempo real dos eventos dos jogos.</li>
    <li>📝 Exportação de relatórios em CSV ou PDF.</li>
  </ul>

  <h2>🧩 Conclusão</h2>
  <p>Este projeto automatiza a captura de dados ao vivo de partidas de futebol com uma arquitetura moderna, robusta e escalável.</p>
  <p>Ideal para alimentar sistemas de análise esportiva, casas de apostas ou criação de banco de dados de estatísticas de partidas em tempo real.</p>

</body>
