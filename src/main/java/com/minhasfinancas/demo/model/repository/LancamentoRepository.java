package com.minhasfinancas.demo.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.minhasfinancas.demo.model.entity.Lancamento;

public interface LancamentoRepository extends JpaRepository<Lancamento, Long> {

}
