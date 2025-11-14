import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterOutlet } from '@angular/router';
import { MatSlideToggleModule } from '@angular/material/slide-toggle';
import { TesteService } from './services/teste.service';

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [CommonModule, RouterOutlet, MatSlideToggleModule],
  templateUrl: './app.html',
  styleUrls: ['./app.css']
})
export class AppComponent implements OnInit {
  mensagem = '';

  constructor(private testeService: TesteService) {}

  ngOnInit(): void {
    this.testeService.ping().subscribe({
      next: msg => this.mensagem = msg,
      error: err => this.mensagem = 'Erro ao conectar: ' + err.message
    });
  }
}
