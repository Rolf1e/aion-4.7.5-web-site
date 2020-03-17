import {Component, OnInit} from '@angular/core';
import {youtubeLinksList} from "./youtube.loarder";

@Component({
  selector: 'app-youtube-incrustation',
  templateUrl: './youtube-incrustation.component.html',
  styleUrls: ['./youtube-incrustation.component.css']
})
export class YoutubeIncrustationComponent implements OnInit {

  private readonly linksList;

  constructor() {
    this.linksList = youtubeLinksList;
  }

  ngOnInit(): void {
  }

  get getLinksList() {
    return this.linksList;
  }
}
