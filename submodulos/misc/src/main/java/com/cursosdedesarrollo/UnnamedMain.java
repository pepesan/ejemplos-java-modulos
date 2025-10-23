import com.cursosdedesarrollo.Order;

import java.util.ArrayList;
import java.util.List;

public void main(){
    // Java 22
    // variables sin Nombre
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
