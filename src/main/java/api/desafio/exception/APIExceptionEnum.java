package api.desafio.exception;

import lombok.Data;
import org.springframework.http.HttpStatus;

public enum APIExceptionEnum {
    CPFInvalido("Cpf inválido", HttpStatus.CONFLICT,"Campo cpf inválido"),
    AssociadoJaVotou("Associado já votou nessa pauta", HttpStatus.FORBIDDEN, "Associado Já Votou"),
    CpfDeveSerPreenchido("Campo cpf deve ser preenchido", HttpStatus.FORBIDDEN,"Campo cpf inválido"),
    CpfDeveConterApenasNumeros("Campo cpf deve ser apenas números", HttpStatus.FORBIDDEN,"Campo cpf inválido"),
    CpfDeveConter11Numeros("Campo cpf deve conter 11 numeros", HttpStatus.FORBIDDEN, "Campo cpf inválido"),
    CpfJáExisteAssociado("Já existe associado com esse cpf", HttpStatus.CONFLICT, "Já existe cpf cadastrado para associado"),
    TituloDeveSerPreenchido("Titulo deve ser preenchido",HttpStatus.FORBIDDEN,"Campo titulo invalido"),
    DescricaoDeveSerPreenchido("Descricao deve ser preenchido",HttpStatus.FORBIDDEN,"Campo Descricao invalido"),
    PautaNaoEncontrada("Não existe essa pauta", HttpStatus.NOT_FOUND,"Pauta não encontrada"),
    NaoExisteResultadoParaAPauta("Não existe resultado para a pauta", HttpStatus.NOT_FOUND,"Resultado não encontrado"),
    NaoExisteResultadoParaAVotacao("Não existe resultado para a votacao", HttpStatus.NOT_FOUND,"Resultado não encontrado"),
    ResultadoNaoEncontrado("Não existe o resultado",HttpStatus.NOT_FOUND,"Resultado não encontrado"),
    VotacaoNaoEncontrada("Não existe essa votação",HttpStatus.NOT_FOUND,"Votação não encontrada"),
    IDPautaDeveSerFornecido("ID da pauta deve ser fornecido",HttpStatus.FORBIDDEN,"Campo invalido"),
    JaExisteVotacao("Ja existe uma votação para a pauta",HttpStatus.FORBIDDEN,"Votação já existe"),
    VotacaoDeveSerFornecida("Votação deve ser fornecida", HttpStatus.FORBIDDEN,"Votaçao deve ser fornecida"),
    AssociadoDeveSerFornecido("Associado deve ser fornecida", HttpStatus.FORBIDDEN,"Associado deve ser fornecida"),
    VotoDeveSerFornecido("Voto deve ser fornecida", HttpStatus.FORBIDDEN,"Voto deve ser fornecida"),
    VotoNaoEncontrado("Voto não encontrado",HttpStatus.NOT_FOUND,"Voto não encontrado"),
    VotoDeveSerSimOuNao("Voto deve ser apenas sim ou não",HttpStatus.FORBIDDEN,"Campo invalido"),
    VotacaoNaoEstaDisponivel("Votação não esta mais disponivel",HttpStatus.FORBIDDEN,"Votação encerrada"),
    AssociadoNaoEncontrado("Associado não encontrado",HttpStatus.NOT_FOUND,"Não encontrado");

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