package com.minhasfinancas.demo.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.minhasfinancas.demo.model.entity.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

}
