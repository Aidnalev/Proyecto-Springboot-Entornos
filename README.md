# Gestor de Ideas Personales

Este proyecto es una aplicación web que permite a los usuarios iniciar sesión y gestionar sus ideas personales mediante operaciones CRUD (Crear, Leer, Actualizar, Eliminar).

## Tecnologías utilizadas

- **Backend**: Spring Boot 3 (Java 17), Maven
- **Base de Datos**: MySQL 8
- **Frontend**: HTML, CSS básico, JavaScript (sin frameworks)
- **Control de versiones**: Git + GitHub

## Estructura del Proyecto

```
src/
├── main/
│   ├── java/com/aidnalev/gestorideas/     # Código Java (controladores, modelos, servicios, repositorios)
│   └── resources/
│       ├── static/                        # Archivos HTML, CSS, JS del frontend
│       └── application.properties         # Configuración del proyecto
```

## Modelo de Datos

El sistema cuenta con dos entidades principales: `Usuario` e `Idea`.

### Tablas

#### Usuarios

| Campo     | Tipo         | Descripción                |
|-----------|--------------|----------------------------|
| id        | BIGINT       | Identificador único        |
| nombre    | VARCHAR(100) | Nombre del usuario         |
| correo    | VARCHAR(100) | Correo (único, para login) |
| contrasena| VARCHAR(100) | Contraseña en texto plano o encriptada |

#### Ideas

| Campo        | Tipo         | Descripción                         |
|--------------|--------------|-------------------------------------|
| id           | BIGINT       | Identificador único                 |
| titulo       | VARCHAR(255) | Título de la idea                   |
| descripcion  | TEXT         | Descripción detallada               |
| estado       | VARCHAR(50)  | Estado (Ej: Pendiente, Completado)  |
| fechaCreacion| DATE         | Fecha de creación                   |
| usuario_id   | BIGINT       | Relación con el usuario (FK)        |

## Endpoints principales

| Método | Endpoint       | Descripción                          |
|--------|----------------|--------------------------------------|
| POST   | /login         | Iniciar sesión con correo y contraseña |
| GET    | /ideas         | Obtener todas las ideas del usuario  |
| POST   | /ideas         | Crear nueva idea                     |
| PUT    | /ideas/{id}    | Editar idea existente                |
| DELETE | /ideas/{id}    | Eliminar idea                        |

## Instrucciones de uso

### 1. Configurar base de datos

1. Crear una base de datos en MySQL llamada `gestorideas`.
2. Crear las tablas `usuario` e `idea` (ver script SQL).
3. Editar el archivo `src/main/resources/application.properties` con las credenciales correctas:

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/gestorideas
spring.datasource.username=root
spring.datasource.password=tu_contraseña

spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.MySQLDialect
server.port=8080
```

### 2. Ejecutar el backend

```bash
mvn spring-boot:run
```

### 3. Usar el frontend

Abrir los archivos `login.html` o `ideas.html` directamente desde:

```
src/main/resources/static/
```

O acceder a ellos vía navegador:
```
http://localhost:8080/login.html
http://localhost:8080/ideas.html
```

## Autor
Miguel Daniel Velandia Pinilla - 2182073 - Aidnalev — Estudiante de Ingeniería de Sistemas
