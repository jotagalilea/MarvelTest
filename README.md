# MarvelTest

## Aplicación Android de prueba que obtiene una lista de personajes de Marvel y muestra su información.


Se ha definido con una arquitectura MVVM, con una actividad principal como contenedor de los 2 fragmentos que utiliza. Para pasar de uno a otro se ha utilizado el componente de navegación de Android.

Para realizar las peticiones a la API de Marvel se ha utilizado Retrofit: https://square.github.io/retrofit/. En el fragmento principal se muestra la lista de personajes, la cual se va ampliando según se desplaza la lista hacia abajo. En caso de que algo falle o no haya conexión a internet se muestra un mensaje simple de error. Para reintentar la carga basta con realizar un gesto de arrastrar la pantalla hacia abajo. Al pulsar sobre uno de los ítems se abre una pantalla con el detalle del elemento seleccionado.

Para la carga de imágenes de los personajes se ha utilizado la librería Picasso: https://square.github.io/picasso/. Se ha tenido que especificar en el archivo de configuración "network_security_config.xml" que se requiere que el tráfico vaya en texto plano para el dominio que alberga las imágenes.

Para explicar el resto creo más conveniente hacerlo con los comentarios que he dejado en el código, los cuales están en inglés.
