**# GymApp

Descripción
-----------
Este proyecto es la app de gimnasio de nuestro equipo.  
Contiene un login local sencillo y una pantalla principal con botones para los siete días de la semana.

Contribuciones del equipo
-------------------------
- Arpan: Simplificación del login local y creación de HomeActivity con botones para cada día.  
  Rama: `feature/arpan/simple-login`
- Said: Diseño de UI primario y paleta de colores.
- Kevin: Configuración de Gradle y dependencias.
- Luz: Creación de pantallas de rutina y assets.

Instrucciones de uso
--------------------
1. Clonar el repositorio
2. Cambiar a la rama `segun contexto y poner nombre ej:Arpan en mi caso`
3. Abrir en Android Studio y sincronizar Gradle
4. Ejecutar en un emulador o dispositivo


se subio al master :

- Validación de campos de ingreso: Usuario = profesor / Contraseña = buentrabajo7
- Animación Lottie en pantalla de login
- Redirección a pantalla principal (`HomeActivity`)
- Preparado para escalar hacia funcionalidades de gimnasio: recuerden solo trabajar en el dia
correspondiente que les toco.
- byron: login y home.
- kevin: lunes y martes.
- luz: miercoles y jueves.
- Said: viernes y sabado.
atte Arpan/Byron Carrasco.

Cambio de ícono de la aplicación

Se actualizó el ícono de la app para personalizarla y diferenciarla de la versión por defecto de Android Studio.

Pasos realizados:

Se generó un Launcher Icon con Image Asset Studio (res > New > Image Asset).

Se agregó el nuevo ícono en las carpetas mipmap-....

Se actualizó el AndroidManifest.xml para usarlo:

en la rama feat/kevin/cambio_de_icono