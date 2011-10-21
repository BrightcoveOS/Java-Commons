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

Downloads
=========

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

**Version 4.0.2**:

This release updates the FTP libraries - there are now threaded utility
classes for FTP upload, download and list generation.

Full Download:

- [BC Commons v4.0.2 with dependencies](https://github.com/downloads/BrightcoveOS/Java-Commons/bc-commons-with-dep-4.0.2.zip)

Individual Packages (3rd Party):

- [3rd Party - Apache Commons for BC Commons v4.0.2](https://github.com/downloads/BrightcoveOS/Java-Commons/apache-commons-bcver-4.0.2.zip)
- [3rd Party - JSON.org for BC Commons v4.0.2](https://github.com/downloads/BrightcoveOS/Java-Commons/json-org-bcver-4.0.2.zip)
- [3rd Party - Xalan for BC Commons v4.0.2](https://github.com/downloads/BrightcoveOS/Java-Commons/xalan-bcver-4.0.2.zip)

Brightcove Libraries Only:

- [BC Commons v4.0.2](https://github.com/downloads/BrightcoveOS/Java-Commons/bc-commons-4.0.2.zip)
- [BC Commons Examples v4.0.2](https://github.com/downloads/BrightcoveOS/Java-Commons/bc-commons-examples-4.0.2.zip)

**Version 4.0.1**:

This release brings ad keys into the Video object in BC Commons Catalog
Objects, and adds classes to help working with Apache HttpParams in
the BC Commons Misc Utils package.

Full Download:

- [BC Commons v4.0.1 with dependencies](https://github.com/downloads/BrightcoveOS/Java-Commons/bc-commons-with-dep-4.0.1.zip)

Individual Packages (3rd Party):

- [3rd Party - Apache Commons for BC Commons v4.0.1](https://github.com/downloads/BrightcoveOS/Java-Commons/apache-commons-bcver-4.0.1.zip)
- [3rd Party - JSON.org for BC Commons v4.0.1](https://github.com/downloads/BrightcoveOS/Java-Commons/json-org-bcver-4.0.1.zip)
- [3rd Party - Xalan for BC Commons v4.0.1](https://github.com/downloads/BrightcoveOS/Java-Commons/xalan-bcver-4.0.1.zip)

Brightcove Libraries Only:

- [BC Commons v4.0.1](https://github.com/downloads/BrightcoveOS/Java-Commons/bc-commons-4.0.1.zip)
- [BC Commons Examples v4.0.1](https://github.com/downloads/BrightcoveOS/Java-Commons/bc-commons-examples-4.0.1.zip)

**Version 4.0.0**:

New major release.

This release introduces a number of new packages, including:

- BC Examples (examples for using other libraries)
- BC FTP Utils (libraries for working with FTP servers)
- BC Misc Utils (libraries that do not easily fit within another package)
- BC XML Utils (for working with XML)
- Xalan for Java (XML / XSLT libraries)
- New Apache Commons libraries (Codec, Lang, Logging)

Full Download:

- [BC Commons v4.0.0 with dependencies](https://github.com/downloads/BrightcoveOS/Java-Commons/bc-commons-with-dep-4.0.0.zip)

Individual Packages (3rd Party):

- [3rd Party - Apache Commons for BC Commons v4.0.0](https://github.com/downloads/BrightcoveOS/Java-Commons/apache-commons-bcver-4.0.0.zip)
- [3rd Party - JSON.org for BC Commons v4.0.0](https://github.com/downloads/BrightcoveOS/Java-Commons/json-org-bcver-4.0.0.zip)
- [3rd Party - Xalan for BC Commons v4.0.0](https://github.com/downloads/BrightcoveOS/Java-Commons/xalan-bcver-4.0.0.zip)

Brightcove Libraries Only:

- [BC Commons v4.0.0](https://github.com/downloads/BrightcoveOS/Java-Commons/bc-commons-4.0.0.zip)
- [BC Commons Examples v4.0.0](https://github.com/downloads/BrightcoveOS/Java-Commons/bc-commons-examples-4.0.0.zip)

**Version 3.0.5**:

This version fixes a bug in CuePoint in the BC Commons - Catalog Objects package.

Full Download:

- [BC Commons v3.0.5](https://github.com/downloads/BrightcoveOS/Java-Commons/bc-commons-3.0.5.zip)

Individual Packages:

- [3rd Party - Apache Commons for BC Commons v3.0.5](https://github.com/downloads/BrightcoveOS/Java-Commons/apache-commons-bcver-3.0.5.zip)
- [3rd Party - JSON.org for BC Commons v3.0.5](https://github.com/downloads/BrightcoveOS/Java-Commons/json-org-bcver-3.0.5.zip)
- [BC Commons - Catalog Objects v3.0.5](https://github.com/downloads/BrightcoveOS/Java-Commons/bc-commons-catalog-objects-3.0.5.jar)
- [BC Commons - Collection Utils v3.0.5](https://github.com/downloads/BrightcoveOS/Java-Commons/bc-commons-collection-utils-3.0.5.jar)
- [BC Commons - HTTP Utils v3.0.5](https://github.com/downloads/BrightcoveOS/Java-Commons/bc-commons-http-utils-3.0.5.jar)
- [BC Commons - System Utils v3.0.5](https://github.com/downloads/BrightcoveOS/Java-Commons/bc-commons-system-utils-3.0.5.jar)

**Version 3.0.4**:

This version fixes a few bugs in the BC Commons - Catalog Objects package.

1 - Requesting toJson() on a Video object could result in calling methods on null
Date objects.  This has been fixed so that an empty string will be used
instead.

2 - Creating a new Playlist object failed trying to cast the playlist id to a
Long object.  A new Long object is now created from the string representation
of the integer handed back to the wrapper.

Full Download:

- [BC Commons v3.0.4](https://github.com/downloads/BrightcoveOS/Java-Commons/bc-commons-3.0.4.zip)

Individual Packages:

- [3rd Party - Apache Commons for BC Commons v3.0.4](https://github.com/downloads/BrightcoveOS/Java-Commons/apache-commons-bcver-3.0.4.zip)
- [3rd Party - JSON.org for BC Commons v3.0.4](https://github.com/downloads/BrightcoveOS/Java-Commons/json-org-bcver-3.0.4.zip)
- [BC Commons - Catalog Objects v3.0.4](https://github.com/downloads/BrightcoveOS/Java-Commons/bc-commons-catalog-objects-3.0.4.jar)
- [BC Commons - Collection Utils v3.0.4](https://github.com/downloads/BrightcoveOS/Java-Commons/bc-commons-collection-utils-3.0.4.jar)
- [BC Commons - HTTP Utils v3.0.4](https://github.com/downloads/BrightcoveOS/Java-Commons/bc-commons-http-utils-3.0.4.jar)
- [BC Commons - System Utils v3.0.4](https://github.com/downloads/BrightcoveOS/Java-Commons/bc-commons-system-utils-3.0.4.jar)

**Version 3.0**:

Full Download:

- [BC Commons v3.0](https://github.com/downloads/BrightcoveOS/Java-Commons/bc-commons-3.0.zip)

Individual Packages:

- [3rd Party - Apache Commons for BC Commons v3.0](https://github.com/downloads/BrightcoveOS/Java-Commons/apache-commons-bcver-3.0.zip)
- [3rd Party - JSON.org for BC Commons v3.0](https://github.com/downloads/BrightcoveOS/Java-Commons/json-org-bcver-3.0.zip)
- [BC Commons - Catalog Objects v3.0](https://github.com/downloads/BrightcoveOS/Java-Commons/bc-commons-catalog-objects-3.0.jar)
- [BC Commons - Collection Utils v3.0](https://github.com/downloads/BrightcoveOS/Java-Commons/bc-commons-collection-utils-3.0.jar)
- [BC Commons - HTTP Utils v3.0](https://github.com/downloads/BrightcoveOS/Java-Commons/bc-commons-http-utils-3.0.jar)
- [BC Commons - System Utils v3.0](https://github.com/downloads/BrightcoveOS/Java-Commons/bc-commons-system-utils-3.0.jar)


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