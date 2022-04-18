package com.springbootmybatis.service.impl;

import com.springbootmybatis.dao.NoteDao;
import com.springbootmybatis.entity.Note;
import com.springbootmybatis.service.NoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@SuppressWarnings("all")
public class NoteServiceImpl implements NoteService {
    @Autowired
    private NoteDao noteDao;

    @Override
    public List<Note> findById(Long userId) {
        return noteDao.findById(userId);
    }

    @Override
    public int insert(Note note) {
        int insert = noteDao.insert(note);
        return insert;
    }
}
