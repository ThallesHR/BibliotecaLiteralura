package com.alura.literalura.repository;

import com.alura.literalura.model.Livro;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LivroRepository  extends JpaRepository<Livro, Long> {
    Optional<Livro> findByTituloContainingIgnoreCase(String titulo);
    Long countByIdiomas(String idioma);


}
