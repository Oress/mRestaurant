package org.bte.restaurant.orders.server.clients.inventory;

import org.bte.auth.client.feign.api.MenuApi;
import org.springframework.cloud.openfeign.FeignClient;

@FeignClient("stores")
public interface ManuItemsClient extends MenuApi {
}
