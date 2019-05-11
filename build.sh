cd MovieCruiserAuthenticationService
source ./env-variable.sh
mvn clean package
cd ..
cd moviecruiserService
source ./env-variable.sh
mvn clean package
cd ..