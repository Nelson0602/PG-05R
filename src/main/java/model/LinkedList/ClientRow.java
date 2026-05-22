package model.LinkedList;

public class ClientRow {
    private String id;
    private String name;
    private String age;
    private String service;
    private String date;

    public ClientRow(String id, String name, String age, String service, String date) {
        this.id      = id;
        this.name    = name;
        this.age     = age;
        this.service = service;
        this.date    = date;
    }

    public String getId()      { return id; }
    public String getName()    { return name; }
    public String getAge()     { return age; }
    public String getService() { return service; }
    public String getDate()    { return date; }
}
