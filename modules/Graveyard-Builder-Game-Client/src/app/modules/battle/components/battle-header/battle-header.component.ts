
import { Component, OnInit, Input } from '@angular/core';

@Component({
  selector: 'app-battle-header',
  templateUrl: './battle-header.component.html',
  styleUrls: ['./battle-header.component.sass']
})
export class BattleHeaderComponent implements OnInit {
  @Input() inBattle: boolean = false;
  @Input() userName!: string;
  @Input() userProfileImage!: string;
  @Input() opponentName!: string;
  @Input() opponentProfileImage!: string;

  constructor() { }

  ngOnInit(): void {
  }

}
