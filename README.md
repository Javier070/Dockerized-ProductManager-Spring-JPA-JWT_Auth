# com.jm.tfg
- para que me devuelva el token tiene que estar con status true 

- el jwtFilter.isAdmin() responde status 500 cuando no has ingresado ningún usuario
si quieres recibir el status 401 le tienes que pasar 
un user con: el status "true" y el rol "user"


---
Busca las partes del codigo donde
se encuentre un //todo (eso son cosas que faltan por hacer)

Cuando el programa da error al hacer isAdmin() puede que se
dena a que el token esta vacio, gestiona este error para que
devuelva un mensaje explicando la situacion