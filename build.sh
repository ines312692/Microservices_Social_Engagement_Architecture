#!/bin/bash

set -e

echo " Building Eureka Server (SOA)..."
docker build -t eureka-server ./SOA

echo "Building User Service (UserSOA)..."
docker build -t user-soa ./UserSOA/UserSOA

echo "Building Chat Service (ChatSOA)..."
docker build -t chat-soa ./ChatSOA/chatSOA

echo "Building Engagement Service (EngagementSOA)..."
docker build -t engagement-soa ./EngagementSOA

echo "All services built successfully!"
