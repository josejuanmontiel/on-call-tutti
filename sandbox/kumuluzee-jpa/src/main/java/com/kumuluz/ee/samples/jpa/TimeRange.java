package com.kumuluz.ee.samples.jpa;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.*;


/**
 * This class represents the RETAIL_PRICES entity in database.
 */
@Entity
@Table(name = "time_range")
@NamedQueries({
	@NamedQuery(
			name = "TimeRange.getAll",
			query = "SELECT rp " +
					"FROM TimeRange rp " +
					"ORDER BY rp.startDate ASC"
	)
})

// List<RetailPrice> list = offerRepository.findByBrandIdAndProductPartnumberOrderByStartDateAsc(brandId, productPartnumber);
public class TimeRange {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;

  private Long startDate;

  private Long endDate;

  private Long person;

  private Integer level;

  public TimeRange() {

  }

  public TimeRange(Long id, Long startDate, Long endDate, Long person, Integer level) {
    this.id = id;
    this.startDate = startDate;
    this.endDate = endDate;
    this.person = person;
    this.level = level;
  }

  public Long getId() {
	  return id;
  }
  public void setId(Long id) {
	  this.id = id;
  }
  public Long getStartDate() {
      return startDate;
  }
  public void setStartDate(Long startDate) {
      this.startDate = startDate;
  }
  public Long getEndDate() {
      return endDate;
  }
  public void setEndDate(Long endDate) {
      this.endDate = endDate;
  }
  public Long getPerson() {
      return person;
  }
  public void setPerson(Long person) {
      this.person = person;
  }
  public Integer getLevel() {
      return level;
  }
  public void setLevel(Integer level) {
      this.level = level;
  }
  @Override
  public String toString() {
      return "TimeRange [id=" + id + ", startDate=" + startDate + ", endDate=" + endDate + ", person=" + person + ", level=" + level + "]";
  }

}