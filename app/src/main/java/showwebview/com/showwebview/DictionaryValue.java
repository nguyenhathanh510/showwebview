package showwebview.com.showwebview;

public class DictionaryValue {

    private String type;
    private String definition;

    public DictionaryValue(String type, String definition) {

        this.type = type;
        this.definition = definition;
    }

    public String getType() {
        return type;
    }

    public String getDefinition() {
        return definition;
    }
}
