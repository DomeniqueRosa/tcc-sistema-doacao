import { ComponentFixture, TestBed } from '@angular/core/testing';

import { PaginaListarUsuarios } from './pagina-listar-usuarios';

describe('PaginaListarUsuarios', () => {
  let component: PaginaListarUsuarios;
  let fixture: ComponentFixture<PaginaListarUsuarios>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [PaginaListarUsuarios]
    })
    .compileComponents();

    fixture = TestBed.createComponent(PaginaListarUsuarios);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
