package juegoDelPaloPoda;

import java.util.LinkedList;
import java.util.Random;

public class Main {
	
	public static void main(String[] args) {
		int palos = 401;
		boolean turnoRnd = false;
		System.out.println(" Comienzo partida...");
		imprimirPalos(palos);
		do {
			turnoRnd = !turnoRnd;
			palos = turnoRnd ? turnoPC(palos) : turnoMiniMax(palos);
			imprimirPalos(palos);
		}while(!validarVictoria(palos));
		String ganador = turnoRnd ? "Aleatorio" : "MiniMax";
		System.out.println("El Ganador es : "+ganador);
	}
	
	public static int turnoMiniMax(int n) {
		int profundidadMax = 15;
		int m = -100;
		int movOptimo = 1;
		int alpha = (int)Double.NEGATIVE_INFINITY;
		int beta = (int)Double.POSITIVE_INFINITY;
		LinkedList<Integer> estSucesores = generador(n);
		for(int i = 0;i<estSucesores.size();i++) {
			int t = algoritmoMM(estSucesores.get(i),profundidadMax,false,alpha,beta);
			if(t > m) {
				m = t;
				movOptimo = estSucesores.get(i);
			}
			alpha = Math.max(alpha, m);
		}
		System.out.println(" Turno MiniMax...");
		return movOptimo;
	}
	
	public static int algoritmoMM(Integer o, int profundidadTemp, boolean jugada, int alpha, int beta) {
		if(o == 1) {
			return jugada ? -1 : 1;
		}
		if(profundidadTemp == 0) {
			return 0;
		}
		int m = jugada ? -1 : 1;
		LinkedList<Integer> estSucesores = generador(o);
		for(int i = 0;i<estSucesores.size();i++) {
			int t = algoritmoMM(estSucesores.get(i), profundidadTemp-1, !jugada, alpha, beta);
			if(jugada) {
				m = Math.max(m, t);
				alpha = Math.max(alpha, m);
			}else {
				m = Math.min(m, t);
				beta = Math.min(beta, m);
			}
			if(alpha >= beta) {
				break;
			}
		}
		return m;
	}
	
	public static LinkedList<Integer> generador(Integer o) {
		LinkedList<Integer> estSucesores = new LinkedList<Integer>();
		for(int i = 1; i<=3;i++) {
			if(i <= o) {
				estSucesores.add(o-i);
			}
		}
		return estSucesores;
	}
	
	public static int turnoPC(int n) {
		Random rnd = new Random();
		System.out.println(" Turno Aleatorio...");
		if(n<=4) {
			return 1;
		}else {
			return n-=rnd.nextInt(1,4);
		}
	}
	
	public static void imprimirPalos(int n) {
		System.out.println("   ");
		for(int i=0;i<n;i++) {
			System.out.print("|");
		}
		System.out.println("\n");
	}
	
	public static boolean validarVictoria(int n) {
		return n==1 ? true : false;
	}
}
