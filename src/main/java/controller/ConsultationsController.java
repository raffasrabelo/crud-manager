package controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.Animal;
import model.Consultation;
import model.ModelException;
import model.dao.ConsultationDAO;
import model.dao.DAOFactory;
import model.dao.AnimalDAO;

@WebServlet(urlPatterns = { "/consultation/form", "/consultations", "/consultation/insert", "/consultation/update", "/consultation/delete" })
public class ConsultationsController extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		String action = req.getRequestURI();
		System.out.println(action);
		
		switch (action) {
		case "/crud-manager/consultation/form": {
			// Buscar lista de animais
			AnimalDAO animalDAO = model.dao.DAOFactory.createDAO(AnimalDAO.class);
			List<model.Animal> animals = new ArrayList<>();
			try {
				animals = animalDAO.listAll();
			} catch (model.ModelException e) {
				e.printStackTrace();
			}
			req.setAttribute("animals", animals);

			req.setAttribute("action", "insert");
			ControllerUtil.forward(req, resp, "/form-consultation.jsp");
			break;
		}
		
		case "/crud-manager/consultations": {
			CommonsController.listConsultations(req);
		    ControllerUtil.forward(req, resp, "/consultations.jsp");
		    System.out.println("Chegou aqui");
		    break;
		}

		case "/crud-manager/consultation/update": {
            try {
                int id = Integer.parseInt(req.getParameter("id"));
                ConsultationDAO consultationDAO = DAOFactory.createDAO(ConsultationDAO.class);
                AnimalDAO animalDAO = DAOFactory.createDAO(AnimalDAO.class);

                Consultation consultation = consultationDAO.findById(id);
                List<Animal> animals = animalDAO.listAll();

                req.setAttribute("consultation", consultation);
                req.setAttribute("animals", animals);
                req.setAttribute("action", "update");
                ControllerUtil.forward(req, resp, "/form-consultation.jsp");
            } catch (Exception e) {
                e.printStackTrace();
                req.setAttribute("error", "Erro ao carregar consulta para edição: " + e.getMessage());
                ControllerUtil.forward(req, resp, "/consultations.jsp");
            }
            break;
		}
		
		default:
			listConsultations(req);

			ControllerUtil.transferSessionMessagesToRequest(req);

			ControllerUtil.forward(req, resp, "/consultations.jsp");
		}
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String action = req.getRequestURI();

		switch (action) {
        case "/crud-manager/consultation/insert": {
            try {
                // 1. Receber dados do formulário
                String dataHora = req.getParameter("dataHora"); // yyyy-MM-ddTHH:mm
                String motivo = req.getParameter("motivo");
                String urgencia = req.getParameter("urgencia");
                int animalId = Integer.parseInt(req.getParameter("animalId"));

                // 2. Converter data/hora para java.util.Date
                java.time.LocalDateTime ldt = java.time.LocalDateTime.parse(dataHora);
                java.util.Date data = java.util.Date.from(ldt.atZone(java.time.ZoneId.systemDefault()).toInstant());

                // 3. Buscar animal
                AnimalDAO animalDAO = DAOFactory.createDAO(AnimalDAO.class);
                Animal animal = null;
                try {
                    animal = animalDAO.findById(animalId);
                } catch (ModelException e) {
                    e.printStackTrace();
                    req.setAttribute("error", "Erro ao buscar animal: " + e.getMessage());
                }

                // 4. Criar consulta
                Consultation cons = new Consultation();
                cons.setData(data);
                cons.setMotivo(motivo);
                cons.setUrgencia(urgencia);
                cons.setAnimal(animal);

                // 5. Salvar
                ConsultationDAO dao = DAOFactory.createDAO(ConsultationDAO.class);
                dao.save(cons);

                // 6. Redirecionar para listagem
                resp.sendRedirect(req.getContextPath() + "/consultations");
            } catch (Exception e) {
                e.printStackTrace();
                req.setAttribute("error", "Erro ao salvar consulta: " + e.getMessage());

                // Recarregar lista de animais para o formulário
                AnimalDAO animalDAO = DAOFactory.createDAO(AnimalDAO.class);
                List<Animal> animals = new ArrayList<>();
                try {
                    animals = animalDAO.listAll();
                } catch (ModelException ex) {
                    ex.printStackTrace();
                }
                req.setAttribute("animals", animals);
                req.setAttribute("action", "insert");
                ControllerUtil.forward(req, resp, "/form-consultation.jsp");
            }
            break;
        }

        case "/crud-manager/consultation/update": {
            try {
                String idStr = req.getParameter("consultationId");
                if (idStr == null || idStr.isEmpty()) {
                    throw new IllegalArgumentException("ID da consulta não informado!");
                }
                int id = Integer.parseInt(idStr);

                String dataHora = req.getParameter("dataHora");
                String motivo = req.getParameter("motivo");
                String urgencia = req.getParameter("urgencia");
                int animalId = Integer.parseInt(req.getParameter("animalId"));

                java.time.LocalDateTime ldt = java.time.LocalDateTime.parse(dataHora);
                java.util.Date data = java.util.Date.from(ldt.atZone(java.time.ZoneId.systemDefault()).toInstant());

                AnimalDAO animalDAO = DAOFactory.createDAO(AnimalDAO.class);
                Animal animal = null;
                try {
                    animal = animalDAO.findById(animalId);
                } catch (ModelException e) {
                    e.printStackTrace();
                    req.setAttribute("error", "Erro ao buscar animal: " + e.getMessage());
                }

                Consultation cons = new Consultation();
                cons.setId(id);
                cons.setData(data);
                cons.setMotivo(motivo);
                cons.setUrgencia(urgencia);
                cons.setAnimal(animal);

                ConsultationDAO dao = DAOFactory.createDAO(ConsultationDAO.class);
                dao.update(cons);

                resp.sendRedirect(req.getContextPath() + "/consultations");
            } catch (Exception e) {
                e.printStackTrace();
                req.setAttribute("error", "Erro ao atualizar consulta: " + e.getMessage());

                // Recarregar dados para o formulário de edição
                int id = Integer.parseInt(req.getParameter("consultationId"));
                ConsultationDAO consultationDAO = DAOFactory.createDAO(ConsultationDAO.class);
                AnimalDAO animalDAO = DAOFactory.createDAO(AnimalDAO.class);

                Consultation consultation = null;
                List<Animal> animals = new ArrayList<>();
                try {
                    consultation = consultationDAO.findById(id);
                    animals = animalDAO.listAll();
                } catch (ModelException ex) {
                    ex.printStackTrace();
                }

                req.setAttribute("consultation", consultation);
                req.setAttribute("animals", animals);
                req.setAttribute("action", "update");
                ControllerUtil.forward(req, resp, "/form-consultation.jsp");
            }
            break;
        }
		case "/crud-manager/consultation/delete": {
			try {
				int id = Integer.parseInt(req.getParameter("id"));
				ConsultationDAO dao = DAOFactory.createDAO(ConsultationDAO.class);

				Consultation cons = dao.findById(id);
				if (cons != null) {
					dao.delete(cons);
					req.getSession().setAttribute("message", "Consulta excluída com sucesso!");
					req.getSession().setAttribute("alertType", 1);
				} else {
					req.getSession().setAttribute("message", "Consulta não encontrada para exclusão.");
					req.getSession().setAttribute("alertType", 0);
				}
			} catch (Exception e) {
				e.printStackTrace();
				req.getSession().setAttribute("message", "Erro ao excluir consulta: " + e.getMessage());
				req.getSession().setAttribute("alertType", 0);
			}
			resp.sendRedirect(req.getContextPath() + "/consultations");
			break;
		}
    }
	}

	private void insertConsultation(HttpServletRequest req, HttpServletResponse resp) {
		Date consultationDate = ControllerUtil.formatDateTime(req.getParameter("dataHora"));
		String motivo = req.getParameter("motivo");
		String urgencia = req.getParameter("urgencia");
		int animalId = Integer.parseInt(req.getParameter("animalId"));

		// Buscar o animal selecionado
		model.dao.AnimalDAO animalDAO = model.dao.DAOFactory.createDAO(model.dao.AnimalDAO.class);
		model.Animal animal = null;
		try {
			animal = animalDAO.findById(animalId);
		} catch (model.ModelException e) {
			e.printStackTrace();
		}

		Consultation cons = new Consultation();
		cons.setData(consultationDate);
		cons.setMotivo(motivo);
		cons.setUrgencia(urgencia);
		cons.setAnimal(animal);

		ConsultationDAO dao = DAOFactory.createDAO(ConsultationDAO.class);

		try {
			if (dao.save(cons)) {
				ControllerUtil.sucessMessage(req, "Consulta do dia " + cons.getData() + " salva com sucesso.");
			} else {
				ControllerUtil.errorMessage(req, "Consulta do dia '" + cons.getData() + "' não pode ser salva.");
			}
		} catch (ModelException e) {
			e.printStackTrace();
			ControllerUtil.errorMessage(req, e.getMessage());
		}
	}

	private void listConsultations(HttpServletRequest req) {
		ConsultationDAO dao = DAOFactory.createDAO(ConsultationDAO.class);

		List<Consultation> consultations = new ArrayList<>();
		try {
			consultations = dao.listAll();
		} catch (ModelException e) {
			// Log no servidor
			e.printStackTrace();
		}

		if (consultations != null)
			req.setAttribute("consultas", consultations);
	}

}
