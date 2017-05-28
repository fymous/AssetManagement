# AssetManagement
Rest api based application which lets you add new shops and fetch nearby shops based on location of customer

APIs
1. Add a shop (POST)
URL: localhost:8080/shops/all-shops/create/
Sample data:
{"shopName": "Shop4","shopAddressNumber": "#94","shopAddressPostCode":560103}

2. List of nearby shops (GET)
URL: localhost:8080/shops/all-shops/shop-data?latitude=”latitude _of_customer”&longitude=”longitude_of_customer”
API Example: 
localhost:8080/shops/all-shops/shop-data?latitude=12.890564&longitude=77.673361
Response: json
Response Example:
[ 
 {    "id": 3,
    "shopName": "Shop4",
    "shopAddressNumber": "#94",
    "shopAddressPostCode": 560103,
    "lattitude": 12.9298689,
    "longitude": 77.6848366 
 }
]

