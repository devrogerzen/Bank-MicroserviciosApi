# API Fintech - Sistema de Gestión de Cuentas

API REST desarrollada en Java con Spring Boot que simula un sistema básico fintech. Permite gestionar clientes y cuentas bancarias, con integración a cotización del dólar en tiempo real.

---

## Tecnologías utilizadas

- Java 21
- Spring Boot 4.0.6
- Spring Data JPA
- MySQL 8
- Lombok
- Springdoc OpenAPI (Swagger)
- JUnit 5 + Mockito

---

## Requisitos previos

- Java 21 instalado
- MySQL 8 corriendo en localhost:3306
- Maven (incluido en el proyecto con `mvnw`)

---

## Configuración de base de datos

La aplicación crea la base de datos automáticamente al iniciar. Solo necesitás tener MySQL corriendo con usuario `root` y contraseña `root`.

Si tu configuración es distinta, editá el archivo `src/main/resources/application.yaml`:

```yaml
spring:
  datasource:
    url: jdbc:mysql://localhost:3306/fintech_db?createDatabaseIfNotExist=true
    username: root
    password: root
```

---

## Cómo ejecutar el proyecto

1. Clonar el repositorio:
```bash
git clone <url-del-repositorio>
cd api
```

2. Compilar y ejecutar:
```bash
./mvnw spring-boot:run
```

La aplicación inicia en el puerto **8082**.

---

## Documentación de la API

Una vez iniciada la aplicación, accedé a Swagger UI:

```
http://localhost:8082/swagger-ui/index.html
```

---

## Endpoints disponibles

### Clientes
| Método | Endpoint | Descripción |
|--------|----------|-------------|
| GET | /clientes | Listar todos los clientes |
| GET | /clientes/{id} | Obtener cliente por ID |
| POST | /clientes | Crear nuevo cliente |
| PUT | /clientes/{id} | Actualizar cliente |
| DELETE | /clientes/{id} | Eliminar cliente |

### Cuentas
| Método | Endpoint | Descripción |
|--------|----------|-------------|
| GET | /cuentas | Listar todas las cuentas |
| GET | /cuentas/{id} | Obtener cuenta con conversión a pesos |
| POST | /cuentas | Crear nueva cuenta |
| PUT | /cuentas/{id} | Actualizar cuenta |
| DELETE | /cuentas/{id} | Eliminar cuenta |

### Dólar
| Método | Endpoint | Descripción |
|--------|----------|-------------|
| GET | /dolar/oficial | Cotización dólar oficial |

---

## Ejemplos de uso

### Crear un cliente
```json
POST /clientes
{
    "nombre": "Gonzalo",
    "apellidoORazonSocial": "Jimenez",
    "documentoOCuit": "20-12345678-9",
    "direccion": "Bolivar 234",
    "telefono": "1153245324",
    "email": "gonzalo@gmail.com"
}
```

### Crear una cuenta de ahorro en dólares
```json
POST /cuentas
{
    "tipo": "AHORRO",
    "numeroCuenta": "001-USD",
    "saldo": 1000.00,
    "moneda": "USD",
    "usuario": { "id": 1 },
    "tasaInteres": 2.5
}
```

### Crear una cuenta corriente en pesos
```json
POST /cuentas
{
    "tipo": "CORRIENTE",
    "numeroCuenta": "002-ARS",
    "saldo": 50000.00,
    "moneda": "ARS",
    "usuario": { "id": 1 },
    "limiteSobregiro": 10000.0
}
```

### Consultar cuenta con conversión en tiempo real
```
GET /cuentas/1
```
Respuesta para cuenta en USD:
```json
{
    "id": 1,
    "numeroCuenta": "001-USD",
    "saldo": 1000.0,
    "moneda": "USD",
    "saldoEnPesos": 1456900.0,
    "cotizacionMepCompra": 1456.9
}
```

---

## Modelo de dominio

```
Client (Usuario)
└── Cuenta (abstracta)
    ├── CuentaAhorro     → campo: tasaInteres
    └── CuentaCorriente  → campo: limiteSobregiro
```

Un cliente puede tener múltiples cuentas. Las cuentas en dólares muestran el equivalente en pesos usando la cotización de compra del dólar MEP obtenida en tiempo real desde [dolarapi.com](https://dolarapi.com).

---

## Ejecutar tests

```bash
./mvnw test
```
