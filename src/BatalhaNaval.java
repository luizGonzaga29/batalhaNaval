

import java.util.InputMismatchException;
import java.util.Random;
import java.util.Scanner;

/*
 	0 = localização neutra
 	P = player
 	C = CPU
 	D = alvo do player destruido
 	X = não acertou nada
 	A = acertos do player
*/

public class BatalhaNaval {

	static String[][] matrizPlay = new String[8][8];
	static String[][] matrizPc = new String[8][8];
	static final int qtdNavios = 20;
	static final String posicao = "0";
	static int acertosPlay = 0;
	static int acertosPc = 0;
	static final int totalMov = 30;
	static final int acertos = 10;
	
	//preenche as matrizes
	static void preencherMatriz() {
		for(int i = 0;i < matrizPlay.length;i++) {
			for(int j = 0;j < matrizPlay[i].length;j++) {
				matrizPlay[i][j] = posicao;
				matrizPc[i][j] = posicao;
			}
		}
	}
	
	//coloca as posições do player
	static void preencherPlayer() {
		int qtd = 0;
				
		while(qtd < qtdNavios) {
			int numAle1 = gerarNumero();
			int numAle2 = gerarNumero();
			if(matrizPlay[numAle1][numAle2].equals(posicao)) {
				matrizPlay[numAle1][numAle2] = "P";
				matrizPc[numAle1][numAle2] = "P";
			}else {
				qtd--;
			}
			qtd++;
		}
	}
	
	//coloca as posições do pc
	static void preencherMatrizPc() {
		int qtd = 0;
		while(qtd < qtdNavios) {
			int numAle1 = gerarNumero();
			int numAle2 = gerarNumero();
			if(matrizPc[numAle1][numAle2].equals(posicao)) {
				matrizPc[numAle1][numAle2] = "C";
			}else {
				qtd--;
			}
			qtd++;
		}
	}
	
	//imprimi a matriz 
	static void printMatriz(String[][] matriz ) {
		for(int i = 0;i < matriz.length;i++) {
			System.out.print((i+1) + "|");
			for(int j = 0;j < matriz[i].length;j++) {
				System.out.print("  " + matriz[i][j] + "  ");
			}
			System.out.println();
			System.out.println();
		} 
		System.out.print("_________________________________________\n");
		System.out.println("    A    B    C    D    E    F    G    H");
		System.out.println();
		
	}
	
	//gera inteiro aleatório
	static int gerarNumero() {
		return new Random().nextInt(8);
	}
	
	//movimento do player
	static boolean ataquePlayer(int linha, String coluna) {
		int col = converter(coluna);
		int l = linha - 1;
		boolean ataque = true;
		if(l < 0 || l > 7 || col < 0) {
			System.out.println("valores inválidos.");
			ataque = false;
		}else {
			ataque = validarAtaquePlay(l, col);
		}
		return ataque;
	}
	
	//valida o ataque do player
	static boolean validarAtaquePlay(int linha, int coluna) {
		boolean ataque = true;
		if(matrizPc[linha][coluna].equals("P")) {
			System.out.println("Fogo amigo não é permitido.");
			ataque = false;
		}else if(matrizPc[linha][coluna].equals(posicao)) {
			matrizPc[linha][coluna] = "X";
			matrizPlay[linha][coluna] = "X";
		}else if(matrizPc[linha][coluna].equals("C")) {
			matrizPc[linha][coluna] = "A";
			matrizPlay[linha][coluna] = "A";
			acertosPlay++;
		}else {
			System.out.println("Movimento já realizado.");
			ataque = false;
		}
		return ataque;
	}
	
	//passa a string para a posição relativa 
	static int converter(String coluna) {
		String[] colunas = {"a","b","c","d","e","f","g","h"};
		int retorno = -1;
		for(int i = 0;i < 8;i++) {
			if(colunas[i].equalsIgnoreCase(coluna)) {
				retorno = i;
			}
		}
		return retorno;
	}
	
	//verifica parada
	static boolean verificarResultado(int x) {
		return (x == totalMov) ? false : true;
		//return (x == totalMov || acertosPlay == acertos || acertosPc == acertos) ? false : true;
	}
	
	//ataque PC
	static void ataquePc() {
		int cont = 0;
		int numAle1 = 0;
		int numAle2 = 0;
		while(cont == 0) {
			numAle1 = gerarNumero();
			numAle2 = gerarNumero();
			if(matrizPc[numAle1][numAle2].equals(posicao)) {
				matrizPc[numAle1][numAle2] = "X"; 
				matrizPlay[numAle1][numAle2] = "X";
				cont = 1;
			}else if(matrizPc[numAle1][numAle2].equals("P")) {
				matrizPc[numAle1][numAle2] = "D"; 
				matrizPlay[numAle1][numAle2] = "D";
				acertosPc++;
				cont = 1;
			}
		}
		System.out.print("Linha = " + (numAle1+1) + " coluna = " + converter(numAle2));
		System.out.println();
	}
	
	static String converter(int coluna) {
		String[] colunas = {"a","b","c","d","e","f","g","h"};
		String retorno = "";
		for(int i = 0;i < 8;i++) {
			if(i == coluna) {
				retorno = colunas[i];
			}
		}
		return retorno;
	}
		
	public static void main(String[] args) {
		
		Scanner entry = new Scanner(System.in);
		boolean controle = true;
		int linha = 0;
		String coluna = "";
		int movs = 0;
		
		preencherMatriz();
		preencherPlayer();
		preencherMatrizPc();
		printMatriz(matrizPlay);
		
		while(controle) {
				
			try {
				System.out.println((movs+1) + "° turno.");
				System.out.println("Player move.");
				System.out.print("Informe a linha: ");
				linha = entry.nextInt();
				System.out.print("Informe a coluna: ");
				coluna = entry.next();
				System.out.println();
				
				if(ataquePlayer(linha, coluna)) {
					printMatriz(matrizPlay);
					if(acertosPlay == acertos) {
						break;
					}
					System.out.println("Pc move.");
					ataquePc();
					System.out.println();
					printMatriz(matrizPlay);
					if(acertosPc == acertos) {
						break;
					}
					System.out.println();
					movs++;
				}else {
					System.out.println();
					printMatriz(matrizPlay);
				}
				controle = verificarResultado(movs);
			}catch (InputMismatchException e) {
				System.out.println("Só valores numéricos de 1 a 8.");
				entry.next();
			}
		}
		System.out.println("Placar final.");
		System.out.print("Player : " + acertosPlay + "\nPC : " + acertosPc + "\n\n");
		printMatriz(matrizPc);
		entry.close();
	}
}
