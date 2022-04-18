package com.springbootmybatis.service;

import com.springbootmybatis.entity.Note;

import java.util.List;

public interface NoteService {
    List<Note> findById(Long userId);
    int insert(Note note);
}
