package Models;

public class NotPacjententException extends Exception {
    public NotPacjententException(){
        super("This is not a Patient");
    }
}
