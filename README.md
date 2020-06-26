# ERI Maven Base #

This repository contains several core/foundational artifacts for developing software with Maven at ERI.

| ℹ Compatibility information |
| --- |
| Projects descending from `eri-maven-base` **5.x.x** must be built with [**JDK 11+**](https://docs.aws.amazon.com/corretto/latest/corretto-11-ug/downloads-list.html) (even if they are targeting Java 8) and Maven **3.5.x+**.

## ERI parent POM ##

The parent POM provides a top-level POM from which all of our ERI artifacts can inherit and centralizes plugins, repositories, and other configuration. This includes:

- Our organization name and inception year (which you should always override so that your project doesn't have an inception in the '90s 😊)
- Our artifact repositories, both for dependencies and plugins (Artifactory and JitPack.io)
- Our CI information (GitLab)
- Our issue management information (Jira)
- Common dependencies across all projects
	- JUnit 4.x
	- [Lombok](http://www.projectlombok.org)
	- Log4j2
	- [AutoService](https://github.com/google/auto/tree/master/service)
- Common plugin configuration
	- Javadoc plugin (ignores Javadoc errors for JDK 1.8+)
	- Compiler plugin (source code at **release 11**)
	- Source plugin
	- Release plugin
	- Build helper plugin (parses build version and adds common code generated folders like `target/generated-sources/java` and `target/generated-sources/jooq` as source)
	- [Enunciate](https://github.com/stoicflame/enunciate/wiki) which is disabled by default (set `enunciate.disabled` to `false` to enable)
	- Site plugin, which configures a Markdown renderer and a responsive, modern skin/theme
	- The current system in a property `system.platform` (either `win32`, `win64`, `mac32`, `mac64`, or `linux`), as well as the corresponding SWT artifact name

### Java 8 backwards compatibility

Since our parent POM is now on Java 11, you may need to cross-compile your code to target both Java 8 and Java 11+. This POM is configured to make that easier than methods like controversial [mult-release .jars](https://www.baeldung.com/java-multi-release-jar).

Set up two child modules like so:
```
/pom.xml             Aggregator POM that descends from eri-maven-base 5.x.x
  /core              "Main" or core child module with Java 11+ source code
    /src/main/java  
    pom.xml
  /java8             Sibling module that is initially empty
    pom.xml          
```

In `java8`'s `pom.xml`, define the following properties:
```
<properties>
	<source.copy.phase>generate-sources</source.copy.phase>
	<relative.source.path>../core/src/main/java</relative.source.path>
	<maven.compiler.release>8</maven.compiler.release>
</properties>
```

This will bind the `maven-resource-plugin` to the `generate-sources` phase so it will copy the main sources from `core`'s source folder into `java8`'s `target/generated-sources` folder before compiling. This allows the `maven-compiler-plugin`, now overridden to target release **8**, to recompile the sources for the sibling module.

If there are any APIs that are Java 11+ only, you will get a compile error in the `java8` module. You can then [exclude them from the copy](https://maven.apache.org/plugins/maven-resources-plugin/examples/include-exclude.html) using the `<excludes>` configuration of the `maven-resource-plugin`. Then, reimplement the excluded classes in `java8`'s `src/main/java` so that it will be compiled along with the copied Java-8-compatible sources from `core`.

Dependencies should be declared in the parent POM so they are shared across both modules.


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
		<artifactId>maven-shade-plugin</artifactId>
	</plugin>

- **Windows**: The above runnable .jar is wrapped in an .exe via launch4j. You must specify the following properties in addition to the ones common to all platforms above:
	- `application.headerType`: either `gui` or `console` to determine if the app has a console (Command Prompt) window
	- `application.iconWin`: path to the icon file (relative to your POM, not this POM) in **.ico** format
	- You must fully specify project descriptors, like `<name>`, `<description>`, etc.

You also must instantiate the following plugins:

	<plugin>
		<groupId>com.akathist.maven.plugins.launch4j</groupId>
		<artifactId>launch4j-maven-plugin</artifactId>
	</plugin>
    
- **MacOS**: The application is packaged into an Application Bundle and then zipped. It can be unzipped and run directly. You must specify the following properties in addition to the ones common to all platforms above:
	- `application.iconMac`: path to the icon file (relative to your POM, not this POM) in **.icns** format

You also must instantiate the following plugin:

	<plugin>
		<groupId>io.github.fvarrui</groupId>
		<artifactId>javapackager</artifactId>
	</plugin>
           
- **Linux**: adds a basic **.sh** script to the runnable jar file that makes it self-executable. You must specify properties common to all platforms above.


You also must instantiate the following plugins:

	<plugin>
		<groupId>org.skife.maven</groupId>
		<artifactId>really-executable-jar-maven-plugin</artifactId>
	</plugin>
	
## ERI Scala Base ##

This POM inherits from `eri-application-base` and sets up Maven for Scala or Java/Scala codebases by configuring the `scala-maven-plugin` to compile Scala code in `src/main/scala` and `src/test/scala` with version **2.11.12**. You can change the compiler's version and compile order with the following 3 properties:
* `scala.major.version`
* `scala.version` (usually a patch version appended to `${scala.major.version}`)
* `scala.compile.order` (defaults to `JavaThenScala`)

