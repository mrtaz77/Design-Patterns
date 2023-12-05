package flavourful;

public interface Chocolate extends Flavourful {
    @Override
    public default String flavour(){
        return "Chocolate";
    }
}
