import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';

import { AppComponent } from './app.component';
import { BannerComponent } from './index/banner/banner.component';
import { MenuComponent } from './index/menu/menu.component';
import { SigninComponent } from './index/login/signin/signin.component';
import { SlideShowComponent } from './index/slide-show/slide-show.component';

import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { NewsComponent } from './index/news/news.component';
import { YoutubeIncrustationComponent } from './index/youtube-incrustation/youtube-incrustation.component';
import { AppRoutingModule } from './app-routing.module';
import { PageNotFoundComponent } from './page-not-found/page-not-found.component';
import { IndexComponent } from './index/index.component';
import { ForumComponent } from './forum/forum.component'

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
    IndexComponent,
    ForumComponent
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
