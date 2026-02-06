# Relazione Progetto JTime

**Studente**: [Nome Cognome]
**Matricola**: 119159
**Corso**: Metodologie di Programmazione / Modellazione e Gestione della Conoscenza

---

## 1. Descrizione delle Funzionalità Implementate
L'applicazione **JTime** è stata sviluppata per la gestione personalizzata di progetti e attività, offrendo strumenti per la pianificazione e la reportistica. Le funzionalità principali includono:

### Gestione Progetti
- **Creazione e Visualizzazione**: È possibile creare nuovi progetti (con nome e descrizione) e visualizzarli in una lista tabellare.
- **Stato Progetto**: I progetti possono essere marcati come "COMPLETATI" quando non ci sono attività pendenti.
- **Filtro**: Un menu a tendina permette di filtrare le operazioni per un progetto specifico.

### Gestione Attività (Task)
- **CRUD Completo**: Creazione, lettura, aggiornamento e cancellazione di attività associate a un progetto.
- **Stima e Consuntivo**: Inserimento del tempo stimato (in fase di creazione) e del tempo effettivo (in fase di completamento).
- **Stato**: Le attività possono essere marcate come completate.

### Pianificazione (Planning)
- **Vista Giornaliera**: Un calendario (DatePicker) permette di visualizzare le attività pianificate per una data specifica.
- **Assegnazione**: È possibile pianificare un'attività esistente per la data selezionata tramite la funzione "Assegna a oggi".

### Reportistica
- **Generazione Report**: Un pulsante dedicato genera un resoconto testuale dettagliato, raggruppato per progetto, che mostra lo stato di avanzamento delle attività e i tempi (stimati vs effettivi).

---

## 2. Architettura e Responsabilità
L'applicazione segue il pattern architetturale **MVC (Model-View-Controller)** per separare chiaramente la logica di business, l'interfaccia utente e i dati.

### Pattern MVC
- **Model**: Rappresenta i dati e la logica di business.
- **View**: Definita tramite FXML (JavaFX), si occupa solo della presentazione.
- **Controller**: Gestisce l'interazione tra View e Model.

### Suddivisione Responsabilità (Classi Principali)

| Classe/Package | Responsabilità Principale |
| :--- | :--- |
| `it.unicam.cs.mpgc.jtime119159.model` | Contiene le entità del dominio (`Project`, `Task`) e gli enum (`ProjectStatus`). |
| `it.unicam.cs.mpgc.jtime119159.controller` | Contiene `MainController`, che gestisce gli eventi dell'interfaccia FXML e coordina le operazioni sui dati. |
| `it.unicam.cs.mpgc.jtime119159.persistence` | Gestisce l'accesso ai dati. `SimpleRepository<T>` implementa un pattern Repository generico per le operazioni CRUD su H2. |
| `it.unicam.cs.mpgc.jtime119159.Main` | Punto di ingresso dell'applicazione (JavaFX Application), carica l'interfaccia e inizializza il contesto. |

---

## 3. Organizzazione dei Dati e Persistenza
La persistenza è gestita tramite **JPA (Java Persistence API)** e **Hibernate**, utilizzando il database embedded **H2**.

### Scelte Implementative
- **Entità JPA**: Le classi `Project` e `Task` sono annotate con `@Entity`, `@Id`, `@Column`, ecc., permettendo a Hibernate di generare automaticamente lo schema del database (`hbm2ddl.auto=update`).
- **Relazioni**: È definita una relazione `@ManyToOne` tra `Task` e `Project` (un progetto ha molte attività).
- **Repository Pattern**: La classe generica `SimpleRepository<T>` astrae le operazioni di `EntityManager` (save, update, delete, findAll), rendendo il codice del controller pulito e indipendente dai dettagli di basso livello del DB.
- **Database**: H2 è configurato in modalità "file" (`jdbc:h2:./data/jtime_db`) per garantire che i dati persistano tra diversi avvii dell'applicazione.

---

## 4. Estendibilità e Integrazione Nuove Funzionalità
Il progetto è stato pensato per essere facilmente estendibile, rispettando i principi SOLID.

### Esempi di Estensione
1.  **Aggiunta di Tag alle Attività**:
    *   Creare un'entità `Tag`.
    *   Aggiungere una relazione `@ManyToMany` in `Task`.
    *   Aggiornare la GUI per permettere la selezione dei tag.
    *   Grazie a JPA, lo schema DB si aggiornerà automaticamente.

2.  **Gestione Risorse**:
    *   Simile ai Tag, si può introdurre un'entità `Resource` (persone, strumenti).
    *   Il `SimpleRepository` è già generico e può gestire la nuova entità senza modifiche.

3.  **Nuovi Report**:
    *   La logica di reporting è attualmente nel Controller per semplicità, ma può essere facilmente estratta in una classe `ReportService` dedicata (Strategy Pattern) per supportare formati diversi (PDF, CSV) senza toccare la logica UI.

---

## 5. Strumenti Utilizzati
- **Linguaggio**: Java 17+
- **Build System**: Gradle
- **GUI**: JavaFX con FXML
- **ORM**: Hibernate / JPA
- **Database**: H2
- **IDE**: IntelliJ IDEA

---

## Conclusione
Il prototipo soddisfa i requisiti funzionali e non funzionali, fornendo una base solida e modulare per futuri sviluppi.
