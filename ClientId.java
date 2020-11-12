public class ClientId implements java.io.Serializable {

    private static final long serialVersionUID = 8136573182851760559L;

    private int id;
    private String name;
    private String email;

    public ClientId(int id, String email) {
        this.id = id;
        this.name = "Jim";
        this.email = email;
    }

    // returns id integer
    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    // prints id number
    public String toString() {
        return Integer.toString(id);
    }
}