package main;

public enum option {
    admin, employee;

    private  option(){}

    private String value(){
        return name();
    }

    public static option fromValue(String v){
        return valueOf(v);
    }
}
