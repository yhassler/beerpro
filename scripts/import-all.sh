#!/bin/sh

node convert-ratebeer-csv-to-json.js locher.csv "Locher Appenzeller Bier" > data.json

node import-data.js

node convert-ratebeer-csv-to-json.js schuetzengarten.csv "Schützengarten" > data.json

node import-data.js

node convert-ratebeer-csv-to-json.js kornhausbraeu.csv "Kornhausbräu" > data.json

node import-data.js
