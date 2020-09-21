package com.nsarupri.bookstore.models.entity;

import com.nsarupri.bookstore.constants.InventoryLogType;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Entity
@Table(name = "inventory_log")
public class InventoryLog {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int uid;
    @CreatedDate
    private Date createdDate;
    private InventoryLogType inventoryLogType;
    private int inventoryId;
    private int previousQuantity;
    private double previousPrice;
    private int currentQuantity;
    private double currentPrice;

    public InventoryLog(InventoryLogType inventoryLogType, int inventoryId, int previousQuantity, double previousPrice, int currentQuantity, double currentPrice) {
        this.inventoryLogType = inventoryLogType;
        this.inventoryId = inventoryId;
        this.previousQuantity = previousQuantity;
        this.previousPrice = previousPrice;
        this.currentQuantity = currentQuantity;
        this.currentPrice = currentPrice;
    }

    public InventoryLog() {
    }

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public InventoryLogType getInventoryLogType() {
        return inventoryLogType;
    }

    public void setInventoryLogType(InventoryLogType inventoryLogType) {
        this.inventoryLogType = inventoryLogType;
    }

    public int getInventoryId() {
        return inventoryId;
    }

    public void setInventoryId(int inventoryId) {
        this.inventoryId = inventoryId;
    }

    public int getPreviousQuantity() {
        return previousQuantity;
    }

    public void setPreviousQuantity(int previousQuantity) {
        this.previousQuantity = previousQuantity;
    }

    public double getPreviousPrice() {
        return previousPrice;
    }

    public void setPreviousPrice(double previousPrice) {
        this.previousPrice = previousPrice;
    }

    public int getCurrentQuantity() {
        return currentQuantity;
    }

    public void setCurrentQuantity(int currentQuantity) {
        this.currentQuantity = currentQuantity;
    }

    public double getCurrentPrice() {
        return currentPrice;
    }

    public void setCurrentPrice(double currentPrice) {
        this.currentPrice = currentPrice;
    }
}
