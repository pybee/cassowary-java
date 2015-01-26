cassowary-java
==============

Cassowary-java is a pure Java implementation of the `Cassowary constraint-solving algorithm`_.

Cassowary is the algorithm that forms the core of the OS X and iOS visual
layout mechanism.

Quickstart
----------

The easiest way to use Cassowary in you Java project is using Gradle.
Just add the JCentre Maven repository url to your project's ``build.gradle``::

    repositories {
        jcenter()
    }

Then add the Cassowary dependency::

    dependencies {
        compile "org.pybee:cassowary:0.0.1"
    }


Building cassowary-java
-----------------------

Gradle can also be used to manage the build and release of Cassowary-java.
To build the Cassowary release jar file, run::

    ./gradlew build

This will also run the test suite. Assuming everything works, you can publish
to a Maven repository. Make sure you have a `gradle.properties` file in the
root directory of the project; it should contain the following::

    bintray_user=<your bintray username>
    bintray_api_key=<your bintray API key>

If this file is correct, you can publish using::

    ./gradlew bintrayUpload

Community
---------

Cassowary-java is part of the `BeeWare suite`_. You can talk to the community through:

* `@pybeeware on Twitter`_

* The `BeeWare Users Mailing list`_, for questions about how to use the BeeWare suite.

* The `BeeWare Developers Mailing list`_, for discussing the development of new features in the BeeWare suite, and ideas for new tools for the suite.

The Cassowary community also has `a mailing list`_; it provides more general
assistance regarding the Cassowary algorithm itself.

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
.. _a mailing list: http://groups.google.com/forum/#!forum/overconstrained

Acknowledgements
----------------

The Cassowary implementation is a fork of the original Java library published
by Greg J. Badros.

The Cassowary code has been updated to be compliant with Java 1.4+, removes
and simplifies certain features, and applies a consistent coding style guide.
