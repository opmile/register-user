# Register User API

API REST para gerenciamento de usuários, construída com Spring Boot e PostgreSQL.  

## Funcionalidades

- CRUD completo de usuários (Create, Read, Update, Delete)  
- Consumo da [Random User API](https://randomuser.me/) para importar usuários aleatórios  
- Persistência de dados no PostgreSQL  

## Tecnologias

- Java 21  
- Spring Boot  
- PostgreSQL  
- Maven  

## Endpoints principais

- `POST /users` – Criar um novo usuário  
- `GET /users` – Listar todos os usuários  
- `GET /users/{id}` – Buscar usuário por ID  
- `PUT /users/{id}` – Atualizar usuário por ID  
- `DELETE /users/{id}` – Remover usuário por ID
- `DELETE /users/{id}` – Remover todos os usuários
- `POST /users/fetch-external` – Buscar e salvar usuário aleatório da Random User API

### Exemplos de payload

**Criar usuário manualmente (`POST /users`):**  

```json
{
  "fullName": "Milena Silva",
  "email": "milena@example.com",
  "username": "milena123",
  "age": 22,
  "gender": "female",
  "location": {
    "city": "Goiânia",
    "state": "GO",
    "country": "Brazil"
  }
}
```

**Importar usuário aleatório (POST /users/random):**

- Não é necessário enviar payload; a API busca e salva um usuário aleatório automaticamente.

## Como rodar

1. Clone o repositório:  
   ```bash
   git clone https://github.com/opmile/register-user.git
   ```
   
2. Configure o banco PostgreSQL no `application.properties`

3. Execute o projeto:

`mvn spring-boot:run`

Os endpoints estarão disponíveis em `http://localhost:8080`.
