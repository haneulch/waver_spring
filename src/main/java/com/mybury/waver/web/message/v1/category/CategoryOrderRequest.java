package com.mybury.waver.web.message.v1.category;

import java.util.List;

public record CategoryOrderRequest(
    List<Integer> categoryIds
) {

}
