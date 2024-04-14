# Ecommerce Full Stack - Proyecto Cliente-Servidor

Este proyecto consiste en una aplicación de Ecommerce dividida en un cliente y un servidor. El cliente está desarrollado con React, Vite, React Router y Tailwind CSS, mientras que el servidor está compuesto por una API de microservicios utilizando Spring Boot y Spring Cloud.

## Imagen del proyecto

![661shots_so](https://github.com/facuvande/Fullstack_eccommerce/assets/107081692/f3d03c52-b491-48f5-ab65-09e67ee00d5d)

## Ver Video del Proyecto

Para una demostración visual del proyecto, puedes ver el video en [YouTube](https://youtu.be/k7Q98zX7zXw).


## Características Principales

- **Cliente:** Desarrollado con React, Vite, React Router, y Tailwind CSS.
- **Servidor:** API de microservicios utilizando Spring Boot y Spring Cloud.
- **Base de Datos:** MySQL, con Spring Data JPA para la comunicación.
- **Autenticación:** Uso de Spring Security para autenticación de usuarios mediante JWT, con contraseñas encriptadas y autorización por roles (USER y ADMIN).
- **Microservicios:** Implementación de microservicios como users-service, carts-service, products-service, payments-service, y eureka-sv, comunicándose mediante Feign y utilizando el patrón de diseño Circuit Breaker.
- **Almacenamiento de Imágenes:** Uso de Cloudinary para el almacenamiento de imágenes, implementado en el microservicio de productos.
- **Pasarela de Pagos:** Integración con MercadoPago como pasarela de pagos.
- **Balanceo de Cargas:** Utilización de Load Balancer para distribuir la carga entre los diferentes servicios.

## Funcionalidades

### Para Usuarios:

- Registro y login de usuarios.
- Gestión de carrito de compras, incluyendo agregar, quitar, comprar productos, y verificación de stock.
- Gestión de productos favoritos.
- Visualización de detalles de productos.
- Cierre de sesión desde el frontend.

### Para Administradores:

- Búsqueda de usuarios registrados por email.
- Gestión de productos: agregar, editar y eliminar.
- Visualización de lista completa de ventas.

## Instalación y Ejecución

Para ejecutar la aplicación, sigue estos pasos:

1. Clona este repositorio en tu máquina local.
2. Configura y ejecuta los microservicios del servidor.
3. Inicia el cliente React.

### Configuración del Servidor

- Cada microservicio tiene su propia configuración de base de datos. Debes editar el archivo `application.properties` de cada microservicio en consecuencia. Se proporciona un ejemplo en `application.properties.example`.
- Ejecuta cada microservicio utilizando Spring Boot.

### Configuración del Cliente

- Sitúate en la carpeta del cliente.
- Instala las dependencias con `pnpm install`.
- Inicia el servidor de desarrollo con `pnpm run dev`.

## Notas

- Se recomienda acceder a la documentación de cada microservicio utilizando Swagger para realizar consultas. La URL de acceso es `http://localhost:{puerto del microservicio}/swagger-ui.html`, reemplazando `{puerto del microservicio}` con el puerto correspondiente de cada microservicio.
