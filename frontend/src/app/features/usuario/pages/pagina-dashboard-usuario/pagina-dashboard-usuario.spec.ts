import { ComponentFixture, TestBed } from '@angular/core/testing';

import { PaginaDashboardUsuario } from './pagina-dashboard-usuario';

describe('PaginaDashboardUsuario', () => {
  let component: PaginaDashboardUsuario;
  let fixture: ComponentFixture<PaginaDashboardUsuario>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [PaginaDashboardUsuario]
    })
    .compileComponents();

    fixture = TestBed.createComponent(PaginaDashboardUsuario);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
