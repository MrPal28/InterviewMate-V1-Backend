#!/bin/bash
#!/bin/bash
set -e

# ----------------------------
# SERVICES TO RELEASE
# ----------------------------
SERVICES=(
  "eureka-server"
  "api-gateway"
  "user-service"
  "resume-service"
  "coding-service"
  "notification-service"
  "judge-worker"
)

echo "🚀 Starting batch release..."

for SERVICE in "${SERVICES[@]}"; do
  echo ""
  echo "=============================="
  echo "📦 Releasing: $SERVICE"
  echo "=============================="

  ./release.sh "$SERVICE"

done

echo ""
echo "✅ All services released successfully!"
