import { Routes } from '@angular/router';
import { PaginaCadastroUsuario } from './features/usuarios/pages/pagina-cadastro-usuarios/pagina-cadastrar-usuario';
import { PaginaListarUsuarios } from './features/usuarios/pages/pagina-listar-usuarios/pagina-listar-usuarios';

export const routes: Routes = [
    {path: 'user-cadastro', component: PaginaCadastroUsuario },
    {path: 'user-listagem', component: PaginaListarUsuarios }
];
