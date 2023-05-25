# MOWEBS-MOBILE
Tugas Mobile (Aplikasi Rental Mobil) dan penggunaan API untuk Database. 

## API

### Mobil
- **(GET)** GetMobilById
	- Endpoint : https://ap-southeast-1.aws.data.mongodb-api.com/app/rentalmobil-qrwuq/endpoint/get_mobil_byid
	- Parameter
		`id`
	- Response 
		```json
		{
			"_id" : ObjectId
			"deskripsi": String
			"harga": String,
			"jenis": String,
			"merk": String,
			"plat": String,
			"reviews":[{"_id":String,"comments":String,"date":String,"rate": int}],
			"spesifikasi":{"akselerasi": String,"fuel": String,"kursi": String,"transmisi": String,"warna": String},
			"url_gambar": String
		}
		```
		
- **(GET)** GetAllMobil
	- Endpoint : https://ap-southeast-1.aws.data.mongodb-api.com/app/rentalmobil-qrwuq/endpoint/get_all_mobil
	- Response
		```json
		[
			{
				"_id" : ObjectId
				"deskripsi": String
				"harga": String,
				"jenis": String,
				"merk": String,
				"plat": String,
				"reviews":[{"_id":String,"comments":String,"date":String,"rate": int}],
				"spesifikasi":{"akselerasi": String,"fuel": String,"kursi": String,"transmisi": String,"warna": String},
				"url_gambar": String
			}, {More data :)}
		]
		```
	
- **(POST)** PostMobil 
	- Endpoint : https://ap-southeast-1.aws.data.mongodb-api.com/app/rentalmobil-qrwuq/endpoint/insertmobil
	- Request Body 
		```json
		{
			"deskripsi": String
			"harga": String,
			"jenis": String,
			"merk": String,
			"plat": String,
			"spesifikasi":{"akselerasi": String,"fuel": String,"kursi": String,"transmisi": String,"warna": String},
			"url_gambar": String
		}
		```

---
### User
- **(POST)** Validate/Login
	- Endpoint : https://ap-southeast-1.aws.data.mongodb-api.com/app/rentalmobil-qrwuq/endpoint/login
	- Parameter 
		`username`
		`password`
- **(POST)** Register
	- Endpoint : https://ap-southeast-1.aws.data.mongodb-api.com/app/rentalmobil-qrwuq/endpoint/register
	- Parameter 
		`username`
		`password`
		`email`

---
### Profile
- **(GET)** GetProfileByUserId
	- Endpoint : https://ap-southeast-1.aws.data.mongodb-api.com/app/rentalmobil-qrwuq/endpoint/getprofilebyuserid
	- Parameter
		`uid`
	
- **(POST)** UpdateProfile 
	- Endpoint https://ap-southeast-1.aws.data.mongodb-api.com/app/rentalmobil-qrwuq/endpoint/update_profile
	- Parameter
		`uid`
	- Request Body
		```json
		{
			"nama": String,
			"alamat": String,
			"no_hp": String,
			"email": String,
			"jenis_kelamin": String,
			"no_ktp": String
		}
		```

---
### Chat
- **(GET)** GetChatByUser 
	- Endpoint : https://ap-southeast-1.aws.data.mongodb-api.com/app/rentalmobil-qrwuq/endpoint/getchats 
	- Parameter
		`uid`

- **(POST)** PostChat
	- Endpoint : https://ap-southeast-1.aws.data.mongodb-api.com/app/rentalmobil-qrwuq/endpoint/insertchat
	- Parameter 
		`uid`
	- Request Body
		```json
		{
			"value": string,
			"from": "CUSTOMER"/ "CS",
			"date": int, //epoch (milisecond)
			"isupdate": boolean
		}
		```
		
- **(POST)** UpdateChat
	- Endpoint : https://ap-southeast-1.aws.data.mongodb-api.com/app/rentalmobil-qrwuq/endpoint/updatechat
	- Parameter 
		`id (chatId)`
	- Request Body
		```json
		{
			"value": string,
			"isupdate": true
		}
		```

---
### Sewa 
- **(GET)** GetSewaByID
	- Endpoint : https://ap-southeast-1.aws.data.mongodb-api.com/app/rentalmobil-qrwuq/endpoint/findsewabyid
	- Parameter
		`sewaid`
		
- **(GET)** GetSewa
	- Endpoint : https://ap-southeast-1.aws.data.mongodb-api.com/app/rentalmobil-qrwuq/endpoint/findsewa
	- Parameter 
		`uid`
		
- **(POST)** PostSewa
	- Endpoint : https://ap-southeast-1.aws.data.mongodb-api.com/app/rentalmobil-qrwuq/endpoint/insertsewa
	- Parameter
		`uid`
		`mid`
	- Request Body
		```json
		{
			"durasi": 5,
			"total_harga": 7500000,
			"status":"DITERIMA", "DIBATALKAN", "DIPROSES", "SELESAI"
			"w_pengembalian": "1682979739999",
			"w_pembayaran": "1682173010999",
			"w_pemesanan": "1682573010999"
		}
		```
		
- **(POST)** UpdateStatusSewa
	- Endpoint :
	- Parameter
		`_id`
		`status`
		

### Review
- **(POST)** PostReview
	- Endpoint : https://ap-southeast-1.aws.data.mongodb-api.com/app/rentalmobil-qrwuq/endpoint/insertreview
	- Parameter
		`uid userid`
		`mid mobilid`
	- Request Body
		```json
		{
			"rate": int,
			"date": int,
			"comments": string,
		}
		```

### User
- **(GET)** GetUsername
	- Endpoint : https://ap-southeast-1.aws.data.mongodb-api.com/app/rentalmobil-qrwuq/endpoint/findusernamebyid
	-Parameter
		`uid`