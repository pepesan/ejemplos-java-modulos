final String HELLO_TEMPLATE = "Hello %s!";

void main() {
    System.out.println(hello("world"));
}

String hello(String name) {
    return HELLO_TEMPLATE.formatted(name);
}
