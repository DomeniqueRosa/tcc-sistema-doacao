import { Component, OnInit, ViewChild, AfterViewInit, inject } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterLink } from '@angular/router';
import { MatCardModule } from '@angular/material/card';
import { MatTableDataSource, MatTableModule } from '@angular/material/table';
import { MatButtonModule } from '@angular/material/button';
import { MatIconModule } from '@angular/material/icon';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatPaginator, MatPaginatorModule } from '@angular/material/paginator';
import { MatSnackBar, MatSnackBarModule } from '@angular/material/snack-bar';
import { UsuarioService } from '../../../../core/services/usuario.service';
import { Usuario } from '../../../../core/models/usuario.model';

@Component({
  selector: 'app-pagina-listar-usuarios',
  standalone: true,
  imports: [
    CommonModule,
    RouterLink,
    MatCardModule,
    MatTableModule,
    MatButtonModule,
    MatIconModule,
    MatFormFieldModule,
    MatInputModule,
    MatPaginatorModule,
    MatSnackBarModule
  ],
  templateUrl: './pagina-listar-usuarios.html',
  styleUrl: './pagina-listar-usuarios.css'
})
export class PaginaListarUsuarios implements OnInit, AfterViewInit {
  private usuarioService = inject(UsuarioService);
  private snackBar = inject(MatSnackBar);

  displayedColumns: string[] = ['id', 'nome', 'cpf', 'email', 'perfil', 'acoes'];
  dataSource = new MatTableDataSource<Usuario>([]);
  carregando = false;

  @ViewChild(MatPaginator) paginator!: MatPaginator;

  ngOnInit(): void {
    this.carregarUsuarios();

this.dataSource.filterPredicate = (usuario: Usuario, filtro: string): boolean => {
  const texto = filtro.trim().toLowerCase();

  return (
    (usuario.nome ?? '').toLowerCase().includes(texto) ||
    (usuario.email ?? '').toLowerCase().includes(texto) ||
    (usuario.cpf ?? '').toLowerCase().includes(texto) ||
    (usuario.perfil ?? '').toLowerCase().includes(texto)
  );
};
  }

  ngAfterViewInit(): void {
    this.dataSource.paginator = this.paginator;
  }

  carregarUsuarios(): void {
    this.carregando = true;

    this.usuarioService.listarUsuarios().subscribe({
      next: (usuarios) => {
        this.dataSource.data = usuarios;
        this.carregando = false;
      },
      error: (erro) => {
        console.error('Erro ao listar usuários:', erro);
        this.snackBar.open('Erro ao carregar usuários.', 'Fechar', {
          duration: 3000
        });
        this.carregando = false;
      }
    });
  }

  aplicarFiltro(event: Event): void {
    const valor = (event.target as HTMLInputElement).value;
    this.dataSource.filter = valor.trim().toLowerCase();

    if (this.dataSource.paginator) {
      this.dataSource.paginator.firstPage();
    }
  }

  excluirUsuario(id: number): void {
    const confirmou = confirm('Tem certeza que deseja excluir este usuário?');

    if (!confirmou) {
      return;
    }

    this.usuarioService.deletarUsuario(id).subscribe({
      next: () => {
        this.snackBar.open('Usuário excluído com sucesso!', 'Fechar', {
          duration: 3000
        });
        this.carregarUsuarios();
      },
      error: (erro) => {
        console.error('Erro ao excluir usuário:', erro);
        this.snackBar.open('Erro ao excluir usuário.', 'Fechar', {
          duration: 3000
        });
      }
    });
  }
}