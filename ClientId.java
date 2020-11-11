public class ClientId implements java.io.Serializable {

    private static final long serialVersionUID = 8136573182851760559L;

    private int id;
    private String name;

    public ClientId(int id) {
        this.id = id;
        this.name = "Jim";
    }

    // returns id integer
    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    // prints id number
    public String toString() {
        return Integer.toString(id);
    }
}