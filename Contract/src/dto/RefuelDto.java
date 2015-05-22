package dto;

import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.util.Date;

/**
 * Created by Petikk on 2. 5. 2015.
 */
@XmlRootElement
public class RefuelDto implements PersistentEntityDto,Serializable {
    private Long id;
    private Date date;
    private Double amount;
    private Double price;
    private Double pricePerUnit;
    private Long userId;
    private String uName;
    private String uSurname;
    private String uUserName;

    public RefuelDto() {
    }

    public RefuelDto(Long id) {
        this.id = id;
    }

    public RefuelDto(Long id, Date date, Double amount, Double price, Double pricePerUnit, Long userId, String uName, String uSurname, String uUserName) {
        this.id = id;
        this.date = date;
        this.amount = amount;
        this.price = price;
        this.pricePerUnit = pricePerUnit;
        this.userId = userId;
        this.uName = uName;
        this.uSurname = uSurname;
        this.uUserName = uUserName;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Double getPricePerUnit() {
        return pricePerUnit;
    }

    public void setPricePerUnit(Double pricePerUnit) {
        this.pricePerUnit = pricePerUnit;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getuName() {
        return uName;
    }

    public void setuName(String uName) {
        this.uName = uName;
    }

    public String getuSurname() {
        return uSurname;
    }

    public void setuSurname(String uSurname) {
        this.uSurname = uSurname;
    }

    public String getuUserName() {
        return uUserName;
    }

    public void setuUserName(String uUserName) {
        this.uUserName = uUserName;
    }


}
