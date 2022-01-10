# Ejemplo de uso de módulos Java 9

## Compilación
mvn clean compile
## Ejecución
<code>$ java --module-path submodulos -p /home/pepesan/.m2/repository/org/projectlombok/lombok/1.18.22/lombok-1.18.22.jar:submodulos/hijo/target/classes:submodulos/mainapp/target/classes --module com.cursosdedesarrollo.mainapp/com.cursosdedesarrollo.mainapp.App </code>