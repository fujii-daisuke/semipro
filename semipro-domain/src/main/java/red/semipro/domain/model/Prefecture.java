package red.semipro.domain.model;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Prefecture implements Serializable {

    private static final long serialVersionUID = 1L;
    private Integer id;
    private String name;
    
}
