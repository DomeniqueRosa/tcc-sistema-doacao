import { Routes } from '@angular/router';
import { PaginaCadastroUsuario } from './features/usuario/pages/pagina-cadastro-usuarios/pagina-cadastrar-usuario';
import { PaginaListarUsuarios } from './features/admin/pages/pagina-listar-usuarios/pagina-listar-usuarios';
import { PaginaCadastrarTecnico } from './features/admin/pages/pagina-cadastrar-tecnico/pagina-cadastrar-tecnico';

export const routes: Routes = [
  {
    path: 'cadastro',
    component: PaginaCadastroUsuario
  },
  {
    path: 'admin/usuarios',
    component: PaginaListarUsuarios
  },
  {
    path: 'admin/usuarios/novo-tecnico',
    component: PaginaCadastrarTecnico
  },
  {
    path: '',
    redirectTo: 'cadastro',
    pathMatch: 'full'
  }
];
