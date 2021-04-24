package main;

public enum option {
    Admin, Employee;

    private  option(){}

    private String value(){
        return name();
    }

    public static option fromValue(String v){
        return valueOf(v);
    }
}
