import { Component, EventEmitter, Output } from '@angular/core';
import { CommonModule } from '@angular/common';
import { MatIconModule } from '@angular/material/icon';
import { MatButtonModule } from '@angular/material/button';

@Component({
  selector: 'app-barra-superior',
  standalone: true,
  imports: [CommonModule, MatIconModule, MatButtonModule],
  templateUrl: './barra-superior.html',
  styleUrl: './barra-superior.css'
})
export class BarraSuperior {
  @Output() menuClick = new EventEmitter<void>();

  nomeUsuario = 'Usuário';

  abrirFecharMenu(): void {
    this.menuClick.emit();
  }

  logout(): void {
    localStorage.clear();
    window.location.href = '/login';
  }
}