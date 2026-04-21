import { ComponentFixture, TestBed } from '@angular/core/testing';

import { PaginaCadastroSolicitacao } from './pagina-cadastro-solicitacao';

describe('PaginaCadastroSolicitacao', () => {
  let component: PaginaCadastroSolicitacao;
  let fixture: ComponentFixture<PaginaCadastroSolicitacao>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [PaginaCadastroSolicitacao]
    })
    .compileComponents();

    fixture = TestBed.createComponent(PaginaCadastroSolicitacao);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
