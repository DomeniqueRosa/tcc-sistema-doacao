export interface SolicitacaoResponseDTO{
    id : number;
    curso : string;
    grr : string;
    motivo : string;
    semComputador : boolean;
    ativo : boolean;
    dataCadastro : string;
    historico : Historico[];

}

interface Historico {
    id : number;
    dataAlteracao : string;
    executor : string;
    status : string;
    observacao : string;

}