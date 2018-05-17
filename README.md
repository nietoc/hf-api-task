**Given the following web-services:**

http://services.groupkt.com/country/get/all
http://services.groupkt.com/country/get/iso2code/{COUNTRY_ISO2CODE} (e.g. http://services.groupkt.com/country/get/iso2code/us )

 
**Please create the following automated test scenarios:**

* Get all countries and validate that US, DE and GB were returned in the response
* Get each country (US, DE and GB) individually and validate the response
* Try to get information for inexistent countries and validate the response
* This API has not a POST method at the moment, but it is being developed. Write a test that would validate new country addition using POST(it will not work now, but no worries).

**Example of json body for POST is below:**
```
{
name: "Test Country",
alpha2_code: "TC",
alpha3_code: "TCY"
}
```

 

Tip: Do not forget to validate the response codes!
