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
	public static int[] fechas = new int[3];
	public static String[] actividad = new String[2];
	public static Scanner s = new Scanner(System.in);
	
	public static void main(String[] args){ 
		// Patricio Javier Vidal Veas
		// 22.330.827-9
		// ICCI
		/*
		try (BufferedWriter bw = new BufferedWriter(new FileWriter("Registros.txt", true))) {
			bw.write()
			
		} catch (Exception e){
			System.out.println("Error");
		}
		*/
		Leer_archivo_usuarios();	
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
		
		int index = 0;
		String usuario;
		do { // ACCESO AL USUARIO \\
		System.out.println("Usuario:");
		usuario = s.nextLine();
		index = Verificar_Usuario(usuario, usuarios);
		if (index != -1) {
			System.out.println("Contraseña:");
			String contraseña = s.nextLine();
			if (contraseñas[index].equals(contraseña)) {
				System.out.println("Acceso Permitido!");
				
			} else {
				index = -1;
				System.out.println("Contraseña erronea");
			}
			
		}
		} while(index == -1); 
		
		
		int opcion = 0;
		do {
			
			System.out.printf("Bienvenido! %s", usuario);
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
				Registrar_Actividad("Registros.txt", usuario);
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
		int contador = 0;
		String linea;
		
		try(Scanner lector = new Scanner(usuarios_arch)) {
			while(lector.hasNextLine()) {
				linea = lector.nextLine();
				String[] partes = linea.split(";");
				
				if (partes[0] == usuarios[0]) {
					usuarios_actividades[0][contador] = linea;
					
				} else if (partes[0] == usuarios[1]) {
					usuarios_actividades[1][contador] = linea;
					
				} else usuarios_actividades[2][contador] = linea;
				
				contador++;
			
			}
		} catch (FileNotFoundException error) {
			System.out.println("Archivo Registros no encontrado");		
		}
	} 
	
	
	
	public static int Verificar_Usuario(String user, String[] lista) {
		int indice = -1;
		for (int i = 0; i < lista.length; i++) {
			if (lista[i].equals(user)) {
					indice = i;
				}
			}
		if (indice == -1) {
			System.out.println("Usuario No Encontrado");
			return indice;
		}
		return indice;
		}
	
	
	
	public static void Menu_Actividades() {
		
	}
	
	public static void Registrar_Actividad(String file, String user) {
		
		boolean valida_fecha = false;
		int cronologia = 0;
		
		do {
			
			try {
			System.out.println("Digame la fecha, de forma dia/mes/año");
			String fecha = s.nextLine();
			String[] partes_fecha = fecha.split("/");
			fechas[0] = Integer.parseInt(partes_fecha[0]);
			fechas[1] = Integer.parseInt(partes_fecha[1]);
			fechas[2] = Integer.parseInt(partes_fecha[2]);
			cronologia = (fechas[0]) + (fechas[1])*30 + (fechas[2]*365);
			if (fechas[0] >= 31 || fechas[0] < 0 || fechas[1] > 12 || fechas[1] < 0 || fechas[2] < 2000) {
				System.out.println("Ingrese numeros correctos");
			} else {
				System.out.println("Digame las horas de la actividad");
				actividad[0] = s.nextLine();
				int horas = Integer.parseInt(actividad[0]); // ARREGLAR ESTO DESPUES
				System.out.println("Digame el nombre de la actividad");
				actividad[1] = s.nextLine();
				
				
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
					bw.write(linea);
					bw.newLine();
					bw.write(user + ";" + fechas[0] + "/" + fechas[1] + "/" + fechas[2] + ";" + actividad[0] + ";" + actividad[1]);
					escrito = true;
					
				} else bw.write(linea);				
				bw.newLine();
			}
			
			if (escrito == false) {
				bw.write(user + ";" + fechas[0] + "/" + fechas[1] + "/" + fechas[2] + ";" + actividad[0] + ";" + actividad[1]);
				bw.newLine();
			}
		} catch (IOException e){
			System.out.println("Error");
		}
		
		if(arch_og.delete()) {
			
			if(arch_new.renameTo(arch_og)) {
				System.out.println("¡Actividad Registrada!");
				
			} else {
				System.out.println("No se pudo renombrar");
			}
			
		} else {
			
			System.out.println("No se pudo borrar archivo original");
		}
	}
		
	public static void Modificar_Actividad() {
		
		File arch_og = new File("Registros.txt");
		File arch_new = new File("Registros_temporal.txt");
		
		try (BufferedReader br = new BufferedReader(new FileReader("Registros.txt"));
			 BufferedWriter bw = new BufferedWriter(new FileWriter("Registros_temporal.txt"))) {
			
			String linea;
			
			while((linea = br.readLine()) != null) {
				
				if (0 == 0) {
					bw.write("pepe");
			
					
				} else bw.write(linea);				
				bw.newLine();
				
			}
		} catch (IOException e){
			System.out.println("Error");
		}
		
		if(arch_og.delete()) {
			
			if(arch_new.renameTo(arch_og)) {
				System.out.println("¡Actividad Modificada!");
				
			} else {
				System.out.println("No se pudo renombrar");
			}
			
		} else {
			
			System.out.println("No se pudo borrar archivo original");
		}
	}

	
	public static void Eliminar_Actividad() {
		
		
	}
	
	public static void Cambiar_Contraseña() {
		
		
	}
	
	public static void Actividad_Mas_Realizada() {
		
		
	}
	
	public static void Actividad_Mas_Usuario() {
		
		
	}
	
	public static void Usuario_Mayor_Procastinacion() {
		
		
	}
	
	public static void Ver_Actividades() {
		
		
	}
}
