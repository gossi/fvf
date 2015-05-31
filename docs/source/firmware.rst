Firmware
========

The firmware runs on the Arduino Uno board. The board is connected via an Universal Serial Port (USB) to the host computer which runs the measurement software. It's main job is to handle incoming commands (via serial port) and send back feedback notifications.

Available Commands
------------------

- :ref:`protocol-input-on`
- :ref:`protocol-input-flicker`
- :ref:`protocol-input-off`
- :ref:`protocol-input-measurement`
- :ref:`protocol-input-ping`

Detailed information about the input and output about the firmware is described in the :doc:`../appendix/led-protocol`.

References
----------

- `Arduino Website`_
- `Arudino IDE`_
- `Arduino API`_

.. _`Arduino Website`: http://www.arduino.cc/
.. _`Arudino IDE`: http://www.arduino.cc/en/Main/Software
.. _`Arduino API`: http://www.arduino.cc/en/Reference/HomePage
