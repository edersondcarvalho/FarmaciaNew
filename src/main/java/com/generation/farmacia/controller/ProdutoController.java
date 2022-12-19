package com.generation.farmacia.controller;

import com.generation.farmacia.model.Produto;
import com.generation.farmacia.repository.ProdutoRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("produto")
@CrossOrigin(origins = "*",allowedHeaders = "*")
public class ProdutoController {
    @Autowired
    private ProdutoRepository produtoRepository;
    @GetMapping
    public ResponseEntity<List<Produto>> getProduto()
    {
        return ResponseEntity.ok(produtoRepository.findAll()) ;
    }
    @GetMapping("/{id}")
    public ResponseEntity<Produto> getProdutoById(@PathVariable Long id)
    {
        return produtoRepository.findById(id)
                .map(resposta -> ResponseEntity.ok(resposta))
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build()) ;
    }
    @GetMapping("titulo/{titulo}")
    public ResponseEntity<List<Produto>> getProdutoByName(@PathVariable String titulo)
    {
        return ResponseEntity.ok(produtoRepository.findAllByNomeContainingIgnoreCase(titulo));
    }
    @PostMapping
    public ResponseEntity postProduto(@Valid @RequestBody Produto produto)
    {
        return ResponseEntity.status(HttpStatus.CREATED).body(produtoRepository.save(produto));

    }
    @PutMapping
    public ResponseEntity putProduto(@Valid @RequestBody Produto produto)
    {
        return produtoRepository.findById(produto.getId())
                .map(resposta -> ResponseEntity.status(HttpStatus.OK).body(produtoRepository.save(produto)))
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());

    }
    @GetMapping("precomenor/{valor}")
    public ResponseEntity<List<Produto>> getProdutoByPrecoMenor(@PathVariable BigDecimal valor)
    {
        return ResponseEntity.ok(produtoRepository.findAllByPrecoIs(valor));
    }
    @GetMapping("precomaior/{valor}")
    public ResponseEntity<List<Produto>> getProdutoByPrecoMaior(@PathVariable BigDecimal valor)
    {
        return ResponseEntity.ok(produtoRepository.findAllByPrecoIsM(valor));
    }
    @PostMapping ("/precointervalo")
    public ResponseEntity<List<Produto>> getProdutoByBetween(@RequestParam BigDecimal[] list)
    {
        return ResponseEntity.ok(produtoRepository.findAllByPrecoIsB(list[0] ,list[1]));
    }
    @DeleteMapping("/{id}")
    public void deletarProduto (@PathVariable Long id)
    {
        Optional<Produto> categoria = produtoRepository.findById(id);

        if(categoria.isEmpty())
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        produtoRepository.deleteById(id);



    }

}
