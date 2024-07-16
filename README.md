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
