import { Component } from '@angular/core';
import { RouterLink } from '@angular/router';
import { MatIconModule } from '@angular/material/icon';

@Component({
  selector: 'app-menu-lateral',
  standalone: true,
  imports: [RouterLink, MatIconModule],
  templateUrl: './menu-lateral.html',
  styleUrl: './menu-lateral.css'
})
export class MenuLateral {

  tipoUsuario: 'comum' | 'tecnico' | 'admin' = 'comum';

  menusPorPerfil = {
    comum: [
      { label: 'Dashboard', rota: '/dashboard', icon: 'dashboard' },
      { label: 'Cadastro de Doação', rota: '/doacoes/cadastrar', icon: 'volunteer_activism' },
      { label: 'Solicitação de Doação', rota: '/solicitacoes/cadastrar', icon: 'assignment' },
      { label: 'Listar Doações', rota: '/doacoes', icon: 'search' },
      { label: 'Listar Solicitações', rota: '/solicitacoes', icon: 'search' }
    ],

    tecnico: [
      { label: 'Dashboard', rota: '/dashboard', icon: 'dashboard' },
      { label: 'Doação', rota: '/doacoes', icon: 'build' },
      { label: 'Histórico', rota: '/historico', icon: 'history' }
    ],

    admin: [
      { label: 'Dashboard', rota: '/dashboard', icon: 'dashboard' },
      { label: 'Listar Equipamentos', rota: '/equipamentos', icon: 'devices' },
      { label: 'Listar Doações', rota: '/doacoes', icon: 'volunteer_activism' },
      { label: 'Listar Solicitações', rota: '/solicitacoes', icon: 'assignment' }
    ]
  };

  get itensMenu() {
    return this.menusPorPerfil[this.tipoUsuario];
  }

}