package dk.zealandcs.gilbert.domain.post;

public class Brand {
    private String name;
    private Integer id;

    public Brand() {
    }

    public Brand(String name, Integer id) {
        this.name = name;
        this.id = id;
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

    @Override
    public String toString() {
        return name;
    }

    @Override
    public boolean equals(Object object) {
        if (object == null || object.getClass() != getClass()) {
            return false;
        }

        var brand = (Brand) object;

        return this.id.equals(brand.id) && this.name.equals(brand.name);
    }
}
