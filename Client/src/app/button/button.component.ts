import {Component, Input} from '@angular/core';

@Component({
  selector: 'app-button',
  templateUrl: './button.component.html',
})
export class ButtonComponent {
  @Input() text: string | undefined;
  @Input() buttonType: 'primary' | 'secondary' = 'primary';

  getButtonClass(buttonType: string): string {
    const colors = {
      primary: 'bg-blue-500',
      secondary: 'bg-gray-500'
    };


    return `${colors[this.buttonType]} text-white inline-flex items-center rounded-lg px-3 py-2 text-sm font-medium`;
  }
}
