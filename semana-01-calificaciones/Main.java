import java.util.Scanner;

/**
 * PROYECTO: Sistema de Gestión de Calificaciones
 * Semana 1 - Java Fundamentos
 */
public class Main{

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        
        // Configuración de datos según el requerimiento
        String[] estudiantes = new String[5];
        double[][] notas = new double[5][3];
        double[] promedios = new double[5];

        System.out.println("========================================");
        System.out.println("   REGISTRO DE NOTAS DE ESTUDIANTE   ");
        System.out.println("========================================");

        // Entrada de datos con validación
        for (int i = 0; i < 5; i++) {
            System.out.print("\nNombre del estudiante " + (i + 1) + ": ");
            estudiantes[i] = sc.nextLine();

            for (int j = 0; j < 3; j++) {
                notas[i][j] = leerNotaValidada(sc, j + 1);
            }
            // Método 1: Calcular promedio
            promedios[i] = calcularPromedio(notas[i]);
        }

        // Método 2: Mostrar reporte en columnas
        mostrarReporte(estudiantes, notas, promedios);

        // Método 3 y 4: Estadísticas e información de aprobados
        generarEstadisticas(promedios);

        sc.close();
    }

    /**
     * Valida que la nota esté entre 0-100 y maneja errores de entrada (try-catch)
     */
    public static double leerNotaValidada(Scanner sc, int numNota) {
        double nota = -1;
        do {
            try {
                System.out.print("  Ingrese Nota " + numNota + " : ");
                nota = Double.parseDouble(sc.nextLine());
                if (nota < 0 || nota > 100) {
                    System.out.println("  [Error] La nota debe estar entre 0 y 100.");
                    nota = -1;
                }
            } catch (NumberFormatException e) {
                System.out.println("  [Error] Entrada no valida. Ingrese un numero.");
            }
        } while (nota == -1);
        return nota;
    }

    // MÉTODO 1: Cálculo de promedio individual
    public static double calcularPromedio(double[] notas) {
        double suma = 0;
        for (double n : notas) suma += n;
        return suma / notas.length;
    }

    // MÉTODO 2: Mostrar reporte en columnas (Formato printf)
    public static void mostrarReporte(String[] nombres, double[][] notas, double[] proms) {
        System.out.println("\n--- RESULTADOS FINALES ---");
        System.out.printf("%-15s | %-5s | %-5s | %-5s | %-10s | %-10s%n", 
                          "ESTUDIANTE", "N1", "N2", "N3", "PROMEDIO", "ESTADO");
        System.out.println("------------------------------------------------------------------");
        
        for (int i = 0; i < nombres.length; i++) {
            String estado = (proms[i] >= 51) ? "APROBADO" : "REPROBADO";
            System.out.printf("%-15s | %-5.0f | %-5.0f | %-5.0f | %-10.2f | %-10s%n", 
                              nombres[i], notas[i][0], notas[i][1], notas[i][2], proms[i], estado);
        }
    }

    // MÉTODO 3: Estadísticas generales con conversión a ENTEROS
    public static void generarEstadisticas(double[] proms) {
        double sumaTotal = 0, max = proms[0], min = proms[0];
        
        for (double p : proms) {
            sumaTotal += p;
            if (p > max) max = p;
            if (p < min) min = p;
        }

        // Conversión explícita a int (Casting) como pide el PDF
        int promGral = (int) (sumaTotal / proms.length);
        int notaMax = (int) max;
        int notaMin = (int) min;

        System.out.println("\n--- ESTADISTICAS DEL CURSO ---");
        System.out.println("Promedio General: " + promGral);
        System.out.println("Nota Maxima:      " + notaMax);
        System.out.println("Nota Minima:      " + notaMin);
        
        // Llamada al Método 4
        contarResultados(proms);
    }

    // MÉTODO 4: Contar aprobados y reprobados (con porcentaje)
    public static void contarResultados(double[] proms) {
        int aprobados = 0;
        int reprobados = 0;
        for (double p : proms) {
            if (p >= 51) aprobados++;
            else reprobados++;
        }
        int total = proms.length;
        double pctAprobados = total > 0 ? (aprobados * 100.0) / total : 0.0;
        double pctReprobados = total > 0 ? (reprobados * 100.0) / total : 0.0;
        System.out.printf("Aprobados:   %d (%.2f%%)%n", aprobados, pctAprobados);
        System.out.printf("Reprobados:  %d (%.2f%%)%n", reprobados, pctReprobados);
    }
}