package org.bte.restaurant.inventory.server.controllers;

import lombok.AllArgsConstructor;
import org.bte.restaurant.inventory.connector.api.IngredientsApi;
import org.bte.restaurant.inventory.connector.model.BaseResponseDTO;
import org.bte.restaurant.inventory.connector.model.IngredientSetupDTO;
import org.bte.restaurant.inventory.server.mappers.IngredientSetupMapper;
import org.bte.restaurant.inventory.server.model.IngredientSetup;
import org.bte.restaurant.inventory.server.model.IngredientSetup_;
import org.bte.restaurant.inventory.server.repository.IngredientsRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@AllArgsConstructor
public class IngredientsController implements IngredientsApi {

    private final IngredientsRepository repository;
    private final IngredientSetupMapper mapper;

    private final Sort ingredientsSortBy = Sort.by(Sort.Order.desc(IngredientSetup_.TITLE), Sort.Order.desc(IngredientSetup_.ID));

    @Override
    public ResponseEntity<List<IngredientSetupDTO>> getIngredients(Integer offset, Integer limit) {
        offset = offset == null ? 0 : offset;
        limit = limit == null ? 0 : limit;

        Pageable pageable = PageRequest.of(offset, limit, ingredientsSortBy);
        Page<IngredientSetup> items = repository.findAll(pageable);

        return ResponseEntity.ok(mapper.toDto(items.toList()));
    }

    @Override
    public ResponseEntity<List<IngredientSetupDTO>> updateIngredients(List<IngredientSetupDTO> ingredientSetupDTO) {
        List<IngredientSetup> items =  mapper.toModel(ingredientSetupDTO);
        List<IngredientSetup> updatedItems = repository.saveAll(items);
        return ResponseEntity.ok(mapper.toDto(updatedItems));
    }

    @Override
    public ResponseEntity<BaseResponseDTO> deleteIngredients(List<IngredientSetupDTO> ingredientSetupDTO) {
        BaseResponseDTO responseDTO = new BaseResponseDTO();

        List<Integer> ids = ingredientSetupDTO.stream().map(IngredientSetupDTO::getId).toList();
        try {
            repository.deleteAllById(ids);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }

        responseDTO.setSuccess(true);
        return ResponseEntity.ok(responseDTO);
    }
}
