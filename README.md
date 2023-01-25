# Avaliação Individual – Springboot – Compass.uol

Projeto usando arquitetura de micro-serviços, foi definida a criação de dois micro-serviços: order e history.

***

Nesse projeto foi utilizado:
* Java 17
* MySql
* MongoDB
* API ViaCep
* OpenFeign
* Kafka
* Arquitetura Hexagonal
* Spring Boot 2.7.7
* Logback
* Docker
* Swagger

***

O Logback vai ficar armazenado no diretório `Users/User/temp`.

>Devido ao avanço rapído do projeto optei por criar dois endpoints para melhor controle dos **items** da **order**, um para criação e outro para visualização dos itens criados. Minha preferência nessa alternativa leva a uma melhor entendimento e separação das partes do projeto.

## Documentação da API

### MS ORDER

```
POST /api/pedidos
```
- Criação do **pedido** já precisa ter itens criados previamente
- Só aceita **CEPs** válidos
- Só aceita **CPFs** válidos

**Request Example**

```json
{
    "cpf": "09963606547",
    "itemsIds": [1, 2],
    "cep": "40140-650",
    "number": 5
}
```

**Curl**

```
curl --location --request POST 'localhost:8080/api/pedidos' \
--header 'Content-Type: application/json' \
--data-raw '{
    "cpf": "09963606547",
    "itemsIds": [1],
    "cep": "40140-650",
    "number": 5
}'
```

***

```
GET /api/pedidos  
```
- Possibilidade de filtrar por **CPF**
- Ordena pelo **total** do valor do **pedido** em ordem crescente ou decrescente

**Curl**

```
curl --location --request GET 'localhost:8080/api/pedidos'
```

***

```
GET /api/pedidos/1  
```

**Curl**

```
curl --location --request GET 'localhost:8080/api/pedidos/1'
```

***

```
PATCH /api/pedidos/itens/1  
```
- Altera os **itens** de determinado **pedido**
- Recebe os **itens** através de um Array contendo o **ID** dos itens

**Request Example**

```json
[3, 4]
```

**Curl**

```
curl --location --request PATCH 'localhost:8080/api/pedidos/itens/1' \
--header 'Content-Type: application/json' \
--data-raw '[3, 4]'
```

***

```
PUT /api/pedidos/1  
```
- Só aceita **CEPs** válidos
- Só aceita **CPFs** válidos

**Request Example**

```json
{
    "cpf": "28991068561",
    "cep": "40015-900",
    "number": 10
}
```

**Curl**

```
curl --location --request PUT 'localhost:8080/api/pedidos/1' \
--header 'Content-Type: application/json' \
--data-raw '{
    "cpf": "28991068561",
    "cep": "40015-900",
    "number": 10
}'
```

***

```
DELETE /api/pedidos/1  
```

**Curl**

```
curl --location --request DELETE 'localhost:8080/api/pedidos/1'
```

***

```
POST /api/itens  
```
- Data em formato brasileiro `dia-mês-ano`
- **Data de criação** não pode ser antes da **data de expiração**
- **Valor** não pode ser negativo ou zero

**Request Example**

```json
{
    "name": "Leite",
    "creationDate": "11-01-2023",
    "expirationDate": "11-03-2023",
    "value": 4.49,
    "description": "1L de Leite Piracanjuba"
}
```

**Curl**

```
curl --location --request POST 'localhost:8080/api/itens' \
--header 'Content-Type: application/json' \
--data-raw '{
    "name": "Leite",
    "creationDate": "11-01-2023",
    "expirationDate": "11-03-2023",
    "value": 4.49,
    "description": "1L de Leite Piracanjuba"
}'
```


***

```
GET /api/itens  
```

**Curl**

```
curl --location --request GET 'localhost:8080/api/itens'
```

***

### MS HISTORY

```
GET /api/historico  
```
- Permite filtrar pela data do **pedido**

**Curl**

```
curl --location --request GET 'localhost:8081/api/historico'
```