package model;


import java.text.SimpleDateFormat;
import java.util.Date;

public class Consultation {
	private int id;
	private Date data;
	private String motivo;
	private String urgencia;
	private Animal animal;
	
	public Consultation() {
		
	}
	
	public Consultation(int id) {
		
	}
		
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public Date getData() {
		return data;
	}
	public void setData(Date data) {
		this.data = data;
	
	}
	public String getMotivo() {
		return motivo;
	}
	public void setMotivo(String motivo) {
		this.motivo = motivo;
	}
	public String getUrgencia() {
		return urgencia;
	}
	public void setUrgencia(String urgencia) {
		this.urgencia = urgencia;
	}
	public Animal getAnimal() {
		return animal;
	}
	public void setAnimal(Animal animal) {
		this.animal = animal;
	}
	
	public String getDataHoraFormatada() {
        if (data == null) return "";
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        return sdf.format(data);
    }
	
	public String getDataHoraLocal() {
    if (this.data == null) return "";
    java.time.ZoneId zone = java.time.ZoneId.systemDefault();
    java.time.LocalDateTime ldt = this.data.toInstant().atZone(zone).toLocalDateTime();
    return ldt.format(java.time.format.DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm"));
}
}
