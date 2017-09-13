# ERI Maven Base #

This repository contains several core/foundational artifacts for developming software with Maven.

It includes:
## ERI parent POM ##

The parent POM provides a top-level POM from which all of our ERI artifacts can inherit and centralizes plugins, respositories, and other configuration.

Included:
- Our organization name
- Our Nexus repositories, both for dependencies and plugins
- Our CI information (currently Jenkins)
- Our issue management information (currently YouTrack)
- Common dependencies across all projects
	- JUnit
	- [Lombok](http://www.projectlombok.org)
	- Log4j2
- Common plugin configuration
	- Javadoc plugin (ignores Javadoc errors for JDK 1.8+)
	- Compiler plugin (source code at **1.8**)
	- Source plugin
	- Release plugin
	- Build helper plugin (parses build version and adds two common code generated folders `target/generated-sources/xjc` and `target/generated-sources/jooq` as source)
	- [Enunciate](https://github.com/stoicflame/enunciate/wiki) which is disabled by default (set `enunciate.disabled` to `false` to enable)
	- Site plugin, which configures a Markdown renderer and a responsive, modern skin/theme
	- The current system in a property `system.platform` (either `win32`, `win64`, `mac32`, `mac64`, or `linux`), as well as the corresponding SWT artifact name

## ERI application base ##

The application base POM descends from the parent POM and it itself functions as a parent POM. It configures several plugins used for wrapping up Java .jars as runnable executables.

The following platforms are supported:
- **All**: A runnable .jar via the `maven-shade-plugin`. To use, you must specify the following properties in your POM:
	- `application.mainClass`: tells the plugin which class to use to launch the application
	- `application.maxHeapMB`: the maximum heap size in MB (defaults to 1024)
	- `application.vmArgs`: additional arguments to pass to the JVM
	- `application.minJavaVersion`: the minimum JVM version (defaults to 1.8)
	- `application.file`: the name of the resulting executable file (defaults to `${project.name}`)

You also must instantiate the following plugin:

	<plugin>
    	<groupId>org.apache.maven.plugins</groupId>
		<artifactId>maven-shade-plugin</artifactId>
	</plugin>

- **Windows**: The above runnable .jar is wrapped in an .exe via launch4j. You must specify the following properties in addition to the ones common to all platforms above:
	- `application.headerType`: either `gui` or `console` to determine if the app has a console (Command Prompt) window
	- `application.iconWin`: path to the icon file (relative to your POM, not this POM) in **.ico** format
	- You must fully specify project descriptors, like `<name>`, `<description>`, etc.

You also must instantiate the following plugins:

	<plugin>
		<groupId>org.apache.maven.plugins</groupId>
		<artifactId>maven-shade-plugin</artifactId>
	</plugin>
	<plugin>
		<groupId>com.akathist.maven.plugins.launch4j</groupId>
		<artifactId>launch4j-maven-plugin</artifactId>
	</plugin>
    
- **MacOS**: The application is packaged into an Application Bundle and then zipped. It can be unzipped and run directly. You must specify the following properties in addition to the ones common to all platforms above:
	- `application.iconMac`: path to the icon file (relative to your POM, not this POM) in **.icns** format

You also must instantiate the following plugin:

	<plugin>
		<groupId>sh.tak.appbundler</groupId>
		<artifactId>appbundle-maven-plugin</artifactId>
	</plugin>
           
- **Linux**: adds a basic **.sh** script to the runnable jar file that makes it self-executable. You must specify properties common to all platforms above.


You also must instantiate the following plugins:

	<plugin>
    	<groupId>org.apache.maven.plugins</groupId>
		<artifactId>maven-shade-plugin</artifactId>
	</plugin>
	<plugin>
		<groupId>org.skife.maven</groupId>
		<artifactId>really-executable-jar-maven-plugin</artifactId>
	</plugin>
	
## ERI Code Metrics ##

Simple code metrics that check Java and Scala files against some of our in-house coding standards. Pre-built Linux/Mac and Windows binaries can be found in Box under **Projects Software**. An example of the output of the tool on the project itself:

	File:                                                          |(c)|/**| @ | # | U
	src\main\java\com\datamininglab\code\CodeCount                 | Y | Y | ! | ! |  0
	src\main\java\com\datamininglab\code\CodeFlag                  | Y | Y | ! | ! |  0
	src\main\java\com\datamininglab\code\CodeMetric                | Y | Y | ! | ! |  0
	src\main\java\com\datamininglab\code\CodeMetrics               | Y | Y | Y | Y |  0
	src\main\java\com\datamininglab\code\metrics\Authorship        | Y | Y | Y | ! |  0
	src\main\java\com\datamininglab\code\metrics\ClassDocumentation| Y | Y | ! | ! |  0
	src\main\java\com\datamininglab\code\metrics\CopyrightHeader   | Y | Y | ! | ! |  0
	src\main\java\com\datamininglab\code\metrics\Creation          | Y | Y | ! | Y |  0
	src\main\java\com\datamininglab\code\metrics\UnitTests         | Y | Y | ! | ! |  0
	
	Summary:
	(c) Has copyright header at top of file.....  9 out of   9 (100.0%)
	/** Has top-level/class comments............  9 out of   9 (100.0%)
 	 @  Has authorship information..............  2 out of   9 (22.2%)
 	 #  Has date of creation information........  2 out of   9 (22.2%)
 	 U  Number of unit tests....................  0 total

## ERI Checkstyle ##

A shared configuration for the Checkstyle plugin which allows your IDE to check that you are meeting ERI's coding style.
    



