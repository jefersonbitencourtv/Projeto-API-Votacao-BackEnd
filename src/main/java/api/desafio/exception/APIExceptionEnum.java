package api.desafio.exception;

import org.springframework.http.HttpStatus;

public enum APIExceptionEnum {
    CPF_NAO_PODE_VOTAR("Esse cpf não esta habilitado para votar", HttpStatus.OK,"Cpf impossibilitado"),
    ERRO_API("Ocorreu um erro interno. Tente novamente.",HttpStatus.BAD_REQUEST, "Erro interno"),
    CPF_INVALIDO("Cpf inválido", HttpStatus.UNPROCESSABLE_ENTITY,"Campo inválido"),
    ASSOCIADO_JA_VOTOU("Associado já votou nessa pauta", HttpStatus.CONFLICT, "Associado Já Votou"),
    CPF_DEVE_SER_PREENCHIDO("Campo cpf deve ser preenchido", HttpStatus.BAD_REQUEST,"Campo cpf inválido"),
    CPF_DEVE_CONTER_APENAS_NUMEROS("Campo cpf deve ser apenas números", HttpStatus.BAD_REQUEST,"Campo cpf inválido"),
    CPF_DEVE_CONTER_11_NUMEROS("Campo cpf deve conter 11 numeros", HttpStatus.BAD_REQUEST, "Campo cpf inválido"),
    CPF_JA_EXISTE_ASSOCIADO("Já existe associado com esse cpf", HttpStatus.CONFLICT, "Já existe cpf cadastrado para associado"),
    TITULO_DEVE_SER_PREENCHIDO("Titulo deve ser preenchido",HttpStatus.BAD_REQUEST,"Campo titulo invalido"),
    DESCRICAO_DEVE_SER_PREENCHIDO("Descricao deve ser preenchido",HttpStatus.BAD_REQUEST,"Campo Descricao invalido"),
    PAUTA_NAO_ENCONTRADA("Não existe essa pauta", HttpStatus.NOT_FOUND,"Pauta não encontrada"),
    NAO_EXISTE_RESULTADO_PARA_A_PAUTA("Não existe resultado para a pauta", HttpStatus.NOT_FOUND,"Resultado não encontrado"),
    NAO_EXISTE_RESULTADO_PARA_A_VOTACAO("Não existe resultado para a votacao", HttpStatus.NOT_FOUND,"Resultado não encontrado"),
    RESULTADO_NAO_ENCONTRADO("Não existe o resultado",HttpStatus.NOT_FOUND,"Resultado não encontrado"),
    VOTACAO_NAO_ENCONTRADA("Não existe essa votação",HttpStatus.NOT_FOUND,"Votação não encontrada"),
    ID_PAUTA_DEVE_SER_FORNECIDO("ID da pauta deve ser fornecido",HttpStatus.BAD_REQUEST,"Campo invalido"),
    JA_EXISTE_VOTACAO("Ja existe uma votação para a pauta",HttpStatus.CONFLICT,"Votação já existe"),
    VOTACAO_DEVE_SER_FORNECIDA("Votação deve ser fornecida", HttpStatus.BAD_REQUEST,"Votaçao deve ser fornecida"),
    ASSOCIADO_DEVE_SER_FORNECIDO("Associado deve ser fornecida", HttpStatus.BAD_REQUEST,"Associado deve ser fornecida"),
    VOTO_DEVE_SER_FORNECIDO("Voto deve ser fornecido", HttpStatus.BAD_REQUEST,"Voto deve ser fornecida"),
    VOTO_NAO_ENCONTRADO("Voto não encontrado",HttpStatus.NOT_FOUND,"Voto não encontrado"),
    VOTO_DEVE_SER_SIM_OU_NAO("Voto deve ser apenas sim ou não",HttpStatus.BAD_REQUEST,"Campo invalido"),
    VOTACAO_NAO_ESTA_DISPONIVEL("Votação não esta mais disponivel",HttpStatus.UNPROCESSABLE_ENTITY,"Votação encerrada"),
    ASSOCIADO_NAO_ENCONTRADO("Associado não encontrado",HttpStatus.NOT_FOUND,"Não encontrado");

private String texto;
private HttpStatus status;
private String erro;

    public String getErro() {
        return erro;
    }

    public void setErro(String erro) {
        this.erro = erro;
    }

    public String getTexto() {
        return texto;
    }

    public void setTexto(String texto) {
        this.texto = texto;
    }

    public HttpStatus getStatus() {
        return status;
    }

    public void setStatus(HttpStatus status) {
        this.status = status;
    }

    APIExceptionEnum(String texto, HttpStatus status, String erro) {
    this.texto = texto;
    this.status = status;
    this.erro = erro;

}
}