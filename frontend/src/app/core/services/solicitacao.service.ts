import { HttpClient } from "@angular/common/http";
import { inject, Injectable } from "@angular/core";
import { SolicitacaoDTO } from "../dto/solicitacao.dto";
import { Observable } from "rxjs";

@Injectable({
  providedIn: 'root'
})

export class SolicitacaoService {

    private http = inject(HttpClient);
    private apiUrl = 'http://localhost:8080/solicitacao';

    cadastrarSolicitacao(dados: SolicitacaoDTO): Observable<SolicitacaoDTO> {
        return this.http.post<SolicitacaoDTO>(this.apiUrl, dados);
    }
}