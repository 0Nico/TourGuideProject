package com.tourguide.gps.client;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;


@FeignClient(value = "users", url = "${CLIENT_USERS_BASE_URL:http://localhost:8083/user}")
public interface UserClient {

	@RequestMapping(method = RequestMethod.GET, value = "/list")
	List<String> getUserList();
}
