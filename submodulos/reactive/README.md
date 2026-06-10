# reactive — RxJava 3

**RxJava 3 (librería externa)**

## Qué cubre

Programación reactiva con RxJava 3. Los observables emiten elementos de forma asíncrona y los observadores reaccionan a ellos. Cubre los tres tipos principales de observables de cardinalidad reducida y los subjects.

## Ejemplos incluidos

| Tipo | Cardinalidad | Descripción |
|------|-------------|-------------|
| `Single<T>` | Exactamente 1 elemento | Emite un valor o un error |
| `Maybe<T>` | 0 o 1 elemento | Puede emitir un valor, completar vacío, o error |
| `Completable` | 0 elementos | Solo notifica completado o error |
| `CompositeDisposable` | — | Agrupa y libera múltiples suscripciones a la vez |
| `PublishSubject` | Multidifusión | Los suscriptores solo reciben lo emitido tras suscribirse |
| `BehaviorSubject` | Multidifusión | Los suscriptores reciben el último valor emitido + los futuros |

## Clase principal

`com.cursosdedesarrollo.app.App`
