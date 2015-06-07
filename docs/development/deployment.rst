Deployment
==========

This page describes, how to deploy your own version of the measurement software.

Required Plugins
----------------

Since this is an e3 Plug-In deployed in an e4 environment all required `compatibility layer plugins`_ must be added.

.. _compatibility layer plugins: https://www.eclipse.org/community/eclipse_newsletter/2013/february/article3.php#compatibiliylayer_plugins

Export the RCP application
--------------------------

Open the ``fvf.product`` file in eclipse. On the `Overview` page there is the export section with a link to open the "Eclipse Product export wizard" (which is also available from the toolbar of this editor). Make sure to check "Export for multiple platforms" (which is only available if you followed the :ref:`setup-deltapack` instructions) and "Synchronize before exporting". Click "Next" which shows the available platforms to deploy to.

Bundle Arduino Drivers
----------------------

TODO: Some platforms should be bundled with the Arduino Drivers...

Automated Builds
----------------

TODO: Create a build script to let the builds run automatically.
