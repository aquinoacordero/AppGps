# AppGps:
Este es un programa para Android que permite por medio de geolocalizacion posicinar un marcador en el mapa mundi en funcion de donde hayamos situado las coordenadas.
Estas pueden ser introducidas:
* Manualmente por medio del teclado.
 * Para lo que se usara enterText y la funcion sendText
 * Se empleara un intent que pasara las coordenadas al mapa
 * El acceso al mapa se hara por medio de un boton y el intent anteriormente mencionado
* A traves de reconocimiento de voz.
 * Se hara uso de la funcion speech que es una appi de reconocimiento de voz
 * Cada dato recogido por voz sera guardado como longitud o latitud en uncion del usuario.
 * El acceso al mapa es por medio de un intent como en el caso anterior

Esta compuesta de tres Activitys:
* Una que mostrará el mapa 
* Y dos de llamada:
  * Voz
  * Manual
