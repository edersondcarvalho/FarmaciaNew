package com.generation.farmacia.repository;

import com.generation.farmacia.model.Produto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;
@Repository
public interface ProdutoRepository extends JpaRepository<Produto,Long>{
    public List<Produto> findAllByNomeContainingIgnoreCase(@Param("nome")String nome);
    @Query("select e from Produto e where e.preco < :preco")
    List<Produto> findAllByPrecoIs(@Param("preco") BigDecimal preco);

    @Query("select e from Produto e where e.preco > :preco")
    List<Produto> findAllByPrecoIsM(@Param("preco") BigDecimal preco);

    @Query("select e from Produto e where e.preco between :valorInicial and :valorFinal")
    List<Produto> findAllByPrecoIsB(@Param("valorInicial") BigDecimal valorInicial ,@Param("valorFinal") BigDecimal valorFinal);

}
