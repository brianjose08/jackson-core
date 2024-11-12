Brian-José Mejia-Rivera 20210144 et Raymond Cung 20232716

# Documentation tâche 3 <br>

## Changements apportés à la Github action
Les changements que nous avons faits pour pouvoir exécuter les cinq flags que nous avons choisis concernent principalement des changements dans le fichier `test.yml` et dans le fichier `pom.xml`. 

### Changements dans le fichier test.yml
Puisque dans cette tâche on cherche à exécuter un job plusieurs fois avec un flag JVM différent, il est utile dans ce cas d'utilisé la fonctionnalité de GitHub Actions qui s'appelle matrix strategy (strategy.matrix). Cette fonctionnalité nous permet justement de lancer plusieurs variantes du même job, chacune avec un flag JVM différent. <br>

Voici une documentation fournit par Github Docs par rapport aux "matrix strategies" (documentation de Github Docs) : [Running variations of jobs in a workflow](https://docs.github.com/en/actions/writing-workflows/choosing-what-your-workflow-does/running-variations-of-jobs-in-a-workflow)

