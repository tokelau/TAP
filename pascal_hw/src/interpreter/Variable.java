package interpreter;

public class Variable {
    private String name;
    public Node value;

    public Variable(String name){
        this.name = name;

    }

    public String getName () {
        return name;
    }

    public Node getValue() {
        return value;
    }

    public void setValue(Node value) {
        this.value = value;
    }
}
