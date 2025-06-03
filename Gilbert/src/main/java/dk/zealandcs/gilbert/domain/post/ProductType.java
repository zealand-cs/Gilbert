package dk.zealandcs.gilbert.domain.post;

import java.util.Objects;
import java.util.Optional;

public class ProductType {
    private String name;
    private Integer id;
    private Integer parentId;

    public ProductType(String name, Integer id, Integer parentId) {
        this.name = name;
        this.id = id;
        this.parentId = parentId;
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

    public Optional<Integer> getParentId() {
        return Optional.ofNullable(parentId);
    }

    @Override
    public boolean equals(Object object) {
        if (object == null || getClass() != object.getClass()) return false;

        ProductType productType = (ProductType) object;

        return Objects.equals(name, productType.name) && Objects.equals(id, productType.id) && Objects.equals(parentId, productType.parentId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, id, parentId);
    }
}
