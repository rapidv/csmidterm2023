# StockBot 

StockBot is a bot that buys and sells shares of a stock based on user desired thresholds. Of course, these are fictional stocks, and it is meant to (somewhat) be a game.

This midterm partially has a "game" to it. You want to exit the StockBot automation with profit. Can you make profit?

## Features
- User Input
  - Asks user how much money they want to invest initially
  - Asks user on price thresholds for buying & selling
  - Option to exit the automation
- Automatic Pricing Adjustment
  - Stock adjusts by a percentage
- Buy/Sell Automation
  - Based on the user's initial input, the bot will buy and sell shares, depending on the share price of the stock. The bot will sell all shares if it is over the user's desired threshold, and it will buy shares if it is under the user's desired threshold.
  - The bot can buy a max of 10 shares automatically
- Operates on a day-by-day simulation
  - User confirmatioin needed to progress to the next day
- User puts in $80 per day into their investment
- User and stock statistics are shown daily
