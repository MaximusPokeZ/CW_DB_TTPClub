package ru.maximuspokez.ttpclub.ttpclub.service.Event.impl.impl;

import org.springframework.stereotype.Service;
import ru.maximuspokez.ttpclub.ttpclub.model.Equipment.Equipment;
import ru.maximuspokez.ttpclub.ttpclub.repository.Equipment.EquipmentRepository;
import ru.maximuspokez.ttpclub.ttpclub.service.Equipment.EquipmentService;

import java.util.List;

@Service
public class EquipmentServiceImpl implements EquipmentService {

  private final EquipmentRepository equipmentRepository;

  public EquipmentServiceImpl(EquipmentRepository equipmentRepository) {
    this.equipmentRepository = equipmentRepository;
  }

  @Override
  public Equipment getEquipment(Long id) {
    return equipmentRepository.findById(id).orElse(null);
  }

  @Override
  public List<Equipment> getAllEquipment() {
    return equipmentRepository.findAll();
  }

  @Override
  public Equipment createEquipment(Equipment equipment) {
    return equipmentRepository.save(equipment);
  }

  @Override
  public Equipment updateEquipment(Long id, Equipment equipment) {
    return equipmentRepository.save(equipment);
  }

  @Override
  public void deleteEquipment(Long id) {
    Equipment equipment = equipmentRepository.findById(id).orElse(null);
    if (equipment == null) {
      throw new IllegalArgumentException("Equipment with ID " + id + " not found");
    }
    equipmentRepository.delete(equipment);
  }
}
