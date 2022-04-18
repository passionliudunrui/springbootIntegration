package com.springbootmybatis.dao;

import com.springbootmybatis.entity.Note;

import java.util.List;

public interface NoteDao {
    List<Note> findById(Long userId);
    int insert(Note note);

}
