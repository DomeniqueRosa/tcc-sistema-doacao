import { Component } from '@angular/core';
import { RouterOutlet } from '@angular/router';

import { MatSidenavModule } from '@angular/material/sidenav';

import { MenuLateral } from './menu-lateral/menu-lateral';
import { BarraSuperior } from './barra-superior/barra-superior';

@Component({
  selector: 'app-layout-principal',
  standalone: true,
  imports: [
    RouterOutlet,
    MatSidenavModule,
    MenuLateral,
    BarraSuperior
  ],
  templateUrl: './layout-principal.html',
  styleUrl: './layout-principal.css'
})
export class LayoutPrincipal {}