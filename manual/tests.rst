Test
====

@TODO Erstmal so einen Test erklären:

- Welche Parameter gibt es
- Wie läuft der Test ab
  - Was muss der Versuchsleiter davor wissen
  - Was muss er dem Probanten sagen
  - In welcher Reihenfolge passiert was.
  - Allgemeine Beschreibungen nach oben und nicht in den Text unten zwischendrin einfließen lassen (Abbrechen geht immer, VL sieht niemals die flimmernde LED)

Neuer Test
----------
Zum Starten eines neuen Tests wird "Neuer Test" ausgewählt und es erscheint ein Fenster, in dem die Parameter für den Test eingegeben und mit "Finish" bestätigt werden.

.. image:: screenshots/test_dialog.*

Test Durchführen
----------------
Nach Eingabe der Testparamter kann der Test gestartet werden. Die zuvor eingegebenen Parameter sind für den Versuchsleiter im
oberen Teil der Dialogbox zu sehen, darunter befindet sich das aktuelle Testprotokoll.
Gestartet wird der Test durch den Button "Start" und abgebrochen werden kann er über den Button "Abbruch".
Die Antwortmöglichkeiten des Probanden können entweder durch das Anklicken des jeweiligen Buttons oder durch drücken der in eckigen Klammern stehenden Zahlen auf der Tastatur.

.. image:: screenshots/testrunner_dialog.*

Nach dem Starten des Tests erscheint ein Fragezeichen beim jeweiligen Testdurchlauf. Der Versuchsleiter bekommt während dem Testdurchlauf nicht angezeigt welche der LEDs die flimmernde ist. Die rot eingefärbte Glühbirne bei den Antwortbuttons markiert
ihm welche der LEDs gerade an der Reihe mit Leuchten bzw Flimmern ist.

.. image:: screenshots/testrunner_running.*

Nach Ende des Testlaufs sind alle Glühbirnen der Antwortbuttons rot gefärbt und der Versuchsleiter kann die Antwort des Probanden eingeben. Außerdem kann jederzeit am Testprotokoll abgelesen werden welche LEDs in den vorherigen Testdurchläufen geflimmert haben, was die Antwort des Probanden war und ob diese richtig war.

.. image:: screenshots/testrunner_waiting.*

Falsche Eingabe des Probanten
^^^^^^^^^^^^^^^^^^^^^^^^^^^^^

Bei einer falschen Angabe des Probanden hat der Versuchsleiter mehrere Optionen wie er nun vorgehen möchte. Entweder die Buttons mit der Maus klicken oder die Taste mit dem in der eckigen Klammer stehenden Buchstaben drücken (Q für "Weiter", W für "Weiter ohne Fehler", E für "Neustart", R für "Neustart mit aktueller Frequenz").

.. image:: screenshots/testrunner_wronganswer.*

Test beenden
^^^^^^^^^^^^

Der Test ist beendet, wenn der Probant mehr falsche Antworten gegeben hat, als vereinbart sind (Abbruchbedingung).

.. image:: screenshots/testrunner_finished.*
