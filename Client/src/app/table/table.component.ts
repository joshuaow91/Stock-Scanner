import {Component, OnInit} from '@angular/core';
import {WatchlistStockService} from "../services/watchlist-stock.service";

@Component({
  selector: 'app-table',
  templateUrl: './table.component.html',
})
export class TableComponent implements OnInit {
  data: any;
  defaultTimeframe = "FIVE_MIN";

  constructor(private watchlistStockService: WatchlistStockService) {
  }

  ngOnInit(): void {
    this.fetchDefaultList(this.defaultTimeframe);
  }

  onTimeframeSelected(timeframe: string): void {
    this.fetchDefaultList(timeframe);
  }

  private fetchDefaultList(timeframe: string): void {
    this.watchlistStockService.getDefaultWatchlist(timeframe).subscribe({
      next: (response) => {
        console.log(response)
        this.data = response
      },
      error: (err) => console.error("error fetching data", err)
    })
  }

}
