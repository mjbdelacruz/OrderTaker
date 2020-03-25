echo "Building the Project....\n\v"
mvn clean package

echo "\nBuilding and Starting Docker container....\n\v"
docker-compose up --build