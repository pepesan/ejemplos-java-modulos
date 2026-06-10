# reactivestreams-flow — Flow API del JDK

**Java 9**

## Qué cubre

`java.util.concurrent.Flow` es la implementación de la especificación Reactive Streams incluida en el JDK. Define las interfaces estándar `Publisher`, `Subscriber`, `Subscription` y `Processor` sin depender de librerías externas.

## Ejemplos incluidos

| Clase | Rol | Descripción |
|-------|-----|-------------|
| `AppPublisherSubscriber` | Entrada | Conecta un `SubmissionPublisher` con un `MySubscriber` |
| `MySubscriber` | `Subscriber<T>` | Implementación que imprime cada elemento recibido |
| `MainProcessor` | `Processor<T,T>` | Conecta publisher → processor → subscriber |
| `CustomProcessor` | `Processor<String,String>` | Transforma elementos antes de reenviarlos |
| `StringPublisher` | `Publisher<String>` | Publisher personalizado del ejercicio |
| `StringSubscriber` | `Subscriber<String>` | Subscriber del ejercicio |
| `StringProcessor` | `Processor<String,String>` | Processor del ejercicio |

## Clases principales

`AppPublisherSubscriber`, `MainProcessor`
