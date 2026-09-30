# Banking Transactions API

API REST desarrollada en **Java 21** con **Spring Boot 4** para la gestión de cuentas bancarias y sus transacciones (depósitos y retiros), implementada siguiendo los principios de **Arquitectura Hexagonal (Ports & Adapters)**.

Este proyecto forma parte de un portafolio personal y tiene como objetivo demostrar buenas prácticas de diseño de software: separación de capas, dominio rico, manejo centralizado de errores y persistencia desacoplada de la lógica de negocio.

## Características

- Creación de cuentas bancarias con saldo inicial.
- Depósitos y retiros con validación de reglas de negocio (montos positivos, fondos suficientes).
- Consulta de una cuenta junto con su historial de transacciones.
- Manejo centralizado de errores mediante `@RestControllerAdvice` (cuenta no encontrada, saldo insuficiente, errores de validación, errores genéricos).
- Persistencia con Spring Data JPA sobre MySQL, mapeando el modelo de dominio a entidades independientes.
- Endpoint de *health check*.

## Arquitectura

El proyecto sigue el patrón de **Arquitectura Hexagonal**, separando el código en tres capas principales:

```
domain/
  model/        -> Entidades de dominio puras (Account, Money, Transaction, TransactionType, AccountId)
  exception/    -> Excepciones de negocio (AccountNotFoundException, InsufficientBalanceException, NegativeMoneyException)
  port/         -> Puertos de salida (AccountRepository)

application/
  port/         -> Casos de uso / puertos de entrada (CreateAccountUseCase, DepositMoneyUseCase, WithdrawMoneyUseCase, GetAccountDetailsUseCase)
  service/      -> Implementación de los casos de uso
  dto/          -> Comandos y DTOs internos de la capa de aplicación

infrastructure/
  web/          -> Adaptadores de entrada (controllers, DTOs de request/response, manejador global de excepciones)
  persistence/  -> Entidades JPA (AccountEntity, TransactionEntity)
  repository/   -> Adaptador de Spring Data JPA (SpringDataAccountRepository)
  adapter/      -> Implementación del puerto de dominio (AccountRepositoryAdapter)
  mapper/       -> Conversión entre entidades de dominio y entidades de persistencia
```

La idea central es que el **dominio** no depende de ningún framework: las reglas de negocio (por ejemplo, que no se puede retirar más dinero del disponible) viven en `Account` y `Money`, y son totalmente independientes de Spring o de la base de datos.

## Tecnologías

- Java 21
- Spring Boot 4.1.1
- Spring Data JPA
- Spring Validation
- MySQL (driver `mysql-connector-j`)
- Lombok
- Maven

## Endpoints principales

| Método | Endpoint                        | Descripción                                   |
|--------|----------------------------------|------------------------------------------------|
| POST   | `/api/account`                   | Crea una nueva cuenta                           |
| GET    | `/api/account/{id}`              | Obtiene el detalle de una cuenta y sus transacciones |
| POST   | `/api/account/{id}/deposit`      | Realiza un depósito en la cuenta                |
| POST   | `/api/account/{id}/withdraw`     | Realiza un retiro de la cuenta                  |

### Ejemplo: crear una cuenta

```http
POST /api/account
Content-Type: application/json

{
  "customerId": "cust-001",
  "initialBalance": 1000.0
}
```

### Ejemplo: depositar dinero

```http
POST /api/account/{id}/deposit
Content-Type: application/json

{
  "amount": 250.0
}
```

## Manejo de errores

Todos los errores se devuelven en un formato consistente:

```json
{
  "status": 400,
  "error": "Bad Request",
  "message": "Insuficient founds for withdraw",
  "path": "/api/account/{id}/withdraw",
  "timestamp": "2026-09-27T12:00:00Z",
  "details": []
}
```

## Cómo ejecutar el proyecto

### Requisitos previos

- JDK 21
- Maven (o usar el wrapper incluido `./mvnw`)
- MySQL corriendo localmente

### Configuración de la base de datos

Por defecto, la aplicación se conecta a MySQL usando esta configuración (`src/main/resources/application.yaml`):

```yaml
spring:
  datasource:
    url: jdbc:mysql://localhost:3307/bankingdb
    username: root
    password: root
```

Crea la base de datos `bankingdb` en tu instancia de MySQL (escuchando en el puerto `3307`) o ajusta estos valores según tu entorno.

### Ejecutar la aplicación

```bash
./mvnw spring-boot:run
```

La aplicación quedará disponible en `http://localhost:8080`.

### Ejecutar los tests

```bash
./mvnw test
```

## Próximas mejoras

- Autenticación y autorización (Spring Security).
- Paginación en el historial de transacciones.
- Tests de integración con Testcontainers.

## Licencia

Este proyecto se distribuye con fines educativos y de portafolio.
