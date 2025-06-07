#!/usr/bin/bash

# delete the applications and deployments
kubectl delete svc/spring-boot    deploy/spring-boot     --namespace=$K8S_NAMESPACE
#kubectl delete svc/elasticsearch  deploy/elasticsearch  --namespace=$K8S_NAMESPACE
#kubectl delete svc/kibana         deploy/kibana         --namespace=$K8S_NAMESPACE
#kubectl delete svc/logstash       deploy/logstash       --namespace=$K8S_NAMESPACE

# delete the cronjob(s)
kubectl delete cronjob spring-boot-check-news            --namespace=$K8S_NAMESPACE
kubectl delete jobs --all

# delete the configmap
kubectl delete configmap spring-boot-env-vars

# delete the secrets
kubectl delete secret spring-boot-h2-password
kubectl delete secret spring-boot-owner-password
kubectl delete secret spring-boot-manager-password
#kubectl delete secret spring-boot-db2-password
