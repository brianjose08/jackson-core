Brian-José Mejia-Rivera 20210144 et Raymond Cung 20232716

# Test 1:
## appendFourbytes(int)

[Fichier Main](src/main/java/com/fasterxml/jackson/core/util/ByteArrayBuilder.java) <br>
[Fichier Test](src/test/java/com/fasterxml/jackson/core/util/ByteArrayBuilderTest.java) (ligne 82)

Ce test vérifie que appendFourBytes fonctionne correctement en ajoutant une valeur de quatre octets dans le buffer. Dans le fond on s'assure que chaque octet de l'entier est placé dans le bon ordre afin de éviter des problèmes de corruption de données.

### Avant 
<img src="images_documentation\test1avant.png" width="700" />

### Après
<img src="images_documentation\test1apres.png" width="700" />

# Test 2:
## equals(int[], int)

[Fichier Main](src/main/java/com/fasterxml/jackson/core/sym/NameN.java) <br>
[Fichier Test](src/test/java/com/fasterxml/jackson/core/sym/NameNTest.java) (ligne 13)

Ce test vérifie que la méthode equals compare correctement les tableaux de quads en fonction de leur longeur et de leur contenu. L'ajout de ce test est important, puisque la méthode equals doit garantir la précision des comparaisons pour éviter des erreurs de correspondance.

### Avant 
<img src="images_documentation\test2avant.png" width="700" />

### Après
<img src="images_documentation\test2apres.png" width="700" />

# Test 3:
## skipChildren()

[Fichier Main](src/main/java/com/fasterxml/jackson/core/util/JsonParserSequence.java) <br>
[Fichier Test](src/test/java/com/fasterxml/jackson/core/read/ParserSequenceTest.java) (ligne 135)

Ce test vérifie que la méthode skipChildren de la class JsonParserSequence fait en sorte que le parser passe à l'élément suivant dans une structure imbriquée JSON. Cette méthode est bien utile puisque elle nous permet de ignorer des sous-structures dans un JSON afin d'éviter le traitement inutile des données.

### Avant 
<img src="images_documentation\test3avant.png" width="700" />

### Après
<img src="images_documentation\test3apres.png" width="700" />

# Test 4:
## appendDesc(StringBuilder)

[Fichier Main](src/main/java/com/fasterxml/jackson/core/filter/TokenFilterContext.java) <br>
[Fichier Test](src/test/java/com/fasterxml/jackson/core/filter/TokenFilterContextTest.java) (ligne 13)

Ce test vérifie que la méthode appendDesc génère correctement la représentation du chemin JSON en fonction de l'état actuel du contexte. C'est important car ce test garantit que cette méthode fournit une description exact du chemin, qui pourrait éviter des problèmes dans les tâches come le filtrage.

### Avant 
<img src="images_documentation\test4avant.png" width="700" />

### Après
<img src="images_documentation\test4apres.png" width="700" />

# Test 5:
## enable(JsonParserFeature)

[Fichier Main](src/main/java/com/fasterxml/jackson/core/util/JsonParserDelegate.java) <br>
[Fichier Test](src/test/java/com/fasterxml/jackson/core/util/DelegatesTest.java) (ligne 497)

Ce test vérifie que la méthode enable permet d'activer une fonctionnalité spécifique du JsonParser. Dans notre cas, la fonctionnalité testée est ALLOW_COMMENTS, qui permet d'autoriser les commentaires dans le JSON. Ce test garantit alors l'activation des options spécifiques pour personnaliser le comportement du parsing JSON sans problèmes.

### Avant 
<img src="images_documentation\test5-6avant.png" width="700" />

### Après
<img src="images_documentation\test5-6apres.png" width="700" />

# Test 6:
## disable(JsonParserFeature)

[Fichier Main](src/main/java/com/fasterxml/jackson/core/util/JsonParserDelegate.java) <br>
[Fichier Test](src/test/java/com/fasterxml/jackson/core/util/DelegatesTest.java) (ligne 516)

Ce test vérifie que la méthode enable permet de désactivé une fonctionnalité spécifique du JsonParser. Encore une fois, la fonctionnalité testée est ALLOW_COMMENTS, qui permet d'autoriser les commentaires dans le JSON. Ce test garantit alors la désactivation des options spécifiques sans problèmes.

### Avant 
<img src="images_documentation\test5-6avant.png" width="700" />

### Après
<img src="images_documentation\test5-6apres.png" width="700" />

# Test 7:
## size()

[Fichier Main](src/main/java/com/fasterxml/jackson/core/util/ByteArrayBuilder.java) <br>
[Fichier Test](src/test/java/com/fasterxml/jackson/core/util/ByteArrayBuilderTest.java) (ligne 107)

Ce test vérifie que la méthode size renvoie correctement le nombre total d'octets actuellement présents dans le buffer après plusieurs opérations d'écriture. Cela permet de s'assurer que la taille du buffer est mise à jour correctement.

### Avant 
<img src="images_documentation\test7avant.png" width="700" />

### Après
<img src="images_documentation\test7apres.png" width="700" />

# Test 8:
## flush()

[Fichier Main](src/main/java/com/fasterxml/jackson/core/util/ByteArrayBuilder.java) <br>
[Fichier Test](src/test/java/com/fasterxml/jackson/core/util/ByteArrayBuilderTest.java) (ligne 129)

Ce test vérifie que la méthode flush ne modifie pas le contenu du buffer. Cela garantit que le buffer reste intact après l'opération de flush, ce qui est crucial pour le traitement des données dans des environnements où l'intégrité des données est essentielle.

### Avant 
<img src="images_documentation\test8avant.png" width="700" />

### Après
<img src="images_documentation\test8apres.png" width="700" />

# Test 9:
## overrideCurrentName()

[Fichier Main](src/main/java/com/fasterxml/jackson/core/util/JsonParserDelegate.java) <br>
[Fichier Test](src/test/java/com/fasterxml/jackson/core/util/DelegatesTest.java) (ligne 535)

Ce test vérifie que la méthode release libère correctement les ressources allouées au buffer. Cela permet de s'assurer qu'il n'y a pas de fuites de mémoire et que les ressources sont gérées de manière efficace.

