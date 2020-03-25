Umfrage aus Datei erstellen
===

Es ist möglich, eine Umfrage aus einer Datei zu erstellen. Ein dafür geeignetes Format ist JSON, dass sich sehr leicht
parsen lässt und im Zweifelsfall auch per Hand erstellt werden kann.

Grundsätzlich müssen alle Attribute definiert sein, die auch bei der Erstellung einer Umfrage per Weboberfläche 
benötigt werden. Im Folgenden sind alle Attribute aufgeführt, die angegeben werden können:

| Attribut      | notwendig | Beschränkungen                        | Beschreibung
|---------------|-----------|---------------------------------------|-------------------------------------------------------
| title         | ja        | maximal 60 Zeichen                    | Der Titel der Umfrage, der fast überall angezeigt wird.
| description   | nein      | maximal 600 Zeichen                   | Beschreibung der Umfrage, kann etwas länger sein.
| location      | nein      | maximal 100 Zeichen                   | Ortsangabe als String
| startDate     | ja        | Format: "YYYY-MM-DD"                  | Ab diesem Datum darf abgestimmt werden.
| startTime     | ja        | Format: "HH:MM"                       | Am Startdatum darf ab dieser Zeit abgestimmt werden.
| endDate       | ja        | Format: "YYYY-MM-DD"                  | Bis zu diesem Datum darf abgestimmt werden.
| endTime       | ja        | Format: "HH:MM"                       | Am Enddatum darf bis zu dieser Zeit abgestimmt werden.
| voteIsEditable| nein      | true/false, default: true             | Legt fest, ob die eigenen Stimmen nachträglich geändert werden dürfen.
| openForOwnEntries| nein   | true/false                            | **nicht implementiert** Dürfen die Nutzer*innen eigene Einträge hinzufügen?
| singleChoice  | nein      | true/false, default: false            | true: Nur ein Termin darf ausgewählt werden
| priorityChoice| nein      | true/false, default: false            | true: Es gibt eine zusätzliche Vielleicht-Option
| anonymous     | nein      | true/false, default: false            | true: Die Namen der Teilnehmer*innen werden nicht angezeigt
| ispublic        | nein      | true/false, default: true             | true: Alle angemeldeten Nutzer*innen dürfen an der Umfrage teilnehmen
| groups        | wenn public=true| List mit IDs der Gruppen        | Legt Gruppen fest, die abstimmen dürfen.
| entries       | ja        | Mindestens ein Eintrag                | Die Terminoptionen, über die abgestimmt wird.
| entries.date  | ja        | Format: "YYYY-MM-DD"                  | Datum eines Termins
| entries.startTime| ja     | Format: "HH:MM"                       | Startzeit eines Termins
| entries.endTime| ja       | Format: "HH:MM"                       | Endzeit eines Termins

Eine Beispieldatei:

```json
{
  "title": "Kuchenessen mit Paul",
  "description": "So sollte eine Datei aussehen, die automatisch eingelesen wird.",
  "location": "überall und immer",
  "startDate": "2020-03-25",
  "startTime": "12:00",
  "endDate": "2020-04-25",
  "endTime": "18:00",
  "voteIsEditable": true,
  "openForOwnEntries": false,
  "singleChoice": false,
  "priorityChoice": true,
  "anonymous": false,
  "open": false,
  "groups": ["termine1", "big_f_for_auas", "gruppe-xyz"],
  "entries": [
    {
      "date": "2020-05-20",
      "startTime": "16:00",
      "endTime": "17:00"
    },
    {
      "date": "2020-05-21",
      "startTime": "16:00",
      "endTime": "17:00"
    },
    {
      "date": "2020-05-22",
      "startTime": "16:00",
      "endTime": "17:00"
    }
  ]
}
```
