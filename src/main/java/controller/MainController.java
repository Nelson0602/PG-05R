package controller;

import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;
import model.tree.AVL;
import model.tree.BST;
import model.tree.BTree;
import model.tree.BTreeNode;
import model.tree.TreeException;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class MainController {

    @FXML
    private TabPane mainTabPane;

    // Binary Tree (BTree) elements
    @FXML
    private TextField btInputTextField;
    @FXML
    private Button btAddButton;
    @FXML
    private Button btRemoveButton;
    @FXML
    private Button btClearButton;
    @FXML
    private ComboBox<String> btTraversalComboBox;
    @FXML
    private Button btPlayButton;
    @FXML
    private ListView<String> btListView;
    @FXML
    private Canvas btCanvas;
    @FXML
    private Label btNodesLabel; // New
    @FXML
    private Label btHeightLabel; // New

    // Binary Search Tree (BST) elements
    @FXML
    private TextField bstInputTextField;
    @FXML
    private Button bstAddButton;
    @FXML
    private Button bstSearchButton; // New
    @FXML
    private Button bstRemoveButton;
    @FXML
    private Button bstClearButton;
    @FXML
    private ComboBox<String> bstTraversalComboBox;
    @FXML
    private Button bstPlayButton;
    @FXML
    private ListView<String> bstListView;
    @FXML
    private Canvas bstCanvas;
    @FXML
    private Label bstNodesLabel; // New
    @FXML
    private Label bstHeightLabel; // New
    @FXML
    private Label bstValidLabel; // New

    // AVL Tree elements
    @FXML
    private TextField avlInputTextField;
    @FXML
    private Button avlAddButton;
    @FXML
    private Button avlSearchButton; // New
    @FXML
    private Button avlRemoveButton;
    @FXML
    private Button avlClearButton;
    @FXML
    private Label avlLastRotationLabel; // New
    @FXML
    private ComboBox<String> avlTraversalComboBox;
    @FXML
    private Button avlPlayButton;
    @FXML
    private ListView<String> avlListView;
    @FXML
    private Canvas avlCanvas;
    @FXML
    private Label avlNodesLabel; // New
    @FXML
    private Label avlHeightLabel; // New
    @FXML
    private Label avlBalancedLabel; // New

    private BTree<Integer> bTree;
    private BST<Integer> bstTree;
    private AVL<Integer> avlTree;

    // Drawing constants
    private static final double NODE_RADIUS = 20;
    private static final double VERTICAL_GAP = 60;
    private static final double HORIZONTAL_SPACING = 40; // Base horizontal spacing between nodes

    // Map to store calculated positions for each node
    private Map<BTreeNode<Integer>, double[]> nodePositions;
    private double currentXPosition; // Used during in-order traversal for X-coordinate calculation

    @FXML
    public void initialize() {
        bTree = new BTree<>();
        bstTree = new BST<>();
        avlTree = new AVL<>();

        // Initialize ComboBoxes
        btTraversalComboBox.getItems().addAll("PreOrder", "InOrder", "PostOrder");
        btTraversalComboBox.setValue("InOrder"); // Default selection
        bstTraversalComboBox.getItems().addAll("PreOrder", "InOrder", "PostOrder");
        bstTraversalComboBox.setValue("InOrder"); // Default selection
        avlTraversalComboBox.getItems().addAll("PreOrder", "InOrder", "PostOrder");
        avlTraversalComboBox.setValue("InOrder"); // Default selection

        // Initial update of UI
        updateBTreeUI();
        updateBSTUI();
        updateAVLUI();
    }

    // --- BTree Handlers ---
    @FXML
    private void handleAddBTree() {
        try {
            Integer value = Integer.parseInt(btInputTextField.getText());
            bTree.add(value);
            btInputTextField.clear();
            updateBTreeUI();
        } catch (NumberFormatException e) {
            btListView.getItems().add("Error: Formato de número inválido.");
        }
    }

    @FXML
    private void handleRemoveBTree() {
        try {
            Integer value = Integer.parseInt(btInputTextField.getText());
            bTree.remove(value);
            btInputTextField.clear();
            updateBTreeUI();
        } catch (NumberFormatException e) {
            btListView.getItems().add("Error: Formato de número inválido.");
        } catch (TreeException e) {
            btListView.getItems().add("Error: " + e.getMessage());
        }
    }

    @FXML
    private void handleClearBTree() {
        bTree.clear();
        updateBTreeUI();
    }

    @FXML
    private void handlePlayBTreeTraversal() {
        updateBTreeUI(); // Just refresh for now, animation would be more complex
    }

    private void updateBTreeUI() {
        btListView.getItems().clear();
        try {
            String selectedTraversal = btTraversalComboBox.getValue();
            String traversalResult = "";

            if (bTree.isEmpty()) {
                btListView.getItems().add("El Árbol Binario está vacío.");
                btNodesLabel.setText("Nodos: 0");
                btHeightLabel.setText("Altura: 0");
            } else {
                btNodesLabel.setText("Nodos: " + bTree.size());
                btHeightLabel.setText("Altura: " + bTree.height());

                switch (selectedTraversal) {
                    case "PreOrder":
                        traversalResult = bTree.preOrder();
                        break;
                    case "InOrder":
                        traversalResult = bTree.inOrder();
                        break;
                    case "PostOrder":
                        traversalResult = bTree.postOrder();
                        break;
                }
                btListView.getItems().add("Recorrido " + selectedTraversal + ": " + traversalResult);
                btListView.getItems().add("Min: " + bTree.min());
                btListView.getItems().add("Max: " + bTree.max());
                btListView.getItems().add("Alturas de Nodos:\n" + bTree.nodeHeight());
            }
        } catch (TreeException e) {
            btListView.getItems().add("Error actualizando información del BTree: " + e.getMessage());
        }
        drawTree(bTree, btCanvas);
    }

    // --- BST Handlers ---
    @FXML
    private void handleAddBST() {
        try {
            Integer value = Integer.parseInt(bstInputTextField.getText());
            bstTree.add(value);
            bstInputTextField.clear();
            updateBSTUI();
        } catch (NumberFormatException e) {
            bstListView.getItems().add("Error: Formato de número inválido.");
        }
    }

    @FXML
    private void handleSearchBST() {
        // Implement search animation logic here
        try {
            Integer value = Integer.parseInt(bstInputTextField.getText());
            if (bstTree.contains(value)) {
                bstListView.getItems().add("BST contiene " + value + ".");
            } else {
                bstListView.getItems().add("BST NO contiene " + value + ".");
            }
        } catch (NumberFormatException e) {
            bstListView.getItems().add("Error: Formato de número inválido.");
        } catch (TreeException e) {
            bstListView.getItems().add("Error: " + e.getMessage());
        }
    }

    @FXML
    private void handleRemoveBST() {
        try {
            Integer value = Integer.parseInt(bstInputTextField.getText());
            bstTree.remove(value);
            bstInputTextField.clear();
            updateBSTUI();
        } catch (NumberFormatException e) {
            bstListView.getItems().add("Error: Formato de número inválido.");
        } catch (TreeException e) {
            bstListView.getItems().add("Error: " + e.getMessage());
        }
    }

    @FXML
    private void handleClearBST() {
        bstTree.clear();
        updateBSTUI();
    }

    @FXML
    private void handlePlayBSTTraversal() {
        updateBSTUI(); // Just refresh for now, animation would be more complex
    }

    private void updateBSTUI() {
        bstListView.getItems().clear();
        try {
            String selectedTraversal = bstTraversalComboBox.getValue();
            String traversalResult = "";

            if (bstTree.isEmpty()) {
                bstListView.getItems().add("El Árbol Binario de Búsqueda está vacío.");
                bstNodesLabel.setText("Nodos: 0");
                bstHeightLabel.setText("Altura: 0");
                bstValidLabel.setText("BST Válido..."); // Assuming it's valid if empty
                bstValidLabel.setTextFill(Color.web("#2ecc71"));
            } else {
                bstNodesLabel.setText("Nodos: " + bstTree.size());
                bstHeightLabel.setText("Altura: " + bstTree.height());
                // TODO: Implement a method to check if BST is valid
                bstValidLabel.setText("BST Válido...");
                bstValidLabel.setTextFill(Color.web("#2ecc71"));

                switch (selectedTraversal) {
                    case "PreOrder":
                        traversalResult = bstTree.preOrder();
                        break;
                    case "InOrder":
                        traversalResult = bstTree.inOrder();
                        break;
                    case "PostOrder":
                        traversalResult = bstTree.postOrder();
                        break;
                }
                bstListView.getItems().add("Recorrido " + selectedTraversal + ": " + traversalResult);
                bstListView.getItems().add("Min: " + bstTree.min());
                bstListView.getItems().add("Max: " + bstTree.max());
                bstListView.getItems().add("Alturas de Nodos:\n" + bstTree.nodeHeight());
            }
        } catch (TreeException e) {
            bstListView.getItems().add("Error actualizando información del BST: " + e.getMessage());
        }
        drawTree(bstTree, bstCanvas);
    }

    // --- AVL Handlers ---
    @FXML
    private void handleAddAVL() {
        try {
            Integer value = Integer.parseInt(avlInputTextField.getText());
            avlTree.add(value);
            avlInputTextField.clear();
            updateAVLUI();
        } catch (NumberFormatException e) {
            avlListView.getItems().add("Error: Formato de número inválido.");
        }
    }

    @FXML
    private void handleSearchAVL() {
        // Implement search animation logic here
        try {
            Integer value = Integer.parseInt(avlInputTextField.getText());
            if (avlTree.contains(value)) {
                avlListView.getItems().add("AVL contiene " + value + ".");
            } else {
                avlListView.getItems().add("AVL NO contiene " + value + ".");
            }
        } catch (NumberFormatException e) {
            avlListView.getItems().add("Error: Formato de número inválido.");
        } catch (TreeException e) {
            avlListView.getItems().add("Error: " + e.getMessage());
        }
    }

    @FXML
    private void handleRemoveAVL() {
        try {
            Integer value = Integer.parseInt(avlInputTextField.getText());
            avlTree.remove(value);
            avlInputTextField.clear();
            updateAVLUI();
        } catch (NumberFormatException e) {
            avlListView.getItems().add("Error: Formato de número inválido.");
        } catch (TreeException e) {
            avlListView.getItems().add("Error: " + e.getMessage());
        }
    }

    @FXML
    private void handleClearAVL() {
        avlTree.clear();
        updateAVLUI();
    }

    @FXML
    private void handlePlayAVLTraversal() {
        updateAVLUI(); // Just refresh for now, animation would be more complex
    }

    private void updateAVLUI() {
        avlListView.getItems().clear();
        try {
            String selectedTraversal = avlTraversalComboBox.getValue();
            String traversalResult = "";

            if (avlTree.isEmpty()) {
                avlListView.getItems().add("El Árbol AVL está vacío.");
                avlNodesLabel.setText("Nodos: 0");
                avlHeightLabel.setText("Altura: 0");
                avlBalancedLabel.setText("Balanceado...");
                avlBalancedLabel.setTextFill(Color.web("#2ecc71"));
                avlLastRotationLabel.setText("---");
            } else {
                avlNodesLabel.setText("Nodos: " + avlTree.size());
                avlHeightLabel.setText("Altura: " + avlTree.height());
                avlBalancedLabel.setText(avlTree.isBalanced() ? "Balanceado..." : "Desbalanceado!");
                avlBalancedLabel.setTextFill(avlTree.isBalanced() ? Color.web("#2ecc71") : Color.web("#e74c3c"));
                // TODO: Implement logic to get last rotation from AVL tree
                avlLastRotationLabel.setText("---"); // Placeholder

                switch (selectedTraversal) {
                    case "PreOrder":
                        traversalResult = avlTree.preOrder();
                        break;
                    case "InOrder":
                        traversalResult = avlTree.inOrder();
                        break;
                    case "PostOrder":
                        traversalResult = avlTree.postOrder();
                        break;
                }
                avlListView.getItems().add("Recorrido " + selectedTraversal + ": " + traversalResult);
                avlListView.getItems().add("Min: " + avlTree.min());
                avlListView.getItems().add("Max: " + avlTree.max());
                avlListView.getItems().add("Alturas de Nodos:\n" + avlTree.nodeHeight());
            }
        } catch (TreeException e) {
            avlListView.getItems().add("Error actualizando información del AVL: " + e.getMessage());
        }
        drawTree(avlTree, avlCanvas);
    }

    // --- Tree Drawing Logic ---
    private void drawTree(BTree<Integer> tree, Canvas canvas) {
        GraphicsContext gc = canvas.getGraphicsContext2D();
        gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight()); // Clear canvas

        if (tree.isEmpty()) {
            gc.setFill(Color.WHITE); // Changed to white for dark background
            gc.setFont(new Font("Arial", 16));
            gc.setTextAlign(TextAlignment.CENTER);
            gc.fillText("El árbol está vacío", canvas.getWidth() / 2, canvas.getHeight() / 2);
            return;
        }

        nodePositions = new HashMap<>();
        currentXPosition = NODE_RADIUS; // Start X position for the leftmost node

        // First pass: Calculate X and Y positions for all nodes
        calculateNodePositions(tree.root, 0, canvas.getWidth());

        // Second pass: Draw nodes and connections
        // Pass the tree instance to drawNodesAndConnections to determine node type for coloring
        drawNodesAndConnections(gc, tree.root, null, tree);
    }

    // First pass: Calculate X and Y positions for all nodes using in-order traversal
    private void calculateNodePositions(BTreeNode<Integer> node, int level, double canvasWidth) {
        if (node == null) {
            return;
        }

        calculateNodePositions(node.left, level + 1, canvasWidth);

        // Assign X position
        double x = currentXPosition;
        double y = NODE_RADIUS + (level * VERTICAL_GAP);
        nodePositions.put(node, new double[]{x, y});
        currentXPosition += HORIZONTAL_SPACING + NODE_RADIUS * 2; // Move to the right for the next node

        calculateNodePositions(node.right, level + 1, canvasWidth);
    }

    // Second pass: Draw nodes and connections using pre-calculated positions
    // Added 'BTree<Integer> treeInstance' parameter to determine node coloring
    private void drawNodesAndConnections(GraphicsContext gc, BTreeNode<Integer> node, BTreeNode<Integer> parent, BTree<Integer> treeInstance) {
        if (node == null) {
            return;
        }

        double[] nodePos = nodePositions.get(node);
        double nodeX = nodePos[0];
        double nodeY = nodePos[1];

        // Draw connection from parent to current node
        if (parent != null) {
            double[] parentPos = nodePositions.get(parent);
            double parentX = parentPos[0];
            double parentY = parentPos[1];

            gc.setStroke(Color.web("#7f8c8d")); // Darker color for lines
            gc.setLineWidth(1);
            // Adjust line start/end to be at the edge of the circles
            double angle = Math.atan2(nodeY - parentY, nodeX - parentX);
            double startX = parentX + NODE_RADIUS * Math.cos(angle);
            double startY = parentY + NODE_RADIUS * Math.sin(angle);
            double endX = nodeX - NODE_RADIUS * Math.cos(angle);
            double endY = nodeY - NODE_RADIUS * Math.sin(angle);

            gc.strokeLine(startX, startY, endX, endY);
        }

        // Draw the current node
        Color nodeFillColor;
        Color nodeStrokeColor;

        // Determine color based on the type of the tree instance being drawn
        if (treeInstance instanceof AVL) {
            nodeFillColor = Color.web("#2ecc71"); // Green for AVL
            nodeStrokeColor = Color.web("#27ae60");
        } else if (treeInstance instanceof BST) {
            nodeFillColor = Color.web("#f1c40f"); // Yellow for BST
            nodeStrokeColor = Color.web("#f39c12");
        } else { // Default to BTree
            nodeFillColor = Color.web("#3498db"); // Blue for BTree
            nodeStrokeColor = Color.web("#2980b9");
        }

        gc.setFill(nodeFillColor);
        gc.setStroke(nodeStrokeColor);
        gc.setLineWidth(2);
        gc.fillOval(nodeX - NODE_RADIUS, nodeY - NODE_RADIUS, 2 * NODE_RADIUS, 2 * NODE_RADIUS);
        gc.strokeOval(nodeX - NODE_RADIUS, nodeY - NODE_RADIUS, 2 * NODE_RADIUS, 2 * NODE_RADIUS);

        gc.setFill(Color.WHITE); // Text color white for dark background
        gc.setFont(new Font("Arial", 12));
        gc.setTextAlign(TextAlignment.CENTER);
        gc.fillText(String.valueOf(node.data), nodeX, nodeY + 5); // +5 to center text vertically

        // Recursively draw children
        drawNodesAndConnections(gc, node.left, node, treeInstance);
        drawNodesAndConnections(gc, node.right, node, treeInstance);
    }
}
