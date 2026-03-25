import { Component, ViewChild } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterOutlet } from '@angular/router';
import { MatSidenav, MatSidenavModule } from '@angular/material/sidenav';
import { BarraSuperior } from './barra-superior/barra-superior';
import { MenuLateral } from './menu-lateral/menu-lateral';

@Component({
  selector: 'app-layout-principal',
  standalone: true,
  imports: [
    CommonModule,
    RouterOutlet,
    MatSidenavModule,
    BarraSuperior,
    MenuLateral
  ],
  templateUrl: './layout-principal.html',
  styleUrl: './layout-principal.css'
})
export class LayoutPrincipal {
  @ViewChild('drawer') drawer!: MatSidenav;

  toggleMenu(): void {
    this.drawer.toggle();
  }
}