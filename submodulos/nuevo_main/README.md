# nuevo_main — Nuevo método main y clases sin boilerplate

**Java 21–25** (preview)

## Qué cubre

Simplificación del punto de entrada de programas Java: eliminación del `public static void main(String[] args)` en favor de formas más concisas.

## Ejemplos incluidos

| Feature | Clase | Descripción |
|---------|-------|-------------|
| `void main()` sin `static` | `Greeter` | El launcher instancia la clase y llama al `main` de instancia; se puede acceder a campos del objeto |
| `void main()` minimalista | `App` | Clase sin modificadores de acceso, sin `static`, sin array de args |
| `var` en método de instancia | `Greeter` | `var nombre = (args.length > 0) ? args[0] : "mundo"` |

## Por qué es útil

Reduce el ruido para programas simples, scripts y ejemplos educativos, sin afectar a los programas existentes con el `main` clásico.

## Clases

`App`, `Greeter`
