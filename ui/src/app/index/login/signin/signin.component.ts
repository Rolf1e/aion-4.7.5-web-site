import {Component, OnInit} from '@angular/core';
import {ApiService} from '../../../api.service';
import {API_BASE_URL, PORT} from '../../../api.config';
import {FormControl, FormGroup} from '@angular/forms';
import {Login} from './login';

@Component({
  selector: 'app-signin',
  templateUrl: './signin.component.html',
  styleUrls: ['./signin.component.css']
})
export class SigninComponent implements OnInit {

  private apiService: ApiService;
  private login: Login;
  loginForm: FormGroup;

  constructor(private api: ApiService) {
    this.apiService = api;

    this.loginForm = new FormGroup({
        username: new FormControl('usermame'),
        password: new FormControl('password')
      }
    );
  }

  ngOnInit(): void {
  }

  checkLogin() {
    this.login = this.loginForm.value;
    this.api.post('http://' + API_BASE_URL + ':' + PORT + '/login', this.login)
      .subscribe(data => {
        console.log(data);
      });
  }
}
