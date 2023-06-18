# ProyectoAscensores
Diagrama: https://correoucuedu-my.sharepoint.com/:u:/g/personal/javier_moreno_correo_ucu_edu_uy/EZJNr95sqadOnpoDGTO_HhMBm-Wz_EAxKRUrbCaXzsEbBA?e=sPA6rx  
  
El paquete correcto es el que esta en la ruta ProyectoAscensores/src/main/java/Entregafinal2  
El ascensor toma un solo pedido a la vez. -Solved  
El ascensor no evalua si hay solicitudes mientras se esta moviendo. -Solved  
No se evalua ni la cantidad de personas ni el peso. - Partially solved. Evalua cantidad de personas. Suficiente segun profesor  
La propiedad destinossSolicitados debería estar en el ascensor y no en el controlador. Genera problemas al tener más de un ascensor. Se debe rediseñar el codigo de la clase Controlador en los métodos atenderPedido y generarPedido para poder cambiar la propiedad al ascensor. -Codigo rediseñado. Problema sigue presente.  
