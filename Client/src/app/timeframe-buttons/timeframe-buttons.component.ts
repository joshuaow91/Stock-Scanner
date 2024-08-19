import {Component, EventEmitter, Input, OnInit, Output} from '@angular/core';

@Component({
  selector: 'app-timeframe-buttons',
  templateUrl: './timeframe-buttons.component.html',
})
export class TimeframeButtonsComponent implements OnInit{
  timeframes = [
    { id: 1, timeframe: 'FIVE_MIN', name: '5' },
    { id: 2, timeframe: 'FIFTEEN_MIN', name: '15' },
    { id: 3, timeframe: 'THIRTY_MIN', name: '30' },
    { id: 4, timeframe: 'SIXTY_MIN', name: '60' },
    { id: 5, timeframe: 'DAY', name: 'D' }
  ]

  @Input() activeTimeframe: string = '';
  @Output() selectedTimeframe = new EventEmitter<string>();


  ngOnInit(): void {
    this.selectedTimeframe.emit(this.activeTimeframe);
  }

  onTimeframeSelect(timeframe: string) {
    this.activeTimeframe = timeframe;
    this.selectedTimeframe.emit(timeframe);
  }
}
