
package com.example.pos;

public class ListModel {
    public String item_code;
    public String item_name;
    public String item_unit;
    public double unit_price;
    public int quantity;
    public double cost;

    public ListModel(String code, String _item, String unit, double unit_price, int quantity, double cost_v) {
        this.item_code = code;
        this.item_name = _item;
        this.item_unit = unit;
        this.unit_price = unit_price;
        this.quantity = quantity;
        this.cost = cost_v;

    }

    public String getCode() {
        return item_code;
    }

    public String get_item() {
        return item_name;
    }
    public double getCost() {
        return cost;
    }

    public double getUnit_price() {
        return unit_price;
    }

    public int getQuantity() {
        return quantity;
    }

}