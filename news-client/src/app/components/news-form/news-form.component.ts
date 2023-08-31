import { Component, ElementRef, OnInit, ViewChild, inject } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { NewsUploadService } from 'src/app/services/news-upload.service';

@Component({
  selector: 'app-news-form',
  templateUrl: './news-form.component.html',
  styleUrls: ['./news-form.component.css']
})
export class NewsFormComponent implements OnInit {

  router: Router = inject(Router)
  fb: FormBuilder = inject(FormBuilder)
  newsService: NewsUploadService = inject(NewsUploadService)

  newsForm!: FormGroup
  @ViewChild('photoFile') photoToUpload!: ElementRef
  @ViewChild('alertDialog') alertDialog!: HTMLDialogElement

  currentTags: string[] = []
  newsId!: string

  ngOnInit(): void {
    this.newsForm = this.fb.group({
      title: this.fb.control<string>('', [Validators.required, Validators.minLength(5)]),
      photo: this.fb.control<string>('', [Validators.required]),
      description: this.fb.control<string>('', [Validators.required, Validators.minLength(5)]),
      tags: this.fb.control<string>('')
    })
  }

  addTags() {
    const tagsToAdd: string[] = this.newsForm.get('tags')?.value.trim().split(" ")
    for (const tag of tagsToAdd){
      if (!this.currentTags.includes(tag))
      this.currentTags.push(tag)
    }
    console.log(this.currentTags)
  }

  deleteTag(tagToDelete: any, tagString: string) {
    // this.currentTags.splice(this.currentTags.indexOf(tagToDelete), 1)
    this.currentTags = this.currentTags.filter(tag => tag !== tagString)
    console.log(this.currentTags)
    tagToDelete.remove()
  }

  processForm() {
    let tagString: string = " "
    for (const currentTag of this.currentTags) {
      tagString += currentTag
      tagString += " "
    }
    this.newsService.postNews(this.newsForm, this.photoToUpload, tagString)
      .then(res => {
        this.newsId = res['newsId']
        // this.alertDialog.show()
      })
  }

  goBack() {
    this.router.navigate(['/'])
  }

  // closeDialog() {
  //   this.alertDialog.close()
  // }

}
