package ru.yandex.practicum.filmorate.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.storage.genre.GenreDaoStorage;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/genres")
public class GenreController {
    private final GenreDaoStorage genreDaoStorage;

    @GetMapping
    public List<Genre> getGenres() {
        return genreDaoStorage.getAllGenres();
    }

    @GetMapping("{id}")
    public Genre getGenre(@PathVariable int id) {
        return genreDaoStorage.getGenreById(id);
    }
}
