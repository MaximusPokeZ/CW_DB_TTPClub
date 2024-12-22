package ru.maximuspokez.ttpclub.ttpclub.controller.Dump;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/db")
public class DumpController {

  @GetMapping("/dump")
  public ResponseEntity<Map<String, String>> dump() {
    String downloadDirectory = "/host_downloads";
    String filePath = downloadDirectory + File.separator + "database_dump.dump";

    System.out.println("File will be saved to: " + filePath);

    try {
      ProcessBuilder processBuilder = new ProcessBuilder(
              "pg_dump",
              "-h", "ttpclub-postgres",
              "-U", "admin_ttpclub",
              "-F", "c",
              "-b",
              "-v",
              "-f", filePath,
              "ttpclub_db");

      processBuilder.environment().put("PGPASSWORD", "pc101w");

      Process process = processBuilder.start();

      StringBuilder output = new StringBuilder();
      StringBuilder errorOutput = new StringBuilder();

      new Thread(() -> {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()))) {
          String line;
          while ((line = reader.readLine()) != null) {
            output.append(line).append("\n");
          }
        } catch (IOException e) {
          e.printStackTrace();
        }
      }).start();

      new Thread(() -> {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(process.getErrorStream()))) {
          String line;
          while ((line = reader.readLine()) != null) {
            errorOutput.append(line).append("\n");
          }
        } catch (IOException e) {
          e.printStackTrace();
        }
      }).start();

      process.waitFor();

      System.out.println("pg_dump Output: " + output);
      System.out.println("pg_dump Error Output: " + errorOutput);

      if (process.exitValue() == 0) {
        return ResponseEntity.ok(Map.of("filePath", filePath));
      } else {
        throw new RuntimeException("pg_dump failed: " + errorOutput);
      }
    } catch (Exception e) {
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
              .body(Map.of("error", e.getMessage()));
    }
  }



  @PostMapping("/restore")
  public ResponseEntity<String> restoreDatabase(@RequestParam("file") MultipartFile file) {
    if (file.isEmpty()) {
      return ResponseEntity.badRequest().body("Файл для восстановления не предоставлен.");
    }

    try {
      File tempFile = File.createTempFile("db_restore_", ".dump");
      file.transferTo(tempFile);

      ProcessBuilder processBuilder = new ProcessBuilder(
              "pg_restore",
              "--clean",
              "-h", "ttpclub-postgres",
              "-U", "admin_ttpclub",
              "-d", "ttpclub_db",
              "-v",
              tempFile.getAbsolutePath()
      );
      processBuilder.environment().put("PGPASSWORD", "pc101w");

      Process process = processBuilder.start();
      process.waitFor();

      if (process.exitValue() == 0) {
        tempFile.delete();
        return ResponseEntity.ok("База данных успешно восстановлена.");
      } else {
        String errorOutput = new String(process.getErrorStream().readAllBytes());
        throw new RuntimeException("Ошибка восстановления базы данных: " + errorOutput);
      }
    } catch (IOException | InterruptedException e) {
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
              .body("Ошибка при восстановлении базы данных: " + e.getMessage());
    }
  }

}
