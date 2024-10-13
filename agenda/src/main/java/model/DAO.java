package model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DAO {
	/** Módulo de conexão **/
	
	// Parâmetros de conexão
	private String driver = "com.mysql.cj.jdbc.Driver";
	private String url = "jdbc:mysql://127.0.0.1:3306/agenda";
	private String user = "root";
	private String password = "Kamenriderv3";
	
	// Métodos de conexão
	private Connection conectar() throws Exception {
		Connection con = null;
		
		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, user, password);
			return con;
		} catch (SQLException e) {
			System.out.println(e);
			return null;
		}
	}
	
	public void inserirContato(JavaBeans contato) throws Exception {
		String create = "insert into contatos(nome, fone, email) values(?, ?, ?)";
		try {
			// abrir a conexão
			Connection con = conectar();
			// Preparar a query para execução no banco de dados
			PreparedStatement pst = con.prepareStatement(create);
			// Substituir os parametros (?) pelo contéudo das variáveis JavaBeans
			pst.setString(1, contato.getNome());
			pst.setString(2, contato.getFone());
			pst.setString(3, contato.getEmail());
			// Executar a query 
			pst.executeUpdate();
			// Encerrar a conexão com o BD
			con.close();
		} catch (SQLException e) {
			System.out.println(e);
		}
	}
	
	public ArrayList<JavaBeans> listarContatos() throws Exception {
		// Criando objeto para acessar a classe JavaBeans
		ArrayList<JavaBeans> contatos = new ArrayList<>();
		String read = "select * from contatos order by nome";
		try {
			Connection con = conectar();
			PreparedStatement pst = con.prepareStatement(read);
			ResultSet rs = pst.executeQuery();
			// o laço abaixo será executado enquanto houver contatos
			while (rs.next()) {
				// variáveis de apoio que recebem dados do banco
				String idcon = rs.getString(1);
				String nome = rs.getString(2);
				String fone = rs.getString(3);
				String email = rs.getString(4);
				// populando o ArrayList
				contatos.add(new JavaBeans(idcon, nome, fone, email));
			}
			con.close();
			return contatos;
		} catch (SQLException e) {
			System.out.println(e);
			return null;
		}
	}
	
	// teste de conexão
	/*public void testeConexao() throws ClassNotFoundException {
		try {
			Connection connection = conectar();
			System.out.println(connection);
			connection.close();
		} catch (SQLException e) {
			System.out.println(e);
		}
	}*/
	
	
}