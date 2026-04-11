import { ComponentFixture, TestBed } from '@angular/core/testing';

import { PaginaCadastrarTecnico } from './pagina-cadastrar-tecnico';

describe('PaginaCadastrarTecnico', () => {
  let component: PaginaCadastrarTecnico;
  let fixture: ComponentFixture<PaginaCadastrarTecnico>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [PaginaCadastrarTecnico]
    })
    .compileComponents();

    fixture = TestBed.createComponent(PaginaCadastrarTecnico);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
