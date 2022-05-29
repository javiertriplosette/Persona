
package entregacommits;

import java.time.LocalDate;


public class EntregaCommits {

    public static void main(String[] args) {

        
    }
    
}
class Persona {

    private String nombre;
    private String apellidos;
    private Fecha fechaNacimiento;

    public Persona(String nombre, String apellidos, String fechaNacimiento) throws IllegalArgumentException {
        if ("".equals(nombre) || "".equals(apellidos) || "".equals(fechaNacimiento)) {
            throw new IllegalArgumentException();
        } else {
            this.nombre = nombre;
            this.apellidos = apellidos;
            Fecha fecha = new Fecha(fechaNacimiento);
            this.fechaNacimiento = fecha;
        }
    }

    public Persona(String nombre, String apellidos) {
        if ("".equals(nombre) || "".equals(apellidos)) {
            throw new IllegalArgumentException();
        } else {
            this.nombre = nombre;
            this.apellidos = apellidos;
            this.fechaNacimiento = null;
        }
    }

    public String getNombre() {
        return nombre;
    }

    public String getApellidos() {
        return apellidos;
    }

    public Fecha getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(String fechaNacimiento) throws IllegalArgumentException {
        if ("".equals(fechaNacimiento)) {
            throw new IllegalArgumentException();
        } else {
            Fecha nueva = new Fecha(fechaNacimiento);

            this.fechaNacimiento = nueva;
        }
    }

    public int getEdadEnFecha(Fecha fecha) {
        Fecha nacimiento = this.fechaNacimiento;
        int anyos = nacimiento.getAnyo();
        int resultado = 0;
        int bisiesto = 0;
        int dias = fecha.calcularDiasDiferenciaCon(nacimiento);

        if (Fecha.esAnioBisiesto(anyos)) {
            bisiesto = 1;
        } else {
            bisiesto = 0;
        }

        while (dias >= 365 + bisiesto) {
            if (Fecha.esAnioBisiesto(anyos)) {
                dias -= 366;
                bisiesto = 1;
            } else {
                dias -= 365;
                bisiesto = 0;
            }
            anyos++;
            resultado++;

        }
        if (dias < 0) {
            resultado = -1;

        }
        return resultado;
    }

    public int getEdad() {
        LocalDate fechaHoy = LocalDate.now();
        Fecha fecha = new Fecha(fechaHoy.getDayOfMonth(), fechaHoy.getMonthValue(), fechaHoy.getYear());
        int edad = this.getEdadEnFecha(fecha);
        return edad;
    }

}