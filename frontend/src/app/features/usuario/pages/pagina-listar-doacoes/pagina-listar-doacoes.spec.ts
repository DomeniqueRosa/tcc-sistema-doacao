import { ComponentFixture, TestBed } from '@angular/core/testing';

import { PaginaListarDoacoes } from './pagina-listar-doacoes';

describe('PaginaListarDoacoes', () => {
  let component: PaginaListarDoacoes;
  let fixture: ComponentFixture<PaginaListarDoacoes>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [PaginaListarDoacoes]
    })
    .compileComponents();

    fixture = TestBed.createComponent(PaginaListarDoacoes);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
