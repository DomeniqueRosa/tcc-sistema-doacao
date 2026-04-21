import { ComponentFixture, TestBed } from '@angular/core/testing';

import { PaginaListarSolicitacoes } from './pagina-listar-solicitacoes';

describe('PaginaListarSolicitacoes', () => {
  let component: PaginaListarSolicitacoes;
  let fixture: ComponentFixture<PaginaListarSolicitacoes>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [PaginaListarSolicitacoes]
    })
    .compileComponents();

    fixture = TestBed.createComponent(PaginaListarSolicitacoes);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
