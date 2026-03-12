import { Component, EventEmitter, Output } from '@angular/core';
import { MatIconModule } from '@angular/material/icon';
import { MatButtonModule } from '@angular/material/button';

@Component({
  selector: 'app-barra-superior',
  standalone: true,
  imports: [
    MatIconModule,
    MatButtonModule
  ],
  templateUrl: './barra-superior.html',
  styleUrl: './barra-superior.css'
})
export class BarraSuperior {

  @Output()
  toggleMenu = new EventEmitter<void>();

}