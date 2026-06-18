# mainapp — Aplicación principal modular

**Java 9+ (JPMS)**

## Qué cubre

Punto de entrada de la aplicación modular. Muestra cómo un módulo consume otros módulos mediante `requires` y cómo usa el ServiceLoader con `uses`.

## Módulo JPMS

```java
module com.cursosdedesarrollo.mainapp {
    requires com.cursosdedesarrollo.hijo;
    requires com.cursosdedesarrollo.dao;
    requires com.cursosdedesarrollo.service;
    uses com.cursosdedesarrollo.dao.Dao;
}
```

## Grafo de dependencias

```
mainapp
 ├── requires → hijo      (modelo de dominio)
 ├── requires → dao       (interfaz)
 ├── requires → service   (implementación)
 └── uses     → Dao       (ServiceLoader en runtime)
```

## Ejecución manual (tras `mvn clean package install` en la raíz)

Si estás en la carpeta `submodulos/`:
```bash
java -p dao/target/classes:hijo/target/classes:servicio/target/classes:mainapp/target/classes:$HOME/.m2/repository/org/projectlombok/lombok/1.18.38/lombok-1.18.38.jar \
     -m com.cursosdedesarrollo.mainapp/com.cursosdedesarrollo.mainapp.App
```

Si estás en la raíz del proyecto:
```bash
java -p submodulos/dao/target/classes:submodulos/hijo/target/classes:submodulos/servicio/target/classes:submodulos/mainapp/target/classes:$HOME/.m2/repository/org/projectlombok/lombok/1.18.38/lombok-1.18.38.jar \
     -m com.cursosdedesarrollo.mainapp/com.cursosdedesarrollo.mainapp.App
```



## Empaquetado nativo con `jpackage` (Java 14+)

`jpackage` (JEP 392, estable desde Java 16) genera un instalador nativo de la aplicación que incluye el JRE embebido. El usuario final no necesita tener Java instalado.

### Paso previo: crear el fat-jar con dependencias

```bash
# Desde la raíz del proyecto
mvn clean package install
```

### Generar instalador para la plataforma actual

```bash
# Linux (.deb / .rpm)
jpackage \
  --type deb \
  --name ejemplos-java \
  --app-version 1.0.0 \
  --module-path dao/target/classes:hijo/target/classes:servicio/target/classes:mainapp/target/classes \
  --module com.cursosdedesarrollo.mainapp/com.cursosdedesarrollo.mainapp.App \
  --dest build/instaladores

# macOS (.dmg / .pkg)
jpackage \
  --type dmg \
  --name ejemplos-java \
  --app-version 1.0.0 \
  --module-path dao/target/classes:hijo/target/classes:servicio/target/classes:mainapp/target/classes \
  --module com.cursosdedesarrollo.mainapp/com.cursosdedesarrollo.mainapp.App \
  --dest build/instaladores

# Windows (.msi / .exe)
jpackage ^
  --type msi ^
  --name ejemplos-java ^
  --app-version 1.0.0 ^
  --module-path dao\target\classes;hijo\target\classes;servicio\target\classes;mainapp\target\classes ^
  --module com.cursosdedesarrollo.mainapp/com.cursosdedesarrollo.mainapp.App ^
  --dest build\instaladores
```

### Opciones útiles

| Opción | Descripción |
|--------|-------------|
| `--type` | Formato: `app-image`, `deb`, `rpm`, `dmg`, `pkg`, `msi`, `exe` |
| `--module-path` | Rutas al module-path (equivale a `-p` en `java`) |
| `--module` | Módulo y clase principal (`module/clase`) |
| `--add-modules` | Módulos extra a incluir en el JRE embebido |
| `--jlink-options` | Opciones pasadas a `jlink` para reducir el tamaño del JRE |
| `--icon` | Icono de la aplicación (`.ico` / `.icns` / `.png`) |
| `--vendor` | Nombre del proveedor que aparece en el instalador |

### Reducir el tamaño con `jlink`

`jpackage` usa `jlink` internamente. Para minimizar el JRE embebido:

```bash
jpackage \
  --jlink-options "--strip-debug --no-header-files --no-man-pages --compress=2" \
  ...
```

## Clase principal

`com.cursosdedesarrollo.mainapp.App`
