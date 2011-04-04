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

**BETA RELEASE - Version 3.0.5-beta**:

This release adds quite a few new libraries (and may actually be released as 3.1 rather than 3.0.5).

The primary additions are:

- FTP Utils - a set of utilities for uploading files to an FTP server
- Misc Utils - a set of utilities that could not be classified elsewhere
- XML Utils - a set of utilities for working with XML files
- Xalan - Repackaged Xalan-J libraries for working with XML files

Full Download:

- [BC Commons v3.0.5-beta](https://github.com/downloads/BrightcoveOS/Java-Commons/bc-commons-3.0.5-beta.zip)

Individual Packages (3rd Party):

- [3rd Party - Apache Commons for BC Commons v3.0.5](https://github.com/downloads/BrightcoveOS/Java-Commons/apache-commons-bcver-3.0.5-beta.zip)
- [3rd Party - JSON.org for BC Commons v3.0.5](https://github.com/downloads/BrightcoveOS/Java-Commons/json-org-bcver-3.0.5-beta.zip)
- [3rd Party - Xalan-J for BC Commons v3.0.5](https://github.com/downloads/BrightcoveOS/Java-Commons/xalan-bcver-3.0.5-beta.zip)

Individual Packages (BC Commons):

- [BC Commons - XML Utils v3.0.5](https://github.com/downloads/BrightcoveOS/Java-Commons/bc-commons-xml-utils-3.0.5-beta.jar)
- [BC Commons - System Utils v3.0.5](https://github.com/downloads/BrightcoveOS/Java-Commons/bc-commons-system-utils-3.0.5-beta.jar)
- [BC Commons - Misc Utils v3.0.5](https://github.com/downloads/BrightcoveOS/Java-Commons/bc-commons-misc-utils-3.0.5-beta.jar)
- [BC Commons - HTTP Utils v3.0.5](https://github.com/downloads/BrightcoveOS/Java-Commons/bc-commons-http-utils-3.0.5-beta.jar)
- [BC Commons - FTP Utils v3.0.5](https://github.com/downloads/BrightcoveOS/Java-Commons/bc-commons-ftp-utils-3.0.5-beta.jar)
- [BC Commons - Collection Utils v3.0.5](https://github.com/downloads/BrightcoveOS/Java-Commons/bc-commons-collection-utils-3.0.5-beta.jar)
- [BC Commons - Catalog Utils v3.0.5](https://github.com/downloads/BrightcoveOS/Java-Commons/bc-commons-catalog-objects-3.0.5-beta.jar)

Individual Packages (Examples):

- [BC Commons - Examples v3.0.5](https://github.com/downloads/BrightcoveOS/Java-Commons/bc-commons-examples-3.0.5-beta.zip)

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