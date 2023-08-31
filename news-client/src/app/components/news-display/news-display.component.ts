import { Component, OnInit, inject } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Article } from 'src/app/models/article';
import { NewsUploadService } from 'src/app/services/news-upload.service';

@Component({
  selector: 'app-news-display',
  templateUrl: './news-display.component.html',
  styleUrls: ['./news-display.component.css']
})
export class NewsDisplayComponent implements OnInit {

  newsUploadService: NewsUploadService = inject(NewsUploadService)
  router: Router = inject(Router)
  activatedRoute: ActivatedRoute = inject(ActivatedRoute)

  tagName: string = ""
  articleArr: any = []

  ngOnInit(): void {
    this.tagName = this.activatedRoute.snapshot.params['tag-name']
    this.newsUploadService.getNewsByTag(this.tagName)
      .then(res => {
        this.articleArr = res
      }).catch(err => {
        console.log(err)
      })
  }

  goBack() {
    this.router.navigate(['/'])
  }

  goToNewPost() {
    this.router.navigate(['/new-post'])
  }

}
