**Über arc42**

arc42, das Template zur Dokumentation von Software- und
Systemarchitekturen.

Erstellt von Dr. Gernot Starke, Dr. Peter Hruschka und Mitwirkenden.

Template Revision: 7.0 DE (asciidoc-based), January 2017

© We acknowledge that this document uses material from the arc42
architecture template, <http://www.arc42.de>. Created by Dr. Peter
Hruschka & Dr. Gernot Starke.

Einführung und Ziele {#section-introduction-and-goals}
====================

Aufgabenstellung {#_aufgabenstellung}
----------------

Qualitätsziele {#_qualit_tsziele}
--------------



Stakeholder {#_stakeholder}
-----------

+-----------------+-----------------+-----------------------------------+
| Rolle           | Kontakt         | Erwartungshaltung                 |
+=================+=================+===================================+
| *\<Rolle-1\>*   | *\<Kontakt-1\>* | *\<Erwartung-1\>*                 |
+-----------------+-----------------+-----------------------------------+
| *\<Rolle-2\>*   | *\<Kontakt-2\>* | *\<Erwartung-2\>*                 |
+-----------------+-----------------+-----------------------------------+

Randbedingungen {#section-architecture-constraints}
===============

Kontextabgrenzung {#section-system-scope-and-context}
=================

Fachlicher Kontext {#_fachlicher_kontext}
------------------

**\<Diagramm und/oder Tabelle\>**

**\<optional: Erläuterung der externen fachlichen Schnittstellen\>**

Technischer Kontext {#_technischer_kontext}
-------------------

**\<Diagramm oder Tabelle\>**

**\<optional: Erläuterung der externen technischen Schnittstellen\>**

**\<Mapping fachliche auf technische Schnittstellen\>**

Lösungsstrategie {#section-solution-strategy}
================

Bausteinsicht {#section-building-block-view}
=============

Whitebox Gesamtsystem {#_whitebox_gesamtsystem}
---------------------

***\<Übersichtsdiagramm\>***

Begründung

:   *\<Erläuternder Text\>*

Enthaltene Bausteine

:   *\<Beschreibung der enthaltenen Bausteine (Blackboxen)\>*

Wichtige Schnittstellen

:   *\<Beschreibung wichtiger Schnittstellen\>*

### \<Name Blackbox 1\> {#__name_blackbox_1}

*\<Zweck/Verantwortung\>*

*\<Schnittstelle(n)\>*

*\<(Optional) Qualitäts-/Leistungsmerkmale\>*

*\<(Optional) Ablageort/Datei(en)\>*

*\<(Optional) Erfüllte Anforderungen\>*

*\<(optional) Offene Punkte/Probleme/Risiken\>*

### \<Name Blackbox 2\> {#__name_blackbox_2}

*\<Blackbox-Template\>*

### \<Name Blackbox n\> {#__name_blackbox_n}

*\<Blackbox-Template\>*

### \<Name Schnittstelle 1\> {#__name_schnittstelle_1}

...

### \<Name Schnittstelle m\> {#__name_schnittstelle_m}

Ebene 2 {#_ebene_2}
-------

### Whitebox *\<Baustein 1\>* {#_whitebox_emphasis_baustein_1_emphasis}

*\<Whitebox-Template\>*

### Whitebox *\<Baustein 2\>* {#_whitebox_emphasis_baustein_2_emphasis}

*\<Whitebox-Template\>*

...

### Whitebox *\<Baustein m\>* {#_whitebox_emphasis_baustein_m_emphasis}

*\<Whitebox-Template\>*

Ebene 3 {#_ebene_3}
-------

### Whitebox \<\_Baustein x.1\_\> {#_whitebox_baustein_x_1}

*\<Whitebox-Template\>*

### Whitebox \<\_Baustein x.2\_\> {#_whitebox_baustein_x_2}

*\<Whitebox-Template\>*

### Whitebox \<\_Baustein y.1\_\> {#_whitebox_baustein_y_1}

*\<Whitebox-Template\>*

Laufzeitsicht {#section-runtime-view}
=============

*\<Bezeichnung Laufzeitszenario 1\>* {#__emphasis_bezeichnung_laufzeitszenario_1_emphasis}
------------------------------------

-   \<hier Laufzeitdiagramm oder Ablaufbeschreibung einfügen\>

-   \<hier Besonderheiten bei dem Zusammenspiel der Bausteine in diesem
    Szenario erläutern\>

*\<Bezeichnung Laufzeitszenario 2\>* {#__emphasis_bezeichnung_laufzeitszenario_2_emphasis}
------------------------------------

...

*\<Bezeichnung Laufzeitszenario n\>* {#__emphasis_bezeichnung_laufzeitszenario_n_emphasis}
------------------------------------

...

Verteilungssicht {#section-deployment-view}
================

Infrastruktur Ebene 1 {#_infrastruktur_ebene_1}
---------------------

***\<Übersichtsdiagramm\>***

Begründung

:   *\<Erläuternder Text\>*

Qualitäts- und/oder Leistungsmerkmale

:   *\<Erläuternder Text\>*

Zuordnung von Bausteinen zu Infrastruktur

:   *\<Beschreibung der Zuordnung\>*

Infrastruktur Ebene 2 {#_infrastruktur_ebene_2}
---------------------

### *\<Infrastrukturelement 1\>* {#__emphasis_infrastrukturelement_1_emphasis}

*\<Diagramm + Erläuterungen\>*

### *\<Infrastrukturelement 2\>* {#__emphasis_infrastrukturelement_2_emphasis}

*\<Diagramm + Erläuterungen\>*

...

### *\<Infrastrukturelement n\>* {#__emphasis_infrastrukturelement_n_emphasis}

*\<Diagramm + Erläuterungen\>*

Querschnittliche Konzepte {#section-concepts}
=========================

*\<Konzept 1\>* {#__emphasis_konzept_1_emphasis}
---------------

*\<Erklärung\>*

*\<Konzept 2\>* {#__emphasis_konzept_2_emphasis}
---------------

*\<Erklärung\>*

...

*\<Konzept n\>* {#__emphasis_konzept_n_emphasis}
---------------

*\<Erklärung\>*

Entwurfsentscheidungen {#section-design-decisions}
======================

Qualitätsanforderungen {#section-quality-scenarios}
======================

Qualitätsbaum {#_qualit_tsbaum}
-------------

Qualitätsszenarien {#_qualit_tsszenarien}
------------------

Risiken und technische Schulden {#section-technical-risks}
===============================

Glossar {#section-glossary}
=======

+-----------------------+-----------------------------------------------+
| Begriff               | Definition                                    |
+=======================+===============================================+
| *\<Begriff-1\>*       | *\<Definition-1\>*                            |
+-----------------------+-----------------------------------------------+
| *\<Begriff-2*         | *\<Definition-2\>*                            |
+-----------------------+-----------------------------------------------+
