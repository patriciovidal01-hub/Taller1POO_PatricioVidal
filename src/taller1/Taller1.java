package taller1;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class Taller1 {
	
	public static String[][] usuarios_actividades = new String[3][300];
	public static String[] usuarios = new String[3];
	public static String[] contraseñas = new String[3];
	public static String[] actividad_y_horas = new String[2];
	public static String[][] actividades_unicas = new String[3][300];
	public static int[][] horas_totales_por_actividad = new int[3][300];
	public static int[] horas_totales = new int[3];
	public static String[] todos_actividades = new String[300];
	public static int[] fechas = new int[3];
	public static Scanner s = new Scanner(System.in);
	public static int identificador;
	public static int contador_actividades = 0;
	public static int[] contador_usuario = new int[3];
	public static int[] posicion_mayor = new int[3];
	public static int[] horas_globales = new int[900];
	public static String[] actividades_globales = new String[900];
	
	
	public static void main(String[] args){ 
		// Patricio Javier Vidal Veas
		// 22.330.827-9
		// ICCI
		Leer_archivo_usuarios();	
		Leer_archivo_registros();
		// CONTROL DE ERRORES \\
		int opcion;	
		do {
			
			System.out.println("1) Menu de Usuarios");
			System.out.println("2) Menu de Actividades");
			System.out.println("3) Salir");
			
			try {
				String recibe = s.nextLine();
				opcion = Integer.parseInt(recibe);
			} catch(Exception e) {

				System.out.println("Solo numeros");
				opcion = 0;
			}
			
			switch(opcion) {
			
			case(0):
				break;
			case(1):
				Menu_Usuarios();
				break;
			case(2):
				Menu_Actividades();
				break;
			case(3):
				break;
			default:
				System.out.println("Ingrese un numero valido");
			}
			
		} while(opcion != 3);
		
	}
	
	
	public static void Menu_Usuarios() {
		
		do { // ACCESO AL USUARIO \\
		System.out.println("Usuario:");
		String usuario = s.nextLine();
		Verificar_Usuario(usuario);
		if (identificador != -1) {
			
			System.out.println("Contraseña:");
			String contraseña = s.nextLine();
			if (contraseñas[identificador].equals(contraseña)) {
				System.out.println("Acceso Permitido!");	
				
			} else {
				identificador = -1;
				System.out.println("Contraseña erronea");
			}
			
		}
		} while(identificador == -1); 
		
		int opcion = 0;
		do {
			
			System.out.printf("Bienvenido! %s", usuarios[identificador]);
			System.out.println("");
			System.out.println("¿Que deseas hacer?");
			System.out.println("");
			System.out.println("1) Registrar Actividad");
			System.out.println("2) Modificar Actividad");
			System.out.println("3) Eliminar Actividad");
			System.out.println("4) Cambiar Contraseña");
			System.out.println("5) Salir");
			
			try {
				String recibe = s.nextLine();
				opcion = Integer.parseInt(recibe);
			} catch(Exception e) {

				System.out.println("Solo numeros");
				opcion = 0;
			}
			
			switch(opcion) {
			
			case(1):
				Registrar_Actividad();
				break;
			case(2):
				Modificar_Actividad();
				break;
			case(3):
				Eliminar_Actividad();
				break;
			case(4):
				Cambiar_Contraseña();
				break;
			case(5):
				break;
			default:
				System.out.println("Ingrese un numero valido");
			}
			
		} while(opcion != 5);
		
	}
	
	public static void Leer_archivo_usuarios() {
			File usuarios_arch = new File("Usuarios.txt");
			int contador = 0;
			String linea;
			
			try(Scanner lector = new Scanner(usuarios_arch)) {
				while(lector.hasNextLine()) {
					linea = lector.nextLine();
					String[] partes = linea.split(";");
					usuarios[contador] = partes[0];
					contraseñas[contador] = partes[1];
					contador++;
				}
			} catch (FileNotFoundException error) {
				System.out.println("Archivo Usuarios no encontrado");
				
			}
		} 
	
	public static void Leer_archivo_registros() {
		File usuarios_arch = new File("Registros.txt");
		String linea;
		
		try(Scanner lector = new Scanner(usuarios_arch)) {
			while(lector.hasNextLine()) {
				linea = lector.nextLine();
				String[] partes = linea.split(";");
				int index = Verificar_Usuario_archivo(partes[0]);
				usuarios_actividades[index][contador_usuario[index]] = linea;
				contador_usuario[index]++;
				todos_actividades[contador_actividades] = linea;
				
				contador_actividades++;
			}
		} catch (FileNotFoundException error) {
			System.out.println("Archivo Registros no encontrado");		
		}
	} 
	public static void Verificar_Usuario(String user) {
		identificador = -1;
		for (int i = 0; i < usuarios.length; i++) {
			if (usuarios[i].equals(user)) {
					identificador = i;
				}
			}
		if (identificador == -1) {
			System.out.println("Usuario No Encontrado");
			
		}
		}
	
	public static int Verificar_Usuario_archivo(String user) {
		for (int k = 0; k < usuarios.length; k++) {
			if (usuarios[k].equals(user)) {
					return k;
				}
			}
		return -1;
		}
	
	public static void Menu_Actividades() {
		int opcion = 0;
		for (int i = 0; i < 3; i++){
			posicion_mayor[i] = 0;
			for(int j = 0; j < 300; j++) {
				actividades_unicas[i][j] = null;
				horas_totales_por_actividad[i][j] = 0;
			}
		}
		
		Creacion_listas_actividades_horas(usuarios_actividades, 0);
		Creacion_listas_actividades_horas(usuarios_actividades, 1);
		Creacion_listas_actividades_horas(usuarios_actividades, 2);
		Creacion_lista_global();
		do {
		System.out.println("¡Bienvenido al menu de Analisis!");
		System.out.println("");
		System.out.println("¿Que desea hacer?");
		System.out.println("");
		System.out.println("1) Actividad más realizada");
		System.out.println("2) Actividad más realizada por cada usuario");
		System.out.println("3) Usuario con mayor procrastinación");
		System.out.println("4) Ver todas las actividades");
		System.out.println("5) Salir");
		
		try {
			String recibe = s.nextLine();
			opcion = Integer.parseInt(recibe);
		} catch(Exception e) {

			System.out.println("Solo numeros");
			opcion = 0;
		}
		
		switch(opcion) {
		
		case(1):
			Actividad_Mas_Realizada();
			break;
		case(2):
			Actividad_Mas_Usuario();
			break;
		case(3):
			Usuario_Mayor_Procastinacion();
			break;
		case(4):
			Ver_Actividades();
			break;
		case(5):
			break;
		default:
			System.out.println("Ingrese un numero valido");
		}
		} while (opcion != 5);
		
		
	}
	
	public static void Registrar_Actividad() {
		
		boolean valida_fecha = false;
		int cronologia = 0;
		if (contador_actividades < 300) {
		do {
			
			try {
			System.out.println("Digame la fecha, de forma dia/mes/año");
			String fecha = s.nextLine();
			String[] partes_fecha = fecha.split("/");
			fechas[0] = Integer.parseInt(partes_fecha[0]);
			fechas[1] = Integer.parseInt(partes_fecha[1]);
			fechas[2] = Integer.parseInt(partes_fecha[2]);
			cronologia = (fechas[0]) + (fechas[1])*30 + (fechas[2]*365);
			if (fechas[0] > 31 || fechas[0] <= 0 || fechas[1] > 12 || fechas[1] < 0 || fechas[2] < 2000) {
				System.out.println("Ingrese numeros correctos");
			} else {
				System.out.println("Digame las horas de la actividad");
				actividad_y_horas[0] = s.nextLine();
				int horas = Integer.parseInt(actividad_y_horas[0]); // ARREGLAR ESTO DESPUES
				System.out.println("Digame el nombre de la actividad");
				actividad_y_horas[1] = s.nextLine();
				
				
				valida_fecha = true;
			}
		
			} catch (Exception e) {
				System.out.println("Formato invalido");
				valida_fecha = false;
			}
		} while(!valida_fecha);
		
		
		// MODIFICAR ARCHIVO \\
		File arch_og = new File("Registros.txt");
		File arch_new = new File("Registros_temporal.txt");
		
		try (BufferedReader br = new BufferedReader(new FileReader("Registros.txt"));
			 BufferedWriter bw = new BufferedWriter(new FileWriter("Registros_temporal.txt"))) {
			
			String linea;
			
			boolean escrito = false;
			
			while((linea = br.readLine()) != null) {
				String[] partes = linea.split(";");
				String[] partes_dos = partes[1].split("/");
				int cronologia_dos = Integer.parseInt(partes_dos[0]) + (Integer.parseInt(partes_dos[1]))*30 + (Integer.parseInt(partes_dos[2]))*365;
				if (cronologia < cronologia_dos && escrito == false) {
					bw.write(usuarios[identificador] + ";" + fechas[0] + "/" + fechas[1] + "/" + fechas[2] + ";" + actividad_y_horas[0] + ";" + actividad_y_horas[1]);
					bw.newLine();
					bw.write(linea);
					
					escrito = true;
					
					
				} else bw.write(linea);				
				bw.newLine();
			}
			
			if (escrito == false) {
				bw.write(usuarios[identificador] + ";" + fechas[0] + "/" + fechas[1] + "/" + fechas[2] + ";" + actividad_y_horas[0] + ";" + actividad_y_horas[1]);
				bw.newLine();
			}
		} catch (IOException e){
			System.out.println("Error");
		}
		
		if(arch_og.delete()) {
			
			if(arch_new.renameTo(arch_og)) {
				System.out.println("¡Actividad Registrada!");
				contador_actividades = 0;
				for(int i = 0; i < contador_usuario.length; i++ ) {
					contador_usuario[i] = 0;

				}
				Leer_archivo_registros();
			} else {
				System.out.println("No se pudo renombrar");
			}
			
		} else {
			
			System.out.println("No se pudo borrar archivo original");
		}
		} else {
			System.out.println("No se pueden registrar más actividades, cupos llenos");
		}
	}
		
	public static void Modificar_Actividad() {
		
		int opcion = -1;
		int opcion2 = -1;
		
		do {
		System.out.println("¿Que actividad desea modificar?");
		System.out.println("0) Regresar");
		for (int j = 0; j < (contador_usuario[identificador]); j++) {
			System.out.println((j+1) + ") " + usuarios_actividades[identificador][j]);		
		
		}
		try {
			String recibe = s.nextLine();
			opcion = Integer.parseInt(recibe);
			if (opcion > (contador_usuario[identificador]) || opcion < 0) {
				opcion =-1;
				System.out.println("Eliga un numero que corresponda a una actividad");
			} 
			
		} catch(NumberFormatException e) {
			System.out.println("Solo numeros");
			opcion = -1;
		}
					
			
		} while (opcion < 0);
		
		
		do {
			if (opcion != 0) {
				System.out.println("¿Que desea modificar de esta actividad?");
				System.out.println(usuarios_actividades[identificador][opcion-1]);
				System.out.println();
				System.out.println("0) Regresar");
				System.out.println("1) Fecha");
				System.out.println("2) Duracion");
				System.out.println("3) Tipo Actividad");
					
				try {
					String recibe = s.nextLine();
					opcion2 = Integer.parseInt(recibe);
				} catch(Exception e) {

					System.out.println("Solo numeros");
					opcion2 = 0;
				}
							
				switch(opcion2) {
				
				case(0):
					 break;
				case(1):
					try {
					System.out.println("Digame la fecha, de forma dia/mes/año");
					String fecha = s.nextLine();
					String[] partes_fecha = fecha.split("/");
					fechas[0] = Integer.parseInt(partes_fecha[0]);
					fechas[1] = Integer.parseInt(partes_fecha[1]);
					fechas[2] = Integer.parseInt(partes_fecha[2]);
					if (fechas[0] >= 31 || fechas[0] < 0 || fechas[1] > 12 || fechas[1] < 0 || fechas[2] < 2000) {
						System.out.println("Ingrese numeros correctos");
					} else {
						Modificar(usuarios_actividades[identificador][opcion-1], 0, fecha);
						
					}
				
					} catch (Exception e) {
						System.out.println("Formato invalido");
						opcion2 = -1;
					}
					break;
				case(2):
					System.out.println("Digame las horas de la actividad");
					actividad_y_horas[0] = s.nextLine();
					try {
						int prueba = Integer.parseInt(actividad_y_horas[0]);
						Modificar(usuarios_actividades[identificador][opcion-1], 1, actividad_y_horas[0]);
					} catch (Exception e) {
						System.out.println("Solo numeros");
					}
					break;
				case(3):
					System.out.println("Digame el nombre de la actividad");
					actividad_y_horas[1] = s.nextLine();
					Modificar(usuarios_actividades[identificador][opcion-1], 2, actividad_y_horas[1]);
					break;
				default:
					System.out.println("Eliga un numero que corresponda a una opcion");
					opcion2 = -1;
					break;
				}
					
			} else {
				opcion2 = 0;
			}
			
			} while (opcion2 < 0);
	}
	
	
	public static void Modificar(String linea_modificar, int atributo, String cambio) {
		File arch_og = new File("Registros.txt");
		File arch_new = new File("Registros_temporal.txt");
		
		try (BufferedReader br = new BufferedReader(new FileReader("Registros.txt"));
			 BufferedWriter bw = new BufferedWriter(new FileWriter("Registros_temporal.txt"))) {
		
			String linea;
			String[] partes = linea_modificar.split(";");
			String usuario = partes[0];
			String fecha = partes[1];
			String horas_actividad = partes[2];
			String actividad = partes[3];
			
			while((linea = br.readLine()) != null) {
			
				if (linea.equals(linea_modificar)) {
					
					String linea_nueva = linea;
					
					switch(atributo) {
						case(0):
							linea_nueva = (usuario + ";" + cambio + ";" + horas_actividad + ";" + actividad );
							break;
						case(1):
							linea_nueva = (usuario + ";" + fecha + ";" + cambio + ";" + actividad );
							break;
						case(2):
							linea_nueva = (usuario + ";" + fecha + ";" + horas_actividad + ";" + cambio);
							break;
					}
					bw.write(linea_nueva);
					bw.newLine();
					
				} else {
					bw.write(linea);
					bw.newLine();
				}
					
			}
		} catch (IOException e){
			System.out.println("Error");
		}
		
		if(arch_og.delete()) {
			
			if(arch_new.renameTo(arch_og)) {
				System.out.println("¡Actividad Registrada!");
				contador_actividades = 0;
				for(int i = 0; i < contador_usuario.length; i++ ) {
					contador_usuario[i] = 0;
				}
				
				Leer_archivo_registros();
			} else System.out.println("No se pudo renombrar");
		} else System.out.println("No se pudo borrar archivo original");
	}
	
	public static void Eliminar_Actividad() {
		int opcion = -1;
		int contador = 0;
		
		do {
		contador = 0;
		System.out.println("¿Que actividad desea eliminar?");
		System.out.println("0) Regresar");
		for (int j = 0; j < (contador_usuario[identificador]); j++) {
			System.out.println((j+1) + ") " + usuarios_actividades[identificador][j]);		
			contador++;
		
		}
		try {
			String recibe = s.nextLine();
			opcion = Integer.parseInt(recibe);
			if (opcion > contador || opcion < 0) {
				opcion =-1;
				System.out.println("Eliga un numero que corresponda a una actividad");
			} else if (opcion != 0) {
				Eliminar(usuarios_actividades[identificador][opcion-1]);
				opcion = 0;
			}
			
		} catch(NumberFormatException e) {
			System.out.println(e);
			System.out.println("Solo numeros");
			opcion = -1;
		}
					
			
		} while (opcion < 0);		
		
	}
	
	public static void Eliminar(String linea_a_borrar) {
		File arch_og = new File("Registros.txt");
		File arch_new = new File("Registros_temporal.txt");
		
		try (BufferedReader br = new BufferedReader(new FileReader("Registros.txt"));
			 BufferedWriter bw = new BufferedWriter(new FileWriter("Registros_temporal.txt"))) {
		
			String linea;
			
			
			while((linea = br.readLine()) != null) {
			
				if (linea.equals(linea_a_borrar)) {
														
				} else {
					bw.write(linea);
					bw.newLine();
				}
					
			}
		} catch (IOException e){
			System.out.println("Error");
		}
		
		if(arch_og.delete()) {
			
			if(arch_new.renameTo(arch_og)) {
				System.out.println("¡Actividad Eliminada!");
				contador_actividades = 0;
				for(int i = 0; i < contador_usuario.length; i++ ) {
					contador_usuario[i] = 0;
				}
				
				Leer_archivo_registros();
			} else System.out.println("No se pudo renombrar");
		} else System.out.println("No se pudo borrar archivo original");
	}
	
	
	public static void Cambiar_Contraseña() {
		System.out.println("A que contraseña desea cambiarla, escribala a continuación");
		String nueva_contraseña = s.nextLine();
		
		File arch_og = new File("Usuarios.txt");
		File arch_new = new File("Usuarios_temporal.txt");
		
		try (BufferedReader br = new BufferedReader(new FileReader("Usuarios.txt"));
			 BufferedWriter bw = new BufferedWriter(new FileWriter("Usuarios_temporal.txt"))) {
		
			String linea;
			
			while((linea = br.readLine()) != null) {
			
				String[] partes = linea.split(";");
				String usuario = partes[0];
				
				if (usuario.equals(usuarios[identificador])) {
					bw.write(usuario + ";" + nueva_contraseña);	
					bw.newLine();
				} else {
					bw.write(linea);
					bw.newLine();
				}
					
			}
		} catch (IOException e){
			System.out.println("Error");
		}
		
		if(arch_og.delete()) {
			
			if(arch_new.renameTo(arch_og)) {
				System.out.println("¡Contraseña cambiada!");
				Leer_archivo_usuarios();
			} else System.out.println("No se pudo renombrar");
		} else System.out.println("No se pudo borrar archivo original");
		
	}
	
	
	public static void Creacion_listas_actividades_horas(String[][] lista, int numero) {
		
		boolean existe = false;
		int contador = 0;
		
		for (int i = 0; i < contador_usuario[numero]; i++) {
			existe = false;
			String[] partes = (lista[numero][i]).split(";");
			int horas_actividad = Integer.parseInt(partes[2]);
			String actividad = partes[3];
			for (int j = 0; j < contador; j++) {
				if(actividades_unicas[numero][j] != null && actividades_unicas[numero][j].equals(actividad) ) {
					existe = true;
					horas_totales_por_actividad[numero][j] += horas_actividad;
					break;
				}
				
			}
			if(existe == false) {
				actividades_unicas[numero][contador] = actividad;
				horas_totales_por_actividad[numero][contador] += horas_actividad;
				contador ++;
			}
		}	
		Encontrar_Mayor(horas_totales_por_actividad, contador, numero );
	}
	
	public static void Actividad_Mas_Realizada() {
		
		
		int mayor = -9999;
		int posicion_mayor_global = 0;
		
		for (int l = 0; l < (actividades_globales.length); l++) {
			if (horas_globales[l] > mayor) {
				mayor = horas_globales[l];
				posicion_mayor_global = l;
			}
		}
		
		System.out.println("La actividad más realizada es: " + actividades_globales[posicion_mayor_global] + " con " + horas_globales[posicion_mayor_global] + " horas");
	
	}
	
	public static void Creacion_lista_global() {
		for (int p = 0; p < horas_globales.length; p++) {
			horas_globales[p] = 0;
			actividades_globales[p] = null;
		}
		
		boolean existe = false;
		int contador = 0;
		for (int i = 0; i < usuarios.length; i++) { 
			for (int j = 0; j < contador_usuario[i]; j++) {
			existe = false;
			String[] partes = usuarios_actividades[i][j].split(";");
			int horas_actividad = Integer.parseInt(partes[2]);
			String actividad = partes[3];
			for (int k = 0; k < contador; k++) {
				if(actividades_globales[k] != null && actividades_globales[k].equals(actividad) ) {
					existe = true;
					horas_globales[k] += horas_actividad;
					break;
				}
				
			}
			if(existe == false) {
				actividades_globales[contador] = actividad;
				horas_globales[contador] += horas_actividad;
				contador ++;
			}
		}	
		}
	}
	
	public static void Encontrar_Mayor(int lista[][], int numero, int identifica) {
		int mayor = -9999;
		
		
		for (int i = 0; i < numero; i++) {
			if (lista[identifica][i] > mayor) {
				mayor = lista[identifica][i];
				posicion_mayor[identifica] = i;
			}
		}
	}
	
	public static void Actividad_Mas_Usuario() {
		System.out.println("La actividad más realizada por Usuario es:");
		System.out.println("");
		System.out.println(usuarios[0] + "--" + actividades_unicas[0][posicion_mayor[0]] + " con " + horas_totales_por_actividad[0][posicion_mayor[0]]);
		System.out.println(usuarios[1] + "--" + actividades_unicas[1][posicion_mayor[1]] + " con " + horas_totales_por_actividad[1][posicion_mayor[1]]);
		System.out.println(usuarios[2] + "--" + actividades_unicas[2][posicion_mayor[2]] + " con " + horas_totales_por_actividad[2][posicion_mayor[2]]);
	}
	
	public static void Usuario_Mayor_Procastinacion() {
		
		int mayor = -99999;
		int posicion_mayor = 0;
		int[] suma_horas = new int[3];
		for(int i = 0; i < usuarios.length; i++) {
			for (int j = 0; j < (contador_usuario[i]); j++) {
				suma_horas[i] += horas_totales_por_actividad[i][j];
			}
			
		}
		
		for(int i = 0; i < usuarios.length; i++) {
			if(suma_horas[i] > mayor) {
				mayor = suma_horas[i];
				posicion_mayor = i;
			}
		}
		System.out.println("El más procrastinador es " + usuarios[posicion_mayor] + " con " + suma_horas[posicion_mayor] + " horas" );
	}
	
	public static void Ver_Actividades() {
		for (int i = 0; i < (todos_actividades.length); i++) {
			if (todos_actividades[i] != null) {
				System.out.println(i+1 + ") " + todos_actividades[i]);
		}
		}
		
	}
}
