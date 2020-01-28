import { Component, OnInit } from '@angular/core';
import {GlobalResourceApi} from "../../core/service/api/global-resource.api";

@Component({
  selector: 'app-footer-component',
  templateUrl: './footer-component.component.html',
  styleUrls: ['./footer-component.component.scss']
})
export class FooterComponentComponent implements OnInit {
  version: any;
  env: any;

  constructor(private globalAPI: GlobalResourceApi) { }

  ngOnInit() {
    this.globalAPI.getVersionDAta().subscribe(data => this.version = data);
    this.globalAPI.getEnvData().subscribe(data => {this.env = data});
  }

}
