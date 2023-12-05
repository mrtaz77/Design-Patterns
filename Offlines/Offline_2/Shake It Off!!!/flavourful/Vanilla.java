package flavourful;

public interface Vanilla extends Flavourful {
    @Override
    public default String flavour(){
        return "Vanilla";
    }
}
