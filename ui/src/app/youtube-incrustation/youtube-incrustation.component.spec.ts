import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { YoutubeIncrustationComponent } from './youtube-incrustation.component';

describe('YoutubeIncrustationComponent', () => {
  let component: YoutubeIncrustationComponent;
  let fixture: ComponentFixture<YoutubeIncrustationComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ YoutubeIncrustationComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(YoutubeIncrustationComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
