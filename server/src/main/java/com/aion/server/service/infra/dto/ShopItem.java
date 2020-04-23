package com.aion.server.service.infra.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ShopItem {

    private String item_id;
    private String item_name;
    private String item_desc;
    private String item_category;
    private String item_count;
    private String price;
    private String item_image_path;
}
