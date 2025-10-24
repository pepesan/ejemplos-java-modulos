import com.cursosdedesarrollo.Order;

import java.util.ArrayList;
import java.util.List;

public void main(){
    // Java 22
    // variables sin Nombre
    // 1) Usamos una variable local anónima.
    //    Se evalúa el método, pero no necesitamos el resultado.
    procesarDato();  // equivalente a: int _ = procesarDato();

    // 2) Creamos un registro (record) para usar patrones anónimos.
    record Point(int x, int y) {}
    Object obj = new Point(10, 20);

    // 3) Usamos pattern matching con un patrón anónimo.
    //    Ignoramos el segundo componente (y) usando '_'.
    if (obj instanceof Point(int x, _)) {
        System.out.println("El punto tiene x = " + x);
    }

    // 4) Usamos switch con patrón anónimo.
    switch (obj) {
        // Ignoramos ambos valores (x,y)
        case Point(_, _) -> System.out.println("Es un punto (valores ignorados)");
        default -> System.out.println("No es un punto");
    }

    // 5) Ejemplo en un bucle for-each donde no usamos el elemento.
    int[] numeros = {1, 2, 3};
    for (int _ : numeros) {  // recorremos, pero ignoramos cada valor
        System.out.println("Iteración sin usar el elemento");
    }

    // Otros ejemplos
    List<Order> orders = new ArrayList<>();

    // Añadir algunos ejemplos de pedidos
    orders.add(new Order(1, "Ana García"));
    orders.add(new Order(2, "Juan Pérez"));
    int total = 0;

    for (Order _ : orders)    // variable sin nombre
        total++;
    System.out.println(total);


    abstract class Ball {
        protected String color;

        public Ball(String color) {
            this.color = color;
        }

        public abstract void process();
    }

    class RedBall extends Ball {

        public RedBall() {
            super("Rojo");
        }

        @Override
        public void process() {
            System.out.println("Procesando pelota roja...");
        }
    }

    class BlueBall extends Ball {

        public BlueBall() {
            super("Azul");
        }

        @Override
        public void process() {
            System.out.println("Procesando pelota azul...");
        }
    }

    class GreenBall extends Ball {

        public GreenBall() {
            super("Verde");
        }

        @Override
        public void process() {
            System.out.println("Procesando pelota verde...");
        }
    }

    Ball ball = new RedBall(); // Ejemplo, puedes cambiar a BlueBall o GreenBall

    switch (ball) {
        case RedBall _, BlueBall _:
            ball.process();
            break;
        case GreenBall _:
            System.out.println("Super Verde");
            break;
        default:
            throw new IllegalStateException("Unexpected value: "+ball);
    }
}
// Método auxiliar: devuelve un número cualquiera.
static int procesarDato() {
    System.out.println("Procesando dato...");
    return 42;
}
