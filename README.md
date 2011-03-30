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

Package - (3rd party) xalan
===========================

This package will include the Xalan libraries from release version 2.7.1.

This package has not yet been released.

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

Package - http-utils
====================

This package includes classes to make working with HTTP objects (requests, responses, entities, etc) easier.

Dependencies: 
 
- Apache HTTP Components (core)
- Apache Commons IO

Package - system-utils
==========================

This package includes classes to make working with the underlying system (e.g. sys exec calls) easier.

Dependencies: 
 
- None

Package - release-build
==========================

This package doesn't contain any libraries itself.  It contains scripts and configuration to build and release the other commons packages.

Dependencies: 
 
- None