Driver
======

The LED driver is a Java API to send commands to the :doc:`firmware` and getting notified about feedback from the firmware.

Communication
-------------

The client software talks to the Arduino board through a serial port connection. For this purpose the RXTX interface is used.

Protocol
--------

The implements the :doc:`../appendix/led-protocol`.

References
----------

- `RXTX`_
- `Arduino Java Interface`_

.. _RXTX: http://rxtx.qbang.org
.. _Arduino Java Interface: http://playground.arduino.cc/Interfacing/Java
