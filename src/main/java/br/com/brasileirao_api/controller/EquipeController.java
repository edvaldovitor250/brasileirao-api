package br.com.brasileirao_api.controller;

import br.com.brasileirao_api.dto.equipe.EquipeResponseDTO;
import br.com.brasileirao_api.exception.StandardError;
import br.com.brasileirao_api.model.Equipe;
import br.com.brasileirao_api.service.EquipeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
            @ApiResponse(responseCode = "200", description = "OK", content = {@Content(schema = @Schema(implementation = Equipe.class))}),
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
}
