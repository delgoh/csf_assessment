import { FormGroup, FormBuilder } from '@angular/forms';
import { Component, OnInit, inject } from '@angular/core';
import { Router } from '@angular/router';
import { NewsUploadService } from 'src/app/services/news-upload.service';

@Component({
  selector: 'app-landing-page',
  templateUrl: './landing-page.component.html',
  styleUrls: ['./landing-page.component.css']
})
export class LandingPageComponent implements OnInit {

  router: Router = inject(Router)
  fb: FormBuilder = inject(FormBuilder)
  newsUploadService: NewsUploadService = inject(NewsUploadService)

  landingForm!: FormGroup

  durations: number[] = [5, 15, 30, 45, 60]
  selectedDuration: number = 5

  tagsList: any = []

  ngOnInit(): void {
    this.landingForm = this.fb.group({
      duration: this.fb.control<number>(5)
    })
    this.updateTags()
    if (localStorage.getItem("selectedDuration") != null) {
      // set selectedDuration to value
    }

  }

  updateTags() {
    localStorage.setItem("selectedDuration", this.selectedDuration.toString())
    this.selectedDuration = this.landingForm.get('duration')!.value
    this.newsUploadService.getTags(this.selectedDuration)
      .then(res => {
        this.tagsList = res
      })
      .catch(err => {
        console.log(err)
      })
  }

  goToNewPost() {
    this.router.navigate(['/new-post'])
  }

}
