Brian-José Mejia-Rivera 20210144 et Raymond Cung 20232716

# Test 1:
## appendFourbytes(int)

[Fichier Main](src/main/java/com/fasterxml/jackson/core/util/ByteArrayBuilder.java) <br>
[Fichier Test](src/test/java/com/fasterxml/jackson/core/util/ByteArrayBuilderTest.java) (ligne 82)

Ce test vérifie que appendFourBytes fonctionne correctement en ajoutant une valeur de quatre octets dans le buffer. Dans le fond on s'assure que chaque octet de l'entier est placé dans le bon ordre afin de éviter des problèmes de corruption de données.

### Avant 
<img src="images_documentation\test1avant.png" width="700" />

### Apres
<img src="images_documentation\test1apres.png" width="700" />

# Test 2:
## equals(int[], int)

[Fichier Main](src/main/java/com/fasterxml/jackson/core/sym/NameN.java) <br>
[Fichier Test](src/test/java/com/fasterxml/jackson/core/sym/NameNTest.java) (ligne 13)

Ce test vérifie que la méthode equals compare correctement les tableaux de quads en fonction de leur longeur et de leur contenu. L'ajout de ce test est important, puisque la méthode equals doit garantir la précision des comparaisons pour éviter des erreurs de correspondance.

### Avant 
<img src="images_documentation\test2avant.png" width="700" />

### Apres
<img src="images_documentation\test2apres.png" width="700" />

# Test 3:
## skipChildren()

[Fichier Main](src/main/java/com/fasterxml/jackson/core/util/JsonParserSequence.java) <br>
[Fichier Test](src/test/java/com/fasterxml/jackson/core/read/ParserSequenceTest.java) (ligne 135)

Ce test vérifie que la méthode skipChildren de la class JsonParserSequence fait en sorte que le parser passe à l'élément suivant dans une structure imbriquée JSON. Cette méthode est bien utile puisque elle nous permet de ignorer des sous-structures dans un JSON afin d'éviter le traitement inutile des données.

### Avant 
<img src="images_documentation\test3avant.png" width="700" />

### Apres
<img src="images_documentation\test3apres.png" width="700" />

# Test 4:
## appendDesc(StringBuilder)

# Test 5:
## enable(JsonParserFeature)

# Test 6:
## enable(JsonParserFeature)

# Test 7:
## size()

# Test 8:
## flush()


