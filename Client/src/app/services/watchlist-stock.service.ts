import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";

@Injectable({
  providedIn: 'root'
})
export class WatchlistStockService {
  private baseUrl = 'http://localhost:8080/watchlists/default/stocks/aggregates'

  constructor(private http: HttpClient) { }

  getDefaultWatchlist(timeframe: string): Observable<any> {
    const url = `${this.baseUrl}?timeframe=${timeframe}`;
    return this.http.get<any>(url);
  }
}
