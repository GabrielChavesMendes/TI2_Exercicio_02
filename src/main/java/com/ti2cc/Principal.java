package com.ti2cc;

import java.util.Scanner;

public class Principal {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        DAO dao = new DAO();
        dao.conectar();
        
        int opcao = 0;
        
        while(opcao != 5) {
            System.out.println("\n===== MENU =====");
            System.out.println("1. Listar jogos");
            System.out.println("2. Inserir jogo");
            System.out.println("3. Excluir jogo");
            System.out.println("4. Atualizar jogo");
            System.out.println("5. Sair");
            System.out.print("Escolha uma opção: ");
            opcao = scanner.nextInt();
            scanner.nextLine(); 

            switch(opcao) {
                case 1:
                    //Listar jogos
                    Jogos[] jogos = dao.getJogos();
                    System.out.println("\n==== LISTA DE JOGOS ====");
                    if (jogos != null) {
                        for (Jogos jogo : jogos) {
                            System.out.println(jogo);
                        }
                    } else {
                        System.out.println("Nenhum jogo encontrado.");
                    }
                    break;
                
                case 2:
                    //Inserir jogo
                    System.out.print("Digite o id do jogo: ");
                    int id = scanner.nextInt();
                    scanner.nextLine(); 
                    System.out.print("Digite o nome do jogo: ");
                    String nome = scanner.nextLine();
                    System.out.print("Digite o valor do jogo: ");
                    double valor = scanner.nextDouble();

                    Jogos novoJogo = new Jogos(id, nome, valor);
                    if (dao.inserirJogo(novoJogo)) {
                        System.out.println("Jogo inserido com sucesso!");
                    } else {
                        System.out.println("Erro ao inserir o jogo.");
                    }
                    break;
                
                case 3:
                    //Excluir jogo
                    System.out.print("Digite o id do jogo a ser excluído: ");
                    int idExcluir = scanner.nextInt();
                    
                    if (dao.excluirJogo(idExcluir)) {
                        System.out.println("Jogo excluído com sucesso!");
                    } else {
                        System.out.println("Erro ao excluir o jogo.");
                    }
                    break;
                
                case 4:
                    //Atualizar jogo
                    System.out.print("Digite o id do jogo a ser atualizado: ");
                    int idAtualizar = scanner.nextInt();
                    scanner.nextLine(); 
                    System.out.print("Digite o novo nome do jogo: ");
                    String novoNome = scanner.nextLine();
                    System.out.print("Digite o novo valor do jogo: ");
                    double novoValor = scanner.nextDouble();
                    
                    Jogos jogoAtualizado = new Jogos(idAtualizar, novoNome, novoValor);
                    if (dao.atualizarJogo(jogoAtualizado)) {
                        System.out.println("Jogo atualizado com sucesso!");
                    } else {
                        System.out.println("Erro ao atualizar o jogo.");
                    }
                    break;
                
                case 5:
                    //Sair
                    dao.close();
                    System.out.println("Encerrando o programa...");
                    break;
                
                default:
                    System.out.println("Opção inválida! Tente novamente.");
                    break;
            }
        }
        
        scanner.close();
    }
}
