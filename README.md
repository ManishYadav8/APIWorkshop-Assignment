# APIWorkshop-Assignment

Precondition:
1.	User1 is already registered with email and password.
2.	User2 email and password are available but not registered (Assumption: If user is not registered, then user should not be able to login).

Test Scenarios:
1.	Verify the status code, Response Body (token in response body should match with token generated while user was registered) and Response Header (e.g.: Server details) when correct email and password of Registered User (User1) are passed in Request Body (any valid format like Json) and valid HTTP method “Post” is used.
2.	Verify the status code, Response Body(error) and Response Header (e.g.: Server details) when correct email and password of Registered User (User1) are passed in Request Body (Invalid Format for eg: plain Text) and valid HTTP method “Post” is used.
3.	Verify the status code, Response Body and Response Header (e.g.: Server details) when correct email and password of Registered User (User1) are passed in Request Body (In Json Format) and invalid HTTP method like “Get” is used.
4.	Verify the status code, Response Body(error) and Response Header (e.g.:  Server details) when blank email and valid password of Registered User (User1) are passed in Request Body (In Json Format) and valid HTTP method “Post” is used.
5.	Verify the status code, Response Body(error) and Response Header (e.g.: Server details) when correct email and blank password of Registered User (User1) are passed in Request Body (In Json Format) and valid HTTP method “Post” is used.
6.	Verify the status code, Response Body(error) and Response Header (e.g.: Server details) when correct email and wrong password of Registered User (User1) are passed in Request Body (In Json Format) and valid HTTP method “Post” is used.
7.	Verify the status code, Response Body and Response Header (e.g.: Server details) when incorrect Request Body (any missing parameter or any wrong parameter) and valid HTTP method “Post” is used.
8.	Verify the response time in all the above-mentioned scenario.
9.	Verify the status code, Response Body and Response Header in case mapping of the server is changed from expected server.
10.	Verify the schema of Request Body and Response Body.
11.	Verify the contracts.

