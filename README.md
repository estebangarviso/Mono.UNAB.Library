## Mono.UNAB.Library

El equipo de desarrollo de la biblioteca de la universidad ha creado este repositorio para facilitar la gestión y persistencia de datos en proyectos académicos. Agradecemos cualquier retroalimentación y sugerencias para mejorar este recurso.

### Funcionalidades Clave

- Lectura de datos desde archivos
- Escritura de datos en archivos
- Gestión de entidades de datos utilizando clases de repositorio

La implementación asegura que la persistencia de datos se maneje de manera eficiente mediante el registro de todas las transacciones históricas de la app, y se adhiera a las mejores prácticas de la programación orientada a objetos.

### Requisitos

- Java 23.0.1
- Maven 3.9.8

### Instalación

1. Clona el repositorio:
    ```bash
    git clone https://github.com/estebangarviso/Mono.UNAB.Library.git
    ```
2. Navega al directorio del proyecto:
    ```bash
    cd Mono.UNAB.Library
    ```
3. Compila el proyecto usando Maven:
    ```bash
    mvn clean && mvn dependency:resolve && mvn package
    ```
4. Corre la aplicación:
    ```bash
    java -jar target/Mono.UNAB.Library-1.0-SNAPSHOT.jar
    ```

### Uso

Para utilizar el repositorio de persistencia de datos, crea una instancia de la clase correspondiente y llama a los métodos necesarios para leer o escribir datos.

### Contribuciones

Las contribuciones son bienvenidas. Por favor, abre un issue o envía un pull request para discutir cualquier cambio que desees realizar.

### Licencia

Este proyecto está licenciado bajo la Licencia MIT. Consulta el archivo `LICENSE` para más detalles.