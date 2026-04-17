import { inject, Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Doacao } from '../models/doacao.model';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class DoacaoService {
  private http = inject(HttpClient);

  private apiUrl = 'http://localhost:8080/doacao';

  cadastrarDoacao(form: any) {
    const formData = new FormData();

    formData.append('equipamento', form.equipamento);
    formData.append('quantidade', String(form.quantidade));
    formData.append('descricao', form.descricao);
    formData.append('statusConservacao', form.statusConservacao);
    formData.append('status', 'PENDENTE');

    if (form.imagem) {
      formData.append('arquivo', form.imagem);
    }

    return this.http.post(this.apiUrl, formData);
  }

  listarDoacoes(): Observable<Doacao[]> {
    return this.http.get<Doacao[]>(this.apiUrl);
  }
}