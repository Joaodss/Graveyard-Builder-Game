import { Router } from '@angular/router';
import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-menu',
  templateUrl: './menu.component.html',
  styleUrls: ['./menu.component.sass']
})
export class MenuComponent implements OnInit {

  private routeMapping = new Map<String, String>([
    ['battle', '/battle'],
    ['party', '/party'],
    ['graveyard', '/graveyard']
  ]);

  constructor(
    private route: Router
  ) { }

  ngOnInit(): void { }

  public navigate(route: string): void {
    this.route.navigate([this.routeMapping.get(route)]);
  }

}
