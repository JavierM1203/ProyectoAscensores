# ProyectoAscensores
El paquete correcto es el que esta en la ruta ProyectoAscensores/src/main/java/com/mycompany/correcto  
El ascensor toma un solo pedido a la vez.  
El ascensor no evalua si hay solicitudes mientras se esta moviendo.  
No se evalua ni la cantidad de personas ni el peso  
La propiedad destinossSolicitados debería esta en el ascensor y no en el controlador. Genera problemas al tener más de un ascensor. Se debe rediseñar el codigo de la clase Controlador en los métodos atenderPedido y generarPedido para poder cambiar la propiedad al ascensor.
