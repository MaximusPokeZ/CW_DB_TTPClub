package ru.maximuspokez.ttpclub.ttpclub.service.Equipment;

import ru.maximuspokez.ttpclub.ttpclub.model.Equipment.Equipment;

import java.util.List;

public interface EquipmentService {
  Equipment getEquipment(Long id);
  List<Equipment> getAllEquipment();
  Equipment createEquipment(Equipment equipment);
  Equipment updateEquipment(Long id, Equipment equipment);
  void deleteEquipment(Long id);
}
