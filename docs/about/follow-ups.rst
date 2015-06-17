Follow-Up Projects
==================

Some ideas for follow-up projects.

Automated Builds
----------------

Currently deploying the software is a manual job. It would be more pleasant to have automated builds. This especially means two tasks:

1. Driver and FVF are two independent projects right now, which means deploying the driver first to use it from FVF, it would be way easier to just refer to the driver project instead.
2. Deploy the software (with all it's required libs). Either automatically, by committing, tagging a release or trigger the build manually.

For option #2 (continuous deployment) there are some online services available, which must be checked individually if they are eligible for the task on hand:

- `semaphore`_
- `codeship`_
- `dploy`_
- `drone.io`_
- `circleci`_

.. _semaphore: https://semaphoreci.com
.. _codeship: https://codeship.com
.. _dploy: http://dploy.io
.. _drone.io: https://drone.io
.. _circleci: https://circleci.com

Additionally, there must be found a good place to distribute binaries to.

Streamline Web Presence
-----------------------

The current web presence is cluttered among this `docs`_ and the `manual`_. A formal webpage introducing FVF, what it is, who is responsible for that, where to download, how to contribute and contains links to the docs and manual is missing. Probably `GitHub pages`_ are a solution to island this or possibly put this up on the `IFS website`_.

.. _docs: https://fvf.readthedocs.org
.. _manual: https://fvf-manual.readthedocs.org
.. _GitHub pages: https://pages.github.com
.. _IFS website: http://www.sport.tu-darmstadt.de

Internationalization (i18n)
---------------------------

Right now, the docs are in english, software and manual are in german. All tools within the toolchain support internationalization. This can be used to better translate all occurring strings. `Transifex`_ is an online service to keep track of all translations, which can be used as a managing instance. There is even an integration between Transifex and Sphinx, to `translate docs`_.

.. _Transifex: https://www.transifex.com
.. _translate docs: http://sphinx-doc.org/intl.html


Self-Validation
---------------

A self-validation routine built into the :doc:`../source/firmware` that averages the deviation for each frequency and applies it during the measurement routine.

Post-Processing of Results
--------------------------

The results can receive some post-processing by either showing statistics and displaying graphs or providing exports to various formats for further processing, e.g. exporting to SPS.

Instructions to setup your own FVF measurement system
-----------------------------------------------------

Instructions to setup one's own FVF measurement system. With technical specifications of the tube and the oculus adapter to connecting the software. Likewise a step-by-step manual for a self-construction-kit.

Port to eclipse e4
------------------

For historical reasons, the software is built on eclipse e3 API. At the time of writing, e4 is the current API and contains modern programming approaches to simply development. It can be worth to port the codebase to the new e4 API.
