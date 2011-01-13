About
=====

This project provides common classes and utilities for use in other Java
projects.  This may include 3rd party libraries as well as Brightcove Open
Source libraries.

Requirements
============

Whenever possible, the libraries will be compatible with Java 1.5, but in
some cases Java 1.6 may be required.  Each commons library should make note
of this.

Several 3rd party libraries (such as Apache Commons HTTP) will be included
with release packages.  A version of each repository without these 3rd party
libraries will also be provided, but the end user must provide them separately
before the library will function correctly.

Package - json-org
==================

This package is almost entirely just the source code downloaded from json.org
(http://www.json.org/java/index.html) downloaded on January 12th 2011.  The
only changes made are to resolve compiler and/or eclipse warnings.

This library will be included with some of the releases packages in this
project, but will not be included in any -no-dep release package.

Package - apache-commons
========================

This package includes all of the Apache Commons libraries
(http://commons.apache.org/) needed by any of the other libraries in this
project.

These libraries will be included with some of the releases packages in this
project, but will not be included in any -no-dep release package.

Package - xalan
===============

This package includes the Xalan libraries from release version 2.7.1.

These libraries will be included with some of the releases packages in this
project, but will not be included in any -no-dep release package.

Package - bc-java-commons-system
================================

This package includes classes to make working with the native system easier.
This includes methods to determinine the host operating system and methods to
execute system calls and capture the output from the command.

These calls tend to use not-perfectly-portable Java code (e.g. Java's
Runtime.exec() call).  All efforts are made to test this code, but these calls
are the most vulerable to problems when running in an enexpected host
environment.



