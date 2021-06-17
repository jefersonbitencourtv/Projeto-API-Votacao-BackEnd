# desafio_digital
----Instruções para uso

link do swagger: http://localhost:8080/swagger-ui.html#

Utilizar o mysql workbench para criar o banco
assembleia
login: root
senha:1234

Inserir as tabelas da pasta TabelasVanco

----------------------------------------------------------------
Funcionamento da api:
Associado em seu post deve ser fornecido cpf númerico válido.
Pauta em seu post deve ser fornecido titulo e descricao.
Votacao em seu post deve ser fornecido idPauta. DuracaoVotacao é opcional caso nao fornecido por default é 1 minuto.
Voto em seu post deve ser fornecido idVotacao, idAssociado, e voto. Voto deve ser apenas sim ou não.
Resultado não possui post.


