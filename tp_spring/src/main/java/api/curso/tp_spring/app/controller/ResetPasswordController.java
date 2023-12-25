package api.curso.tp_spring.app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import api.curso.tp_spring.app.service.usuario.IUsuarioService;

@RestController
@RequestMapping("/api")
public class ResetPasswordController {

    @Autowired
    private IUsuarioService usuarioService;
    
}
