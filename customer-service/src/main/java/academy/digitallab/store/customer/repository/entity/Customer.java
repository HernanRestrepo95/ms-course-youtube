package academy.digitallab.store.customer.repository.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.io.Serializable;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@Entity
@Table(name="tbl_customers")
public class Customer implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty(message = "El n�mero de documento no puede ser vac�o")
    @Size( min = 8 , max = 8, message = "El tama�o del n�mero de documento es 8")
    @Column(name = "number_id" , unique = true ,length = 8, nullable = false)
    private String numberID;

    @NotEmpty(message = "El nombre no puede ser vac�o")
    @Column(name="first_name", nullable=false)
    private String firstName;

    @NotEmpty(message = "El apellido no puede ser vac�o")
    @Column(name="last_name", nullable=false)
    private String lastName;

    @NotEmpty(message = "el correo no puede estar vac�o")
    @Email(message = "no es un direcci�n de correo bien formada")
    @Column(unique=true, nullable=false)
    private String email;

    @Column(name="photo_url")
    private String photoUrl;


    @NotNull(message = "la regi�n no puede ser vacia")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "region_id")
    @JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
    private Region region;

    private String state;
}
