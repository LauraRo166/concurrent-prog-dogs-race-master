Escuela Colombiana de Ingeniería

Arquitecturas de Software – ARSW

## Integrantes
- Sergio Andrés Bejarano Rodríguez
- Laura Daniela Rodríguez Sánchez

---

## Taller – programación concurrente, condiciones de carrera y sincronización de hilos. EJERCICIO INDIVIDUAL O EN PAREJAS.

### Parte I – Antes de terminar la clase.

Creación, puesta en marcha y coordinación de hilos.

**1.** Revise el programa “primos concurrentes” (en la carpeta parte1), dispuesto en el paquete edu.eci.arsw.primefinder. Este es un programa que calcula los números primos entre dos intervalos, distribuyendo la búsqueda de los mismos entre hilos independientes. Por ahora, tiene un único hilo de ejecución que busca los primos entre 0 y 30.000.000. Ejecútelo, abra el administrador de procesos del sistema operativo, y verifique cuantos núcleos son usados por el mismo.

<img width="2879" height="1709" alt="image" src="https://github.com/user-attachments/assets/f72d0ead-4b00-4cbb-baa1-dfb58aba7868" />

Vemos que el porcentaje de uso de CPU es del 78%.

**2.** Modifique el programa para que, en lugar de resolver el problema con un solo hilo, lo haga con tres, donde cada uno de éstos hará la tarcera parte del problema original. Verifique nuevamente el funcionamiento, y nuevamente revise el uso de los núcleos del equipo.


<img width="2879" height="1711" alt="image" src="https://github.com/user-attachments/assets/4220a41a-ee0c-4ef5-95a7-559db349d7f4" />

En este caso, ahora vemos que el porcentaje de uso de CPU ha aumentado: es del 89%.

Podemos apreciar un aumento en el uso de núcleos al ejecutar el algoritmo de solución con tres hilos.

**3.** Lo que se le ha pedido es: debe modificar la aplicación de manera que cuando hayan transcurrido 5 segundos desde que se inició la ejecución, se detengan todos los hilos y se muestre el número de primos encontrados hasta el momento. Luego, se debe esperar a que el usuario presione ENTER para reanudar la ejecución de los mismo.

#### Explicación 

La sincronización mediante synchronized, junto con los métodos wait() y notifyAll(), es fundamental para coordinar correctamente la ejecución de múltiples hilos en esta aplicación. El uso de synchronized en los métodos pauseThread() y resumeThread() garantiza que solo un hilo a la vez pueda modificar la variable compartida paused, evitando condiciones de carrera. Por su parte, el método run() incluye un bloque synchronized que encierra el while (paused) y la llamada a wait(), ya que en Java es obligatorio invocar wait() solo cuando el hilo posee el candado del objeto (es decir, dentro de una sección sincronizada). Además, este patrón asegura que los hilos revisen y esperen sobre una condición (paused) de manera segura: si paused es true, el hilo se bloquea; si luego otro hilo ejecuta resumeThread() y llama a notifyAll(), el hilo en espera se despierta, vuelve a adquirir el candado y continúa ejecutando solo si la condición ha cambiado. Esta coordinación evita bloqueos indefinidos y permite pausar y reanudar los hilos correctamente durante la ejecución.

### Parte II 

**1.**  *Corrija la aplicación para que el aviso de resultados se muestre
    sólo cuando la ejecución de todos los hilos ‘galgo’ haya finalizado.*

Se implementa en la clase *MainCanodromo* en la acción del botón *start*, después de crear 
e iniciar los hilos:

    for (int i = 0; i < can.getNumCarriles(); i++) {
        try {
            galgos[i].join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

Con esto pasamos de este resultado donde se observa que el ganador es null:

<img width="980" height="914" alt="image" src="https://github.com/user-attachments/assets/b41c9cc3-2696-40fa-a821-af498847c7f5" />

A este resultado, donde ya se obtiene un galgo como ganador:

<img width="974" height="911" alt="image" src="https://github.com/user-attachments/assets/d8ef8b60-516a-4e7f-bbd8-88daf0438946" />

**2.**  *Una vez corregido el problema inicial, corra la aplicación varias
    veces, e identifique las inconsistencias en los resultados de las
    mismas viendo el ‘ranking’ mostrado en consola (algunas veces
    podrían salir resultados válidos, pero en otros se pueden presentar
    dichas inconsistencias). A partir de esto, identifique las regiones
    críticas () del programa.*
    
Después de correr la aplicación múltiples veces se observó que en los resultados varios galgos
obtenían la misma posición, incluyendo el puesto ganador. 

<img width="359" height="370" alt="image" src="https://github.com/user-attachments/assets/4c27e270-ae82-4a3c-b91c-c1f784b68d64" />

A partir de esto se identificaron las siguientes zonas críticas:

- En ***RegistroLlegada*** en el método ***setGanador***:

<img width="856" height="122" alt="image" src="https://github.com/user-attachments/assets/4eb5e66b-72fb-4357-b9f9-6721d865cfb9" />

- En ***RegistroLlegada*** en el método ***getUltimaPosicionAlcanzada***:
  
<img width="856" height="111" alt="image" src="https://github.com/user-attachments/assets/ec2b96b3-65f2-41be-82e7-4e1a72a159b5" />

- En ***RegistroLlegada*** en el método ***setUltimaPosicionAlcanzada***:

<img width="901" height="106" alt="image" src="https://github.com/user-attachments/assets/91a34067-2f5e-4f0f-8e19-5df60171cf5a" />


**3.**  *Utilice un mecanismo de sincronización para garantizar que a dichas
    regiones críticas sólo acceda un hilo a la vez. Verifique los
    resultados.*

Se implementa en los métodos de la clase ***RegistroLlegada***:

<img width="966" height="847" alt="image" src="https://github.com/user-attachments/assets/19b9c347-6bb6-446d-8511-67325abd44f2" />


**4.**  *Implemente las funcionalidades de pausa y continuar. Con estas,
    cuando se haga clic en ‘Stop’, todos los hilos de los galgos
    deberían dormirse, y cuando se haga clic en ‘Continue’ los mismos
    deberían despertarse y continuar con la carrera. Diseñe una solución que permita hacer esto 
    utilizando los mecanismos de sincronización con las primitivas de los Locks provistos por el 
    lenguaje (wait y notifyAll).*

En la clase ***Galgo*** se incluyen los métodos *pausar* y *continuar*, además se definen variables
estáticas, para que todos los Galgos compartan el mismo ***lock*** y la misma variable de ***pausado***:

    private static final Object lock = new Object();
    private static boolean pausado = false;

<img width="546" height="333" alt="image" src="https://github.com/user-attachments/assets/013034e2-d021-467b-a131-2712f3642d62" />

Además, se agrega en el método ***corra*** al iniciar el while de tal forma:

    synchronized (lock) {
        while (pausado){
            lock.wait();
        }
    }

En la clase ***MainCanodromo*** se implementa:

<img width="540" height="583" alt="image" src="https://github.com/user-attachments/assets/6f17d483-c643-49b5-919b-aadae4130211" />


Se realizan pruebas pausando y resumiendo repetidamente, se obtienen los siguientes resultados:

<img width="330" height="546" alt="image" src="https://github.com/user-attachments/assets/71a7e619-bf23-4b27-8706-847784dc01d6" />
