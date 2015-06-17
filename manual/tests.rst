Test
====

Zum Test wird hier zuerst der :ref:`test-process` erläutert und die dazugehörigen :ref:`test-parameter` erklärt. Die Abschnitte :ref:`test-new` und :ref:`test-run` beziehen sich auf die Eingaben für die Software.

.. _test-process:

Ablauf
------

Zu Beginn wird ein :ref:`test-new` gestartet und die :ref:`test-parameter` müssen eingegeben werden. Der Test selbst durchläuft mehrere Frequenz-Zyklen. Innerhalb dieses Frequenzzyklus gibt es mehrere Messzyklen. Bei jedem Messzyklus flimmert eine LED, mit einer Ausnahme: Pro Frequenz-Zyklus gibt es zusätzlich einen Messzyklus, bei dem keine LED flimmert. Der Proband gibt ja jedem Messzyklus an, welche LED geflimmert hat.

Messzyklus
^^^^^^^^^^

Der Messzyklus folgt immer dem gleichen Ablauf. Zuerst werden alle LEDs angeschaltet und leuchten. Nach kurzer Pause werden die LEDs einzeln angeschaltet (wovon eine flimmert). Danach werden wieder alle angeschaltet. Das soll dem Probanden signalisieren, er soll seine Antwort dem Versuchsleiter mitteilen.

Ende
^^^^

Der Test ist beendet, wenn der Proband zu viele Falschnennungen angegeben hat und damit das vorher definierte Abbruchkriterium überschritten hat oder der Versuchsleiter den Test abbricht.

Durchführung
^^^^^^^^^^^^

Der Versuchsleiter informiert den Probanden vor dem Start des Tests über den Ablauf. Die Aufgabe des Probanden ist es die flimmernde Lichtquelle zu erkennen und dem Versuchsleiter zu nennen. Dazu bekommt er je nach Einstellung zwei bis vier LEDs gezeigt, von denen pro Messzyklus nur eine flimmert. Sobald der Proband die flimmernde LED für sich indentifiziert hat nennt er seine Antwort dem Versuchsleiter, der diese dann in die Messsoftware eingibt.

Während des Tests sind die zuvor eingegebenen Parameter für den Versuchsleiter im oberen Teil der Dialogbox zu sehen, darunter befindet sich das aktuelle Testprotokoll. So kann jederzeit jederzeit am Testprotokoll abgelesen werden welche LEDs in den vorherigen Testdurchläufen geflimmert haben, was die Antwort des Probanden war und ob diese richtig war. Welche die im aktuellen Messzyklus flimmernde LED ist, wird dem Versuchsleiter nicht angezeigt. Nach dem Beenden des Tests können die Ergebnisse durch das Anklicken des Buttons "Speichern" gespeichert werden.

.. _test-parameter:

Testparameter
-------------

Bevor der Test gestartet werden kann, müssen folgende Paramter festgelegt werden:

**Anzahl der LEDs**
  Der Test kann entweder mit 2 oder 4 LEDs durchgeführt werden.

**Startfrequenz [Hz]**
  Legt die Frequenz für den ersten Frequenzzyklus fest.

**Frequenzsteigerung [Hz]**
  Legt die Steigerung für jeden neuen Frequenzzyklus fest.

**Messzyklen pro Frequenz**
  Gibt an, wieviele Messzyklen innerhalb eines Frequenzzyklus stattfinden.

**Pausendauer pro Messzyklen [s]**
  Die Zeit zwischen zwei Messzyklen

**Anzeigedauer pro LED [s]**
  Die Leuchtdauer pro LED

**Pausendauer pro LED [s]**
  Die Dauer der Pausen zwischen den Leuchtphasen

**Abbruchkriterium (Falschnennungen)**
  Ab wieviel Falschnennungen (durch den Probanden) der Test beendet wird

**Hell/Dunkel-Quotient**
  Dieser Quotient bezieht sich auf die Flimmerphasen. Und zwar zu welchen Anteilen jeweils die Hell- und Dunkelphase aktiv sind.

**Bemerkungen**
  Platz für Test-spezifische Bemerkungen

.. _test-new:

Neuer Test
----------
Zum Starten eines neuen Tests wird "Neuer Test" ausgewählt und es erscheint ein Fenster, in dem die Parameter für den Test eingegeben und mit ``Finish`` bestätigt werden.

.. image:: screenshots/test_dialog.*
   :class: screen-400h
   :height: 400px

.. _test-run:

Test Durchführen
----------------
Nach Eingabe der Testparamter kann der Test gestartet werden. Dies geschieht durch den Button ``Start`` Die Antwortmöglichkeiten des Probanden können wie oben beschrieben eingegeben werden. Der Test kann jederzeit über den Button ``Abbruch`` abgebrochen werden, die Ergebnisse werden dann aber nicht gespeichert.

.. image:: screenshots/testrunner_dialog.*
   :class: screen-400h
   :height: 400px

Nach dem Starten des Tests erscheint ein Fragezeichen beim jeweiligen Testdurchlauf.

.. image:: screenshots/testrunner_running.*
   :class: screen-400h
   :height: 400px

Nach Ende des Messzykluses sind alle LEDs und der Antwortbuttons rot gefärbt und der Versuchsleiter kann die Antwort des Probanden eingeben.

.. image:: screenshots/testrunner_waiting.*
   :class: screen-400h
   :height: 400px

Falsche Eingabe des Probanden
^^^^^^^^^^^^^^^^^^^^^^^^^^^^^

Bei einer falschen Angabe des Probanden hat der Versuchsleiter mehrere Optionen wie er nun vorgehen möchte. Entweder die Buttons mit der Maus klicken oder die Taste mit dem in der eckigen Klammer stehenden Buchstaben drücken (``Q`` für "Weiter", ``W`` für "Weiter ohne Fehler", ``E`` für "Neustart", ``R`` für "Neustart mit aktueller Frequenz").

.. image:: screenshots/testrunner_wronganswer.*
   :class: screen-400h
   :height: 400px

Der Proband kann keine flimmernde LED benennen
^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^

Wenn der Proband keine flimmernde LED mehr wahrnehmen kann und dies auch entsprechend mitteilt, wählt der Versuchsleiter die Antwort, dass keine LED geflimmert hat (z.B. die Taste ``0``).

Test beenden
^^^^^^^^^^^^

Der Test ist beendet, wenn der Proband mehr falsche Antworten gegeben hat als vereinbart sind (Abbruchbedingung). Durch klicken des ``Speichern`` Buttons wird der Test gespeichert und beendet.

.. image:: screenshots/testrunner_finished.*
   :class: screen-400h
   :height: 400px
