package ch.hegarc.ig.formatter;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class LocalDateSerializer extends StdSerializer<LocalDate> {
    private static SimpleDateFormat formatter
            = new SimpleDateFormat("dd.MM.yyyy");

    private DateTimeFormatter format = DateTimeFormatter.ofPattern("dd.MM.yyyy");

    public LocalDateSerializer() {
        this(LocalDate.class);
    }

    public LocalDateSerializer(Class<LocalDate> t) {
        super(t);
    }

    @Override
    public void serialize (LocalDate date, JsonGenerator gen, SerializerProvider arg2)
            throws IOException{
        gen.writeString(date.format(format));
    }
}
