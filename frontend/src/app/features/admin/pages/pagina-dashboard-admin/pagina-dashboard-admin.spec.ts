import { ComponentFixture, TestBed } from '@angular/core/testing';

import { PaginaDashboardAdmin } from './pagina-dashboard-admin';

describe('PaginaDashboardAdmin', () => {
  let component: PaginaDashboardAdmin;
  let fixture: ComponentFixture<PaginaDashboardAdmin>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [PaginaDashboardAdmin]
    })
    .compileComponents();

    fixture = TestBed.createComponent(PaginaDashboardAdmin);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
