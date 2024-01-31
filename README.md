# A la découverte de Spring Modulith

## Prérequis

Afin de jouer cet atelier, assurez-vous d'avoir sur votre poste :
- Java 17
- Docker Compose

## Un peu de contexte

Amis du Snowcamp bonjour,  
Nous te sollicitons car nous avons besoin de ton aide.  
Nous avons externalisé le développement de notre site de vente en ligne de Chartreuse auprès de ceux que nous pensions
être les plus à même de le faire, à savoir *Le cabinet de conseil des frères Chartreux*.

Après de nombreux mois sans la moindre évolution majeure, ni prise en compte des bugs, 
nous avons découvert que frère Bernard, notre développeur, avait plus tendance à contrôler la qualité 
de nos produits qu'à s'appliquer au développement de notre site.

Nous faisons donc appel à vous pour reprendre en l'état notre backend et développer les fonctionnalités manquantes pour,
espérons-le, inonder l'Europe, voire le monde de notre liqueur verte.

## Exercice 1

Le premier problème que nous avons, c'est que l'application ne démarre même pas, 
il semble qu'il y ait une dépendance cyclique entre des composants gérant les commandes (`Order`) et le paiement (`Payment`).

<details>
  <summary>Besoin d'aide ?</summary>
  
  Si vous essayez de lancer l'application à l'aide de la commande `./gradlew bootRun`, vous constaterez que l'application ne démarre pas :
  
  ```
  ***************************
  APPLICATION FAILED TO START
  ***************************
  
  Description:
  
  The dependencies of some of the beans in the application context form a cycle:
  
  orderController defined in file [./spring-modulith-workshop/build/classes/java/main/org/snowcamp/university/springmodulith/order/api/web/OrderController.class]
  ┌─────┐
  |  orderManager defined in file [./spring-modulith-workshop/build/classes/java/main/org/snowcamp/university/springmodulith/order/domain/OrderManager.class]
  ↑     ↓
  |  paymentHandler defined in file [./spring-modulith-workshop/build/classes/java/main/org/snowcamp/university/springmodulith/payment/domain/PaymentHandler.class]
  └─────┘
  
  
  Action:
  
  Relying upon circular references is discouraged and they are prohibited by default. Update your application to remove the dependency cycle between beans. As a last resort, it may be possible to break the cycle automatically by setting spring.main.allow-circular-references to true.
  ```
  
  `OrderManager` et `PaymentHandler`dépendent l'un de l'autre, essayez de répartir la logique de `PaymentHandler` dans deux classes séparées pour résoudre ce problème.
</details>

## Exercice 2

Le deuxième problème rencontré, c'est que nos utilisateurs n'arrivent pas passer de commandes ;
il semble qu'il y ait un problème lors du paiement.  
Essayez d'aller jusqu'au paiement complet... Ça coince. 
Nous savons que le problème est apparu quand notre père supérieur a insisté pour voir développer une fonctionnalité 
permettant d'être notifié des commandes complétées. Le site ne permettait même pas de mener une commande complète
mais il fallait développer la notification du paiement de commande... Nous ne reviendrons pas sur cette gestion 
des priorités, mais s'il vous plaît, pourriez-vous faire en sorte que les notifications n'empêchent
pas le traitement principal ?

<details>
  <summary>Besoin d'aide ?</summary>

  En utilisant le [Swagger de l'application](http://localhost:8080/swagger-ui/index.html), commencez par créer un _order_ (POST `/api/vi/order`).
  
  Passez ensuite cette _order_ en paiement (PUT `/api/v1/orders/static-for-demo/state/in_payment`).
  
  Vous pouvez finalement invoquer la complétion du paiement (PUT `/api/v1/payments/static-for-demo/complete`).
  
  L'API vous renvoie alors une erreur `500` et vous constatez en inspectant les logs que le problème vient du `GreeterService`
  
  ```
  java.lang.RuntimeException: No greeting !!!
  at org.snowcamp.university.springmodulith.greeting.configuration.GreetingConfiguration.lambda$noGreeterClient$1(GreetingConfiguration.java:29)
  at org.snowcamp.university.springmodulith.greeting.domain.GreeterService.greet(GreeterService.java:25)
  ...
  at org.snowcamp.university.springmodulith.greeting.domain.GreeterService$$SpringCGLIB$$0.greet(<generated>)
  at org.snowcamp.university.springmodulith.order.domain.OrderManager.paymentComplete(OrderManager.java:101)
  ...
  at org.snowcamp.university.springmodulith.order.domain.OrderManager$$SpringCGLIB$$0.paymentComplete(<generated>)
  at org.snowcamp.university.springmodulith.payment.domain.PaymentHandler.paymentComplete(PaymentHandler.java:22)
  ...
  at org.snowcamp.university.springmodulith.payment.domain.PaymentHandler$$SpringCGLIB$$0.paymentComplete(<generated>)
  at org.snowcamp.university.springmodulith.payment.api.web.PaymentController.paymentComplete(PaymentController.java:22)
  ...
  ```
  
  L'objectif n'est pour l'instant pas de corriger le problème levé par le `GreeterService` mais juste de faire en sorte qu'en cas d'échec, cela ne vienne pas faire échouer la complétion du paiement.
  Une solution pourrait être de rendre le traitement du `GreeterService` [asynchrone](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/scheduling/annotation/Async.html).

</details>

## Exercice 3 - init modulith et visualisation de la structure actuelle

Dans notre appel d'offre, nous avions demandé à Frère Bernard d'utiliser Spring Modulith pour garantir la modularisation
de notre application. Nous avons des projections de croissance à l'international ambitieuses et il n'est pas exclu
que nous découpions notre backend en micro services par la suite.

Il semble que la dépendance Spring Modulith soit commentée, pourriez-vous l'activer
(sans observabilité ni message vers système externe pour l'instant) ?

Une fois Spring Modulith ajouté, vous allez pouvoir étudier la structure actuelle du projet.

Faites en sorte de visualiser les modules actuellement découverts par Modulith, voire d'en générer une documentation. 

<details>
  <summary>Besoin d'aide ?</summary>

  Si vous n'êtes pas familier de Gradle, les dépendances sont dans le fichier [build.gradle.kts](build.gradle.kts).

  Les modules vus par Modulith sont accessibles via :

  ```java
  ApplicationModules.of(ChartreuseShopApplication.class);
  ```

  Vous pouvez simplement rendre dans la sorties standard le résultat de la commande ci-dessus.

  Une autre option, est d'utiliser l'[outil](https://docs.spring.io/spring-modulith/docs/current/api/org/springframework/modulith/docs/Documenter.html) de génération de documentation mis à disposition par Modulith.

  ![structure initiale](static/modularity-mess.png)

</details>

## Exercice 4 - alors, c'est modulaire ?

Vous devriez également pouvoir évaluer la modularité de l'application en ajoutant un `Test` dans
[`ChartreuseShopApplicationTest`](src%2Ftest%2Fjava%2Forg%2Fsnowcamp%2Funiversity%2Fspringmodulith%2FChartreuseShopApplicationTest.java) 
qui fait

```java
ApplicationModules.of(ChartreuseShopApplication.class).verify();
```

Pourquoi échoue-t-il? Nous allons résoudre ces problèmes dans les deux prochains exercices.

## Exercice 5 - module scanning

Nous avons constaté que Frère Bernard avait créé un package common, cela risque de poser un problème si ce dernier
est considéré comme un module.

Il serait certainement plus sage de l'omettre du scanning... 

Pour celà, créez un `Bean` qui implémente l'interface
[`ApplicationModuleDetectionStrategy`](https://docs.spring.io/spring-modulith/docs/current/api/org/springframework/modulith/core/ApplicationModuleDetectionStrategy.html).

<details>
  <summary>Besoin d'aide ?</summary>

  Pour plus d'information sur comment configurer la détection de module, vous pouvez jeter un œil à ce [lien](
https://docs.spring.io/spring-modulith/reference/fundamentals.html#customizing-modules).
</details>

## Exercice 6 - internal events

En lisant la documentation de Spring Modulith, nous avons lu qu'il était préconisé d'opter pour une approche
évènementielle pour ce qui est de la communication entre les modules.  

Vous devriez pouvoir cloisonner les packages en procédant ainsi :

* créez des objets évènements applicatifs sur les order
* servez-vous de [`ApplicationEventPublisher`](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/context/ApplicationEventPublisher.html) pour les publier (`Bean` disponible dans le contexte d'applicationn spring)
* annotez des méthodes de `Component` spring avec en paramétre une instance de ces évènements. Plusieurs annotations existent
    * [`EventListener`](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/context/event/EventListener.html) (avec éventuellement [`Transactionnal`](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/transaction/annotation/Transactional.html)) pour créer un listener **synchrone** (qui s'insèrera éventuellement dans la transaction parente)
    * [`TransactionalEventListener`](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/transaction/event/TransactionalEventListener.html) pour créer un listener qui recevra l'événement **en fin de transaction** uniquement si la transaction est réussie (possibilité de régler la phase de commit)
    *  [`ApplicationModuleListener`](https://docs.spring.io/spring-modulith/docs/current/api/org/springframework/modulith/ApplicationModuleListener.html) qui fait la même chose que le précédant, avec en plus une exécution asynchrone et une transaction dédiée

À la fin de cet exercice le test de l'étape 3 doit passer au vert.

<details>
  <summary>Besoin d'aide ?</summary>
  L'objectif ici est de remplacer les dépendances à des beans d'autres modules en remplaçant les appels directs à des méthodes de ces beans par des envois d'évènements.  

  Ce genre de dépendances est présente dans la classe `OrderManager`, les méthodes `processToPayment` et `paymentComplete` invoquent chacune un bean différents.
</details>

## Exercice 7 - tests

Lorsque nous avons dit à Frère Bernard de tester le produit, il était clair pour nous qu'il s'agissait de s'assurer 
du bon fonctionnement de l'application et par la suite de prévenir les régressions.
Nous n'imaginions pas que ce dernier testerait tous les jours que Dieu fait la qualité gustative de nos Chartreuses Jaune, Verte et AOP.



Nous avons pensé de commencer par garantir le comportement du module qui s'assure de la commande, à savoir le module `order`.
Pour ce faire, créez une classe de tests annotée [`ApplicationModuleTest`](https://docs.spring.io/spring-modulith/docs/current/api/org/springframework/modulith/test/ApplicationModuleTest.html)
dans laquelle vous écrirez deux tests :

* écrivez un premier test qui prend en paramètre `events` de type [`AssertablePublishedEvents`](https://docs.spring.io/spring-modulith/docs/current/api/org/springframework/modulith/test/AssertablePublishedEvents.html) qui doit
    * initialiser un `Order` en base avec un statut en `IN_PAYMENT`
    * appeler la méthode `paymentComplete` sur l'attribut de la classe de test `OrderManager` annoté `Autowired`
    * faire un `assertThat(events)...` pour vérifier qu'un évènement est bien publié avec le bon id
* écrivez un autre test qui prend un objet de paramètre de type [`Scenario`](https://docs.spring.io/spring-modulith/docs/current/api/org/springframework/modulith/test/class-use/Scenario.html). Le test est similaire sauf qu'au lieu d'appeler une quelconque méthode d'`OrderManager` on doit
    * publier un event qui signale que la commande est payée
    * tester qu'un event de commande complète a été émis avec le bon id

## Exercice 8 - reprise d'évènement 

Pouvez vous jouer avec les `Bean` [`CompletedEventPublications`](https://docs.spring.io/spring-modulith/docs/current/api/org/springframework/modulith/events/CompletedEventPublications.html) et [`IncompleteEventPublications`](https://docs.spring.io/spring-modulith/docs/current/api/org/springframework/modulith/events/IncompleteEventPublications.html)  et purger
les évènements et les rejouer ?

Sinon, il paraît qu'il y a un paramètre pour les rejouer au démarrage (sous `spring.mod...`).

<details>
  <summary>Besoin d'aide ?</summary>

  Vous devriez pouvoir trouver votre bonheur dans cette [documentation](https://docs.spring.io/spring-modulith/docs/current-SNAPSHOT/reference/html/#events.publication-registry).
</details>

## Exercice 9 - external events

Les moines en charge de la gestion des stocks aimeraient recevoir un message qui leur signifie le nombre de bouteilles
commandées pour chaque type.

Ils possèdent déjà un système de messaging et voudraient que notre système pousse un message contenant :

* le type de chartreuse
* le nombre de bouteilles


Pour ce faire, rajouter les dépendances et publier

* un objet annoté de l'annotation [`@Externalized`](https://docs.spring.io/spring-modulith/docs/current/api/org/springframework/modulith/events/Externalized.html).  
* On peut même éventuellement paramétrer la clef de manière dynamique ; par exemple, dans la déclaration :

```java
@Externalized("example::#{#this.name().toLowerCase()}") 
record MonExample(String name){}
```

L'objet `new MonExample("YouPi")` sera publié sur le topic `example` avec la clef `youpi`.


Les moines en charge des stocks vous ont monté un environnement de développement à l'image de leur système en production.

Pour vous y connecter :

```shell
docker compose exec -it kafka bash
```

Vous pouvez suivre les messages publiés sur un topic donné, ici par exemple sur le topic `example`

```shell
 /bin/kafka-console-consumer --bootstrap-server localhost:9092 --topic example --from-beginning
```

<details>
  <summary>Besoin d'aide ?</summary>

  L'objectif ici est de créer un nouveau module de gestion des stocks au même titre qu'il en existe déjà pour les commandes, le paiement, ...

  Dans ce module `stock`, créez un nouveau listener qui écoute les évènements que vous publiez déjà depuis la méthode `OrderManager::paymentComplete`.

  Il vous faudra certainement enrichir l'évènement existant pour savoir quels types de bouteilles de Chartreuse sont commandées.

  Publiez alors depuis un Bean du module `stock` un évènement par type de bouteille présent dans la commande.

  Chaque évènement indiquera qu'il faut décrémenter le stock de X pour un type de Chartreuse donné.  
  
  C'est cet évènement pour lequel la classe devra être annotée `@Externalized`. 
  
</details>

## Exercice 10 - observabilité

Nos moines en charge de l'exploitation sont tout excités après avoir assisté à une présentation `OpenTelemetry` et ont monté
une petite infrastructure permettant la collecte de traces applicatives. Pour vous aider, à l'image des moines en charge des stocks,
ils vous ont construit une petite infrastructure pour collecter les traces applicatives.

Pourriez-vous remonter les traces applicatives et les observer dans `Grafana`? 

L'interface de `Grafana` est accessible à http://localhost:3000/.


![illustration](static/observability.png)

## Exercice 11 - visualiser la structure finale 

Vous pouvez désormais générer la documentation finale afin de constater l'évolution de la structure du projet.

<details>
  <summary>Besoin d'aide ?</summary>

Spring Modulith propose un [`Documenter`](https://docs.spring.io/spring-modulith/docs/current/api/org/springframework/modulith/docs/Documenter.htmlhttps://docs.spring.io/spring-modulith/docs/current/api/org/springframework/modulith/docs/Documenter.html) à cet effet.

Vous pouvez simplement générer la documentation dans son format par défaut ainsi :

```java
@Test
void generateDocumentation() {
    ApplicationModules modules = ApplicationModules.of(ChartreuseShopApplication.class);
    new Documenter(modules).writeDocumentation();
}
```

N'hésitez pas à jeter un œil à [`DiagramOptions`](https://docs.spring.io/spring-modulith/docs/current/api/org/springframework/modulith/docs/Documenter.DiagramOptions.html) et [`CanvasOptions`](https://docs.spring.io/spring-modulith/docs/current/api/org/springframework/modulith/docs/Documenter.CanvasOptions.html) qui permettent do configurer le format de votre documentation.

Par défaut, la documentation est générée sous [build/spring-modulith-docs](build/spring-modulith-docs).
</details>
