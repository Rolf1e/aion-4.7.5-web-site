import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-banner',
  templateUrl: './banner.component.html',
  styleUrls: ['./banner.component.css']
})
export class BannerComponent implements OnInit {

  private bannerTitle = 'Aion Server';
  constructor() { }

  ngOnInit(): void {
  }


  get getBannerTitle(): string {
    return this.bannerTitle;
  }
}
