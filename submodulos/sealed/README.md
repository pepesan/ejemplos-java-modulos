# sealed — Clases selladas

**Java 17** (preview en 15–16)

## Qué cubre

Las clases (e interfaces) selladas permiten controlar con precisión qué clases pueden extender o implementar una jerarquía. El compilador conoce el conjunto cerrado de subtipos y puede verificar la exhaustividad de los `switch`.

## Jerarquía del ejemplo

```
Account  (sealed abstract)
 ├── SavingsAccount    (final)       → sin extensión posible
 ├── CheckingAccount   (final)       → sin extensión posible
 └── InvestmentAccount (non-sealed)  → puede seguir extendiéndose
      └── EtfAccount                 → clase libre fuera de la jerarquía sellada
```

## Ejemplos incluidos

| Concepto | Descripción |
|----------|-------------|
| `sealed … permits` | Declaración de la clase sellada con lista explícita de subtipos |
| `final` | Subtipo que no puede extenderse más |
| `non-sealed` | Subtipo que rompe el sellado: cualquier clase puede extenderlo |
| `switch` exhaustivo | Sin `default`: el compilador garantiza que todos los `permits` están cubiertos |
| Pattern matching | `case SavingsAccount s ->` extrae y liga la referencia tipada |
| Guardia `when` | Condiciones adicionales dentro del `case` |

## Clases

`Account`, `SavingsAccount`, `CheckingAccount`, `InvestmentAccount`, `EtfAccount`, `App`
