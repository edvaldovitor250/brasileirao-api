package br.com.brasileirao_api.controller;

import br.com.brasileirao_api.dto.partida.PartidaDTO;
import br.com.brasileirao_api.dto.partida.PartidaResponseDTO;
import br.com.brasileirao_api.exception.StandardError;
import br.com.brasileirao_api.model.Equipe;
import br.com.brasileirao_api.model.Partida;
import br.com.brasileirao_api.service.PartidaService;
import io.swagger.annotations.Api;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.servlet.http.Part;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@Api("API de partidas")
@RestController
@RequestMapping("/api/v1/partidas")
@AllArgsConstructor
public class PartidaController {

    private final PartidaService partidaService;

    @Operation(summary = "Busca uma partida pelo ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK", content = {@Content(schema = @Schema(implementation = Partida.class))}),
            @ApiResponse(responseCode = "400", description = "Bad request", content = {@Content(schema = @Schema(implementation = StandardError.class))}),
            @ApiResponse(responseCode = "401", description = "Unauthorized", content = {@Content(schema = @Schema(implementation = StandardError.class))}),
            @ApiResponse(responseCode = "403", description = "Forbidden", content = {@Content(schema = @Schema(implementation = StandardError.class))}),
            @ApiResponse(responseCode = "404", description = "Not found", content = {@Content(schema = @Schema(implementation = StandardError.class))}),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = {@Content(schema = @Schema(implementation = StandardError.class))})
    })
    @GetMapping("/{id}")
    public ResponseEntity<Partida> buscarPartidaId(@PathVariable("id") Long id) {
        return ResponseEntity.ok().body(partidaService.buscarPartidaPorId(id));
    }

    @Operation(summary = "Listar partidas")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK", content = {@Content(schema = @Schema(implementation = PartidaResponseDTO.class))}),
            @ApiResponse(responseCode = "400", description = "Bad request", content = {@Content(schema = @Schema(implementation = StandardError.class))}),
            @ApiResponse(responseCode = "401", description = "Unauthorized", content = {@Content(schema = @Schema(implementation = StandardError.class))}),
            @ApiResponse(responseCode = "403", description = "Forbidden", content = {@Content(schema = @Schema(implementation = StandardError.class))}),
            @ApiResponse(responseCode = "404", description = "Not found", content = {@Content(schema = @Schema(implementation = StandardError.class))}),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = {@Content(schema = @Schema(implementation = StandardError.class))})
    })
    @GetMapping
    public ResponseEntity<PartidaResponseDTO> listarPartidas(){
        return  ResponseEntity.ok().body(partidaService.listarPartidas());
    }

    @Operation(summary = "Inserir partida")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "OK", content = {@Content(schema = @Schema(implementation = Partida.class))}),
            @ApiResponse(responseCode = "400", description = "Bad request", content = {@Content(schema = @Schema(implementation = StandardError.class))}),
            @ApiResponse(responseCode = "401", description = "Unauthorized", content = {@Content(schema = @Schema(implementation = StandardError.class))}),
            @ApiResponse(responseCode = "403", description = "Forbidden", content = {@Content(schema = @Schema(implementation = StandardError.class))}),
            @ApiResponse(responseCode = "404", description = "Not found", content = {@Content(schema = @Schema(implementation = StandardError.class))}),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = {@Content(schema = @Schema(implementation = StandardError.class))})
    })
    @PostMapping()
    public ResponseEntity<Partida> inserirPartida(@Valid @RequestBody PartidaDTO dto){
        Partida partida = partidaService.inserirPartida(dto);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(partida.getId()).toUri();

        return  ResponseEntity.created(location).body(partida);
    }

    @Operation(summary = "Alterar partida")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "OK", content = {@Content(schema = @Schema(implementation = Void.class))}),
            @ApiResponse(responseCode = "400", description = "Bad request", content = {@Content(schema = @Schema(implementation = StandardError.class))}),
            @ApiResponse(responseCode = "401", description = "Unauthorized", content = {@Content(schema = @Schema(implementation = StandardError.class))}),
            @ApiResponse(responseCode = "403", description = "Forbidden", content = {@Content(schema = @Schema(implementation = StandardError.class))}),
            @ApiResponse(responseCode = "404", description = "Not found", content = {@Content(schema = @Schema(implementation = StandardError.class))}),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = {@Content(schema = @Schema(implementation = StandardError.class))})
    })
    @PutMapping("/{id}")
    public ResponseEntity<Void> alterarPartida(@PathVariable("id") Long id,@Valid @RequestBody PartidaDTO dto){
        partidaService.alterarPartida(id,dto);
                return ResponseEntity.noContent().build();

    }
}
