#!/usr/bin/bash

# If errors are thrown on this script, make sure that your local Kubernetes environment is running nicely, and
# make sure that you have followed the setup instructions in the readme file in this local folder.

# load the client bundle
#export KUBECONFIG={{ KUBECONFIG_FILE }}
export DOCKER_TAG=${DOCKER_TAG:-latest}
export K8S_NAMESPACE=${K8S_NAMESPACE:-default}

envsubst < spring-boot-deployment.yaml > deploy.yaml
envsubst < cron-deployment.yaml > deploy-cronjobs.yaml
#envsubst < elasticsearch-deployment.yaml > deploy-elasticsearch.yaml
#envsubst < kibana-deployment.yaml > deploy-kibana.yaml
#envsubst < logstash-deployment.yaml > deploy-logstash.yaml

# create required secrets
kubectl create secret generic spring-boot-h2-password       --from-literal=h2-password='password'
kubectl create secret generic spring-boot-owner-password    --from-literal=owner-password='$2a$10$UZBULM3B5qA2bIljHJULoOUMooxtmzwvbF4nraPURkA/DTtnTEKe2'
kubectl create secret generic spring-boot-manager-password  --from-literal=manager-password='$2a$10$Cs7ndaORjL.WIAWa/NmZ8.SSk5OFYKrZNAf8Yla/uZHqceQJYT7MO'
#kubectl create secret generic spring-boot-db2-password         --from-file=./db2-password

kubectl create configmap spring-boot-env-vars --from-env-file=./env_vars.txt

kubectl apply -f deploy.yaml
kubectl apply -f deploy-cronjobs.yaml
#kubectl apply -f deploy-elasticsearch.yaml
#kubectl apply -f deploy-kibana.yaml
#kubectl apply -f deploy-logstash.yaml
