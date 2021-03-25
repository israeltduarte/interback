# Desafio de implementação em Java do processo seletivo do Banco Inter

## Instalações

- Maven
- Java 8
- Eclipse ou Intellij
- Postman
- Visual Studio Code Swagger Viewer (opcional)

## Preparando a aplicação

Após ser realizado o download do projeto, o mesmo deve ser importado como um maven project para a IDE escolhida. Deve ser verificada a instalação do Java 8, tendo em vista que foi a versão utilizada para a implementação. O download e indexação das dependências do maven são então realizadas para a execução da aplicação.

## Executando a aplicação

O microsserviço pode ser executado tanto por Eclipse ou Intellij ou alguma outra IDE que tenha suporte a Spring Boot ou até mesmo pela linha de comando. Para o caso de utilização de IDE, basta ir até o arquivo InterbackApplication, abrir o menu auxiliar clicando com o botão direito, e selecionar a opção _Run()_. Para utilização por linha de comando, basta abrir o CMD, Bash ou equivalente, ir até a pasta raíz do projeto e executar os comandos _mvn clean compile_ para realizar a compilação dos arquivos e _mvn spring-boot:run_ para a execução do mesmo.

## Configurando o Banco de dados

Foi utilizado um banco H2 em memória para o desenvolvimento. Portando, uma vez que a aplicação foi executada, o banco pode ser acessado diretamente no navegador por meio do endereço [localhost:8080/h2-console](localhost:8080/h2-console), apenas sendo necessária a alteração do campo JDBC URL para _jdbc:h2:mem:interdb_, conforme configurado. Assim, as informações registradas no Banco de dados podem ser conferidas.

## Visualizando o Swagger

Assim sendo, o serviço estará pronto para ser utilizado. O Swagger completo do serviço pode ser encontrado na pasta raíz do projeto como _swagger.yaml_, contendo informações mais detalhadas sobre a utilização do mesmo. O arquivo pode ser aberto em qualquer editor de texto. **Indica-se a utilização da extensão Swagger Viewer no Visual Studio Code** para uma melhor visualização do mesmo. Um resumo pode ser visualizado em [localhost:8080/swagger-ui.html](localhost:8080/swagger-ui.html).

## Executando os testes unitários

Os testes unitários podem ser utilizados pela IDE, abrindo o menu auxiliar na pasta raíz do projeto e selecionando a opção _Run_All_Tests()_ ou então realizados diretamente pela linha de comando utilizando o comando _mvn test_.

## Utilizando o microsserviço

O microsserviço pode ter seus endpoints utilizados por meio da coleção, conforme disponibilizada, em conjunto com o Postman. Para a importação da coleção, acessar em _Importação por Arquivo_ e enviar o arquivo _postman_collection.json_. Uma seção chamada interback é criada e então o microsserviço pode ser acessado. Basta escolhar as requisições e clicar em _Send_ para utilizar as funcionalidades desenvolvidas.
