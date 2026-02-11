import { Component } from '@angular/core';
import { CommonModule, DatePipe } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { MatInputModule } from '@angular/material/input';
import { MatCardModule } from '@angular/material/card';
import { MatIconModule } from '@angular/material/icon';
import { MatTableModule } from '@angular/material/table';
import { MatButtonModule } from '@angular/material/button';
import { MatPaginatorModule } from '@angular/material/paginator';
import { FlexLayoutModule } from '@angular/flex-layout';

@Component({
  selector: 'app-pagina-listar-usuarios',
  standalone: true,
  imports: [
    CommonModule,
    FormsModule,
    MatInputModule,
    MatCardModule,
    MatIconModule,
    MatTableModule,
    MatButtonModule,
    MatPaginatorModule,
    FlexLayoutModule,
    DatePipe
  ],
  templateUrl: './pagina-listar-usuarios.html',
  styleUrls: ['./pagina-listar-usuarios.css'],
})
export class PaginaListarUsuarios {

  filtro: string = '';

  displayedColumns = [
    'id',
    'grr',
    'nome',
    'cpf',
    'dataCadastro',
    'curso',
    'acoes'
  ];

  dataSource = [
    { id: '001', grr: '202500000', nome: 'Maria', cpf: '00000000-00', dataCadastro: '2025-05-01', curso: 'TADS' },
    { id: '002', grr: '202400000', nome: 'José', cpf: '00000000-00', dataCadastro: '2025-05-01', curso: '---' },
    { id: '003', grr: '202300000', nome: 'Ana', cpf: '00000000-00', dataCadastro: '2025-05-01', curso: '---' },
  ];

  editar(element: any) {
    console.log('Editar:', element);
  }

  excluir(element: any) {
    console.log('Excluir:', element);
  }

}
