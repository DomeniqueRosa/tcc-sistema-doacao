import { Routes } from '@angular/router';
import { PaginaCadastroUsuario } from './features/usuarios/pages/pagina-cadastro-usuarios/pagina-cadastrar-usuario';
import { PaginaListarUsuarios } from './features/usuarios/pages/pagina-listar-usuarios/pagina-listar-usuarios';
import { LayoutAutenticacao } from './layouts/layout-autenticacao/layout-autenticacao';
import { LayoutPrincipal } from './layouts/layout-principal/layout-principal';

export const routes: Routes = [
  {
    path: 'auth',
    component: LayoutAutenticacao,
    children: [
      { path: 'cadastro', component: PaginaCadastroUsuario }
    ]
  },
  {
    path: '',
    component: LayoutPrincipal,
    children: [
      { path: 'dashboard', loadComponent: () => import('./features/dashboard/pages/pagina-dashboard/pagina-dashboard').then(m => m.PaginaDashboard) },
      { path: 'usuarios', component: PaginaListarUsuarios }
    ]
  },
  { path: '', redirectTo: 'dashboard', pathMatch: 'full' }
];


