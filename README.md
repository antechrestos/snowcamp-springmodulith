# A la découverte de Spring Modulith

## Exercice 1

Résolvez la dépendance cyclique Order <-> Payment

## Exercice 2

Essayez d'aller jusqu'au paiement complet... Ca coince. Faites en sorte que les échecs du `GreeterService`
ne polluent pas le traitement principal.

## Exercice 3 - init modulith dans la douleur

Activez les dépendances Spring Modulith (sans observabilité pour l'instant)

Ajouter un `Test` dans `ChartreuseShopApplicationTest` qui fait

```java
ApplicationModules.of(ChartreuseShopApplication.class).verify()
```

Pourquoi échoue-t-i?

## Exercice 4 - module scanning

On rentre dans le vif du sujet...

On va omettre le package `common` du scanning... Créez un `Bean` qui implémente l'interface
`ApplicationModuleDetectionStrategy`

## Exercice 5 - internal events

On va maintenant cloisonner les packages.

* créez des objets évènements applicatifs sur les order
* servez vous de `ApplicationEventPublisher` pour les publier (bean dispo dans l'application context)
* servez vous des annotations sur des `Composants` qui appelleront des objets internes au module
    * `EventListener` (avec éventuellement `Transactionnal`) pour créer un listener **synchrone** (qui s'insèrera éventuellement dans la transaction parente)
    * `TransactionalEventListener` pour créer un listener qui recevra l'événement **en fin de traitement** uniquement si la transaction est réussie (possiblité de régler le moment de la phase de commit)
    *  `ApplicationModuleListener` qui fait la même chose que le précédent, avec en plus une exécution asynchrone et une transaction dédiée

À la fin de cet exercice le test de l'étape 3 doit passer au vert.

## Exercice 6 - tests

Deux tests à faire: au niveau du package `order`, créez une classe de tests annotée `ApplicationModuleTest`.

* écrivez un premier test qui prend en paramètre `events` de type `AssertablePublishedEvents` qui doit
    * initialiser un `Order` en base en `IN_PAYMENT`
    * appeler la méthode `paymentComplete` sur l'attribut de la classe de test `OrderManager` annoté `Autowired`
    * faire un `assertThat(events)...` pour vérifier qu'un évènement est bien publié avec le bon id
* écrivez un autre test qui prend un objet de paramètre de type `Scenario`. Le test est similaire sauf qu'au lieu d'appeler une quelconque méthode d'`OrderHandler` on doit
    * publier un event qui signale que l'order est payé
    * et observer qu'un event est sorti

## Exercice 7 - reprise d'event

Pouvez vous jouer avec les `Bean` `CompletedEventPublications` et `IncompleteEventPublications`  et purger les évènements et les rejouer ?

et sinon il paraît qu'il y a un paramètre pour les rejouer au démarrage (sous `spring.moo...`).

## Exercice 8 - external events

Les stocks pourraient être intéressés par externaliser les events. Pour ce faire, rajouter les dépendances et publier
un objet annoté de l'annotation. On peut même paramétrer la routing key; par exemple, dans l'exemple suivant

```java
@Externalized("example::#{#this.name().toLowerCase()}") 
record MonExample(String name){}
```

l'objet `new MonExample("YouPi")` sera publié sur l'exchange `example` avec la routing key `youpi`.
Regardez la configuration `QueuesConfiguration` pour designer votre premier external event.


## Exercice 9 - observabilité

Rajoutez les dépendances, jouez et regardez les traces dans grafana

## Exercice 10 - documentation

Générez la documentation sous

```kotlin
    @Test
    fun generateDocumentation() {
        val modules: ApplicationModules = ApplicationModules.of(TzModulithDemoApplication::class.java)
        val canvasOptions = CanvasOptions.defaults()
            .revealInternals()
        val diagramOptions = DiagramOptions.defaults()
            .withStyle(DiagramOptions.DiagramStyle.C4)
        Documenter(modules)
            .writeDocumentation(diagramOptions, canvasOptions)
    }
``` 
