# Tarjetas API

API REST desarrollada con Spring Boot para la gestión de tarjetas de crédito/débito y productos bancarios. Proyecto paralelo a la API de clientes, diseñado para trabajar en conjunto simulando el backend de un homebanking.

---

## Requisitos previos

- Java 21
- Maven
- MySQL 8+
- La [API de clientes](https://github.com/Pedroottaviano/api) corriendo en el puerto `8080`

---

## Configuración inicial

### 1. Crear la base de datos

Conectarse a MySQL y ejecutar:

```sql
CREATE DATABASE utn_tarjetas;
```

### 2. Clonar el repositorio

```bash
git clone https://github.com/Pedroottaviano/TarjetasApi.git
cd TarjetasApi
```

### 3. Verificar credenciales en `application.yaml`

El archivo se encuentra en `src/main/resources/application.yaml`. Por defecto usa:

```yaml
spring:
  datasource:
    url: jdbc:mysql://localhost:3306/utn_tarjetas
    username: root
    password: root
```

Modificar usuario y contraseña si es necesario.

### 4. Levantar la API

```bash
./mvnw spring-boot:run
```

La API corre en el puerto **8081**. Las tablas se crean automáticamente al iniciar.

### 5. Verificar que funciona

Abrir Swagger UI: [http://localhost:8081/swagger-ui/index.html](http://localhost:8081/swagger-ui/index.html)

---

## Endpoints disponibles

### Tarjetas — `/tarjetas`

| Método | Endpoint | Descripción |
|--------|----------|-------------|
| GET | `/tarjetas` | Listar todas las tarjetas |
| GET | `/tarjetas/{id}` | Obtener tarjeta por ID |
| GET | `/tarjetas/cliente/{clienteId}` | Tarjetas de un cliente |
| GET | `/tarjetas/estado/{estado}` | Filtrar por estado |
| POST | `/tarjetas/agregar` | Crear tarjeta |
| PATCH | `/tarjetas/{id}/estado/{nuevoEstado}` | Cambiar estado |
| DELETE | `/tarjetas/eliminar/{id}` | Eliminar tarjeta |
| GET | `/tarjetas/cotizacion/mep` | Cotización del dólar MEP |

**Valores de `tipo`:** `CREDITO`, `DEBITO`, `PREPAGA`

**Valores de `estado`:** `ACTIVA`, `BLOQUEADA`, `CANCELADA`, `VENCIDA`

**Valores de `marca`:** `VISA`, `MASTERCARD`, `AMEX`, etc. (texto libre)

### Productos — `/productos`

| Método | Endpoint | Descripción |
|--------|----------|-------------|
| GET | `/productos` | Listar todos los productos |
| GET | `/productos/{id}` | Obtener producto por ID |
| GET | `/productos/cliente/{clienteId}` | Productos de un cliente |
| GET | `/productos/tipo/{tipo}` | Filtrar por tipo |
| GET | `/productos/activos` | Solo productos activos |
| POST | `/productos/agregar` | Crear producto |
| PUT | `/productos/actualizar/{id}` | Actualizar producto |
| DELETE | `/productos/eliminar/{id}` | Eliminar producto |

**Valores de `tipo`:** `CUENTA_CORRIENTE`, `CAJA_AHORRO`, `PLAZO_FIJO`, `PRESTAMO_PERSONAL`, `PRESTAMO_HIPOTECARIO`, `SEGURO_VIDA`, `SEGURO_HOGAR`

---

## Consigna — Integración entre Microservicios

### Contexto

En la realidad, un banco no es Visa ni Mastercard: les compra el servicio. El banco gestiona la relación con su cliente — qué productos tiene, qué ve en el homebanking. Cuando un cliente entra al homebanking, ve todo agregado bajo su perfil: cuentas, tarjetas, préstamos.

Hoy vamos a implementar exactamente eso.

Tenemos dos APIs independientes:

- **`api`** — gestiona clientes y cuentas bancarias (puerto `8080`)
- **`tarjetas-api`** — gestiona tarjetas y productos bancarios (puerto `8081`)

Las tarjetas y productos ya tienen un campo `clienteId` que referencia al cliente de la otra API, pero por ahora ese vínculo es solo lógico — no hay comunicación entre los servicios.

El objetivo es **implementar esa comunicación** para simular lo que haría el backend de un homebanking real.

### Tarea

Modificar la **`api` de clientes** para que exponga un nuevo endpoint:

```
GET /clientes/{id}/perfil
```

Este endpoint debe devolver un único objeto con la información del cliente **y** todas sus tarjetas y productos, obtenidos desde `tarjetas-api` mediante una llamada HTTP con **OpenFeign**.

**Respuesta esperada:**

```json
{
  "cliente": {
    "id": 1,
    "nombre": "Juan",
    "apellidoORazonSocial": "Pérez",
    "email": "juan@mail.com"
  },
  "tarjetas": [
    {
      "tipo": "CREDITO",
      "marca": "VISA",
      "estado": "ACTIVA",
      "saldoDisponible": 80000.00
    }
  ],
  "productos": [
    {
      "tipo": "PLAZO_FIJO",
      "nombre": "Plazo Fijo 30 días",
      "montoAsociado": 500000.00
    }
  ]
}
```

### Pasos sugeridos

1. **Levantar ambas APIs** — verificar que `api` corra en `8080` y `tarjetas-api` en `8081`
2. **Cargar datos de prueba** — agregar al menos un cliente en la `api`, y una tarjeta y un producto en `tarjetas-api` usando Swagger UI o Postman, respetando que la tarjeta/producto tengan el `clienteId` correcto
3. **Crear un DTO `PerfilClienteDTO`** en la `api` de clientes que agrupe cliente + tarjetas + productos
4. **Crear un FeignClient** en la `api` de clientes que apunte a `tarjetas-api` y consuma los endpoints `/tarjetas/cliente/{clienteId}` y `/productos/cliente/{clienteId}`
5. **Implementar el endpoint** `GET /clientes/{id}/perfil` en `ClientController` usando el nuevo FeignClient
6. **Probar** el endpoint y verificar que devuelve los datos de ambas APIs en una sola respuesta

> **Tip:** OpenFeign ya está configurado en el proyecto. Tomar como referencia el `DolarClient` existente como modelo para crear el nuevo cliente HTTP.

### Preguntas para pensar

- ¿Qué pasa si `tarjetas-api` está caída cuando alguien consulta el perfil?
- ¿Tiene sentido que sea la API de clientes la que "junta" la información, o debería hacerlo el frontend?
- ¿Por qué usamos `clienteId` como campo simple en lugar de una FK real en la base de datos?
