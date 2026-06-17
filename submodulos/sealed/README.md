# sealed — Clases selladas

**Java 17** (preview en 15–16)

## Qué cubre

Las clases (e interfaces) selladas permiten controlar con precisión qué clases pueden extender o implementar una jerarquía. El compilador conoce el conjunto cerrado de subtipos y puede verificar la exhaustividad de los `switch`.

## Jerarquías del ejemplo

### Clases Selladas (Account)
```
Account  (sealed abstract)
 ├── SavingsAccount    (final)       → sin extensión posible
 ├── CheckingAccount   (final)       → sin extensión posible
 └── InvestmentAccount (non-sealed)  → puede seguir extendiéndose
      └── EtfAccount                 → clase libre fuera de la jerarquía sellada
```

### Interfaces Selladas (PaymentMethod)
```
PaymentMethod  (sealed interface)
 ├── CreditCard    (record)          → implícitamente final
 ├── Paypal        (final class)     → sin extensión posible
 └── BankTransfer  (non-sealed class)→ libremente extensible
```

### Herencia entre Interfaces Selladas (Service)
```
Service  (sealed interface)
 ├── HttpService   (sealed interface)   → las interfaces NO pueden ser 'final', deben ser 'sealed' o 'non-sealed'
 │    └── RestService (record)          → implícitamente final, implementa HttpService
 └── LocalService  (non-sealed interface) → desella esta rama, permitiendo cualquier extensión
      └── DatabaseService (class)       → clase estándar que implementa LocalService libremente
```

## Ejemplos incluidos

| Concepto | Descripción |
|----------|-------------|
| `sealed … permits` | Declaración de clases e interfaces selladas con una lista explícita de subtipos permitidos |
| `final` | Subtipo que cierra completamente la jerarquía de extensión |
| `non-sealed` | Subtipo que rompe el sellado permitiendo que cualquier clase lo extienda (ej: `InvestmentAccount`, `BankTransfer`, `LocalService`) |
| Records en sellado | Un `record` que implementa una interfaz sellada satisface la regla de sellado al ser implícitamente `final` (ej: `CreditCard`, `RestService`) |
| Herencia de Interfaces | Muestra cómo una `sealed interface` puede ser extendida por otra `sealed` o `non-sealed` interface, ilustrando que no es posible usar `final` con interfaces |
| `switch` exhaustivo | Switch sin cláusula `default`: el compilador verifica estáticamente que todas las ramas del `permits` están contempladas |
| Pattern matching | `case SavingsAccount s ->` o `case CreditCard cc ->` extrae y vincula la referencia tipada |

## Clases e Interfaces

`Account`, `SavingsAccount`, `CheckingAccount`, `InvestmentAccount`, `EtfAccount`, `PaymentMethod`, `CreditCard`, `Paypal`, `BankTransfer`, `Service`, `HttpService`, `LocalService`, `RestService`, `DatabaseService`, `App`
