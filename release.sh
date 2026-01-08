#!/bin/bash
set -e

# ----------------------------
# CONFIG
# ----------------------------
DOCKER_USER="arindampal28"

SERVICE=$1

if [ -z "$SERVICE" ]; then
  echo "❌ Usage: ./release.sh <service-folder>"
  echo "Example: ./release.sh user-service"
  exit 1
fi

# ----------------------------
# RESOLVE REAL SERVICE PATH
# backend/user-service/user-service
# ----------------------------
SERVICE_PATH="./$SERVICE/$SERVICE"

if [ ! -d "$SERVICE_PATH" ]; then
  echo "❌ Service path not found: $SERVICE_PATH"
  exit 1
fi

cd "$SERVICE_PATH"

# ----------------------------
# VALIDATIONS
# ----------------------------
for file in pom.xml Dockerfile VERSION; do
  if [ ! -f "$file" ]; then
    echo "❌ Missing $file in $SERVICE_PATH"
    exit 1
  fi
done

# ----------------------------
# VERSIONING (PATCH bump)
# ----------------------------
CURRENT_VERSION=$(cat VERSION)
IFS='.' read -r MAJOR MINOR PATCH <<< "$CURRENT_VERSION"
NEW_VERSION="$MAJOR.$MINOR.$((PATCH+1))"

echo "🔁 $SERVICE: $CURRENT_VERSION → $NEW_VERSION"
echo "$NEW_VERSION" > VERSION

# ----------------------------
# BUILD JAR
# ----------------------------
echo "🔨 Building Maven artifact..."
mvn clean install -DskipTests

# ----------------------------
# DOCKER BUILD
# ----------------------------
IMAGE="$DOCKER_USER/interviewmate-$SERVICE:$NEW_VERSION"

echo "🐳 Building image $IMAGE"
docker build -t "$IMAGE" .

# ----------------------------
# PUSH IMAGE
# ----------------------------
echo "📤 Pushing image..."
docker push "$IMAGE"

# ----------------------------
# TAG latest
# ----------------------------
docker tag "$IMAGE" "$DOCKER_USER/interviewmate-$SERVICE:latest"
docker push "$DOCKER_USER/interviewmate-$SERVICE:latest"

echo "✅ Release successful"
echo "📦 Service: $SERVICE"
echo "🏷 Version: $NEW_VERSION"
