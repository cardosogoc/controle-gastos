# 💰 Controle de Gastos

[![Docker Hub](https://img.shields.io/badge/Docker%20Hub-cardosogoc%2Fcontrole--gastos-2496ED?logo=docker&logoColor=white)](https://hub.docker.com/r/cardosogoc/controle-gastos)

API REST para controle financeiro pessoal. Cada usuário cadastra suas categorias de receita e despesa, registra lançamentos e consulta o resumo mensal. A autenticação é feita com **JWT** e a API é documentada com **Swagger (OpenAPI)**.

Projeto de portfólio desenvolvido com Java e Spring Boot. O frontend em Angular é a próxima etapa.

---

## 📌 Sumário

- [Funcionalidades](#-funcionalidades)
- [Tecnologias](#-tecnologias)
- [Arquitetura](#-arquitetura)
- [Modelo de domínio](#-modelo-de-domínio)
- [Como executar](#-como-executar)
- [Documentação da API (Swagger)](#-documentação-da-api-swagger)
- [Autenticação JWT](#-autenticação-jwt)
- [Endpoints](#-endpoints)
- [Banco de dados e migrações](#-banco-de-dados-e-migrações)
- [Roadmap](#-roadmap)
- [Autor](#-autor)

---

## ✨ Funcionalidades

- Cadastro e autenticação de usuários com JWT
- Gerenciamento de perfil (consultar, editar e excluir a própria conta)
- CRUD de categorias por usuário
- CRUD de lançamentos financeiros (receitas e despesas)
- Listagem de lançamentos por mês e ano
- Resumo financeiro mensal
- Isolamento de dados: cada usuário acessa apenas os seus próprios dados
- Documentação interativa com Swagger UI

---

## 🛠 Tecnologias

| Camada | Tecnologia |
|---|---|
| Linguagem | Java |
| Framework | Spring Boot |
| Segurança | Spring Security + JWT (stateless) |
| Persistência | Spring Data JPA / Hibernate |
| Banco de dados | PostgreSQL |
| Migrações | Flyway |
| Documentação | Swagger / OpenAPI (springdoc) |
| Utilitários | Lombok, Bean Validation |

---

## 🏗 Arquitetura

O projeto é organizado em camadas:

```
com.portifolio.controlegastos
├── controller          # Endpoints REST
│   └── docs            # Interfaces com as anotações do Swagger
├── business
│   ├── service         # Regras de negócio
│   └── dto             # Objetos de entrada e saída (Request/Response)
└── infrastructure
    ├── entity          # Entidades JPA
    ├── repository      # Acesso a dados
    └── security        # Configuração de segurança e JWT
```

As anotações do Swagger ficam em interfaces separadas (`*ControllerDocs`) para manter os controllers limpos.

---

## 🗂 Modelo de domínio

- **Usuario**: `id`, `nome`, `email`, `senha` (criptografada com BCrypt), `telefone`
- **Categoria**: `nome`, `tipo` (`RECEITA` ou `DESPESA`) e o usuário dono
- **Lancamento**: lançamento financeiro vinculado a uma categoria e a um usuário. O tipo (receita ou despesa) vem da categoria.

**Regra de unicidade:** o mesmo usuário pode ter categorias com o mesmo nome desde que sejam de tipos diferentes (por exemplo, "Outros" como `RECEITA` e como `DESPESA`). A unicidade é garantida por uma constraint composta no banco: `usuario_id + nome + tipo`.

---

## 🚀 Como executar

Há duas formas de rodar o projeto: com **Docker** (recomendada, não exige instalar Java nem PostgreSQL) ou **localmente**.

### Com Docker (recomendado)

**Pré-requisito:** [Docker](https://www.docker.com/) instalado e em execução.

1. Clone o repositório:

```bash
git clone https://github.com/cardosogoc/<nome-do-repositorio>.git
cd <nome-do-repositorio>
```

2. Suba a aplicação e o banco de dados:

```bash
docker compose up --build
```

O Compose sobe dois containers: a API e o PostgreSQL. As migrações do Flyway rodam automaticamente na inicialização e criam as tabelas.

3. Acesse:

- **API:** http://localhost:8080
- **Swagger UI:** http://localhost:8080/swagger-ui/index.html

Para parar, use `Ctrl+C` ou `docker compose down`. Para apagar também os dados do banco, use `docker compose down -v`.

> ⚠️ As credenciais do banco (`admin/admin`) e a chave do JWT definidas no `docker-compose.yml` são **apenas para desenvolvimento**. Não as reutilize em produção.

> 💡 Se a porta `5432` já estiver em uso (por exemplo, por um PostgreSQL instalado na sua máquina), altere o mapeamento de portas do serviço `db` no `docker-compose.yml`.

#### Imagem no Docker Hub

A imagem da API também está publicada no [Docker Hub](https://hub.docker.com/r/cardosogoc/controle-gastos):

```bash
docker pull cardosogoc/controle-gastos:1.0
```

A imagem contém **apenas a API**. Para rodá-la sozinha, é necessário ter um PostgreSQL acessível e informar as variáveis de ambiente `SPRING_DATASOURCE_URL`, `SPRING_DATASOURCE_USERNAME`, `SPRING_DATASOURCE_PASSWORD`, `DB_PASSWORD` e `JWT_SECRET`. Por isso, o `docker-compose.yml` é o caminho mais simples.

### Execução local (sem Docker)

**Pré-requisitos:**

- JDK (versão definida no `pom.xml`)
- Maven (ou o wrapper `./mvnw` incluído no projeto)
- PostgreSQL em execução

**Passo a passo:**

1. Clone o repositório:

```bash
git clone https://github.com/cardosogoc/<nome-do-repositorio>.git
cd <nome-do-repositorio>
```

2. Crie o banco de dados:

```sql
CREATE DATABASE controle_gastos;
```

3. Confira a conexão no `application.yaml`. Por padrão, a aplicação usa o banco `controle_gastos` em `localhost:5432` com o usuário `postgres`:

```yaml
spring:
  datasource:
    url: jdbc:postgresql://localhost:5432/controle_gastos
    username: postgres
    password: ${DB_PASSWORD}

jwt:
  secret: ${JWT_SECRET}
```

4. Defina as variáveis de ambiente `DB_PASSWORD` (senha do seu PostgreSQL) e `JWT_SECRET` (chave com pelo menos 32 caracteres):

```bash
# Linux / macOS
export DB_PASSWORD=sua_senha
export JWT_SECRET=uma_chave_secreta_com_pelo_menos_32_caracteres
```

```powershell
# Windows (PowerShell)
$env:DB_PASSWORD="sua_senha"
$env:JWT_SECRET="uma_chave_secreta_com_pelo_menos_32_caracteres"
```

> ⚠️ Nunca versione senhas ou a chave secreta do JWT. Use sempre variáveis de ambiente.

5. Execute a aplicação:

```bash
./mvnw spring-boot:run
```

As migrações do Flyway rodam automaticamente na inicialização e criam as tabelas.

A API sobe em `http://localhost:8080`.

---

## 📖 Documentação da API (Swagger)

Com a aplicação rodando, acesse:

- **Swagger UI:** http://localhost:8080/swagger-ui/index.html
- **OpenAPI (JSON):** http://localhost:8080/v3/api-docs

### Como testar rotas protegidas pelo Swagger

1. Cadastre um usuário em `POST /usuario`
2. Faça login em `POST /usuario/login` e copie o token retornado
3. Clique no botão **Authorize** (cadeado) no topo da página
4. Cole o token e confirme. O Swagger envia o header `Authorization: Bearer <token>` automaticamente
5. Chame qualquer endpoint protegido

> Se o token retornado pelo login já vier com o prefixo `Bearer `, remova o prefixo ao colar no Authorize, porque o Swagger adiciona sozinho.

---

## 🔐 Autenticação JWT

A API é **stateless**: não existe sessão no servidor. A cada requisição protegida, o cliente envia o token no header.

### Fluxo

```
1. POST /usuario          → cadastra o usuário (público)
2. POST /usuario/login    → valida e-mail e senha, retorna o token JWT (público)
3. Demais requisições     → enviam o header Authorization com o token
```

### Header esperado

```
Authorization: Bearer <seu_token_jwt>
```

### Detalhes

- Senhas armazenadas com **BCrypt**
- O token expira em **1 hora**. Depois disso, a API responde `401` e é preciso fazer login novamente
- Um filtro (`JwtRequestFilter`) valida o token antes de cada requisição protegida
- CSRF desativado, o que é normal em APIs REST stateless com JWT

### Rotas públicas

| Método | Rota |
|---|---|
| `POST` | `/usuario` |
| `POST` | `/usuario/login` |
| — | `/swagger-ui/**`, `/v3/api-docs/**` |

Todas as outras rotas exigem token válido.

---

## 📡 Endpoints

🔒 = exige o header `Authorization`.

### Usuário

| Método | Rota | Descrição | Auth | Respostas |
|---|---|---|---|---|
| `POST` | `/usuario` | Cadastrar usuário | — | 201, 400, 409 |
| `POST` | `/usuario/login` | Autenticar e obter o token JWT | — | 200, 401 |
| `GET` | `/usuario/me` | Consultar o próprio perfil | 🔒 | 200, 401, 404 |
| `PUT` | `/usuario/me` | Editar o próprio perfil | 🔒 | 200, 401, 404 |
| `DELETE` | `/usuario/me` | Excluir a própria conta | 🔒 | 204, 401, 404 |

### Categoria

| Método | Rota | Descrição | Auth | Respostas |
|---|---|---|---|---|
| `POST` | `/categoria` | Criar categoria | 🔒 | 201, 400, 401 |
| `GET` | `/categoria` | Listar categorias do usuário | 🔒 | 200, 401 |
| `PUT` | `/categoria/{id}` | Editar categoria | 🔒 | 200, 400, 401, 404 |
| `DELETE` | `/categoria/{id}` | Excluir categoria | 🔒 | 204, 401, 404 |

### Lançamento

| Método | Rota | Descrição | Auth | Respostas |
|---|---|---|---|---|
| `POST` | `/lancamento` | Criar lançamento | 🔒 | 201, 400, 401, 404 |
| `GET` | `/lancamento` | Listar lançamentos do usuário | 🔒 | 200, 401 |
| `GET` | `/lancamento/mes?ano=2026&mes=10` | Listar lançamentos de um mês | 🔒 | 200, 400, 401 |
| `PUT` | `/lancamento/{id}` | Editar lançamento | 🔒 | 200, 400, 401, 404 |
| `DELETE` | `/lancamento/{id}` | Excluir lançamento | 🔒 | 204, 401, 404 |

### Resumo

| Método | Rota | Descrição | Auth | Respostas |
|---|---|---|---|---|
| `GET` | `/resumo/mensal?ano=2026&mes=10` | Resumo financeiro do mês | 🔒 | 200, 400, 401 |

### Exemplos

**Cadastrar usuário**

```http
POST /usuario
Content-Type: application/json

{
  "nome": "Maria Silva",
  "email": "maria@email.com",
  "senha": "senha123",
  "telefone": "55999999999"
}
```

**Login**

```http
POST /usuario/login
Content-Type: application/json

{
  "email": "maria@email.com",
  "senha": "senha123"
}
```

**Listar lançamentos de outubro de 2026**

```http
GET /lancamento/mes?ano=2026&mes=10
Authorization: Bearer <seu_token_jwt>
```

---

## 🗄 Banco de dados e migrações

O esquema do banco é versionado com **Flyway** (`src/main/resources/db/migration`). Qualquer mudança de estrutura deve virar uma nova migração (`V<n>__descricao.sql`), nunca uma edição direta no banco.

---

## 🧭 Roadmap

- [x] Base do projeto
- [x] Módulo de usuário
- [x] Autenticação JWT
- [x] Módulo de categoria
- [x] Lançamentos (receitas e despesas)
- [x] Resumo mensal
- [x] Documentação da API com Swagger
- [ ] Testes automatizados
- [x] Dockerização (v. 1.1)
- [ ] Frontend em Angular
- [ ] Deploy

---

## 👨‍💻 Autor

**Gabriel de Oliveira Cardoso**
Desenvolvedor Fullstack · Santa Maria, RS

- GitHub: [cardosogoc](https://github.com/cardosogoc)
- LinkedIn: [gabriel-cardoso-developer](https://www.linkedin.com/in/gabriel-cardoso-developer)
