# CarDealership

database - mysql
backend - spring boot, spring security
frontend - vuejs

Endpoints
	Accounts
		POST	/api/auth/signup		signup new account
		POST	/api/auth/signin		login an account
		POST	/api/auth/signout		logout the account
		POST	/api/auth/refreshtoken	refresh expired JWT

	Cars:
		Basic CRUD:
		POST	/api/cars/new		new car
		GET		/api/cars/$id		get car with given id
		POST	/api/cars/edit 		edit existing car
		DELETE  /api/cars/$id		delete car

		GET		/api/cars			get all cars
		GET		/api/cars/$region	get all cars from given region