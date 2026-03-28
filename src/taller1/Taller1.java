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
	
	public static String[] usuarios = new String[3];
	public static String[] contraseñas = new String[3];
	
	
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
		
		Scanner s = new Scanner(System.in);
		// CONTROL DE ERRORES \\
		int opcion;	
		boolean cargado = false;
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
			
			case(1):
				Menu_Usuarios(s, cargado);
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
	
	
	public static void Menu_Usuarios(Scanner scanner, boolean cargado) {
		
		cargado = Leer_archivo(cargado, "Usuarios.txt");	
		int index = 0;
		String usuario;
		do { // ACCESO AL USUARIO \\
		System.out.println("Usuario:");
		usuario = scanner.nextLine();
		index = Verificar_Usuario(scanner, usuario, usuarios);
		if (index != -1) {
			System.out.println("Contraseña:");
			String contraseña = scanner.nextLine();
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
				String recibe = scanner.nextLine();
				opcion = Integer.parseInt(recibe);
			} catch(Exception e) {

				System.out.println("Solo numeros");
				opcion = 0;
			}
			
			switch(opcion) {
			
			case(1):
				Registrar_Actividad("Registros.txt", scanner, usuario);
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
	
	public static boolean Leer_archivo(boolean cargado, String archivo) {
		if (!cargado) {
			File usuarios_arch = new File(archivo);
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
				cargado = true;
			} catch (FileNotFoundException error) {
				System.out.println("Archivo Usuarios no encontrado");
				
			}
		} 
		return cargado;
	}
	
	
	public static int Verificar_Usuario(Scanner scanner, String user, String[] lista) {
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
	
	public static void Registrar_Actividad(String file, Scanner scanner, String user) {
		
		boolean valida_fecha = false;
		
		do {
			
			try {
			System.out.println("Digame la fecha, de forma dia/mes/año");
			String fecha = scanner.nextLine();
			String[] partes_fecha = fecha.split("/");
			int dia = Integer.parseInt(partes_fecha[0]);
			int mes = Integer.parseInt(partes_fecha[1]);
			int año = Integer.parseInt(partes_fecha[2]);
			if (dia >= 31 || dia < 0 || mes > 12 || mes < 0 || año < 2000) {
				System.out.println("Ingrese numeros correctos");
			} else {
				System.out.println("Digame el nombre de la actividad");
				String actividad = scanner.nextLine();
				System.out.println("Digame las horas de la actividad");
				String horas_actividad = scanner.nextLine();
				int horas = Integer.parseInt(horas_actividad);
				
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
			
			while((linea = br.readLine()) != null) {
				
				
				bw.write(linea);
				bw.newLine();
			}
				
		} catch (IOException e){
			System.out.println("Error");
		}
		
		if(arch_og.delete()) {
			
			if(arch_new.renameTo(arch_og)) {
				System.out.println("Archivo cambiado");
				
			} else {
				System.out.println("No se pudo renombrar");
			}
			
		} else {
			
			System.out.println("No se pudo borrar archivo original");
		}
		}
		
		
		
		/*
		
		try (BufferedWriter bw = new BufferedWriter(new FileWriter(file, true))) {
			bw.write("hola");
			
		} catch (Exception e){
			System.out.println("Error");
		}
		
		*/
	
	public static void Modificar_Actividad() {
		
		
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
