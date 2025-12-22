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
import { UsuarioService } from '../services/usuario.service';

@Component({
  selector: 'app-consulta',
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
  templateUrl: './consulta.component.html',
  styleUrls: ['./consulta.component.css'],
})
export class ConsultaComponent {

  dataSource: any;

  constructor(private usuarioService: UsuarioService) { }

  ngOnInit() {
    this.dataSource = this.usuarioService.listarTodos();
  }

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


  editar(element: any) {
    console.log('Editar:', element);
  }

  excluir(element: any) {
    console.log('Excluir:', element);
    this.usuarioService.excluir(element.id).subscribe({
      next: () => {
        console.log('Usuário excluído com sucesso!');
        // Atualizar a lista após exclusão
        this.dataSource = this.usuarioService.listarTodos();
      },
      error: (err) => {
        console.error('Erro ao excluir usuário:', err);
      }
    });
  }

}
