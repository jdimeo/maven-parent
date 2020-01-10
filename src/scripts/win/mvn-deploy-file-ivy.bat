REM Checking this in to easily edit and reuse in the future
mvn deploy:deploy-file^
  -Dfile="jars/eri-plotting-jfx_2.11-0.7.1.jar"^
  -Dsources="srcs/eri-plotting-jfx_2.11-0.7.1-sources.jar"^
  -DgroupId="com.elderresearch.viz"^
  -DartifactId="eri-plotting-jfx_2.11"^
  -Dversion="0.7.1"^
  -DrepositoryId="eri-releases"^
  -Durl="https://elderresearch.jfrog.io/elderresearch/maven-legacy"
  