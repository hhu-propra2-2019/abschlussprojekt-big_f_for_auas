#Erstellung einer Terminfindung

[siehe arc42 whitebox ebene 2](../arc42-template-DE.md) für eine grobe Visuelle Übersicht der Terminfindungserstellung.

Folgende Architektur Entscheidungen wurden getroffen:

1. Wir haben uns dafür entschieden alle Value Objekte eines Aggregates nach außen zu exposen,
und für jede Interaktion mit Entitäten dtos zu benutzen. Falls es invarianten zwischen view und einem value Objekt gibt
können wir hier natürlich auch dtos benutzen. 
Wenn man stadtdessen auch Entitäten exposed, könnte die Infrastrukturschicht den State einer Entität verändern ohne über die
root Entität des Aggregats zu gehen, dies stellt eine Konsistenz Verletzung dar. 
Anderseits könnte man auch alle Values verpacken, dadurch würden wir ein einheitlicheres Aufrufschema erreichen, allerdings 
haben wir es als unnötig erachtet, mehr dto Klassen als notwendig zu erstellen, um mapping code und doppelete Entscheidungen 
über die Representation der Daten zu vermeiden.
2. Wir haben die Konstruktion und Validierung von unseren Domain modellen getrennt. Ein domain model Objekt ist somit nicht unbedingt
in einem validen state nachdem es initialisert wird. Aber im Builder wird das Objekt nur in die root Entität geschrieben, wenn
auch die Validierung zu keinen Fehlern führt, so haben wir auch das obige Problem des einheitlichen Aufrufschemas entschärft. 
Wir hatten zuerst überlegt nur über Exceptions bei dem Konstruktoraufruf den state sicherzustellen, aber dies hat mehrere Probleme:
    1. Da wir uns bei 1 für ein nicht einheitliches Aufrufschema entschieden haben, würden Exceptions an unterschiedlichen
    stellen im flow auftreten.
    2. Man Könnte nicht alle Eingabefehler auf einmal zurückgeben.
