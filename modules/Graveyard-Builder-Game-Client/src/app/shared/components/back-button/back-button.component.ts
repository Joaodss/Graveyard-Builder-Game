import { Router } from '@angular/router';
import { Component, Input, OnInit } from '@angular/core';

@Component({
  selector: 'app-back-button',
  templateUrl: './back-button.component.html',
  styleUrls: ['./back-button.component.sass']
})
export class BackButtonComponent implements OnInit {
  private routeMapping = new Map<String, String>([
    ['login', '/login'],
    ['menu', '/menu']
  ]);
  @Input() routeName!: string

  constructor(
    private route: Router
  ) { }

  ngOnInit(): void { }

  public navigateBack(): void {
    this.route.navigate([this.routeMapping.get(this.routeName)]);
  }

}
