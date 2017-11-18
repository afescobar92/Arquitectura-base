package com.cosmo.arquitecturamvpbase.repository;

import com.cosmo.arquitecturamvpbase.helper.ServicesFactory;
import com.cosmo.arquitecturamvpbase.model.Note;
import com.cosmo.arquitecturamvpbase.services.IServices;

/**
 * Created by user on 07/11/2017.
 */

public class NotesRepository implements INotesRepository {

    private IServices services;

    public NotesRepository() {
        ServicesFactory servicesFactory = new ServicesFactory(ServicesFactory.TypeConverter.XML);
        services = (IServices) servicesFactory.getInstance(IServices.class);
    }

    @Override
    public Note getNote() {
        return services.getNotes();
    }
}
