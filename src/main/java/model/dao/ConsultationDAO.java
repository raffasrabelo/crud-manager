package model.dao;

import java.util.List;

import model.Consultation;
import model.ModelException;

public interface ConsultationDAO {
	boolean save(Consultation consultation) throws ModelException ;
	boolean update(Consultation consultation) throws ModelException;
	boolean delete(Consultation consultation) throws ModelException;
	List<Consultation> listAll() throws ModelException;
	Consultation findById(int id) throws ModelException;
}
