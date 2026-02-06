package it.unicam.cs.mpgc.jtime119159.controller;

import it.unicam.cs.mpgc.jtime119159.model.Project;
import it.unicam.cs.mpgc.jtime119159.persistence.SimpleRepository;
import javafx.fxml.FXML;

public class MainController {

    private SimpleRepository<Project> projectRepository;
    private SimpleRepository<it.unicam.cs.mpgc.jtime119159.model.Task> taskRepository;

    @FXML
    private javafx.scene.control.TableView<Project> projectTable;
    @FXML
    private javafx.scene.control.TableColumn<Project, String> projectNameColumn;
    @FXML
    private javafx.scene.control.TableColumn<Project, String> projectDescriptionColumn;
    @FXML
    private javafx.scene.control.TableColumn<Project, String> projectStatusColumn;

    @FXML
    private javafx.scene.control.ComboBox<Project> projectFilterComboBox;
    @FXML
    private javafx.scene.control.TableView<it.unicam.cs.mpgc.jtime119159.model.Task> taskTable;
    @FXML
    private javafx.scene.control.TableColumn<it.unicam.cs.mpgc.jtime119159.model.Task, String> taskNameColumn;
    @FXML
    private javafx.scene.control.TableColumn<it.unicam.cs.mpgc.jtime119159.model.Task, String> taskDescriptionColumn;
    @FXML
    private javafx.scene.control.TableColumn<it.unicam.cs.mpgc.jtime119159.model.Task, String> taskProjectColumn;
    @FXML
    private javafx.scene.control.TableColumn<it.unicam.cs.mpgc.jtime119159.model.Task, String> taskEstimatedColumn;
    @FXML
    private javafx.scene.control.TableColumn<it.unicam.cs.mpgc.jtime119159.model.Task, String> taskActualColumn;
    @FXML
    private javafx.scene.control.TableColumn<it.unicam.cs.mpgc.jtime119159.model.Task, String> taskDateColumn;
    @FXML
    private javafx.scene.control.TableColumn<it.unicam.cs.mpgc.jtime119159.model.Task, String> taskCompletedColumn;
    @FXML
    private javafx.scene.control.DatePicker planningDatePicker;
    @FXML
    private javafx.scene.control.TableView<it.unicam.cs.mpgc.jtime119159.model.Task> planningTable;
    @FXML
    private javafx.scene.control.TableColumn<it.unicam.cs.mpgc.jtime119159.model.Task, String> planningTaskColumn;
    @FXML
    private javafx.scene.control.TableColumn<it.unicam.cs.mpgc.jtime119159.model.Task, String> planningProjectColumn;
    @FXML
    private javafx.scene.control.TableColumn<it.unicam.cs.mpgc.jtime119159.model.Task, String> planningTimeColumn;
    @FXML
    private javafx.scene.control.TableColumn<it.unicam.cs.mpgc.jtime119159.model.Task, String> planningStatusColumn;

    @FXML
    public void initialize() {
        this.projectRepository = new SimpleRepository<>(Project.class);
        this.taskRepository = new SimpleRepository<>(it.unicam.cs.mpgc.jtime119159.model.Task.class);

        // Project Table Setup
        projectNameColumn.setCellValueFactory(
                cellData -> new javafx.beans.property.SimpleStringProperty(cellData.getValue().getName()));
        projectDescriptionColumn.setCellValueFactory(
                cellData -> new javafx.beans.property.SimpleStringProperty(cellData.getValue().getDescription()));
        projectStatusColumn.setCellValueFactory(
                cellData -> new javafx.beans.property.SimpleStringProperty(cellData.getValue().getStatus().toString()));

        // Task Table Setup
        taskNameColumn.setCellValueFactory(
                cellData -> new javafx.beans.property.SimpleStringProperty(cellData.getValue().getName()));
        taskDescriptionColumn.setCellValueFactory(
                cellData -> new javafx.beans.property.SimpleStringProperty(cellData.getValue().getDescription()));
        taskProjectColumn.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(
                cellData.getValue().getProject() != null ? cellData.getValue().getProject().getName() : "N/A"));
        taskEstimatedColumn.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(
                cellData.getValue().getEstimatedMinutes() != null ? cellData.getValue().getEstimatedMinutes().toString()
                        : ""));
        taskActualColumn.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(
                cellData.getValue().getActualMinutes() != null ? cellData.getValue().getActualMinutes().toString()
                        : ""));
        taskDateColumn.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(
                cellData.getValue().getScheduledDate() != null ? cellData.getValue().getScheduledDate().toString()
                        : ""));
        taskCompletedColumn.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(
                cellData.getValue().isCompleted() ? "Si" : "No"));

        // Planning Table Setup
        planningTaskColumn.setCellValueFactory(
                cellData -> new javafx.beans.property.SimpleStringProperty(cellData.getValue().getName()));
        planningProjectColumn.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(
                cellData.getValue().getProject() != null ? cellData.getValue().getProject().getName() : "N/A"));
        planningTimeColumn.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(
                cellData.getValue().getEstimatedMinutes() != null ? cellData.getValue().getEstimatedMinutes().toString()
                        : ""));
        planningStatusColumn.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(
                cellData.getValue().isCompleted() ? "Si" : "No"));

        planningDatePicker.setValue(java.time.LocalDate.now());

        // Setup Project ComboBox
        projectFilterComboBox.setConverter(new javafx.util.StringConverter<Project>() {
            @Override
            public String toString(Project project) {
                return project == null ? "Tutti i progetti" : project.getName();
            }

            @Override
            public Project fromString(String string) {
                return null;
            }
        });

        handleRefreshProjects();
        handleFilterTasks();
        handlePlanningDateChange();
    }

    @FXML
    public void handlePlanningDateChange() {
        java.time.LocalDate date = planningDatePicker.getValue();
        if (date != null) {
            java.util.List<it.unicam.cs.mpgc.jtime119159.model.Task> tasks = taskRepository.findAll().stream()
                    .filter(t -> date.equals(t.getScheduledDate()))
                    .toList();
            planningTable.setItems(javafx.collections.FXCollections.observableArrayList(tasks));
        }
    }

    @FXML
    public void handleAssignToDate() {
        // Assign selected task from Task View to the selected date in Planning View
        // Or open a dialog to pick tasks.
        // Let's implement assigning the task selected in the MAIN task table to the
        // currently selected date.

        it.unicam.cs.mpgc.jtime119159.model.Task selectedTask = taskTable.getSelectionModel().getSelectedItem();
        java.time.LocalDate date = planningDatePicker.getValue();

        if (selectedTask != null && date != null) {
            selectedTask.setScheduledDate(date);
            taskRepository.update(selectedTask);
            handleFilterTasks(); // Update main table
            handlePlanningDateChange(); // Update planning table
        } else {
            showAlert(javafx.scene.control.Alert.AlertType.WARNING, "Operazione non valida",
                    "Seleziona un'attività dalla tabella 'Attività' e una data.");
        }
    }

    @FXML
    private javafx.scene.control.TextArea reportTextArea;

    @FXML
    public void handleGenerateReport() {
        StringBuilder report = new StringBuilder();
        report.append("=== JTime Report ===\n");
        report.append("Generato il: ").append(java.time.LocalDateTime.now()).append("\n\n");

        java.util.List<Project> projects = projectRepository.findAll();
        for (Project project : projects) {
            report.append("Progetto: ").append(project.getName())
                    .append(" (").append(project.getStatus()).append(")\n");

            // Filter tasks for this project
            java.util.List<it.unicam.cs.mpgc.jtime119159.model.Task> projectTasks = taskRepository.findAll().stream()
                    .filter(t -> t.getProject() != null && t.getProject().getId().equals(project.getId()))
                    .toList();

            long completedCount = projectTasks.stream().filter(it.unicam.cs.mpgc.jtime119159.model.Task::isCompleted)
                    .count();
            long totalCount = projectTasks.size();

            report.append("  Attività completate: ").append(completedCount).append("/").append(totalCount).append("\n");

            for (it.unicam.cs.mpgc.jtime119159.model.Task task : projectTasks) {
                report.append("    - [")
                        .append(task.isCompleted() ? "X" : " ")
                        .append("] ")
                        .append(task.getName());
                if (task.getScheduledDate() != null) {
                    report.append(" (Scadenza: ").append(task.getScheduledDate()).append(")");
                }
                report.append("\n");
            }
            report.append("\n");
        }

        reportTextArea.setText(report.toString());
    }

    @FXML
    public void handleRefreshProjects() {
        java.util.List<Project> projects = projectRepository.findAll();
        projectTable.setItems(javafx.collections.FXCollections.observableArrayList(projects));

        // Update ComboBox but keep selection if possible (simplified here)
        Project selected = projectFilterComboBox.getValue();
        projectFilterComboBox.setItems(javafx.collections.FXCollections.observableArrayList(projects));
        // Add a null option for "All" is tricky with standard ComboBox used this way,
        // usually better to have a "All" item or clear button.
        // For now, let's just create a wrapper or handle null selection as "All".
        // The combo box is populated directly with Projects.
        projectFilterComboBox.getItems().add(0, null); // Add null for "All"
        projectFilterComboBox.setValue(selected);

        handleFilterTasks();
    }

    @FXML
    public void handleFilterTasks() {
        Project selectedProject = projectFilterComboBox.getValue();
        java.util.List<it.unicam.cs.mpgc.jtime119159.model.Task> tasks = taskRepository.findAll();

        if (selectedProject != null) {
            tasks = tasks.stream()
                    .filter(t -> t.getProject() != null && t.getProject().getId().equals(selectedProject.getId()))
                    .collect(java.util.stream.Collectors.toList());
        }

        taskTable.setItems(javafx.collections.FXCollections.observableArrayList(tasks));
    }

    @FXML
    public void handleAddTask() {
        // We need a Project to be selected (either in filter or ask in dialog)
        // For simplicity, let's require a project selection in the filter or show a
        // dialog to pick one.
        // Let's implement a simple Task Creation Dialog.

        java.util.List<Project> activeProjects = projectRepository.findAll().stream()
                .filter(p -> p.getStatus() == it.unicam.cs.mpgc.jtime119159.model.ProjectStatus.ACTIVE)
                .toList();

        if (activeProjects.isEmpty()) {
            showAlert(javafx.scene.control.Alert.AlertType.WARNING, "Nessun progetto attivo",
                    "Devi creare un progetto attivo prima di aggiungere attività.");
            return;
        }

        javafx.scene.control.Dialog<it.unicam.cs.mpgc.jtime119159.model.Task> dialog = new javafx.scene.control.Dialog<>();
        dialog.setTitle("Nuova Attività");
        dialog.setHeaderText("Inserisci i dettagli dell'attività");

        javafx.scene.control.ButtonType loginButtonType = new javafx.scene.control.ButtonType("Crea",
                javafx.scene.control.ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(loginButtonType, javafx.scene.control.ButtonType.CANCEL);

        javafx.scene.layout.GridPane grid = new javafx.scene.layout.GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new javafx.geometry.Insets(20, 150, 10, 10));

        javafx.scene.control.TextField nameField = new javafx.scene.control.TextField();
        nameField.setPromptText("Nome attività");
        javafx.scene.control.TextField descField = new javafx.scene.control.TextField();
        descField.setPromptText("Descrizione");
        javafx.scene.control.ComboBox<Project> projectCombo = new javafx.scene.control.ComboBox<>();
        projectCombo.setItems(javafx.collections.FXCollections.observableArrayList(activeProjects));
        projectCombo.setConverter(new javafx.util.StringConverter<Project>() {
            @Override
            public String toString(Project project) {
                return project == null ? "" : project.getName();
            }

            @Override
            public Project fromString(String string) {
                return null;
            }
        });
        // Default to filter selection if valid
        if (projectFilterComboBox.getValue() != null && activeProjects.contains(projectFilterComboBox.getValue())) {
            projectCombo.setValue(projectFilterComboBox.getValue());
        }

        grid.add(new javafx.scene.control.Label("Progetto:"), 0, 0);
        grid.add(projectCombo, 1, 0);
        grid.add(new javafx.scene.control.Label("Nome:"), 0, 1);
        grid.add(nameField, 1, 1);
        grid.add(new javafx.scene.control.Label("Descrizione:"), 0, 2);
        grid.add(descField, 1, 2);

        javafx.scene.control.TextField estimatedField = new javafx.scene.control.TextField();
        estimatedField.setPromptText("Minuti stimati");
        grid.add(new javafx.scene.control.Label("Stima (min):"), 0, 3);
        grid.add(estimatedField, 1, 3);

        dialog.getDialogPane().setContent(grid);

        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == loginButtonType) {
                if (projectCombo.getValue() != null && !nameField.getText().trim().isEmpty()) {
                    it.unicam.cs.mpgc.jtime119159.model.Task t = new it.unicam.cs.mpgc.jtime119159.model.Task();
                    t.setName(nameField.getText());
                    t.setDescription(descField.getText());
                    t.setProject(projectCombo.getValue());
                    try {
                        if (!estimatedField.getText().trim().isEmpty()) {
                            t.setEstimatedMinutes(Long.parseLong(estimatedField.getText().trim()));
                        }
                    } catch (NumberFormatException e) {
                        // Ignore invalid number or handle error
                    }
                    return t;
                }
            }
            return null;
        });

        java.util.Optional<it.unicam.cs.mpgc.jtime119159.model.Task> result = dialog.showAndWait();

        result.ifPresent(task -> {
            taskRepository.save(task);
            handleFilterTasks();
        });
    }

    @FXML
    public void handleDeleteTask() {
        it.unicam.cs.mpgc.jtime119159.model.Task selected = taskTable.getSelectionModel().getSelectedItem();
        if (selected != null) {
            taskRepository.delete(selected);
            handleFilterTasks();
        } else {
            showAlert(javafx.scene.control.Alert.AlertType.WARNING, "Nessuna selezione",
                    "Seleziona un'attività da eliminare.");
        }
    }

    @FXML
    public void handleCompleteTask() {
        it.unicam.cs.mpgc.jtime119159.model.Task selected = taskTable.getSelectionModel().getSelectedItem();
        if (selected != null) {
            javafx.scene.control.TextInputDialog dialog = new javafx.scene.control.TextInputDialog();
            dialog.setTitle("Completa Attività");
            dialog.setHeaderText("Attività completata: " + selected.getName());
            dialog.setContentText("Inserisci la durata effettiva (minuti):");

            java.util.Optional<String> result = dialog.showAndWait();
            result.ifPresent(minutes -> {
                try {
                    if (!minutes.trim().isEmpty()) {
                        selected.setActualMinutes(Long.parseLong(minutes.trim()));
                    }
                    selected.setCompleted(true);
                    taskRepository.update(selected);
                    handleFilterTasks();
                } catch (NumberFormatException e) {
                    showAlert(javafx.scene.control.Alert.AlertType.ERROR, "Errore", "Inserisci un numero valido.");
                }
            });
        } else {
            showAlert(javafx.scene.control.Alert.AlertType.WARNING, "Nessuna selezione",
                    "Seleziona un'attività da completare.");
        }
    }

    @FXML
    public void handleAddProject() {
        javafx.scene.control.TextInputDialog dialog = new javafx.scene.control.TextInputDialog();
        dialog.setTitle("Nuovo Progetto");
        dialog.setHeaderText("Crea un nuovo progetto");
        dialog.setContentText("Nome del progetto:");

        java.util.Optional<String> result = dialog.showAndWait();
        result.ifPresent(name -> {
            if (!name.trim().isEmpty()) {
                Project newProject = new Project(name, "Descrizione di default"); // In a real app, ask for description
                                                                                  // too
                projectRepository.save(newProject);
                handleRefreshProjects();
            }
        });
    }

    @FXML
    public void handleCloseProject() {
        Project selectedProject = projectTable.getSelectionModel().getSelectedItem();
        if (selectedProject != null) {
            if (selectedProject.getTasks().isEmpty()) { // Simplified check (should check active tasks)
                selectedProject.setStatus(it.unicam.cs.mpgc.jtime119159.model.ProjectStatus.COMPLETED);
                projectRepository.update(selectedProject);
                handleRefreshProjects();
            } else {
                showAlert(javafx.scene.control.Alert.AlertType.WARNING, "Attenzione",
                        "Il progetto ha attività pendenti.");
            }
        } else {
            showAlert(javafx.scene.control.Alert.AlertType.WARNING, "Nessuna selezione",
                    "Seleziona un progetto dalla tabella.");
        }
    }

    private void showAlert(javafx.scene.control.Alert.AlertType type, String title, String content) {
        javafx.scene.control.Alert alert = new javafx.scene.control.Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
}
