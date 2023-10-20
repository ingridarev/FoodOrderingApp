package lt.techin.FoodOrderApp.exception;

public class FoodOrderAppServiceDisabledException extends RuntimeException{

    public FoodOrderAppServiceDisabledException() {
    }

    public FoodOrderAppServiceDisabledException(String message) {
        super(message);
    }

}
