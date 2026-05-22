package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import model.Client;
import model.LinkedList.CircularDoublyLinkedList;
import model.LinkedList.CircularLinkedList;
import model.LinkedList.CircularListRow;
import model.LinkedList.ClientRow;
import model.LinkedList.ListException;
import model.LinkedList.ProductRow;
import model.Product;
import model.queue.PriorityLinkedQueue;
import model.queue.QueueException;
import model.stack.LinkedStack;
import model.stack.StackException;
import model.LinkedList.StackRow;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class MainController {

    @FXML private BorderPane bpCenter;
    @FXML private TabPane    mainTabs;

    @FXML private AnchorPane  paneCircularList;
    @FXML private TextField   txfCircularValue;
    @FXML private TextField   txfCircularRep;
    @FXML private Label       lblCircularStatus;
    @FXML private TableView   tableCircularData;
    @FXML private TableColumn colCircIndex;
    @FXML private TableColumn colCircData;
    @FXML private TableColumn colCircNext;
    @FXML private TableColumn colCircPrev;
    @FXML private TableColumn colCircHead;
    @FXML private TableColumn colCircTail;
    @FXML private TextArea    txtAreaCircularLog;
    @FXML private Button      btnCircAddFirst;
    @FXML private Button      btnCircAddLast;
    @FXML private Button      btnCircAddRandom;
    @FXML private Button      btnCircSearch;
    @FXML private Button      btnCircRemove;
    @FXML private Button      btnCircClean;

    @FXML private AnchorPane  paneCircDoublyList;
    @FXML private TextField   txfCircDoublyRep;
    @FXML private Label       lblCircDoublyStatus;
    @FXML private TableView   tableCircDoublyData;
    @FXML private TableColumn colProdId;
    @FXML private TableColumn colProdName;
    @FXML private TableColumn colProdPrice;
    @FXML private TableColumn colProdStock;
    @FXML private TableColumn colProdType;
    @FXML private TableColumn colProdRegDate;
    @FXML private TextArea    txtAreaCircDoublyLog;
    @FXML private TextField   txfProdId;
    @FXML private TextField   txfProdName;
    @FXML private TextField   txfProdPrice;
    @FXML private TextField   txfProdStock;
    @FXML private ComboBox    cbProductType;
    @FXML private DatePicker  dpProdRegDate;
    @FXML private Button      btnProdAdd;
    @FXML private Button      btnProdSearch;
    @FXML private Button      btnProdCleanForm;
    @FXML private Button      btnProdSortName;
    @FXML private Button      btnProdSortStock;
    @FXML private Button      btnProdRemove;
    @FXML private Button      btnProdRemoveFirst;
    @FXML private Button      btnProdRemoveLast;

    @FXML private AnchorPane  paneStackSimulation;
    @FXML private TextField   txfStackValue;
    @FXML private TextField   txfStackRep;
    @FXML private Label       lblStackStatus;
    @FXML private TableView   tableStackData;
    @FXML private TableColumn colStackNode;
    @FXML private TableColumn colStackData;
    @FXML private TableColumn colStackNext;
    @FXML private TextArea    txtAreaStackLog;
    @FXML private Button      btnStackPush;
    @FXML private Button      btnStackPushRandom;
    @FXML private Button      btnStackSearch;
    @FXML private Button      btnStackPop;
    @FXML private Button      btnStackRemoveValue;
    @FXML private Button      btnStackClean;

    @FXML private AnchorPane  panePrioritySim;
    @FXML private TextField   txfPriorityRep;
    @FXML private Label       lblPriorityStatus;
    @FXML private TableView   tablePriorityData;
    @FXML private TableColumn colPId;
    @FXML private TableColumn colPName;
    @FXML private TableColumn colPAge;
    @FXML private TableColumn colPService;
    @FXML private TableColumn colPDate;
    @FXML private TextArea    txtAreaPriorityLog;
    @FXML private TextField   txfClientId;
    @FXML private TextField   txfClientName;
    @FXML private TextField   txfClientAge;
    @FXML private ComboBox    cbBankService;
    @FXML private DatePicker  dpClientDate;
    @FXML private Button      btnEnqueue;
    @FXML private Button      btnEnqueueRandom;
    @FXML private Button      btnPriorityCleanForm;
    @FXML private Button      btnPriorityPeek;
    @FXML private Button      btnPriorityIndexof;
    @FXML private Button      btnDequeue;
    @FXML private Button      btnRemoveClient;

    private final CircularLinkedList<Integer>       circularList  = new CircularLinkedList<>();
    private final CircularDoublyLinkedList<Product> doublyList    = new CircularDoublyLinkedList<>();
    private final LinkedStack<Integer>              stack         = new LinkedStack<>();
    private final PriorityLinkedQueue<Client>       priorityQueue = new PriorityLinkedQueue<>();

    private static final DateTimeFormatter DATE_FMT      = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    private static final String[]          RAND_NAMES    = {"Carlos","María","Juan","Ana","Pedro","Laura","José","Carmen","Luis","Rosa"};
    private static final String[]          RAND_SERVICES = {"Plataforma","Cajas","Créditos y Préstamos","Gestión de Cuentas","Atención al Cliente y Reclamos"};

    @FXML
    public void initialize() {
        setupCircularTable();
        wireCircularButtons();
        lblCircularStatus.setText("Lista vacía");

        setupDoublyTable();
        wireDoublyButtons();
        lblCircDoublyStatus.setText("Lista inicializada vacía.");
        refreshDoubly();

        setupStackTable();
        wireStackButtons();
        lblStackStatus.setText("Pila vacía");
        refreshStack();

        setupQueueTable();
        wireQueueButtons();
        lblPriorityStatus.setText("Cola inicializada vacía.");
        refreshQueue();
    }

    // =========================================================================
    // TAB 1 — CIRCULAR LINKED LIST
    // =========================================================================

    @SuppressWarnings("unchecked")
    private void setupCircularTable() {
        colCircIndex.setCellValueFactory(new PropertyValueFactory<>("index"));
        colCircData .setCellValueFactory(new PropertyValueFactory<>("data"));
        colCircNext .setCellValueFactory(new PropertyValueFactory<>("nextData"));
        colCircPrev .setCellValueFactory(new PropertyValueFactory<>("prevData"));
        colCircHead .setCellValueFactory(new PropertyValueFactory<>("headData"));
        colCircTail .setCellValueFactory(new PropertyValueFactory<>("tailData"));
    }

    private void wireCircularButtons() {
        btnCircAddFirst .setOnAction(e -> circAddFirst());
        btnCircAddLast  .setOnAction(e -> circAddLast());
        btnCircAddRandom.setOnAction(e -> circAddRandom());
        btnCircSearch   .setOnAction(e -> circSearch());
        btnCircRemove   .setOnAction(e -> circRemove());
        btnCircClean    .setOnAction(e -> circClean());
    }

    private void circAddFirst() {
        try {
            int val = parseCircularValue();
            circularList.addFirst(val);
            circLog("addFirst(" + val + ")  " + buildCircRep());
            lblCircularStatus.setText("Insertado al inicio: " + val);
            refreshCircular();
        } catch (NumberFormatException ex) { showAlert("Ingresá un número entero válido."); }
    }

    private void circAddLast() {
        try {
            int val = parseCircularValue();
            circularList.addLast(val);
            circLog("addLast(" + val + ")  " + buildCircRep());
            lblCircularStatus.setText("Insertado al final: " + val);
            refreshCircular();
        } catch (NumberFormatException ex) { showAlert("Ingresá un número entero válido."); }
    }

    private void circAddRandom() {
        int val = (int)(Math.random() * 100) + 1;
        txfCircularValue.setText(String.valueOf(val));
        circularList.add(val);
        circLog("addRandom(" + val + ")  " + buildCircRep());
        lblCircularStatus.setText("Insertado aleatoriamente: " + val);
        refreshCircular();
    }

    private void circSearch() {
        try {
            int val = parseCircularValue();
            int idx = circularList.indexOf(val);
            if (idx == -1) { circLog("search(" + val + ") → No encontrado"); lblCircularStatus.setText("No encontrado: " + val); }
            else           { circLog("search(" + val + ") → índice " + idx); lblCircularStatus.setText("Encontrado en índice " + idx + ": " + val); }
        } catch (NumberFormatException ex) { showAlert("Ingresá un número entero válido.");
        } catch (ListException ex)         { showAlert(ex.getMessage()); }
    }

    private void circRemove() {
        try {
            int val = parseCircularValue();
            circularList.remove(val);
            circLog("remove(" + val + ")  " + buildCircRep());
            lblCircularStatus.setText("Eliminado: " + val);
            refreshCircular();
        } catch (NumberFormatException ex) { showAlert("Ingresá un número entero válido.");
        } catch (ListException ex)         { showAlert(ex.getMessage()); }
    }

    private void circClean() {
        circularList.clear();
        circLog("clear() → Lista vaciada");
        lblCircularStatus.setText("Lista limpiada");
        refreshCircular();
    }

    @SuppressWarnings("unchecked")
    private void refreshCircular() {
        ObservableList<CircularListRow> rows = FXCollections.observableArrayList();
        try {
            int size = circularList.size();
            String head = circularList.isEmpty() ? "-" : String.valueOf(circularList.getFirst());
            String tail = circularList.isEmpty() ? "-" : String.valueOf(circularList.getLast());
            for (int i = 1; i <= size; i++) {
                Integer cur  = (Integer) circularList.get(i);
                String  next = String.valueOf(circularList.getNext(cur));
                String  prev = String.valueOf(circularList.getPrev(cur));
                rows.add(new CircularListRow(String.valueOf(i), String.valueOf(cur), next, prev, head, tail));
            }
        } catch (ListException e) { showAlert(e.getMessage()); }
        tableCircularData.setItems(rows);
        txfCircularRep.setText(buildCircRep());
        drawCircularList();
    }

    private String buildCircRep() {
        if (circularList.isEmpty()) return "HEAD → NULL";
        StringBuilder sb = new StringBuilder("HEAD → ");
        try {
            int size = circularList.size();
            for (int i = 1; i <= size; i++) {
                sb.append("[").append(circularList.get(i)).append("]");
                if (i < size) sb.append(" → ");
            }
            sb.append(" → HEAD");
        } catch (ListException e) { sb.append("ERROR"); }
        return sb.toString();
    }

    private void drawCircularList() {
        paneCircularList.getChildren().clear();
        if (circularList.isEmpty()) return;
        try {
            int    size   = circularList.size();
            double nodeW  = 80, nodeH = 40, gap = 45, startX = 85;
            double cw     = Math.max(620, startX + size * (nodeW + gap) + 60);
            double cy     = 110;
            Canvas canvas = new Canvas(cw, 180);
            GraphicsContext gc = canvas.getGraphicsContext2D();

            gc.setFill(Color.web("#e67e22"));
            gc.fillRoundRect(10, cy - 15, 55, 30, 8, 8);
            gc.setFill(Color.WHITE); gc.setFont(Font.font("Arial", FontWeight.BOLD, 13));
            gc.fillText("HEAD", 16, cy + 5);
            drawArrow(gc, 65, cy, startX, cy);

            for (int i = 1; i <= size; i++) {
                Integer data = (Integer) circularList.get(i);
                double  nx   = startX + (i - 1) * (nodeW + gap);
                gc.setFill(Color.web("#1e3a5f")); gc.fillRoundRect(nx, cy - nodeH/2, nodeW, nodeH, 8, 8);
                gc.setStroke(Color.web("#4a90d9")); gc.setLineWidth(1.5);
                gc.strokeLine(nx + 55, cy - nodeH/2, nx + 55, cy + nodeH/2);
                gc.setFill(Color.WHITE); gc.setFont(Font.font("Arial", FontWeight.BOLD, 14));
                gc.fillText(String.valueOf(data), nx + 14, cy + 5);
                gc.setFill(Color.web("#4a90d9")); gc.fillOval(nx + 61, cy - 6, 12, 12);
                if (i < size) drawArrow(gc, nx + nodeW, cy, nx + nodeW + gap, cy);
            }
            double lastNx = startX + (size - 1) * (nodeW + gap), topY = cy - nodeH/2 - 35;
            gc.setStroke(Color.web("#4a90d9")); gc.setLineWidth(2);
            gc.strokeLine(lastNx + nodeW, cy, lastNx + nodeW + 14, cy);
            gc.strokeLine(lastNx + nodeW + 14, cy, lastNx + nodeW + 14, topY);
            gc.strokeLine(lastNx + nodeW + 14, topY, startX + nodeW/2, topY);
            drawArrow(gc, startX + nodeW/2, topY, startX + nodeW/2, cy - nodeH/2);

            paneCircularList.setPrefSize(cw, 180); paneCircularList.getChildren().add(canvas);
        } catch (ListException e) { showAlert(e.getMessage()); }
    }

    private int    parseCircularValue() { return Integer.parseInt(txfCircularValue.getText().trim()); }
    private void   circLog(String msg)  { txtAreaCircularLog.appendText(msg + "\n"); }

    // =========================================================================
    // TAB 2 — CIRCULAR DOUBLY LINKED LIST
    // =========================================================================

    @SuppressWarnings("unchecked")
    private void setupDoublyTable() {
        colProdId     .setCellValueFactory(new PropertyValueFactory<>("id"));
        colProdName   .setCellValueFactory(new PropertyValueFactory<>("name"));
        colProdPrice  .setCellValueFactory(new PropertyValueFactory<>("price"));
        colProdStock  .setCellValueFactory(new PropertyValueFactory<>("stock"));
        colProdType   .setCellValueFactory(new PropertyValueFactory<>("type"));
        colProdRegDate.setCellValueFactory(new PropertyValueFactory<>("regDate"));
    }

    private void wireDoublyButtons() {
        btnProdAdd        .setOnAction(e -> prodAdd());
        btnProdSearch     .setOnAction(e -> prodSearch());
        btnProdCleanForm  .setOnAction(e -> prodCleanForm());
        btnProdSortName   .setOnAction(e -> prodSortByName());
        btnProdSortStock  .setOnAction(e -> prodSortByStock());
        btnProdRemove     .setOnAction(e -> prodRemove());
        btnProdRemoveFirst.setOnAction(e -> prodRemoveFirst());
        btnProdRemoveLast .setOnAction(e -> prodRemoveLast());
    }

    private void prodAdd() {
        try {
            String id = txfProdId.getText().trim(), name = txfProdName.getText().trim();
            String priceStr = txfProdPrice.getText().trim(), stockStr = txfProdStock.getText().trim();
            String type = (String) cbProductType.getValue();
            if (id.isEmpty() || name.isEmpty() || priceStr.isEmpty() || stockStr.isEmpty() || type == null || dpProdRegDate.getValue() == null) {
                showAlert("Todos los campos son requeridos."); return;
            }
            Product p = new Product(id, name, Double.parseDouble(priceStr), Integer.parseInt(stockStr), type, dpProdRegDate.getValue().toString());
            doublyList.add(p);
            doublyLog("add(" + id + ") HEAD ↔ [@ Product ID: " + id + "] ↔ ...");
            lblCircDoublyStatus.setText("Insertado item: " + id);
            refreshDoubly();
        } catch (NumberFormatException e) { showAlert("Price debe ser decimal y Stock debe ser entero."); }
    }

    private void prodSearch() {
        try {
            String id = txfProdId.getText().trim();
            if (id.isEmpty()) { showAlert("Ingresá un ID para buscar."); return; }
            int size = doublyList.size();
            for (int i = 1; i <= size; i++) {
                Product p = (Product) doublyList.get(i);
                if (p.getId().equals(id)) {
                    txfProdName.setText(p.getName()); txfProdPrice.setText(String.valueOf(p.getPrice()));
                    txfProdStock.setText(String.valueOf(p.getStock())); cbProductType.setValue(p.getType());
                    dpProdRegDate.setValue(p.getDate());
                    doublyLog("search(" + id + ") → Encontrado: " + p.getName());
                    lblCircDoublyStatus.setText("Encontrado: " + p.getName()); return;
                }
            }
            doublyLog("search(" + id + ") → No encontrado"); lblCircDoublyStatus.setText("No encontrado: " + id);
        } catch (ListException e) { showAlert(e.getMessage()); }
    }

    private void prodCleanForm() {
        txfProdId.clear(); txfProdName.clear(); txfProdPrice.clear(); txfProdStock.clear();
        cbProductType.setValue(null); dpProdRegDate.setValue(null);
    }

    private void prodSortByName() {
        try {
            int size = doublyList.size(); if (size <= 1) return;
            ArrayList<Product> list = new ArrayList<>();
            for (int i = 1; i <= size; i++) list.add((Product) doublyList.get(i));
            list.sort((a, b) -> a.getName().compareToIgnoreCase(b.getName()));
            doublyList.clear(); for (Product p : list) doublyList.add(p);
            doublyLog("sortByName()  " + buildDoublyRep()); lblCircDoublyStatus.setText("Ordenado por nombre"); refreshDoubly();
        } catch (ListException e) { showAlert(e.getMessage()); }
    }

    private void prodSortByStock() {
        try {
            int size = doublyList.size(); if (size <= 1) return;
            ArrayList<Product> list = new ArrayList<>();
            for (int i = 1; i <= size; i++) list.add((Product) doublyList.get(i));
            list.sort((a, b) -> Integer.compare(a.getStock(), b.getStock()));
            doublyList.clear(); for (Product p : list) doublyList.add(p);
            doublyLog("sortByStock()  " + buildDoublyRep()); lblCircDoublyStatus.setText("Ordenado por stock"); refreshDoubly();
        } catch (ListException e) { showAlert(e.getMessage()); }
    }

    private void prodRemove() {
        try {
            String id = txfProdId.getText().trim();
            if (id.isEmpty()) { showAlert("Ingresá un ID para eliminar."); return; }
            Product toRemove = null; int size = doublyList.size();
            for (int i = 1; i <= size; i++) { Product p = (Product) doublyList.get(i); if (p.getId().equals(id)) { toRemove = p; break; } }
            if (toRemove == null) { showAlert("No encontrado: " + id); return; }
            doublyList.remove(toRemove);
            doublyLog("remove(" + id + ")  " + buildDoublyRep()); lblCircDoublyStatus.setText("Eliminado: " + id); refreshDoubly();
        } catch (ListException e) { showAlert(e.getMessage()); }
    }

    private void prodRemoveFirst() {
        try {
            Product r = (Product) doublyList.removeFirst();
            doublyLog("removeFirst() → " + r.getId() + "  " + buildDoublyRep()); lblCircDoublyStatus.setText("Eliminado inicio: " + r.getId()); refreshDoubly();
        } catch (ListException e) { showAlert(e.getMessage()); }
    }

    private void prodRemoveLast() {
        try {
            Product r = (Product) doublyList.removeLast();
            doublyLog("removeLast() → " + r.getId() + "  " + buildDoublyRep()); lblCircDoublyStatus.setText("Eliminado final: " + r.getId()); refreshDoubly();
        } catch (ListException e) { showAlert(e.getMessage()); }
    }

    @SuppressWarnings("unchecked")
    private void refreshDoubly() {
        ObservableList<ProductRow> rows = FXCollections.observableArrayList();
        try {
            int size = doublyList.size();
            for (int i = 1; i <= size; i++) {
                Product p = (Product) doublyList.get(i);
                rows.add(new ProductRow(p.getId(), p.getName(), String.valueOf(p.getPrice()), String.valueOf(p.getStock()), p.getType(), p.getDate().format(DATE_FMT)));
            }
        } catch (ListException e) { showAlert(e.getMessage()); }
        tableCircDoublyData.setItems(rows); txfCircDoublyRep.setText(buildDoublyRep()); drawDoublyList();
    }

    private String buildDoublyRep() {
        if (doublyList.isEmpty()) return "HEAD ↔ HEAD";
        StringBuilder sb = new StringBuilder("HEAD ←→ ");
        try {
            int size = doublyList.size();
            for (int i = 1; i <= size; i++) { sb.append("[").append(doublyList.get(i)).append("]"); if (i < size) sb.append(" ←→ "); }
            sb.append(" ←→ HEAD");
        } catch (ListException e) { sb.append("ERROR"); }
        return sb.toString();
    }

    private void drawDoublyList() {
        paneCircDoublyList.getChildren().clear();
        if (doublyList.isEmpty()) {
            Canvas canvas = new Canvas(600, 200); GraphicsContext gc = canvas.getGraphicsContext2D();
            gc.setFill(Color.web("#aaaaaa")); gc.setFont(Font.font("Arial", 13));
            gc.fillText("Lista vacía  'use los botones'  para agregar elementos", 90, 110);
            paneCircDoublyList.setPrefSize(600, 200); paneCircDoublyList.getChildren().add(canvas); return;
        }
        try {
            int size = doublyList.size();
            double nodeW = 95, nodeH = 40, gap = 45, startX = 85;
            double cw = Math.max(620, startX + size * (nodeW + gap) + 60), cy = 120;
            Canvas canvas = new Canvas(cw, 200); GraphicsContext gc = canvas.getGraphicsContext2D();

            gc.setFill(Color.web("#e67e22")); gc.fillRoundRect(10, cy - 15, 55, 30, 8, 8);
            gc.setFill(Color.WHITE); gc.setFont(Font.font("Arial", FontWeight.BOLD, 13)); gc.fillText("HEAD", 16, cy + 5);
            drawArrow(gc, 65, cy, startX, cy);

            for (int i = 1; i <= size; i++) {
                Product p = (Product) doublyList.get(i);
                double  nx = startX + (i - 1) * (nodeW + gap);
                gc.setFill(Color.web("#1e3a5f")); gc.fillRoundRect(nx, cy - nodeH/2, nodeW, nodeH, 8, 8);
                gc.setStroke(Color.web("#4a90d9")); gc.setLineWidth(1.5); gc.strokeLine(nx + 68, cy - nodeH/2, nx + 68, cy + nodeH/2);
                String name = p.getName(); if (name.length() > 6) name = name.substring(0, 5) + "...";
                gc.setFill(Color.WHITE); gc.setFont(Font.font("Arial", FontWeight.BOLD, 13)); gc.fillText(name, nx + 8, cy + 5);
                gc.setFill(Color.web("#4a90d9")); gc.fillOval(nx + 74, cy - 6, 12, 12);
                if (i < size) { drawArrow(gc, nx + nodeW, cy - 7, nx + nodeW + gap, cy - 7); drawArrow(gc, nx + nodeW + gap, cy + 7, nx + nodeW, cy + 7); }
            }
            double lastNx = startX + (size - 1) * (nodeW + gap), topY = cy - nodeH/2 - 35;
            gc.setStroke(Color.web("#4a90d9")); gc.setLineWidth(2);
            gc.strokeLine(lastNx + nodeW, cy, lastNx + nodeW + 14, cy);
            gc.strokeLine(lastNx + nodeW + 14, cy, lastNx + nodeW + 14, topY);
            gc.strokeLine(lastNx + nodeW + 14, topY, startX + nodeW/2, topY);
            drawArrow(gc, startX + nodeW/2, topY, startX + nodeW/2, cy - nodeH/2);

            paneCircDoublyList.setPrefSize(cw, 200); paneCircDoublyList.getChildren().add(canvas);
        } catch (ListException e) { showAlert(e.getMessage()); }
    }

    private void doublyLog(String msg) { txtAreaCircDoublyLog.appendText(msg + "\n"); }

    // =========================================================================
    // TAB 3 — LINKED STACK
    // =========================================================================

    @SuppressWarnings("unchecked")
    private void setupStackTable() {
        colStackNode.setCellValueFactory(new PropertyValueFactory<>("node"));
        colStackData.setCellValueFactory(new PropertyValueFactory<>("data"));
        colStackNext.setCellValueFactory(new PropertyValueFactory<>("nextNode"));
    }

    private void wireStackButtons() {
        btnStackPush       .setOnAction(e -> stackPush());
        btnStackPushRandom .setOnAction(e -> stackPushRandom());
        btnStackSearch     .setOnAction(e -> stackSearch());
        btnStackPop        .setOnAction(e -> stackPop());
        btnStackRemoveValue.setOnAction(e -> stackRemoveValue());
        btnStackClean      .setOnAction(e -> stackClean());
    }

    private void stackPush() {
        try {
            int val = Integer.parseInt(txfStackValue.getText().trim());
            stack.push(val);
            stackLog("push(" + val + ") TOP → [" + val + "] → ...");
            lblStackStatus.setText("Apilado en el tope de la pila: " + val); refreshStack();
        } catch (NumberFormatException ex) { showAlert("Ingresá un número entero válido.");
        } catch (StackException ex)        { showAlert(ex.getMessage()); }
    }

    private void stackPushRandom() {
        try {
            for (int i = 0; i < 10; i++) {
                int val = (int)(Math.random() * 100) + 1;
                stack.push(val); stackLog("push(" + val + ") TOP → [" + val + "] → ...");
            }
            lblStackStatus.setText("10 elementos aleatorios apilados"); refreshStack();
        } catch (StackException ex) { showAlert(ex.getMessage()); }
    }

    private void stackSearch() {
        try {
            int val = Integer.parseInt(txfStackValue.getText().trim());
            int idx = stack.indexOf(val);
            if (idx == -1) { stackLog("search(" + val + ") → No encontrado"); lblStackStatus.setText("No encontrado: " + val); }
            else           { stackLog("search(" + val + ") → posición " + idx); lblStackStatus.setText("Encontrado en posición " + idx + ": " + val); }
        } catch (NumberFormatException ex) { showAlert("Ingresá un número entero válido.");
        } catch (StackException ex)        { showAlert(ex.getMessage()); }
    }

    private void stackPop() {
        try {
            int val = (Integer) stack.pop();
            stackLog("pop() → desapilado: " + val + "  " + buildStackRep());
            lblStackStatus.setText("Desapilado: " + val); refreshStack();
        } catch (StackException ex) { showAlert(ex.getMessage()); }
    }

    private void stackRemoveValue() {
        try {
            int val = Integer.parseInt(txfStackValue.getText().trim());
            stack.remove(val);
            stackLog("remove(" + val + ")  " + buildStackRep());
            lblStackStatus.setText("Eliminado: " + val); refreshStack();
        } catch (NumberFormatException ex) { showAlert("Ingresá un número entero válido.");
        } catch (StackException ex)        { showAlert(ex.getMessage()); }
    }

    private void stackClean() {
        stack.clear(); stackLog("clear() → Pila vaciada"); lblStackStatus.setText("Pila limpiada"); refreshStack();
    }

    private ArrayList<Integer> snapshotStack() throws StackException {
        ArrayList<Integer> elements = new ArrayList<>();
        LinkedStack<Integer> aux = new LinkedStack<>();
        while (!stack.isEmpty()) { Integer v = (Integer) stack.pop(); elements.add(v); aux.push(v); }
        while (!aux.isEmpty()) stack.push((Integer) aux.pop());
        return elements;
    }

    private String buildStackRep() {
        if (stack.isEmpty()) return "TOP → BOTTOM (vacía)";
        try {
            ArrayList<Integer> e = snapshotStack();
            StringBuilder sb = new StringBuilder("TOP → ");
            for (int i = 0; i < e.size(); i++) { sb.append("[").append(e.get(i)).append("]"); if (i < e.size()-1) sb.append(" , "); }
            sb.append(" → BOTTOM"); return sb.toString();
        } catch (StackException e) { return "ERROR"; }
    }

    @SuppressWarnings("unchecked")
    private void refreshStack() {
        try {
            ArrayList<Integer> elements = snapshotStack();
            ObservableList<StackRow> rows = FXCollections.observableArrayList();
            for (int i = 0; i < elements.size(); i++) {
                String next = (i < elements.size()-1) ? "N" + (i+2) : "NULL";
                rows.add(new StackRow("N" + (i+1), String.valueOf(elements.get(i)), next));
            }
            tableStackData.setItems(rows); txfStackRep.setText(buildStackRep()); drawStack();
        } catch (StackException e) { showAlert(e.getMessage()); }
    }

    private void drawStack() {
        paneStackSimulation.getChildren().clear();
        try {
            ArrayList<Integer> elements = snapshotStack();
            double nodeW = 140, nodeH = 45, gap = 40, cw = 500;
            double ch = Math.max(300, elements.size() * (nodeH + gap) + 150), cx = cw/2, startY = 65;
            Canvas canvas = new Canvas(cw, ch); GraphicsContext gc = canvas.getGraphicsContext2D();

            gc.setFill(Color.web("#1e1e24")); gc.fillRect(0, 0, cw, ch);
            gc.setFill(Color.web("#2ecc71")); gc.setFont(Font.font("Arial", FontWeight.BOLD, 18)); gc.fillText("Pila Enlazada", cx - 65, 35);

            if (elements.isEmpty()) {
                gc.setFill(Color.web("#777777")); gc.setFont(Font.font("Arial", 14)); gc.fillText("Pila vacía", cx - 35, ch/2);
            } else {
                for (int i = 0; i < elements.size(); i++) {
                    double ny = startY + i * (nodeH + gap), nx = cx - nodeW/2;
                    if (i == 0) { gc.setFill(Color.web("#4a90d9")); gc.setFont(Font.font("Arial", FontWeight.BOLD, 12)); gc.fillText("top →", nx - 52, ny + nodeH/2 + 5); }
                    gc.setFill(Color.web("#1e3a5f")); gc.fillRoundRect(nx, ny, nodeW, nodeH, 8, 8);
                    gc.setStroke(Color.web("#4a90d9")); gc.setLineWidth(1.5); gc.strokeRoundRect(nx, ny, nodeW, nodeH, 8, 8);
                    String val = String.valueOf(elements.get(i));
                    gc.setFill(Color.WHITE); gc.setFont(Font.font("Arial", FontWeight.BOLD, 16)); gc.fillText(val, cx - val.length() * 5, ny + nodeH/2 + 6);
                    double ay1 = ny + nodeH, ay2 = ny + nodeH + gap - 8;
                    gc.setStroke(Color.web("#4ab8c1")); gc.setLineWidth(2); gc.strokeLine(cx, ay1, cx, ay2);
                    gc.setFill(Color.web("#4ab8c1")); gc.fillPolygon(new double[]{cx, cx-7, cx+7}, new double[]{ay2+8, ay2, ay2}, 3);
                }
                double nullY = startY + elements.size() * (nodeH + gap);
                gc.setFill(Color.web("#2d1010")); gc.fillRoundRect(cx - 30, nullY, 60, 30, 6, 6);
                gc.setStroke(Color.web("#e74c3c")); gc.setLineWidth(1.5); gc.strokeRoundRect(cx - 30, nullY, 60, 30, 6, 6);
                gc.setFill(Color.web("#e74c3c")); gc.setFont(Font.font("Arial", FontWeight.BOLD, 13)); gc.fillText("null", cx - 14, nullY + 20);
            }
            paneStackSimulation.setPrefSize(cw, ch); paneStackSimulation.getChildren().add(canvas);
        } catch (StackException e) { showAlert(e.getMessage()); }
    }

    private void stackLog(String msg) { txtAreaStackLog.appendText(msg + "\n"); }

    // =========================================================================
    // TAB 4 — PRIORITY QUEUE
    // =========================================================================

    @SuppressWarnings("unchecked")
    private void setupQueueTable() {
        colPId     .setCellValueFactory(new PropertyValueFactory<>("id"));
        colPName   .setCellValueFactory(new PropertyValueFactory<>("name"));
        colPAge    .setCellValueFactory(new PropertyValueFactory<>("age"));
        colPService.setCellValueFactory(new PropertyValueFactory<>("service"));
        colPDate   .setCellValueFactory(new PropertyValueFactory<>("date"));
    }

    private void wireQueueButtons() {
        btnEnqueue          .setOnAction(e -> queueEnqueue());
        btnEnqueueRandom    .setOnAction(e -> queueEnqueueRandom());
        btnPriorityCleanForm.setOnAction(e -> queueCleanForm());
        btnPriorityPeek     .setOnAction(e -> queuePeek());
        btnPriorityIndexof  .setOnAction(e -> queueIndexOf());
        btnDequeue          .setOnAction(e -> queueDequeue());
        btnRemoveClient     .setOnAction(e -> queueRemoveClient());
    }

    private void queueEnqueue() {
        try {
            String id = txfClientId.getText().trim(), name = txfClientName.getText().trim();
            String ageStr = txfClientAge.getText().trim();
            String service = (String) cbBankService.getValue();
            if (id.isEmpty() || name.isEmpty() || ageStr.isEmpty() || service == null || dpClientDate.getValue() == null) {
                showAlert("Todos los campos son requeridos."); return;
            }
            int age = Integer.parseInt(ageStr);
            Client c = new Client(id, name, age, service, dpClientDate.getValue());
            priorityQueue.enQueue(c, getPriority(age));
            priorityLog("enQueue(" + id + ") FRONT → [" + id + "] + ...");
            lblPriorityStatus.setText("Encolado en el frente de la cola: " + id);
            refreshQueue();
        } catch (NumberFormatException ex) { showAlert("La edad debe ser un número entero.");
        } catch (QueueException ex)        { showAlert(queueMsg(ex)); }
    }

    private void queueEnqueueRandom() {
        try {
            Client c = randomClient();
            priorityQueue.enQueue(c, getPriority(c.getAge()));
            priorityLog("enQueue(" + c.getId() + ") FRONT → [" + c.getId() + "] + ...");
            lblPriorityStatus.setText("Encolado automático: " + c.getName());
            refreshQueue();
        } catch (QueueException ex) { showAlert(queueMsg(ex)); }
    }

    private void queuePeek() {
        try {
            Client c = (Client) priorityQueue.peek();
            priorityLog("peek() → FRONT: " + c.getName() + " (ID: " + c.getId() + ")");
            lblPriorityStatus.setText("Frente: " + c.getName() + " (ID: " + c.getId() + ")");
        } catch (QueueException ex) { showAlert(queueMsg(ex)); }
    }

    private void queueIndexOf() {
        try {
            String id = txfClientId.getText().trim();
            if (id.isEmpty()) { showAlert("Ingresá un ID."); return; }
            ArrayList<Client> elements = snapshotQueueSafe();
            for (Client c : elements) {
                if (c.getId().equals(id)) {
                    int idx = priorityQueue.indexOf(c);
                    priorityLog("indexOf(" + id + ") → posición " + idx);
                    lblPriorityStatus.setText("Encontrado en posición " + idx + ": " + c.getName()); return;
                }
            }
            priorityLog("indexOf(" + id + ") → No encontrado"); lblPriorityStatus.setText("No encontrado: " + id);
        } catch (QueueException ex) { showAlert(queueMsg(ex)); }
    }

    private void queueDequeue() {
        try {
            Client c = (Client) priorityQueue.deQueue();
            priorityLog("deQueue() → descolado: " + c.getName() + "  " + buildQueueRep());
            lblPriorityStatus.setText("Descolado: " + c.getName()); refreshQueue();
        } catch (QueueException ex) { showAlert(queueMsg(ex)); }
    }

    private void queueRemoveClient() {
        try {
            String id = txfClientId.getText().trim();
            if (id.isEmpty()) { showAlert("Ingresá un ID para eliminar."); return; }
            Client toRemove = null;
            for (Client c : snapshotQueueSafe()) { if (c.getId().equals(id)) { toRemove = c; break; } }
            if (toRemove == null) { showAlert("No encontrado: " + id); return; }
            priorityQueue.remove(toRemove);
            priorityLog("remove(" + id + ")  " + buildQueueRep());
            lblPriorityStatus.setText("Eliminado: " + id); refreshQueue();
        } catch (QueueException ex) { showAlert(queueMsg(ex)); }
    }

    private void queueCleanForm() {
        txfClientId.clear(); txfClientName.clear(); txfClientAge.clear();
        cbBankService.setValue(null); dpClientDate.setValue(null);
    }

    @SuppressWarnings("unchecked")
    private void refreshQueue() {
        try {
            ArrayList<Client> elements = snapshotQueueSafe();
            ObservableList<ClientRow> rows = FXCollections.observableArrayList();
            for (Client c : elements) {
                rows.add(new ClientRow(c.getId(), c.getName(), String.valueOf(c.getAge()), c.getService(), c.getDate().format(DATE_FMT)));
            }
            tablePriorityData.setItems(rows); txfPriorityRep.setText(buildQueueRep()); drawPriorityQueue();
        } catch (QueueException e) { showAlert(queueMsg(e)); }
    }

    private ArrayList<Client> snapshotQueueSafe() throws QueueException {
        ArrayList<Client> elements = new ArrayList<>();
        ArrayList<int[]>  priorities = new ArrayList<>();
        while (!priorityQueue.isEmpty()) {
            Client c = (Client) priorityQueue.deQueue();
            elements.add(c); priorities.add(new int[]{getPriority(c.getAge())});
        }
        for (int i = 0; i < elements.size(); i++) {
            priorityQueue.enQueue(elements.get(i), priorities.get(i)[0]);
        }
        return elements;
    }

    private String buildQueueRep() {
        if (priorityQueue.isEmpty()) return "FRONT → REAR";
        try {
            ArrayList<Client> elements = snapshotQueueSafe();
            StringBuilder sb = new StringBuilder("FRONT → ");
            for (int i = 0; i < elements.size(); i++) { sb.append("[").append(elements.get(i)).append("]"); if (i < elements.size()-1) sb.append(" → "); }
            sb.append(" → REAR"); return sb.toString();
        } catch (QueueException e) { return "ERROR"; }
    }

    private void drawPriorityQueue() {
        panePrioritySim.getChildren().clear();
        try {
            ArrayList<Client> elements = snapshotQueueSafe();
            double nodeW = 130, nodeH = 60, gap = 50, startX = 60;
            double cw = Math.max(620, startX + elements.size() * (nodeW + gap) + 60), ch = 220, ny = 80;
            Canvas canvas = new Canvas(cw, ch); GraphicsContext gc = canvas.getGraphicsContext2D();

            gc.setFill(Color.web("#1e1e24")); gc.fillRect(0, 0, cw, ch);
            gc.setFill(Color.web("#4ab8c1")); gc.setFont(Font.font("Arial", FontWeight.BOLD, 18));
            gc.fillText("Cola Enlazada", cw/2 - 65, 30);

            if (elements.isEmpty()) {
                gc.setFill(Color.web("#777777")); gc.setFont(Font.font("Arial", 13));
                gc.fillText("Cola vacía  'use los botones'  para encolar elementos", 80, ch/2);
            } else {
                for (int i = 0; i < elements.size(); i++) {
                    Client c = elements.get(i);
                    double nx = startX + i * (nodeW + gap);

                    if (i == 0) {
                        gc.setFill(Color.web("#27ae60")); gc.fillRoundRect(nx, ny - 28, 48, 20, 5, 5);
                        gc.setFill(Color.WHITE); gc.setFont(Font.font("Arial", FontWeight.BOLD, 10)); gc.fillText("front", nx + 7, ny - 13);
                    }
                    if (i == elements.size() - 1) {
                        gc.setFill(Color.web("#e67e22")); gc.fillRoundRect(nx + nodeW - 42, ny - 28, 42, 20, 5, 5);
                        gc.setFill(Color.WHITE); gc.setFont(Font.font("Arial", FontWeight.BOLD, 10)); gc.fillText("rear", nx + nodeW - 32, ny - 13);
                    }

                    gc.setFill(Color.web("#1e3a5f")); gc.fillRoundRect(nx, ny, nodeW, nodeH, 8, 8);
                    gc.setStroke(Color.web("#4a90d9")); gc.setLineWidth(1.5); gc.strokeRoundRect(nx, ny, nodeW, nodeH, 8, 8);

                    double divX = nx + 80;
                    gc.strokeLine(divX, ny, divX, ny + nodeH);

                    gc.setFill(Color.web("#aaaaaa")); gc.setFont(Font.font("Arial", 9));
                    gc.fillText("data", nx + 5, ny + 14);
                    gc.fillText("next", divX + 5, ny + 14);

                    gc.setFill(Color.WHITE); gc.setFont(Font.font("Arial", FontWeight.BOLD, 16));
                    gc.fillText(c.getId(), nx + 20, ny + 42);

                    if (i < elements.size() - 1) {
                        gc.setFill(Color.web("#4ab8c1")); gc.setFont(Font.font("Arial", FontWeight.BOLD, 14)); gc.fillText("→", divX + 10, ny + 42);
                        drawArrow(gc, nx + nodeW, ny + nodeH/2, startX + (i+1) * (nodeW + gap), ny + nodeH/2);
                    } else {
                        gc.setFill(Color.web("#e74c3c")); gc.setFont(Font.font("Arial", FontWeight.BOLD, 11)); gc.fillText("null", divX + 5, ny + 42);
                    }
                }
                gc.setFill(Color.web("#aaaaaa")); gc.setFont(Font.font("Arial", 11));
                gc.fillText("← dequeue", startX, ny + nodeH + 20);
                gc.fillText("enqueue →", startX + (elements.size()-1) * (nodeW + gap) + 25, ny + nodeH + 20);
            }
            panePrioritySim.setPrefSize(cw, ch); panePrioritySim.getChildren().add(canvas);
        } catch (QueueException e) { showAlert(queueMsg(e)); }
    }

    private int getPriority(int age) {
        if (age >= 65) return 1;
        if (age >= 50) return 2;
        return 3;
    }

    private Client randomClient() {
        String id      = String.valueOf((int)(Math.random() * 900) + 100);
        String name    = RAND_NAMES   [(int)(Math.random() * RAND_NAMES.length)];
        int    age     = (int)(Math.random() * 68) + 18;
        String service = RAND_SERVICES[(int)(Math.random() * RAND_SERVICES.length)];
        return new Client(id, name, age, service, LocalDate.now());
    }

    private String queueMsg(QueueException ex) {
        return ex.getMessage() != null ? ex.getMessage() : "Error en la cola.";
    }

    private void priorityLog(String msg) { txtAreaPriorityLog.appendText(msg + "\n"); }

    // =========================================================================
    // SHARED UTILITIES
    // =========================================================================

    private void drawArrow(GraphicsContext gc, double x1, double y1, double x2, double y2) {
        gc.setStroke(Color.web("#4a90d9")); gc.setLineWidth(2); gc.strokeLine(x1, y1, x2, y2);
        double angle = Math.atan2(y2 - y1, x2 - x1), s = 9;
        double x3 = x2 - s * Math.cos(angle - Math.PI/6), y3 = y2 - s * Math.sin(angle - Math.PI/6);
        double x4 = x2 - s * Math.cos(angle + Math.PI/6), y4 = y2 - s * Math.sin(angle + Math.PI/6);
        gc.setFill(Color.web("#4a90d9")); gc.fillPolygon(new double[]{x2, x3, x4}, new double[]{y2, y3, y4}, 3);
    }

    private void showAlert(String msg) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setHeaderText(null); alert.setContentText(msg); alert.showAndWait();
    }
}