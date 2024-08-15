import {Component, Input} from '@angular/core';

@Component({
  selector: 'app-button',
  templateUrl: './button.component.html',
  styleUrls: ['./button.component.css']
})
export class ButtonComponent {
  @Input() text: string | undefined;
  @Input() buttonType: 'primary' | 'secondary' = 'primary';

  getButtonClass(buttonType: string): string {
    const colors = {
      primary: 'bg-green-500',
      secondary: 'bg-gray-500'
    };


    return `${colors[this.buttonType]} text-white inline-flex items-center rounded-full px-2.5 py-0.5 text-sm font-medium`;
  }
}
