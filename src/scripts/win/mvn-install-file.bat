REM Install a file to a local folder in a repo that can be checked in  
mvn install:install-file^
  -Dfile="marvin-plugins-2.0.jar"^
  -Dsources="marvinproject-1.5.5.zip"^
  -DgroupId="org.marvinproject"^
  -DartifactId="marvin-plugins"^
  -Dversion="2.0.0"^
  -Dpackaging="jar"^
  -DlocalRepositoryPath="C:\\Users\\jdimeo\\Repositories\\glyphic\\GSI-Reader\\lib"