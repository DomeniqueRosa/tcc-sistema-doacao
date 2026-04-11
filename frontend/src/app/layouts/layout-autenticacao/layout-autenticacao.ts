import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterOutlet } from '@angular/router';

@Component({
  selector: 'app-layout-autenticacao',
  standalone: true,
  imports: [CommonModule, RouterOutlet],
  templateUrl: './layout-autenticacao.html',
  styleUrl: './layout-autenticacao.css'
})
export class LayoutAutenticacao {}