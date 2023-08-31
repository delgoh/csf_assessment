import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { NewsFormComponent } from './components/news-form/news-form.component';
import { ReactiveFormsModule } from '@angular/forms';
import { HttpClientModule } from '@angular/common/http';
import { LandingPageComponent } from './components/landing-page/landing-page.component';
import { NewsDisplayComponent } from './components/news-display/news-display.component';
import { NewsArticleComponent } from './components/news-article/news-article.component'

@NgModule({
  declarations: [
    AppComponent,
    NewsFormComponent,
    LandingPageComponent,
    NewsDisplayComponent,
    NewsArticleComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    ReactiveFormsModule,
    HttpClientModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
