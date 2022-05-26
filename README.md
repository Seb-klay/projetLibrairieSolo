# Projet de librairie de programmation

---

## Commandes pour les manipulations de l'application

À noter que les options d'une commande peuvent être executées à la suite ou une par une.

### import
La commande import permet d'importer les fichiers - XML et JSON - dans
une liste de l'application.

Cela permettra par la suite d'effectuer des traitements dessus 
(voir ci-dessous).

```
import -f nom du fichier
```

Exemple d'application de la commande :

```
import -f data.json
```
### export
La commande export permet de transférer la liste obtenue dans l'application
en "produit fini", c'est-à-dire en un fichier de type JSON avec les informations
pour une ou toutes les compétitions.

```
export -f nom du fichier
export -c nom de la compétition
```

Exemple d'application de la commande :

```
export -f test.json -c Effium
```

### add
ATTENTION : Tous les paramètres sont nécessaires pour cette commande.

La commande add permet d'ajouter un athlète en fonction des paramètres
donnés en argument.

```
add -$ prix
add -a année de naissance de l'athlète
add -c nom de la compétition
add -n nom de l'athlète
add -p prénom de l'athlète
```

Exemple d'application de la commande :

```
add -$ 32 -a 2000 -c Effium -n Hardman -p Julietta
```
### delete
ATTENTION : Tous les paramètres sont nécessaires pour cette commande.

La commande delete permet de supprimer un athlète en fonction des paramètres
donnés en argument.

```
delete -a année de naissance de l'athlète
delete -c nom de la compétition
delete -n nom de l'athlète
delete -p prénom de l'athlète
```

Exemple d'application de la commande :

```
delete -a 2000 -c Effium -n Hardman -p Julietta
```

### donateurs
La commande donateurs permet d'afficher les 5 plus gros donateurs d'une 
compétition donnée.

```
donateurs -c nom de la compétition
```

Exemple d'application de la commande :

```
donateurs -c Effium
```

### dons
La commande dons permet d'afficher les athlètes qui n'ont pas payés leurs dons.

```
dons -c nom de la compétition
```

Exemple d'application de la commande :

```
dons -c Effium
```

### somme
La commande somme permet d'afficher la somme des dons déjà payés, 
des dons restants et de la somme totale d'une compétition.

```
somme -c nom de la compétition
```
Exemple d'application de la commande :

```
somme -c Effium
```

### mail
La commande mail permet d'afficher la liste des e-mails des participants d'une compétition donnée,
chacun suivi d'un séparateur ";".

```
mail -c nom de la compétition
```

Exemple d'application de la commande :

```
mail -c Effium
```
### pays
La commande pays permet d'afficher les pays participants à une compétition donnée.

```
pays -c nom de la compétition
```

Exemple d'application de la commande :

```
pays -c Effium
```
### categorie
La commande categorie permet d'afficher la catérogie d'un athlète d'une compétition.
Les catégories sont les suivantes :
- Junior (18-25 ans)
- Elite (26-45 ans)
- Vétéran 1 (46-65 ans)
- Vétéran 2 (66 ans et plus)

```
categorie -c nom de la compétition
```

Exemple d'application de la commande :

```
categorie -c Effium
```
### stats

Cette fonctionnalité marche avec ou sans paramètre. On peut obtenir les statistiques d'une
ou toutes les compétitions.

Cette fonctionnalité permet de créer un fichier PDF avec les informations
comme les plus gros donateurs, les pays participants

```
stats
stats -c nom de la compétition
```

Exemple d'application de la commande :

```
stats
stats -c Effium
```

### exit
La commande exit permet de fermer le programme.
Aucune option n'est nécessaire.

```
exit
```