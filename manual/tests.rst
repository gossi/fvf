Test
====

@TODO Erstmal so einen Test erklären:

- Welche Parameter gibt es
- Wie läuft der Test ab
  - Was muss der Versuchsleiter davor wissen
  - Was muss er dem Probanden sagen
  - In welcher Reihenfolge passiert was.
  - Allgemeine Beschreibungen nach oben und nicht in den Text unten zwischendrin einfließen lassen (Abbrechen geht immer, VL sieht niemals die flimmernde LED)

Bevor der Test gestartet werden kann, müssen folgende Paramter festgelegt werden:
  - Anzahl der LEDs
  - Startfrequenz in Hertz
  - Frequenzsteigerung in Hertz
  - Messzyklen pro Frequenz in Sekunden
  - Pausendauer pro Messzyklus in Sekunden
  - Anzeigedauer pro LED in Sekunden
  - Pausendauer pro LED in Sekunden
  - Abbruchkriterium (Falschnennungen), dass heißt der Test wird bei einer bestimmten Anzahl an Fehlern abgebrochen
  - Hell/ Dunkel- Quotient
  - Bemerkungen

Mit Auswahl dieser Parameter und der Personendaten (s. Kapitel Testpersonen) hat der Versuchsleiter alle für den
Test relevanten Informationen von der Versuchsperson.
Der Versuchsleiter informiert den Probanden vor dem Start des Tests über den Ablauf. Die Aufgabe des Probanden ist es die flimmernde Lichtquelle zu erkennen und dem Versuchsleiter
zu nennen. Dazu bekommt er je nach Einstellung zwei bis vier LEDs gezeigt, von denen pro Messzyklus nur eine flimmert. Auch die Messzyklen pro Frequenz wurden zuvor bei den Parametern eingestellt. Die LEDs
sind folgendermaßen durchnummeriert:
  - 1 : Links oben
  - 2 : Rechts oben
  - 3 : Links unten
  - 4 : Rechts unten
Sobald der Proband die flimmernde LED für sich indentifiziert hat nennt er seine Antwort dem Versuchsleiter, der diese dann über die Zahl oder durch Auswahl des enstprechenden
Buttons angibt. Nach Eingabe der Antwort des Probanden beginnt der nächste Messzyklus. Der Test kann jederzeit durch den Versuchsleiter
durch die Auswahl des Buttons "Abbruch" beendet werden oder er wird durch das Erreichern der eingegeben Abbruchbedingungen (die Anzahl der Falschnennungen) beendet.
Während des Tests sind die zuvor eingegebenen Parameter für den Versuchsleiter im oberen Teil der Dialogbox zu sehen,
darunter befindet sich das aktuelle Testprotokoll. So kann jederzeit jederzeit am Testprotokoll abgelesen werden welche LEDs in den vorherigen Testdurchläufen geflimmert haben, was die Antwort des Probanden war und ob diese richtig war. Welche die im aktuellen Messzyklus flimmernde LED ist, wird dem Versuchsleiter
nicht angezeigt. Nach dem Beenden des Tests können die Ergebnisse durch das Anklicken des Buttons "Speichern" gespeichert werden.


Neuer Test
----------
Zum Starten eines neuen Tests wird "Neuer Test" ausgewählt und es erscheint ein Fenster, in dem die Parameter für den Test eingegeben und mit "Finish" bestätigt werden.

.. image:: screenshots/test_dialog.*

Test Durchführen
----------------
Nach Eingabe der Testparamter kann der Test gestartet werden. Dies geschieht durch den Button "Start" und abgebrochen werden
kann er über den Button "Abbruch". Die Antwortmöglichkeiten des Probanden können wie oben beschrieben eingegeben werden.

.. image:: screenshots/testrunner_dialog.*

Nach dem Starten des Tests erscheint ein Fragezeichen beim jeweiligen Testdurchlauf.

.. image:: screenshots/testrunner_running.*

Nach Ende des Messzykluses sind alle Glühbirnen der Antwortbuttons rot gefärbt und der Versuchsleiter kann die Antwort des Probanden eingeben.

.. image:: screenshots/testrunner_waiting.*

Falsche Eingabe des Probanden
^^^^^^^^^^^^^^^^^^^^^^^^^^^^^

Bei einer falschen Angabe des Probanden hat der Versuchsleiter mehrere Optionen wie er nun vorgehen möchte. Entweder die Buttons mit der Maus klicken oder die Taste mit dem in der eckigen Klammer stehenden Buchstaben drücken (Q für "Weiter", W für "Weiter ohne Fehler", E für "Neustart", R für "Neustart mit aktueller Frequenz").

.. image:: screenshots/testrunner_wronganswer.*

Test beenden
^^^^^^^^^^^^

Der Test ist beendet, wenn der Proband mehr falsche Antworten gegeben hat als vereinbart sind (Abbruchbedingung).

.. image:: screenshots/testrunner_finished.*
