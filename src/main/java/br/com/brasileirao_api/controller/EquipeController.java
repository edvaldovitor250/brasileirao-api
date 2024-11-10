package br.com.brasileirao_api.controller;

import br.com.brasileirao_api.dto.equipe.EquipeDTO;
import br.com.brasileirao_api.dto.equipe.EquipeResponseDTO;
import br.com.brasileirao_api.exception.StandardError;
import br.com.brasileirao_api.model.Equipe;
import br.com.brasileirao_api.service.EquipeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.servlet.Servlet;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/api/v1/equipes")
@AllArgsConstructor
public class EquipeController {

    private final EquipeService equipeService;

    @Operation(summary = "Busca uma equipe pelo ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK", content = {@Content(schema = @Schema(implementation = Equipe.class))}),
            @ApiResponse(responseCode = "400", description = "Bad request", content = {@Content(schema = @Schema(implementation = StandardError.class))}),
            @ApiResponse(responseCode = "401", description = "Unauthorized", content = {@Content(schema = @Schema(implementation = StandardError.class))}),
            @ApiResponse(responseCode = "403", description = "Forbidden", content = {@Content(schema = @Schema(implementation = StandardError.class))}),
            @ApiResponse(responseCode = "404", description = "Not found", content = {@Content(schema = @Schema(implementation = StandardError.class))}),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = {@Content(schema = @Schema(implementation = StandardError.class))})
    })
    @GetMapping("/{id}")
    public ResponseEntity<Equipe> buscarEquipeId(@PathVariable("id") Long id) {
        return ResponseEntity.ok().body(equipeService.buscarEquipeId(id));
    }


    @Operation(summary = "Listar equipe")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK", content = {@Content(schema = @Schema(implementation = EquipeResponseDTO.class))}),
            @ApiResponse(responseCode = "400", description = "Bad request", content = {@Content(schema = @Schema(implementation = StandardError.class))}),
            @ApiResponse(responseCode = "401", description = "Unauthorized", content = {@Content(schema = @Schema(implementation = StandardError.class))}),
            @ApiResponse(responseCode = "403", description = "Forbidden", content = {@Content(schema = @Schema(implementation = StandardError.class))}),
            @ApiResponse(responseCode = "404", description = "Not found", content = {@Content(schema = @Schema(implementation = StandardError.class))}),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = {@Content(schema = @Schema(implementation = StandardError.class))})
    })
    @GetMapping("")
    public ResponseEntity<EquipeResponseDTO> listarEquipes(){
        return  ResponseEntity.ok().body(equipeService.listarEquipes());
    }

    @Operation(summary = "Inserir equipe")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Created", content = {@Content(schema = @Schema(implementation = Equipe.class))}),
            @ApiResponse(responseCode = "400", description = "Bad request", content = {@Content(schema = @Schema(implementation = StandardError.class))}),
            @ApiResponse(responseCode = "401", description = "Unauthorized", content = {@Content(schema = @Schema(implementation = StandardError.class))}),
            @ApiResponse(responseCode = "403", description = "Forbidden", content = {@Content(schema = @Schema(implementation = StandardError.class))}),
            @ApiResponse(responseCode = "404", description = "Not found", content = {@Content(schema = @Schema(implementation = StandardError.class))}),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = {@Content(schema = @Schema(implementation = StandardError.class))})
    })
    @PostMapping()
    public ResponseEntity<Equipe> inserirEquipe(@Valid @RequestBody EquipeDTO dto){
        Equipe equipe = equipeService.inserirEquipe(dto);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(equipe.getId()).toUri();

        return ResponseEntity.created(location).body(equipe);
    }

    @Operation(summary = "Alterar equipe")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "no content", content = {@Content(schema = @Schema(implementation = Void.class))}),
            @ApiResponse(responseCode = "400", description = "Bad request", content = {@Content(schema = @Schema(implementation = StandardError.class))}),
            @ApiResponse(responseCode = "401", description = "Unauthorized", content = {@Content(schema = @Schema(implementation = StandardError.class))}),
            @ApiResponse(responseCode = "403", description = "Forbidden", content = {@Content(schema = @Schema(implementation = StandardError.class))}),
            @ApiResponse(responseCode = "404", description = "Not found", content = {@Content(schema = @Schema(implementation = StandardError.class))}),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = {@Content(schema = @Schema(implementation = StandardError.class))})
    })
    @PutMapping("/{id}")
    public ResponseEntity<Void> alterarEquipe(@PathVariable("id") long id, @Valid @RequestBody EquipeDTO dto){

        equipeService.alterarEquipe(id,dto);

        return ResponseEntity.noContent().build();
    }






}
