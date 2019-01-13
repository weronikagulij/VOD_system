package controllers;

public class ShortProduct {
    private Integer id;
    private String title;
    private Integer distributor;
    private String type;

    public ShortProduct(Integer idCol, String titleCol, Integer distributorCol, String typeCol) {
        this.id = idCol;
        this.title = titleCol;
        this.distributor = distributorCol;
        this.type = typeCol;
    }

    public Integer getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public Integer getDistributor() {
        return distributor;
    }

    public String getType() {
        return type;
    }
}