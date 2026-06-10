# jshell — REPL y API programático

**Java 9+ (JEP 222)**

## Qué cubre

JShell es el REPL (Read-Eval-Print Loop) del JDK, introducido en Java 9. Permite evaluar expresiones, declarar variables, métodos y tipos de forma interactiva sin crear una clase completa.

Este módulo muestra dos formas de uso:

- **Interactivo** (`scripts/`): ficheros `.jsh` ejecutables directamente con el comando `jshell`
- **Programático** (`App.java`): API `jdk.jshell` embebida dentro de una aplicación Java para evaluar código dinámicamente en tiempo de ejecución

## Uso interactivo

### Iniciar JShell

```bash
jshell
```

### Ejecutar un script directamente

```bash
jshell scripts/01_basico.jsh
jshell scripts/02_lambdas_streams.jsh
jshell scripts/03_records_sealed.jsh
jshell scripts/04_classpath_deps.jsh
```

### Cargar un script desde dentro de JShell

```
jshell> /open scripts/01_basico.jsh
```

### Comandos útiles de JShell

| Comando | Descripción |
|---------|-------------|
| `/list` | Muestra todos los snippets evaluados |
| `/vars` | Lista las variables activas |
| `/methods` | Lista los métodos declarados |
| `/types` | Lista los tipos declarados |
| `/edit <id>` | Edita un snippet en un editor externo |
| `/drop <id>` | Elimina un snippet de la sesión |
| `/reset` | Resetea el estado completo de la sesión |
| `/help` | Ayuda completa |
| `/exit` | Sale de JShell |

### Resolución de dependencias en JShell

JShell no tiene gestión de dependencias nativa. Para usar librerías externas:

```bash
# Añadir un JAR al classpath al arrancar JShell
jshell --class-path ruta/a/libreria.jar

# Añadir classpath desde dentro de JShell
/env --class-path ruta/a/libreria.jar

# Con Maven: usar el classpath resuelto por Maven
jshell --class-path $(mvn -q dependency:build-classpath -Dmdep.outputFile=/dev/stdout)
```

> Los imports de `java.lang`, `java.util`, `java.io`, `java.math`, `java.net` y `java.util.stream`
> están activos por defecto en JShell — no hace falta importarlos explícitamente.

## Scripts incluidos

| Script | Contenido |
|--------|-----------|
| `01_basico.jsh` | Expresiones, variables, `var`, sentencias, bloques `try-catch` |
| `02_lambdas_streams.jsh` | Lambdas, Stream API, `map`, `filter`, `reduce`, `collect` |
| `03_records_sealed.jsh` | Records, sealed classes y pattern matching con `switch` |
| `04_classpath_deps.jsh` | Carga de dependencias externas desde el classpath |

## API programático (`App.java`)

La clase `App` demuestra cómo embeber JShell dentro de una aplicación:

| Demo | Qué muestra |
|------|-------------|
| `demoEvaluacionExpresiones` | `JShell.eval(String)`, `SnippetEvent`, `value()` |
| `demoDeclaracionVariables` | `VarSnippet`, persistencia de estado entre `eval()`, `variables()` |
| `demoDeclaracionMetodos` | `MethodSnippet`, declaración incremental, `methods()` |
| `demoDeclaracionTipos` | `TypeDeclSnippet`, records y clases dentro de JShell, `types()` |
| `demoManejoDeErrores` | `Status.REJECTED` (compilación), `exception()` (runtime), `diagnostics()` |
| `demoInspeccionEstado` | `snippets()`, filtrado por `Status.VALID`, inspección del historial |

## Clase principal

`com.cursosdedesarrollo.jshell.App`
