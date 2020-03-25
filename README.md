# K8S client example

## Description

A small Java service to show an example to connect to the k8s Api using the 
official [K8s Java client](https://github.com/kubernetes-client/java) in local
and from within the cluster.

## Running locally

Create a kubernetes proxy on port 3000 to your cluster using `kubectl` with below command.

```bash
kubectl proxy --port=3000
```

Then run the application using the `local` profile.

```bash
mvn spring-boot:run -Dspring-boot.run.profiles=local
```

Or in __IntellijIDEA__ specifying the profile `local` in the _run/debug configuration_ window.  

## K8s privileges

To run the application in a K8s cluster, the service account which will run the pods
must have enough privileges to perform the labor we want. The file 
[`service_account.yaml`](deploy/service_account.yaml) contains an example for development 
scenario assigning the rol __cluster-admin__ to the __service_account__ named default.

__NOTE 1__: Remember that this file is an example, explore the 
[RBAC configuration](https://kubernetes.io/docs/reference/access-authn-authz/rbac/) to 
define correct configuration.
__NOTE 2__: This example can be useful for development environments, avoit this approach in 
production environments.
