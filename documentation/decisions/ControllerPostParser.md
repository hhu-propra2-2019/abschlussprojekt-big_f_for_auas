<h4> DatePollEntryConverter : </h>

- Um bei Spring in der Post Controller Methode das Set von DatePollEntries zurückzukriegen, braucht Spring durch `th:value` einen uniquen Identifier.
(in der Controller Methode annotiert man dann das formular, das man zurückkriegen möchte mit `@ModelAttribute("name_des_attributes")`).

- Das Formular ist DatePollEntryOverview und beinhaltet 3 Sets : 
    + Alle auswählbaren Entries
    + Die "JA" ausgewählten Entries
    + Die "Vielleicht" ausgewählten Entries
    

Dabei wird im Template bei thymeleaf bei den checkboxen mit `th:field="name_des_feldes_im_formular"` das Attribut festgelegt wo der input gespeichert wird.

Da wir aber in dem Formular selbst Collections haben, in der Objekte vom Typ DatePollEntryDto sind, muss man Spring sagen wie genau man im Post die Entries zurückgewinnt aus dem `th:field`.

Da im `th:value` ein String steht, wird folgender Converter benutzt :

    Die DatePollEntryDto haben ein Timespan als uniquen Identifier, der aus 2 `LocalDateTimes` (*start* und *end* ) besteht.
    Diesen stellen wir durch die getId Methode dar als  :
    
    [*String_Darstellung_vom_ersten_Termin*]@[*String_Darstellung_vom_ersten_Termin*]

Beim Post Mapping werden dann die entsprechenden DatePollDtos mit dieser Id und dem DatePollEntryConverter erstellt
, indem dieser IdString nach dem "@" gesplittet wird und dann LocalDateTime.parse() 2 mal auf die entstandenen Strings aufgerufen wird.

Dieser Converter ist nötig, da Spring ansonsten nur primitive Datentypen automatisch mapped.