#Erstellung einer Terminfindung

[siehe arc42 whitebox ebene 2](../arc42-template-DE.md) für eine grobe Visuelle Übersicht der Terminfindungserstellung.

Folgende Architektur Entscheidungen wurden getroffen:

1. Wir haben uns dafür entschieden alle Value Objekte eines Aggregates nach außen zu exposen,
und alle für jede Interaktion mit Entitäten dtos zu benutzen. Falls es invarianten zwischen view und einem value objekt gibt
können wir hier natürlich auch dtos benutzen. 
Wenn man stadtdessen auch Entitäten exposed, könnte die infrastrukturschicht den State einer Entität verändern ohne über die
root Entität des Aggregats zu gehen, dies stellt eine Konsistenz verletzung dar. 
Anderseits könnte man auch alle Values verpacken, dardurch würden wir ein einheitlicheres Aufrufschema erreichen, allerdings 
haben wir es als unnötig erachtet mehr dto Klassen zu erstellen als notwendig zu erstlen um mapping code und doppelete Entscheidungen 
über die Representation der Daten zu vermeiden
2. Wir haben die Konstruktion und Validierung von unseren Domain modellen getrent. Ein domain model Objekt ist somit nicht unbedingt
in einem validen state nachdemdem es initialisert wird, aber im Builder wird das Objekt nur in die root entität geschrieben, wenn
auch die Valieedierung zu keinen Fehlern führt, so haben wir auch das obige Problem des einheitlichen Aufrufschemas entschärft. 
Wir hatten zuerst überlegt nur über Exceptions bei dem Konstruktoraufruf den state sicherzustellen, aber dies hat mehrere Probleme:
    1. Da wir uns bei 1 für ein nicht einheitliches Aufrufschema entschieden und somit würden exception an unterschiedlichen
    stellen im flow 