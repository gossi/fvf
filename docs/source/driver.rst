Driver
======

The LED driver is a Java API to send commands to the :doc:`firmware` and getting notified about feedback from the firmware.

Communication
-------------

The client software talks to the Arduino board through a serial port connection. For this purpose the RXTX interface is used.

Protocol
--------

The driver implements the :doc:`../appendix/led-protocol`.

Deployment
----------

The driver is deployed as ``*.jar`` file into the softwares ``lib/`` folder.

.. image:: ../images/driver_export.*
   :class: screen-400h
   :height: 400px

.. image:: ../images/driver_export_dialog.*
   :align: right
   :class: screen-400h
   :height: 400px

Note: This is not ideal and must be triggered manually. Better solutions are welcome.

Remarks
-------

There is a `rxtx wiki entry`_ describing how to bundle the jni extension with an eclipse rcp application.

.. _rxtx wiki entry: http://rxtx.qbang.org/wiki/index.php/Wrapping_RXTX_in_an_Eclipse_Plugin

Mac OS X
^^^^^^^^

The normal distributed ``librxtxSerial.jnilib`` is only in 32-bit mode which doesn't match a 64-bit processor architecture and can thus not be autoloaded. See here::

  $ file librxtxSerial.jnilib
  librxtxSerial.jnilib: Mach-O universal binary with 2 architectures
  librxtxSerial.jnilib (for architecture ppc):	Mach-O dynamically linked shared library ppc
  librxtxSerial.jnilib (for architecture i386):	Mach-O dynamically linked shared library i386

Luckily there is a 64-bit version available, forged by `Robert Harder`_. The eclipse plugin distributes the 64-bit version mentioned here::

  $ file librxtxSerial.jnilib
  librxtxSerial.jnilib: Mach-O universal binary with 4 architectures
  librxtxSerial.jnilib (for architecture x86_64):	Mach-O 64-bit bundle x86_64
  librxtxSerial.jnilib (for architecture i386):	Mach-O bundle i386
  librxtxSerial.jnilib (for architecture ppc7400):	Mach-O bundle ppc
  librxtxSerial.jnilib (for architecture ppc64):	Mach-O 64-bit bundle ppc64

.. _Robert Harder: http://blog.iharder.net/2009/08/18/rxtx-java-6-and-librxtxserial-jnilib-on-intel-mac-os-x/


References
----------

- `RXTX`_
- `Arduino Java Interface`_

.. _RXTX: http://rxtx.qbang.org
.. _Arduino Java Interface: http://playground.arduino.cc/Interfacing/Java
