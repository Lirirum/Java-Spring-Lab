package com.karacheban.demo.controller;
import com.karacheban.demo.Dto.ApiResponseWrapper;
import com.karacheban.demo.Dto.CreatePatientDto;
import com.karacheban.demo.Dto.PatientDto;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.List;

@RestController
@RequestMapping("api/patients")
public class RemotePatientController {
    private final WebClient webClient;
    public RemotePatientController(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder
                .baseUrl("http://localhost:5162/api/patients")
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .build();
    }

    @GetMapping
    public ResponseEntity<Mono<List<PatientDto>>> getAllPatients() {
        return ResponseEntity.ok(
                webClient.get()
                        .retrieve()
                        .bodyToMono(new ParameterizedTypeReference<ApiResponseWrapper<List<PatientDto>>>() {})
                        .map(ApiResponseWrapper::getResult)
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<Mono<PatientDto>> getPatientById(@PathVariable int id) {
        return ResponseEntity.ok(
                webClient.get()
                        .uri("/{id}", id)
                        .retrieve()
                        .bodyToMono(new ParameterizedTypeReference<ApiResponseWrapper<PatientDto>>() {})
                        .map(ApiResponseWrapper::getResult)
        );
    }



    @PostMapping
    public Mono<ResponseEntity<PatientDto>> createPatient(@RequestBody CreatePatientDto patient) {
        return webClient.post()
                .bodyValue(patient)
                .exchangeToMono(response ->
                        response.toEntity(PatientDto.class)
                                .map(entity ->
                                        ResponseEntity.status(entity.getStatusCode())
                                                .headers(headers -> headers.putAll(entity.getHeaders()))
                                                .body(entity.getBody())
                                )
                );
    }

}
