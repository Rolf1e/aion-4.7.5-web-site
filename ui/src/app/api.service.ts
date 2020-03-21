import {Injectable} from '@angular/core';
import {HttpClient, HttpHeaders} from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class ApiService {

  constructor(private http: HttpClient) {
  }

  get(url: string) {
    return this.http.get(url, {responseType: 'json'});
  }

  post(url: string, body) {
    return this.http.post(url, body, {responseType: 'json'});
  }
}
