package ru.maximuspokez.ttpclub.ttpclub.controller.Equipment;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.maximuspokez.ttpclub.ttpclub.model.Equipment.Equipment;
import ru.maximuspokez.ttpclub.ttpclub.service.Equipment.EquipmentService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/equipment")
public class EquipmentController {

  private final EquipmentService equipmentService;

  public EquipmentController(EquipmentService equipmentService) {
    this.equipmentService = equipmentService;
  }

  @GetMapping
  public ResponseEntity<List<Equipment>> getAll() {
    List<Equipment> equipment = equipmentService.getAllEquipment();
    return ResponseEntity.ok(equipment);
  }

  @GetMapping("/{id}")
  public ResponseEntity<Equipment> getById(@PathVariable Long id) {
    Equipment equipment = equipmentService.getEquipment(id);
    if (equipment == null) {
      return ResponseEntity.notFound().build();
    }
    return ResponseEntity.ok(equipment);
  }

  @PostMapping
  public ResponseEntity<Equipment> createEquipment(@RequestBody Equipment equipment) {
    Equipment newEquipment = equipmentService.createEquipment(equipment);
    return ResponseEntity.ok(newEquipment);
  }

  @PutMapping("/{id}")
  public ResponseEntity<Equipment> updateEquipment(@PathVariable Long id, @RequestBody Equipment equipment) {
    Equipment newEquipment = equipmentService.updateEquipment(id, equipment);
    return ResponseEntity.ok(newEquipment);
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Equipment> deleteEquipment(@PathVariable Long id) {
    equipmentService.deleteEquipment(id);
    return ResponseEntity.ok().build();
  }
}
