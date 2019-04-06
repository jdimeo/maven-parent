REM Checking this in to easily edit and reuse in the future
mvn deploy:deploy-file^
  -Dfile="D:\\Glyphic\\FRE12R2_part_1342_11_build_12_2_27_0\\CADF\\Inc\\Java\\com.abbyy.FREngine.jar"^
  -DgroupId="com.abbyy"^
  -DartifactId="FREngine"^
  -Dversion="12.2.27.0"^
  -Dclassifier="win64"^
  -DrepositoryId="gsi-releases"^
  -Durl="packagecloud+https://packagecloud.io/glyphicsoftware/releases"
  