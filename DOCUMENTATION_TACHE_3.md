Brian-José Mejia-Rivera 20210144 et Raymond Cung 20232716

# Documentation tâche 3 <br>

## Changements apportés à la Github action
Les changements que nous avons faits pour pouvoir exécuter les cinq flags que nous avons choisis concernent principalement des changements dans le fichier `test.yml` et dans le fichier `pom.xml`. 

### Changements dans le fichier test.yml
Puisque dans cette tâche on cherche à exécuter un job plusieurs fois avec un flag JVM différent, il est utile dans ce cas d'utilisé la fonctionnalité de GitHub Actions qui s'appelle matrix strategy (strategy.matrix). Cette fonctionnalité nous permet justement de lancer plusieurs variantes du même job, chacune avec un flag JVM différent. <br>

Voici une documentation fournit par Github Docs par rapport aux "matrix strategies" (documentation de Github Docs) : [Running variations of jobs in a workflow](https://docs.github.com/en/actions/writing-workflows/choosing-what-your-workflow-does/running-variations-of-jobs-in-a-workflow)

### Changements dans le fichier pom.xml
Nous avons rencontré un problème lorsque nous essayons de exécuter un build avec succès. Le problème était dû au fait que dans le projet parent de jackson-core, le fichier `pom.xml` à été modifié de façon qu'une version différent de `snapshot` est utilisé dans le projet parent. En conséquence, cela à mener au fait que nos build ne passent pas avec succès puisque nous n'avions pas la bonne version. Il suffisait uniquement dans ce cas là de changer la version du snapshot dans notre fichier `pom.xml`.
