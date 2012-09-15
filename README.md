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
[JavaDocs](http://brightcoveos.github.com/Java-Commons/javadoc)

Downloads
=========

**Version 4.1.8**:

Adding iOS Rendition objects.

Full Download:

- [BC Commons v4.1.8 with dependencies](https://github.com/downloads/BrightcoveOS/Java-Commons/bc-commons-with-dep-4.1.8.zip)

Individual Packages (3rd Party):

- [3rd Party - Apache Commons for BC Commons v4.1.8](https://github.com/downloads/BrightcoveOS/Java-Commons/apache-commons-bcver-4.1.8.zip)
- [3rd Party - JSON.org for BC Commons v4.1.8](https://github.com/downloads/BrightcoveOS/Java-Commons/json-org-bcver-4.1.8.zip)
- [3rd Party - Xalan for BC Commons v4.1.8](https://github.com/downloads/BrightcoveOS/Java-Commons/xalan-bcver-4.1.8.zip)

Brightcove Libraries Only:

- [BC Commons v4.1.8](https://github.com/downloads/BrightcoveOS/Java-Commons/bc-commons-4.1.8.zip)
- [BC Commons Examples v4.1.8](https://github.com/downloads/BrightcoveOS/Java-Commons/bc-commons-examples-4.1.8.zip)


**Version 4.1.7**:

Adding a few (new-ish) sort by type enum values (namely DISPLAY_NAME and REFERENCE_ID)

Full Download:

- [BC Commons v4.1.7 with dependencies](https://github.com/downloads/BrightcoveOS/Java-Commons/bc-commons-with-dep-4.1.7.zip)

Individual Packages (3rd Party):

- [3rd Party - Apache Commons for BC Commons v4.1.7](https://github.com/downloads/BrightcoveOS/Java-Commons/apache-commons-bcver-4.1.7.zip)
- [3rd Party - JSON.org for BC Commons v4.1.7](https://github.com/downloads/BrightcoveOS/Java-Commons/json-org-bcver-4.1.7.zip)
- [3rd Party - Xalan for BC Commons v4.1.7](https://github.com/downloads/BrightcoveOS/Java-Commons/xalan-bcver-4.1.7.zip)

Brightcove Libraries Only:

- [BC Commons v4.1.7](https://github.com/downloads/BrightcoveOS/Java-Commons/bc-commons-4.1.7.zip)
- [BC Commons Examples v4.1.7](https://github.com/downloads/BrightcoveOS/Java-Commons/bc-commons-examples-4.1.7.zip)


**Version 4.1.6**:

Numerous small fixes:
- Adding name field to Brightcove Account object
- Adding Collection Util methods for static arrays
- Adding Log Utils - generate logging messages on one line only
- Updating Apache net libraries to v3.1
- Cleaning up build process

Full Download:

- [BC Commons v4.1.6 with dependencies](https://github.com/downloads/BrightcoveOS/Java-Commons/bc-commons-with-dep-4.1.6.zip)

Individual Packages (3rd Party):

- [3rd Party - Apache Commons for BC Commons v4.1.6](https://github.com/downloads/BrightcoveOS/Java-Commons/apache-commons-bcver-4.1.6.zip)
- [3rd Party - JSON.org for BC Commons v4.1.6](https://github.com/downloads/BrightcoveOS/Java-Commons/json-org-bcver-4.1.6.zip)
- [3rd Party - Xalan for BC Commons v4.1.6](https://github.com/downloads/BrightcoveOS/Java-Commons/xalan-bcver-4.1.6.zip)

Brightcove Libraries Only:

- [BC Commons v4.1.6](https://github.com/downloads/BrightcoveOS/Java-Commons/bc-commons-4.1.6.zip)
- [BC Commons Examples v4.1.6](https://github.com/downloads/BrightcoveOS/Java-Commons/bc-commons-examples-4.1.6.zip)


**Version 4.1.5**:

Numerous small fixes:
- Per [mikerswain](https://github.com/mikerswain) - Fixing bug in Rendition/CuePoint serialization/deserialization (confusing type with typeEnum)
- Per [mikerswain](https://github.com/mikerswain) - Fixing bug with adKeys - not properly returning with video or sending with update_video
- Fixing bug with Playlists (difference between "null" string, null object and JSONObject.NULL object)
- Quieting logging when serializing list of videos - was much too noisy
- Fixing bug with VideoCodecEnum - upper/lower case string values getting mixed up
- Greatly simplifying CollectionUtils JoinToString() logic
- Adding some utils to the XalanUtils to remove non-valid XML characters - there is some odd behavior where the serialization of an XML object allows saving of characters that will break the parser on re-read

Full Download:

- [BC Commons v4.1.5 with dependencies](https://github.com/downloads/BrightcoveOS/Java-Commons/bc-commons-with-dep-4.1.5.zip)

Individual Packages (3rd Party):

- [3rd Party - Apache Commons for BC Commons v4.1.5](https://github.com/downloads/BrightcoveOS/Java-Commons/apache-commons-bcver-4.1.5.zip)
- [3rd Party - JSON.org for BC Commons v4.1.5](https://github.com/downloads/BrightcoveOS/Java-Commons/json-org-bcver-4.1.5.zip)
- [3rd Party - Xalan for BC Commons v4.1.5](https://github.com/downloads/BrightcoveOS/Java-Commons/xalan-bcver-4.1.5.zip)

Brightcove Libraries Only:

- [BC Commons v4.1.5](https://github.com/downloads/BrightcoveOS/Java-Commons/bc-commons-4.1.5.zip)
- [BC Commons Examples v4.1.5](https://github.com/downloads/BrightcoveOS/Java-Commons/bc-commons-examples-4.1.5.zip)


**Version 4.1.3**:

This release fixes a minor bug with GeoCode lookups

Full Download:

- [BC Commons v4.1.3 with dependencies](https://github.com/downloads/BrightcoveOS/Java-Commons/bc-commons-with-dep-4.1.3.zip)

Individual Packages (3rd Party):

- [3rd Party - Apache Commons for BC Commons v4.1.3](https://github.com/downloads/BrightcoveOS/Java-Commons/apache-commons-bcver-4.1.3.zip)
- [3rd Party - JSON.org for BC Commons v4.1.3](https://github.com/downloads/BrightcoveOS/Java-Commons/json-org-bcver-4.1.3.zip)
- [3rd Party - Xalan for BC Commons v4.1.3](https://github.com/downloads/BrightcoveOS/Java-Commons/xalan-bcver-4.1.3.zip)

Brightcove Libraries Only:

- [BC Commons v4.1.3](https://github.com/downloads/BrightcoveOS/Java-Commons/bc-commons-4.1.3.zip)
- [BC Commons Examples v4.1.3](https://github.com/downloads/BrightcoveOS/Java-Commons/bc-commons-examples-4.1.3.zip)


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
