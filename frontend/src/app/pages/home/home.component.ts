import { Component, OnInit } from '@angular/core';
import { Store } from '@ngrx/store';
import { NotificationService } from 'src/app/services/notification.service';
import { AuthState } from 'src/app/store/auth/auth.state';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.scss']
})
export class HomeComponent implements OnInit {

  constructor() { }

  ngOnInit(): void {
  }

}
