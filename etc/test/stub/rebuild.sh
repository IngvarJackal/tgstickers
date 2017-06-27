cd etc/test/stub &&
mvn clean package &&
rm -r src/main/docker/target/
cp -r target/ src/main/docker/