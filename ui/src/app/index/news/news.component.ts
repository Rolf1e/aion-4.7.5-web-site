import {Component, OnInit} from '@angular/core';
import {newsList} from './new.loader';

@Component({
  selector: 'app-news',
  templateUrl: './news.component.html',
  styleUrls: ['./news.component.css']
})
export class NewsComponent implements OnInit {

  private newsList;

  constructor() {
    this.newsList = newsList;
  }

  ngOnInit(): void {
  }

  get getNewsList() {
    return this.newsList;
  }
}
