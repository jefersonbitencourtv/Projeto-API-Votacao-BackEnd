# desafio_digital
----Novas implementações
<<<<<<< HEAD

  Branch developer está sendo inserido as novas funcionalidades.
  
  -Mapeamento JPA
  
  -Consulta api externa para validação e se cpf pode votar
  
  -Responses especificos para os endpoints
  
  Em implementação documentação mais elaborada com swagger
  
  
----Instruções para uso

Heroku:
https://api-votacao-sicredi.herokuapp.com/swagger-ui.html
------------------------

Caso queira rodar na propria maquina:
No arquivo application.properties
Comentar a segunda linha número 2 com // no inicio
Descomentar as linhas 3,4 e 5
link do swagger: http://localhost:8080/swagger-ui.html#
=======

Branch developer está sendo inserido as novas funcionalidades.
>>>>>>> developer

-Mapeamento JPA

-Consulta api externa para validação e se cpf pode votar

<<<<<<< HEAD
Observação:
Criação do host
login: root
senha: 1234
O mysql deve usar o caminho localhost:3306

----------------------------------------------------------------

**Funcionamento da api:

Associado controller:

/api/v1/associado               POST-- deve ser fornecido cpf númerico válido.
/api/v1/associado               GET -- retorna uma lista com os associados
/api/v1/associado/{id}          GET -- deve ser fornecido o id do associado, retorna o associado pelo id

Pauta controller:

/api/v1/pauta                   GET -- retorna uma lista com as pautas
/api/v1/pauta/{id}              GET -- deve ser fornecido o id da pauta, retorna a pauta pelo id
/api/v1/pauta                   POST-- deve ser fornecido titulo e descricao.

Votacao Controller:

/api/v1/votacao                 GET -- retorna uma lista com as votações
/api/v1/votacao/{id}            GET -- deve ser fornecido o id da votação, retorna a votação pelo id
/api/v1/votacao                 POST-- deve ser fornecido idPauta. DuracaoVotacao é opcional caso nao fornecido por default é 1 minuto.

Pauta controller:

/api/v1/voto                    GET -- retorna uma lista com os votos
/api/v1/voto/{id}               GET -- deve ser fornecido o id do voto, retorna o voto pelo id
/api/v1/voto                    POST--  deve ser fornecido idVotacao, idAssociado, e voto. Voto deve ser apenas sim ou não.

Resultado controller:

/api/v1/resultado               GET -- retorna uma lista com os resultados
/api/v1/resultado/{id}          GET -- deve ser fornecido o id do resultado, retorna o resultado pelo id
/api/v1/resultado/pauta/{id}    GET -- deve ser fornecido o id da pauta, retorna o resultado pelo id da pauta
/api/v1/resultado/votacao/{id}  GET -- deve ser fornecido o id da votacao, retorna o resultado pelo id da votacao

Resultado não possui post.




=======
-Responses especificos para os endpoints

--Documentação explicativa com swagger

Em andamento: Adequação dos testes aos novos endpoints
>>>>>>> developer
