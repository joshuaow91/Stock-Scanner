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
- `List<Watchlists> getWatchlist()`
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
#### getStocks `GET /stock` 
*probable dont need*
#### getStock `GET /stocks/{id}`
*probably dont need*
#### addStockToWatchlist `POST /stocks`
#### removeStock `DELETE /stocks/{id}`
### Service `WatchlistStockService`
- `List <WatchlistStocks> getStocks()`
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
## Controller `AggregateController`
### Default view for non-registered users
- ##### getStockSymbols `GET /aggregates`
## Service 
### AggregateService
- `List<Aggregates> getStockSymbols()`
- `double calculateTriggerPriceUp(List<Aggregates> recentAggregates)`
- `double calculateTriggerPriceDown(List<Aggregates> recentAggregates)`
- `double calculateTargetPriceUp(List<Aggregates> recentAggregates)`
- `double calculateTargetPriceDown(List<Aggregates> recentAggregates)`
- `double calculateHighPrice (List<Aggregates> aggregates)`
- `double calculateLowPrice (List<Aggregates> aggregates)`
- `void saveAggregates (List<Aggregates> aggregates)`
### AggregationConversion `Abstract Class`
- `protected abstract List<Aggregates> queryAggregates(String stockSymbol, TimeframeEnums timeframe)`
- `protected abstract Aggregate convertToHigherTimeFrame(List<Aggregates> lowerTimeframe)`
- `protected void saveAggregates(Aggregate higherTimeFrame)`
- `public void execute(String stockSymbol, TimeframeEnums timeframe)`
	- calls on `queryAggregates`, `converToHigherTimeframe` & `saveAggregates` within method block
### AggregationServiceFactory
- `AggregationConversion getService(TimeframeEnums timeframe)`
	- use switch statement for timeframe enums
### AggregationScheduler
- *@Scheduled(cron = "0 */5 * * * MON-FRI", zone = "America/New_York")*
	- `void runFiveMinuteAggregation()`
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
- *@Override* `Aggregate convertToHigherTimeFrame(List<Aggregates> lowerTimeframe)`
- *Override* `List<Aggregates> queryAggregates(String stockSymbol, TimeframeEnums timeframe)`
### FifteenMinuteAggregationService *extends AggregationConversion*
- *@Override* `Aggregate convertToHigherTimeFrame(List<Aggregates> lowerTimeframe)`
- *Override* `List<Aggregates> queryAggregates(String stockSymbol, TimeframeEnums timeframe)`
### ThirtyMinuteAggregationService *extends AggregationConversion*
- *@Override* `Aggregate convertToHigherTimeFrame(List<Aggregates> lowerTimeframe)`
- *Override* `List<Aggregates> queryAggregates(String stockSymbol, TimeframeEnums timeframe)`
### SixtyMinuteAggregationService *extends AggregationConversion*
- *@Override* `Aggregate convertToHigherTimeFrame(List<Aggregates> lowerTimeframe)`
- *Override* `List<Aggregates> queryAggregates(String stockSymbol, TimeframeEnums timeframe)`
### DailyAggregationService *extends AggregationConversion*
- *@Override* `Aggregate convertToHigherTimeFrame(List<Aggregates> lowerTimeframe)`
- *Override* `List<Aggregates> queryAggregates(String stockSymbol, TimeframeEnums timeframe)`

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
- 1_MIN
- 5_MIN
- 15_MIN
- 30_MIN
- 60_MIN
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

---
# DTO
### UserDTO
### WatchlistDTO
### WatchlistStockDTO



---
# Websocket
## Websocket Client
## Websocket Config
## WebsocketMessageHandler
## WebSocketService
 ---
# Utils
### MarketHoursUtil
#### public static boolean isMarketOpen()
### mappingDTOUtil
---
# Notes

##### Processing 1 min aggregates into higher time frames
- batch processing with scheduled tasks to aggregate 1 min candles to higher time frames (5min, 15min, 30min, 60min, Day)
- logic for calculating timeframes and saving aggregates per timeframe 
	- dedicated time frame classes, separate classes for each timeframe
	- one single aggregates service class to process candles
	- each time frame service will save to the aggregates repository
	- websockets set up to display the higher time frames in real time
##### Scenarios
- query aggregates by stock symbol and time frame and the most recent 3 rows in the DB
- have logic to process these candles and determine the scenario it falls under for each of the recent 3 aggregates (1-2u-2d, etc)
	- *potentially need to create a patterns table to only display stocks with specific patterns and set alerts based on these patterns opposed to alerts for scenarios. This will also ensure the stocks displaying the most recent three candles are showing actionable set ups.*
- display the most recent three candles per stock, use web sockets to stream this

##### Default Filters
- by default, without registering an account this is the data that should be displayed along with potential filtering
	- Top 10 stocks from the S&P
	- Each stock will be in a table and display
		- symbol
		- most recent 3 candles displayed as scenarios (2u-2d-2u, etc)
		- current price of stock (may need separate API, or aggregate candles to determine)
		- *trigger, target prices in the future*
		- *timeframe continuity in the future, using a historical data api*
	- Timeframe buttons above the table to filter all stocks by selected timeframe
	- 
trigger price (entry price) target price

- **Whats Left**
	- subscribe to websocket based on user watchlist
	- DTOs
	- mapping for DTOs
	- change service layer return types to DTOs
	- websocket endpoints for client
	- scenarios get sent to client with websockets
