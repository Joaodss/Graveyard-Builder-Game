import { Router } from '@angular/router';
import { Component, Input, OnInit } from '@angular/core';

@Component({
  selector: 'app-battle-end',
  templateUrl: './battle-end.component.html',
  styleUrls: ['./battle-end.component.sass']
})
export class BattleEndComponent implements OnInit {
  @Input() won!: number;
  @Input() experienceEarned: number = 0;
  @Input() goldEarned: number = 0;

  constructor(
    private route: Router
  ) { }

  ngOnInit(): void {
  }

  backToMenu() {
    this.route.navigate(['/menu']);
  }

}
