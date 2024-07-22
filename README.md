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


User and WatchList:

A User can have many WatchLists.
Each WatchList belongs to one User.
The WatchList table has a foreign key user_id referencing the id in the User table.
WatchList and WatchList_Stocks:

A WatchList can contain many WatchList_Stocks.
Each WatchList_Stock belongs to one WatchList.
The WatchList_Stocks table has a foreign key watchlist_id referencing the id in the WatchList table.
WatchList_Stocks and Stock_Alerts:

A WatchList_Stock can have many Stock_Alerts.
Each Stock_Alert belongs to one WatchList_Stock.
The Stock_Alerts table has a foreign key watchlist_stock_id referencing the id in the WatchList_Stocks table.
Stock_Alerts and Setup_Type:

A Stock_Alert uses one Setup_Type.
Each Stock_Alert has a foreign key setup_type_id referencing the id in the Setup_Type table.



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
#### getWatchlists `GET /watchlists`

#### getWatchlist `GET /watchlists/{id}`
#### createWatchlist `POST /watchlists`
#### updateWatchlist `PUT /watchlists/{id}`
#### deleteWatchlist `DELETE /watchlists/{id}`
### Service `WatchlistService`
- `List<Watchlists> getWatchlist()`
- `Watchlists getWatchlist(Long id)`
- `Watchlists createWatchList(Watchlists watchlist`
- `Watchlists updateWatchlists(Long id, Watchlists watchlist)`
- `void deleteWatchlist(Long id)`
---
# Watchlist_stocks

### Entity `WatchlistStocks`
- id - PK
- watchlist_id `FK (watchlist)`
- aggregate_id `FK (aggregates)`
### Controller `WatchlistStocksController`
#### getStocks `GET /stocks`
#### getStock `GET /stocks/{id}`
#### addStockToWatchlist `POST /stocks`
#### removeStock `DELETE /stocks/{id}`
### Service `WatchlistStockService`
- `List <WatchlistStocks> getStocks()`
- `WatchlistStocks getStock(Long id)`
- `WatchlistStocks addStock(WatchlistStocks stock)`
- `void removeStock(Long id)`
---
# Aggregates
### Entity `Aggregates`
- id - PK
- stock_symbol
- open
- close
- high
- low
- start_time
- end_time
- timeframe `FK (timeframes)`
### Controller `AggregateController`
#### Default view for non-registered users
- ##### getStockSymbols `GET /aggregates`
### Service 
#### `AggregateService`
- `List<Aggregates> getStockSymbols()`
- `List`
#### `OneMinuteAggregationService
#### `FiveMinuteAggregationService`
#### `FifteenMinuteAggregationService`
#### `ThirtyMinuteAggregationService`
#### `SixtyMinuteAggregationService`
#### `DailyAggregationService`
---
# Timeframe
### Entity `Timeframes`
- timeframe PK
### Enums `TimeframeEnums`
- 1_MIN
- 5_MIN
- 15_MIN
- 30_MIN
- 60_MIN
- DAY
### Controller `TimeframeController`
#### getTimeframes `GET /`
**Might not need this, figure out what i need for non registered user to query stocks by timeframes, will that go in here or in the aggregates? thinking the aggregates with query params**
### Service 
#### `TimeframeService`

---
# Stock_alerts
### Entity `StockAlerts`
- id - PK
- alert_time
- alert_status
- watclist_stock_id - `FK (watchlist_stocks)`
- scenario_id - `FK (scenarios)`
### Enums `AlertStatus`
- 
### Controller `StockAlertController`
#### createAlert `POST /alerts`
#### deleteAlert `DELETE /alerts/{id}`
### Service `StockAlertService`
- `StockAlerts createAlert(StockAlerts alert)`
- `void deleteAlerts(Long id)`
---
# Scenarios
### Entity `Scenarios`
- scenario - PK
### Enums `ScenarioEnums`
- INSIDE_BAR
- DIRECTIONAL_BAR_UP
- DIRECTIONAL_BAR_DOWN
- OUTSIDE_BAR
### Controller `ScenarioController`
### Service
#### `InsideBarService`
##### methods
#### `DirectionalBarUpService`
#### `DirectionalBarDownService`
(the directional bar service will have similar logic, might be able to make a single class)
#### `OutsideBarService`
---
# Web Sockets/config
- set up java server to act as a client to the web socket API
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

 
