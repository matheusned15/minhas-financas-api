package com.minhasfinancas.demo.service;

import java.util.Optional;

import com.minhasfinancas.demo.model.entity.Usuario;

public interface UsuarioService {

	Usuario autenticar(String email, String senha);

	Usuario salvarUsuario(Usuario usuario);

	void validarEmail(String email);

	Optional<Usuario> obterPorId(Long id);

}
