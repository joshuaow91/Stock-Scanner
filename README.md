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
