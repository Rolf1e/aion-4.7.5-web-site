import {Component, OnInit} from '@angular/core';
import {slideImages} from './slide.loader';
import { slideAnimation } from './slide.loader';

@Component({
  selector: 'app-slide-show',
  templateUrl: './slide-show.component.html',
  styleUrls: ['./slide-show.component.css'],
  animations: [slideAnimation]
})
export class SlideShowComponent implements OnInit {

  private slides;
  private currentIndex = 0;

  constructor() {
    this.slides = slideImages;
    this.preloadImages();
  }

  ngOnInit(): void {
  }

  preloadImages() {
    this.slides.forEach(slide => {
      (new Image()).src = slide.image;
    });
  }

  nextSlide() {
    this.currentIndex = (this.currentIndex >= this.slides.length - 1)
      ? 0
      : ++this.currentIndex;
  }

  previousSlide() {
    this.currentIndex = (this.currentIndex <= 0)
      ? this.slides.length - 1
      : --this.currentIndex;
  }

  isCurrentSlideIndex(index) {
    return this.currentIndex === index;
  }

  get getSlides() {
    return this.slides;
  }

  get getCurrentIndex(): number {
    return this.currentIndex;
  }

   setCurrentIndex(value: number) {
    this.currentIndex = value;
  }
}
