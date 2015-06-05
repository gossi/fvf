Setup
=====

Learn how to setup your development environment for FVF.

Installing Arduino
------------------

Installing the Arduino IDE is straight forward. From the `Arduino Website`_ download the Arduino IDE for your platform and open ``firmware/fvf/fvf.ino`` to start your firmware development.

.. _Arduino Website: http://www.arduino.cc/en/Main/Software

Arduino Drivers
^^^^^^^^^^^^^^^

Some systems require a manual driver installation for the Arduino board. Please refer to the `Getting Started Guide`_ from the Arduino website if this is required for you and how to get this done.

.. _Getting Started Guide: http://www.arduino.cc/en/Guide/HomePage

CoolTerm
^^^^^^^^

`CoolTerm`_ is a simple serial port terminal application. CoolTerm can be used to send commands to the Arduino Board and test your firmware.

.. _CoolTerm: http://freeware.the-meiers.org/

Install Git
-----------

Git is used as VCS and GitHub as repository master.

Windows
^^^^^^^

Luckily GitHub provides an application with GUI to access git repositories. `Download GitHub for Windows`_ and install it; Clone the repo from GitHub and you are ready to go.

.. _Download GitHub for Windows: https://windows.github.com/

Mac
^^^

Also Mac got a GitHub app with GUI to access git repositories. `Download GitHub for Mac`_ and install it; Clone the repo from GitHub and you are ready to go.

.. _Download GitHub for Mac: https://mac.github.com/

Linux
^^^^^

You are on Linux, you know how to use your personal package manager to install yourself a git package and of course you can handle it from your favorite shell.

Installing Eclipse
------------------

Eclipse is the main development environment. A good start is to download the `Eclipse for RCP and RAP Developers`_ package.

.. _Eclipse for RCP and RAP Developers: https://www.eclipse.org/downloads/

Install PDE Tools
^^^^^^^^^^^^^^^^^

To help and assist you with programming (Javadoc + proper code completion), install the following plugins from "The Eclipse Project and Updates" update site (Help > Install New Software ... Update Site: http://download.eclipse.org/eclipse/updates/4.4 - replace "4.4" with the current version number):

- Eclipse Plug-In Development Environment
- Eclipse Platform SDK
- Eclipse Java Development Tools

Remark: Some of them might already be installed.

Install Deployment Tools
^^^^^^^^^^^^^^^^^^^^^^^^

To deploy the FVF application bundle to multiple platforms the eclipse "DeltaPack" is required for this.
Read here for installation: https://stackoverflow.com/a/12737382/483492

Install Optional Tools
^^^^^^^^^^^^^^^^^^^^^^

There are more useful plug-ins to support your development. They are available via the current releases update site (Help > Install New Software ... Update Site: http://download.eclipse.org/releases/luna - replace "luna" with the current release):

- SWT Designer
- Eclipse GIT Team provider
