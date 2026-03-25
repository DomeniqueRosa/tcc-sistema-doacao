import { Routes } from '@angular/router';

// Layouts
import { LayoutAutenticacao } from './layouts/layout-autenticacao/layout-autenticacao';
import { LayoutPrincipal } from './layouts/layout-principal/layout-principal';

// Páginas públicas
import { PaginaLogin } from './features/auth/pages/pagina-login/pagina-login';
import { PaginaCadastrarUsuario } from './features/usuario/pages/pagina-cadastrar-usuario/pagina-cadastrar-usuario';

// Admin
import { PaginaDashboardAdmin } from './features/admin/pages/pagina-dashboard-admin/pagina-dashboard-admin';
import { PaginaListarUsuarios } from './features/admin/pages/pagina-listar-usuarios/pagina-listar-usuarios';
import { PaginaCadastrarTecnico } from './features/admin/pages/pagina-cadastrar-tecnico/pagina-cadastrar-tecnico';

// Técnico
import { PaginaDashboardTecnico } from './features/tecnico/pages/pagina-dashboard-tecnico/pagina-dashboard-tecnico';

// Usuário
import { PaginaDashboardUsuario } from './features/usuario/pages/pagina-dashboard-usuario/pagina-dashboard-usuario';

export const routes: Routes = [
  {
    path: '',
    component: LayoutAutenticacao,
    children: [
      { path: 'login', component: PaginaLogin },
      { path: 'cadastro', component: PaginaCadastrarUsuario }
    ]
  },
  {
    path: '',
    component: LayoutPrincipal,
    children: [
      { path: 'admin', component: PaginaDashboardAdmin },
      { path: 'admin/usuarios', component: PaginaListarUsuarios },
      { path: 'admin/usuarios/novo-tecnico', component: PaginaCadastrarTecnico },

      { path: 'tecnico', component: PaginaDashboardTecnico },

      { path: 'usuario', component: PaginaDashboardUsuario }
    ]
  },
  {
    path: '',
    redirectTo: 'login',
    pathMatch: 'full'
  }
];