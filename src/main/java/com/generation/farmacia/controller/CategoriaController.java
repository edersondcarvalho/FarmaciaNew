package com.generation.farmacia.controller;

import com.generation.farmacia.model.Categoria;
import com.generation.farmacia.repository.CategoriaRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping("categoria")
public class CategoriaController {
    @Autowired
    private CategoriaRepository categoriaRepository;
    @GetMapping
    public ResponseEntity<List<Categoria>> getProduto()
    {
        return ResponseEntity.ok(categoriaRepository.findAll()) ;
    }
    @GetMapping("/{id}")
    public ResponseEntity<Categoria> getProdutoById(@PathVariable Long id)
    {
        return categoriaRepository.findById(id)
                .map(resposta -> ResponseEntity.ok(resposta))
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build()) ;
    }
    @GetMapping("titulo/{titulo}")
    public ResponseEntity<List<Categoria>> getProdutoByName(@PathVariable String titulo)
    {
        return ResponseEntity.ok(categoriaRepository.findAllByTituloContainingIgnoreCase(titulo));
    }
    @PostMapping
    public ResponseEntity postProduto(@Valid @RequestBody Categoria categoria)
    {
        return ResponseEntity.status(HttpStatus.CREATED).body(categoriaRepository.save(categoria));

    }
    @PutMapping
    public ResponseEntity putProduto(@Valid @RequestBody Categoria categoria)
    {
        return categoriaRepository.findById(categoria.getId())
                .map(resposta -> ResponseEntity.status(HttpStatus.OK).body(categoriaRepository.save(categoria)))
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());

    }
    @DeleteMapping("/{id}")
    public void deletarProduto (@PathVariable Long id)
    {
        Optional<Categoria> categoria = categoriaRepository.findById(id);

        if(categoria.isEmpty())
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        categoriaRepository.deleteById(id);



    }


}
