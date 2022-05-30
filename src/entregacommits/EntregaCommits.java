
package entregacommits;

import java.time.LocalDate;
import java.util.Scanner;


public class EntregaCommits {

    public static void main(String[] args) {
   Scanner numeros = new Scanner(System.in);
        int personas = 0;
        String nombre = "javier";
        String apellidos = "Materazzi";
        String fechaNacimiento = "07/02/2001";
        int cantidadFechas = 0;
        Persona persona = new Persona(nombre, apellidos, fechaNacimiento);

        Fecha fecha;

        personas = numeros.nextInt();
        numeros.nextLine();
        boolean continuar = true;

        for (int i = 0; i < personas; i++) {
            nombre = numeros.nextLine();
            apellidos = numeros.nextLine();
            fechaNacimiento = numeros.nextLine();
            try {
                persona = new Persona(nombre, apellidos, fechaNacimiento);
                        
                        if ( persona.getEdad() >= 0) {
                            System.out.println(persona.getNombre() + " " + persona.getApellidos() + " tiene " + persona.getEdad() + " anyos a dia de hoy");

                        } else {
                            System.out.println(persona.getNombre() + " " + persona.getApellidos() + "aun no ha nacido a dia de hoy");

                        }
                    
                
            } catch (IllegalArgumentException L) {
                System.out.println("ERROR. Procesando siguiente persona");
            }

        }
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

class Fecha implements Comparable<Fecha> {

    private int dia;
    private int mes;
    private int anyo;

    public Fecha(int dia, int mes, int anyo) {
        if (!esFechaCorrecta(dia, mes, anyo)) {
            throw new IllegalArgumentException();
        } else {
            this.dia = dia;
            this.mes = mes;
            this.anyo = anyo;
        }
    }

    public Fecha(String fecha) {
        if (!fecha.matches("[0-9]{2}[-/][0-9]{2}[-/][0-9]{4}")) {
            throw new IllegalArgumentException();
        } else {
            int dia = Integer.parseInt(fecha.substring(0, 2));
            int mes = Integer.parseInt(fecha.substring(3, 5));
            int anyo = Integer.parseInt(fecha.substring(6, 10));
            if (!esFechaCorrecta(dia, mes, anyo)) {
                throw new IllegalArgumentException();
            } else {
                this.dia = dia;
                this.mes = mes;
                this.anyo = anyo;
            }

        }
    }

    public Fecha(Fecha fecha) {
        this.dia = fecha.getDia();
        this.mes = fecha.getMes();
        this.anyo = fecha.anyo;
    }

    public Fecha restarAnios(int aniosMenos) {
        int primerAnyo = this.anyo;
        int primerDia = this.dia;
        int sumaDias = this.dia;
        int sumaMeses = this.mes;
        int sumaAnyos = this.anyo - aniosMenos;

        if (!esAnioBisiesto(sumaAnyos) && esAnioBisiesto(primerAnyo) && sumaMeses == 2 && primerDia == 29) {
            sumaDias = 28;
        } else if (esAnioBisiesto(sumaAnyos) && !esAnioBisiesto(primerAnyo) && sumaMeses == 2 && primerDia == 28) {
            sumaDias = 29;
        } else {
            sumaDias = sumaDias;
        }

        Fecha fecha = new Fecha(sumaDias, sumaMeses, sumaAnyos);
        return fecha;
    }

    public Fecha sumarAnios(int aniosExtra) {
        if (aniosExtra < 0) {
            throw new IllegalArgumentException();
        } else {
            int primerAnyo = this.anyo;
            int primerDia = this.dia;
            int sumaDias = this.dia;
            int sumaMeses = this.mes;
            int sumaAnyos = this.anyo + aniosExtra;
            int[] meses = {0, 31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};

            if (!esAnioBisiesto(sumaAnyos) && esAnioBisiesto(primerAnyo) && sumaMeses == 2 && primerDia == 29) {
                sumaDias = 28;
            } else if (esAnioBisiesto(sumaAnyos) && !esAnioBisiesto(primerAnyo) && sumaMeses == 2 && primerDia == 28) {
                sumaDias = 29;
            } else {
                sumaDias = sumaDias;
            }

            Fecha fecha = new Fecha(sumaDias, sumaMeses, sumaAnyos);
            return fecha;
        }
    }

    public Fecha restarMeses(int mesesMenos) {
        int primerMes = this.mes;
        int sumaDias = this.dia;
        int sumaMeses = this.mes - mesesMenos;
        int sumaAnyos = this.anyo;

        int[] meses = {0, 31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};

        while (sumaMeses <= 0) {
            sumaAnyos--;
            sumaMeses = 12 + sumaMeses;
        }
        int ultimoAnyo = sumaAnyos;
        int ultimoMes = sumaMeses;

        int bisiestoUltimo = 0;
        if (esAnioBisiesto(ultimoAnyo)) {
            bisiestoUltimo = 1;
        } else {
            bisiestoUltimo = 0;
        }

        if (sumaDias == meses[primerMes] || sumaDias > (meses[sumaMeses] + bisiestoUltimo)) {
            sumaDias = meses[sumaMeses];
        }

        if (esAnioBisiesto(ultimoAnyo) && sumaMeses == 2 && sumaDias == 28) {
            bisiestoUltimo = 1;
            sumaDias += 1;
        } else if (!esAnioBisiesto(ultimoAnyo) && sumaMeses == 2 && sumaDias == 29) {

        } else {
            bisiestoUltimo = 0;
            sumaDias = sumaDias;
        }

        Fecha fecha = new Fecha(sumaDias, sumaMeses, sumaAnyos);
        return fecha;
    }

    public Fecha sumarMeses(int mesesExtra) {
        int[] meses = {0, 31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};
        int primerMes = this.mes;
        int sumaDias = this.dia;
        int sumaMeses = this.mes + mesesExtra;
        int sumaAnyos = this.anyo;

        int resto = sumaMeses % 12;

        while (sumaMeses > 12) {

            sumaAnyos++;
            sumaMeses = sumaMeses - 12;

        }
        int ultimoAnyo = sumaAnyos;
        int ultimoMes = sumaMeses;

        int bisiestoUltimo = 0;

        if (esAnioBisiesto(ultimoAnyo)) {
            bisiestoUltimo = 1;
        } else {
            bisiestoUltimo = 0;
        }

        if (sumaDias == meses[primerMes] || sumaDias > (meses[resto] + bisiestoUltimo)) {
            sumaDias = meses[resto];
        }

        if (esAnioBisiesto(ultimoAnyo) && sumaMeses == 2 && sumaDias == 28) {
            bisiestoUltimo = 1;
            sumaDias = 29;
        } else if (!esAnioBisiesto(ultimoAnyo) && sumaMeses == 2 && sumaDias == 29) {
            sumaDias = 28;
        } else {
            bisiestoUltimo = 0;
            sumaDias = sumaDias;
        }

        Fecha fecha = new Fecha(sumaDias, sumaMeses, sumaAnyos);
        return fecha;

    }

    public Fecha sumarDias(int diasExtra) {
        int[] meses = {0, 31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};
        int sumaDias = this.getDiaDelAnyo() + diasExtra;
        int sumaMeses = 0;
        int sumaAnyos = this.anyo;

        int bisiesto = 0;
        if (esAnioBisiesto(sumaAnyos)) {
            bisiesto = 1;
        } else {
            bisiesto = 0;
        }

        while (sumaDias > 365 + bisiesto) {

            if (esAnioBisiesto(sumaAnyos)) {
                sumaDias -= 366;
            } else {
                sumaDias -= 365;
            }
            sumaAnyos += 1;
            if (esAnioBisiesto(sumaAnyos)) {
                bisiesto = 1;
            } else {
                bisiesto = 0;
            }

        }

        if (sumaDias > 0 && sumaDias <= 31) {
            sumaMeses = 1;
            sumaDias = sumaDias - 0;
        } else if (sumaDias > 31 && sumaDias <= 59 + bisiesto) {
            sumaMeses = 2;
            sumaDias = sumaDias - 31;
        } else if (sumaDias > 59 + bisiesto && sumaDias <= 90 + bisiesto) {
            sumaMeses = 3;
            sumaDias -= (59 + bisiesto);
        } else if (sumaDias > 90 + bisiesto && sumaDias <= 120 + bisiesto) {
            sumaMeses = 4;
            sumaDias -= (90 + bisiesto);
        } else if (sumaDias > 120 + bisiesto && sumaDias <= 151 + bisiesto) {
            sumaMeses = 5;
            sumaDias -= (120 + bisiesto);
        } else if (sumaDias > 151 + bisiesto & sumaDias <= 181 + bisiesto) {
            sumaMeses = 6;
            sumaDias -= (151 + bisiesto);
        } else if (sumaDias > 181 + bisiesto && sumaDias <= 212 + bisiesto) {
            sumaMeses = 7;
            sumaDias -= (181 + bisiesto);
        } else if (sumaDias > 212 + bisiesto && sumaDias <= 243 + bisiesto) {
            sumaMeses = 8;
            sumaDias -= (212 + bisiesto);
        } else if (sumaDias > 243 + bisiesto && sumaDias <= 273 + bisiesto) {
            sumaMeses = 9;
            sumaDias -= (243 + bisiesto);
        } else if (sumaDias > 273 + bisiesto && sumaDias <= 304 + bisiesto) {
            sumaMeses = 10;
            sumaDias -= (273 + bisiesto);
        } else if (sumaDias > 304 + bisiesto && sumaDias <= 334 + bisiesto) {
            sumaMeses = 11;
            sumaDias -= (304 + bisiesto);
        } else if (sumaDias > 334 + bisiesto && sumaDias <= 365 + bisiesto) {
            sumaMeses = 12;
            sumaDias -= (334 + bisiesto);
        }
        Fecha fecha = new Fecha(sumaDias, sumaMeses, sumaAnyos);
        return fecha;

    }

    public Fecha restarDias(int diasMenos) {
        int sumaDias = this.getDiaDelAnyo() - diasMenos;
        int sumaMeses = 0;
        int totalAnyos = this.anyo;
        int bisiesto = 0;

        while (sumaDias <= 0) {
            totalAnyos -= 1;

            if (esAnioBisiesto(totalAnyos)) {
                bisiesto = 1;
            } else {
                bisiesto = 0;
            }
            if (sumaDias < 0) {
                sumaDias = (365 + bisiesto) + sumaDias;

            } else if (sumaDias == 0) {
                if (esAnioBisiesto(totalAnyos)) {
                    sumaDias = (366);
                } else {

                    sumaDias = (365);

                }

            }

        }
        if (sumaDias > 0 && sumaDias <= 31) {
            sumaMeses = 1;
            sumaDias = sumaDias - 0;
        } else if (sumaDias > 31 && sumaDias <= 59 + bisiesto) {
            sumaMeses = 2;
            sumaDias = sumaDias - 31;
        } else if (sumaDias > 59 + bisiesto && sumaDias <= 90 + bisiesto) {
            sumaMeses = 3;
            sumaDias -= (59 + bisiesto);
        } else if (sumaDias > 90 + bisiesto && sumaDias <= 120 + bisiesto) {
            sumaMeses = 4;
            sumaDias -= (90 + bisiesto);
        } else if (sumaDias > 120 + bisiesto && sumaDias <= 151 + bisiesto) {
            sumaMeses = 5;
            sumaDias -= (120 + bisiesto);
        } else if (sumaDias > 151 + bisiesto & sumaDias <= 181 + bisiesto) {
            sumaMeses = 6;
            sumaDias -= (151 + bisiesto);
        } else if (sumaDias > 181 + bisiesto && sumaDias <= 212 + bisiesto) {
            sumaMeses = 7;
            sumaDias -= (181 + bisiesto);
        } else if (sumaDias > 212 + bisiesto && sumaDias <= 243 + bisiesto) {
            sumaMeses = 8;
            sumaDias -= (212 + bisiesto);
        } else if (sumaDias > 243 + bisiesto && sumaDias <= 273 + bisiesto) {
            sumaMeses = 9;
            sumaDias -= (243 + bisiesto);
        } else if (sumaDias > 273 + bisiesto && sumaDias <= 304 + bisiesto) {
            sumaMeses = 10;
            sumaDias -= (273 + bisiesto);
        } else if (sumaDias > 304 + bisiesto && sumaDias <= 334 + bisiesto) {
            sumaMeses = 11;
            sumaDias -= (304 + bisiesto);
        } else if (sumaDias > 334 + bisiesto && sumaDias <= 365 + bisiesto) {
            sumaMeses = 12;
            sumaDias -= (334 + bisiesto);
        }
        Fecha fecha = new Fecha(sumaDias, sumaMeses, totalAnyos);
        return fecha;
    }

    public int calcularDiasDiferenciaCon(Fecha fechaComparada) {
        int resultado = 0;
        int sumaDias = 0;
        if (this.anyo == fechaComparada.getAnyo()) {
            if (this.getDiaDelAnyo() == fechaComparada.getDiaDelAnyo()) {
                sumaDias = 0;
            } else if (this.getDiaDelAnyo() > fechaComparada.getDiaDelAnyo()) {
                sumaDias = this.getDiaDelAnyo() - fechaComparada.getDiaDelAnyo();
            } else {
                sumaDias = fechaComparada.getDiaDelAnyo() - this.getDiaDelAnyo();
                sumaDias = (sumaDias * -1);
            }
        } else if (this.anyo > fechaComparada.getAnyo()) {
            if (this.getDiaDelAnyo() >= fechaComparada.getDiaDelAnyo()) {
                if (esAnioBisiesto(fechaComparada.getAnyo())) {
                    sumaDias = 366 - fechaComparada.getDiaDelAnyo();
                } else {
                    sumaDias = 365 - fechaComparada.getDiaDelAnyo();
                }
                sumaDias += this.getDiaDelAnyo();
                for (int i = fechaComparada.getAnyo() + 1; i < this.anyo; i++) {
                    if (esAnioBisiesto(i)) {
                        sumaDias += 366;
                    } else {
                        sumaDias += 365;
                    }
                }

            } else if (this.getDiaDelAnyo() < fechaComparada.getDiaDelAnyo()) {
                if (esAnioBisiesto(fechaComparada.getAnyo())) {
                    sumaDias = 366 - fechaComparada.getDiaDelAnyo();
                } else {
                    sumaDias = 365 - fechaComparada.getDiaDelAnyo();
                }
                sumaDias += this.getDiaDelAnyo();

                for (int i = fechaComparada.getAnyo() + 1; i < this.anyo; i++) {
                    if (esAnioBisiesto(i)) {
                        sumaDias += 366;
                    } else if (!esAnioBisiesto(i)) {
                        sumaDias += 365;
                    }

                }

            }

        } else if (this.anyo < fechaComparada.getAnyo()) {
            if (this.getDiaDelAnyo() <= fechaComparada.getDiaDelAnyo()) {
                if (esAnioBisiesto(this.anyo)) {
                    sumaDias = 366 - this.getDiaDelAnyo();
                } else {
                    sumaDias = 365 - this.getDiaDelAnyo();
                }
                sumaDias += fechaComparada.getDiaDelAnyo();
                sumaDias = -1 * sumaDias;
                for (int i = this.anyo + 1; i < fechaComparada.getAnyo(); i++) {
                    if (esAnioBisiesto(i)) {
                        sumaDias -= 366;
                    } else {
                        sumaDias -= 365;
                    }

                }

            } else if (this.getDiaDelAnyo() > fechaComparada.getDiaDelAnyo()) {
                if (esAnioBisiesto(this.anyo)) {
                    sumaDias = 366 - this.getDiaDelAnyo();
                } else {
                    sumaDias = 365 - this.getDiaDelAnyo();
                }
                sumaDias += fechaComparada.getDiaDelAnyo();
                sumaDias = -1 * sumaDias;
                for (int i = this.anyo + 1; i < fechaComparada.getAnyo(); i++) {
                    if (esAnioBisiesto(i)) {
                        sumaDias -= 366;
                    } else if (!esAnioBisiesto(i)) {
                        sumaDias -= 365;
                    }

                }

            }

        }
        resultado = sumaDias;

        return resultado;
    }

    public int getDiaDelAnyo() {
        int dias = 0;
        boolean bisiesto = false;

        int[] meses = {31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};
        if (!this.esAnioBisiesto()) {
            for (int i = 0; i < this.mes - 1; i++) {
                dias += meses[i];
            }
            dias += this.dia;
        } else {

            for (int i = 0; i < this.mes - 1; i++) {
                dias += meses[i];
                if (i == 1) {
                    bisiesto = true;
                }
            }
            if (bisiesto) {
                dias += 1;
            }
            dias += this.dia;
        }
        return dias;
    }

    public String getDiaSemana() {
        String semana[] = {"domingo", "lunes", "martes", "miercoles", "jueves", "viernes", "sabado"};
        return semana[this.getNumeroDiaSemana()];
    }

    private int getNumeroDiaSemana() {
        int[] calcularMes = {6, 2, 2, 5, 0, 3, 5, 1, 4, 6, 2, 4};
        int siglo = 0;
        int anio;
        int bisiesto;
        int mes_ = calcularMes[this.mes - 1];
        int dia_ = this.dia;
        int i;
        if (this.anyo >= 2000) {
            siglo = 0;
            i = this.anyo - 2000;
            while (i > 100) {
                i = i - 100;
                siglo = siglo - 2;
            }
        } else {
            siglo = 1;
            i = 2000 - this.anyo;
            while (i > 100) {
                i = i - 100;
                siglo = siglo + 2;
            }
        }
        int j;
        anio = Math.floorDiv(this.anyo, 100) * 100;
        anio = this.anyo - anio;
        j = Math.floorDiv(anio, 4);
        anio = anio + j;

        if (esAnioBisiesto(this.anyo) && (this.mes == 1 || this.mes == 2)) {
            bisiesto = -1;
        } else {
            bisiesto = 0;
        }
        int resultado = (siglo + anio + bisiesto + mes_ + dia_) % 7;
        return resultado;
    }

    public String getMesNombre() {
        String[] mes = {"enero", "febrero", "marzo", "abril", "mayo", "junio", "julio", "agosto", "septiembre", "octubre", "noviembre", "diciembre"};
        return mes[this.mes - 1];

    }

    private boolean esFechaCorrecta(int dia, int mes, int anyo) {
        boolean diaCorrecto = false;
        int febrero = 28;
        switch (mes) {
            case 1:
                if (dia > 0 && dia < 32) {
                    diaCorrecto = true;
                } else {
                    diaCorrecto = false;
                }
                break;
            case 2:
                if (Fecha.esAnioBisiesto(anyo)) {
                    febrero += 1;
                }
                if (dia > 0 && dia < (febrero + 1)) {
                    diaCorrecto = true;
                } else {
                    diaCorrecto = false;
                }
                break;
            case 3:
                if (dia > 0 && dia < 32) {
                    diaCorrecto = true;
                }
                break;
            case 4:
                if (dia > 0 && dia < 31) {
                    diaCorrecto = true;
                } else {
                    diaCorrecto = false;
                }
                break;
            case 5:
                if (dia > 0 && dia < 32) {
                    diaCorrecto = true;
                } else {
                    diaCorrecto = false;
                }
                break;
            case 6:
                if (dia > 0 && dia < 31) {
                    diaCorrecto = true;
                } else {
                    diaCorrecto = false;
                }
                break;
            case 7:
                if (dia > 0 && dia < 31) {
                    diaCorrecto = true;
                } else {
                    diaCorrecto = false;
                }
                break;
            case 8:
                if (dia > 0 && dia < 32) {
                    diaCorrecto = true;
                } else {
                    diaCorrecto = false;

                }
                break;
            case 9:
                if (dia > 0 && dia < 31) {
                    diaCorrecto = true;
                } else {
                    diaCorrecto = false;
                }
                break;
            case 10:
                if (dia > 0 && dia < 32) {
                    diaCorrecto = true;
                } else {
                    diaCorrecto = false;
                }
                break;
            case 11:
                if (dia > 0 && dia < 31) {
                    diaCorrecto = true;
                } else {
                    diaCorrecto = false;
                }
                break;
            case 12:
                if (dia > 0 && dia < 32) {
                    diaCorrecto = true;
                } else {
                    diaCorrecto = false;
                }
                break;
        }
        if (diaCorrecto && anyo > 0) {
            diaCorrecto = true;
        } else {
            diaCorrecto = false;
        }
        return diaCorrecto;
    }

    public boolean esAnioBisiesto() {
        boolean esDivisibleEntre4;
        boolean esDivisibleEntre400;
        boolean esDivisibleEntre100;
        boolean bisiesto = false;

        if (this.anyo % 4 == 0) {
            esDivisibleEntre4 = true;
        } else {
            esDivisibleEntre4 = false;
        }
        if (this.anyo % 100 == 0) {
            esDivisibleEntre100 = true;
        } else {
            esDivisibleEntre100 = false;
        }
        if (this.anyo % 400 == 0) {
            esDivisibleEntre400 = true;
        } else {
            esDivisibleEntre400 = false;
        }
        if (esDivisibleEntre4) {
            if (!esDivisibleEntre100) {
                bisiesto = true;
            } else {
                if (esDivisibleEntre400) {
                    bisiesto = true;
                } else {
                    bisiesto = false;
                }
            }
        } else {
            bisiesto = false;
        }
        return bisiesto;
    }

    public static boolean esAnioBisiesto(int anyo) {
        boolean esDivisibleEntre4;
        boolean esDivisibleEntre400;
        boolean esDivisibleEntre100;
        boolean bisiesto = false;

        if (anyo % 4 == 0) {
            esDivisibleEntre4 = true;
        } else {
            esDivisibleEntre4 = false;
        }
        if (anyo % 100 == 0) {
            esDivisibleEntre100 = true;
        } else {
            esDivisibleEntre100 = false;
        }
        if (anyo % 400 == 0) {
            esDivisibleEntre400 = true;
        } else {
            esDivisibleEntre400 = false;
        }
        if (esDivisibleEntre4) {
            if (!esDivisibleEntre100) {
                bisiesto = true;
            } else {
                if (esDivisibleEntre400) {
                    bisiesto = true;
                } else {
                    bisiesto = false;
                }
            }
        } else {
            bisiesto = false;
        }
        return bisiesto;
    }

    public int getDia() {
        return dia;
    }

    public int getMes() {
        return mes;
    }

    public int getAnyo() {
        return anyo;
    }

    public void setDia(int dia) {
        if (!esFechaCorrecta(dia, this.mes, this.anyo)) {
            throw new IllegalArgumentException();
        } else {
            this.dia = dia;
        }
    }

    public void setMes(int mes) {
        if (!esFechaCorrecta(this.dia, mes, this.anyo)) {
            throw new IllegalArgumentException();
        } else {
            this.mes = mes;
        }
    }

    public void setAnyo(int anyo) {
        if (!esFechaCorrecta(this.dia, this.mes, anyo)) {
            throw new IllegalArgumentException();
        } else {
            this.anyo = anyo;
        }
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 59 * hash + this.dia;
        hash = 59 * hash + this.mes;
        hash = 59 * hash + this.anyo;
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Fecha other = (Fecha) obj;
        if (this.dia != other.dia) {
            return false;
        }
        if (this.mes != other.mes) {
            return false;
        }
        if (this.anyo != other.anyo) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Fecha{" + "dia=" + dia + ", mes=" + mes + ", anyo=" + anyo + '}';
    }

    @Override
    public int compareTo(Fecha o) {
        int resultado = 0;
        if (this.calcularDiasDiferenciaCon(o) > 0) {
            resultado = 1;
        } else if (this.calcularDiasDiferenciaCon(o) < 0) {
            resultado = -1;
        } else {
            resultado = 0;
        }
        return resultado;
    }

}
