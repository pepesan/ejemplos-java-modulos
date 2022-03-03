# posible ejecución
## paso 1
### en parent /raiz
$ mvn clean compile package install
## paso 2
### en mainapp
java -p /home/pepesan/grive/cursos/java/ejemplos/ejemplos-java-modulos/submodulos/dao/target/classes:/home/pepesan/grive/cursos/java/ejemplos/ejemplos-java-modulos/submodulos/hijo/target/classes:/home/pepesan/grive/cursos/java/ejemplos/ejemplos-java-modulos/submodulos/servicio/target/classes:/home/pepesan/grive/cursos/java/ejemplos/ejemplos-java-modulos/submodulos/mainapp/target/classes:/home/pepesan/.m2/repository/org/projectlombok/lombok/1.18.22/lombok-1.18.22.jar -m com.cursosdedesarrollo.mainapp/com.cursosdedesarrollo.mainapp.App
