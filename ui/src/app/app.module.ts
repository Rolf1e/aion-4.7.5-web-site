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
import { AppRoutingModule } from './app-routing.module';
import { PageNotFoundComponent } from './page-not-found/page-not-found.component';
import { IndexComponent } from './index/index.component'

@NgModule({
  declarations: [
    AppComponent,
    BannerComponent,
    MenuComponent,
    SigninComponent,
    SlideShowComponent,
    NewsComponent,
    YoutubeIncrustationComponent,
    PageNotFoundComponent,
    IndexComponent
  ],
  imports: [
    AppRoutingModule,
    BrowserModule,
    BrowserAnimationsModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
