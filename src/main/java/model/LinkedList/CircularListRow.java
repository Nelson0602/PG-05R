package model.LinkedList;

public class CircularListRow {
    private String index;
    private String data;
    private String nextData;
    private String prevData;
    private String headData;
    private String tailData;

    public CircularListRow(String index, String data, String nextData,
                           String prevData, String headData, String tailData) {
        this.index    = index;
        this.data     = data;
        this.nextData = nextData;
        this.prevData = prevData;
        this.headData = headData;
        this.tailData = tailData;
    }

    public String getIndex()    { return index; }
    public String getData()     { return data; }
    public String getNextData() { return nextData; }
    public String getPrevData() { return prevData; }
    public String getHeadData() { return headData; }
    public String getTailData() { return tailData; }
}
