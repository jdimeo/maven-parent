# Changelog
All notable changes to this project will be documented in this file.

The format is based on [Keep a Changelog](http://keepachangelog.com/en/1.0.0/)
and this project adheres to [Semantic Versioning](http://semver.org/spec/v2.0.0.html).

## [3.5.0] - 2019-04-08
### Added
- Improved Lombok/Javadoc interaction. Since Lombok will copy Javadocs to generated getters and setters, the Javadoc plugin should run against the "delomboked" code, not the original. Now, all code (whether it uses Lombok or not) will be delomboked to `target/delombok-for-javadoc`. Javadoc and Enunciate will run against that directory. If you have additional sources that you want to be delombked but _not_ added to the source path (primary use case: running Enunciate against an unpacked dependency), put them in `src/main/lombok`.

### Changed
- Changed Nexus URL to FQDN with `cho.elderresearch.com` for when on VPN
- Update to latest Jackson, Lombok, Log4j, and various plugin versions

### Removed
- Removed support for 32 bit OSes in profile activation and SWT artifacts. Now instead of `win32` and `win64` for inherited property `system.platform` it will just be `win`. Likewise for `mac`.

## [3.4.2] - 2017-09-15
### Added
- Added Jackson dependencies needed to parse YAML config files for Log4j2

### Changed
- Changed group ID from `com.datamininglab` to `com.elderresearch`

## [3.4.0] - 2017-09-13
### Added
- README.md
- This changelog
- [Log4j2](https://logging.apache.org/log4j/2.x/) as a common dependency

### Changed
- Changed "Elder Research, Inc." to "Elder Research"
- Improved Checkstyle suppression to allow suppression of all rules or specific named rules via on/off comments

[Unreleased]: http://code.elderresearch.com/compare/eri-commons%2Feri-maven-base.git
[3.4.0]: http://code.elderresearch.com/compare/eri-commons%2Feri-maven-base.git/refs%2Ftags%2Feri-maven-base-1.0..refs%2Ftags%2Feri-maven-base-3.4.0?w=1
[3.4.2]: http://code.elderresearch.com/compare/eri-commons%2Feri-maven-base.git/refs%2Ftags%2Feri-maven-base-3.4.0..refs%2Ftags%2Feri-maven-base-3.4.2?w=1
[3.5.0]: http://code.elderresearch.com/compare/eri-commons%2Feri-maven-base.git/refs%2Ftags%2Feri-maven-base-3.4.2..refs%2Ftags%2Feri-maven-base-3.5.0?w=1