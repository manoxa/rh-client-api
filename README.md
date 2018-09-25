# rh-client-api

Cliente consumidor da API Rest - sistema de administração de recursos humanos

Este projeto foi desenvolvido com spring MVC e Kubernetes utilizando o plugin fabric8 no maven. Para executar esse projeto e necessario ter o cluster do Kubernetes executando localmente atraves do minikube ou online atraves do Google Cloud Plataform.

Com o seu kubernetes executando, siga os passos abaixo para reproduzir o exemplo.

Faca o build na aplicacao e o deploy no kubernetes

    $ mvn clean package

    $ mvn fabric8:build fabric8:resource fabric8:deploy

Test application :

    http://192.168.99.100:31372/  

Para deletar os recursos do kubernetes como: deployment, service, secret and pvc.

    $ mvn fabric8:undeploy

