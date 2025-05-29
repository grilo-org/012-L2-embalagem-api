
# Embalagem API

API para otimização do uso de embalagens na preparação de pedidos, indicando quais embalagens devem ser usadas para embalar produtos de modo eficiente.


---

## Tecnologias Utilizadas

- Java 17
- Spring Boot 3
- Spring Data JPA
- H2 Database (em memória, para ambiente de desenvolvimento)
- PostgreSQL (para ambiente de produção)
- Lombok
- Swagger/OpenAPI (para documentação da API)
- Docker & Docker Compose

---

## Perfis do Spring Boot

O projeto está configurado com dois perfis principais:

| Perfil | Banco de Dados        | Uso                            |
|--------|----------------------|--------------------------------|
| `dev`  | H2 (banco em memória) | Para desenvolvimento local rápido e testes |
| `prod` | PostgreSQL           | Para ambientes de produção e testes integrados |

Para alternar entre perfis, configure a variável de ambiente:

```bash
SPRING_PROFILES_ACTIVE=dev
```
ou
```bash
SPRING_PROFILES_ACTIVE=prod
```

---

## Rodando a aplicação com Docker

### Pré-requisitos

- Docker instalado
- Docker Compose instalado

### Passos para rodar

1. Clone o repositório:
```bash
git clone https://github.com/augusttosantana/embalagem-api
cd embalagem-api
```

2. Para rodar a aplicação em ambiente de desenvolvimento (`dev` profile com H2):

```bash
docker-compose up --build
```

Isso subirá a API na porta `8080` e o banco H2 em memória será usado automaticamente.

3. Para rodar em ambiente de produção (`prod` profile com PostgreSQL):

```bash
docker-compose up --build
```

O serviço do Postgres será iniciado junto com a aplicação.

---

## Testando a API

A documentação da API gerada pelo Swagger está disponível após iniciar a aplicação em:

```
http://localhost:8080/swagger-ui.html
```

Use essa interface para testar os endpoints, visualizar os modelos de dados (DTOs) e entender o funcionamento da API.

---

## Estrutura geral do projeto

- `src/main/java/com/astech/l2code` - Código fonte Java
- `src/main/resources/application.yml` - Configurações gerais
- `src/main/resources/application-dev.yml` - Configuração para perfil dev
- `src/main/resources/application-prod.yml` - Configuração para perfil prod
- `Dockerfile` - Imagem Docker da aplicação
- `docker-compose.yml` - Orquestração Docker da aplicação e banco PostgreSQL

---

## Contato

Para dúvidas ou sugestões, entre em contato:

- Email: santana.acesar@gmail.com
- GitHub: https://github.com/augusttosantana
- LinkedIn: https://www.linkedin.com/in/augusttosantana/

---

**ASTech Innovation** - Inovação e Excelência em Tecnologia
