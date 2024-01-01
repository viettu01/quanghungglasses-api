package fithou.tuplv.quanghungglassesapi.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BannerResponse implements Serializable {
    private Long id;
    private String name;
    private String image;
    private Boolean status;
    private Date createdDate;
    private Date updatedDate;
    private Long userId;
}