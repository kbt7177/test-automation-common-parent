package com.springer.quality.utilities.exceptions;

public class ReadExcelException extends Exception{

    String message = "Read Excel Exception";

    public ReadExcelException(String message){
        super(message);
        this.message = message;
    }

    public ReadExcelException(){
        super();
    }

    public ReadExcelException(Throwable e){
        super(e);
    }

    @Override
    public String toString() {
        return "ReadExcelException{" +
                "message='" + message + '\'' +
                '}';
    }
}
