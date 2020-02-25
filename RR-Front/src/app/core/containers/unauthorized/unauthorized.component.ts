import { Component, OnInit } from '@angular/core';
import {AuthenticationApi} from "../../service/api/authentication.api";
import {Router} from "@angular/router";

@Component({
  selector: 'app-unauthorized',
  templateUrl: './unauthorized.component.html',
  styleUrls: ['./unauthorized.component.scss']
})
export class UnauthorizedComponent implements OnInit {

  constructor(private authAPI: AuthenticationApi, private router: Router) { }

  ngOnInit() {
    this.authAPI.Authentication().subscribe((data: any) => {
      window.localStorage.setItem('token', data.jwtToken);
      this.router.navigate(['dashboard']);
    }, () => {
      console.log('err');
    })
  }

}
