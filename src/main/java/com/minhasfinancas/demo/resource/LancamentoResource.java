package com.minhasfinancas.demo.resource;

import java.util.List;
import java.util.Optional;

import com.minhasfinancas.demo.dto.LancamentoDTO;
import com.minhasfinancas.demo.exception.RegraNegocioException;
import com.minhasfinancas.demo.model.entity.Lancamento;
import com.minhasfinancas.demo.model.entity.Usuario;
import com.minhasfinancas.demo.model.enums.StatusLancamento;
import com.minhasfinancas.demo.model.enums.TipoLancamento;
import com.minhasfinancas.demo.service.LancamentoService;
import com.minhasfinancas.demo.service.UsuarioService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import lombok.RequiredArgsConstructor;


@RestController
@RequestMapping("/api/lancamentos")
@RequiredArgsConstructor
public class LancamentoResource {

    private final LancamentoService service;
    private UsuarioService usuarioService;


    @PostMapping
    public ResponseEntity salvar(@RequestBody LancamentoDTO dto){
        try {
            Lancamento entidade = converter(dto);
            entidade = service.salvar(entidade);
            return new ResponseEntity(entidade, HttpStatus.CREATED);
        } catch (RegraNegocioException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }

    }

    @PutMapping("{id}")
    public ResponseEntity atualizar(@PathVariable("id") Long id, @RequestBody LancamentoDTO dto){
            return service.obterPorId(id).map( entity -> {
                try {
                    Lancamento lancamento = converter(dto);
                    lancamento.setId(entity.getId());
                    service.atualizar(lancamento);
                    return ResponseEntity.ok(lancamento);
                } catch (RegraNegocioException e){
                    return ResponseEntity.badRequest().body(e.getMessage());
                }
            }).orElseGet( () ->
                    new ResponseEntity("Lancamento não encontrado na base de Dados.", HttpStatus.BAD_REQUEST));
    }

    private Lancamento converter(LancamentoDTO dto){
        Lancamento lancamento = new Lancamento();
        lancamento.setId(dto.getId());
        lancamento.setDescricao(dto.getDescricao());
        lancamento.setAno(dto.getAno());
        lancamento.setMes(dto.getMes());
        lancamento.setValor(dto.getValor());

        Usuario usuario = usuarioService.obterPorId(dto.getUsuario())
                .orElseThrow( () -> new RegraNegocioException("Usuário não encontrando para o Id informado"));

        lancamento.setUsuario(usuario);
        lancamento.setTipo(TipoLancamento.valueOf(dto.getTipo()));
        lancamento.setStatus(StatusLancamento.valueOf(dto.getStatus()));

        return lancamento;

    }
}
