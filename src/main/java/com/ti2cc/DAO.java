package com.ti2cc;

import java.sql.*;

public class DAO {
    private Connection conexao;
    
    public DAO() {
        conexao = null;
    }
    
    public boolean conectar() {
        String driverName = "org.postgresql.Driver";                    
        String serverName = "localhost";
        String mydatabase = "postgres";
        int porta = 5432;
        String url = "jdbc:postgresql://" + serverName + ":" + porta + "/" + mydatabase;
        String username = "postgres";
        String password = "Favorito007";
        boolean status = false;

        try {
            Class.forName(driverName);
            conexao = DriverManager.getConnection(url, username, password);
            status = (conexao != null);
            System.out.println("Conexão efetuada com o postgres!");
        } catch (ClassNotFoundException e) { 
            System.err.println("Conexão NÃO efetuada com o postgres -- Driver não encontrado -- " + e.getMessage());
        } catch (SQLException e) {
            System.err.println("Conexão NÃO efetuada com o postgres -- " + e.getMessage());
        }

        return status;
    }
    
    public boolean close() {
        boolean status = false;
        
        try {
            conexao.close();
            status = true;
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        return status;
    }
    
    public boolean inserirJogo(Jogos jogo) {
        boolean status = false;
        try {  
            Statement st = conexao.createStatement();
            st.executeUpdate("INSERT INTO jogos (id, nome, valor) "
                           + "VALUES (" + jogo.getId() + ", '" + jogo.getNome() + "', "  
                           + jogo.getValor() + ");");
            st.close();
            status = true;
        } catch (SQLException u) {  
            throw new RuntimeException(u);
        }
        return status;
    }
    
    public boolean atualizarJogo(Jogos jogo) {
        boolean status = false;
        try {  
            Statement st = conexao.createStatement();
            String sql = "UPDATE jogos SET nome = '" + jogo.getNome() + "', valor = "  
                       + jogo.getValor() + " WHERE id = " + jogo.getId();
            st.executeUpdate(sql);
            st.close();
            status = true;
        } catch (SQLException u) {  
            throw new RuntimeException(u);
        }
        return status;
    }
    
    public boolean excluirJogo(int id) {
        boolean status = false;
        try {  
            Statement st = conexao.createStatement();
            st.executeUpdate("DELETE FROM jogos WHERE id = " + id);
            st.close();
            status = true;
        } catch (SQLException u) {  
            throw new RuntimeException(u);
        }
        return status;
    }
    
    public Jogos[] getJogos() {
        Jogos[] jogos = null;
        
        try {
            Statement st = conexao.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            ResultSet rs = st.executeQuery("SELECT * FROM jogos");      
            if(rs.next()) {
                rs.last();
                jogos = new Jogos[rs.getRow()];
                rs.beforeFirst();

                for(int i = 0; rs.next(); i++) {
                    jogos[i] = new Jogos(rs.getInt("id"), rs.getString("nome"), rs.getDouble("valor"));
                }
            }
            st.close();
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
        return jogos;
    }
    
    public Jogos[] getJogosPorValor(double valorMinimo) {
        Jogos[] jogos = null;
        
        try {
            Statement st = conexao.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            ResultSet rs = st.executeQuery("SELECT * FROM jogos WHERE valor >= " + valorMinimo);      
            if(rs.next()) {
                rs.last();
                jogos = new Jogos[rs.getRow()];
                rs.beforeFirst();

                for(int i = 0; rs.next(); i++) {
                    jogos[i] = new Jogos(rs.getInt("id"), rs.getString("nome"), rs.getDouble("valor"));
                }
            }
            st.close();
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
        return jogos;
    }
}
