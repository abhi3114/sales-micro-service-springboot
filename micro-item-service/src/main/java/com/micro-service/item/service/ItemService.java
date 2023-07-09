package com.micro-service.item.service;

import java.util.List;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.micro-service.item.common.exceptions.RecordNotFoundException;
import com.micro-service.item.common.messages.BaseResponse;
import com.micro-service.item.common.messages.CustomMessage;
import com.micro-service.item.common.utils.Topic;
import com.micro-service.item.dto.ItemDTO;
import com.micro-service.item.entity.ItemEntity;
import com.micro-service.item.repo.ItemRepo;

@Service
@Transactional
public class ItemService {

	@Autowired
	private ItemRepo itemRepo;

	public List<ItemDTO> findItemList() {
		return itemRepo.findAll().stream().map(this::copyItemEntityToDto).collect(Collectors.toList());
	}

	public ItemDTO findByItemId(Long itemId) {
		ItemEntity itemEntity = itemRepo.findById(itemId)
				.orElseThrow(() -> new RecordNotFoundException("Item id '" + itemId + "' does not exist !"));
		return copyItemEntityToDto(itemEntity);
	}

	public BaseResponse createOrUpdateItem(ItemDTO itemDTO) {
		ItemEntity itemEntity = copyItemDtoToEntity(itemDTO);
		itemRepo.save(itemEntity);
		return new BaseResponse(Topic.ITEM.getName() + CustomMessage.SAVE_SUCCESS_MESSAGE, HttpStatus.CREATED.value());
	}

	public BaseResponse deleteItemById(Long itemId) {
		if (itemRepo.existsById(itemId)) {
			itemRepo.deleteById(itemId);
		} else {
			throw new RecordNotFoundException("No record found for given id: " + itemId);
		}
		return new BaseResponse(Topic.ITEM.getName() + CustomMessage.DELETE_SUCCESS_MESSAGE, HttpStatus.OK.value());
	}

	private ItemDTO copyItemEntityToDto(ItemEntity itemEntity) {
		ItemDTO itemDTO = new ItemDTO();
		BeanUtils.copyProperties(itemEntity, itemDTO);
		return itemDTO;
	}

	private ItemEntity copyItemDtoToEntity(ItemDTO itemDTO) {
		ItemEntity itemEntity = new ItemEntity();
		BeanUtils.copyProperties(itemDTO, itemEntity);
		return itemEntity;
	}

}
