package model.LinkedList;

public class StackRow {
    private String node;
    private String data;
    private String nextNode;

    public StackRow(String node, String data, String nextNode) {
        this.node     = node;
        this.data     = data;
        this.nextNode = nextNode;
    }

    public String getNode()     { return node; }
    public String getData()     { return data; }
    public String getNextNode() { return nextNode; }
}
