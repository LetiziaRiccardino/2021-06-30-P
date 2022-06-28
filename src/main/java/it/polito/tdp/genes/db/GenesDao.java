package it.polito.tdp.genes.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import it.polito.tdp.genes.model.Adiacenza;
import it.polito.tdp.genes.model.Genes;
import it.polito.tdp.genes.model.Interactions;


public class GenesDao {
	
	public List<Genes> getAllGenes(){
		String sql = "SELECT DISTINCT GeneID, Essential, Chromosome FROM Genes";
		List<Genes> result = new ArrayList<Genes>();
		Connection conn = DBConnect.getConnection();

		try {
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet res = st.executeQuery();
			while (res.next()) {

				Genes genes = new Genes(res.getString("GeneID"), 
						res.getString("Essential"), 
						res.getInt("Chromosome"));
				result.add(genes);
			}
			res.close();
			st.close();
			conn.close();
			return result;
			
		} catch (SQLException e) {
			throw new RuntimeException("Database error", e) ;
		}
	}
	
	public List<String> getLocalization(){
		String sql="SELECT DISTINCT Localization "
				+ "FROM classification";
		List<String> loc= new ArrayList<>();
		Connection conn = DBConnect.getConnection();
		try {
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet res = st.executeQuery();
			while (res.next()) {
				loc.add(res.getString("Localization"));
			}
			res.close();
			st.close();
			conn.close();
			
		} catch (SQLException e) {
			throw new RuntimeException("Database error", e) ;
		}
		return loc;
	}
	
	public List<Adiacenza> getArchi(){
		String sql ="SELECT c1.Localization, c2.Localization, count(DISTINCT Type) AS peso "
				+ "FROM classification c1, classification c2, interactions i "
				+ "WHERE c1.GeneID=i.GeneID1 AND c2.GeneID=i.GeneID2 AND c1.GeneID<c2.GeneID AND c1.Localization<>c2.Localization "
				+ "GROUP BY c1.Localization, c2.Localization "
				+ "ORDER BY c1.Localization, c2.Localization ";
		List<Adiacenza> archi= new ArrayList<Adiacenza>();
		Connection conn = DBConnect.getConnection();
		try {
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet res = st.executeQuery();
			while (res.next()) {
				archi.add(new Adiacenza(res.getString("c1.Localization"), res.getString("c2.Localization"), res.getInt("peso")));
			}
			res.close();
			st.close();
			conn.close();
			
		} catch (SQLException e) {
			throw new RuntimeException("Database error", e) ;
		}
		return archi;
	}
	
	


	
}
