package dk.zealandcs.gilbert.domain.post;

public class ProductType {
    private String name;
    private Integer id;

    public ProductType(String name, Integer id) {
        this.name = name;
        this.id = id;
    }

    public ProductType() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
