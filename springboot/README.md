# Forum API - Challenge Alura

O **Forum API** é uma solução REST desenvolvida em Java com Spring Boot, projetada para gerenciar tópicos de discussão em um fórum. Este projeto faz parte do desafio "Challenge Alura" e foca em boas práticas de desenvolvimento, persistência de dados e segurança.

## 🚀 Funcionalidades Atuais

O projeto implementa um CRUD completo para a entidade `Tópico`, incluindo:

- **Cadastro de Tópicos**: Registro de dúvidas vinculadas a um autor e curso.
- **Listagem Paginada**: Visualização de tópicos com ordenação e paginação.
- **Busca com Filtros**: Pesquisa por nome do curso e ano de criação.
- **Detalhamento**: Visualização detalhada de um tópico específico.
- **Atualização**: Edição de título e mensagem de tópicos existentes.
- **Exclusão**: Remoção de tópicos do banco de dados.
- **Segurança (Em progresso)**: Autenticação via tokens JWT.

## 🛠️ Tecnologias Utilizadas

- **Java 17**
- **Spring Boot 3**
- **Spring Data JPA**
- **Flyway Migration** (Controle de versão do banco de dados)
- **MySQL 8.0**
- **Maven**
- **Spring Security & Auth0 JWT** (Para autenticação)
- **Lombok** (Para produtividade)

## 📋 Como Rodar o Projeto

1. **Clone o repositório**:
   ```bash
   git clone [url-do-repositorio]
   ```

2. **Configuração do Banco de Dados**:
   - Certifique-se de ter o MySQL instalado e rodando.
   - Abra o arquivo `src/main/resources/application.properties`.
   - Configure sua senha do MySQL na propriedade `spring.datasource.password`.
   - O banco `forum_api` será criado automaticamente se não existir.

3. **Execução**:
   - Via IDE (IntelliJ, VS Code, etc): Execute a classe `ForumApplication`.
   - Via Terminal:
     ```bash
     mvn spring-boot:run
     ```

## 🧪 Como Testar a API

Você pode usar ferramentas como **Postman** ou **Insomnia**. Os principais endpoints são:

- `POST /topicos`: Cadastrar novo tópico.
- `GET /topicos`: Listar todos (aceita parâmetros `nomeCurso` e `ano`).
- `GET /topicos/{id}`: Detalhar tópico.
- `PUT /topicos/{id}`: Atualizar tópico.
- `DELETE /topicos/{id}`: Excluir tópico.

---
Desenvolvido como parte do currículo de Java da Alura.
