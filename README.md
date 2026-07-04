# Inventory Microservice — SmartLogix

Microservicio encargado de gestionar el stock de productos por almacén. Es consultado y actualizado automáticamente por el microservicio de Orders cada vez que se crea un pedido.

## Datos técnicos

| Campo | Valor |
|---|---|
| Puerto | `8082` |
| Base de datos | `inventory_db` (MySQL) |
| Autenticación | JWT (Bearer Token — emitido por Users MS) |

## Modelo de datos

```json
{
  "id": 1,
  "productoCodigo": "PROD-001",
  "almacenCodigo": "ALM-A",
  "stock": 100
}
```

## Endpoints (todos requieren JWT)

| Método | Ruta | Descripción |
|---|---|---|
| GET | `/api/inventory/all` | Listar todo el inventario |
| GET | `/api/inventory/{productoCodigo}/{almacenCodigo}` | Stock de un producto en un almacén |
| POST | `/api/inventory/add` | Agregar un producto al inventario |
| POST | `/api/inventory/bulk-add` | Agregar múltiples productos a la vez |
| POST | `/api/inventory/update` | Actualizar stock de un producto |
| PUT | `/api/inventory/{id}` | Editar un registro completo por ID |

---

## Pruebas en Postman

> Todas las peticiones requieren el header:
> `Authorization: Bearer <token>`
>
> Obtén el token primero desde el Users MS (`POST http://localhost:8083/api/auth/login`) o desde el BFF (`POST http://localhost:8080/api/auth/login`).

### 1. Listar todo el inventario

```
GET http://localhost:8082/api/inventory/all
Authorization: Bearer <token>
```

**Respuesta esperada (200):**
```json
[
  { "id": 1, "productoCodigo": "PROD-001", "almacenCodigo": "ALM-A", "stock": 100 },
  { "id": 2, "productoCodigo": "PROD-002", "almacenCodigo": "ALM-B", "stock": 50 }
]
```

---

### 2. Consultar stock de un producto en un almacén

```
GET http://localhost:8082/api/inventory/PROD-001/ALM-A
Authorization: Bearer <token>
```

**Respuesta esperada (200):**
```json
{ "id": 1, "productoCodigo": "PROD-001", "almacenCodigo": "ALM-A", "stock": 100 }
```

---

### 3. Agregar producto al inventario

```
POST http://localhost:8082/api/inventory/add
Authorization: Bearer <token>
Content-Type: application/json

{
  "productoCodigo": "PROD-001",
  "almacenCodigo": "ALM-A",
  "stock": 100
}
```

**Respuesta esperada (201):**
```json
{ "id": 1, "productoCodigo": "PROD-001", "almacenCodigo": "ALM-A", "stock": 100 }
```

---

### 4. Agregar múltiples productos (bulk)

```
POST http://localhost:8082/api/inventory/bulk-add
Authorization: Bearer <token>
Content-Type: application/json

[
  { "productoCodigo": "PROD-001", "almacenCodigo": "ALM-A", "stock": 100 },
  { "productoCodigo": "PROD-002", "almacenCodigo": "ALM-B", "stock": 50 }
]
```

---

### 5. Actualizar stock de un producto

```
POST http://localhost:8082/api/inventory/update
Authorization: Bearer <token>
Content-Type: application/json

{
  "productoCodigo": "PROD-001",
  "almacenCodigo": "ALM-A",
  "stock": 85
}
```

---

### 6. Editar registro completo por ID

```
PUT http://localhost:8082/api/inventory/1
Authorization: Bearer <token>
Content-Type: application/json

{
  "productoCodigo": "PROD-001",
  "almacenCodigo": "ALM-A",
  "stock": 200
}
```

---

## Cómo levantar

```bash
./mvnw spring-boot:run
```

Requiere MySQL corriendo en `localhost:3306` con usuario `root` / contraseña `root`. La base de datos `inventory_db` se crea automáticamente al iniciar.
