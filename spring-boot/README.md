# Sample Spring Boot application used in a Docker container

## Local Docker Setup
Make sure you have Docker Desktop installed, up and running.

## Build Application
The application is using openJDK 21.x so you need to make sure you're using the correct version of the JDK when building and running the application.

* Go to the **spring-boot** directory
* Run `mvn clean install` to build the application
* Run "`docker build --file Dockerfile --tag com.jimmccarthy/rugby/spring-boot:latest .`" (including the period) to create the docker image

### Deploy using Docker Compose
* Go to the **ace-app-monitor/docker-compose/local**
* Update the **env_vars.txt** file to include your DB2 hashid and password
    * DB2_USERNAME=
    * DB2_PASSWORD=
* Run `docker-compose up --detach` to deploy the application to the container
* Run `docker-compose logs --follow` to display the log output
* The application will be available on port 30000: [http://localhost:30000/](http://localhost:30000/)
* Before redeploying the application, run these commands
    * Run `docker kill svc-app-monitor-webapp` to kill the container
    * Run `docker rm svc-app-monitor-webapp` to remove the container

### Deploy using Kubernetes
> NOTE: before you try and deploy the application, make sure Kubernetes is configured correctly. Run `kubectl api-versions`. If you get an error similar to following:

>> Get https://kubernetes.docker.internal:6443/api?timeout=32s: Service Unavailable

> Edit **C:\Users\<USER>\.kube\config** and replace **https://kubernetes.docker.internal:6443** with **https://localhost:6443**

* Open Git Bash and to to the **ace-app-monitor/kubectl/local**
* Update the **start.sh** file to include your DB2 hashid and password from [CyberArK](https://cyberark.cbp.dhs.gov/PasswordVault)
* Add a file named db2-username with your DB2 username
* Add a file named db2-password with your DB2 password from [CyberArK](https://cyberark.cbp.dhs.gov/PasswordVault)
* Run `start.sh` to create the secrets and deploy the application to Kubernetes
* The application will be available on port 30000: [http://localhost:30000/AppMon/](http://localhost:30000/AppMon/)
* Run `stop.sh` to delete the secrets and remove the application from Kubernetes

## Additional Docker Commands

### Create Image
Run `docker-build.sh` from root directory of the project to create the LocalDockerfile

### Run Image
Run `docker-compose up` from the directory of docker-compose.yml file

### Check Images
Run `docker images` to check the images

### Check Container
Run `docker ps --all` to check the status of the container

### Stop and Remove Container
Run `docker-compose down` from the directory of docker-compose.yml file to stop and remove the container

### Remove Image
Run `docker stop <container id>` to stop the container

Run `docker rm <conatiner id>` (or `docker-compose down` from the directory of docker-compose.yml file) to remove the container

Run `docker rmi cspd-dtr.cbp.dhs.gov/cspd/ace-app-monitor/:latest` to remove the image

## Additional Kubernets Commands

### Get Events
Run `kubectl get events --sort-by=.metadata.creationTimestamp` to get events

### Get Pods
Run `kubectl get pods` to get the status of the running pods

### Get Pod details
Run `kubectl describe pod` to get more details about the running pods

Run `kubectl describe pod <pod-id>` to get more details about a specific pod

## Kubernetes Dashboard
[Dashboard](https://kubernetes.io/docs/tasks/access-application-cluster/web-ui-dashboard/) is a web-based Kubernetes user interface. You can use Dashboard to deploy containerized applications to a Kubernetes cluster, troubleshoot your containerized application, and manage the cluster resources. You can use Dashboard to get an overview of applications running on your cluster, as well as for creating or modifying individual Kubernetes resources (such as Deployments, Jobs, DaemonSets, etc). For example, you can scale a Deployment, initiate a rolling update, restart a pod or deploy new applications using a deploy wizard.

The Dashboard UI is not deployed by default. To deploy it, run the following command:

> kubectl apply -f https://raw.githubusercontent.com/kubernetes/dashboard/v2.0.4/aio/deploy/recommended.yaml

You can access Dashboard using the kubectl command-line tool by running the following command:

> kubectl proxy

Kubectl will make Dashboard available at:

> http://localhost:8001/api/v1/namespaces/kubernetes-dashboard/services/https:kubernetes-dashboard:/proxy/

To log in:
* Run `kubectl get secrets` to see the *kubernetes.io/service-account-token*
* Run `kubectl describe secret <token_name>` to access the token
* Copy the token string and paste it into the token field

To delete the Dashbard (and all associated resources), run this command:

> kubectl delete -f kubectl delete -f https://raw.githubusercontent.com/kubernetes/dashboard/master/aio/deploy/recommended.yaml

## Notes
### HTTPS using Self-Signed Certificate
https://www.baeldung.com/spring-boot-https-self-signed-certificate

