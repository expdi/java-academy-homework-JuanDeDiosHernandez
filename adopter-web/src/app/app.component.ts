import { Component } from '@angular/core';
import { AdopterService } from './services/adopter.service';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss']
})
export class AppComponent {
  title = 'adopter-web';
  public dsAdopters: any;

  constructor(private adopterService: AdopterService) {

  }

  public getAdopters(): void {
    this.adopterService.Get().subscribe({ next: (res: any) => {
      this.dsAdopters = res;
    }, error: (err: any) => {
      console.error(err);
    }});
  }
}
