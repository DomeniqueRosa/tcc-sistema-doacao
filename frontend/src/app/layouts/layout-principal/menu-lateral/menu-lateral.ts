import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterLink, RouterLinkActive } from '@angular/router';
import { MatIconModule } from '@angular/material/icon';

@Component({
  selector: 'app-menu-lateral',
  standalone: true,
  imports: [CommonModule, RouterLink, RouterLinkActive, MatIconModule],
  templateUrl: './menu-lateral.html',
  styleUrl: './menu-lateral.css'
})
export class MenuLateral {
  // TEMPORÁRIO
  // depois virá do AuthService / token
  perfil: 'ADMIN' | 'TECNICO' | 'USUARIO' = 'USUARIO';

  usuariosAberto = false;

  toggleUsuarios(): void {
    this.usuariosAberto = !this.usuariosAberto;
  }
}