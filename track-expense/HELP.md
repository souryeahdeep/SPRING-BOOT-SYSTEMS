

## TRACK EXPENSE
 
1. User can register and login.
2. User can add the expenses.
3. User can categorize the expenses in the DEBIT and CREDIT.
4. User can get the monthly expenses i.e. TOTAL AMOUNT DEBITED AND CREDITED.
5. User can filter expenses by date and transactionType.
6. Transaction will be fetched in sorted (in date) order.










## <I> ALL THE API ENDPOINTS </I>

1. **POST** /api/user/ [CREATE USER] <br>
   `{"name":"Sourya"} => req`
   `{"id":1,"name":"Sourya"} => res`



2. **GET** /api/user/ [GET ALL USERS IN DB] <br>
   `{"id":1,"name":"Sourya"} => res`


3. **GET** /api/user/{userId}/transactions [GET ALL TRANSACTIONS OF A USER] <br>
    `{[{"id":1,"amount":120,"transactionType":DEBIT,"timestamp":2026-11-10T12:00:12}],"total":120} => res`


4. **POST** /api/transaction [SAVE THE TRANSACTION] <br>
    `{"amount":1200,"transactionType":DEBIT,"userId":1} => req`
   `{"id":1,"amount":120,"transactionType":DEBIT,"timestamp":2026-11-10T12:00:12} => res`
