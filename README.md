# E-commerce API com Docker Compose

Este projeto é uma API REST para um e-commerce básico, construída com Spring Boot e PostgreSQL, utilizando Docker para containerização.

## 🏗️ Estrutura da API

### Endpoints
- `GET /api/itens`: Lista todos os produtos cadastrados
- `POST /api/item`: Adiciona um novo produto

### Modelo de Produto
```json
{
  "nome": "string",
  "descricao": "string",
  "preco": "double",
  "quantidade": "integer",
  "categoria": "string"
}
```

## 🐳 Configuração Docker

### Dockerfile
O projeto utiliza um multi-stage build para otimizar o tamanho da imagem final:

1. **Estágio de Build**
   ```dockerfile
   FROM maven:3.8.4-openjdk-17-slim AS build
   WORKDIR /app
   COPY pom.xml .
   COPY src ./src
   RUN mvn clean package -DskipTests
   ```
    - Usa Maven para compilar o projeto
    - `-DskipTests` evita a execução de testes durante o build
    - Resultado: arquivo JAR executável

2. **Imagem Final**
   ```dockerfile
   FROM openjdk:17-jdk-slim
   COPY --from=build /app/target/*.jar app.jar
   EXPOSE 8080
   ENTRYPOINT ["java","-jar","/app.jar"]
   ```
    - Copia apenas o JAR necessário da etapa de build
    - Imagem final mais leve por não incluir Maven e código-fonte

### Docker Compose
O `docker-compose.yml` configura dois serviços principais:

1. **Aplicação (app)**
   ```yaml
   app:
     build: .
     ports:
       - "8080:8080"
     depends_on:
       - db
     environment:
       - SPRING_DATASOURCE_URL=jdbc:postgresql://db:5432/ecommerce
       - SPRING_DATASOURCE_USERNAME=postgres
       - SPRING_DATASOURCE_PASSWORD=postgres
   ```
    - Constrói a imagem usando o Dockerfile local
    - Mapeia a porta 8080 do container para a porta 8080 do host
    - Depende do serviço `db` estar funcionando
    - Define variáveis de ambiente para conexão com o banco

2. **Banco de Dados (db)**
   ```yaml
   db:
     image: postgres:13
     ports:
       - "5432:5432"
     environment:
       - POSTGRES_DB=ecommerce
       - POSTGRES_USER=postgres
       - POSTGRES_PASSWORD=postgres
     volumes:
       - postgres-data:/var/lib/postgresql/data
   ```
    - Usa a imagem oficial do PostgreSQL
    - Mapeia a porta 5432
    - Define as credenciais do banco
    - Usa volume para persistência dos dados

## 🚀 Como Executar

1. Clone o repositório
2. No diretório raiz, execute:

   ```bash
   docker-compose up --build
   ```

## 📝 Exemplos de Uso com Postman

### 1. Adicionar Produto (POST)

**Configuração no Postman:**
- Method: POST
- URL: `http://localhost:8080/api/item`
- Body:

    ```json
    {
      "nome": "Smartphone",
      "descricao": "iPhone 16 Pro",
      "preco": 999.99,
      "quantidade": 10,
      "categoria": "Eletrônicos"
    }
    ```

### 2. Listar Produtos (GET)

**Configuração no Postman:**
- Method: GET
- URL: `http://localhost:8080/api/itens`
