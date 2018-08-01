mkdir ctre
cd ctre

v="5.7.1.0"

curl "http://www.ctr-electronics.com//downloads/lib/CTRE_Phoenix_FRCLibs_NON-WINDOWS_v$v.zip" --output ctre-libs.zip
unzip ctre-libs.zip
cd java/lib
mv CTRE_Phoenix.jar CTRE-phoenix-java-$v.jar
mv CTRE_Phoenix-sources.jar CTRE-phoenix-java-$v-sources.jar

#Oh no zip isn't included in git bash
if command -v zip 2> /dev/null; then
    zip "CTRE-phoenix-java-$v-native.zip" "libCTRE_PhoenixCCI.so"
else
    clear
    echo "Hey, I've just noticed that you don't have the zip command"
    echo "Can you kindly put 'libCTRE_PhoenixCCI.so' in the folder 'ctre/java/lib' into a zip named 'CTRE-phoenix-java-$v-native.zip'?"
    echo "That would be very helpful."
    read -p "I'll just wait here, when you're done press enter."
    echo "Thank you."
fi

mvn install:install-file -Dfile="CTRE-phoenix-java-$v.jar" -DgroupId=davchoo.mirror.third.ctre -DartifactId=CTRE-phoenix-java -Dversion=$v -Dpackaging=jar

mvn install:install-file -Dfile="CTRE-phoenix-java-$v-sources.jar" -DgroupId=davchoo.mirror.third.ctre -DartifactId=CTRE-phoenix-java -Dversion=$v -Dpackaging=jar -Dclassifier=sources

mvn install:install-file -Dfile="CTRE-phoenix-java-$v-native.zip" -DgroupId=davchoo.mirror.third.ctre -DartifactId=CTRE-phoenix-java -Dversion=$v -Dpackaging=zip -Dclassifier=native