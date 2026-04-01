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
	public static String[] actividad = new String[2];
	public static int[] fechas = new int[3];
	public static Scanner s = new Scanner(System.in);
	public static int identificador;
	public static int contador_actividades = 0;
	public static int[] contador_usuario = new int[3];
	
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
				Registrar_Actividad("Registros.txt", usuarios[identificador]);
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
		
	}
	
	public static void Registrar_Actividad(String file, String user) {
		
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
					bw.write(user + ";" + fechas[0] + "/" + fechas[1] + "/" + fechas[2] + ";" + actividad[0] + ";" + actividad[1]);
					bw.newLine();
					bw.write(linea);
					
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
		int contador = 0;
		do {
		contador = 0;
		System.out.println("¿Que actividad desea modificar?");
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
			} 
			
		} catch(NumberFormatException e) {
			System.out.println(e);
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
					
				String recibe2 = s.nextLine();
				opcion2 = Integer.parseInt(recibe2);
		
				if (opcion2 != 0 && opcion2 != 1 && opcion2 != 2 && opcion2 != 3)	{
					System.out.println("Eliga un numero que corresponda a una opcion");
					opcion2 = -1;
					
				} else {
					
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
						actividad[0] = s.nextLine();
						try {
							int prueba = Integer.parseInt(actividad[0]);
							Modificar(usuarios_actividades[identificador][opcion-1], 1, actividad[0]);
						} catch (Exception e) {
							System.out.println("Solo numeros");
						}
						break;
					case(3):
						System.out.println("Digame el nombre de la actividad");
						actividad[1] = s.nextLine();
						Modificar(usuarios_actividades[identificador][opcion-1], 2, actividad[1]);
						break;
					}
				}
					
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
			String actividades = partes[3];
			
			while((linea = br.readLine()) != null) {
			
				if (linea.equals(linea_modificar)) {
					
					String linea_nueva = linea;
					
					switch(atributo) {
						case(0):
							linea_nueva = (usuario + ";" + cambio + ";" + horas_actividad + ";" + actividades );
							break;
						case(1):
							linea_nueva = (usuario + ";" + fecha + ";" + cambio + ";" + actividades );
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
		int opcion2 = -1;
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
