package flavourful;

public interface Strawberry extends Flavourful{
    @Override
    public default String flavour(){
        return "Strawberry";
    }
}
