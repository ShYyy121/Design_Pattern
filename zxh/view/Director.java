package view;

public class Director {
    private Builder builder;

    public Director(Builder builder) {
        this.builder = builder;
    }
    public StringBuffer gethtml(){
        builder.buildhtml();
        return builder.createhtml();
    }
}
