package com.demo.springboot.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.demo.springboot.entity.Client;
import com.demo.springboot.service.ClientService;

@RestController
@RequestMapping("/client")
public class ClientController {
	@Autowired
	ClientService clientService;

	/**
	 * 依ID 查詢用戶資料
	 * 
	 * @param id
	 * @return
	 */
	@PreAuthorize("hasAnyRole('MANAGER','OPERATOR')")
	@GetMapping(value = "/searchById")
	@ResponseBody
	public Client getClient(@RequestParam(value = "id", required = true) int id) {
		Client client = clientService.findById(id);
		return client;
	}

	/**
	 * 依名稱模糊查詢用戶資料
	 * 
	 * @param name
	 * @return
	 */
	@PreAuthorize("hasAnyRole('MANAGER','OPERATOR')")
	@GetMapping(value = "/searchByName")
	@ResponseBody
	public List<Client> getClientsByName(@RequestParam(value = "name", required = false) String name) {
		List<Client> clientList = clientService.findByName(name);
		return clientList;
	}

	/**
	 * 新增用戶資料
	 * 
	 * @param client
	 * @return
	 */
	@PreAuthorize("hasRole('OPERATOR')")
	@PostMapping(value = "/create")
	@ResponseBody
	public Client createClient(@RequestBody Client client) {

		try {
			client = clientService.create(client);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}

		return client;
	}
	
	/**
	 * 新增多筆用戶資料
	 * 
	 * @param client
	 * @return
	 */
	@PreAuthorize("hasRole('OPERATOR')")
	@PostMapping(value = "/createMulti")
	@ResponseBody
	public String createMultiClient(@RequestBody List<Client> clients) {
		Boolean result =false;
		try {
			result = clientService.createMulti(clients);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}

		return result?"SUCCESS":"FAILD";
	}

	/**
	 * 更新用戶資料
	 * 
	 * @param client
	 * @return
	 */
	@PreAuthorize("hasRole('MANAGER')")
	@PutMapping(value = "/save")
	@ResponseBody
	public Client saveClient(@RequestBody Client client) {
		try {
			client = clientService.update(client);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}

		return client;
	}

	/**
	 * 刪除用戶資料
	 * 
	 * @param id
	 * @return
	 */
	@PreAuthorize("hasRole('MANAGER')")
	@DeleteMapping(value = "/delById")
	@ResponseBody
	public String deleteClientById(@RequestParam(value = "id", required = true) Integer id) {

		try {
			clientService.deleteById(id);
		} catch (Exception e) {
			e.printStackTrace();
			return "FAILD :" + e.getMessage();
		}

		return "SUCCESS";
	}
}
