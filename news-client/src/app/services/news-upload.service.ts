import { HttpClient, HttpParams } from '@angular/common/http';
import { ElementRef, Injectable, inject } from '@angular/core';
import { FormGroup } from '@angular/forms';
import { firstValueFrom } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class NewsUploadService {

  constructor() { }

  http: HttpClient = inject(HttpClient)

  postNews(form: FormGroup, imageRef: ElementRef, tagString: string) {

    const formData = new FormData();
    formData.set('title', form.get('title')?.value)
    formData.set('photo', imageRef.nativeElement.files[0])
    formData.set('description', form.get('description')?.value)
    formData.set('tags', tagString)

    return firstValueFrom(
      this.http.post<any>('/api/news', formData)
    )
  }

  getTags(minuteOption: number) {
    let queryParams = new HttpParams()
      .set("minuteOption", minuteOption.toString())
    return firstValueFrom(
      this.http.get<any>('/api/tags', {params: queryParams})
    )
  }
}
