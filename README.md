About
=====

This project provides common classes and utilities for use in other Java
projects. This may include 3rd party libraries as well as Brightcove Open
Source libraries.

Requirements
============

Whenever possible, the libraries will be compatible with Java 1.5, but in
some cases Java 1.6 may be required.  Each commons library should make note
of this.

Documentation
=============
New - [JavaDocs!](http://brightcoveos.github.com/Java-Commons/javadoc)

Downloads
=========

**Version 4.1.1**:

This release makes some changes to the common applications (FTPUploader and FTPDownloader) to make them more usable from the command line.

Full Download:

- [BC Commons v4.1.1 with dependencies](https://github.com/downloads/BrightcoveOS/Java-Commons/bc-commons-with-dep-4.1.1.zip)

Individual Packages (3rd Party):

- [3rd Party - Apache Commons for BC Commons v4.1.1](https://github.com/downloads/BrightcoveOS/Java-Commons/apache-commons-bcver-4.1.1.zip)
- [3rd Party - JSON.org for BC Commons v4.1.1](https://github.com/downloads/BrightcoveOS/Java-Commons/json-org-bcver-4.1.1.zip)
- [3rd Party - Xalan for BC Commons v4.1.1](https://github.com/downloads/BrightcoveOS/Java-Commons/xalan-bcver-4.1.1.zip)

Brightcove Libraries Only:

- [BC Commons v4.1.1](https://github.com/downloads/BrightcoveOS/Java-Commons/bc-commons-4.1.1.zip)
- [BC Commons Examples v4.1.1](https://github.com/downloads/BrightcoveOS/Java-Commons/bc-commons-examples-4.1.1.zip)


**Version 4.1.0**:

This release adds quite a few features:

- (new) bc-account-objects: Library to keep track of account details (e.g. read/write tokens, FTP credentials, etc)
- (new) bc-commons-applications: Utility applications that take advantage of the commons libraries (e.g. FTPUploader)
- (new) xml-utils: Added a pure W3C library (no Xalan dependecy) for simple operations
- (changed) bc-catalog-objects: Added ability to serialize/deserialize objects to/from XML
- (removed) ftp-utils: Moved command line applications to new bc-commons-applications library

Full Download:

- [BC Commons v4.1.0 with dependencies](https://github.com/downloads/BrightcoveOS/Java-Commons/bc-commons-with-dep-4.1.0.zip)

Individual Packages (3rd Party):

- [3rd Party - Apache Commons for BC Commons v4.1.0](https://github.com/downloads/BrightcoveOS/Java-Commons/apache-commons-bcver-4.1.0.zip)
- [3rd Party - JSON.org for BC Commons v4.1.0](https://github.com/downloads/BrightcoveOS/Java-Commons/json-org-bcver-4.1.0.zip)
- [3rd Party - Xalan for BC Commons v4.1.0](https://github.com/downloads/BrightcoveOS/Java-Commons/xalan-bcver-4.1.0.zip)

Brightcove Libraries Only:

- [BC Commons v4.1.0](https://github.com/downloads/BrightcoveOS/Java-Commons/bc-commons-4.1.0.zip)
- [BC Commons Examples v4.1.0](https://github.com/downloads/BrightcoveOS/Java-Commons/bc-commons-examples-4.1.0.zip)


**Version 4.0.3**:

This release adds the HttpClientFactory - so an external program can override
how an HttpClient is created for use by other methods.

Full Download:

- [BC Commons v4.0.3 with dependencies](https://github.com/downloads/BrightcoveOS/Java-Commons/bc-commons-with-dep-4.0.3.zip)

Individual Packages (3rd Party):

- [3rd Party - Apache Commons for BC Commons v4.0.3](https://github.com/downloads/BrightcoveOS/Java-Commons/apache-commons-bcver-4.0.3.zip)
- [3rd Party - JSON.org for BC Commons v4.0.3](https://github.com/downloads/BrightcoveOS/Java-Commons/json-org-bcver-4.0.3.zip)
- [3rd Party - Xalan for BC Commons v4.0.3](https://github.com/downloads/BrightcoveOS/Java-Commons/xalan-bcver-4.0.3.zip)

Brightcove Libraries Only:

- [BC Commons v4.0.3](https://github.com/downloads/BrightcoveOS/Java-Commons/bc-commons-4.0.3.zip)
- [BC Commons Examples v4.0.3](https://github.com/downloads/BrightcoveOS/Java-Commons/bc-commons-examples-4.0.3.zip)

Package - (3rd party) apache-commons
====================================

This package includes several Apache Commons libraries, including: 
 
- Mime4j (from Apache James project)
- Commons IO
- HTTP Components (core and client)

These can all be downloaded from Apache directly if preferred: 
 
- [Apache Commons](http://commons.apache.org/)
- [Apache James](http://james.apache.org/download.cgi)
- [Apache HTTP Components](http://hc.apache.org/)

The versions stored here will be stripped down to just what is needed (plus any legal / license notifications)

Dependencies: 
 
- None

Package - (3rd party) json-org
==============================

This package is almost entirely just the source code downloaded from json.org
(http://www.json.org/java/index.html) downloaded on January 12th 2011.  The
only changes made are to resolve compiler and/or eclipse warnings.

Dependencies: 
 
- None

Package - (3rd party) Xalan-J
====================================

This package includes repackaged Xalan-J libraries for working with XML

These can all be downloaded from directly from the source if preferred: 
 
- [Xalan-J](http://xml.apache.org/xalan-j/downloads.html)

The versions stored here will be stripped down to just what is needed (plus any legal / license notifications)

Dependencies: 
 
- None

Package - bc-catalog-objects
============================

This package includes classes to represent all of the fundamental data objects produced by or needed to interface with the Brightcove system.

Most of the objects are created following the Brightcove Media API object reference, but some modifications are needed to support other projects.

Dependencies: 
 
- json.org

Package - collection-utils
==========================

This package includes classes to make working with Collections (Sets, Lists, etc) easier.

Dependencies: 
 
- None

Package - ftp-utils
====================

This package includes classes to make working with FTP servers easier.

Dependencies: 
 
- Xalan-J
- BC Commons - XML Utils (used for config files)

Package - http-utils
====================

This package includes classes to make working with HTTP objects (requests, responses, entities, etc) easier.

Dependencies: 
 
- Apache HTTP Components (core)
- Apache Commons IO

Package - misc-utils
====================

This package includes miscellaneous classes that could not easily be fit into another package.

E.g. Date/Time utils, String utils, Object comparison utils

Dependencies: 
 
- None

Package - system-utils
==========================

This package includes classes to make working with the underlying system (e.g. sys exec calls) easier.

Dependencies: 
 
- None

Package - xml-utils
====================

This package includes classes to make working with XML objects (especially XML files) easier.

Dependencies: 
 
- Xalan-J

Package - examples
====================

This package includes examples for working with the other BC Commons packages.

Dependencies: 
 
- *.*

Package - release-build
==========================

This package doesn't contain any libraries itself.  It contains scripts and configuration to build and release the other commons packages.

Dependencies: 
 
- None
