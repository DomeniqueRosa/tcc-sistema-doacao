import { AfterViewInit, Component, OnInit, ViewChild, inject } from '@angular/core';
import { CommonModule, DatePipe } from '@angular/common';
import { Router, RouterLink } from '@angular/router';
import { MatCardModule } from '@angular/material/card';
import { MatTableDataSource, MatTableModule } from '@angular/material/table';
import { MatPaginator, MatPaginatorModule } from '@angular/material/paginator';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatButtonModule } from '@angular/material/button';
import { MatIconModule } from '@angular/material/icon';
import { MatTooltipModule } from '@angular/material/tooltip';
import { MatMenuModule } from '@angular/material/menu';

@Component({
  selector: 'app-pagina-listar-solicitacoes',
  standalone: true,
  imports: [
    CommonModule,
    RouterLink,
    DatePipe,
    MatCardModule,
    MatTableModule,
    MatPaginatorModule,
    MatFormFieldModule,
    MatInputModule,
    MatButtonModule,
    MatIconModule,
    MatTooltipModule,
    MatMenuModule
  ],
  templateUrl: './pagina-listar-solicitacoes.html',
  styleUrl: './pagina-listar-solicitacoes.css'
})
export class PaginaListarSolicitacoes implements OnInit, AfterViewInit {
  private router = inject(Router);

  displayedColumns: string[] = [
    'id',
    'equipamento',
    'dataCadastro',
    'ultimaAtualizacao',
    'status',
    'acoes'
  ];

  dataSource = new MatTableDataSource<any>([]);

  carregando = false;
  erroAoCarregar = false;

  @ViewChild(MatPaginator) paginator!: MatPaginator;

  ngOnInit(): void {
    this.carregarDadosMockados();

    // depois substituir pela chamada de api
    // this.buscarSolicitacoes();
  }

  ngAfterViewInit(): void {
    this.dataSource.paginator = this.paginator;

    this.dataSource.filterPredicate = (data: any, filter: string) => {
      const texto = `
        ${data.id}
        ${data.equipamento}
        ${data.dataCadastro}
        ${data.ultimaAtualizacao}
        ${data.status}
      `.toLowerCase();

      return texto.includes(filter);
    };
  }

  carregarDadosMockados(): void {
    this.carregando = false;
    this.erroAoCarregar = false;

    this.dataSource.data = [
      {
        id: '001',
        equipamento: 'Computador',
        dataCadastro: '2025-05-01',
        ultimaAtualizacao: '2025-05-01',
        status: 'APROVADA'
      },
      {
        id: '002',
        equipamento: 'Notebook',
        dataCadastro: '2025-05-01',
        ultimaAtualizacao: '2025-05-01',
        status: 'REPROVADA'
      },
      {
        id: '003',
        equipamento: 'Monitor',
        dataCadastro: '2025-05-01',
        ultimaAtualizacao: '2025-05-01',
        status: 'EM_ANALISE'
      },
      {
        id: '004',
        equipamento: 'Computador',
        dataCadastro: '2025-05-02',
        ultimaAtualizacao: '2025-05-02',
        status: 'ENTREGUE_DOADO'
      },
      {
        id: '005',
        equipamento: 'Notebook',
        dataCadastro: '2025-05-02',
        ultimaAtualizacao: '2025-05-02',
        status: 'ENTREGUE_DOADO'
      },
      {
        id: '006',
        equipamento: 'Monitor',
        dataCadastro: '2025-05-03',
        ultimaAtualizacao: '2025-05-03',
        status: 'ENTREGUE_DOADO'
      },
      {
        id: '007',
        equipamento: 'Computador',
        dataCadastro: '2025-05-03',
        ultimaAtualizacao: '2025-05-03',
        status: 'ENTREGUE_DOADO'
      },
      {
        id: '008',
        equipamento: 'Notebook',
        dataCadastro: '2025-05-04',
        ultimaAtualizacao: '2025-05-04',
        status: 'ENTREGUE_DOADO'
      },
      {
        id: '009',
        equipamento: 'Monitor',
        dataCadastro: '2025-05-04',
        ultimaAtualizacao: '2025-05-04',
        status: 'ENTREGUE_DOADO'
      },
      {
        id: '010',
        equipamento: 'Computador',
        dataCadastro: '2025-05-05',
        ultimaAtualizacao: '2025-05-05',
        status: 'ENTREGUE_DOADO'
      }
    ];
  }

  buscarSolicitacoes(): void {
    this.carregando = true;
    this.erroAoCarregar = false;

    // chamda de api
    // GET /solicitacao
  }

  aplicarFiltro(event: Event): void {
    const valor = (event.target as HTMLInputElement).value;
    this.dataSource.filter = valor.trim().toLowerCase();

    if (this.dataSource.paginator) {
      this.dataSource.paginator.firstPage();
    }
  }

  cadastrarSolicitacao(): void {
    this.router.navigate(['/usuario/solicitacao-doacao']);
  }

  verDetalhes(solicitacao: any): void {
    console.log('Ver detalhes da solicitação:', solicitacao);

    // chamada de api 
  }

  obterClasseStatus(status: string): string {
    switch (status?.toUpperCase()) {
      case 'APROVADA':
      case 'APROVADO':
        return 'status-aprovado';
      case 'REPROVADA':
      case 'REPROVADO':
        return 'status-reprovado';
      case 'EM_ANALISE':
        return 'status-analise';
      case 'ENTREGUE_DOADO':
      case 'DOADO':
        return 'status-entregue';
      default:
        return 'status-default';
    }
  }

  obterTextoStatus(status: string): string {
    switch (status?.toUpperCase()) {
      case 'APROVADA':
        return 'Aprovada';
      case 'REPROVADA':
        return 'Reprovada';
      case 'EM_ANALISE':
        return 'Em_Análise';
      case 'ENTREGUE_DOADO':
        return 'Entregue/Doado';
      default:
        return status;
    }
  }

  tentarNovamente(): void {
    // chamda de api 
    // this.buscarSolicitacoes();

    this.carregarDadosMockados();
  }
}
