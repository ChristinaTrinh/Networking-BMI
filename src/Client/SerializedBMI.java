package Client;


import java.io.*;
public class SerializedBMI implements Serializable{
    private String name;
    private int age;
    private double height, weight;
    public static final double kpp = 0.45359237;
    public static final double mpi = 0.0254;
    private String report;
    public SerializedBMI(String name, int age, double w, double h){
        height = h;
        weight = w;
        this.name = name;
        this.age = age;
    }
    public String toString(){
        return name+"  "+age+"  "+weight+"  "+height;
    }
    public String getReport(){
        return report;
    }
    public void setReport(String r){
        report = r;
    }
    public double getHeight(){
        return height;
    }
    public double getWeight(){
        return weight;
    }
    public double getBMI(){
        return Math.round(weight*kpp/Math.pow(height*mpi, 2)*100)/100.0;
    }
    public String getStatus(){
        double bmi = getBMI();
        if (bmi<18.5){
            return "Underweight";
        } else if (bmi<25){
            return "Normal";
        } else if (bmi<30){
            return "Overweight" ;
        } else {
            return "Obese";
        }
    }
    public String getName(){
        return name;
    }
    public int getAge(){
        return age;
    }
}

