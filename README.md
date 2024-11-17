## Mono.UNAB.Library

El equipo de desarrollo de la biblioteca de la universidad ha creado este repositorio para facilitar la gestión y persistencia de datos en proyectos académicos. Agradecemos cualquier retroalimentación y sugerencias para mejorar este recurso.

### Funcionalidades Clave

- Lectura de datos desde archivos
- Escritura de datos en archivos
- Gestión de entidades de datos utilizando clases de repositorio

La implementación asegura que la persistencia de datos se maneje de manera eficiente mediante el registro de todas las transacciones históricas de la app, y se adhiera a las mejores prácticas de la programación orientada a objetos.

_**Nota**: Este proyecto es parte de un proyecto académico y no está destinado a ser utilizado en producción._

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
    mvn clean package
    ```
4. Corre la aplicación:
    ```bash
    java -jar target/Mono.UNAB.Library-1.0-SNAPSHOT.jar
    ```

### Uso

Para utilizar el repositorio de persistencia de datos, crea una instancia de la clase correspondiente y llama a los métodos necesarios para leer o escribir datos.

## FAQ

#### ¿Cómo solucionar arrancar la aplicación en macOS, si al momento de arrancarla no se ejecuta?

Ejecutar en la terminal el siguiente comando:

```bash
/usr/libexec/java_home -V
```

Utilizar las rutas que se obtienen en la salida del comando anterior para configurar el archivo `settings.json` de Visual Studio Code.

Ejemplo:

```bash
Matching Java Virtual Machines (3):
    23.0.1 (arm64) "Oracle Corporation" - "Java SE 23.0.1" /Library/Java/JavaVirtualMachines/jdk-23.jdk/Contents/Home
    17.0.8.1 (x86_64) "Eclipse Adoptium" - "OpenJDK 17.0.8.1" /Library/Java/JavaVirtualMachines/temurin-17.jdk/Contents/Home
    1.8.421.09 (x86_64) "Oracle Corporation" - "Java" /Library/Internet Plug-Ins/JavaAppletPlugin.plugin/Contents/Home
```

Luego abrir `settings.json`, agregar la siguiente configuración y guardar los cambios:

```json
{
  "java.home": "/Library/Java/JavaVirtualMachines/jdk-23.jdk/Contents/Home",
  "java.configuration.runtimes": [
    {
      "name": "JavaSE-23",
      "path": "/Library/Java/JavaVirtualMachines/jdk-23.jdk/Contents/Home",
      "default": true
    },
    {
      "name": "JavaSE-17",
      "path": "/Library/Java/JavaVirtualMachines/temurin-17.jdk/Contents/Home"
    },
    {
      "name": "JavaSE-1.8",
      "path": "/Library/Internet Plug-Ins/JavaAppletPlugin.plugin/Contents/Home"
    }
  ]
}
```

### Contribuciones

Las contribuciones son bienvenidas. Por favor, abre un issue o envía un pull request para discutir cualquier cambio que desees realizar.

### Licencia

Este proyecto está licenciado bajo la Licencia MIT. Consulta el archivo `LICENSE` para más detalles.