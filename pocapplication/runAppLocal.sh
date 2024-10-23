#!/bin/bash

# Define a function to check the last command's result
check_status() {
  if [ $? -ne 0 ]; then
    echo "An error occurred. Stopping the script."
    exit 1
  fi
}

cd GRPC_POC\pocapplication

# Start the build process
echo "Starting the build process..."
./gradlew clean build
check_status

echo "Build successful. Starting Quarkus in development mode..."
./gradlew quarkusDev
check_status

echo "Quarkus is running in development mode. Exit by pressing Ctrl+C."
