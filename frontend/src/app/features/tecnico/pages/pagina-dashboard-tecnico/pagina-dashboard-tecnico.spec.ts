import { ComponentFixture, TestBed } from '@angular/core/testing';

import { PaginaDashboardTecnico } from './pagina-dashboard-tecnico';

describe('PaginaDashboardTecnico', () => {
  let component: PaginaDashboardTecnico;
  let fixture: ComponentFixture<PaginaDashboardTecnico>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [PaginaDashboardTecnico]
    })
    .compileComponents();

    fixture = TestBed.createComponent(PaginaDashboardTecnico);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
