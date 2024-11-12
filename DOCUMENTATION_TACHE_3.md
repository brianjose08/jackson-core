Brian-José Mejia-Rivera 20210144 et Raymond Cung 20232716

# Documentation tâche 3 <br>

## Changements apportés à la Github action
Les changements que nous avons faits pour pouvoir exécuter les cinq flags que nous avons choisis concernent principalement des changements dans le fichier `test.yml` et dans le fichier `pom.xml`. 

### Changements dans le fichier test.yml
Puisque dans cette tâche on cherche à exécuter un job plusieurs fois avec un flag JVM différent, il est utile dans ce cas d'utilisé la fonctionnalité de GitHub Actions qui s'appelle matrix strategy (strategy.matrix). Cette fonctionnalité nous permet justement de lancer plusieurs variantes du même job, chacune avec un flag JVM différent. <br>

Voici une documentation fournit par Github Docs par rapport aux "matrix strategies" (documentation de Github Docs) : [Running variations of jobs in a workflow](https://docs.github.com/en/actions/writing-workflows/choosing-what-your-workflow-does/running-variations-of-jobs-in-a-workflow)

### Changements dans le fichier pom.xml
Nous avons rencontré un problème lorsque nous essayons de exécuter un build avec succès. Le problème était dû au fait que dans le projet parent de jackson-core, le fichier `pom.xml` à été modifié de façon qu'une version différent de `snapshot` est utilisé dans le projet parent. En conséquence, cela à mener au fait que nos build ne passent pas avec succès puisque nous n'avions pas la bonne version. Il suffisait uniquement dans ce cas là de changer la version du snapshot dans notre fichier `pom.xml`.

## Choix et justification des cinq flags

### -XX:+UseG1GC
Ce flag active le garbage collector G1, qui est fait pour réduire les pauses de nettoyage de mémoire dans les grandes applications. Cela rend donc les applications plus rapide et plus fluide, parce que la gestion de la mémoire est plus efficace. En testant avec ce flag, on peut donc voir si l'optimisation de la mémoire améliore effectivement la stabilité et la vitesse de l'application lors des tests.

### -XX:+UseZGC
Ce flag active le garbage collector ZGC, qui minimise les pauses de collecte de mémoire, entrainant une amélioration de la performance des applications qui ont besoin de réagir rapidement sans interruptions. Ce flag est donc utile pour voir comment le faible temps de pause pourrait affecter les performances globales de l'application.

### -XX:+UseCompressedOops
Ce flag active les pointeurs d'objets compressés, ce qui entraîne une réduction de l'utilisation de la mémoire. Il améliore donc les performances pour les applications qui ont un haut niveau de consommation de mémoire et il évite également les erreurs de mémoire insuffisante.

### -XX:+HeapDumpOnOutOfMemoryError 
Ce flag va créer un fichier de dump mémoire quand une erreur de mémoire (typiquement OutOfMemoryError) déclenche. Le fichier qui est créer aide à comprende ce qui à causé l'erreur. Simplement dit, c'est utile pour le débogage.


### -XX:MaxNewSize
Ce flag fixe la taille maximale de la zone mémoire où les nouveaux objets sont créés. En ajustant cette taille, cela peut entraîner une amélioration des performances en réduisant la fréquence des nettoyages de mémoire fréquents et rendre l'application plus stable. C'est donc utile pour les applications qui créent beaucoup d'objets temporaires.
