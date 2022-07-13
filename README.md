# DemoBankREST

A basic, unsecured banking system written in Java and utilizing various Spring technologies.

Admittedly, this currently isn't a proper REST API, that will change soon with the addition of transactions and the
ability to lookup transaction history and make them between accounts.

## Usage

### Add new account

```console
curl -X POST localhost:8080/accounts/new -H 'Content-type:application/json' -d '{ "firstName": "xxx", "lastName": "xxx", "firstDeposit": xxx, "hasOverdraft": xxx, "overdraftLimit": -xxx }'
```

### Delete account

```console
curl -v -X DELETE localhost:8080/accounts/<accountID>
```

### Query account

```console
curl -v localhost:8080/account/<accountID>
```