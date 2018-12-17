# CCCPharma-backend

Esse é o back-end em SpringBoot + Hibernate de um projeto proposto na disciplina Projeto de Software (UFCG, 2018)

O back-end foi pensado e desenvolvido em conjunto por @danielmitre e @DiegoGamaV

# Rotas

O servidor REST atualmente se encontra disponível em https://whispering-ocean-74723.herokuapp.com/

## Padrão CRUD (Create, Read, Update e Delete) utilizado para as rotas.
create: POST

read:   GET

update: PATCH

delete: DELETE

## Product
### URL da rota: /product/create/

Método do servidor: addProduct(name, barcode, company, category, status)

Método HTTP: POST

Body do Request: {user:{login, password}, data:{name, barcode, company, category, status}}

Possíveis códigos de status: 201, 400, 401, 403


### URL da rota: /product/change_price/

Método do servidor: changeProductPrice(barcode, preco)

Método HTTP: PATCH 

Body do Request: {user:{login, password}, data:{barcode, newPrice}}  

Possíveis códigos de status: 200, 400, 401, 403

### URL da rota: /product/decrease_amount/

Método do servidor: decreaseProductAmount(barcode, qtd)

Método HTTP: PATCH 

Body do Request: {user:{login, password}, data:{barcode, removeAmount}}

Possíveis códigos de status: 200, 400, 409, 401, 403

### URL da rota: /product/

Método do servidor: getProductsInfo()

Método HTTP: GET

Body do Response: JSON com o array de produtos no formato {{name, company, status, price}, ...}

Possíveis códigos de status: 200

OBS: se o produto tiver status "available" ele virá com o atributo price. Se tiver status "unavailable" ele virá sem o price.

### URL da rota: /product/report/

Método do servidor: getInventoryReport()

Método HTTP: GET

Body do Request: {login, password}

Body do Response: JSON com o array de produtos no formato {{name, company, status, price, lots:{{amount, shelflife}, ... }}, ...}

Possíveis códigos de status: 200, 401, 403


## Lots
### URL da rota: /product/lot/

Método do servidor: addLot(productName, shelfLife, qtd)

Método HTTP: POST

Body do Request: {user:{login, password}, data:{productbarcode, shelfLife, amount}}

Possíveis status codes: 201, 400, 401, 403


## Category
### URL da rota: /category/

Método do servidor: changeCategoryDiscount()

Método HTTP: PATCH

Body do Request: {user:{login, password}, data:{categoryClass, newDiscount}} 

Possíveis status HTTP: 200, 400, 401, 403


## User
### URL da rota: /user/create/

Método do servidor: addUser(nome, login, password)

Método HTTP: POST

Body do Request: {nome, login, pass}

Possíveis status HTTP: 201, 400, 409


### URL da rota: /user/

Método do servidor: checkRegistered(login)

Método HTTP: GET

Body do Request: {login}

Body do Response: Vazio, porém deve-se fazer uso do status da resposta (200 se o login estiver em uso e 404 se estiver disponível)

Possíveis status HTTP: 200, 404


### URL da rota: /user/login/

Método do servidor: login(login, password)

Método HTTP: GET

Body do Request: {login, password}

Body do Response: {name, login, password, isAdmin}

Possíveis status HTTP: 200, 400

