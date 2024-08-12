package com.ideaaedi.zoo.commonbase.zoo_event;

import com.ideaaedi.zoo.commonbase.zoo_entity.LoggerLevelDTO;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;

import javax.annotation.Nullable;
import java.util.Collections;
import java.util.List;

@Getter
public class LoggerLevelChangeEvent extends ApplicationEvent {
    
    /**
     * 最新日志级别
     */
    private final List<LoggerLevelDTO> lastestLoggerLevelList;
    
    public LoggerLevelChangeEvent(@Nullable List<LoggerLevelDTO> lastestLoggerLevelList) {
        super(lastestLoggerLevelList == null ? Collections.emptyList() : lastestLoggerLevelList);
        this.lastestLoggerLevelList = lastestLoggerLevelList;
    }
}
