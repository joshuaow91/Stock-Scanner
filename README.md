# TECH SPEC
## Project Description
- Stream real time stock market data with the polygon websocket api. Allow users to scan and filter real time market data for triggers/set ups based on "The Strat" trading strategy. Users can create watch lists and stream data for specific symbols and save preferences for specific set ups. Users can also get alerts when stocks on their watch list trigger a set up from the user's preferences.
### External WebSockets API - Polygon.io
- Stream aggregates (1 min or 1 sec) to Java server which will act as a client for the API
### Server - Java/Spring Boot
- User Accounts
	- ability to save user strategy and stock symbol preferences for a custom watch list
	- receive alerts based on preferences selected 
- Filter incoming market data based on stock symbols list (have a default filter of data incoming on load, or require user to select a specific strategy?)
	- Initial iteration will use either the Weekly or Daily time frame
	- further filter by specific "set-ups"
		- create enums for strategies and stock symbols
			- logic to determine strategies with incoming data
- Test with Junit/mockito
### Client - Angular
- client to retrieve data from java server
- display a table of set ups based on filtering
### Database - PostgreSQL
- Tables for User, User Preferences, Watch Lists



# Users

### Entity `Users`
- id - PK
- username - unique
- password
- email - unique
### Controller `UserController`
#### getUsers `GET /users`
#### getUser `GET /users/{id}`
#### createUser `POST /
#### updateUser `PUT /users/{id]`
### Service `UserService`
- `List<Users> getUsers()`
- `Users getUser(Long id)`
- `Users createUser(Users user)`
- `Users updateUser(Long id, Users user)` 

---
# Authentication

### Controller `AuthenticationController`
#### login `POST /session`
#### logout `DELETE /session`

---
# Watchlists

### Entity `Watchlists`
 - id - PK
 - name
 - user_id - `FK (users)`
### Controller `WatchlistController`
#### getDefaultWatchlist `GET /default-watchlist`
#### getWatchlists `GET /watchlists`

#### getWatchlist `GET /watchlists/{id}`
#### createWatchlist `POST /watchlists`
#### updateWatchlist `PUT /watchlists/{id}`
#### deleteWatchlist `DELETE /watchlists/{id}`
### Service `WatchlistService`
- `List<WatchlistStock> getDefaultWatchlistStocks()`
- `List<Watchlists> getWatchlist(Long userId)`
- `Watchlists getWatchlist(Long id)`
- `Watchlists createWatchList(Watchlists watchlist`
- `Watchlists updateWatchlists(Long id, Watchlists watchlist)`
- `void deleteWatchlist(Long id)`
---
# Watchlist_stock

### Entity `WatchlistStock`
- id - PK
- watchlist_id `FK (watchlist)`
- stock_symbol
### Controller `WatchlistStockController`
#### getDefaultWatchlistStocks                                                                                `GET /watchlists/stock/aggregates?timeframe=X`
#### getAggregatesByWatchlistIdStockAndTimeframe                                      `GET /watchlists/{watchlistId}/stocks/aggregates?timeframe=X`
#### addStockToWatchlist `POST /watchlists/{watchlistId}/stocks`
#### deleteStockFromWatchlist                                                                                `DELETE /watchlists/{watchlistId}/stocks/{stock}`

### Service `WatchlistStockService`
- `List <WatchlistStocks> getStocks(Long watchlistId)`
- `WatchlistStocks getStock(Long id)`
- `WatchlistStocks addStock(WatchlistStocks stock)`
- `void removeStock(Long id)`
---
# Aggregates
## Entity `Aggregates`
- id - PK
- timeframe
- stock_symbol
- open
- close
- high
- low
- start_time
- end_time
- scenario
- trigger_price
- target_price
## Service 
### AggregationCalculationService
- `method to call on the calculate methods`
- `double calculateHighPrice(List<Aggregates> aggregates)`
- `double calculateLowPrice(List<Aggregates> aggregates)`
- `double calculateOpenPrice(List<Aggregates> aggregates)`
- `double calculateClosePrice(List<Aggregates> aggregates)`
- `double calculateTriggerPriceUp(List<Aggregates> aggregates)`
- `double calculateTriggerPriceDown(List<Aggregates> aggregates)`
- `double calculateTargetPriceUp(List<Aggregates> aggregates)`
- `double calculateTargetPriceDown(List<Aggregates> aggregates)`
- `LocalDateTime calculateStartTime(List<Aggregates> aggregates)`
- `LocalDateTime calculateEndTime(List<Aggregates> aggregates)`

### AggregationAbstractClass `Abstract Class`
- `protected abstract List<Aggregates> queryAggregates(String stockSymbol, TimeframeEnums timeframe)`
- `protected Aggregate convertToHigherTimeFrame(List<Aggregates> lowerTimeframe)`
	- Will need to call on calculate high and low price, open close, start and end time methods from main service class
- `protected void saveAggregates(Aggregate higherTimeFrame)`
- `public void execute(String stockSymbol, TimeframeEnums timeframe)`
	- calls on `queryAggregates`, `converToHigherTimeframe` & `saveAggregates` within method block
	- needs to call on scenarios method, trade actions methods before saving
### AggregationFactory
- `AggregationConversion getService(TimeframeEnums timeframe)`
	- use switch statement for timeframe enums
### AggregationScheduler
- *@Scheduled(cron = "0 */5 * * * MON-FRI", zone = "America/New_York")*
	- `void runFiveMinuteAggregation()`
		- method to get watchlist_stocks, conditional for if theres a user or not to either fetch user stocks or default list stocks
		- `execute()`
- *@Scheduled(cron = "0 */15 * * * MON-FRI", zone = "America/New_York")*
	- `void runFifteenMinuteAggregation()`
		- `execute()`
- *@Scheduled(cron = "0 */30 * * * MON-FRI", zone = "America/New_York")*
	- `void runThirtyMinuteAggregation()`
		- `execute()`
- *@Scheduled(cron = "0 0 * * * MON-FRI", zone = "America/New_York")*
	- `void runSixtyMinuteAggregation()`
		- `execute()`
- *@Scheduled(cron = "0 0 16 * * MON-FRI", zone = "America/New_York")*
	- `void runDailyAggregation()`
		- `execute()`
### FiveMinuteAggregationService *extends AggregationConversion*
- *@Override* `List<Aggregates> queryAggregates(String stockSymbol, TimeframeEnums timeframe)`
### FifteenMinuteAggregationService *extends AggregationConversion*
- *@Override* `List<Aggregates> queryAggregates(String stockSymbol, TimeframeEnums timeframe)`
### ThirtyMinuteAggregationService *extends AggregationConversion*
- *@Override* `List<Aggregates> queryAggregates(String stockSymbol, TimeframeEnums timeframe)`
### SixtyMinuteAggregationService *extends AggregationConversion*
- *@Override* `List<Aggregates> queryAggregates(String stockSymbol, TimeframeEnums timeframe)`
### DailyAggregationService *extends AggregationConversion*
- *@Override* `List<Aggregates> queryAggregates(String stockSymbol, TimeframeEnums timeframe)`

---
# Stock_alert
### Entity `StockAlert`
- id - PK
- timeframe
- alert_time
- alert_status
- alert_type
- watclist_stock_id - `FK (watchlist_stocks)`
### Controller `StockAlertController`
#### getAlerts `GET /alerts`
#### createAlert `POST /alerts`
#### deleteAlert `DELETE /alerts/{id}`
### Service `StockAlertService`
- `List<StockAlert> getAllAlerts()`
- `StockAlerts createAlert(StockAlerts alert)`
- `void deleteAlerts(Long id)`
---
# Scenarios

### Service `ScenarioService`
- `ScenarioEnums determineScenarios(List<Aggregates> recentAggregates`
	- `boolean isInsiderBar(Aggregate first, Aggregate second, Aggregate third)`
	- `boolean isDirectionalBarUp(Aggregate first, Aggregate second, Aggregate third)`
	- `boolean isDirectionalBarDown(Aggregate first, Aggregate second, Aggregate third)`
	- `boolean isOutsideBar(Aggregate first, Aggregate second, Aggregate third)`

---
# Enums

### `ScenarioEnums`
- INSIDE_BAR
- DIRECTIONAL_BAR_UP
- DIRECTIONAL_BAR_DOWN
- OUTSIDE_BAR
### `TimeframeEnums`
- ONE_MIN
- FIVE_MIN
- FIFTEEN_MIN
- THIRTY_MIN
- SIXTY_MIN
- DAY
### `AlertStatusEnums`
- ACTIVE
- INACTIVE
- TRIGGERED
- CANCELLED
### `AlertTypeEnums`
- TRIGGER_UP
- TRIGGER_DOWN
- TARGET_UP
- TARGET_DOWN

### `Stocks`
- AAPL("Apple Inc."),  
- MSFT("Microsoft Corporation"),  
- AMZN("Amazon.com, Inc."),  
- GOOGL("Alphabet Inc. (Class A)"),  
- GOOG("Alphabet Inc. (Class C)"),  
- FB("Facebook, Inc."),  
- TSLA("Tesla, Inc."),  
- BRK_B("Berkshire Hathaway Inc. (Class B)"),  
- JNJ("Johnson & Johnson"),  
- V("Visa Inc."),  
- WMT("Walmart Inc."),  
- PG("Procter & Gamble Co."),  
- MA("Mastercard Incorporated"),  
- NVDA("NVIDIA Corporation"),  
- HD("The Home Depot, Inc."),  
- PYPL("PayPal Holdings, Inc."),  
- DIS("The Walt Disney Company"),  
- ADBE("Adobe Inc."),  
- NFLX("Netflix, Inc."),  
- CMCSA("Comcast Corporation"),  
- PEP("PepsiCo, Inc."),  
- CSCO("Cisco Systems, Inc."),  
- XOM("Exxon Mobil Corporation"),  
- INTC("Intel Corporation"),  
- KO("The Coca-Cola Company");

---
# DTO
### UserDTO
### WatchlistDTO
### WatchlistStockDTO



---
# Websocket

## Websocket Config
- websocket client
	- return new standard websocket client
- websocket connection manager
	- new websocket connection manager
	- new polygon websocket handler
		- polygon url
## WebsocketMessageHandler
- auth after connection
- handle message
- handle status
- subscribe to watchlist stocks
 ---
# Utils
### MarketHoursUtil
#### public static boolean isMarketOpen()
### mappingDTOUtil
