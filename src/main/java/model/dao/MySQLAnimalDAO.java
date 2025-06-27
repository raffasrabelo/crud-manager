package model.dao;

import java.util.ArrayList;
import java.util.List;
import model.Animal;
import model.ModelException;

public class MySQLAnimalDAO implements AnimalDAO {

    @Override
    public boolean save(Animal animal) throws ModelException {
        DBHandler db = new DBHandler();
        String sql = "INSERT INTO animal (nome, especie, raca, idade, tutor) VALUES (?, ?, ?, ?, ?)";
        db.prepareStatement(sql);
        db.setString(1, animal.getNomeAnimal());
        db.setString(2, animal.getEspecie());
        db.setString(3, animal.getRaca());
        db.setInt(4, animal.getIdade());
        db.setString(5, animal.getTutor());
        return db.executeUpdate() > 0;
    }

    @Override
    public boolean update(Animal animal) throws ModelException {
        DBHandler db = new DBHandler();
        String sql = "UPDATE animal SET nome = ?, especie = ?, raca = ?, idade = ?, tutor = ? WHERE id = ?";
        db.prepareStatement(sql);
        db.setString(1, animal.getNomeAnimal());
        db.setString(2, animal.getEspecie());
        db.setString(3, animal.getRaca());
        db.setInt(4, animal.getIdade());
        db.setString(5, animal.getTutor());
        db.setInt(6, animal.getIdAnimal());
        return db.executeUpdate() > 0;
    }

    @Override
    public boolean delete(Animal animal) throws ModelException {
        DBHandler db = new DBHandler();
        String sql = "DELETE FROM animal WHERE id = ?";
        db.prepareStatement(sql);
        db.setInt(1, animal.getIdAnimal());
        return db.executeUpdate() > 0;
    }

    @Override
    public List<Animal> listAll() throws ModelException {
        List<Animal> animals = new ArrayList<>();
        DBHandler db = new DBHandler();
        String sql = "SELECT * FROM animal";
        db.createStatement();
        db.executeQuery(sql);
        while (db.next()) {
            Animal animal = new Animal();
            animal.setIdAnimal(db.getInt("id"));
            animal.setNomeAnimal(db.getString("nome"));
            animal.setEspecie(db.getString("especie"));
            animal.setRaca(db.getString("raca"));
            animal.setIdade(db.getInt("idade"));
            animal.setTutor(db.getString("tutor"));
            animals.add(animal);
        }
        return animals;
    }

    @Override
    public Animal findById(int id) throws ModelException {
        DBHandler db = new DBHandler();
        String sql = "SELECT * FROM animal WHERE id = ?";
        db.prepareStatement(sql);
        db.setInt(1, id);
        db.executeQuery();
        Animal animal = null;
        if (db.next()) {
            animal = new Animal();
            animal.setIdAnimal(db.getInt("id"));
            animal.setNomeAnimal(db.getString("nome"));
            animal.setEspecie(db.getString("especie"));
            animal.setRaca(db.getString("raca"));
            animal.setIdade(db.getInt("idade"));
            animal.setTutor(db.getString("tutor"));
        }
        return animal;
    }
}
