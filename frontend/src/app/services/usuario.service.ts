import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class UsuarioService {
  // A URL do backend para o endpoint de usuários
  private apiUrl = 'http://localhost:8080/usuarios'; 

  constructor(private http: HttpClient) { }

  salvar(usuario: any): Observable<any> {
    if(usuario.senha !== usuario.senhaConfirmacao){
      throw new Error("As senhas não coincidem.");
    }
    
    return this.http.post(this.apiUrl, usuario);
  }

  listarTodos() : Observable<any[]> {
    return this.http.get<any[]>(this.apiUrl);
  }

  excluir(id: string): Observable<void> {
    const url = `${this.apiUrl}/${id}`;
    return this.http.delete<void>(url);
  }

  listarPorId(id: string): Observable<any> {
    const url = `${this.apiUrl}/${id}`;
    return this.http.get<any>(url);
  }

  atualizar(id: string, usuario: any): Observable<any> {
    const url = `${this.apiUrl}/${id}`;
    return this.http.put<any>(url, usuario);
  }
}