package com.uade.pds.findyourguide.model.trofeo;

import com.uade.pds.findyourguide.observer.IObserver;
import com.uade.pds.findyourguide.service.TrofeoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Component
public abstract class CondicionTrofeo implements IObserver {
    @Autowired protected TrofeoService trofeoService;


}
