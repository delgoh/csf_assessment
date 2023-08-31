import { FormGroup, FormBuilder } from '@angular/forms';
import { Component, OnInit, inject } from '@angular/core';
import { Router } from '@angular/router';

@Component({
  selector: 'app-landing-page',
  templateUrl: './landing-page.component.html',
  styleUrls: ['./landing-page.component.css']
})
export class LandingPageComponent implements OnInit {

  router: Router = inject(Router)
  fb: FormBuilder = inject(FormBuilder)

  landingForm!: FormGroup

  durations: number[] = [5, 15, 30, 45, 60]
  selectedDuration: number = 5

  ngOnInit(): void {
    this.landingForm = this.fb.group({
      duration: this.fb.control<number>(5)
    })
  }

  goToNewPost() {
    this.router.navigate(['/new-post'])
  }

}
