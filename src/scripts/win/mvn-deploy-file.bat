REM Checking this in to easily edit and reuse in the future
mvn deploy:deploy-file^
  -Dfile="histogram-lite-4.0.1.jar"^
  -Dsources="histogram-lite-4.0.1-sources.jar"^
  -DpomFile="histogram-lite-4.0.1.pom"^
  -DgroupId="bigml"^
  -DartifactId="histogram-lite"^
  -Dversion="4.0.1"^
  -DrepositoryId="eri-releases"^
  -Durl="https://elderresearch.jfrog.io/elderresearch/libs-release"
  