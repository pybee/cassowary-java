cassowary-java
==============

Cassowary-java is comprised of two parts:

* A pure Java implementation of the `Cassowary constraint-solving algorithm`_.

* An Android ``ViewGroup`` that uses Cassowary to perform the layout of widgets.

Cassowary is the algorithm that forms the core of the OS X and iOS visual
layout mechanism.

Quickstart
----------

To build ``cassowary-java``::

    $ export ANDROIDSDK=<path to Android SDK directory>
    $ make

This will produce 2 JAR files:

* ``Cassowary.jar``, containing just the Cassowary implementation
* ``AndroidCassowary.jar``, containig all the contents of ``Cassowary.jar``,
  plus the Android layout parts.

Community
---------

Cassowary-java is part of the `BeeWare suite`_. You can talk to the community through:

* `@pybeeware on Twitter`_

* The `BeeWare Users Mailing list`_, for questions about how to use the BeeWare suite.

* The `BeeWare Developers Mailing list`_, for discussing the development of new features in the BeeWare suite, and ideas for new tools for the suite.

Contributing
------------

If you experience problems with this backend, `log them on GitHub`_. If you
want to contribute code, please `fork the code`_ and `submit a pull request`_.

.. _Cassowary constraint-solving algorithm: http://www.cs.washington.edu/research/constraints/cassowary/
.. _BeeWare suite: http://pybee.org
.. _@pybeeware on Twitter: https://twitter.com/pybeeware
.. _BeeWare Users Mailing list: https://groups.google.com/forum/#!forum/beeware-users
.. _BeeWare Developers Mailing list: https://groups.google.com/forum/#!forum/beeware-developers
.. _log them on Github: https://github.com/pybee/cassowary-java/issues
.. _fork the code: https://github.com/pybee/cassowary-java
.. _submit a pull request: https://github.com/pybee/cassowary-java/pulls

Acknowledgements
----------------

The Cassowary implementation is a fork of the original Java library published
by Greg J. Badros.

The Cassowary code has been updated to be compliant with Java 1.4+, removes
and simplifies certain features, and applies a consistent coding style guide.
