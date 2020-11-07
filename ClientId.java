public class ClientId implements java.io.Serializable {

    private static final long serialVersionUID = 8136573182851760559L;

    private int id;

    public ClientId(int id) {
        this.id = id;
    }

    // returns id integer
    public int getId() {
        return id;
    }

    // prints id number
    public String toString() {
        return Integer.toString(id);
    }
    
}