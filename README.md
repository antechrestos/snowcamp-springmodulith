# A la découverte de Spring Modulith

Si vous comptez assister à notre atelier sur Spring Modulith lors du Snowcamp, nous vous invitons fortement à suivre les étapes suivantes AVANT le jour J.  
Cela nous permettra de nous concenter sur l'atelier en lui même et de ne pas perdre trop de temps avec les différents aléas techniques.

## Prérequis

Afin de jouer cet atelier, assurez vous d'avoir sur votre poste :
- Java 17
- Docker Compose

## Lancement de l'infrastructure

Dans un terminal, allez dans le répertoire `infrastructure` et exécutez la commande

```shell
docker compose up
```

Au bout de quelques secondes, ouvrez un autre terminal et **dans ce même répertoire `infrastructure`**, exécutez la commande

```shell
 docker compose ps --status=running --format 'table {{.Name}}'
```

Celle-ci doit afficher les cinq lignes suivantes

```shell
chartreuse-shop-kafka-1
chartreuse-shop-mongo-1
chartreuse-shop-monitoring-grafana-1
chartreuse-shop-monitoring-otel-collector-1
chartreuse-shop-monitoring-tempo-1
```

S'il vous manque des lignes, c'est que des containers se sont arrêtés.  
Merci de saisir une **issue** en mettant les lignes d'arrêt du container, vous les trouverez dans le terminal qui a lancé la commande `docker compose up`. 

## Téléchargement des dépendances applicatives

Dans un **autre** terminal, à la racine du projet, exécutez la commande `./gradlew clean build` (ou `./gradlew.bat clean build`)
selon votre système d'exploitation.

Vous devriez voir apparaître en fin de processus la ligne `BUILD SUCCESSFUL`.

En cas de soucis, merci de saisir une **issue** avec le résultat de l'éxécution de la commande.
