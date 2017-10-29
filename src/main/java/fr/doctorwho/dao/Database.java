package fr.doctorwho.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitTask;

import fr.doctorwho.utils.SQLConnection;



/* DataBase management */
public class Database extends SQLConnection implements Runnable{
	
	private static BukkitTask task;
	

	/** Reconnect to the DB every hour*/
	public Database() {
		super("urlbase", "host", "database", "username", "password");
		
		 // 20 tick * 60 Seconde * 60 minute
		task = Bukkit.getScheduler().runTaskTimer(API.getInstance(), this, 0, 20 * 60 * 60);
	}
	
	
	// reload every hour
	@Override
	public void run() {
		disconnect();
		connect();
	}
	
	
	public int getAllID(String table){
		int value = 0;
		try{
			PreparedStatement ps = prepareStatement("SELECT id FROM " + table);
			ResultSet rs = ps.executeQuery();
			while(rs.next()){
				value = rs.getInt("id");
			}
			ps.close();
		}catch(SQLException e){
			e.printStackTrace();
		}
		return value;
	}
	
	public PreparedStatement prepareStatement(String value){
		try {
			return getConnection().prepareStatement(value);
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
}