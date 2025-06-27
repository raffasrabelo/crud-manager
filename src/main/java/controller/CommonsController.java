package controller;

import java.util.ArrayList;
import java.util.List;

import jakarta.servlet.http.HttpServletRequest;
import model.Consultation;
import model.ModelException;
import model.User;
import model.dao.ConsultationDAO;
import model.dao.DAOFactory;
import model.dao.UserDAO;

public class CommonsController {
	
	public static void listUsers(HttpServletRequest req) {
		UserDAO dao = DAOFactory.createDAO(UserDAO.class);
		
		List<User> listUsers = null;
		try {
			listUsers = dao.listAll();
		} catch (ModelException e) {
			// Log no servidor
			e.printStackTrace();
		}
		
		if (listUsers != null)
			req.setAttribute("users", listUsers);		
	}

	public static void listConsultations(HttpServletRequest req) {
		ConsultationDAO dao = DAOFactory.createDAO(ConsultationDAO.class);
		
		List<Consultation> listConsultations = new ArrayList<>();
		try {
			listConsultations = dao.listAll();
			System.out.println("Consultations retornadas: " + listConsultations.size());
		} catch (ModelException e) {
			e.printStackTrace();
		}
		if (listConsultations != null) {
			req.setAttribute("consultas", listConsultations);
		}
		
	}
	
	
}
