import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';

import { AppComponent } from './app.component';
import { BannerComponent } from './banner/banner.component';
import { MenuComponent } from './menu/menu.component';
import { SigninComponent } from './login/signin/signin.component';
import { SlideShowComponent } from './slide-show/slide-show.component';

import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { NewsComponent } from './news/news.component';
import { YoutubeIncrustationComponent } from './youtube-incrustation/youtube-incrustation.component';

@NgModule({
  declarations: [
    AppComponent,
    BannerComponent,
    MenuComponent,
    SigninComponent,
    SlideShowComponent,
    NewsComponent,
    YoutubeIncrustationComponent
  ],
  imports: [
    BrowserModule,
    BrowserAnimationsModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
