package model.dao;


import java.util.ArrayList;
import java.util.List;

import model.Animal;
import model.Consultation;
import model.ModelException;
import model.Post;
import model.User;

public class MySQLConsultationDAO implements ConsultationDAO {

	@Override
	public boolean save(Consultation consultation) throws ModelException {
	    DBHandler db = new DBHandler();

	    String sqlInsert = "INSERT INTO consulta (data_consulta, horario, motivo, urgencia, id_animal) VALUES (?, ?, ?, ?, ?)";
	    db.prepareStatement(sqlInsert);

	    // Supondo que consultation.getData() é um java.util.Date com data e hora juntos
	    java.util.Date dataHora = consultation.getData();
	    java.sql.Date dataConsulta = null;
	    java.sql.Time horario = null;
	    if (dataHora != null) {
	        dataConsulta = new java.sql.Date(dataHora.getTime());
	        horario = new java.sql.Time(dataHora.getTime());
	    }

	    db.setDate(1, dataConsulta);
	    db.setTime(2, horario);
	    db.setString(3, consultation.getMotivo());
	    db.setString(4, consultation.getUrgencia());
	    db.setInt(5, consultation.getAnimal().getIdAnimal());

	    return db.executeUpdate() > 0;
	}

	@Override
	public boolean update(Consultation consultation) throws ModelException {
		DBHandler db = new DBHandler();
		String sql = "UPDATE consulta SET data_consulta = ?, horario = ?, motivo = ?, urgencia = ?, id_animal = ? WHERE id = ?";
		db.prepareStatement(sql);

		java.util.Date dataHora = consultation.getData();
		java.sql.Date dataConsulta = null;
		java.sql.Time horario = null;
		if (dataHora != null) {
			dataConsulta = new java.sql.Date(dataHora.getTime());
			horario = new java.sql.Time(dataHora.getTime());
		}

		db.setDate(1, dataConsulta);
		db.setTime(2, horario);
		db.setString(3, consultation.getMotivo());
		db.setString(4, consultation.getUrgencia());
		db.setInt(5, consultation.getAnimal().getIdAnimal());
		db.setInt(6, consultation.getId());

		return db.executeUpdate() > 0;
	}

	@Override
	public boolean delete(Consultation consultation) throws ModelException {
		DBHandler db = new DBHandler();
		String sql = "DELETE FROM consulta WHERE id = ?";
		db.prepareStatement(sql);
		db.setInt(1, consultation.getId());
		return db.executeUpdate() > 0;
	}

	@Override
	public List<Consultation> listAll() throws ModelException {
	    List<Consultation> consultations = new ArrayList<>();
	    DBHandler db = new DBHandler();

	    String sql = "SELECT c.*, a.nome AS nomeAnimal, a.tutor, a.id AS id_animal " +
             "FROM consulta c " +
             "INNER JOIN animal a ON c.id_animal = a.id " +
			 "ORDER BY c.data_consulta ASC, c.horario ASC";

	    db.createStatement();
	    db.executeQuery(sql);

	    while (db.next()) {
	        Consultation cons = new Consultation();
	        cons.setId(db.getInt("id"));

	        // Junta data_consulta e horario em um Date
	        java.util.Date dataConsulta = db.getDate("data_consulta");
	        java.sql.Time horario = db.getTime("horario");
	        if (dataConsulta != null && horario != null) {
	            java.util.Calendar cal = java.util.Calendar.getInstance();
	            cal.setTime(dataConsulta);
	            java.util.Calendar calHora = java.util.Calendar.getInstance();
	            calHora.setTime(horario);
	            cal.set(java.util.Calendar.HOUR_OF_DAY, calHora.get(java.util.Calendar.HOUR_OF_DAY));
	            cal.set(java.util.Calendar.MINUTE, calHora.get(java.util.Calendar.MINUTE));
	            cal.set(java.util.Calendar.SECOND, calHora.get(java.util.Calendar.SECOND));
	            cal.set(java.util.Calendar.MILLISECOND, 0);
	            cons.setData(cal.getTime());
	        }

	        cons.setMotivo(db.getString("motivo"));
	        cons.setUrgencia(db.getString("urgencia"));

	        Animal animal = new Animal();
	        animal.setIdAnimal(db.getInt("id_animal"));
	        animal.setNomeAnimal(db.getString("nomeAnimal"));
	        animal.setTutor(db.getString("tutor"));
	        cons.setAnimal(animal);

	        consultations.add(cons);
	    }

	    return consultations; 
	}
	@Override
	public Consultation findById(int id) throws ModelException {
	    DBHandler db = new DBHandler();
	    String sql = "SELECT c.*, a.nome AS nomeAnimal, a.tutor, a.id AS id_animal " +
	                 "FROM consulta c " +
	                 "INNER JOIN animal a ON c.id_animal = a.id " +
	                 "WHERE c.id = ?";
	    db.prepareStatement(sql);
	    db.setInt(1, id);
	    db.executeQuery();

	    Consultation cons = null;
	    if (db.next()) {
	        cons = new Consultation();
	        cons.setId(db.getInt("id"));

	        // Junta data_consulta e horario em um Date
	        java.util.Date dataConsulta = db.getDate("data_consulta");
	        java.sql.Time horario = db.getTime("horario");
	        if (dataConsulta != null && horario != null) {
	            java.util.Calendar cal = java.util.Calendar.getInstance();
	            cal.setTime(dataConsulta);
	            java.util.Calendar calHora = java.util.Calendar.getInstance();
	            calHora.setTime(horario);
	            cal.set(java.util.Calendar.HOUR_OF_DAY, calHora.get(java.util.Calendar.HOUR_OF_DAY));
	            cal.set(java.util.Calendar.MINUTE, calHora.get(java.util.Calendar.MINUTE));
	            cal.set(java.util.Calendar.SECOND, calHora.get(java.util.Calendar.SECOND));
	            cal.set(java.util.Calendar.MILLISECOND, 0);
	            cons.setData(cal.getTime());
	        }

	        cons.setMotivo(db.getString("motivo"));
	        cons.setUrgencia(db.getString("urgencia"));

	        Animal animal = new Animal();
	        animal.setIdAnimal(db.getInt("id_animal"));
	        animal.setNomeAnimal(db.getString("nomeAnimal"));
	        animal.setTutor(db.getString("tutor"));
	        cons.setAnimal(animal);
	    }
	    return cons;
	}
}
	
