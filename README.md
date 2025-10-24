# Ejemplo de uso de módulos Java 9

## Compilación
mvn clean compile package install
## Ejecución
<code>$ java -p /home/pepesan/IdeaProjects/ejemplos-java-modulos/submodulos/dao/target/classes:/home/pepesan/IdeaProjects/ejemplos-java-modulos/submodulos/hijo/target/classes:/home/pepesan/IdeaProjects/ejemplos-java-modulos/submodulos/servicio/target/classes:/home/pepesan/IdeaProjects/ejemplos-java-modulos/submodulos/mainapp/target/classes:/home/pepesan/.m2/repository/org/projectlombok/lombok/1.18.22/lombok-1.18.22.jar -m com.cursosdedesarrollo.mainapp/com.cursosdedesarrollo.mainapp.App </code>