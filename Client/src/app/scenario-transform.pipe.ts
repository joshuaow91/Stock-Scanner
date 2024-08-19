import { Pipe, PipeTransform } from '@angular/core';

@Pipe({
  name: 'scenarioTransform'
})
export class ScenarioTransformPipe implements PipeTransform {

  transform(value: string): string {
    switch(value) {
      case 'INSIDE_BAR':
        return '1';
      case 'DIRECTIONAL_BAR_UP':
        return '2U';
      case 'DIRECTIONAL_BAR_DOWN':
        return '2D';
      case 'OUTSIDE_BAR':
        return '3';
      default:
        return value;
    }
  }


}
