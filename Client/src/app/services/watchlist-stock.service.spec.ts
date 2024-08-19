import { TestBed } from '@angular/core/testing';

import { WatchlistStockService } from './watchlist-stock.service';

describe('WatchlistStockService', () => {
  let service: WatchlistStockService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(WatchlistStockService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
