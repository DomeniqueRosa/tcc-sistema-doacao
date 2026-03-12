import { ComponentFixture, TestBed } from '@angular/core/testing';

import { PaginaDashboard } from './pagina-dashboard';

describe('PaginaDashboard', () => {
  let component: PaginaDashboard;
  let fixture: ComponentFixture<PaginaDashboard>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [PaginaDashboard]
    })
    .compileComponents();

    fixture = TestBed.createComponent(PaginaDashboard);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
