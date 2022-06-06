package com.springer.quality.database.entities;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "CA.ITEM")
@NamedQuery(name = "CaItemEntity.findItem", query = "select caItemEntity from CaItemEntity as caItemEntity where " +
        "caItemEntity.contentId=:contentId")
public class CaItemEntity {
    @Id
    @Column(name = "ItemID")
    private String itemId;

    @Column(name = "ItemType")
    private String itemType;

    @Column(name = "ScanningUser")
    private String scanningUser;

    @Column(name = "Adnm")
    private String adnm;

    @Column(name = "ContentId")
    private String contentId;

}
