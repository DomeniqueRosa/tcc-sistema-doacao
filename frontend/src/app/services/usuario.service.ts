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
}