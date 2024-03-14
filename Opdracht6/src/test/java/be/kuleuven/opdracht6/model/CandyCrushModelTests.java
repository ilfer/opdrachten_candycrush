package be.kuleuven.opdracht6.model;

import org.junit.jupiter.api.Test;

public class CandyCrushModelTests {

    @Test
    public void gegeven_wanneer_dan(){
        CandyCrushModel model = new CandyCrushModel("Arne");
        String result = model.getSpeler();
        assert (result.equals("Arne"));
    }

    //TODO: Delete previous test and test your own code

    @Test
    public void empty(){
        CandyCrushModel model = new CandyCrushModel(" ");
        String result = model.getSpeler();
        assert (result.equals(" "));
    }
}