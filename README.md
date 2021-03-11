# IntroduccionADockerYAWS

# Daniel Vargas Ordoñez

# AREP-LAB5

## Descripción:

Se construira una aplicación con la arquitectura propuesta y desplegarla en AWS usando EC2 y Docker.

## Arquitectura:

![image](https://user-images.githubusercontent.com/50029247/111857545-3f4dca00-8900-11eb-8659-bd5e672189c6.png)

- El servicio MongoDB es una instancia de MongoDB corriendo en un container de docker en una máquina virtual de EC2:

  - Para esto debemos instalar mongoDB. 
  
  ![image](https://user-images.githubusercontent.com/50029247/111857664-f8ac9f80-8900-11eb-99d9-d0608b711794.png)
  - Después crear la base de datos, una colección e insertar un dato para este laboratorio. Para esto debemos tener docker previamente instalado.
  
  ![image](https://user-images.githubusercontent.com/50029247/111857769-c8193580-8901-11eb-8ca9-9dd0e1c05e86.png)
  - Seguido a esto creamos la imagen en docker
  
  ![image](https://user-images.githubusercontent.com/50029247/111857702-432e1c00-8901-11eb-9ecf-5fa36bdde7f2.png)
  - Aparecera algo parecido a esto: 
  
  ![image](https://user-images.githubusercontent.com/50029247/111857898-b8e6b780-8902-11eb-9cac-b78874f129f0.png)
  - Despues la montamos en AWS educate, en EC2 y nos conectamos a través de SSH
  
  ![image](https://user-images.githubusercontent.com/50029247/111858128-7faf4700-8904-11eb-970b-0013fdf6fc5c.png)
  
  ![image](https://user-images.githubusercontent.com/50029247/111858138-935aad80-8904-11eb-9d86-1dd3f9ad5680.png)
  
  ![image](https://user-images.githubusercontent.com/50029247/111858141-9d7cac00-8904-11eb-9eea-a64b21b982c0.png)

- LogService es un servicio REST que recibe una cadena, la almacena en la base de datos y responde en un objeto JSON con las 10 ultimas cadenas almacenadas en la base de datos y la fecha en que fueron almacenadas. (Para este servicio se creo una aplicación maven y se repiten los pasos del punto anterior en docker y aws, por ende se omitiran)
  - Primero creamos la clase donde nos conectamos a la base de datos en aws de mongo.
  
  ![image](https://user-images.githubusercontent.com/50029247/111858265-97d39600-8905-11eb-8f32-f14ace88fef6.png)
  - Con sus respectivos metodos get y add

  ![image](https://user-images.githubusercontent.com/50029247/111858282-b20d7400-8905-11eb-85c2-55208cb5083f.png)

  ![image](https://user-images.githubusercontent.com/50029247/111858286-bc2f7280-8905-11eb-907b-936ebe24dff2.png)

  - Y la clase sparkWeb donde estan los metodos get y post:
  
  ![image](https://user-images.githubusercontent.com/50029247/111858308-f0a32e80-8905-11eb-886d-463f0e827b46.png)
  
  - De esta imagen se crearon tres contenedores en docker para asi cumplir con lo requerido

- La aplicación web APP-LB-RoundRobin está compuesta por un cliente web y al menos un servicio REST. El cliente web tiene un campo y un botón y cada vez que el usuario envía un mensaje, este se lo envía al servicio REST y actualiza la pantalla con la información que este le regresa en formato JSON. El servicio REST recibe la cadena e implementa un algoritmo de balanceo de cargas de Round Robin, delegando el procesamiento del mensaje y el retorno de la respuesta a cada una de las tres instancias del servicio LogService.