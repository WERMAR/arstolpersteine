import { ComponentFixture, TestBed } from '@angular/core/testing';

import { StolpersteinManageViewComponent } from './stolperstein-manage-view.component';

describe('StolpersteinManageViewComponent', () => {
  let component: StolpersteinManageViewComponent;
  let fixture: ComponentFixture<StolpersteinManageViewComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [StolpersteinManageViewComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(StolpersteinManageViewComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
