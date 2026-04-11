

export class Usuario {

    nome?: string;
    email?: string;
    cpf?: string;
    senha?: string;
    senhaConfirmacao?: string;

    static novoUsuario(){
        let usuario = new Usuario();
        usuario.nome = "";
        usuario.email = "";
        usuario.cpf = "";
        usuario.senha = "";
        usuario
        return usuario;
    }
}