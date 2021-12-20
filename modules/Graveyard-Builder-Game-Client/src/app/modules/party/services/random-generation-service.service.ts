import { Observable } from 'rxjs';
import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class RandomGenerationService {
  readonly userGeneratorAPI = 'https://fakerapi.it/api/v1/persons?_quantity=10'
  private names: string[] = ['Lola', 'Wolff', 'Julien', 'Winfield', 'Jeanette', 'Lexus', 'Pattie', 'Marisol', 'Kristy', 'Lauriane', 'Eva', 'Shane'];
  private types = ['WARRIOR', 'ARCHER', 'MAGE']
  private statPoints = ['health', 'energy', 'attack']
  private generatedData: GeneratedData = {
    name: '',
    type: 'WARRIOR',
    healthPoints: 0,
    energyPoints: 0,
    attackPoints: 0
  };

  constructor(
    private http: HttpClient
  ) { }

  getRandomData(): GeneratedData {
    this.generatedData = {
      name: '',
      type: 'WARRIOR',
      healthPoints: 0,
      energyPoints: 0,
      attackPoints: 0
    };
    this.getRandomName();
    this.setRandomType();
    this.setRandomStatPoints();
    return this.generatedData;
  }


  private getRandomName(): void {
    this.generatedData.name = this.names[Math.floor(Math.random() * this.names.length)];
    this.http.get(this.userGeneratorAPI).subscribe({
      next: (results: any) => {
        this.names = [];
        results.data.forEach((element: any) => this.names.push(element.firstname));
      },
      error: (err: any) => console.log(err)
    });
  }


  private setRandomType(): void {
    this.generatedData.type = this.types[Math.floor(Math.random() * this.types.length)];
  }

  private setRandomStatPoints(): void {
    for (let i = 0; i < 3; i++) {
      let randomStat = this.statPoints[Math.floor(Math.random() * this.statPoints.length)];
      switch (randomStat) {
        case 'health':
          this.generatedData.healthPoints++;
          break;
        case 'energy':
          this.generatedData.energyPoints++;
          break;
        case 'attack':
          this.generatedData.attackPoints++;
          break;
        default:
          break;
      }
    }
  }

}


export interface UserNameData {
  data: [
    {
      firstname: string
    }
  ]
}


export interface GeneratedData {
  name: string;
  type: string
  healthPoints: number
  energyPoints: number
  attackPoints: number
}
