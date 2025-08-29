package arsw.threads;

public class RegistroLlegada {

	private static int ultimaPosicionAlcanzada=1;

	private String ganador=null;

    private final Object lock = new Object();

	public String getGanador() {
		return ganador;
	}

    // Región crítica si dos o más hilos entran al tiempo puede haber dos ganadores
    public void setGanador(String ganador) {
        synchronized (lock) {
            if (this.ganador == null) {
                this.ganador = ganador;
            }
        }
	}
    // Región crítica si dos o más hilos entran al tiempo devuelve la misma posicion
    public int getUltimaPosicionAlcanzada() {
        synchronized (lock) {
            return ultimaPosicionAlcanzada++;
        }
    }

    // Región crítica si dos o más hilos entran al tiempo establecem la misma posicion
	public void setUltimaPosicionAlcanzada(int ultimaPosicionAlcanzada) {
        synchronized (lock) {
            this.ultimaPosicionAlcanzada = ultimaPosicionAlcanzada;
        }
	}

	
	
}
