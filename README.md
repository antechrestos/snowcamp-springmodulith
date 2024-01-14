# A la découverte de Spring Modulith

## Prérequis

Afin de jouer cet atelier, assurez vous d'avoir sur votre poste :
- Java 17
- Docker Compose

## Lancement de l'infrastructure

Dans un terminal, allez dans le répertoire `infrastructure` et exécutez la command

```shell
docker compose up
```

Au bout de quelques secondes, ouvrez un autre terminal et **dans ce même répertoire `infrastructure`**, exécutez la commande

```shell
 docker compose ps --status=running --format 'table {{.Name}}'
```

affiche les cinq lignes telles que

```shell
chartreuse-shop-kafka-1
chartreuse-shop-mongo-1
chartreuse-shop-monitoring-grafana-1
chartreuse-shop-monitoring-otel-collector-1
chartreuse-shop-monitoring-tempo-1
```

les containers manquant se sont arrêtés. Merci de saisir une issue en mettant les lignes d'arrêt du container qui 
sont dans le terminal qui a lancé la commande `docker compose up`. 

## Téléchargement des dépendances applicatives

Dans un **autre** terminal, exécutez la commande `./gradlew clean build` (ou `./gradlew.bat clean build` )
selon votre système d'exploitation.

Vous devriez voir apparaître en fin de processus la ligne `BUILD SUCCESSFUL`.

En cas de soucis, merci de saisir une **issue** avec les informations demandées.
