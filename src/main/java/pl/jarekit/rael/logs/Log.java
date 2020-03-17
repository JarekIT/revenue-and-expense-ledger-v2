package pl.jarekit.rael.logs;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter @Getter
@NoArgsConstructor @AllArgsConstructor
public class Log {

    private Site site;
    private LocalDateTime date;
    private String message;
    private Level level;

}