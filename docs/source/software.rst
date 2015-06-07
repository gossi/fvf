Software
========

The heart of the measurement system is the software, which is responsible for managing your probands, running tests and browsing the results. It is an `Eclipse RCP`_ application. It uses the eclipse e3 API. Latest API docs are available at `Eclipse Help`_.

.. _Eclipse RCP: http://eclipse.org/rcp
.. _Eclipse Help: http://help.eclipse.org

Database
--------

Database development is realized via `sormula`_ ORM. The models are placed in the ``de.tu_darmstadt.sport.fvf.model`` package. The respective :doc:`../appendix/erm` is available as appendix. It is a SQLite database which is realized with `SQLJet`_ and connected with `SQLite JDBC`_.

.. _sormula: http://sormula.org
.. _SQLJet: http://sqljet.com
.. _SQLite JDBC: http://www.xerial.org/trac/Xerial/wiki/SQLiteJDBC

Migrations
^^^^^^^^^^

Further version might require a migration of the underlying database. The mechanics for this are already implemented and ready to use. SQLJet allows to stores a user version number along with the database file. The purpose is to read this number at start and run a migration if necessary. The class ``de.tu_darmstadt.sport.fvf.database.DatabaseLoader`` handles this logic. The required code to read the version number is already available in the ``initialize()`` method, yet commented out but provides good start.

Icons
-----

Icons are `Silk`_ by famfamfam and `Fugue`_ by Yusuke Kamiyamane.

.. _Silk: http://www.famfamfam.com/lab/icons/silk/
.. _Fugue: http://p.yusukekamiyamane.com
