# posible ejecución
## paso 1
### en hijo
$ mvn clean compile package
## paso 2
### en mainapp
java --module-path /home/pepesan/grive/cursos/java/ejemplos/ejemplos-java-modulos/submodulos/hijo/target/hijo-1.0-SNAPSHOT.jar --module com.cursosdedesarrollo.mainapp/com.cursosdedesarrollo.mainapp.App
