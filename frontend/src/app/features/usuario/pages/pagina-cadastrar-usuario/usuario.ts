import { v4 as uuid } from 'uuid';

export class Usuario {
    id?: string;
    nome?: string;
    email?: string;
    cpf?: string;
    senha?: string;
    senhaConfirmacao?: string;

    static novoUsuario(){
        let usuario = new Usuario();
        usuario.id = uuid();
        usuario.nome = "";
        usuario.email = "";
        usuario.cpf = "";
        usuario.senha = "";
        usuario
        return usuario;
    }
}