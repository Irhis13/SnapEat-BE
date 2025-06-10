# SnapEat Backend Project

Bienvenido al repositorio backend del proyecto **SnapEat**, una plataforma para compartir recetas, gestionar favoritos y construir una comunidad gastronómica. Esta API REST permite gestionar usuarios, recetas, autenticación JWT y funcionalidades de interacción como likes o favoritos.

---

## Funcionalidades

- **Autenticación JWT**: Inicio de sesión seguro basado en email y contraseña.
- **Gestión de usuarios**: Registro, edición de perfil, cambio de contraseña, selección o subida de avatar.
- **CRUD de recetas**: Creación, edición, visualización y eliminar recetas con imágenes, ingredientes y pasos a seguir.
- **Likes y recetas Favoritas**: Añadir recetas a favoritos y dar “me gusta”.
- **Validaciones personalizadas**: Verificación de formatos de imagen y tamaño de archivo.
- **Carga de archivos**: Gestión de imágenes de recetas y avatares de usuario.

---

## Tecnologías utilizadas

- **Lenguaje**: Java 17
- **Framework**: Spring Boot 3
- **Seguridad**: Spring Security + JWT
- **Base de datos**: MySQL 8
- **ORM**: Hibernate & JPA
- **Build Tool**: Maven
- **Testing**: Postman

---

 ## Estructura ##
 
 ```plaintext
 src/
├── main/
│   ├── java/com/dam/web_cocina/
│   │   ├── AplicacionDeCocinaApplication.java
│   │   ├── common/                  # Validadores, utilidades, excepciones
│   │   ├── configuration/           # Configuración de seguridad y recursos
│   │   ├── controller/              # Controladores REST
│   │   ├── dto/                     # DTOs para transferencia de datos
│   │   ├── entity/                  # Entidades JPA
│   │   ├── mapper/                  # MapStruct mappers
│   │   ├── repository/              # Interfaces JPA
│   │   ├── security/                # Filtro y utilidades de JWT
│   │   └── service/                 # Interfaces y servicios
│   └── resources/
│       ├── application.properties   # Configuración de entorno
│       ├── static/                  
│       └── templates/               

```
---

## Rutas API SnapEat
```plaintext

Módulo     | Ruta base         | Descripción
-----------|-------------------|--------------------------------------------------------
Auth       | /api/auth         | Endpoints para autenticación y registro de usuarios
Recetas    | /api/recetas      | Gestión de recetas (crear, obtener, editar, eliminar)
Favoritos  | /api/favoritos    | Operaciones para marcar y desmarcar recetas favoritas
Likes      | /api/likes        | Gestión de "me gusta" en recetas
Usuarios   | /api/usuarios     | Perfil de usuario: datos, avatar, contraseña, etc.

```

---

## Construcción y ejecución
### 1. Requisitos previos

- Java 17
- Maven
- MySQL 8 

### 2. Clonar el repositorio

```bash
git clone https://github.com/Irhis13/SnapEat.git
```

### 3. Application properties

```plaintext

spring.application.name=snapeat

spring.datasource.url=jdbc:mysql://localhost:3306/cocina?useSSL=false&serverTimezone=UTC
spring.datasource.username=USERNAME
spring.datasource.password=PASSWORD

spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.MySQL8Dialect

jwt.secret=SECRET_JWT
jwt.expiration=86400000

spring.web.resources.static-locations=classpath:/static/,file:uploads/
```
> **Importante**: Personalizar estos valores con los datos del entorno local (credenciales MySQL, JWT secreto , etc.) antes de ejecutar la aplicación.

### 4. Compilar el proyecto

```bash
mvn spring-boot:run
```
---

## Contribuciones

¿Tienes una idea o encontraste un error? Las contribuciones son bienvenidas. Cualquier mejora ayuda a que SnapEat crezca y sea más útil para toda la comunidad.
