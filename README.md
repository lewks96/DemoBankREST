# DemoBankREST

A basic, unsecured banking system written in Java and utilizing various Spring technologies.

## Usage

### Accounts

#### Create a new account

```console
curl -X POST localhost:8080/accounts/new -H 'Content-type:application/json' -d '{ "firstName": "xxx", "lastName": "xxx", "firstDeposit": xxx, "hasOverdraft": xxx, "overdraftLimit": -xxx }'
```

#### Delete account

```console
curl -v -X DELETE localhost:8080/accounts/<accountID>
```

#### Query account

```console
curl -v localhost:8080/accounts/<accountID>
```
<br>

### Transactions

#### Attempt a new transaction

```console
curl -X POST localhost:8080/transaction/new -H 'Content-type:application/json' -d '{ "sourceAccountID": "xxx", "destinationAccountID": "xxx", "amount": xxx}'  
```

#### Get a transaction description

```console
curl -v localhost:8080/transaction/<transactionID>
```

#### Get an accounts entire transaction history

```console
curl -v localhost:8080/transaction/all/<accountID>
```

#### Get an accounts recent transactions (1 month)

```console
curl -v localhost:8080/transaction/month/<accountID>
```

#### Get an accounts' transaction history, going back x days

```console
curl -v localhost:8080/transaction/days/<accountID>/<numberOfDays>
```